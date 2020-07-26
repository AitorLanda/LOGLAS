<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="container" id="lobb">
   
    <h1>${title}</h1>
        
    <div class="row">
		
		<h2>
			Lobby owner id: ${ lobby.getUserId() }
		</h2>
		
		<h2>
			Max player Limit: ${ lobby.getPlayerLimit() }
		</h2>
		
		<hr>
		
		<h2>
			User id: ${ user.getId() }
		</h2>
		
		<h2>
			Username: ${ user.getUserName() }
		</h2>
		
		<h2>
			Email: ${ user.getEmail() }
		</h2>
		
	</div>
   
</div>
