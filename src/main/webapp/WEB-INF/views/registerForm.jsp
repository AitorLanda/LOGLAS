<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container" id="regis">
	<h1>
		<spring:message    code="label.registerTitle" /> 
	</h1>
	<p>
		<spring:message    code="label.registerMessage" />
   	</p>
   
    <form:form action="saveUser" method="post" modelAttribute="user">
            <form:hidden path="id"/>
            <div>
                <spring:message    code="label.username" />
                <form:input id="usernameInput" path="userName" />
            </div>
            <div>
                <spring:message    code="label.password" />
                <form:input id="passwordInput" type="password" path="password" />
            </div>
            <div>
                <spring:message    code="label.userTableFname" />
                <form:input id="firstNameInput" path="firstName" />
            </div>
            <div>
                <spring:message    code="label.userTableLname" />
                <form:input id="lastNameInput" path="lastName" />
            </div>
            <div>
                <spring:message    code="label.userEmail" />
                <form:input id="emailImput" path="email" />
            </div>
        
        	
        		
        		<button id="registerButton" type="submit" class="my-button-text">
					<span class="my-button-span">
						REGISTER
					</span>
				</button>
        	
			
        	
        
	</form:form>
   
</div>
