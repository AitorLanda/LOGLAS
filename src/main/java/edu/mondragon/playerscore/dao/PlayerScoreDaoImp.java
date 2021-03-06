package edu.mondragon.playerscore.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.mondragon.playerscore.model.PlayerScore;

@Repository
public class PlayerScoreDaoImp implements PlayerScoreDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public PlayerScore getPlayerScore(int id) {
		return sessionFactory.getCurrentSession().get(PlayerScore.class, id);
	}
	
	public void addPlayerScore(PlayerScore playerScore) {
		sessionFactory.getCurrentSession().save(playerScore);
	}
	
	
	
	public void updatePlayerScore(PlayerScore playerScore) {
		PlayerScore ps = getPlayerScore(playerScore.getId());
		// Use evict in order to update
		sessionFactory.getCurrentSession().evict(ps);
		ps.setScore(playerScore.getScore());
		sessionFactory.getCurrentSession().update(ps);
	}
	
	
	
	public List<PlayerScore> listPlayerScoresByUserId(int userId) {
		TypedQuery<PlayerScore> query=sessionFactory.getCurrentSession().createQuery("from PlayerScore where userId= :userId", 
				PlayerScore.class);
		query.setParameter("userId", userId);
		return query.getResultList();
	}
	
	public List<PlayerScore> listPlayerScores() {
		TypedQuery<PlayerScore> query=sessionFactory.getCurrentSession().createQuery("from PlayerScore", PlayerScore.class);
		
		return query.getResultList();
	}
	
}
