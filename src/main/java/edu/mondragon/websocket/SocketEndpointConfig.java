package edu.mondragon.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @brief	WebSocket Endpoint Configurator.
 * 			Use this to get the HTTP session attributes available in WebSocket Endpoints
 * @author 	Loredi
 *
 */

public class SocketEndpointConfig extends ServerEndpointConfig.Configurator {

	/**
	 * 	@brief	Configuration for web-socket
	 */
	@Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }
	
}
