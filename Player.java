package model.player;


//Singleton Class
public class Player {
	private static Player player;
	private int lives;
	private int score;
	
	private Player() {
		this.lives = 3;
		this.score = 0;
	}
	public static Player getInstance() {
		if(player == null)
			return player = new Player();
		return player;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	public void subtractLife() {
		this.lives--;
	}
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
