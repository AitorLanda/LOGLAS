<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container" id="gameDas">
   
    <h1>
		<spring:message    code="label.gameDashboard" />
	</h1>
        
    <div class="row">		
		<c:forEach items="${gameList}" var="game">
        	
        	<!-- PLAIN CARD -->
			<div class="col-xs-12 col-md-6 col-lg-4 col-xl-4 mt-3">
				<div class="card mb-5">
					<img id="Game${game.getId()}Image" class="card-img-top" src="${pageContext.request.contextPath}/resources/img/${game.getImg()}" alt="${game.getAlt()}">
					<div class="card-body">
						<p id="Game${game.getId()}Description" class="card-text">${game.getDescription()}</p>
					</div>
					<div class="card-footer text-left">
						<a id="Game${game.getId()}Link" href="${pageContext.request.contextPath}/lobby/dashboard?id=${game.getId()}">${game.getTitle()}</a>
					</div>
				</div>
			</div>
        	
        </c:forEach>
	</div>
   
</div>
