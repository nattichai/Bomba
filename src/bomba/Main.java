package bomba;

import javafx.animation.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.*;

public class Main extends Application{
	
	public static Bomb[] bombs;
	public static int nBomb = 0;
	public static Explosion[] explosions;
	public static int nExplosion = 0;
	public static boolean isPause;
	
	private VBox root;
	private static Pane top;
	private static Rectangle overlay;
	private static Text overlayText;
	private HBox bottom;
	private Button restartBtn;
	private static int score;
	private static Text scoreTxt;
	private Map map;
	private static Player player;
	private Scene scene;
	private static Timeline timeline;
	private static Text diedText;
	private static int speed;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		initialGame(primaryStage);
		
		primaryStage.setOnCloseRequest(e -> {
			if (timeline != null)
				timeline.stop();
		});
		primaryStage.show();
	}
	
	private void initialGame(Stage primaryStage) {
		
		primaryStage.setTitle("BOMBA!");
		
		score = 0;
		speed = 1000;
		isPause = true;
		root = new VBox();
			root.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, null, null)));
		top = new Pane();
			top.setPrefSize(450, 450);
		overlay = new Rectangle(450, 450, Color.WHITE);
			overlay.setOpacity(0.6);
		overlayText = new Text("Press ENTER to start");
			overlayText.setX(125);
			overlayText.setY(231.5);
			overlayText.setFont(new Font(20));
		bottom = new HBox();
			bottom.setPrefSize(450, 50);
			bottom.setPadding(new Insets(10));
			bottom.setSpacing(100);
			bottom.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
		restartBtn = new Button("Restart");
		scoreTxt = new Text("Score : " + Integer.toString(score));
		map = new Map();
		player = new Player(225, 225);
		scene = new Scene(root, 450, 500);
		bombs = new Bomb[1000];
		explosions = new Explosion[1000];
		
		top.getChildren().addAll(map, player, overlay, overlayText);
		bottom.getChildren().addAll(restartBtn, scoreTxt);
		root.getChildren().addAll(top, bottom);
		
		primaryStage.setScene(scene);
		
		scene.setOnKeyPressed(e -> {player.playerController(e);});
		
		restartBtn.setOnAction(e -> {
			restart(primaryStage);
		});
	}
	
	private void restart(Stage primaryStage) {
		root = null;
		top = null;
		bottom = null;
		map = null;
		player = null;
		scene = null;
		bombs = null;
		nBomb = 0;
		explosions = null;
		nExplosion = 0;
		diedText = null;
		timeline.stop();
		initialGame(primaryStage);		
	}

	public static void runTime() {
		top.getChildren().removeAll(overlay, overlayText);
		timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> {
			checkExplosion();
			checkBomb();
			createBomb(4);
			scoreCal();
			timeline.stop();
			if (player.isDead() == false) {
				runTime();
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	private static void scoreCal() {
		score += 100;
		speed -= 10;
		scoreTxt.setText("Score : " + Integer.toString(score));
	}

	private static void checkExplosion() {
		for (int i = 0; i < nExplosion; ++i) {
			if (explosions[i] != null) {
				explosions[i].decreaseDuration();
				if (explosions[i].checkDurationOver()) {
					top.getChildren().remove(explosions[i]);
					explosions[i] = null;
				}
			}
		}
	}
	
	private static void checkBomb() {
		for (int i = 0; i < nBomb; ++i) {
			if (bombs[i] != null) {
				bombs[i].decreaseTimeLeft();
				if (bombs[i].checkTimeOver()) {
					explosions[nExplosion] = new Explosion(bombs[i].getxBombPos() - 75, bombs[i].getyBombPos() - 75);
					clearBomb(bombs[i].getxBombPos() - 75, bombs[i].getyBombPos() - 75);
					top.getChildren().add(explosions[nExplosion++]);
					player.checkAllBombs();
				}
			}
		}
	}
	
	private static void clearBomb(int x, int y) {
		for (int i = 0; i < nBomb; ++i) {
			if (bombs[i] != null) {
				if (bombs[i].isInArea(x, y)) {
					top.getChildren().remove(bombs[i]);
					bombs[i] = null;
				}
			}
		}
	}

	private static void createBomb(int n) {
		while (n-- != 0 && player.isDead() == false) {
			bombs[nBomb] = new Bomb();
			top.getChildren().add(bombs[nBomb++]);
		}
	}
	
	public static void dead() {
		timeline.stop();
		player.dead();
		diedText = new Text("You Died!!");
		diedText.setX(125);
		diedText.setY(225);
		diedText.setFont(new Font(40));
		top.getChildren().addAll(overlay, diedText);
	}
}
