package com.github.alexkirnsu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource);
        em.setPackagesToScan("com.github.alexkirnsu.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();

        properties.put(DIALECT, environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put(SHOW_SQL, environment.getProperty("spring.jpa.properties.hibernate.show_sql"));
        properties.put(HBM2DDL_AUTO, environment.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        properties.put(CURRENT_SESSION_CONTEXT_CLASS,
                environment.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        properties.put(JDBC_TIME_ZONE, environment.getProperty("spring.jpa.properties.hibernate.jdbc.time_zone"));

        properties.put(C3P0_MIN_SIZE, environment.getProperty("spring.jpa.properties.hibernate.c3p0.min_size"));
        properties.put(C3P0_MAX_SIZE, environment.getProperty("spring.jpa.properties.hibernate.c3p0.max_size"));
        properties.put(C3P0_ACQUIRE_INCREMENT,
                environment.getProperty("spring.jpa.properties.hibernate.c3p0.acquire_increment"));
        properties.put(C3P0_TIMEOUT, environment.getProperty("spring.jpa.properties.hibernate.c3p0.timeout"));
        properties.put(C3P0_MAX_STATEMENTS, environment.getProperty("spring.jpa.properties.hibernate.c3p0.max_statements"));

        return properties;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}