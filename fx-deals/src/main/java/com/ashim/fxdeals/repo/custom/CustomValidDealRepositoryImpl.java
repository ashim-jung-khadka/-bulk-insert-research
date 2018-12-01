package com.ashim.fxdeals.repo.custom;

import com.ashim.fxdeals.bean.ValidDeal;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
public class CustomValidDealRepositoryImpl implements CustomValidDealRepository {

    @Autowired private StatelessSession statelessSession;

    @Override
    @Transactional
    public void saveDeal(List<ValidDeal> deals) {
        for (ValidDeal entity : deals) {
            this.statelessSession.insert(entity);
        }
    }
}
