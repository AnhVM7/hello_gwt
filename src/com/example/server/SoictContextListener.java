package com.example.server;

import javax.servlet.ServletContextEvent;
import com.example.shared.model.User;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.googlecode.objectify.ObjectifyService;

@WebListener
public class SoictContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		ObjectifyService.init();
		ObjectifyService.register(User.class);
		
	}

    
}
    