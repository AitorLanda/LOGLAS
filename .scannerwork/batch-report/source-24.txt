package edu.mondragon.lobbyregtime.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.mondragon.lobby.model.Lobby;
import edu.mondragon.lobbyregtime.model.LobbyRegtime;

@Repository
public interface LobbyRegtimeDao {
	public void addRegtime(LobbyRegtime lobby);
	
	public LobbyRegtime getLobbyRegtime(int id);
	
	public List<LobbyRegtime> getLobbiesRegtimeByLobbyId(int gameId);
	public List<LobbyRegtime> listLobbyRegtime();
	
	public List<Lobby> getLobbiesByGameId(int gameId);
	public List<Lobby> listLobbies();
	
}
