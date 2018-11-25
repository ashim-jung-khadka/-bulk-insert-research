package com.ashim.batch.ex.persist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * @author ashimjk on 11/24/2018
 */
@Repository
public class CustomExampleRepositoryImpl implements CustomExampleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${hibernate.jdbc.batch_size}")
    private String batchSize;

    public void bulkSave(Collection<ExampleEntity> entities) {
        int i = 0;
        for (ExampleEntity t : entities) {
            entityManager.persist(t);
            i++;
            if (batchSize.equals(i)) {
                // Flush a batch of inserts and release memory.
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

}
