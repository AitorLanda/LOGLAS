/**
 * @brief Tetris Connection Controller.
 * 		  For websocket connetion
 * 		  Save peers in order to differentiate different players' games from each others
 */
class TetrisConnectionController {
	constructor(tetrisController) {
		this.connection = null;
		this.peers = new Map;
		
		this.tetrisController = tetrisController;
		this.localTetrisGame = [...tetrisController.gameInstances][0];
	}
	
	connect(address) {
		// Connect to the the socket ('ws:../lobbychat/lobbyId')
		this.connection = new WebSocket(address);
		
		// On connection opened
		this.connection.addEventListener('open', () => {
			this.eventsBroadcast();
		});
		
		// On message recieved
		this.connection.addEventListener('message', event => {
			this.receive(event.data);
		});
	}
	
	/**
	 * @brief Use to send data through web socket
	 */
	send(data) {
		const string = JSON.stringify(data);
		this.connection.send(string);
	}
	
	/**
	 * @brief When receiving broadcasted message from server
	 */
	receive(data) {
		const string = JSON.parse(data);
		console.log(string.action);
		// Score board updates
		var lobbyGroup = this.searchLobbyGroup(lobbyId, string["lobbyGroupsList"]);
    	var playerList = lobbyGroup["playersList"];
    	// Rearrange array by score
		playerList = this.sortPlayerListByScore(playerList);
    	this.listRankingScoreboard(playerList);
		
		// Refresh instances depending action
		this.updateController(string);
	}
	
	/**
	 * @brief Use this to check if the data we collected comes from a new player
	 * 		  If so, we save the info in peers map
	 * 		  Then refresh state into that instance
	 */
	updateController(string) {
		
        // Check if it is a new player (ADD AND UPDATE OTHER PLAYER'S INSTANCE, using userId)
		// Use gameOver to check if player is out (don't add again)
		if (!this.peers.has(string.userId) && string.userId !== this.localTetrisGame.userId 
				&& string.userId !== 0  && string.gameOver === false) {
            const tetris = this.tetrisController.createPlayer(string.userId);
            tetris.unserialize(string.gameState);
            this.peers.set(string.userId, tetris);
        } else {
        	// Private user instance (UPDATE OTHER PLAYER'S INSTANCE, using userId)
        	if (string.action === "tetrisGameUpdate") {
        		// Search for this user
            	[...this.peers.entries()].forEach(([userId, tetris]) => {
                    if (string.userId === userId) {
            			tetris.unserialize(string.gameState);
                    }
                });
            // Handle game over
        	} else if (string.action === "tetrisGameOver" && string.gameOver === true && this.localTetrisGame.player.gameOver === false) {
        		// DELETE PLAYER'S INSTANCE FROM PEERS
        		[...this.peers.entries()].forEach(([userId, tetris]) => {
                    if (string.userId !== this.localTetrisGame.userId) { //-> UNPROBALE TO BE FALSE
            			this.peers.delete(string.userId);
            			
            			// If there are no more players playing along, set me as winner
                    	// (IF I AM A LOSER WATCHING OTHERS, I WILL ALWAYS HAVE MORE THAN 0 PEERS)
                		if (this.peers.size === 0) {
                			console.log(this.peers);
                			this.localTetrisGame.player.winner = true;
                		}
                		
                    }
                });
        	 	
        	}
        	// Handle end of game
        	if (string.action === "gameWinner") {
        		// Clean canvas instances
        		this.tetrisController.removeAllPlayers();
        		setTimeout(function(){
            		// REDIRECT TO WHATEVER href OF 'leave button'
        			var href = document.getElementById('leaveLobbyBtn').getAttribute('href');
        			window.location.href = href; //causes the browser to refresh and load the requested url
            		
            	}, 5000);
        	}
        	
        }
        
	}
	
	/**
	 * @brief To broadcast events that happened in local game
	 */
	eventsBroadcast() {
		const local = this.localTetrisGame;
		
		// To notify player state (moves)
		const player = local.player;
		// Listen to all these event types
		['pos', 'matrix', 'score'].forEach(property => {
			// for every property value
			player.events.listen(property, value => {
				// When this player loses
				var act = "gameWinner"
				if (this.localTetrisGame.player.gameOver === true && this.localTetrisGame.player.winner === true) {
					console.log("sending winner");
					act = "gameWinner";
				} else if (this.localTetrisGame.player.gameOver === true ) {
					act = "tetrisGameOver";
				} else { // Otherwise: update
					act = "tetrisGameUpdate";
				}
				
				this.send({
					action: act,
					userId: this.localTetrisGame.userId, 			// Player identified (whose is the info)
					lobbyId: this.localTetrisGame.lobbyId,			// Lobby id (to set up info lobby wise in memory)
					state: this.localTetrisGame.serialize(),		// Game state (to show other players my current state)
					score: this.localTetrisGame.player.score,		// Score info
					gameOver: this.localTetrisGame.player.gameOver, // IN ORDER TO CHECK IF IS NEW PLAYER OR NOT WHEN I ... 
																	// ... DELETE FROM 'PEERS'. TO PREVENT RE-ADDING TO PEERS
				});
				
			});
		});
		
	}
	
	/**
     *	Creates a table as final ranking (similar to players Scoreboard)
     *	@param {Array} containing player object items
     */
    listRankingScoreboard(playerList) {
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
     * Search for this lobby group info
     * @param {array} items of lobbies containing info and players inside
     * @param {number} containing the id of the actual lobby
     */
    searchLobbyGroup(lobbyId, lobbyGroupsList){
        for (var i=0; i < lobbyGroupsList.length; i++) {
            if (lobbyGroupsList[i]["lobbyId"] === parseInt(lobbyId, 10)) {
                return lobbyGroupsList[i];
            }
        }
    }
    
    /**
     *	Sorts the players list from higest score to lowest score
     *	@param {Array} containing player object items
     */
    sortPlayerListByScore(playerList) {
    	playerList = playerList.sort(function(a, b) {
    		return b.score - a.score;
    	});
  		return playerList;
    }
	
	
}