<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container" id="quesTable">
	<h1>
		<spring:message code="label.questionTableTitle" />
	</h1>
	<p>
		<spring:message code="label.questionTalbleMessage" />
   	</p>
        <h3>
            <a id="newQuestionButton" href="questionForm"><spring:message code="label.newQuestion" /></a>
        </h3>
        <table>
 			<tr>
            	<th><spring:message code="label.question" /></th>
            	<th><spring:message code="label.type" /></th>
            	<th><spring:message code="label.correct" /></th>
           	 	<th><spring:message code="label.userTableAction" /></th>
 			</tr>
            <c:forEach items="${questionList}" var="question">
                <tr>
 					<td>${question.getQuestionText()}</td>
                    <td>${question.getType()}</td>
                    <td>${question.getCorrectAnswer()}</td>
                    <td><a id="eidtButton${question.getId()}" href="editQuestion?id=${question.getId()}">Edit</a>
                        <a id="deleteButton${question.getId()}" href="deleteQuestion?id=${question.getId()}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
   
</div>