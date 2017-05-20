import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	private static ArrayList<Level> levels;
	public static final int LEVEL_WIDTH = 1000;
	public static final int LEVEL_HEIGHT = 600;
	private static Stage lvlScreen;
	private static Scene scene;
	private static Level currLevel;
	private static ArrayList<Boolean> levelsPassed;
	
	public static void main(String args[]) {
		launch(); 
	}

	@Override
	public void start(Stage homeScreen) 
	{
		loadLevels();
		fillLevelsPassed();
		
		homeScreen.setTitle("Sniper Game Alpha"); // TODO This Name is tentative. Need a cooler one.
		homeScreen.setResizable(false);
		
		
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 500);
		
		BackgroundImage myBI = new BackgroundImage(new Image("file:sprites/backgrounds/start_background.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
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
	
	private void loadLevels()
	{
		levels = new ArrayList<Level>();
		// Note: there cannot be two levels with the same level numbers
		levels.add(new LevelOne(1));
		levels.add(new LevelTwo(2));
		levels.add(new LevelThree(3));
	}
	
	public static void startLevel(int lvlNum) 
	{
		currLevel = levels.get(lvlNum);
		if (currLevel.isStarted()) {
			currLevel.stop();
			restart();
		}
		
		if (lvlScreen != null) {
			lvlScreen.close();
		}
		lvlScreen = new Stage();
		lvlScreen.setTitle(currLevel.getName());
		lvlScreen.setResizable(false);
		
		if (scene == null)
			scene = new Scene(currLevel,LEVEL_WIDTH,LEVEL_HEIGHT);
		else
			scene.setRoot(currLevel);
		
		lvlScreen.setScene(scene);
		lvlScreen.show();
		
		currLevel.activateDefaultBackground();
		currLevel.start();
	}
	
	private static void restart() {
		for (Level lvl : levels) {
			if (lvl == currLevel) {
				String className = currLevel.getClass().getName();
				System.out.println(className);
				int levelNum = currLevel.getLevelNumber();
				switch(className) {
				case "LevelTutorial": currLevel = new LevelTutorial(levelNum);
				break;	
				case "LevelOne": currLevel = new LevelOne(levelNum);
				break;	
				case "LevelTwo": currLevel = new LevelTwo(levelNum);
				break;
				case "LevelThree" : currLevel = new LevelThree(levelNum);
				break;
				}
			}
		}
	}

	private void fillLevelsPassed() 
	{
		levelsPassed = new ArrayList<Boolean>();
		File f = new File("level_info.txt");
		try
		{
			Scanner s = new Scanner(f);
			while(s.hasNextInt())
			{
				boolean b = s.nextInt()==0?false:true;
				levelsPassed.add(b);
			}
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setLevelPassed(int levelNum, boolean passed)
	{
		levelsPassed.set(levelNum,passed);
	}
	
	private void openMap() 
	{
		Map map =  new Map(levels,levelsPassed);
		Stage mapScreen = new Stage();
		mapScreen.setResizable(true);
		
		Scene scene = new Scene(map,496,750);
		mapScreen.setScene(scene);
		mapScreen.show();
		map.activateDefaultBackground();
	}
	
	private class startGameHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event)
		{
			openMap();
		}
		
	}

	public static void setClosingState() 
	{
		FileWriter fw;
		try 
		{
			fw = new FileWriter("level_info.txt");
			for(Boolean b : levelsPassed)
			{
				if(b==true)
					fw.write("1\r\n");
				else
					fw.write("0\r\n");
			}
			fw.close();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
