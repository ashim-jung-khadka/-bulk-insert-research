package com.ashim.batch.processing.repository;

import com.ashim.batch.processing.model.Customer;
import org.skife.jdbi.v2.IDBI;
import org.skife.jdbi.v2.PreparedBatch;
import org.skife.jdbi.v2.tweak.HandleCallback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ashimjk on 11/24/2018
 */
public class JdbiBulkOperationsRepo implements BulkOperationsRepo {

    private final IDBI dbi;
    private final int batchSize;

    public JdbiBulkOperationsRepo(IDBI dbi, int batchSize) {
        this.dbi = dbi;
        this.batchSize = batchSize;
    }

    @Override
    @Transactional
    public void bulkPersist(final List<Customer> entities) {
        this.dbi.withHandle((HandleCallback<Void>) handle -> {
            String sql = "insert into customer (id) values (?)";

            PreparedBatch preparedBatch = handle.prepareBatch(sql);

            int i = 0;
            for (Customer entity : entities) {
                preparedBatch.add().bind(0, entity.getId());
                i++;

                if (i % this.batchSize == 0) {
                    preparedBatch.execute();
                    preparedBatch = handle.prepareBatch(sql);
                }
            }

            preparedBatch.execute();

            return null;
        });
    }
}
