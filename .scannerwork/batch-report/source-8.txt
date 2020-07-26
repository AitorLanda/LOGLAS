package edu.mondragon.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.annotation.Configuration;

import edu.mondragon.object.Container;

/**
 * @brief	Lobby Chat WebSocket EndPoint
 * 			Used to communicate client / application context
 * 			Uses JSON data structure
 * @author 	Loredi
 *
 */

@Configuration
public class ServletListener implements ServletContextListener {
	
		
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		Container container = new Container();
		servletContextEvent.getServletContext().setAttribute("container", container);
		
	}
	
	@Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // do things when server is shutting down
    }
	
}
