import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
 *	This class handles the UI, namely the main game interface.
 */

public class SniperGame extends Application 
{
	
	private ArrayList<Level> levels;
	public static final int LEVEL_WIDTH = 1000;
	public static final int LEVEL_HEIGHT = 600;

	
//	public static void main(String args[]) {
//		launch(); // I have failed to move this into its own driver classes. You guys are welcome to try.
//	}

	@Override
	public void start(Stage homeScreen) {
		loadLevels();
		
		homeScreen.setTitle("Sniper Game Alpha"); // TODO This Name is tentative. Need a cooler one.
		homeScreen.setResizable(false);
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 250); // The Golden Ratio
		
		Button startGameButton = new Button();
		startGameButton.setPrefSize(50, 50);
		startGameButton.setText("Start");
		startGameButton.setOnAction(new startGameHandler());
		root.setCenter(startGameButton);
		
		homeScreen.setScene(scene);
		homeScreen.show();
	}
	
	private void loadLevels() {
		levels = new ArrayList<Level>();
		// Note: there cannot be two levels with the same level numbers
		levels.add(new LevelTutorial(0));
		
		Collections.sort(levels);
	}
	
	private void startLevel(int lvlNum) {
		Level currLevel = levels.get(lvlNum);
		Stage lvlScreen = new Stage();
		lvlScreen.setTitle(currLevel.getName());
		lvlScreen.setResizable(false);
		
		Scene scene = new Scene(currLevel, LEVEL_WIDTH, LEVEL_HEIGHT);
		
		Image background = new Image("file:sprites/background.jpg");
		BackgroundImage myBI	= new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		currLevel.setBackground(new Background(myBI));
		
		//Image img = new Image("file:sprites/scope2.gif");
		scene.setCursor(Cursor.CROSSHAIR);
		//scene.setCursor(new ImageCursor(img,img.getWidth()/2,img.getHeight()/2));
		
		lvlScreen.setScene(scene);
		lvlScreen.show();
		
		currLevel.start();
	}
	
	private class startGameHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (levels.size() != 0) {
				startLevel(0);
			} else {
				System.out.println("ERROR: no level loaded.");
			}
		}
		
	}

}
