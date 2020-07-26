package edu.mondragon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.mondragon.config.WebMvcConfig;
import edu.mondragon.object.Email;
import edu.mondragon.object.UserStats;
import edu.mondragon.playerscore.model.PlayerScore;
import edu.mondragon.playerscore.service.PlayerScoreService;
import edu.mondragon.user.model.User;
import edu.mondragon.user.service.UserService;

/**
 * 
 * @version v0.01
 * @brief  	Spring MVC Controller. For pages navigation. --- /**
 * 			Creates a Map of model object and find a view 
 * 			(check on MUdle: Spring MVC Hello World example)
 * @see		WebMvcConfig and MyWebInitializer
 * @date	Creation: 12/02/19
 * @date	Update: "Boot" 12/03/19
 * @author 	Loredi Altzibar
 *
 */

@Controller
public class SpringController {
	
	static final String MESSAGE = "message";	
	static final String LOGIN_REDIRECT = "redirect:/login";
	static final String RECOVERFORM_REDIRECT = "redirect:/recoverForm";
	static final String ADMINDASHBOARD_REDIRECT = "redirect:/admin/dashboard";
	static final String GAMEDASHBOARD_REDIRECT = "redirect:/game/dashboard" ;
	static final String USERINFO_PAGE = "userInfo";
	static final String PASSWORD_CHANGE_FORM = "passChangeForm";
	@Autowired
    private UserService userService;
	
	@Autowired
    private PlayerScoreService playerScoreService;
	
	
	/**
	 * @brief index page. START HERE
	 * @return simple String
	 */
	@GetMapping(path = "/")
	public String goToIndex(ModelMap model) {
		model.addAttribute(MESSAGE, "LOGLAS Spring 3 MVC");
		return "index";
	}
	
	/**
	 * @brief 	Used as a filter. Use this to return a new model with objects inside and to set view at once
	 * 			Here we can check where "/" we are and restrict permissions etc. ??
	 * @param 	value: {val:.+} to use this value furthermore in the code
	 * 			if you put @pathVariale(val) String str
	 * @return	ModelMap / ModelAndVew: includes pages-view and objects
	 */
	@GetMapping(path = "/login")
	public ModelAndView goToLogin(ModelAndView model) {
		// I create a new empty user so I can work with it in the form and fill it down
		User user = new User();
        model.addObject("user", user);
        model.setViewName("loginForm");
        return model;
	}
	
	/**
	 * @brief 	Use to recover a forgotten password
	 */
	@GetMapping(path = "/recoverForm")
    public ModelAndView recoverPassword(ModelAndView model) {
		User user = new User();
        model.addObject("user", user);
        model.setViewName("recoverForm");
        return model;
    }

	/**	 
 	 * @brief 	Use to register a user
	 * 			First create and empty user and send it in order to fill it in.
	 * @param 	value: {val:.+} to use this value furthermore in the code
	 * 			if you put @pathVariale(val) String str
	 * @return	ModelMap / ModelAndVew: includes pages-view and objects
	 */	
	@GetMapping(path = "/register")
	public ModelAndView goToRegister(ModelAndView model) {
		User user = new User();
        model.addObject("user", user);
        model.setViewName("registerForm");
        return model;
	}
	

	/**
	 * @brief 	Used to check the login credentials.
	 * 			If they are valid, then save a copy of this user object
	 * 			this way we can use it on the session.
	 * @param 	value: checkUser
	 * @return	ModelAndVew: redirect either way.
	 */
	@PostMapping(path = "/checkUser")
    public ModelAndView checkUser(@ModelAttribute User user, HttpServletRequest request) {
		// Get introduces user info from DATABASE
        User fullUserInfo = userService.authenticateUser(user.getUserName(), user.getPassword());
        String url = LOGIN_REDIRECT;
        // Handle the user credential
        if (fullUserInfo != null) {
        	HttpSession session = request.getSession();
        	session.setAttribute("user", fullUserInfo);
        	url = GAMEDASHBOARD_REDIRECT;
        }
        return new ModelAndView(url);
    }
	
	/**
	 * @brief Us
	 * @param user
	 * @param model
	 * @return
	 */
	@PostMapping(path = "/recoverForm")
    public ModelAndView emailPass(@ModelAttribute User user, ModelAndView model) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
        User fullUserInfo = userService.authenticateUserEmail(user.getUserName(), user.getEmail());
        if (fullUserInfo != null) {
        	logger.info("enviar correo");
        	/*Email.sendEmail(fullUserInfo.getEmail(),"LOGLAS - Password recovery","Click here to change your password: "
        			+ "loglas.duckdns.org:8181/POPBL5/passChangeRequest?id=" + fullUserInfo.getId());*/
        	fullUserInfo.setPassword(fullUserInfo.getFirstName());
        	userService.updateUser(fullUserInfo);
        	Email.sendEmail(fullUserInfo.getEmail(),"LOGLAS - Password recovery","Your new password: " + fullUserInfo.getFirstName()+". Please, change when you can");
        	return new ModelAndView("emailNotice");
        } else {
        	User newUser = new User();
            model.addObject("user", newUser);
            model.setViewName(RECOVERFORM_REDIRECT);
            return model;
        }
    }
	
	@PostMapping(path = "/passChangeRequest")
    public ModelAndView changePassRequest(HttpServletRequest request) {
		int userId = Integer.parseInt(request.getParameter("id"));
        User user = new User();
        user.setId(userId);
        
        ModelAndView newModel = new ModelAndView(PASSWORD_CHANGE_FORM);
        newModel.addObject("user", user);
        newModel.setViewName(PASSWORD_CHANGE_FORM);
        return newModel;
    }
	
	@PostMapping(path = "/passChange")
    public ModelAndView changePass(@ModelAttribute User user, HttpServletRequest request) {
        
        // DB UPDATE USER
        userService.updateUser(user);
		
        ModelAndView newModel = new ModelAndView(PASSWORD_CHANGE_FORM);
        newModel.setViewName(LOGIN_REDIRECT);
        return newModel;
    }
	
	@GetMapping(path = "/info")
    public ModelAndView goToInfo(HttpServletRequest request, HttpServletResponse response) {
		// Retrieve user info
		User user = (User) request.getSession().getAttribute("user");
		
		// Get all score history
		List<PlayerScore> scoreList = playerScoreService.listPlayerScoresByUserId(user.getId());
		UserStats userStats = new UserStats(scoreList);
		
		// Send as JSON to be able to manage this info in javascript
		Gson gson = new Gson();
		String jsonString = gson.toJson(userStats); 
		
		// Handle admin rights
		String url = USERINFO_PAGE;
		if (user.isAdmin()) {
			url = ADMINDASHBOARD_REDIRECT;
		}
		
		// View model
		ModelAndView model = new ModelAndView(url);
		model.addObject("userStats", jsonString);
		return model;
    }
	
	@GetMapping(path = "/logout")
	public ModelAndView logout(HttpServletRequest request, SessionStatus status, ModelAndView model) {
		HttpSession session = request.getSession();
		status.setComplete();
		session.removeAttribute("user");
		session.invalidate();
        model.setViewName(LOGIN_REDIRECT);
        return model;
	}
	
	@PostMapping(path = "/saveUser")
    public ModelAndView saveUser(@ModelAttribute User user) {
        if (user.getId() == 0) { // if user id is 0 then CREATE new user
            userService.addUser(user);
        } else {
        	// else UPDATE user
        	userService.updateUser(user);
        }
        return new ModelAndView(LOGIN_REDIRECT);
    }
	
	@GetMapping(path = "/deleteUser")
    public ModelAndView deleteUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(userId);
        return new ModelAndView("redirect:/");
    }
 
    @GetMapping(path = "/editUser")
    public ModelAndView editUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.getUser(userId);
        ModelAndView model = new ModelAndView("registerForm");
        model.addObject("user", user);
        return model;
    }
	

	/*---------- MY DEBUGGING CODE (do not pay attention) -----------*/
	@GetMapping(path = "/{name:.+}")
	public ModelAndView clickTestUrl(@PathVariable("name") String name) {
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		model.addObject("msg", name);
		return model;
	}

}