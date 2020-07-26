package edu.mondragon.playerscore.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @version v0.01
 * @brief  	PlayerScore POJO class entity
 * @date	Creation: 01/13/20
 * @author 	Loredi Altzibar
 *
 */

@Entity
@Table(name = "PLAYER_SCORE")
public class PlayerScore implements Serializable {

	/**
	 * @brief Default generated serial
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "gameId")
	private int gameId;
	
	@Column(name = "lobbyId")
	private int lobbyId;
	
	@Column(name = "userId")
	private int userId;
	
	@Column(name = "score")
	private int score;
	
	@Column(name = "won")
	private boolean won;
	
	
	public PlayerScore() {}
	
	public PlayerScore(int gameId, int lobbyId, int userId, int score, boolean won) {
		this.gameId = gameId;
		this.lobbyId = lobbyId;
		this.userId = userId;
		this.score = score;
		this.won = won;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getLobbyId() {
		return lobbyId;
	}

	public void setLobbyId(int lobbyId) {
		this.lobbyId = lobbyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}
	
}
