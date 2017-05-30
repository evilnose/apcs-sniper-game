
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 *	This class handles the UI, namely the main game interface.
 */

public class SniperGame extends Application 
{

	private static ArrayList<Level> levels;
	public static final int LEVEL_WIDTH = 1000;
	public static final int LEVEL_HEIGHT = 600;
	private static Stage mainScreen,levelScreen,mapScreen,missionScreen;
	private static Scene levelScene;
	private static Level currLevel;
	private static ArrayList<Boolean> levelsPassed;
    
	public final static Media victory = new Media(new File("sounds/victory_sound.wav").toURI().toString());
	public final static MediaPlayer victoryPlayer = new MediaPlayer(victory);
	public final static Media lost = new Media(new File("sounds/lost_sound2.wav").toURI().toString());
	public final static MediaPlayer lostPlayer = new MediaPlayer(lost);
	public final static Media reload = new Media(new File("sounds/reload_sound.wav").toURI().toString());
	public final static MediaPlayer reloadPlayer = new MediaPlayer(reload);
	
	public final static Image sleeperCivR = new Image("file:sprites/hittables/civilians/sleeper_right.png");
	public final static Image sleeperCivL = new Image("file:sprites/hittables/civilians/sleeper_left.png");
	public final static Image sleeperTgtR = new Image("file:sprites/hittables/targets/sleeper_right.png");
	public final static Image sleeperTgtL = new Image("file:sprites/hittables/targets/sleeper_left.png");
	public final static Image sitterCivR = new Image("file:sprites/hittables/civilians/sitter_right.png");
	public final static Image sitterTgtR = new Image("file:sprites/hittables/targets/sitter_right.png");
	public final static Image sitterCivL = new Image("file:sprites/hittables/civilians/sitter_left.png");
	public final static Image sitterTgtL = new Image("file:sprites/hittables/targets/sitter_left.png");
	public final static Image[] startledSitterCivR = decodeGifToImages("file:sprites/hittables/civilians/startled_sitter_right.gif");
	public final static Image[] startledSitterTgtR = decodeGifToImages("file:sprites/hittables/targets/startled_sitter_right.gif");
	public final static Image[] startledSitterCivL = decodeGifToImages("file:sprites/hittables/civilians/startled_sitter_left.gif");
	public final static Image[] startledSitterTgtL = decodeGifToImages("file:sprites/hittables/targets/startled_sitter_left.gif");
	public final static Image runnerCivR = new Image("file:sprites/hittables/civilians/runner_right.gif");
	public final static Image runnerCivL = new Image("file:sprites/hittables/civilians/runner_left.gif");
	public final static Image runnerTgtR = new Image("file:sprites/hittables/targets/runner_right.gif");
	public final static Image runnerTgtL = new Image("file:sprites/hittables/targets/runner_left.gif");
	public final static Image walkerCivR = new Image("file:sprites/hittables/civilians/walker_right.gif");
	public final static Image walkerTgtR = new Image("file:sprites/hittables/targets/walker_left.gif");

	public static void main(String args[]) {
		launch(); 
	}

	@Override
	public void start(Stage homeScreen) 
	{
		loadLevels();
		fillLevelsPassed();

		mainScreen = homeScreen;
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


		Button howToPlay = new Button();
		howToPlay.setText("Instructions");
		howToPlay.setFont(new Font("Monospaced Bold",30));
		howToPlay.setStyle("-fx-background-color: transparent;");
		howToPlay.setOnAction(new startGameHandler());

		VBox contents = new VBox();
		contents.getChildren().addAll(startGameButton,howToPlay);

		VBox.setMargin(startGameButton,new Insets(100,300,10,400));
		VBox.setMargin(howToPlay,new Insets(10,300,10,400));

		root.setRight(contents);



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
		//		levels.add(new LevelFour(4));
				levels.add(new LevelFive(5));
		//		levels.add(new LevelSix(6));
		//		levels.add(new LevelSeven(7));
		//		levels.add(new LevelEight(8));
		//		levels.add(new LevelNine(9));
		//		levels.add(new LevelTen(10));
	}

	public static void displayLevelMessage(int lvlNum)
	{
		mapScreen.close();
		String message = levels.get(lvlNum).getLevelMessage();
		missionScreen = new Stage();
		missionScreen.setTitle("Mission "+currLevel.getLevelNumber());
		missionScreen.setResizable(false);

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,960,540);

		Text t = new Text();
		t.setFont(new Font("American Typewriter", 25));

		DoubleProperty maxX = new SimpleDoubleProperty(900);
		t.wrappingWidthProperty().bind(maxX);
		t.setLineSpacing(10);

		Button b = new Button("CONTINUE");	
		b.setFont(new Font("American Typewriter", 20));
		b.setStyle("-fx-background-color: transparent;");
		b.setTextFill(Color.BLACK);


		b.setOnMouseClicked(new EventHandler<MouseEvent>()
		{


			@Override
			public void handle(MouseEvent event) 
			{
				if (currLevel.isStarted()) {
					currLevel.stop();
					restart();
				}

				if (levelScreen != null) {
					levelScreen.close();
				}
				levelScreen = new Stage();
				levelScreen.setTitle(currLevel.getName());
				levelScreen.setResizable(false);

				if (levelScene == null)
					levelScene = new Scene(currLevel,LEVEL_WIDTH,LEVEL_HEIGHT);
				else
					levelScene.setRoot(currLevel);

				levelScreen.setScene(levelScene);
				levelScreen.show();

				//currLevel.activateDefaultBackground();
				currLevel.start();

				missionScreen.close();
			}
		});

		AnimationTimer timer = new AnimationTimer() 
		{
			int i = 0;
			@Override
			public void handle(long now) 
			{
				long start = 0;
				if(now-start>Math.pow(10, 9))
				{
					if(i<message.length())
					{
						t.setText(t.getText()+message.charAt(i));
						i++;
					}
					else
					{
						if(i==message.length())
							root.setBottom(b);
						this.stop();
					}
					start = now;
				}
			}
		};

		// click to skip message animation
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					t.setText(message);
					root.setBottom(b);
					timer.stop();
				}
			}

		});

		BorderPane.setMargin(t, new Insets(10,10,10,10));
		BorderPane.setMargin(b, new Insets(20,300,20,400));

		root.setBackground(new Background(new BackgroundImage(new Image("file:sprites/backgrounds/mission_screen.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));

		root.setCenter(t);

		missionScreen.setScene(scene);
		missionScreen.show();
		timer.start();
	}

	public static void startLevel(int lvlNum) 
	{
		if(lvlNum>=levels.size())
			gamePassed();
		else
		{
			currLevel = levels.get(lvlNum);
			displayLevelMessage(lvlNum);
		}
	}

	public static void restart() {
		for (Level lvl : levels) {
			if (lvl == currLevel) {
				String className = currLevel.getClass().getName();
				int levelNum = currLevel.getLevelNumber();
				switch(className) {
				case "LevelOne": currLevel = new LevelOne(levelNum);
				break;	
				case "LevelTwo": currLevel = new LevelTwo(levelNum);
				break;
				case "LevelThree" : currLevel = new LevelThree(levelNum);
				break;
				case "LevelFour" : currLevel = new LevelFour(levelNum);
				break;
				case "LevelFive" : currLevel = new LevelFive(levelNum);
				break;
				case "LevelSix" : currLevel = new LevelSix(levelNum);
				break;
				case "LevelSeven" : currLevel = new LevelSeven(levelNum);
				break;
				case "LevelEight" : currLevel = new LevelEight(levelNum);
				break;
				case "LevelNine" : currLevel = new LevelNine(levelNum);
				break;
				case "LevelTen" : currLevel = new LevelTen(levelNum);
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
			s.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static void setLevelPassed(int levelNum)
	{
		levelsPassed.set(levelNum, true);
	}

	private void openMap() 
	{
		mainScreen.close();
		Map map =  new Map(levels,levelsPassed);
		mapScreen = new Stage();
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
			e.printStackTrace();
		}
	}

	public static Image[] decodeGifToImages(String url) {
		GifDecoder gd = new GifDecoder();
		gd.read(url);
		Image[] imgs = new Image[gd.getFrameCount()];
		for(int i=0; i < gd.getFrameCount(); i++) {

			WritableImage wimg = null;
			BufferedImage bimg = gd.getFrame(i);
			imgs[i] = SwingFXUtils.toFXImage(bimg, wimg);
		}
		return imgs;
	}

	public static void gamePassed() {
		// TODO The game is WON
	}

}

