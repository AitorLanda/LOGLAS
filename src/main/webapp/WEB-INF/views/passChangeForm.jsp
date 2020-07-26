<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container" id="passChan">
	<h1>
		Recover password
	</h1>
	
	 <form:form action="passChange" method="post" modelAttribute="user">
            <form:hidden path="id"/>
            <div>
            	<spring:message code="label.newpass" />
                <form:input id="newPasswordInput" path="password" />
            </div>
        <button id="changePasswordButton" type="submit" class="my-button-text bg-transparent">
				<span class="my-span-button">
					Change password
				</span>
		</button>       
	</form:form> 
</div>