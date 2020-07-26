<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container" id="quesForm">
	<h1>
		<spring:message code="label.questionTitle" />
	</h1>
	<p>
		<spring:message code="label.questionMessage" />
   	</p>
   
    <form:form action="saveQuestion" method="post" modelAttribute="question">
        	<form:hidden path="id"/>
            <div>
            	<spring:message code="label.category" />
                <form:input id="cateforyInput" path="category" />
            </div>
            <div>
            	<spring:message code="label.difficulty" />
                	<select id="difficultySelector" name="dif">
    					<option id="difficultySelectorEasy" value="easy">easy</option>
    					<option id="difficultySelectorMedium" value="mediun">mediun</option>
    					<option id="difficultySelectorHard" value="hard">hard</option>
  					</select>
            </div>
            <div>
                <spring:message code="label.type" />
                	<select id="typeSelector" name="type">
    					<option id="typeSelectorMultiple" value="multiple">multiple</option>
    					<option id="typeSelectoBoolean" value="boolean">boolean</option>
  					</select>
            </div>
            <div>
            	<spring:message code="label.question" />
                <form:input id="questionInput" path="questionText" />
            </div>
            <div>
            	<spring:message code="label.correct" />
                <form:input id="correctAnswerInput" path="correctAnswer" />
            </div>
            <div>
            	<spring:message code="label.incorrect" />
                <form:input id="incorrectAnswersInput" path="incorrectAnswers" />
            </div>		
        		<button id="submitQuestionButton" type="submit" class="my-button-text">
					<span class="my-button-span">
						REGISTER
					</span>
				</button>
	</form:form>
   
</div>
