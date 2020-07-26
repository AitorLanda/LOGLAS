package edu.mondragon.playerscore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mondragon.playerscore.dao.PlayerScoreDao;
import edu.mondragon.playerscore.model.PlayerScore;

@Service
@Transactional
public class PlayerScoreServiceImp implements PlayerScoreService {

	@Autowired
	private PlayerScoreDao playerScoreDao;
	
	
	//------------ SETTERS --------------
	
	/**
	 * @brief	Creates player score
	 * 			If there is already in database (getting user id),
	 * 			then UPDATE
	 * @param 	PlayerScore
	 */
	@Transactional
	@Override
	public void savePlayerScore(PlayerScore playerScore) {
		try {
			playerScoreDao.updatePlayerScore(playerScore);
		} catch (Exception e) {
			playerScoreDao.addPlayerScore(playerScore);
		}
	}
	
	
	
	//------------ GETTERS --------------
	
	/**
	 * @brief	Gets a lobby from the table as a Lobby from ID
	 * @return 	Lobby
	 */
	@Transactional(readOnly = true)
	@Override
	public PlayerScore getPlayerScore(int id) {
		return playerScoreDao.getPlayerScore(id);
	}
	
	

	/**
	 * @brief	Gets all playerScores from the table as a list by USER ID
	 * @return 	List: all lobbies as a list
	 */
	public List<PlayerScore> listPlayerScoresByUserId(int userId) {
		return playerScoreDao.listPlayerScoresByUserId(userId);
	}
	
	/**
	 * @brief	Gets all lobbies from the table as a list
	 * @return 	List: all lobbies as a list
	 */
	@Transactional(readOnly = true)
	@Override
	public List<PlayerScore> listPlayerScores() {
		return playerScoreDao.listPlayerScores();
	}
	
}
