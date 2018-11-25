package com.ashim.batch.ex.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * @author ashimjk on 11/24/2018
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.ashim.batch.ex")
@EnableJpaRepositories(basePackages = "com.ashim.batch.ex.persist")
public class HibernateConfiguration {

    @Resource
    private Environment env;

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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
            throws IllegalStateException, PropertyVetoException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan("com.ashim.batch.ex.persist");

        entityManagerFactoryBean.setJpaProperties(hibProperties());
        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.connection.driver_class",
                "org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider");
        properties.put("hibernate.jdbc.batch_size", "50");
        properties.put("hibernate.c3p0.max_size", 5);
        properties.put("hibernate.c3p0.min_size", 0);
        properties.put("hibernate.c3p0.timeout", 60);
        properties.put("hibernate.c3p0.max_statements", 5);
        properties.put("hibernate.c3p0.acquireIncrement", 1);

        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.order_inserts", "true");
        properties.put("hibernate.order_updates", "true");
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws IllegalStateException, PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
