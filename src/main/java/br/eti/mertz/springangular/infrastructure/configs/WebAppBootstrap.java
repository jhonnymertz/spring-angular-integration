package br.eti.mertz.springangular.infrastructure.configs;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppBootstrap implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container)
			throws ServletException {
		// Spring application context
		AnnotationConfigWebApplicationContext context;
		context = new AnnotationConfigWebApplicationContext();
		context.scan("br.eti.mertz.springangular.infrastructure.configs");
		// Manage lifecycle application context
		container.addListener(new ContextLoaderListener(context));
		// Spring MVC
		ServletRegistration.Dynamic dispatcher = container
				.addServlet("dispatcher", new DispatcherServlet(context));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
	
}
