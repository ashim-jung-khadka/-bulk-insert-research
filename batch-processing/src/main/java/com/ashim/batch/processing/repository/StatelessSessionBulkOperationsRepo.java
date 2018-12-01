package com.ashim.batch.processing.repository;

import com.ashim.batch.processing.model.Customer;
import org.hibernate.StatelessSession;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ashimjk on 11/24/2018
 */
public class StatelessSessionBulkOperationsRepo implements BulkOperationsRepo {

    private final StatelessSession statelessSession;

    public StatelessSessionBulkOperationsRepo(StatelessSession statelessSession) {
        this.statelessSession = statelessSession;
    }

    @Override
    @Transactional
    public void bulkPersist(List<Customer> entities) {
        for (Customer entity : entities) {
            this.statelessSession.insert(entity);
        }
    }

}
