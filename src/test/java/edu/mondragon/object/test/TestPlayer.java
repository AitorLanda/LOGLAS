package edu.mondragon.object.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.mondragon.object.Player;

public class TestPlayer {
	
	Player player;
	
	
	@Before
	public void setUp() {
		
		String username = "Username1";
		int id = 1;
		
		player = new Player(id,username);
		
	}
	
	@Test
	public void testId() {
		player.setId(101);
		assertEquals(player.getId(),101);
	}
	
	@Test
	public void testUsername() {
		player.setUserName("User");
		assertEquals(player.getUserName(),"User");
	}
	@Test
	public void testScore() {
		player.setScore(10);
		assertEquals(player.getScore(),10);
	}
}
