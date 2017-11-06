package bomba;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends Circle{
	
	private boolean isDead;
	private int xPlayerPos;
	private int yPlayerPos;
	
	public Player(int x, int y) {
		super(20, Color.CORNFLOWERBLUE);
		isDead = false;
		xPlayerPos = x;
		yPlayerPos = y;
		setPlayerPostion();
	}
	
	public void setPlayerPostion() {
		if (xPlayerPos < 0)
			xPlayerPos = 25;
		else if(xPlayerPos > 425)
			xPlayerPos = 425;
		if (yPlayerPos < 0)
			yPlayerPos = 25;
		else if(yPlayerPos > 425)
			yPlayerPos = 425;
		checkAllBombs();
		setCenterX(xPlayerPos);
		setCenterY(yPlayerPos);
	}
	
	public void playerController(KeyEvent e) {
		if (isDead == false) {
			if (e.getCode() == KeyCode.UP && Main.isPause == false) {
				yPlayerPos -= 50;
				setPlayerPostion();
			} else if (e.getCode() == KeyCode.DOWN && Main.isPause == false) {
				yPlayerPos += 50;
				setPlayerPostion();
			} else if (e.getCode() == KeyCode.LEFT && Main.isPause == false) {
				xPlayerPos -= 50;
				setPlayerPostion();
			} else if (e.getCode() == KeyCode.RIGHT && Main.isPause == false) {
				xPlayerPos += 50;
				setPlayerPostion();
			} else if (e.getCode() == KeyCode.ENTER && Main.isPause == true) {
				Main.isPause = false;
				Main.runTime();
			}
		}
	}
	
	public void checkAllBombs() {
		for (int i = 0; i < Main.nExplosion; ++i) {
			if (Main.explosions[i] != null) {
				if (isInDanger(Main.explosions[i].getxPos(), Main.explosions[i].getyPos())) {
					Main.dead();
				}
			}
		}
	}
	
	public boolean isInDanger(int x, int y) {
		return 	(xPlayerPos >= x) && (xPlayerPos <= x + 150) &&
				(yPlayerPos >= y) && (yPlayerPos <= y + 150);
	}

	public void dead() {
		isDead = true;
	}

	public void revive() {
		isDead = false;
	}

	public boolean isDead() {
		return isDead;
	}
}
