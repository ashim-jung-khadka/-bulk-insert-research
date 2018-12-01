package com.ashim.fxdeals.repo.custom;

import com.ashim.fxdeals.bean.ValidDeal;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
@Component
public interface CustomValidDealRepository {

    void saveDeal(List<ValidDeal> deals);
}
