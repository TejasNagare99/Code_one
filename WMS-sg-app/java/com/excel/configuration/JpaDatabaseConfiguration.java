package com.excel.configuration;
      
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JpaDatabaseConfiguration {

		
	
	//WMS PG
	 private static final String DATABASE_URL = "jdbc:sqlserver://103.172.151.205:1433;databaseName=WMS_PG_01032023";     //DEMO
	 private static final String DATABASE_USERNAME = "sa";
	 private static final String DATABASE_PASSWORD = "	";


	private static final String DATABASE_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DATABASE_DIALECT = "org.hibernate.dialect.SQLServer2008Dialect";

	// NON-ORACLE Databasesdata
	@Bean("dataSource")
	public DriverManagerDataSource getDataSource() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DATABASE_DRIVER);
		dataSource.setUrl(DATABASE_URL);
		dataSource.setUsername(DATABASE_USERNAME);
		dataSource.setPassword(DATABASE_PASSWORD);
		return dataSource;
	}

	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() throws SQLException {
		System.out.println("Database Driver = " + DATABASE_DRIVER);
		System.out.println("Database URL = " + DATABASE_URL);

		HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
		vendor.setShowSql(true);
		vendor.setGenerateDdl(false);

		// Explicitly for Microsoft SQL Server
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", DATABASE_DIALECT);

		// Properties properties = new Properties();
		properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
		properties.setProperty("hibernate.globally_quoted_identifiers", "true");
		// setting this to false to fix the sequence generator
		// properties.setProperty("hibernate.id.new_generator_mappings", "false");

		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setDataSource(getDataSource());
		bean.setPackagesToScan("com.excel.model");
		bean.setJpaVendorAdapter(vendor);
		bean.setJpaProperties(properties);
		bean.afterPropertiesSet();
		System.out.println("EntityManagerFactory = " + bean.getObject().toString());
		return bean;
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager getTransactionManager() throws SQLException {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(getEntityManagerFactory().getObject());
		manager.setDataSource(getDataSource());
		return manager;
	}

}
