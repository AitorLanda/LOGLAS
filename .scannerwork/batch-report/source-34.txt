package edu.mondragon.object;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import edu.mondragon.user.model.User;

/**
 * @brief	ServerContainer class.
 * 			This class identifies all objects to be managed in memory.
 * 			action: used to identify user actions, such as sending a message to chat
 * 			
 * 			Acts as a service for LobbyGroup class
 * 			
 * @author 	Loredi
 *
 */
public class Container {

	private List<Game> gamesList;
	private List<LobbyGroup> lobbyGroupsList;
	private ChatMessage chatMessage;
	private String action;
	
	// Tetris game handle
	private int userId;
	private boolean gameOver;
	private JsonObject gameState;
	
	
	public Container() {
		gamesList = loadGames();
		lobbyGroupsList = new ArrayList<>();
		gameOver = false;
	}

	
	private List<Game> loadGames() {
		Game gameTF = new Game(1, "True or False", "Answer true or false questions. Highest score player wins.",
				"true-false.jpg", "true-false-img");
		Game gameQA = new Game(2, "Multiple choice", "Answer questions by selecting the appropriate answer "
				+ "from the possible answers. Highest score player wins.","question-answer.jpg", "multiple-choice-img");
		Game gameMix = new Game(3, "Mix", "A mixed game with true/false and multiple choice questions. "
				+ "Highest score player wins.", "mix.jpg", "mix-img");
		Game gameSort = new Game(4, "Sort the answers", "Answer questions by sorting the appropriate answer "
				+ "from the possible answers. Highest score player wins.","drag.gif", "sort-img");
		Game gameTetris = new Game(5, "Tetris", "The classical tetris game, multiplayer.", "tetris.jpg", "tetris-img");

		List<Game> list = new ArrayList<>();
		list.add(gameTF);
		list.add(gameQA);
		list.add(gameMix);
		list.add(gameSort);
		list.add(gameTetris);		
		return list;
	}


	/**
	 * @brief	Creates a new LobbyGroup object 
	 * 			1. Create new LobbyGroup object
	 * 			2. Add to lobbyGroup list
	 * @param 	Lobby
	 * @param 	player
	 * @return	LobbyGroup
	 */
	public LobbyGroup createNewLobbyGroup(User user, String title, Integer lobbyId, Integer gameId, Integer playerLimit) {
		LobbyGroup lobbyGroup = new LobbyGroup(user, title, lobbyId, gameId, playerLimit);
		lobbyGroupsList.add(lobbyGroup);
		return lobbyGroup;
	}
	
	/**
	 * @brief	Deletes the lobbyGroup by lobby ID
	 * 			1. Iterate through all lobbyGroups
	 * 			2. Find the needed lobbyGroup by lobby ID
	 * 			2.1. Remove the lobbyGroup
	 * @param 	lobbyId
	 */
	public void deleteLobbyGroupByLobbyId(int lobbyId) {
		for (int i = 0; i < lobbyGroupsList.size(); i++) {
			if (lobbyGroupsList.get(i).getLobbyId() == lobbyId) {
				lobbyGroupsList.remove(i);
			}
		}
	}
	
	/**
	 * @brief	Adds the player to an existing lobby by lobby ID
	 * 			1. Iterate through all lobbyGroups
	 * 			2. Find the needed lobbyGroup by lobby ID
	 * 			2.1. Add current player to lobbyGroup list
	 * @param 	player
	 * @param 	lobbyId
	 */
	public void playerJoins(User user, int lobbyId) {
		for (LobbyGroup lg : lobbyGroupsList) {
			if (lg.getLobbyId() == lobbyId) {
				lg.playerJoins(user);
			}
		}
	}
	
	/**
	 * @brief	Deletes the player from an existing lobby by lobby ID.
	 * 			1. Iterate through all lobbyGroups
	 * 			2. Find the needed lobbyGroup by lobby ID
	 * 			3. Remove current player from lobbyGroup list
	 * 			4. Check if lobbyGroup is empty
	 * 			4.1. Remove the lobbyGroup from list
	 * @param 	player
	 * @param 	lobbyId
	 */
	public void playerLeaves(User user, int lobbyId) {
		for (int i = 0; i < lobbyGroupsList.size(); i++) {
			if (lobbyGroupsList.get(i).getLobbyId() == lobbyId) {
				lobbyGroupsList.get(i).playerLeaves(user);
				// If player was the last one exiting lobby = delete lobby from list
				if (lobbyGroupsList.get(i).isEmpty()) {
					lobbyGroupsList.remove(i);
				}
			}
		}
	}
	
	/**
	 * @brief	Updates the player's score
	 * 			1. Iterate through all lobbyGroups
	 * 			2. Find the needed lobbyGroup by lobby ID
	 * 			3. Call lobbyGroup's updateScore by user ID
	 * @param 	userId
	 * @param 	score
	 * @param	lobbyId
	 */
	public void updateScore(int userId, int score, int lobbyId) {
		for (int i = 0; i < lobbyGroupsList.size(); i++) {
			if (lobbyGroupsList.get(i).getLobbyId() == lobbyId) {
				lobbyGroupsList.get(i).updateScore(userId, score);
			}
		}
	}
	
	/**
	 * @brief	Sets winner by user ID
	 * 			1. Get the lobby group which user belongs to
	 * 			2. Call setWinner
	 * @param 	user ID
	 */
	public void setWinnerByUserId(int lobbyId, int userId) {
		LobbyGroup lobbyGroup = getLobbyGroupByLobbyId(lobbyId);
		lobbyGroup.setWinnerId(userId);
	}
	
	/**
	 * @brief	Checks if user is the winner using user ID
	 * 			1. Get the lobby group which user belongs to
	 * 			2. Call isWinner to check if this user us the winner
	 * @param 	user ID
	 */
	public boolean isWinnerByUserId(int lobbyId, int userId) {
		LobbyGroup lobbyGroup = getLobbyGroupByLobbyId(lobbyId);
		return lobbyGroup.isWinnerByUserId(userId);
	}
	
	/**
	 * @brief	Loads 3 new questions for rematch games
	 * 			1. Iterate through all lobbyGroups
	 * 			2. Find the needed lobbyGroup by lobby ID
	 * 			3. Call load questions
	 * @param 	userId
	 * @param 	score
	 * @param	lobbyId
	 */
	public void loadQuestionsForRematch(int lobbyId) {
		for (int i = 0; i < lobbyGroupsList.size(); i++) {
			if (lobbyGroupsList.get(i).getLobbyId() == lobbyId) {
				List<Question> questionListRematch = lobbyGroupsList.get(i).loadRandomQuestions(3);
				lobbyGroupsList.get(i).setQuestionList(questionListRematch);
			}
		}
	}
	
	/**
	 * @brief	Gets game ID by lobby ID.
	 * 			1. Iterate through all lobbyGroups
	 * 			2. Find the needed lobbyGroup by lobby ID
	 * 			3. Return game ID
	 * 			3.1 Return 0
	 * @param 	lobbyId
	 * @return	gameId
	 */
	public Integer getGameIdByLobbyId(int lobbyId) {
		Integer id = 0;
		for (int i = 0; i < lobbyGroupsList.size(); i++) {
			if (lobbyGroupsList.get(i).getLobbyId() == lobbyId) {
				id = lobbyGroupsList.get(i).getGameId();
			}
		}
		return id;
	}
	
	/**
	 * @brief	Gets the lobbyGroup by lobby ID
	 * 			1. Iterate through all lobbyGroups
	 * 			2. Find the needed lobbyGroup by lobby ID
	 * @param 	lobbyId
	 * @return	lobbyGroup
	 */
	public LobbyGroup getLobbyGroupByLobbyId(int lobbyId) {
		LobbyGroup lobbyGroup = null;
		for (int i = 0; i < lobbyGroupsList.size(); i++) {
			if (lobbyGroupsList.get(i).getLobbyId() == lobbyId) {
				lobbyGroup = lobbyGroupsList.get(i);
			}
		}
		return lobbyGroup;
	}
	
	/**
	 * @brief	Get all lobbies from a game by game ID
	 * 			1. Iterate through all lobbyGroups
	 * 			2. Find the needed lobby by game ID
	 * 			2.1. Add to the list
	 * @param 	gameId
	 * @return	lobbyGroups list
	 */
	public List<LobbyGroup> getLobbyGroupsByGameId(int gameId) {
		List<LobbyGroup> lobbyGroupList = new ArrayList<>();
		for (int i = 0; i < lobbyGroupsList.size(); i++) {
			if (lobbyGroupsList.get(i).getGameId() == gameId) {
				lobbyGroupList.add(lobbyGroupsList.get(i));
			}
		}
		return lobbyGroupList;
	}
	
	
	/**Setters - getters**/
	
	public Integer getLobbyGroupsCount() {
		return lobbyGroupsList.size();
	}
	
	public List<Game> getGamesList() {
		return gamesList;
	}


	public void setGamesList(List<Game> gamesList) {
		this.gamesList = gamesList;
	}


	public void setLobbyGroupsList(List<LobbyGroup> lobbyGroupsList) {
		this.lobbyGroupsList = lobbyGroupsList;
	}
	
	public List<LobbyGroup> getLobbyGroupsList() {
		return lobbyGroupsList;
	}


	public ChatMessage getChatMessage() {
		return chatMessage;
	}


	public void setChatMessage(ChatMessage message) {
		this.chatMessage = message;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public JsonObject getGameState() {
		return gameState;
	}


	public void setGameState(JsonObject gameState) {
		this.gameState = gameState;
	}


	public boolean getGameOver() {
		return gameOver;
	}


	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	
	
}
