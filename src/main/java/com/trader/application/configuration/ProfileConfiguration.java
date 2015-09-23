package com.trader.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class ProfileConfiguration {

	@Bean
	@Profile("development")
	public static PropertySourcesPlaceholderConfigurer developmentPropertyPlaceholderConfigurer() {
		String propertiesFilename = "application.properties";

		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new ClassPathResource(propertiesFilename));

		return configurer;
	}

	@Configuration
	@Profile("production")
	static class ProductionProfile {

	}
}
