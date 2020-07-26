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
		
		<!-- JQUERY SCRIPT -->
		<spring:url value="/resources/core/js/jquery-3.2.1.js" var="jqueryJs" />
		<script src="${jqueryJs}"></script>
		
		
		
		<!-- C3 CSS FILE -->
		<spring:url value="/resources/core/lib/c3-0.4.11/c3.min.css" var="c3Css" />
		<link href="${c3Css}" rel="stylesheet" />
		<!-- D3 SCRIPT FILE -->
		<spring:url value="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.17/d3.min.js" var="d3Js" />
		<script src="${d3Js}"></script>
		<!-- C3 SCRIPT FILE -->
		<spring:url value="/resources/core/lib/c3-0.4.11/c3.min.js" var="c3Js" />
		<script src="${c3Js}"></script>
		
		
		
		<!-- MAIN CSS FILE -->
        <spring:url value="/resources/core/css/style.css" var="coreCss" />
		<link href="${coreCss}" rel="stylesheet" />
		
		<!-- TETRIS CSS FILE -->
        <spring:url value="/resources/core/css/tetris.css" var="tetrisCss" />
		<link href="${tetrisCss}" rel="stylesheet" />
		
		
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
       

		<!-- BOOTSTRAP SCRIPT FILE -->
		<spring:url value="/resources/core/js/bootstrap.min.js" var="bootstrapJs" />
		<script src="${bootstrapJs}"></script>
        
        <!--  PARTICLES SCRIPT FILE -->
        <spring:url value="/resources/core/js/particles.js" var="particlesJs" />
		<script src="${particlesJs}"></script>
		<spring:url value="/resources/core/js/app.js" var="particlesAppJs" />
		<script src="${particlesAppJs}"></script>
		
		<!-- USER TRACKING SCRIPT FILE -->
		<spring:url value="/resources/core/js/tracker.js" var="trackerJs" />
		<script src="${trackerJs}" onerror="console.log('error loading tracker.js')"></script>
        
    </body>
</html>