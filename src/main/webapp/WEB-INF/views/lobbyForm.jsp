<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container" id="lobForm">
   
    <h1>
		<spring:message code="label.createLobbyId" /> ${game.getTitle()}
	</h1>
	
	<p>
		<spring:message code="label.createLobbyText" />
   	</p>
   
  	<form:form action="save" method="post" modelAttribute="lobby">
      <form:hidden path="id"/>
            <div>	
                <spring:message code="label.createLobbyTitle" />
                <form:input id="titleInput" path="title" />
            </div>
           	<div>
                <spring:message code="label.createLobbyLimit" />
                <form:input id="playerLimitInput" path="playerLimit" type="number" min="2" max="6" />
            </div>	
        <button id="createLobbyButton" class="my-button-text">
				<span class ="my-button-span">
					<spring:message code="label.createLobbyCreate" />
				</span>
		</button>
	</form:form> 
   
</div>
