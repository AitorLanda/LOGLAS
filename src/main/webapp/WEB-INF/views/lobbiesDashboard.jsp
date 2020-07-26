<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
	//Retrieve game ID that we just joined
	var gameId = <%=(Integer) request.getAttribute("gameId")%>;
	
	// Create web socket with lobbyId attached
    var webSocket = new WebSocket('ws://' + document.location.host + '/POPBL5/lobbychat/' + gameId);
	
    webSocket.onerror = function(event) {
      	onError(event)
    };

    webSocket.onopen = function(event) {
      	onOpen(event)
    };

    webSocket.onmessage = function(event) {
      	onMessage(event)
    };
    
 	// event.data contains the data that Endpoint broadcasted
    function onMessage(event) {
    	// Since we have a JSON format data, we have a structure.
    	// We will visualize these structure datas on different DOM elements.
    	const container = JSON.parse(event.data);	
    	
    	// Show lobbies by game ID
    	const lobbiesContainer = document.getElementById('lobbiesContainer');
    	var lobbyGroups = getLobbyGroupsByGameId(gameId, container.lobbyGroupsList);
    	
    	$('#lobbiesContainer').empty();
    	// Populate with cards iterating each lobbyGroup
    	for (var i in lobbyGroups) {
    		var html = '<div class="col-xs-12 col-md-6 col-lg-4 col-xl-4 mt-3">';
        	html += '<div class="card mb-5">';
        	html += '<div class="card-header text-left text-dark title">' + lobbyGroups[i].title + '</div>';
        	html += '<div class="card-body">';
    		html += '<p class="card-text"> Owner: ' + lobbyGroups[i].owner.username + '</p>';
    		html += '<p class="card-text">' + lobbyGroups[i].count + '/' + lobbyGroups[i].playerLimit + '</p>';
    		if (lobbyGroups[i].full == false) {
    			html += '<a id="joinButton" class="btn btn-primary" href="${pageContext.request.contextPath}/lobby/info?id=' + 
					lobbyGroups[i].lobbyId + '">Join</a>';
    		} else {
    			html += '<a id="viewButton" class="btn btn-primary" href="${pageContext.request.contextPath}/lobby/view?id=' + 
				lobbyGroups[i].lobbyId +'">View</a>';
    		}
    		html += '</div>';
    		html += '</div>';
        	html += '</div>';
    		$('#lobbiesContainer').append(html);
    	}
    }
 	
    function getLobbyGroupsByGameId(gameId, lobbyGroupsList){
    	var lobbyGroups = [];
        for (var i=0; i < lobbyGroupsList.length; i++) {
            if (lobbyGroupsList[i].gameId === gameId) {
            	lobbyGroups.push(lobbyGroupsList[i]);
            }
        }
        return lobbyGroups;
    }
    
    function onOpen(event) {}
	
</script>

<div class="container" id=lobDas>
   
    <h1>
    	${game.getTitle()}
    </h1>
        
    <div class="row" id="lobbiesContainer">
    	<!-- LobbyGroup CARDS -->
		<!-- Auto filled by info (JSON) coming form websocket connection (script) -->
	</div>
   
	<div id="createLobbyBtn">
	   	<div class="my-button">
			<a class="my-button-text" href="${pageContext.request.contextPath}/lobby/new?id=${gameId}">
				<span class="my-button-span">
					<spring:message    code="label.createLobbyButton" />
				</span>
			</a>
		</div>
	</div> 
   
</div>
