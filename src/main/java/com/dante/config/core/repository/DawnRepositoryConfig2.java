package com.dante.config.core.repository;

import java.beans.PropertyVetoException;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dante.constants.ConfigConstants;
import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ComponentScan({ "com.dante.*" })
@PropertySource("classpath:/com/dante/config/core/config.properties")
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.dante.db.dawn2.repository", 
		entityManagerFactoryRef = "dawnEntityManagerFactory2", 
		transactionManagerRef = "dawnTransactionManager2"
)
public class DawnRepositoryConfig2 {

	private static final String HIBERNATE_MY_SQL5_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

	private static final String DAWN_SCAN_PACKAGE = "com.dante.db.dawn2";

	@Autowired
	Environment environment;

	@Bean
	PlatformTransactionManager dawnTransactionManager2() {
		return new JpaTransactionManager(dawnEntityManagerFactory2().getObject());
	}

	@Bean
	LocalContainerEntityManagerFactoryBean dawnEntityManagerFactory2() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setShowSql(false);
		vendorAdapter.setDatabasePlatform(HIBERNATE_MY_SQL5_DIALECT);

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dawnDataSource2());
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setPackagesToScan(DAWN_SCAN_PACKAGE);

		return factoryBean;
	}

	@Bean
	DataSource dawnDataSource2() {

		ComboPooledDataSource dawnDataSource = new ComboPooledDataSource();
		String driverClass = environment.getProperty(ConfigConstants.DAWN_DRIVER_CLASS_NAME);
		try {
			dawnDataSource.setDriverClass(driverClass);
		} catch (PropertyVetoException e) {
			throw new BeanCreationException(String.format("Failed to set Drive Class of DataSource to '%s'", driverClass), e);
		}
		dawnDataSource.setJdbcUrl(environment.getProperty(ConfigConstants.DAWN_DB_URL_2));
		dawnDataSource.setUser(environment.getProperty(ConfigConstants.DAWN_DB_USER_2));
		dawnDataSource.setPassword(environment.getProperty(ConfigConstants.DAWN_DB_PASS_2));

		dawnDataSource.setAcquireIncrement(2);
		dawnDataSource.setMinPoolSize(2);
		dawnDataSource.setMaxPoolSize(30);
		dawnDataSource.setMaxIdleTime(5000);
		dawnDataSource.setMaxStatements(100);
		dawnDataSource.setIdleConnectionTestPeriod(300);

		Set<?> set = C3P0Registry.getPooledDataSources();
		int sz = set.size();
		if (sz == 1) {
			System.out.println("My pool: " + set.iterator().next());
		}

		return dawnDataSource;
	}
}
