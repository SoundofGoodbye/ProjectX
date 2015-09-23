package com.trader.application.configuration;

import java.util.Properties;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.trader.application.utils.PropertiesLoader;

public class ApplicationInitializer implements WebApplicationInitializer {

	private static final PropertiesLoader propertiesLoader = new PropertiesLoader();

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = getContext();
		context.setServletContext(servletContext);
		servletContext.addListener(new ContextLoaderListener(context));
		context.refresh();

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet",
				new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		dispatcher.setMultipartConfig(context.getBean(MultipartConfigElement.class));
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.setConfigLocation("com.trader.application.configuration");
		Properties prop = propertiesLoader.load("application.properties");
		mvcContext.getEnvironment().setActiveProfiles(prop.getProperty("spring.profiles.active", "development"));
		mvcContext.register(MvcAppConfig.class);
		return mvcContext;
	}
}
