 <%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript" charset="UTF-8">

	// Retrieve lobby ID that we just joined
	var lobbyId = "${lobby.getId()}";
	
	//Retrieve user ID
	var userId = "${user.getId()}";
	
	// Create web socket with lobbyId attached
    var webSocket = new WebSocket('ws://' + document.location.host + '/POPBL5/lobbychat/' + lobbyId);
	
	
    webSocket.onerror = function(event) {
      	onError(event)
    };

    webSocket.onopen = function(event) {
      	onOpen(event)
    };

    webSocket.onmessage = function(event) {
      	onMessage(event)
    };
    

	// parse variables to int
	lobbyId = parseInt(lobbyId);
	userId = parseInt(userId);
	
	
	var container;
	var lobbyGroup;
	var playerList;
    /**
      * Data retrieved by websocket
      * event.data contains the data that Endpoint broadcasteds
      */
    function onMessage(event) {
    	// Since we have a JSON format data, we have a known object structure.
    	container = JSON.parse(event.data);
    	
    	// Show lobby players (SCORE-BOARD)
    	lobbyGroup = searchLobbyGroup(lobbyId, container["lobbyGroupsList"]);
    	playerList = lobbyGroup["playersList"];
    	listRankingScoreboard(playerList);
    }
    
    /**
     * Search for this lobby group info
     * @param {array} items of lobbies containing info and players inside
     * @param {number} containing the id of the actual lobby
     */
    function searchLobbyGroup(lobbyId, lobbyGroupsList){
        for (var i=0; i < lobbyGroupsList.length; i++) {
            if (lobbyGroupsList[i]["lobbyId"] === parseInt(lobbyId, 10)) {
                return lobbyGroupsList[i];
            }
        }
    }

    function onOpen(event) {    	
    }

    function onError(event) {
      	alert("Could not connect to server");
    }
    
    /**
     *	Creates a table as final ranking (similar to players Scoreboard)
     *	@param {Array} containing player object items
     */
    function listRankingScoreboard(playerList) {
    	// Remove old info
    	$("#scoreboard").empty();
    	
    	// Add head
    	var head = '<thead> <tr>';
    	head += '<th> Ranking </th>';
    	head += '<th> Player </th>';
    	head += '<th> Score </th>';
    	head += '</tr> </thead>';
    	$("#scoreboard").append(head);
    	// Add boddy
    	var body = '<tbody>';
    	var place = 1;
    	playerList.forEach(function(obj){ // For each row
    		// Each column
    		var row = '<tr>' + '<td>' + place + '</td>';
    		row += '<td>' + obj["username"] + '</td>';
    		row += '<td>' + obj["score"] + '</td>';
    		body += row + '</tr>';
    		// Increment podium ranking place
    		place ++;
    	});
    	// Append generated rows/columns to table
    	$("#scoreboard").append(body+'</tbody>');
    }
     
    /**
      *	Sorts the players list from higest score to lowest score
      *	@param {Array} containing player object items
      */
    function sortPlayerListByScore(playerList) {
    	playerList = playerList.sort(function(a, b) {
    		return b.score - a.score;
    	});
   		return playerList;
    }
    
    /**
      * First exit lobbyGroup then show leave button X
      * Trigger leave button
      */
    function exitLobbyAndShowLeaveButton() {
    	// Redirect to new page (this game lobbies list)
        var href = $('#leaveLobbyBtn').attr('href');
		window.location.href = href; //causes the browser to refresh and load the requested url
    }
    
    /**
     *	Do on page load. Start game (countdown)
     */
    $(document).ready(listPlayersScoreboard());
    
</script>

<div class="container">
	
	<div class="row">		
		
		<div class="col-sm">
    		<h3>
	    		Score-board:
		    </h3>
		    <table id="scoreboard"></table>
   		</div>
		
	</div>
	
	<div class="row">
		
		<!-- Hidden link to game lobbies exit -->
		<a id="leaveLobbyBtn" href="${pageContext.request.contextPath}/dashboard?id=${lobby.getGameId()}"></a>
	
	</div>
   
</div>
