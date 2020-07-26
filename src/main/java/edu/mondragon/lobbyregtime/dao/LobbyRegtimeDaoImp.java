package edu.mondragon.lobbyregtime.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.mondragon.lobby.model.Lobby;
import edu.mondragon.lobbyregtime.model.LobbyRegtime;

@Repository
public class LobbyRegtimeDaoImp implements LobbyRegtimeDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	//------------ SETTERS --------------
	
	public void addRegtime(LobbyRegtime lobbyRegtime) {
		sessionFactory.getCurrentSession().save(lobbyRegtime);
		
	}
	
	//------------ GETTERS --------------
	
	public LobbyRegtime getLobbyRegtime(int id) {
		return sessionFactory.getCurrentSession().get(LobbyRegtime.class, id);
	}
	
	
	public List<LobbyRegtime> getLobbiesRegtimeByLobbyId(int lobbyId){
		TypedQuery<LobbyRegtime> query=sessionFactory.getCurrentSession().createQuery("from LobbyRegtime where lobbyId= :lobbyId", LobbyRegtime.class);
		query.setParameter("lobbyId", lobbyId);
		
		return query.getResultList();
	}
	public List<LobbyRegtime> listLobbyRegtime(){
		TypedQuery<LobbyRegtime> query=sessionFactory.getCurrentSession().createQuery("from LobbyRegtime", LobbyRegtime.class);
		
		return query.getResultList();
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