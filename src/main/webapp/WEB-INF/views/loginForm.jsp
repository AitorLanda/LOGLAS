<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container" id="logg">
	<h1>
		<spring:message code="label.loginTitle" />
	</h1>
	<p>
		<spring:message code="label.loginMessage" />
   	</p>
   
	 <form:form action="checkUser" method="POST" modelAttribute="user">
         <form:hidden path="id"/>
          	<div>
                <spring:message code="label.username" />
                <form:input id = "usernameInput" path="userName" />
           	</div>
           	<div>
                <spring:message code="label.password" />
                <form:input id = "passwordInput" type="password" path="password" />
          	</div>
        <button id="loginButton" class="my-button-text">
				<span class="my-button-span">
					<spring:message code="label.loginLoginButton" />
				</span>
		</button>        
	</form:form> 
	<div class="my-button">
		<a id="recoverButton" class="my-button-text" href="${pageContext.request.contextPath}/recoverForm">
			<span  class="my-button-span">
				<spring:message code="label.loginRecoverButton" />
			</span>
		</a>
	</div>
</div>
