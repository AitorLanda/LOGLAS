package edu.mondragon.lobbyregtime.service;

import java.util.List;

import edu.mondragon.lobbyregtime.model.LobbyRegtime;

public interface LobbyRegtimeService {

	public void addRegtime(LobbyRegtime lobbyRegtime);
	
	public LobbyRegtime getLobbyRegtime(int id);
	public List<LobbyRegtime> getLobbiesRegtimeByLobyId(int lobbyId);
	public List<LobbyRegtime> listLobbiesRegtime();
}
