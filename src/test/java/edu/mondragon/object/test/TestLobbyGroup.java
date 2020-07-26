package edu.mondragon.object.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.mondragon.object.LobbyGroup;
import edu.mondragon.object.Player;
import edu.mondragon.object.Question;
import edu.mondragon.user.model.User;

public class TestLobbyGroup {
	
	User user, user2;
	Player player, player2;
	LobbyGroup lg;
	
	@Before
	public void setUp() {
		String password = "123";
		String username = "Username1";
		String firstName = "User";
		String lastName = "Surname";
		String email = "examplemail@gmail.com";
		
		user = new User(password, username, firstName, lastName, email);
		user2 = new User(password, username, firstName, lastName, email);
		user.setId(1);
		user2.setId(2);
		player = new Player(user.getId(), user.getUserName());
		player2 = new Player(user2.getId(), user2.getUserName());
		lg = new LobbyGroup(user, "myTitle", 1,1,2);
	}
	
	@Test
	public void testOwnerPlayer() {
		assertEquals(lg.getOwnerPlayer().getId(),user.getId());
	}
	
	@Test
	public void testIsFull() {
		assertFalse(lg.isFull());
		lg.playerJoins(user);
		lg.playerJoins(user2);
		assertTrue(lg.isFull());
		lg.playerLeaves(user);
		Integer expect = new Integer(1);
		assertEquals(lg.getCount(),expect);
	}
	
	
	@Test
	public void testIsEmpty() {
		assertTrue(lg.isEmpty());
		lg.playerJoins(user);
		assertFalse(lg.isEmpty());
	}
	
	
	@Test
	public void testTitle() {
		lg.setTitle("titulin");
		assertEquals(lg.getTitle(),"titulin");
	}
	
	@Test
	public void testgetPlayersList() {
		lg.playerJoins(user);
		List<Player> result = lg.getPlayersList();
		assertEquals(result.get(0).getId(),user.getId());
	}
	
	@Test
	public void testQuestionList() {
		List<Question> list = lg.loadRandomQuestions(4);
		
		lg.setQuestionList(list);
		
		assertEquals(lg.getQuestionList(),list);
	}
	
	@Test
	public void testWiner() {
		lg.setWinnerId(user.getId());
		assertTrue(lg.isWinnerByUserId(user.getId()));
		assertFalse(lg.isWinnerByUserId(user2.getId()));
		Integer expect = new Integer(user.getId());
		Integer result = new Integer(lg.getWinnerId());
		assertEquals(expect, result);
	}
	@Test
	public void testget() {
		Integer expectLobbyId = new Integer(1);
		Integer expectGameId = new Integer(1);
		assertEquals(lg.getLobbyId(), expectLobbyId);
		assertEquals(lg.getGameId(), expectGameId);
	}
	
	@Test
	public void testScore() {
		lg.playerJoins(user);
		lg.updateScore(user.getId(),10);
		Player p = lg.getPlayerInfoByUserId(user.getId());
		assertEquals(p.getScore(),10);
	}
	
}
