/**
 * @brief Events class. Use this to update information to other players in order to send it through web socket
 */
class Events {
	constructor() {
		this.listeners = new Set;
	}
	
	/**
	 * @brief 
	 */
	listen(name, callback) {
		this.listeners.add( {
			name,
			callback,
		});
	}
	
	/**
	 * @brief 
	 */
	emit(name, ...data) {
		this.listeners.forEach(listener => {
			if (listener.name === name) {
				listener.callback(...data);
			}
		});
	}
	
}
