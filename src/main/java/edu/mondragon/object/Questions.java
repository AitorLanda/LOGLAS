package edu.mondragon.object;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @brief	CRUDE Question for file (questions-answers.json)
 * 
 */
public class Questions {
	static final String JSON_FILE="/files/questions-answers.json";
	static Logger logger = LoggerFactory.getLogger(Questions.class);

	private Questions() {

	}
	/**
	 * @brief	Static class Question
	 * 			Writes a question list on a file
	 * @param 	{list} allQuestions items : questions
	 */
	public static void writeQuestions( List<Question> allQuestions) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File(JSON_FILE), allQuestions);
		
		} catch (IOException e) {
			logger.error("An error occurred", e);
		}
	}

	/**
	 * @brief	Loads all question from question-answers.json file
	 * @return	{list} containing Question items
	 */
	public static List<Question> loadAllQuestions() {		
		
		ObjectMapper objectMapper = new ObjectMapper();
		List<Question> allQuestions = new ArrayList<>();
		// Read file as input stream
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(JSON_FILE));	
			// Map Input stream info as list of questions
			allQuestions = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class));
		} catch (IOException e) {
			logger.error("An error occurred", e);
		}
		
		return allQuestions;
	}
	
	/**
	 * @brief	Adds a question and writes to question-answers.json file
	 * @param 	{item} Question object
	 */
	public static List<Question> addQuestion(Question question){
		int a = 0;
		List<Question>allQuestions = new ArrayList<>();
		allQuestions = loadAllQuestions();
		for(Question q :allQuestions) {
			if(q.getId()>a) {
				 a = q.getId();
			}
		}
		question.setId(a+1);		
		if (checkValidQuestion(question)) {			
			allQuestions.add(question);
			writeQuestions(allQuestions);}
		return allQuestions;	
	}
	
	/**
	 * @brief	Checks if a question is valid
	 * 			- The fields must not be empty
	 * @param 	question
	 * @return	{boolean} valid
	 */
	public static boolean checkValidQuestion(Question question) {
		boolean valid = false;
		String type = question.getType();
		String questionText = question.getQuestionText();
		String correctAnswer = question.getCorrectAnswer();
		List<String> incorrectAnswers = question.getIncorrectAnswers();
		
		valid = checkQuestionType(type);
		
		// Check question and correctAnswer
		if (questionText.equals("") || correctAnswer.equals("")) {
			valid = false;
		}
		
		// Check incorrectAnswers
		for (String answer : incorrectAnswers) {
			if (answer.equals("")) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	/**
	 * @brief	Checks if a question type is valid
	 * 			- The fields must be "boolean" or "multiple"
	 * @param 	{String} type
	 * @return	{boolean} valid
	 */
	public static boolean checkQuestionType(String type) {
		boolean valid = false;
		
		// Check if type
		if (type.contentEquals("multiple") || type.contentEquals("boolean") || type.contentEquals("sort")) {
			valid = true;
		}
		
		return valid;
	}
	
	/**
	 * @brief	Updates a question.
	 * 			Returns the list and updates the file
	 * @param 	{Question} 
	 * @return	{list} items: question
	 */
	public static List<Question> updateQuestion(Question question) {
		List<Question> allQuestions = loadAllQuestions();
		for(Question question2 : allQuestions) {
			if(question.getId()==question2.getId()) {
				question2.setCategory(question.getCategory());
				question2.setCorrectAnswer(question.getCorrectAnswer());
				question2.setDifficulty(question.getDifficulty());
				question2.setIncorrectAnswers(question.getIncorrectAnswers());
				question2.setQuestionText(question.getQuestionText());
				question2.setType(question.getType());
				
				writeQuestions(allQuestions);
			}
		}
		return allQuestions;
	}
	
	/**
	 * @brief	Deletes a question.
	 * 			Returns the list and updates the file
	 * @param 	{int} id
	 * @return	{list} items: question
	 */
	public static List<Question> deleteQuestion(int id) {
		List<Question> allQuestions = new ArrayList<>();
		allQuestions = loadAllQuestions();
		Question a = null;
		for(Question q :allQuestions) {
			if(q.getId()==id) {
				 a = q;
			}
		}
		allQuestions.remove(a);
		writeQuestions(allQuestions);
		return allQuestions;
	}
	
}
