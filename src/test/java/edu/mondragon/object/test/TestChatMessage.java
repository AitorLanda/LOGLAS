package edu.mondragon.object.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.mondragon.object.ChatMessage;

public class TestChatMessage {
	
	ChatMessage ch;
	
	@Before
	public void setup() {
		
		ch = new ChatMessage("me","message");
	}
	
	@Test
	public void testSender() {
		ch.setSender("MeAgain");
		
		assertEquals(ch.getSender(),"MeAgain");
	}
	
	@Test
	public void testMessage() {
		ch.setMessage("someMessage");
		
		assertEquals(ch.getMessage(),"someMessage");
	}
	
}
