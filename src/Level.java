import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public abstract class Level extends Pane implements Comparable<Level> {

	private ArrayList<Hittable> targets;
	private ArrayList<Hittable> civilians;
	private int numCivilians;
	private int numMaxBullets;
	private int levelNumber;
	private int numRemainingBullets;
	private double windSpeed;
	private AnimationTimer timer;
	private Image defaultBackground;
	protected Scope scope;
	private Level thisLevel;
	private MyEventHandler evHan;
	private final Cursor LEVEL_SCREEN_CURSOR = Cursor.DEFAULT;
	private final Cursor SCOPE_CURSOR = Cursor.NONE;
	private final String LEVEL_PASSED_FONT = "Accord Heavy SF";
	private final String LEVEL_FAILED_FONT = "Candara";
	private KeyHandler zoomer;
	private boolean isZoomedIn;
	private double lastPivotX, lastPivotY;
	private final Scale ZOOM_IN_SCALE = new Scale(2.0, 2.0);
	private double currX, currY;
	private Button exit, restart, next;
	private ButtonHandler btnHandler;
	private Stage winScreen, loseScreen;
	private ImageView locImage;
	private boolean isStarted;
	private boolean isPaused = false;
	private ImageView pause = new ImageView(new Image("file:sprites/pause.png"));
	private Label bulletLabel;
	private int cartridgeSize; // number of bullets per cartridge
	private int numRemainingCartridges;
	private int numAvailableBullets;
	protected String levelMessage;
    
	public Level(Integer numLevel) 
	{
		// Use the "super" keyword in subclass constructors to invoke this.
		super();
		isStarted = false;
		btnHandler = new ButtonHandler();
		numMaxBullets = 9; // Default value
		numRemainingBullets = numMaxBullets;
		cartridgeSize = 3;
		numRemainingCartridges = numMaxBullets / cartridgeSize;
		numAvailableBullets = numMaxBullets % cartridgeSize;
		thisLevel = this;
		evHan = new MyEventHandler();
		thisLevel.setOnMousePressed(evHan);
		thisLevel.setOnMouseMoved(evHan);
		levelNumber = numLevel;
		targets = new ArrayList<Hittable>();
		civilians = new ArrayList<Hittable>();
		locImage = new ImageView(new Image("file:sprites/level_" + levelNumber+ "_loc.png"));
		
		scope = new Scope();
		addScope(scope);
		
		 
		this.setCursor(SCOPE_CURSOR);
		
		this.setCursor(SCOPE_CURSOR);
		addBulletLabel();
		addAllHittables();

		timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) 
			{
				for(Hittable h : civilians)
				{
					h.act(now);
				}
				for(Hittable h : targets)
				{
					h.act(now);
				}
				scope.act(now);
				act(now);
			}

		};
		isZoomedIn = false;
		zoomer = new KeyHandler();
	}
	 
	public String getLevelMessage()
	{
		return this.levelMessage;
	}
	
	private void act(long now) 
	{
		if(isWon())
		{
			thisLevel.stop();
			thisLevel.setCursor(LEVEL_SCREEN_CURSOR);
			thisLevel.setOnMouseMoved(null);
			thisLevel.displayWinMessage();
		}
		if (isLost()) {
			thisLevel.stop();
			thisLevel.setCursor(LEVEL_SCREEN_CURSOR);
			thisLevel.setOnMouseMoved(null);
			thisLevel.displayLostMessage();
		}
	}
	
	protected abstract void addAllHittables();

	public void load() {
		Stage lvlStage = new Stage();

	}
	
	public void activateCustomBackground(Image background) {
		BackgroundImage myBI = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
	}

	public void activateDefaultBackground() {
		BackgroundImage myBI = new BackgroundImage(defaultBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
	}

	protected void setDefaultBackgroundImage(Image img) {
		defaultBackground = img;
	}

	public void pause()
	{
		timer.stop();
		// TODO maybe add s'more later
	}

	public void start() {
		isStarted = true;
		thisLevel.getScene().setOnKeyPressed(zoomer);
		numCivilians = civilians.size();
		timer.start();
	}
	
	public boolean isStarted() {
		return isStarted;
	}

	public void stop() {
		timer.stop();
//		removeEventHandler(MouseEvent.MOUSE_PRESSED, evHan);
//		removeEventHandler(MouseEvent.MOUSE_MOVED, evHan);
//		removeEventHandler(KeyEvent.KEY_PRESSED, zoomer);
	}

	private boolean isWon() 
	{
		for(Hittable h : targets)
			if(h.isWithinBounds()==false)
				return false;
		if(numCivilians==civilians.size()&&targets!=null&&targets.size()==0&&numRemainingBullets>0)
			return true;
		else
			return false;
	}

	private boolean isLost() {
		for(Hittable h : targets)
			if(h.isWithinBounds()==false)
				return true;
		if(civilians.size()<numCivilians||numRemainingBullets<0)
			return true;
		else
			return false;

	}

	protected void displayLostMessage()
	{
		loseScreen = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 541);
		loseScreen.setTitle("You Lose!");
		loseScreen.setResizable(false);

		Text t = new Text("MISSION\n       "+ getLevelNumber() + " \nFAILED");
		t.setFill(Color.WHITE);
		t.setFont(Font.font(LEVEL_FAILED_FONT, FontWeight.BOLD, 15));

		ImageView img = new ImageView(new Image("file:sprites/indicators/lose.gif"));

		VBox vb = new VBox();
		exit = new Button("Exit");
		exit.setOnMouseClicked(btnHandler);
		restart = new Button("Retry Level");
		restart.setOnMouseClicked(btnHandler);
		VBox.setMargin(t,new Insets(0,10,10,10));
		VBox.setMargin(exit,new Insets(10,10,10,10));
		VBox.setMargin(restart,new Insets(10,10,10,10));
		vb.getChildren().addAll(t,exit, restart);

		root.setLeft(img);
		root.setRight(vb);

		root.setStyle("-fx-background-color: #24ff21;");

		loseScreen.setScene(scene);
		HBox.setMargin(exit, new Insets(0,0,exit.getScene().getHeight() / 5,(exit.getScene().getWidth() - exit.getPrefWidth()) / 2));
		HBox.setMargin(restart, new Insets(0,0,restart.getScene().getHeight() / 5,(restart.getScene().getWidth() - restart.getPrefWidth()) / 2));
		loseScreen.setAlwaysOnTop(true);
		loseScreen.show();
	}

	protected void displayWinMessage() 
	{
		winScreen = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 650, 270);
		winScreen.setTitle("You Win!");
		winScreen.setResizable(false);

		Text t = new Text("MISSION\n       "+ getLevelNumber() + " \nPASSED");
		t.setFill(Color.WHITE);
		t.setFont(Font.font("Monospaced Bold", FontWeight.BOLD, 35));

		ImageView img = new ImageView(new Image("file:sprites/indicators/win.gif"));

		VBox vb = new VBox();
		next = new Button("Next Level");
		next.setPrefSize(125, 30);
		next.setOnMouseClicked(btnHandler);
		VBox.setMargin(next, new Insets(20,10,10,10));
		vb.getChildren().addAll(t,next);

		root.setLeft(img);
		root.setRight(vb);

		root.setStyle("-fx-background-color: #24ff21;");

		winScreen.setScene(scene);
		HBox.setMargin(next, new Insets(0,0,next.getScene().getHeight() / 5,(next.getScene().getWidth() - next.getPrefWidth()) / 2));
		winScreen.setAlwaysOnTop(true);
		winScreen.show();
	}

	public double getWindSpeed(){
		return windSpeed;
	}

	public void setWindSpeed(double speed){
		windSpeed = speed;
	}
	
	private void reduceNumBullets() {
		if (numRemainingBullets > 0) {
			if (numAvailableBullets == 0) {
				remindToReload();
			} else {
				numAvailableBullets--;
				numRemainingBullets--;
				updateBulletLabel();
			}
		} else {
			// TODO display out of bullets
		}
	}
	
	private void updateBulletLabel() {
		
		bulletLabel.setText(numAvailableBullets + "/" + numRemainingCartridges);
	}

	private void addBulletLabel() {
        bulletLabel = new Label();
		bulletLabel.setFont(new Font(30));
		bulletLabel.setTextFill(Color.WHITE);
		reloadBulletLabel();
		updateBulletLabel();
		bulletLabel.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().add(bulletLabel);
	}
	
	public void reloadBulletLabel() {
		if (numRemainingCartridges > 0 && numAvailableBullets == 0) {
			numAvailableBullets = cartridgeSize;
			numRemainingCartridges--;
			updateBulletLabel();
		}
	}

	private void remindToReload() {
		// TODO implement code to remind player to reload
	}

	public int getNumRemainingBullets() {
		return numRemainingBullets;
	}
	
	public int getNumMaxBullets(){
		return numMaxBullets;
	}

	protected void setNumMaxBullets(int n) {
		numMaxBullets = n;
	}

	public int getLevelNumber(){
		return levelNumber;
	}
	
	public double getMouseX() {
		return currX;
	}
	
	public double getMouseY() {
		return currY;
	}

	protected void addHittable(Hittable h) {
		getChildren().addAll(h, h.getHitbox());

		if (h.isTarget()) {
			targets.add(h);
		} else {
			civilians.add(h);
		}
	}

	protected void removeHittable(Hittable h) {
		getChildren().removeAll(h, h.getHitbox());

		if (h.isTarget()) {
			targets.remove(h);
		} else {
			civilians.remove(h);
		}
	}

	protected void addScope(Scope s) {
		getChildren().add(s);
	}

	public <A extends Node> java.util.List<A> getObjects(java.lang.Class<A> cls) {
		ArrayList<A> verifiedList = new ArrayList<A>();
		for (Node node : getChildren()) {
			if (cls.isInstance(node))
				verifiedList.add(cls.cast(node));
		}

		return verifiedList;

	}

	protected abstract String getDescription();

	protected abstract String getName();

	@Override
	public int compareTo(Level other) {
		return this.getLevelNumber() - other.getLevelNumber();
	}


	public class MyEventHandler implements EventHandler<MouseEvent>
	{

		@Override
		public void handle(MouseEvent event)
		{
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED)
			{
				if (event.getButton() == MouseButton.PRIMARY) 
				{
					if (numRemainingBullets>0 && numAvailableBullets > 0) {
						scope.shoot();
						reduceNumBullets();
						updateBulletLabel();
					}
				}	
				
			}
			else if (event.getEventType() == MouseEvent.MOUSE_MOVED)
			{
				scope.moveTo(event.getX(), event.getY());
				currX = event.getX();
				currY = event.getY();
			}
		}

	}

	private class ButtonHandler implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent event)
		{
			if(event.getSource().equals(exit))
			{
				SniperGame.setLevelPassed(levelNumber-1, false);
				SniperGame.setClosingState();
				loseScreen.close();
				System.exit(0);
			}
			else if(event.getSource().equals(restart))
			{
				loseScreen.close();
				
				SniperGame.startLevel(levelNumber - 1); // TODO should have been levelNumber; change before finishing
			}
			else if(event.getSource().equals(next))
			{
				Stage s = (Stage) thisLevel.getScene().getWindow();
				s.close();
				SniperGame.setLevelPassed(levelNumber-1, true);
				winScreen.close();
				SniperGame.startLevel(levelNumber); // TODO should have been levelNumber + 1; change before finishing
			}
		}
		
	}
	
	
	public ImageView getLocationImage()
	{
		return this.locImage;
	}
	
	private class KeyHandler implements EventHandler<KeyEvent> 
	{

		@Override
		public void handle(KeyEvent event) 
		{
			if (event.getCode() == KeyCode.Z)
			{
				if (!isZoomedIn)
				{
					isZoomedIn = true;
					lastPivotX = scope.getX()+scope.getImage().getWidth()/2;
					lastPivotY = scope.getY()+scope.getImage().getHeight()/2;
					ZOOM_IN_SCALE.setPivotX(lastPivotX);
					ZOOM_IN_SCALE.setPivotY(lastPivotY);
					thisLevel.getTransforms().add(ZOOM_IN_SCALE);
					
					scope.setScaleX(0.5);
					scope.setScaleY(0.5);
					lastPivotX = scope.getX()+scope.getImage().getWidth()/2;
					lastPivotY = scope.getY()+scope.getImage().getHeight()/2;
					
				}
				else
				{
					isZoomedIn = false;
					thisLevel.getTransforms().remove(ZOOM_IN_SCALE);
					scope.setScaleX(1);
					scope.setScaleY(1);
					scope.move(scope.getX()+scope.getImage().getWidth()/2 - lastPivotX, scope.getY()+scope.getImage().getHeight()/2 - lastPivotY); // calibrate the scope
				}
			}
			else if(event.getCode() == KeyCode.SPACE)
			{
				if(!isPaused)
				{
					timer.stop();
					thisLevel.getChildren().remove(scope);
					thisLevel.getChildren().add(pause);
					pause.relocate(500-256,300-256);
					thisLevel.setOnMouseMoved(null);
					isPaused = true;
				}
				else
				{
					thisLevel.setOnMouseMoved(evHan);
					thisLevel.getChildren().add(scope);
					thisLevel.getChildren().remove(pause);
					timer.start();
					isPaused = false;
				}
			} else if (event.getCode() == KeyCode.R) {
				reloadBulletLabel();
			}
		}
	}
}

