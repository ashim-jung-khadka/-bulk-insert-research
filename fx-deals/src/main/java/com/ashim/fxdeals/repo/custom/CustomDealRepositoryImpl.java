package com.ashim.fxdeals.repo.custom;

import com.ashim.fxdeals.bean.Deal;
import org.hibernate.StatelessSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ashimjk on 12/1/2018
 */
public class CustomDealRepositoryImpl implements CustomDealRepository {

    @Autowired private StatelessSession statelessSession;

    @Override
    @Transactional
    public void saveDeal(List<Deal> deals) {
        for (Deal entity : deals) {
            this.statelessSession.insert(entity);
        }
    }
}
