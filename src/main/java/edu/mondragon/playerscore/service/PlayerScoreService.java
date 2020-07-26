package edu.mondragon.playerscore.service;

import java.util.List;

import edu.mondragon.playerscore.model.PlayerScore;

public interface PlayerScoreService {
	
	public PlayerScore getPlayerScore(int id);
	
	public void savePlayerScore(PlayerScore playerScore);

	public List<PlayerScore> listPlayerScoresByUserId(int userId);
	public List<PlayerScore> listPlayerScores();
}
