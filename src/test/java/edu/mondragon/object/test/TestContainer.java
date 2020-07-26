package edu.mondragon.object.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.mondragon.object.ChatMessage;
import edu.mondragon.object.Container;
import edu.mondragon.object.LobbyGroup;
import edu.mondragon.user.model.User;

public class TestContainer {
	
	Container container;
	User user;
	User user2;
	final static String TITLE = "myTitle";
	
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
		container = new Container();
	}
	
	@Test
	public void testCreateLobbyGroup() {
		assertTrue(container.createNewLobbyGroup(user, TITLE, 1,1,2) instanceof LobbyGroup);
		
	}
	
	@Test
	public void testGetLobbyGroupByLobbyId() {
		LobbyGroup lg = container.createNewLobbyGroup(user, TITLE, 3,3,2);
		
		
		assertEquals(lg,container.getLobbyGroupByLobbyId(3));
	}
	
	@Test
	public void testGetLobbyGroupsByGameId() {
		LobbyGroup lga = container.createNewLobbyGroup(user, TITLE, 1,2,2);
		LobbyGroup lg = container.createNewLobbyGroup(user, TITLE, 3,2,2);
		container.createNewLobbyGroup(user, TITLE, 1,1,2);
		assertEquals(container.getLobbyGroupsByGameId(2).contains(lga), true);
		assertEquals(container.getLobbyGroupsByGameId(2).contains(lg), true);
		int gCount = container.getLobbyGroupsCount();
		assertEquals(gCount,3);
	}
	
	@Test
	public void testGetGameIdByLobbyId() {
		container.createNewLobbyGroup(user, TITLE, 3,2,2);
		int num = container.getGameIdByLobbyId(3);
		assertEquals(num,2);
	}
	
	/*@Test
	public void testUpdateScore() {
		LobbyGroup lg = container.createNewLobbyGroup(user, TITLE, 3,2,2);
		
		
		container.updateScore(1, 14, 3);
		assertEquals(lg.getPlayerInfoByUser(user).getScore(),14);
	}*/
	
	@Test
	public void testPlayerJoins() {
		LobbyGroup lg = container.createNewLobbyGroup(user, TITLE, 3,2,2);
		
		container.playerJoins(user, 3);
		
		assertEquals(lg.getPlayersList().get(0).getId(), user.getId());
	}
	
	@Test
	public void testPlayerLeaves() {
		LobbyGroup lg = container.createNewLobbyGroup(user, TITLE, 3,2,2);
		
		container.playerJoins(user2, 3);
		
		container.playerLeaves(user,3);
		container.playerLeaves(user2,3);
		assertEquals(lg.getPlayersList().size(), 0);
	}
	
	@Test
	public void testDeleteLobbyGroupByLobbyId(){
		container.createNewLobbyGroup(user, TITLE, 3,2,2);
		
		container.deleteLobbyGroupByLobbyId(3);
		
		
		assertEquals(container.getLobbyGroupsList().size(),0);
	}
	
	@Test
	public void testAction() {
		container.setAction("myAction");
		assertEquals(container.getAction(),"myAction");
	}
	
	@Test
	public void testChatMessage() {
		container.setChatMessage(new ChatMessage("me","message"));
		assertEquals(container.getChatMessage().getMessage(),"message");
	}
}
