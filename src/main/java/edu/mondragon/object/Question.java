package edu.mondragon.object;

import java.util.List;

/**
 * @brief	Question class POJO.
 * 			For JSON object from questions file.
 * @author 	Loredi
 *
 */
public class Question {
	
	private int id;
	private String category;
	private String type;
	private String difficulty;
	private String questionText;
	private String correctAnswer;
	private List<String> incorrectAnswers;
	private List<List<String>> answerPairs;
	
	
	public Question() {
		super();
	}
	
	public Question(String category, String type, String difficulty, String questionText, 
			String correctAnswer, List<String> incorrectAnswers, List<List<String>> answerPairs) {
		this.category = category;
		this.type = type;
		this.difficulty = difficulty;
		this.questionText=questionText;
		this.correctAnswer = correctAnswer;
		this.incorrectAnswers = incorrectAnswers;
		this.answerPairs = answerPairs;

	}

	public List<List<String>> getAnswerPairs() {
		return answerPairs;
	}

	public void setAnswerPairs(List<List<String>> answerPairs) {
		this.answerPairs = answerPairs;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public List<String> getIncorrectAnswers() {
		return incorrectAnswers;
	}
	public void setIncorrectAnswers(List<String> incorrectAnswers) {
		this.incorrectAnswers = incorrectAnswers;
	}
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n----- ONE QUESTION -----\n");
        sb.append("category: " + getCategory() + "\n");
        sb.append("type: " + getType() + "\n");
        sb.append("difficulty: " + getDifficulty() + "\n");
        sb.append("questionText: " + getQuestionText() + "\n");
        sb.append("correctAnswer: " + getCorrectAnswer() + "\n");
        sb.append("incorrectAnswer: " + getIncorrectAnswers() + "\n");
        sb.append("answerPairs: " + getAnswerPairs() + "\n");
        sb.append("*****************************");
        return sb.toString();
    }

}
