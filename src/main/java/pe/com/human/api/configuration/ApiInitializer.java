package pe.com.human.api.configuration;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApiInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	protected Class<?>[] getRootConfigClasses() {
		PropertyConfigurator.configure(getClass().getClassLoader().getResourceAsStream("config/log4j.properties"));
		return new Class[] { ApiConfiguration.class };
	}

	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
