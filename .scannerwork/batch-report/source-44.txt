package edu.mondragon.playerscore.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.mondragon.playerscore.model.PlayerScore;

@Repository
public interface PlayerScoreDao {

	public PlayerScore getPlayerScore(int id);
	public void addPlayerScore(PlayerScore playerScore);
	
	public void updatePlayerScore(PlayerScore playerScore);
	
	public List<PlayerScore> listPlayerScoresByUserId(int userId);
	public List<PlayerScore> listPlayerScores();
}
