package edu.mondragon.websocket;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import edu.mondragon.object.ChatMessage;
import edu.mondragon.object.Container;

/**
 * @brief	ContainerEncoder.
 * 			Websocket JSON data encoder.
 * 			Every time data is sent by @onMessage, it goes through here
 * @author 	Loredi
 *
 */
public class ContainerEncoderDecoder implements Encoder.Text<Container>, Decoder.Text<Container> {
	
	static final String ACTION = "action";
	static final String SCORE = "score";
	static final String LOBBY_ID = "lobbyId";
	static final String USER_ID = "userId";
	Container container;
	
	@Override
	public void destroy() { //Destroy the encoder
	}

	/**
	 * @brief	Since we are working with application scoped data, a.k.a "global variables", 
	 * 			first we need to retrieve all the data we have in such scope into the local variable,
	 * 			in order to use it when encoding/decoding.
	 */
	@Override
	public void init(EndpointConfig config) {
		// Retrieve application scope data
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		Container containerInit = (Container) httpSession.getServletContext().getAttribute("container");
		// Assign to class, so we can manage with it to write the info at DECODE
		this.container = containerInit;
	}

	/**
	 * @brief	Every time data is SENT, we send application scoped data (Container class)
	 * 			to them, so we need to encode it to JSON since front end cannot interpret Java.
	 */
	@Override
	public String encode(Container container) throws EncodeException {
		return new Gson().toJson(container);
	}

	/**
	 * 	@brief	Every time a data is RETRIEVED, we need to identify which ACTION
	 * 			they performed. We have a known structure for each known action. 
	 */
	@Override
	public Container decode(String jsonString) throws DecodeException {
		// Get JSON object type in order to get the key values
		JsonObject jsonObject = Json.createReader(new StringReader(jsonString)).readObject();
		String action = jsonObject.getString(ACTION);
		// Set our Conponent by handling which action was performed
		if (action.equals("chatMessage") || action.contentEquals("leaveLobby") || action.contentEquals("joinLobby")) {
			ChatMessage chatMessage = new ChatMessage(jsonObject.getString("sender"), jsonObject.getString("message"));
			this.container.setChatMessage(chatMessage);
			this.container.setAction(jsonObject.getString(ACTION));
		} else if (action.equals("updateScore")) {
			this.container.updateScore(jsonObject.getInt("userId"), jsonObject.getInt(SCORE), jsonObject.getInt(LOBBY_ID));
			this.container.setAction(jsonObject.getString(ACTION));
		} else if (action.equals("rematch")) {
			this.container.loadQuestionsForRematch(jsonObject.getInt(LOBBY_ID));
		} else if (action.equals("gameWinner")) {
			this.container.setAction("gameWinner");
			this.container.setWinnerByUserId(jsonObject.getInt(LOBBY_ID), jsonObject.getInt("userId"));
		} else if (action.equals("tetrisGameUpdate")) {
			this.container.setAction("tetrisGameUpdate");
			this.container.setUserId(jsonObject.getInt("userId"));
			this.container.setGameState(jsonObject.getJsonObject("state"));
			this.container.updateScore(jsonObject.getInt("userId"), jsonObject.getInt(SCORE), jsonObject.getInt(LOBBY_ID));
		} else if (action.equals("tetrisGameOver")) {
			this.container.setAction("tetrisGameOver");
			this.container.setUserId(jsonObject.getInt("userId"));
			this.container.setGameOver(jsonObject.getBoolean("gameOver"));
			this.container.setGameState(jsonObject.getJsonObject("state"));
			this.container.updateScore(jsonObject.getInt("userId"), jsonObject.getInt(SCORE), jsonObject.getInt(LOBBY_ID));
		}
		return this.container;
	}

	@Override
	public boolean willDecode(String argString) {
		return true;
	}
	
}
