<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<header class="col bg-light">

<!-- TOP NAVIGATION -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">

	<!-- LOGO -->

	<a class="navbar-brand" href="${pageContext.request.contextPath}/">
		<img id="logo" src="${pageContext.request.contextPath}/resources/img/logo.png" 
			width="30" height="30" class="d-inline-block align-to bg-transparent" alt="logo-img">
		${title}
	</a>
	
	<!-- BUTTON FOR SMALL SCREEN COLLAPSE MENU -->
	<button class="navbar-toggler" type="button" data-toggle="collapse" 
		data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	
	<!-- will collapse everything in this div -->
	<div class="collapse navbar-collapse bg-light" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<!-- LINK TO NEXT PAGE -->
			<li class="nav-item">
				<a class="nav-link bg-light" href="${pageContext.request.contextPath}/game/dashboard">
					
					<spring:message code="label.navHomeButton" />
				</a>
			</li>
			<!-- GAMES dropdown -->
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle bg-light" href="#" id="navbarDropdown" 
					role="button" data-toggle="dropdown" aria-haspopup="true" 
					aria-expanded="false">

					<spring:message code="label.navGameButton" />
				</a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<c:forEach items="${applicationScope.container.getGamesList()}" var="game">
						<a id="Game${game.getId()}" class="dropdown-item" href="${pageContext.request.contextPath}/lobby/dashboard?id=${game.getId()}">
							${game.getTitle()}
						</a>
					</c:forEach>
				</div>
			</li>
		</ul>
		
		<!-- User Box (right) -->
		<c:if test="${sessionScope.user != null}">
			<ul class="nav navbar-nav navbar-right">
            	<!-- USER dropdown -->
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle bg-light" href="#" id="GameDropdown" 
						role="button" data-toggle="dropdown" aria-haspopup="true" 
						aria-expanded="false">
						${sessionScope.user.userName}
					</a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="GameDropdown">


						<a id="informationDropdown" class="dropdown-item" href="${pageContext.request.contextPath}/info">
						<spring:message code="label.navAccount" />
						</a>
						<a id="passwordRecoveryDropdown" class="dropdown-item" href="${pageContext.request.contextPath}/editUser?id=${sessionScope.user.getId()}"><spring:message code="label.navChangePassword" /></a>
						<c:if test="${sessionScope.user.admin}">

							<a id="questionDropdown" class="dropdown-item" href="${pageContext.request.contextPath}/admin/questionTable">
							<spring:message code="label.navQuestions" />
							</a>
							<a id="grafanaDropdown" class="dropdown-item" href="http://loglas.duckdns.org:3000/d/oy8XafPWk/loglas?orgId=1&from=now-30d&to=now" target="_blank">
							<spring:message code="label.navData" />
							</a>
						</c:if>
					<div class="dropdown-divider"></div>

						<a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><spring:message code="label.navLogOut" /></a>
					</div>
				</li>			
			</ul>
		</c:if>
		
		<ul class="nav navbar-nav navbar-right">
            	<!-- USER dropdown -->
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle bg-light" href="#" id="lenguageDropdown" 
						role="button" data-toggle="dropdown" aria-haspopup="true" 
						aria-expanded="false">
						<spring:message code="label.navLanguage" />
					</a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="lenguageDropdown">
						<a id="spanishDropdown" class="dropdown-item" href="${pageContext.request.contextPath}/?lang=es">es</a>
						<a id="vasqueDropdown" class="dropdown-item" href="${pageContext.request.contextPath}/?lang=eus">eus</a>
						<a id="englishDropdown" class="dropdown-item" href="${pageContext.request.contextPath}/?lang=en">en</a>
					</div>
				</li>			
			</ul>
		
	</div>	
</nav> 

</header>
