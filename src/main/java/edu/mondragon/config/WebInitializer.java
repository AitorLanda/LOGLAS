package edu.mondragon.config;

import javax.servlet.ServletContext;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import edu.mondragon.websocket.SocketEndpointConfig;

/**
 * @version v0.01
 * @brief  	Spring MVC Initializer. 
 * 			For pages directory page management. (DEFAULT)
 * 			HERE IS THE CONTEXT INITIALIZATION
 * 			(check on MUdle: Spring MVC Hello World example)
 * @see		SpringController and MyWebConfig
 * @date	Creation: 12/02/19
 * @author 	Loredi Altzibar
 *
 */

@Configuration
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { HibernateConfig.class, SecurityConfig.class, SocketEndpointConfig.class };
	}
	
	@Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);
        servletContext.addListener(new ServletListener());
    }

}