package com.ashim.batch.processing.repository;

import com.ashim.batch.processing.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ashimjk on 11/24/2018
 */
@Component
public interface BulkOperationsRepo {

	public void bulkPersist(List<Customer> entities);

}
