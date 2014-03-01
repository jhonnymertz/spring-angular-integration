package br.eti.mertz.springangular.infrastructure.configs;

import java.beans.PropertyVetoException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"br.eti.mertz.springangular.application.repositories.jpa"})
@Import(WebConfig.class)
public class JpaConfiguration implements TransactionManagementConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(JpaConfiguration.class);
	
	@Value("${config.db.jdbcUrl}")
	private String jdbcUrl;
	
	@Value("${config.db.username}")
	private String username;
	
	@Value("${config.db.password}")
	private String password;
	
	@Value("${config.db.driverClass}")
	private String jdbcDriver;
	
	@Value("${config.db.pool.minPoolSize}")
	private Integer minPoolSize;
	
	@Value("${config.db.pool.maxPoolSize}")
	private Integer maxPoolSize;
	
	@Value("${config.db.pool.acquireIncrement}")
	private Integer acquireIncrement;
	
	@Value("${config.db.pool.idleConnectionTestPeriod}")
	private Integer idleConnectionTestPeriod;
	
	@Value("${config.db.pool.maxStatements}")
	private Integer maxStatements;
	

	@Bean
	public DataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(jdbcDriver);
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		dataSource.setUser(username);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setAcquireIncrement(acquireIncrement);
		dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
		dataSource.setMaxStatements(maxStatements);
		return dataSource;
	}
	
	@Value("${config.db.dialect}")
	private String dialect;
	
	@Value("${config.db.hbm2ddl}")
	private String hbm2ddl;
	
	@Value("${config.db.showSql}")
	private Boolean showSql;
	
	@Value("${config.db.formatSql}")
	private Boolean formatSql;
	
	@Value("${config.db.packagesToScan}")
	private String packagesToScan;
	
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		Map<String, Object> jpaProperties = new HashMap<String, Object>();
		jpaProperties.put("hibernate.dialect", dialect);
		jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		jpaProperties.put("hibernate.format_sql", formatSql);

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(showSql);

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean; 
		entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceUnitName("default");
		entityManagerFactoryBean.setJpaPropertyMap(jpaProperties);
		entityManagerFactoryBean.setPackagesToScan(packagesToScan);
		entityManagerFactoryBean.setJpaVendorAdapter(adapter);

		return entityManagerFactoryBean;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = null;
		transactionManager = new JpaTransactionManager(
				entityManagerFactory().getObject());
		return transactionManager;
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager();
	}
	
	@PreDestroy
	public void shutdown() throws Exception {
		logger.debug("JpaConfiguration shutdown...");
		Driver driver = DriverManager.getDriver(jdbcUrl);
		DriverManager.deregisterDriver(driver);
	}
	
}
