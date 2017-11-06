package bomba;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Explosion extends Rectangle{
	
	private int duration;
	private int xPos;
	private int yPos;

	public Explosion(int x, int y) {
		super(x, y, 150, 150);
		xPos = x;
		yPos = y;
		setFill(Color.LIGHTCORAL);
		duration = 1;
	}
	
	public void decreaseDuration() {
		duration--;
	}
	
	public boolean checkDurationOver() {
		return duration <= 0;
	}
	
	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

}
