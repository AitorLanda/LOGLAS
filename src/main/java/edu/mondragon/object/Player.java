package edu.mondragon.object;

/**
 * @brief	Player class.
 * 			This object will be created every time a user joins a lobby, so they can play a game and have their own score.
 * @author 	Loredi
 *
 */
public class Player {

	private int id;
	private String username;
	private int score;
	
	public Player(int id, String username) {
		this.id = id;
		this.username = username;
		this.score = 0;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	
}
