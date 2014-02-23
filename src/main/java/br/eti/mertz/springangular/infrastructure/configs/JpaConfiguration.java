package br.eti.mertz.springangular.infrastructure.configs;

import java.beans.PropertyVetoException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
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
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Classe para configuração dos beans de ORM do Spring e Hibernate JPA
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"br.eti.mertz.springangular.application.repositories.jpa"})
@Import(WebConfig.class)
public class JpaConfiguration implements TransactionManagementConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(JpaConfiguration.class);
	/**
	 * Classe que contém os parâmetros de configuração do ambiente atual,
	 * definidos em arquivos properties
	 */
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
	
	/**
	 * Cria um {@link DataSource} do framework C3P0, obtém os parâmetros do
	 * {@link #environment}
	 * 
	 * @see DataSource
	 * @see ComboPooledDataSource
	 * @return Objeto {@link DataSource} configurado
	 * @throws PropertyVetoException
	 *             Caso um atributo seja alterado para um valor não aceito
	 */
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
	
	/**
	 * Cria e configura o bean de {@link EntityManager}, a partir do objeto
	 * {@link LocalContainerEntityManagerFactoryBean}, obtém os parâmetros do
	 * {@link #environment}, inicia o {@link HibernateJpaVendorAdapter}, define
	 * o {@link #dataSource()}, o pacote de entidades e retorna o bean.
	 * 
	 * @see LocalContainerEntityManagerFactoryBean
	 * @see JpaVendorAdapter
	 * @see HibernateJpaVendorAdapter
	 * @return Objeto {@link LocalContainerEntityManagerFactoryBean} instanciado
	 * @throws PropertyVetoException
	 *             Caso um atributo seja alterado para um valor não aceito
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		Map<String, Object> jpaProperties = new HashMap<String, Object>();
		jpaProperties.put("hibernate.dialect", dialect);
		jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		jpaProperties.put("hibernate.format_sql", formatSql);
		
		/*jpaProperties.put("hibernate.cache.use_second_level_cache",
				environment.getProperty("config.db.cache.secondLevel"));
		jpaProperties.put("hibernate.cache.use_query_cache",
				environment.getProperty("config.db.cache.query"));
		jpaProperties.put("hibernate.cache.region.factory_class",
				environment.getProperty("config.db.cache.factory"));*/

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
	/**
	 * Cria o {@link PersistenceExceptionTranslationPostProcessor}, responsável
	 * por obter exceções de persistência e repassá-las para repositórios
	 * 
	 * @see PersistenceExceptionTranslationPostProcessor
	 * @return {@link PersistenceExceptionTranslationPostProcessor}
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	/**
	 * Inicia o gerenciador de transações do banco de dados para o
	 * {@link #entityManagerFactory()}
	 * 
	 * @see PlatformTransactionManager
	 * @see JpaTransactionManager
	 * @return Objeto que gerenciará as transações
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = null;
		transactionManager = new JpaTransactionManager(
				entityManagerFactory().getObject());
		return transactionManager;
	}
	/**
	 * Retorna o {@link PlatformTransactionManager} criado anteriormente
	 * {@link #transactionManager()}, e define ele como o gerenciador de
	 * transações para as anotações
	 * 
	 * @see #transactionManager()
	 * @return Objeto que gerenciará as transações
	 */
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
