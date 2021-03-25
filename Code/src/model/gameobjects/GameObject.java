package model.gameobjects;

public abstract class GameObject {
	protected int type;
	protected double positionX;
	protected double positionY;
	protected double velocityX;
	protected double velocityY;
	protected int size;
	protected int points;
	protected boolean hit;
	
	public GameObject() {
		size = 0;
		positionX = 0;
		positionY = 0;
		velocityX = 0;
		velocityY = 0;
		hit = false;
	}
	
	public int getSize() {
		return this.size;
	}
	public int getPoints() {
		return this.points;
	}
	public void setPosition(double x, double y) {
		this.positionX = (double) x;
		this.positionY = (double) y;
	}
	public void setVelocity(int x, int y) {
		this.velocityX = x;
		this.velocityY = y;
	}
	public int getX() {
		return (int) this.positionX;
	}
	public int getY() {
		return (int) this.positionY;
	}
	public int getType() {
		return this.type;
	}
	public boolean insideBounds(double x, double y) {
		if ((x >= (int) this.positionX) && (x <= (int) (this.positionX + size)) && (y >= (int) this.positionY)
				&& (y <= (int) (this.positionY + size))) {
			return true;
		} else {
			return false;
		}
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	
	
}
