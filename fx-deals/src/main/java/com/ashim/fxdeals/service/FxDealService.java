package com.ashim.fxdeals.service;

import com.ashim.fxdeals.bean.DealCount;
import com.ashim.fxdeals.repo.DealRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ashimjk on 12/1/2018
 */
@Service
public class FxDealService {

    private static final Logger logger = LoggerFactory.getLogger(FxDealService.class);

    @Autowired private DealRepository dealRepository;
    @Autowired private StorageService storageService;
    @Autowired private OnlineService onlineService;
    @Autowired private OfflineService offlineService;

    public Stream<Path> loadAll() {
        return this.storageService.loadAll();
    }

    public boolean saveDeal(MultipartFile file) throws IOException {
        return onlineService.saveDeal(file);
    }

    public Resource loadAsResource(String filename) {
        return this.storageService.loadAsResource(filename);
    }

    public List<DealCount> getTotalInvalidDealCount() {
        List<Object[]> results = dealRepository.getTotalCountOfInvalidDeal();
        List<DealCount> dealCounts = new ArrayList<>();
        for (Object[] result : results) {

            String fromCurrencyCode = (String) result[0];
            String toCurrencyCode = (String) result[1];
            int count = ((Number) result[2]).intValue();

            DealCount dealCount = new DealCount(fromCurrencyCode, toCurrencyCode, count);
            dealCounts.add(dealCount);
        }

        return dealCounts;
    }
}
