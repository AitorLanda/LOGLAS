package edu.mondragon.lobby.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mondragon.lobby.dao.LobbyDao;
import edu.mondragon.lobby.model.Lobby;

@Service
@Transactional
public class LobbyServiceImp implements LobbyService {

	@Autowired
	private LobbyDao lobbyDao;
	
	
	//------------ SETTERS --------------
	
	/**
	 * @brief	Inserts a lobby into the table Lobby
	 * @param 	Lobby
	 */
	@Transactional
	@Override
	public void addLobby(Lobby lobby) {
		lobbyDao.addLobby(lobby);
	}
	
	/**
	 * @brief	Deletes a lobby from the table Lobby from ID
	 * @param 	Lobby
	 */
	@Override
    @Transactional
    public void deleteLobby(Integer id) {
		lobbyDao.deleteLobby(id);
    }
	
	/**
	 * @brief	Updates a lobby from the table Lobby
	 * 			Updates player COUNT
	 * @param 	Lobby
	 */
	@Override
    @Transactional
    public void updateLobbyPlayerCount(Lobby lobby) {
		lobbyDao.updateLobbyPlayerCount(lobby);
    }
	
	/**
	 * @brief	Updates a lobby from the table Lobby
	 * 			Sets game start date
	 * @param 	Lobby
	 */
	@Override
    @Transactional
    public void updateLobbyStartDate(Lobby lobby) {
		lobbyDao.updateLobbyStartDate(lobby);
    }
	
	/**
	 * @brief	Updates a lobby from the table Lobby
	 * 			Sets game start date
	 * @param 	Lobby
	 */
	@Override
    @Transactional
    public void updateLobbyFinishDate(Lobby lobby) {
		lobbyDao.updateLobbyFinishDate(lobby);
    }
	
	
	
	//------------ GETTERS --------------
	
	/**
	 * @brief	Gets a lobby from the table as a Lobby from ID
	 * @return 	Lobby
	 */
	@Transactional(readOnly = true)
	@Override
	public Lobby getLobby(int id) {
		return lobbyDao.getLobby(id);
	}
	
	/**
	 * @brief	Gets a list of lobbies from the table as a list of Lobby from gameId
	 * @return 	Lobby list
	 */
	public List<Lobby> getLobbiesByGameId(int gameId) {
		return lobbyDao.getLobbiesByGameId(gameId);
	}
	
	/**
	 * @brief	Gets all lobbies from the table as a list
	 * @return 	List: all lobbies as a list
	 */
	@Transactional(readOnly = true)
	@Override
	public List<Lobby> listLobbies() {
		return lobbyDao.listLobbies();
	}

}
