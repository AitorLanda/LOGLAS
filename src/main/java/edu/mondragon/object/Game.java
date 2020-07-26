package edu.mondragon.object;
/**
 * @brief	Games class.
 * 			Use this enclapsulation to form a Game which will be used to load into memory
 * @author 	Loredi
 *
 */
public class Game {
	
	

	private int id;
	private String title;
	private String description;
	private String img;
	private String alt;

	
	public Game() {
	}
	
	public Game(int id, String title, String description, String img, String alt) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.img = img;
		this.alt = alt;
	}

	/**
	 * @brief	Use this method to retrieve the game title when selecting a game type
	 * 			This information is hardcoded, since it will be always on memory
	 * @return
	 */
	public String getTitle(int gameId) {
		String gameTitle = "undefined";
		if (gameId == 1) {
			gameTitle = "True of false";
		} else if (gameId == 2) {
			gameTitle = "Multiple choice";
		} else if (gameId == 3) {
			gameTitle = "Mixed questions";
		} else if (gameId == 4) {
			gameTitle = "Drag and drop";
		} else if (gameId == 5) {
			gameTitle = "Tetris";
		}
		return gameTitle;
	}
	
	/*---GETTERS-SETTERS----*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
	
	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + ", description=" + description + ", img=" + img + ", alt=" + alt
				+ "]\n";
	}
}
