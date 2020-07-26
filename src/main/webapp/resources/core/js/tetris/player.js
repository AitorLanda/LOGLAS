/**
 * @brief Player class. Containing moving piece (matrix and position) and score
 */
class Player {
	constructor(tetris) {
		// Pieces drop speeds
		this.DROP_SLOW = 1000;
		this.DROP_FAST = 50;
		this.modifier = 0; // In order to set more speed acording to score
		
		this.events = new Events();
		
		// Tetris instance in order to arena to be available
		this.tetris = tetris;
		this.arena = tetris.arena;
		
		// Timer for pieces drawing refresh (UPDATE)
		this.dropCounter = 0;
		this.dropInterval = this.DROP_SLOW;
		// Piece and score
		this.pos = {x : 0, y : 0};
		this.matrix = null;
		this.score = 0;
		
		this.gameOver = false;
		this.winner = false;
		
		this.resetRandom();
	}
	
	/**
	 * @brief Move player piece by editing the position
	 * 		  Direction : 1 	->
	 * 		  Direction : -1 	<-
	 * 		  When colliding remove this move, extracting direction again 
	 * @param direction
	 * @returns
	 */
	moveRight(direction) {
		this.pos.x = this.pos.x + direction;
		if (this.arena.collides(this)) {
			this.pos.x = this.pos.x - direction;
			return
		}
		this.events.emit('pos', this.pos);
	}
	
	/**
	 * @brief Rotate player piece
	 * 		  After rotation, listen to collision in while loop,
	 * 		  in order to prevent clipping into lateral walls
	 * 		  1. If piece collides, we will increment offset. 
	 * 			 Will look like this when incrementing: 3, -4, 5, -6, ...
	 * @param direction
	 * @returns
	 */
	rotate(direction) {
		const pos = this.pos.x;
		let offset = 1;
		this.rotateMatrix(this.matrix, direction);
		// Listen to collision
		// Every time piece collides horizontally needs to retreat back, so we do sum the offset
		while (this.arena.collides(this)) {
			this.pos.x = this.pos.x + offset;
			offset = -(offset + (offset > 0 ? 1 : -1));
			// If we rotate and collide too much
			if (offset > this.matrix[0].length) {
				// Rotate back
				rotate(this.matrix, -direction);
				this.pos.x = pos;
				return;
			}
		}
		this.events.emit('matrix', this.matrix);
	}
	
	/**
	 * @brief Use to rotate the piece. In order to rotate:
	 * 		  1. Transpose the matrix
	 * 		  2. Reverse the matrix
	 * @param matrix
	 * @param dir
	 * @returns
	 */
	rotateMatrix(matrix, direction) {
		for (let y = 0; y < matrix.length; ++y) {
			for (let x = 0; x < y; ++x) {
				// Transpose (tuple switching)
				[
					matrix[x][y], matrix[y][x]
				] = [
					matrix[y][x], matrix[x][y]
				];
			}
		}
		
		// Check the direction
		if (direction > 0) { // If direction is positive
			matrix.forEach(row => row.reverse());
		} else {
			// Reverse
			matrix.reverse();
		}
	}
	
	/**
	 * @brief Used to spawn new piece on top
	 * 		  Also check if piece is colliding when it spawn, to make it game over
	 */
	resetRandom() {
		const pieces = 'TOLJISZ';
		// Get random letter
		var letter = pieces[pieces.length * Math.random() | 0]; // take the first position one too
		this.matrix = this.createPiece(letter);
		// Put piece at the top
		this.pos.y = 0;
		// Put piece in the middle
		this.pos.x = (this.arena.matrix[0].length / 2 | 0) - (this.matrix[0].length / 2 | 0);
		
		// If piece collides inmediatelly, means it is game over
		if (this.arena.collides(this)) {
			// Set game over
			this.gameOver = true;
			
			// Emit state -> trigger event, in order to send this info (game over)
			this.events.emit('score', this.score);
			
			return;
		}
		// If user score is higher than accumulate, faster drop ( + 10)
		if (this.score - this.modifier > 50) {
			this.DROP_SLOW = this.DROP_SLOW - 50;
		}
		
		this.events.emit('pos', this.pos);
		this.events.emit('matrix', this.matrix);
	}
	
	/**
	 * @brief Used for committing changes to environment
	 * @returns
	 */
	drop() {
		// Move downwards by 1
		this.pos.y++;
		// To prevent the piece to fall to the next drop. Need a delay
		this.dropCounter = 0;
		//  IF colliding
		if (this.arena.collides(this)) {
			this.pos.y--;
			// Copy this piece to arena
			this.arena.merge(this);
			// Create a new piece in the center and top
			this.resetRandom();
			// Check arena state
			this.score = this.score + this.arena.clean();
			// Update player score
			this.events.emit('score', this.score);
			return;
		}
		this.events.emit('pos', this.pos);
	}
	
	/**
	 * @brief Used for doing "drop()" for 1 second interval
	 */
	update(deltaTime) {
		this.dropCounter = this.dropCounter + deltaTime;
		if (this.dropCounter > this.dropInterval) {
			this.drop();
		}
	}
	
	/**
	 * @brief Use to create pieces matrices by letter
	 * @param type
	 * @returns
	 */
	createPiece(type) {
		if (type === 'T') {
			return [
				[0, 0, 0],
				[1, 1, 1],
				[0, 1, 0],
			];
		} else if (type === 'O') {
			return [
				[2, 2],
				[2, 2],
			];
		} else if (type === 'L') {
			return [
				[0, 3, 0],
				[0, 3, 0],
				[0, 3, 3],
			];
		} else if (type === 'J') {
			return [
				[0, 4, 0],
				[0, 4, 0],
				[4, 4, 0],
			];
		} else if (type === 'I') {
			return [
				[0, 5, 0, 0],
				[0, 5, 0, 0],
				[0, 5, 0, 0],
				[0, 5, 0, 0],
			];
		} else if (type === 'S') {
			return [
				[0, 6, 6],
				[6, 6, 0],
				[0, 0, 0],
			];
		} else if (type === 'Z') {
			return [
				[7, 7, 0],
				[0, 7, 7],
				[0, 0, 0],
			];
		}
	}
	
}
