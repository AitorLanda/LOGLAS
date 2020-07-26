/**
 * @brief Arena class. Containing the pieces that fell down (starting big matrix filled by 0)
 */
class Arena {
	constructor(width, height) {
		const matrix = [];
		// While height is no 0, decrease height by 1
		while (height--) {
			matrix.push(new Array(width).fill(0));
		}
		this.matrix = matrix;
		
		// Add events listener
		this.events = new Events;
	}
	
	/**
	 * @brief Check if player piece collides with any arena's pieces (1s)
	 * @param arena
	 * @param player
	 * @returns
	 */
	collides(player) {
		// Get player position using tupler asigner
		const [m, o] = [player.matrix, player.pos];
		// Checking for the player
		for (let y = 0; y < m.length; ++y) {
			for (let x = 0; x < m[y].length; ++x) {
				// Player bit mustn't be 0
				if (m[y][x] !== 0 && 
						(this.matrix[y + o.y] && this.matrix[y + o.y][x + o.x]) !== 0) { // Arena row and column mustn't be 0
					// Collided
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @brief Copies the piece (player) into the arena in the position with 1s.
	 * 		  This way we stick the piece to the "place" (the position of the arena)
	 * @param arena
	 * @param player
	 * @returns
	 */
	merge(player) {
		player.matrix.forEach((row, y) => {
			row.forEach((value, x) => {
				if (value !== 0) {
					this.matrix[y + player.pos.y][x + player.pos.x] = value;
				}
			});
		});
		this.events.emit('matrix', this.matrix);
	}
	
	/**
	 * @brief Check if there are rows than can be scored in the arena (mechanics)
	 * @returns
	 */
	clean() {
		let rowCount = 1;
		let score = 0;
		// Iterate from bottom to top
		outer: for (let y = this.matrix.length - 1; y > 0; --y) {
			for (let x = 0; x < this.matrix[y].length; ++x) {
				// Check if we continue without cleaning, skipping actual row's next indexes right to the next row (outer)
				if (this.matrix[y][x] === 0) {
					continue outer;
				}
			}
			
			// Remove row from arena
			// Splice returns all the rows from array
			const row = this.matrix.splice(y, 1)[0].fill(0); // Fill the row with zeroes
			this.matrix.unshift(row); // put the row on top of the arena, so we aren't shrinking the arena ourselves
			++y;
			
			//operating with global player
			score = score + rowCount * 10;
			rowCount = rowCount * 2;
		}
		this.events.emit('matrix', this.matrix);
		return score;
	}
	
	/**
	 * @brief Use to clear the whole arena (when winning or losing)
	 */
	clear() {
		this.matrix.forEach(row => row.fill(0));
		this.events.emit('matrix', this.matrix);
	}
	
}
