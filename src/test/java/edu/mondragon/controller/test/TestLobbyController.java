package edu.mondragon.controller.test;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import edu.mondragon.controller.LobbyController;
import edu.mondragon.object.Game;


public class TestLobbyController {
	
	@Autowired
    private WebApplicationContext wac;

	LobbyController lbc;
	Game game;
	
	

	
	@Before
	public void setup() {
		lbc = new LobbyController();
		game = lbc.setUpGameAttribute();
		//this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	
	

	/*
	@Test
	public void testGoToLobby() throws Exception{
		ModelAndView model = new ModelAndView();
		
		this.mockMvc.perform(get("/dashboard"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(forwardedUrl("index.html"));
		
		
	}
	*/
}
