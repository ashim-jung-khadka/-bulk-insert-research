package com.ashim.batch.processing.repository;

import com.ashim.batch.processing.model.Customer;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ashimjk on 11/24/2018
 */
public class JdbcTemplateBulkOperationsRepo implements BulkOperationsRepo {

	private final JdbcTemplate template;

	public JdbcTemplateBulkOperationsRepo(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	@Transactional
	public void bulkPersist(final List<Customer> entities) {
		this.template.batchUpdate("insert into customer (id) values (?)", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, entities.get(i).getId());
			}

			@Override
			public int getBatchSize() {
				return entities.size();
			}
		});
	}

}
