<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container" id="reco">
	<h1>
		Recover password
	</h1>
	
	 <form:form action="recoverForm" method="post" modelAttribute="user">

            <form:hidden path="id"/>
            <div>
                <spring:message code="label.username"/>
                <form:input id="userNameInput" path="userName" />
            </div>
            <div>
                <spring:message code="label.userEmail"/>
                <form:input id="emailInput" path="email" />
            </div>
   
        <button id="recoverPasswordButton" class="my-button-text">
			<span class="my-button-span">
				<spring:message code="label.recoverPasswordButton"/>
			</span>
		</button>      
	</form:form> 
</div>