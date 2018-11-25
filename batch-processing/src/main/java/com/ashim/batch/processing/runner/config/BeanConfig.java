package com.ashim.batch.processing.runner.config;

import com.ashim.batch.processing.repository.BulkOperationsRepo;
import com.ashim.batch.processing.repository.DefaultBulkOperationsRepo;
import com.ashim.batch.processing.repository.FlushingBulkOperationsRepo;
import com.ashim.batch.processing.repository.JdbcTemplateBulkOperationsRepo;
import com.ashim.batch.processing.repository.JdbiBulkOperationsRepo;
import com.ashim.batch.processing.repository.StatelessSessionBulkOperationsRepo;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.IDBI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * @author ashimjk on 11/24/2018
 */
@Configuration
public class BeanConfig {

	// DataSource

	@Bean
	public DataSource dataSource() throws IllegalStateException, PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		// IMPORTANT! THE rewriteBatchedStatements=true is required, otherwise mysql won'tchange statements to one batch insert!
		dataSource
				.setJdbcUrl("jdbc:mysql://localhost:3306/examplesdb?rewriteBatchedStatements=true&autoReconnect=true");
		dataSource.setUser("root");
		dataSource.setPassword("root");

		return dataSource;
	}

	// EntityManagerFactory

	@Bean
	public EntityManagerFactory entityManagerFactory() throws PropertyVetoException {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaProperties(hibProperties());

		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.ashim.batch.processing");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.put("hibernate.connection.driver_class",
				"org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
		properties.put("hibernate.jdbc.batch_size", batchSize());
		properties.put("hibernate.c3p0.max_size", 10);
		properties.put("hibernate.c3p0.min_size", 0);
		properties.put("hibernate.c3p0.timeout", 60);
		properties.put("hibernate.c3p0.max_statements", 5);
		properties.put("hibernate.c3p0.acquireIncrement", 1);

		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.order_inserts", "true");
		properties.put("hibernate.order_updates", "true");
		return properties;
	}

	// EntityManager

	@Bean
	public SharedEntityManagerBean entityManager(EntityManagerFactory entityManagerFactory) {
		SharedEntityManagerBean bean = new SharedEntityManagerBean();
		bean.setEntityManagerFactory(entityManagerFactory);
		return bean;
	}

	// TransactionManager

	@Bean
	public JpaTransactionManager transactionManager() throws IllegalStateException, PropertyVetoException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory());
		return transactionManager;
	}

	// bulk operations

	@Bean
	@Qualifier("defaultRepo")
	public BulkOperationsRepo defaultBulkOperationsRepo(EntityManager entityManager) {
		return new DefaultBulkOperationsRepo(entityManager);
	}

	@Bean
	@Qualifier("flushingRepo")
	public BulkOperationsRepo flushingBulkOperationsRepo(EntityManager entityManager) {
		return new FlushingBulkOperationsRepo(entityManager, batchSize());
	}

	@Bean
	public StatelessSession statelessSession(EntityManagerFactory entityManager) {
		return entityManager.unwrap(SessionFactory.class).getSessionFactory().openStatelessSession();
	}

	// Note the special bean name.
	// According to the Spring Data naming conventions,
	// this will be used as implementation of the repository repository operations.
	@Bean(name = "customerRepositoryImpl")
	@Qualifier("statelessSessionRepo")
	public BulkOperationsRepo statelessSessionBulkOperationsRepo(EntityManager entityManager,
			StatelessSession statelessSession) {
		return new StatelessSessionBulkOperationsRepo(entityManager, statelessSession);
	}

	@Bean
	@Qualifier("jdbcTemplateRepo")
	public BulkOperationsRepo jdbcTemplateBulkOperationsRepo() throws PropertyVetoException {
		return new JdbcTemplateBulkOperationsRepo(jdbcTemplate());
	}

	@Bean
	@Qualifier("jdbiRepo")
	public BulkOperationsRepo jdbiBulkOperationsRepo() throws PropertyVetoException {
		return new JdbiBulkOperationsRepo(jdbi(), batchSize());
	}

	// JdbcTemplate

	@Bean
	public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
		return new JdbcTemplate(dataSource());
	}

	// JDBI

	@Bean
	public IDBI jdbi() throws PropertyVetoException {
		return new DBI(dataSource());
	}

	// JUnit rule to clear the DB

	@Bean
	public CleanDb clearDatabase() throws PropertyVetoException {
		return new CleanDb(jdbi());
	}

	// other configuration

	private int batchSize() {
		return 500;
	}

}
