package com.trader.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * The main AppConfig configuration class hits Spring on where to look for its
 * components through @ComponentScan annotation.
 * 
 * @author CHUCHI
 * 
 */
@Configuration
@EnableWebMvc // <mvc:annotation-driven />
@ComponentScan(basePackages = { PackagesToScanConstants.CONFIG_PACKAGE, PackagesToScanConstants.CONTROLLERS_PACKAGE })
public class MvcAppConfig {

	@Bean
	public UrlBasedViewResolver configureUrlBasedViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		return resolver;
	}
}
