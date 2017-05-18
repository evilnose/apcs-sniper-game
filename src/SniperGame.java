import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
 *	This class handles the UI, namely the main game interface.
 */

public class SniperGame extends Application 
{
	
	private ArrayList<Level> levels;
	public static final int LEVEL_WIDTH = 1000;
	public static final int LEVEL_HEIGHT = 600;

	
	public static void main(String args[]) {
		launch(); 
	}

	@Override
	public void start(Stage homeScreen) {
		loadLevels();
		
		homeScreen.setTitle("Sniper Game Alpha"); // TODO This Name is tentative. Need a cooler one.
		homeScreen.setResizable(false);
		
		
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 500);
		
		BackgroundImage myBI = new BackgroundImage(new Image("file:sprites/start_background.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		root.setBackground(new Background(myBI));
		
		Button startGameButton = new Button();
		startGameButton.setText("Start");
		startGameButton.setFont(new Font("Monospaced Bold",30));
		startGameButton.setStyle("-fx-background-color: transparent;");
		startGameButton.setOnAction(new startGameHandler());
		
		BorderPane.setMargin(startGameButton,new Insets(100,250,50,50));
		root.setRight(startGameButton);
		
		homeScreen.setScene(scene);
		homeScreen.show();
	}
	
	private void loadLevels() {
		levels = new ArrayList<Level>();
		// Note: there cannot be two levels with the same level numbers
		levels.add(new LevelTutorial(0));
		levels.add(new LevelOne(1));
		levels.add(new LevelTwo(2));
		levels.add(new LevelThree(3));
	}
	
	private void startLevel(int lvlNum) {
		Level currLevel = levels.get(lvlNum);
		Stage lvlScreen = new Stage();
		lvlScreen.setTitle(currLevel.getName());
		lvlScreen.setResizable(false);
		
		Scene scene = new Scene(currLevel, LEVEL_WIDTH, LEVEL_HEIGHT);
		lvlScreen.setScene(scene);
		lvlScreen.show();
		
		currLevel.activateDefaultBackground();
		currLevel.start();
	}
	
	private class startGameHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (levels.size() != 0) {
//				startLevel(0);
//				startLevel(1);
//				startLevel(2);
				startLevel(3);
			} else {
				System.out.println("ERROR: no level loaded.");
			}
		}
		
	}

}
