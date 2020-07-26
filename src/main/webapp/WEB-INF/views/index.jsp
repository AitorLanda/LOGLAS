<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="indexContainer">
		<div class="row">
			
			<div class="col-sm">
				<spring:message    code="label.loginText" />
			</div>
			
			<div class="col-sm">
				<spring:message    code="label.registerText" />
			</div>
			
		</div>
		
		<div class="row">
			
			<div class="col-sm">
				 <div class="my-button mainButton">
				 	<a id="loginButton" class="my-button-text bg-transparent" href="${pageContext.request.contextPath}/login">
				 		<span  class="my-button-span">

				 			<spring:message    code="label.loginButton" />
				 		</span>
				 	</a>
				 </div>
			</div>
			
			<div class="col-sm">
				<div class="my-button mainButton">
					<a id="registerButton" class="my-button-text bg-transparent" href="${pageContext.request.contextPath}/register">
						<span  class="my-button-span">

							<spring:message    code="label.registerButton" />
						</span>
					</a>
				</div>
			</div>
			
		</div>
</div>
	<div id="particles-js" class="bg"></div>
