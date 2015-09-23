package com.trader.application.configuration;

import java.util.Properties;

import javax.servlet.MultipartConfigElement;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.annotation.JsonInclude;

@Configuration
@ComponentScan(basePackages = { PackagesToScanConstants.REPO_PACKAGE, PackagesToScanConstants.SERVICES_PACKAGE })
@PropertySource(value = { "classpath:application.properties" })
@EnableTransactionManagement
public class GlobalConfiguration {

	@Autowired
	public Environment environment;

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		return transactionManager;
	}

	@Bean(destroyMethod = "close")
	@Qualifier(value = "dataSource")
	public DataSource dataSource() {
		DataSource dataSource = new DataSource();
		dataSource.setUsername(environment.getRequiredProperty("db.username"));
		dataSource.setPassword(environment.getRequiredProperty("db.password"));
		dataSource.setUrl(environment.getRequiredProperty("db.url"));
		dataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));

		return dataSource;
	}

	@Bean
	@Autowired
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource,
			@Qualifier(value = "jpaVendorAdapter") HibernateJpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setPackagesToScan("com.trader.application.core.models.entities");

		Properties jpaProperties = new Properties();
		// Configures the used database dialect. This allows
		// Hibernate to create SQL that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));

		// Specifies the action that is invoked to the database when the
		// Hibernate SessionFactory is created or closed.
		jpaProperties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));

		// Configures the naming strategy that is used when Hibernate creates
		// new database objects and schema elements
		jpaProperties.put("hibernate.ejb.naming_strategy",
				environment.getRequiredProperty("hibernate.ejb.naming_strategy"));

		// If the value of this property is true, Hibernate writes all SQL
		// statements to the console.
		jpaProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

		// If the value of this property is true, Hibernate will format the SQL
		// that is written to the console.
		jpaProperties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));

		jpaProperties.put("hibernate.connection.url", environment.getRequiredProperty("hibernate.connection.url"));

		jpaProperties.put("hibernate.connection.username",
				environment.getRequiredProperty("hibernate.connection.username"));

		jpaProperties.put("hibernate.connection.password",
				environment.getRequiredProperty("hibernate.connection.password"));

		jpaProperties.put("hibernate.connection.driverClassName",
				environment.getRequiredProperty("hibernate.connection.driverClassName"));

		entityManagerFactory.setJpaProperties(jpaProperties);

		return entityManagerFactory;
	}

	@Bean
	@Qualifier(value = "jpaVendorAdapter")
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);

		return converter;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
		multipartConfigFactory.setMaxFileSize("10MB");
		multipartConfigFactory.setMaxRequestSize("50MB");
		return multipartConfigFactory.createMultipartConfig();
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		commonsMultipartResolver.setMaxUploadSize(50000000);
		return commonsMultipartResolver;
	}

}
