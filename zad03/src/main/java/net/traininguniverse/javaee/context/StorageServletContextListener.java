package net.traininguniverse.javaee.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.traininguniverse.javaee.service.StorageService;

@WebListener
public class StorageServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		ServletContext servletContext = servletContextEvent.getServletContext();
		// JSP bean musi mieć unikalną wartość w application context i session 
		servletContext.setAttribute("storageC", new StorageService());
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}