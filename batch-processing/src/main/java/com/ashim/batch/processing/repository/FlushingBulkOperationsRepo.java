package com.ashim.batch.processing.repository;

import com.ashim.batch.processing.model.Customer;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author ashimjk on 11/24/2018
 */
public class FlushingBulkOperationsRepo implements BulkOperationsRepo {

	private final EntityManager em;

	private final int batchSize;

	public FlushingBulkOperationsRepo(EntityManager em, int batchSize) {
		this.em = em;
		this.batchSize = batchSize;
	}

	@Override
	@Transactional
	public void bulkPersist(List<Customer> entities) {
		int i = 0;
		for (Customer entity : entities) {
			this.em.persist(entity);
			i++;

			if (i % this.batchSize == 0) {
				this.em.flush();
				this.em.clear();
			}
		}
	}

}
