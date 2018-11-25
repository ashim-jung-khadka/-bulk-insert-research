package com.ashim.batch.processing.runner.repository;

import com.ashim.batch.processing.model.Customer;
import com.ashim.batch.processing.repository.BulkOperationsRepo;
import com.ashim.batch.processing.runner.config.CleanDb;
import org.skife.jdbi.v2.IDBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ashimjk on 11/24/2018
 */
public abstract class AbstractBulkOperations {

    private final Logger log = LoggerFactory.getLogger(AbstractBulkOperations.class);

    @Autowired
    public CleanDb cleanDb;

    @Autowired
    private IDBI dbi;

    /**
     * Persists lots of entities and checks the count.
     */
    public final void bulkInsert() {
        final int n = 100000;

        List<Customer> entities = createEntities(n);
        log.info("{} - Persisting...", this.getClassName());

        long start = System.currentTimeMillis();
        bulkOperations().bulkPersist(entities);
        long end = System.currentTimeMillis();

        log.info("{} - Done in {} sec", getClassName(), (end - start) / 1000);
        this.cleanDb.cleanUp();
    }

    private static List<Customer> createEntities(int num) {
        List<Customer> entities = new ArrayList<>(num);

        for (int i = 1; i <= num; i++) {
            entities.add(new Customer(i));
        }

        return entities;
    }

    protected abstract BulkOperationsRepo bulkOperations();

    private String getClassName() {
        return this.getClass().getSimpleName();
    }

}
