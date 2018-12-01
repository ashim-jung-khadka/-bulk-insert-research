package com.ashim.fxdeals.service;

import com.ashim.fxdeals.bean.DealCount;
import com.ashim.fxdeals.repo.DealCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ashimjk on 12/1/2018
 */
@Service
public class DealCountService {

    @Autowired
    private DealCountRepository dealCountRepository;

    public Iterable<DealCount> getAllDealCount() {
        return this.dealCountRepository.findAll();
    }

    public DealCount getDealCount(String fromCurrencyCode, String toCurrencyCode) {
        return this.dealCountRepository.findByFromCurrencyCodeAndToCurrencyCode(fromCurrencyCode, toCurrencyCode);
    }

    public void save(DealCount dealCount) {
        this.dealCountRepository.save(dealCount);
    }
}
