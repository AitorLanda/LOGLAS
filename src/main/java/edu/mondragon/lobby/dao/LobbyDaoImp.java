package edu.mondragon.lobby.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.mondragon.lobby.model.Lobby;

@Repository
public class LobbyDaoImp implements LobbyDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	//------------ SETTERS --------------
	
	public void addLobby(Lobby lobby) {
		sessionFactory.getCurrentSession().save(lobby);
	}
	
	public void deleteLobby(Integer id) {
		Lobby lobby = sessionFactory.getCurrentSession().load(
				Lobby.class, id);
        if (null != lobby) {
            this.sessionFactory.getCurrentSession().delete(lobby);
        }
	}
	
	public void updateLobbyPlayerCount(Lobby lobby) {
		Lobby lob = getLobby(lobby.getId());
		// Use evict in order to update
		sessionFactory.getCurrentSession().evict(lob);
		lob.setPlayerCount(lobby.getPlayerCount());
		sessionFactory.getCurrentSession().update(lob);
	}
	
	public void updateLobbyStartDate(Lobby lobby) {
		Lobby lob = getLobby(lobby.getId());
		// Use evict in order to update
		sessionFactory.getCurrentSession().evict(lob);
		lob.setStartDate(lobby.getStartDate());
		sessionFactory.getCurrentSession().update(lob);
	}
	
	public void updateLobbyFinishDate(Lobby lobby) {
		Lobby lob = getLobby(lobby.getId());
		// Use evict in order to update
		sessionFactory.getCurrentSession().evict(lob);
		lob.setFinishDate(lobby.getFinishDate());
		sessionFactory.getCurrentSession().update(lob);
	}
	
	//------------ GETTERS --------------
	
	public Lobby getLobby(int id) {
		return sessionFactory.getCurrentSession().get(Lobby.class, id);
	}
	
	public List<Lobby> getLobbiesByGameId(int gameId) {
		TypedQuery<Lobby> query=sessionFactory.getCurrentSession().createQuery("from Lobby where gameId= :gameId", Lobby.class);
		query.setParameter("gameId", gameId);
		
		return query.getResultList();
	}
	
	public List<Lobby> listLobbies() {
		TypedQuery<Lobby> query=sessionFactory.getCurrentSession().createQuery("from Lobby", Lobby.class);
		
		return query.getResultList();
	}

}
