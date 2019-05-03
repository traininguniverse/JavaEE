package net.traininguniverse.javaee.session;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.traininguniverse.javaee.service.StorageService;

@WebListener
public class BasketRequestSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {

		HttpSession session = httpSessionEvent.getSession();
		// JSP bean musi mieć unikalną wartość w application context i session 
		session.setAttribute("storageS", new StorageService());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
	}
}
