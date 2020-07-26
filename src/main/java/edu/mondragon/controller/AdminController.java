package edu.mondragon.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.mondragon.object.Question;
import edu.mondragon.object.Questions;
import edu.mondragon.user.model.User;
import edu.mondragon.user.service.UserService;

/**
 * 
 * @version v0.01
 * @brief  	Admin Controller --- /admin/**
 * @date	Creation: 12/10/19
 * @author 	Loredi Altzibar
 *
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
	

	static final String QUESTIONTABLE_REDIRECT = "redirect:/admin/questionTable";
	static final String USERTABLE_PAGE = "userTable";
	static final String QUESTIONTABLE_PAGE = "questionTable";
	static final String QUESTIONFORM_PAGE = "questionForm";
	static final String QUESTIONLIST_PAGE= "questionList";
	
	@Autowired
    private UserService userService;
	
	
	
	/**
	 * @brief 	VIEW all registered user info
	 * @param 	value: info
	 * @return	ModelAndVew: includes pages-view and objects
	 */

	@GetMapping(path = "/dashboard")
	public ModelAndView goToAdminDashboard(ModelAndView model) {
		List<User> userList = userService.listUsers();
        model.addObject("userList", userList);
        model.setViewName(USERTABLE_PAGE);
        return model;
	}
	
	/**
	 * @brief 	VIEW all questions info
	 * @param 	value: info
	 * @return	ModelAndVew: includes pages-view and object
	 */
	@GetMapping(path = "/questionTable")
	public ModelAndView goToAdminquestion(ModelAndView model) {
		List<Question> questionList = Questions.loadAllQuestions();
        model.addObject(QUESTIONLIST_PAGE, questionList);
        model.setViewName(QUESTIONTABLE_PAGE);
        return model;
	}
	
	/**
	 * @brief 	FORM for a new question
	 * @param 	value: info
	 * @return	ModelAndVew: includes pages-view and object
	 */
	@GetMapping(path = "/questionForm")
	public ModelAndView goToRegister(ModelAndView model) {
		Question question = new Question();
        model.addObject("question", question);
        model.setViewName(QUESTIONFORM_PAGE);
        return model;
	}
	
	/**
	 * @brief 	ADD new questions
	 * @param 	value: info
	 * @return	ModelAndVew: includes pages-view and object
	 */
	@PostMapping(path = "/saveQuestion")
	public ModelAndView addquestion(@ModelAttribute Question question,  HttpServletRequest request, ModelAndView model) {
		List<Question> questionList = Questions.loadAllQuestions(); 
		question.setType(request.getParameter("type"));
		question.setDifficulty(request.getParameter("dif"));
		boolean newQ = true;
		for (Question q:questionList) {
			if(q.getId()==question.getId()) {
				newQ=false;
			}
		}
		if (newQ) {
			 questionList = Questions.addQuestion(question);
		 }else{
			 questionList = Questions.updateQuestion(question);
		 }
        model.addObject(QUESTIONLIST_PAGE, questionList);
        model.setViewName(QUESTIONTABLE_PAGE);
        return model;
	}
	/**
	 * @brief 	Edit question
	 * @param 	value: info
	 * @return	ModelAndVew: includes pages-view and object
	 */
	@GetMapping(path = "/editQuestion")
    public ModelAndView editQuestion(HttpServletRequest request, ModelAndView model) {
        int questionId = Integer.parseInt(request.getParameter("id"));
        List<Question> questionList = Questions.loadAllQuestions();
        Question question = null;
        for (Question q:questionList) {
        	if(q.getId()==questionId) {
        		question = q;
        	}
        }
        model.addObject("question", question);
        model.setViewName(QUESTIONFORM_PAGE);
        return model;
    }

	@GetMapping(path = "/deleteQuestion")
    public ModelAndView deleteQuestion(HttpServletRequest request, ModelAndView model) {
        int questionId = Integer.parseInt(request.getParameter("id"));
        List<Question> questionList = Questions.deleteQuestion(questionId);

        model.addObject(QUESTIONLIST_PAGE, questionList);
        model.setViewName(QUESTIONTABLE_PAGE);
        return model;
    }
	
	

}