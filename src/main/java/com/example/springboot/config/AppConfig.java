package com.example.springboot.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(value = "com.example.springboot")
public class AppConfig {
	@Autowired
	private Environment env;

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("db.driver"));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getDataSource());

		Properties props = new Properties();
		props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		em.setPersistenceUnitName("main");
		em.setPackagesToScan(env.getProperty("db.entity.package"));
		em.setJpaProperties(props);
		em.afterPropertiesSet();

		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}
}
