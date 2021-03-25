package model.gameobjects;

public class Blade {

	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private boolean swiped;
	
	public Blade() {
		setStartPosition(0, 0);
		setEndPosition(0, 0);
	}
	
	public void setStartPosition(double startX, double startY) {
		this.startX = startX;
		this.startY = startY;
	}
	public void setEndPosition(double endX, double endY) {
		this.endX = endX;
		this.endY = endY;
	}
	public boolean isSwiped() {
		if ((startX != endX) && (startY != endY)) {
			return swiped;
		} else {
			return false;
		}
	}
	public void setSwiped(boolean swiped) {
		this.swiped = swiped;
	}

}
