/**
 * @brief Contoller class for tetris. Will manage multiple players and views
 */
class TetrisController {
	constructor(document) {
		this.document = document;
		
		this.view = this.document.getElementById('gameView');
		
		// Update: from array to Set
		this.gameInstances = new Set;
	}
	
	/**
	 * @brief Create new instance of the game and add it to the set and the html
	 */
	createPlayer(userId, lobbyId) {
		console.log("Player instance created: " + userId);
		// Create new Canvas and score div
		const element = document.createElement("div");
		element.className = "player";
		
		const elementScore = document.createElement("div");
		elementScore.className = "score";

		const elementCanvas = document.createElement("canvas");
		elementCanvas.setAttribute("id", "tetris");
		elementCanvas.setAttribute("width", "240");
		elementCanvas.setAttribute("height", "400");
		
		element.appendChild(elementScore);
		element.appendChild(elementCanvas);
		
		// Start new instance of tetris (with all the needed classes inside)
		const tetris = new Tetris(element, userId, lobbyId);	// element has the canvas inside
		this.gameInstances.add(tetris);

		// Append to document
		this.view.appendChild(tetris.element);
		
		return tetris;
	}
	
	removePlayer(tetris) {
		this.gameInstances.delete(tetris);
		this.view.removeChild(tetris.element);
	}
	
	removeAllPlayers() {
		var child = this.view.lastElementChild;  
	    while (child) { 
	    	this.view.removeChild(child); 
	        child = this.view.lastElementChild; 
	    }
	}
}