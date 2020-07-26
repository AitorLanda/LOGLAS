package edu.mondragon.lobby.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.mondragon.lobby.model.Lobby;

@Repository
public interface LobbyDao {
	public void addLobby(Lobby lobby);
	public void deleteLobby(Integer id);
	public void updateLobbyPlayerCount(Lobby lobby);
	public void updateLobbyStartDate(Lobby lobby);
	public void updateLobbyFinishDate(Lobby lobby);
	
	
	public Lobby getLobby(int id);
	public List<Lobby> getLobbiesByGameId(int gameId);
	public List<Lobby> listLobbies();
}
