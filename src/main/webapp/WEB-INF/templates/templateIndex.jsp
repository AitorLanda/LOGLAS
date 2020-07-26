<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

		<title>
	        <tiles:insertAttribute name="title">
	        </tiles:insertAttribute>
    	</title>
		
		<!-- BOOTSTRAP CSS FILE -->
        <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
		<link href="${bootstrapCss}" rel="stylesheet" />
		
		<!-- MAIN CSS FILE -->
        <spring:url value="/resources/core/css/style.css" var="coreCss" />
		<link href="${coreCss}" rel="stylesheet" />
		
		<!-- MAIN FONT -->
		<link href="https://fonts.googleapis.com/css?family=Tomorrow&display=swap" rel="stylesheet">
 
    </head>
    <body>
        <div class="header-tile">
            <tiles:insertAttribute name="header" />
        </div>

        <div class="content-tile">
            <tiles:insertAttribute name="content" />
        </div>

        <div class="footer-tile">
            <tiles:insertAttribute name="footer" />
        </div>
        
        <!-- MAIN WS SCRIPT -->
		<!-- <spring:url value="/resources/core/js/contentWebsocket.js" var="wsJs" />
		<script src="${wsJs}"></script> -->
        
		<!-- JQUERY SCRIPT -->
		<spring:url value="/resources/core/js/jquery-3.2.1.js" var="jqueryJs" />
		<script src="${jqueryJs}"></script>

		<!-- BOOTSTRAP SCRIPT FILE -->
		<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
		<script src="${bootstrapJs}"></script>
        
        <!--  PARTICLES SCRIPT FILE -->
        <spring:url value="/resources/core/js/particles.js" var="particlesJs" />
		<script src="${particlesJs}"></script>
		<spring:url value="/resources/core/js/app.js" var="particlesAppJs" />
		<script src="${particlesAppJs}"></script>
    </body>
</html>