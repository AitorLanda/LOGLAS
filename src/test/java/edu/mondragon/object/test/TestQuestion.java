package edu.mondragon.object.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.mondragon.object.Question;

public class TestQuestion {

	Question question, questio2;
	
	@Before
	public void setUp() {
		String category="Math", type="boolean", difficulty="easy", quest="2+2=5", correctAnswer="False";
		List<String> incorrectAnswers = new ArrayList<>(); 
		incorrectAnswers.add("False");
		List<List<String>> list = new ArrayList<>();
		for(int a = 0;a<3;a++) {
			List<String> i = new ArrayList<>(); 
			i.add("1");
			i.add("2");
			i.add("3");
			list.add(i);
		}	
		questio2 = new Question(category, type,difficulty,quest, correctAnswer, incorrectAnswers, list);
		question = new Question();
	}
	
	@Test
	public void testId() {
		question.setId(1);
		assertEquals(question.getId(),1);
	}
	@Test
	public void testCategory() {
		question.setCategory("Math");
		assertEquals(question.getCategory(),questio2.getCategory());
	}
	@Test
	public void testType() {
		question.setType("boolean");
		assertEquals(question.getType(),questio2.getType());
	}
	@Test
	public void test() {
		question.setId(1);
		assertEquals(question.getId(),1);
	}
	@Test
	public void testDifficulty() {
		question.setDifficulty("easy");
		assertEquals(question.getDifficulty(),questio2.getDifficulty());
	}
	@Test
	public void testQuestion() {
		question.setQuestionText("2+2=5");
		assertEquals(question.getQuestionText(),questio2.getQuestionText());
	}
	@Test
	public void testCorrect() {
		question.setCorrectAnswer("False");
		assertEquals(question.getCorrectAnswer(),questio2.getCorrectAnswer());
	}
	@Test
	public void testIncorrect() {
		List<String> incorrectAnswers = new ArrayList<>(); 
		incorrectAnswers.add("False");
		question.setIncorrectAnswers(incorrectAnswers);
		assertEquals(question.getIncorrectAnswers().get(0),questio2.getIncorrectAnswers().get(0));
	}
	
	@Test
	public void testPair() {
		List<List<String>> list = new ArrayList<>();
		for(int a = 0;a<3;a++) {
			List<String> i = new ArrayList<>(); 
			i.add("1");
			i.add("2");
			i.add("3");
			list.add(i);
		}	
		question.setAnswerPairs(list);
		assertEquals(question.getAnswerPairs().get(1).get(1),questio2.getAnswerPairs().get(1).get(1));
	}
	
}
