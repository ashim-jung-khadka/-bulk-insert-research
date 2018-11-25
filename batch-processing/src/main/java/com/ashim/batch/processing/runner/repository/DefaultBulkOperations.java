package com.ashim.batch.processing.runner.repository;

import com.ashim.batch.processing.repository.BulkOperationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author ashimjk on 11/24/2018
 */
@Component
@Qualifier("default")
public final class DefaultBulkOperations extends AbstractBulkOperations {

    @Autowired
    @Qualifier("defaultRepo")
    private BulkOperationsRepo bulkOperationsRepo;

    @Override
    protected BulkOperationsRepo bulkOperations() {
        return bulkOperationsRepo;
    }

}
