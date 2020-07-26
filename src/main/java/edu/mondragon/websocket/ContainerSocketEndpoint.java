package edu.mondragon.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.mondragon.object.Container;
import edu.mondragon.user.model.User;

/**
 * @brief	Lobby Chat WebSocket EndPoint
 * 			Used to communicate (exchange messages) between users in a common lobby.
 * 			Uses simple String data.
 * @author 	Loredi
 *
 */

@Component
@ServerEndpoint(value = "/lobbychat/{id}",
				configurator = SocketEndpointConfig.class,
				encoders = {ContainerEncoderDecoder.class},
				decoders = {ContainerEncoderDecoder.class})
public class ContainerSocketEndpoint {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private Session session;
	private User user;
	private int lobbyId;
	private static final Set<ContainerSocketEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
	private EndpointConfig config; // To be available to fetch application scope data (Container class)
	
	/**
	 * @brief	Executed when send() is done from client side (front-end)
	 */
	@OnMessage
	public void onMessage(Container container, Session session){
        
		// Set application scoped data
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		httpSession.getServletContext().setAttribute("container", container);
		// Send
		broadcastMessage(container);
	}
		  
	/**
	 * @brief	Configurations when connection is opened. Every time a user ENTERS a page, goes through this.
	 */
	@OnOpen
	public void onOpen (Session session, @PathParam("id") Integer lobbyId, EndpointConfig config){
		
		// Retrieve user information from HTTP Session scope
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		User userOnOpen = (User) httpSession.getAttribute("user");
		this.session = session;
		this.user = userOnOpen;
		this.lobbyId = lobbyId;
		this.config = config;
		
		// Add this EndPoint to the list in order to keep track of different lobbies EndPoints 
		chatEndpoints.add(this);
		
		// Send application scoped data in order to visualize in the jsp
		Container container = (Container) httpSession.getServletContext().getAttribute("container");
		container.setAction("infoUpdate");
		broadcastContainer(container);
	}

	/**
	 * @brief	Configurations when connection is closed. Every time a user LEAVES a page, goes through this.
	 */
	@OnClose
	public void onClose (Session session, @PathParam("id") Integer lobbyId) {
		// Remove this EndPoint 
		chatEndpoints.remove(this);
	}
	
	/**
	 * @brief	Used only to notify lobby specific changes, such as communication of a chat or score-board update in a private lobby.
	 * 			Content is broadcasted to everyone in the server connected to this socket WITH SAME LOBBY ID.
	 * 			!! Triggered just when a user sends info by "send()" in front-end (@OnMessage)
	 * @param 	Container
	 */
	public void broadcastMessage(Container container){
		chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
            	if (endpoint.getLobbyId() == lobbyId) {
            		try {
            			if (container.getAction().equals("joinLobby")) {
            				// Only send "join" message to the other members, I don't need to know that I just joined... 
            				if (!endpoint.user.getUserName().equals(container.getChatMessage().getSender())) {
            					endpoint.session.getBasicRemote().sendObject(container);
            				}
            			} else {
            				endpoint.session.getBasicRemote().sendObject(container);
            			}	
                	} catch (IOException | EncodeException e) {
                		logger.error("An error occurred", e);
                    }
                }             
            }
        });
	}
	
	/**
	 * @brief	Used only to notify global changes, such as lobbies statuses (if they are 3/5 or already full 5/5...).
	 * 			Content is broadcasted to EVERYONE in the server connected to this socket.
	 * 			!! Triggered when just connecting to a new page with the socket (@OnOpen)
	 * @param 	Container
	 */
	public void broadcastContainer(Container container) {
		chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
            	try {
            		endpoint.session.getBasicRemote().sendObject(container);
                } catch (IOException | EncodeException e) {
                	logger.error("An error ocurred",e);
                }
            }
        });
	}
	
	public int getLobbyId() {
		return this.lobbyId;
	}
	
}
