package com.ashim.batch.processing.repository;

import com.ashim.batch.processing.model.Customer;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author ashimjk on 11/24/2018
 */
public class DefaultBulkOperationsRepo implements BulkOperationsRepo {

    private final EntityManager em;

    public DefaultBulkOperationsRepo(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void bulkPersist(List<Customer> entities) {
        for (Customer entity : entities) {
            this.em.persist(entity);
        }
    }

}
