package com.akhil.video;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Singleton class to get a Hibernate session factory. Spring manages the
 * creation of the instance and will create only one session per container,
 * achieving singleton pattern.
 * 
 * @author akhil
 */
@Configuration
@EnableTransactionManagement
public class HibernateUtility {
	@Value("${db.driver}")
	private String jdbcDriver;

	@Value("${db.password}")
	private String dbPassword;

	@Value("${db.url}")
	private String dbUrl;

	@Value("${db.username}")
	private String dbUserName;

	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;

	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateDdl;

	@Value("${entitymanager.packagesToScan}")
	private String entityManagerPackages;

	/**
	 * Builds a hibernate transaction manager for a single Hibernate SessionFactory.
	 * Binds a Hibernate Session from the specified factory to the
	 * thread,potentially allowing for one thread-bound Session per factory.
	 * 
	 * @return The hibernate transaction manager.
	 */
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	/**
	 * Factory Bean that creates a Hibernate SessionFactory. It is shared across the
	 * spring application context achieving a singleton object per container.
	 * 
	 * @return The Session Factory Bean.
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(buildDataSource());
		sessionFactory.setPackagesToScan(entityManagerPackages);
		sessionFactory.setHibernateProperties(buildHibernateProperties());

		return sessionFactory;
	}

	/**
	 * Builds a data source to get the connection to the database based on the
	 * properties.
	 * 
	 * @return The Data Source.
	 */
	@Bean
	public DataSource buildDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(jdbcDriver);
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(dbUserName);
		dataSource.setPassword(dbPassword);
		return dataSource;
	}

	/**
	 * Builds the properties for hibernate.
	 * 
	 * @return The hibernate {@link Properties}.
	 */
	private Properties buildHibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", hibernateDialect);
		hibernateProperties.put("hibernate.show_sql", hibernateShowSql);
		hibernateProperties.put("hibernate.hbm2ddl.auto", hibernateDdl);
		return hibernateProperties;
	}
}
