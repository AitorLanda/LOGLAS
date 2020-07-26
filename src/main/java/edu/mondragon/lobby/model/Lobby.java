package edu.mondragon.lobby.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @version v0.01
 * @brief  	Lobby POJO class entity
 * @date	Creation: 12/04/19
 * @author 	Loredi Altzibar
 *
 */

@Entity
@Table(name = "LOBBY")
public class Lobby implements Serializable {

	/**
	 * @brief Default generated serial
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "userId")
	private int userId;
	
	@Column(name = "gameId")
	private int gameId;
	
	@Column(name = "playerCount")
	private int playerCount;
	
	@Column(name = "playerLimit")
	private int playerLimit;
	
	@Column(name = "createDate")
	private ZonedDateTime createDate;
	
	@Column(name = "startDate")
	private ZonedDateTime startDate;
	
	@Column(name = "finishDate")
	private ZonedDateTime finishDate;
	
	
	public Lobby() {
		this.createDate = ZonedDateTime.now();
	}
	
	public Lobby(String title, int userId, int gameId, int playerLimit) {
		this.title = title;
		this.userId = userId;
		this.gameId = gameId;
		this.playerCount = 0;
		this.playerLimit = playerLimit;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int getPlayerLimit() {
		return playerLimit;
	}

	public void setPlayerLimit(int playerLimit) {
		this.playerLimit = playerLimit;
	}

	public ZonedDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(ZonedDateTime createDate) {
		this.createDate = createDate;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	public ZonedDateTime getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(ZonedDateTime finishDate) {
		this.finishDate = finishDate;
	}

		
	
}
