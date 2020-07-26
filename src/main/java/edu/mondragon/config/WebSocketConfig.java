package edu.mondragon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @brief	This is a server end-point exporter.
 * 			We need this to make web-socket work with 
 * @author 	Loredi
 *
 */
@Configuration
public class WebSocketConfig {

	@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
	
}
