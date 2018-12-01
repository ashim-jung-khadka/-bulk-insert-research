package com.ashim.fxdeals.service;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.bean.DealCount;
import com.ashim.fxdeals.bean.ValidDeal;
import com.ashim.fxdeals.repo.DealRepository;
import com.ashim.fxdeals.repo.ValidDealRepository;
import com.ashim.fxdeals.util.ThreadPoolManager;
import com.ashim.fxdeals.util.Validator;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
@Service
public class OfflineService {

    private static final Logger logger = LoggerFactory.getLogger(OfflineService.class);

    @Autowired private ValidDealRepository validDealRepository;
    @Autowired private DealRepository dealRepository;
    @Autowired private StorageService storageService;
    @Autowired private DealCountService dealCountService;
    @Value("${batch_size}") private int batchSize;

    public boolean saveDeal(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        // check if file already uploaded
        Long count = this.validDealRepository.countByFileName(fileName);
        if (count != null && count > 0) {
            return false;
        }

        count = this.dealRepository.countByFileName(fileName);
        if (count != null && count > 0) {
            return false;
        }

        this.storageService.storeFile(file);
        List<Deal> deals = this.extractDealFromJacksonCSV(file);

        List<ValidDeal> insertValidDeals = new ArrayList<>();
        List<Deal> insertInvalidDeals = new ArrayList<>();

        logger.info("uploading csv to table");
        ListeningExecutorService service = ThreadPoolManager.getExecutorService();
        List<ListenableFuture<String>> futures = new ArrayList<>();

        deals.forEach(deal -> {
            ListenableFuture<String> future = service
                    .submit(() -> this
                            .persistData(fileName, deal, insertInvalidDeals, insertValidDeals));
            futures.add(future);
        });

        this.updateDealCount(file, futures, insertInvalidDeals, insertValidDeals, validDealRepository, dealRepository);

        return true;
    }

    private void updateDealCount(MultipartFile file, List<ListenableFuture<String>> futures, List<Deal> invalidDeals,
            List<ValidDeal> validDeals, ValidDealRepository validDealRepository, DealRepository dealRepository) {

        ListenableFuture<List<String>> lf = Futures.successfulAsList(futures);
        Futures.addCallback(lf, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> rs) {

                if (validDeals.size() > 0) {
                    validDealRepository.saveAll(validDeals);
                }

                if (invalidDeals.size() > 0) {
                    dealRepository.saveAll(invalidDeals);
                }

                List<Object[]> results = validDealRepository.getAggCountByFileName(file.getOriginalFilename());
                for (Object[] result : results) {

                    String fromCurrencyCode = (String) result[0];
                    String toCurrencyCode = (String) result[1];
                    int count = ((Number) result[2]).intValue();

                    DealCount dealCount = dealCountService.getDealCount(fromCurrencyCode, toCurrencyCode);
                    if (dealCount == null) {
                        dealCount = new DealCount(fromCurrencyCode, toCurrencyCode, count);
                    } else {
                        dealCount.incrementCount(count);
                    }
                    dealCountService.save(dealCount);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                // No implementation required
            }
        });
    }

    private List<Deal> extractDealFromJacksonCSV(MultipartFile file) throws IOException {
        logger.info("extract data from csv");

        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        MappingIterator<Deal> readValues = mapper.readerFor(Deal.class).with(bootstrapSchema)
                .readValues(file.getInputStream());
        return readValues.readAll();
    }

    private List<Deal> extractDealFromOpenCSV(String fileName) throws IOException {
        logger.info("extract data from csv");

        return new CsvToBeanBuilder<Deal>(new FileReader(this.storageService.loadAsResource(fileName).getFile()))
                .withType(Deal.class).build()
                .parse();
    }

    private String persistData(String fileName, Deal deal, List<Deal> invalidDeals, List<ValidDeal> validDeals) {
        String currency = "";
        deal.setFileName(fileName);

        if (Validator.isDTOValid(deal)) {
            currency = deal.getFromCurrency();
            validDeals.add(deal.buildValidDealObj());
            if (batchSize == validDeals.size()) {
                this.validDealRepository.saveAll(validDeals);
                validDeals.clear();
            }

        } else {
            deal.setId(null);
            invalidDeals.add(deal);
            if (batchSize == invalidDeals.size()) {
                this.dealRepository.saveAll(invalidDeals);
                invalidDeals.clear();
            }
        }

        return currency;
    }
}
