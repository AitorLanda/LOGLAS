<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@page import="edu.mondragon.lobby.model.Lobby"%> 

<script type="text/javascript">

	// Retrieve lobby ID that we just joined
	<%Lobby lobby = (Lobby)request.getAttribute("lobby");%>;
	var lobbyId = <%=lobby.getId()%>;
	
	// Retrieve username
	var username = "${user.getUserName()}";
	
	// Create web socket with lobbyId attached
    var webSocket = new WebSocket('ws://' + document.location.host + '/POPBL5/lobbychat/' + lobbyId);
	
	// Used for blocking more recieving messages from websocket
	var gameStarted = false;
	
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
    	var container = JSON.parse(event.data);
    	// action = "chatMessage"  --> show message on chat too
    	if (container.action === "chatMessage") {
    		if (container.chatMessage.sender === username) {
    			document.getElementById('chatMessages').innerHTML += '<br />' + container.chatMessage.message;
    		} else {
    			document.getElementById('chatMessages').innerHTML += '<br />' + container.chatMessage.sender 
    			+ ': ' + container.chatMessage.message;
    		}
    	} else if (container.action === "leaveLobby" || container.action === "joinLobby") {
    		document.getElementById('chatMessages').innerHTML += '<br />' + container.chatMessage.message;
    	}
    	
    	// Show lobby players count / max player limit
    	var lobbyGroup = searchLobbyGroup(lobbyId, container.lobbyGroupsList);
    	document.getElementById('count').innerHTML = lobbyGroup.count + "/" + lobbyGroup.playerLimit;
    	
    	// Show lobby players
    	listPlayers(lobbyGroup.playersList);
    	
    	// Show lobby owner
    	document.getElementById('owner').innerHTML = lobbyGroup.owner.username;
    	
    	// Start game
    	if (lobbyGroup.full === true && gameStarted === false) {
    		$("#leaveLobbyBtn").removeAttr('href');
    		$("#leaveLobbyBtn").addClass('disabledBtn');
    		startCountDown();
    		gameStarted = true;
    	}
    	
    }
    
    function searchLobbyGroup(lobbyId, lobbyGroupsList){
        for (var i=0; i < lobbyGroupsList.length; i++) {
            if (lobbyGroupsList[i].lobbyId === lobbyId) {
                return lobbyGroupsList[i];
            }
        }
    }
    
    function listPlayers(playerList) {
    	var list = document.getElementById("playersList");
    	$(list).empty(); //remove old player info
    	for(var i = 0; i<playerList.length; i++){
   		 	var item = playerList[i];
    		var elem = document.createElement("li");
    		elem.innerHTML = item.username;

   			list.appendChild(elem);
   		}
    }
    
    function startCountDown() {
    	var counter = 5;
    	var interval = setInterval(function() {
    		// Write down time
    		document.getElementById('chatMessages').innerHTML += '<br />' + counter;
    	    counter--;
    	    // End of timer
    	    if (counter == -1) {
    	        clearInterval(interval);
    	        // Redirect to new page
    	        var href = $('#startGame').attr('href');
      			window.location.href = href; //causes the browser to refresh and load the requested url
    	    }
    	}, 1000);
    }

    function onOpen(event) {
    	// Create object structure
      	var txt = username + " joined the lobby. Say hello!";
      	var chatObj = {
      		'action'	: "joinLobby",
			'sender'	: username,
			'message'	: txt
      	};
     	// Send info thought websocket
      	webSocket.send(JSON.stringify(chatObj));
    }

    function onError(event) {
      	alert(event.data);
    }

    // This function is a 'onclick' trigger (chat SEND button)
    function sendMessage() {
    	// Get input value
      	var txt = document.getElementById('inputMessage').value;
      	// Clean input value
      	document.getElementById('inputMessage').value = "";
      	// Create object structure
      	var chatObj = {
      		'action'	: "chatMessage",
			'sender'	: username,
			'message'	: txt
      	};
     	// Send info thought websocket
      	webSocket.send(JSON.stringify(chatObj));
      	return false;
    }
    
 	// This function is a 'onclick' trigger (LEAVE lobby button)
    function sendLeaveMessage() {
      	// Create object structure
      	var txt = username + " left the lobby. Bye!";
      	var chatObj = {
      		'action'	: "leaveLobby",
			'sender'	: username,
			'message'	: txt
      	};
     	// Send info thought websocket
      	webSocket.send(JSON.stringify(chatObj));
      	return false;
    }
    
</script>


<!-- Actual page content -->
<div class="container" id="lobbyContainer">
	
	<!-- Hidden link to game (triggered when countdown is completed) -->
	<a id="startGame" style="display: none;" href="${pageContext.request.contextPath}/lobby/game?id=${lobby.getId()}"></a>
	
	<!-- Exit lobby button -->
    <div class="my-button" id="leaveBtn">
		<a class="my-button-text" id="leaveLobbyBtn" onclick="sendLeaveMessage()" 
			href="${pageContext.request.contextPath}/lobby/leave?id=${lobby.getId()}">
			<span class ="my-button-span">
				<spring:message code="label.lobbyLeaveButton" />
			</span>
		</a>
	</div>
	
	<!-- LOBBY TITLE -->
	<h1 id="lobbyTitle">
		<spring:message code="label.lobbyTitle" />${lobby.getTitle()}
	</h1>
	
	<!-- Chat -->
	<div id="chat">
	    		
	    <div id="chatMessages">
			<!-- messages print here -->
		</div>
					
		<!-- Chat input -->
	    <div id="chatInput">
		   	<input id="inputMessage" type="text" />
		</div>
				
		<!-- Chat input button -->
		<button type="submit" class="my-button-text" onclick="sendMessage()" id="sendBtn">
				<span class ="my-button-span">
					<spring:message code="label.lobbyChatButton" />
				</span>
		</button>
				
    </div>
    
    <div id="lobby-left">
    		
    	<p>
			<spring:message code="label.lobbyCurrentPlayers" />
		</p>
    	<div id="count"></div>
    	
		<p>
			<spring:message code="label.lobbyLimit" />
		</p>
		<p>
			${lobby.getPlayerLimit()}
		</p>
		
		<p>
	    	<spring:message code="label.lobbyOwner" />
		</p>
		<div id="owner"></div>
		
    </div>
    	
    <div id="lobby-right">
    	
    	<h3>
	    	<spring:message code="label.lobbyPlayers" />
		</h3>
		    
		<ul id="playersList">
		</ul>
	    	
    </div>
	
   
</div>
