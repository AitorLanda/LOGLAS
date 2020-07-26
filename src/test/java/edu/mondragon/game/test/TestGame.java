package edu.mondragon.game.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.mondragon.object.Game;

public class TestGame {
	Game game;
	
	@Before
	public void prepareGame() {
		String title = "MyGame";
		String description = "Some Description";
		String img = "true-false.img";
		String alt = "Some Description img";
		
		game = new Game(1,title,description,img,alt);
		//game.setId(1);
	}
	
	@Test 
	public void testId() {
		assertEquals(game.getId(), 1);
	}
	@Test 
	public void testTitle() {
		assertEquals(game.getTitle(),"MyGame");
	}
	@Test 
	public void testTitle2() {
		game.setTitle("anotherTitle");
		assertEquals(game.getTitle(),"anotherTitle");
	}
	@Test 
	public void testDescription() {
		assertEquals(game.getDescription(), "Some Description");
	}
	@Test 
	public void testDescription2() {
		game.setDescription("anotherDescription");
		assertEquals(game.getDescription(),"anotherDescription");
	}
}
