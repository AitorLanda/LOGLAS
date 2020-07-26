<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<header class="col bg-light">

<!-- TOP NAVIGATION -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">

	<!-- LOGO -->
	<a class="navbar-brand" href="${pageContext.request.contextPath}/">
		<img id="logo" src="${pageContext.request.contextPath}/resources/img/logo.png" width="30" height="30" class="d-inline-block align-to bg-transparent" alt="logo-img">
		${title}
	</a>
	
	<!-- BUTTON FOR SMALL SCREEN COLLAPSE MENU -->
	<button id="collapsedMenu" class="navbar-toggler" type="button" data-toggle="collapse" 
		data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" 
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon my-button-span"></span>
	</button>
	
	<!-- will collapse everything in this div -->
	<div class="collapse navbar-collapse bg-light" id="navbarSupportedContent">
		<!-- User Box (right) -->
		<c:if test="${sessionScope.user != null}">
			<ul class="nav navbar-nav navbar-right">
            	<!-- USER dropdown -->
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle bg-light" href="#" id="navbarDropdown" 
						role="button" data-toggle="dropdown" aria-haspopup="true" 
						aria-expanded="false">
						${sessionScope.user.userName}
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/info">
						<spring:message code="label.navAccount" />
						</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/recover"><spring:message code="label.navChangePassword" /></a>
						<c:if test="${sessionScope.user.admin}">
							<a class="dropdown-item" href="${pageContext.request.contextPath}/admin/questionList">
							<spring:message code="label.navQuestions" />
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
					<a class="nav-link dropdown-toggle bg-light" href="#" id="navbarDropdown" 
						role="button" data-toggle="dropdown" aria-haspopup="true" 
						aria-expanded="false">
						<spring:message code="label.navLanguage" />
					</a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/?lang=es">es</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/?lang=eus">eus</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/?lang=en">en</a>
					</div>
				</li>			
			</ul>
	  </div>	
</nav> 

</header>
