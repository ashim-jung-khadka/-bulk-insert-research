package com.ashim.batch.processing.runner;

import com.ashim.batch.processing.runner.repository.DefaultBulkOperations;
import com.ashim.batch.processing.runner.repository.FlushingBulkOperations;
import com.ashim.batch.processing.runner.repository.JdbcTemplateBulkOperations;
import com.ashim.batch.processing.runner.repository.JdbiBulkOperations;
import com.ashim.batch.processing.runner.repository.StatelessSessionBulkOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author ashimjk on 11/24/2018
 */
@Component
public class Processing {

	@Autowired
	@Qualifier("default")
	private DefaultBulkOperations defaultBulkOperations;

	@Autowired
	@Qualifier("flushing")
	private FlushingBulkOperations flushingBulkOperations;

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplateBulkOperations jdbcTemplateBulkOperations;

	@Autowired
	@Qualifier("jdbi")
	private JdbiBulkOperations jdbiBulkOperations;

	@Autowired
	@Qualifier("statelessSession")
	private StatelessSessionBulkOperations statelessSessionBulkOperations;

	public void run() {

		this.defaultBulkOperations.bulkInsert();

		this.flushingBulkOperations.bulkInsert();

		this.jdbcTemplateBulkOperations.bulkInsert();

		this.jdbiBulkOperations.bulkInsert();

		this.statelessSessionBulkOperations.bulkInsert();
	}
}
