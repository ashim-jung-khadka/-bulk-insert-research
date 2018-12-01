package com.ashim.fxdeals.service;

import com.ashim.fxdeals.bean.Deal;
import com.ashim.fxdeals.bean.DealCount;
import com.ashim.fxdeals.bean.ValidDeal;
import com.ashim.fxdeals.repo.DealRepository;
import com.ashim.fxdeals.repo.ValidDealRepository;
import com.ashim.fxdeals.repo.custom.CustomDealRepository;
import com.ashim.fxdeals.repo.custom.CustomValidDealRepository;
import com.ashim.fxdeals.util.Validator;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
@Service
public class OnlineService {

    private static final Logger logger = LoggerFactory.getLogger(OfflineService.class);

    @Autowired private ValidDealRepository validDealRepository;
    @Autowired private DealRepository dealRepository;
    @Autowired private StorageService storageService;
    @Autowired private DealCountService dealCountService;
    @Autowired private CustomValidDealRepository customValidDealRepo;
    @Autowired private CustomDealRepository customDealRepo;
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
        File uploadedFile = this.storageService.loadAsResource(fileName).getFile();
        List<ValidDeal> validDeals = new ArrayList<>();
        List<Deal> invalidDeals = new ArrayList<>();

        logger.info("persisting in database");
        CSVReader reader = new CSVReader(new FileReader(uploadedFile));
        reader.skip(1);
        for (String[] nextLine : reader) {
            Deal deal = new Deal(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], fileName);
            this.persistData(deal, validDeals, invalidDeals);
        }

        if (validDeals.size() > 0) {
            this.customValidDealRepo.saveDeal(validDeals);
        }

        if (invalidDeals.size() > 0) {
            this.customDealRepo.saveDeal(invalidDeals);
        }

        this.updateDealCount(validDeals, invalidDeals, fileName);
        return true;
    }

    private void updateDealCount(List<ValidDeal> validDeals, List<Deal> invalidDeals, String fileName) {
        logger.info("updating deal count");

        List<Object[]> results = validDealRepository.getAggCountByFileName(fileName);
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

    private void persistData(Deal deal, List<ValidDeal> validDeals, List<Deal> invalidDeals) {
        if (Validator.isDTOValid(deal)) {
            validDeals.add(deal.buildValidDealObj());
            if (batchSize == validDeals.size()) {
                this.customValidDealRepo.saveDeal(validDeals);
                validDeals.clear();
            }

        } else {
            invalidDeals.add(deal);
            if (batchSize == invalidDeals.size()) {
                this.customDealRepo.saveDeal(invalidDeals);
                invalidDeals.clear();
            }
        }
    }
}
