package edu.mondragon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.mondragon.object.Container;
import edu.mondragon.object.Game;

/**
 * 
 * @version v0.01
 * @brief  	Lobby Controller --- /game/**
 * @date	Creation: 12/10/19
 * @author 	Loredi Altzibar
 *
 */

@Controller
@RequestMapping("/game")
public class GameController {
	
	static final String CONTAINER = "container";
	
	static final String GAMEDASHBOARD_PAGE = "gamesDashboard" ;
	
	/**
	 * @brief	VIEW all games
	 * @param	value: dashboard
	 * @return	ModelAndView
	 */
	@GetMapping(path = "/dashboard")
	public ModelAndView goToGamesDashboard(HttpServletRequest request, ModelAndView model) {		
		// Use the application scope attribute to manage lobbies
		Container container = (Container) request.getSession().getServletContext().getAttribute(CONTAINER);
		
		// Get the list of games from memory container
		List<Game> gameList = container.getGamesList();
		
		
        model.addObject("gameList", gameList);
        model.setViewName(GAMEDASHBOARD_PAGE);
        return model;
	}
	

}