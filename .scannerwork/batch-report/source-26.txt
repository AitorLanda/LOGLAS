package edu.mondragon.lobby.service;

import java.util.List;

import edu.mondragon.lobby.model.Lobby;

public interface LobbyService {
	public void addLobby(Lobby lobby);
	public void deleteLobby(Integer id);
	public void updateLobbyPlayerCount(Lobby lobby);
	public void updateLobbyStartDate(Lobby lobby);
	public void updateLobbyFinishDate(Lobby lobby);
	
	
	public Lobby getLobby(int id);
	public List<Lobby> getLobbiesByGameId(int gameId);
	public List<Lobby> listLobbies();
}
