package org.faf.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.faf.controller.CrudController;

public class InitializeApplicationListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent event) {
		CrudController controller = new CrudController();
		controller.initialize();
		System.out.println("Application initialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}
	
}
