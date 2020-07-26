/**
 * @brief This class contains the visual work.
 * 		  Updating canvas 
 */
class Tetris {
	constructor(element, userId, lobbyId) {
		this.userId = userId;
		this.lobbyId = lobbyId;
		
		this.element = element;
		this.canvas = element.querySelector('canvas');
		// Need to get the context in order to draw on it
		this.context = this.canvas.getContext('2d');
		// Scale up the scale so we draw pixel scalated (20 times)
		this.context.scale(20, 20);
		
		
		// Create 12 x 20 matrix which holds the map for the arena (in order to resolve game mechanics)
		this.arena = new Arena(12, 20);

		//Player. Used to move pieces. We need an instance of arena inside player, so we give THIS Tetris scope
		this.player = new Player(this);
		
		// Use Event listener even to update our own score
		this.player.events.listen('score', score => {
			this.updateScore(score);
		});
		
		
		// PIECES
		// Colors
		this.colors = [
			null,
			'purple',
			'yellow',
			'orange',
			'blue',
			'cyan',
			'green',
			'red',
		];
		
		/**
		 * @brief Refreshing tick. Updates the canvas
		 * @returns
		 */
		let lastTime = 0;
		this.update = (time = 0) => {
			
			// Time the piece drop for each second
			const deltaTime = time - lastTime;
			lastTime = time;
			
			this.player.update(deltaTime);
			
			// Repaint
			if (this.player.gameOver === false) {
				this.draw();
				requestAnimationFrame(this.update);
			}
		}
		
		// Init score
		this.updateScore(0);
	}
	
	/**
	 * @brief Draw all elements in canvas
	 * @returns
	 */
	draw() {
		// Clean canvas
		// Background of the canvas
		this.context.fillStyle = '#000';
		this.context.fillRect(0, 0, this.canvas.width, this.canvas.height);
		
		// Draw arena in the origin position (arena is always static)
		this.drawMatrix(this.arena.matrix, {x: 0, y: 0})
		// Draw player (piece)
		this.drawMatrix(this.player.matrix, this.player.pos);
	}

	/**
	 * @brief Draws a piece in the offset (x, y)
	 * @param matrix : piece
	 * @param offset
	 * @returns
	 */
	drawMatrix(matrix, offset) {
		// Draw pieces
		matrix.forEach((row, y) => {
			row.forEach((value, x) => {
				// We need to skip zeroes (we don't want to draw them)
				if (value !== 0) {
					this.context.fillStyle = this.colors[value];
					this.context.fillRect(x + offset.x, y + offset.y, 1, 1);
				}
				
			});
		});
	}
	
	run() {
		this.update();
	}
	
	/**
	 * @brief Updates the score with "score" class
	 */
	updateScore(score) {
		this.element.querySelector('.score').innerHTML = score;
	}
	

	serialize()
    {
        return {
            arena: {
                matrix: this.arena.matrix,
            },
            player: {
                matrix: this.player.matrix,
                pos: this.player.pos,
                score: this.player.score,
            },
        };
    }

    unserialize(state)
    {
        this.arena.matrix = this.parseInt(state.arena.matrix);
        this.player.matrix = this.parseInt(state.player.matrix);
        this.player.pos.x = state.player.pos.x.num;
        this.player.pos.y = state.player.pos.y.num;
        this.player.score = state.player.score.num;
        
        this.updateScore(this.player.score);
        this.draw();
    }
    
    parseInt(matrix) {
    	
    	matrix.forEach((row, y) => {
			row.forEach((value, x) => {
				// We need to skip zeroes (we don't want to draw them)
				matrix[y][x] = value.num;
				
			});
		});
    	return matrix;
    }
    
}