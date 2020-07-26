package edu.mondragon.lobbyregtime.model;

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
 * @brief  	LobbyRegtime class entity
 * @date	Creation: 15/01/20
 * @author 	Nahia Li Gómara
 *
 */

@Entity
@Table(name = "LOBBY_REGTIME")

public class LobbyRegtime implements Serializable {
	/**
	 * @brief Default generated serial
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "lobbyId")
	private int lobbyId;
	
	@Column(name = "playerCount")
	private int playerCount;
	
	@Column(name = "regtime")
	private ZonedDateTime regtime;
	
	
	public LobbyRegtime(int lobbyId, int playerCount) {
		this.lobbyId = lobbyId;
		this.playerCount = playerCount;
		regtime = ZonedDateTime.now();
		
	}

	public int getId() {
		return id;
	}

	public int getLobbyId() {
		return lobbyId;
	}

	public void setLobbyId(int lobbyId) {
		this.lobbyId = lobbyId;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public ZonedDateTime getRegtime() {
		return regtime;
	}

	public void setRegtime() {
		this.regtime = ZonedDateTime.now();
	}

	public void setId(int id) {
		this.id = id;
	}

	
}

