package bomba;

import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Bomb extends Pane{
	
	Circle bomb;
	private Random rd;
	private int xBombPos;
	private int yBombPos;
	private int timeLeft;
	private Text text;

	public Bomb() {
		bomb = new Circle(20, Color.INDIANRED);
		
		rd = new Random();
		xBombPos = (rd.nextInt(9) * 50) + 25;
		yBombPos = (rd.nextInt(9) * 50) + 25;
		bomb.setCenterX(xBombPos);
		bomb.setCenterY(yBombPos);
		
		timeLeft = 3;
		
		text = new Text(xBombPos - 4, yBombPos + 4, Integer.toString(timeLeft));
		text.setFill(Color.WHITE);
		text.setScaleX(2);
		text.setScaleY(2);
		
		getChildren().addAll(bomb, text);
	}
	
	public void decreaseTimeLeft() {
		if (checkTimeOver() == false) {
			timeLeft--;
			text.setText(Integer.toString(timeLeft));
		}
	}
	
	public boolean checkTimeOver() {
		return timeLeft <= 0;
	}

	public int getxBombPos() {
		return xBombPos;
	}

	public int getyBombPos() {
		return yBombPos;
	}

	public boolean isInArea(int x, int y) {
		return 	(xBombPos >= x) && (xBombPos <= x + 150) &&
				(yBombPos >= y) && (yBombPos <= y + 150);
	}
	
}
