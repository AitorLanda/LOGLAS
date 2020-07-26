package edu.mondragon.lobbyregtime.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mondragon.lobbyregtime.dao.LobbyRegtimeDao;
import edu.mondragon.lobbyregtime.model.LobbyRegtime;

@Service
@Transactional
public class LobbyRegtimeServiceImp implements LobbyRegtimeService {

	@Autowired
	private LobbyRegtimeDao lobbyRegtimeDao;
	
	//------------ SETTERS --------------
	
		/**
		 * @brief	Inserts a lobby regtime into the table lobby_regtime
		 * @param 	LobbyRegtime
		 */
	@Transactional
	@Override
	public void addRegtime(LobbyRegtime lobbyRegtime) {
		lobbyRegtimeDao.addRegtime(lobbyRegtime);
		
	}

	/**
	 * @brief	Gets a lobby from the table as a Lobby from ID
	 * @return 	Lobby
	 */
	@Transactional(readOnly = true)
	@Override
	public LobbyRegtime getLobbyRegtime(int id) {
		return lobbyRegtimeDao.getLobbyRegtime(id);
	}
	
	/**
	 * @brief	Gets a list of lobbies regtime from the table as a list of Lobby from lobbyId
	 * @return 	Lobby list
	 */
	@Override
	public List<LobbyRegtime> getLobbiesRegtimeByLobyId(int lobbyId) {
		return lobbyRegtimeDao.getLobbiesRegtimeByLobbyId(lobbyId);
	}
	
	/**
	 * @brief	Gets all lobbies from the table as a list
	 * @return 	List: all lobbies as a list
	 */
	@Transactional(readOnly = true)
	@Override
	public List<LobbyRegtime> listLobbiesRegtime() {
		return lobbyRegtimeDao.listLobbyRegtime();
	}



}
