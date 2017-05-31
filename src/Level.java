import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.util.Duration;

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
	private final Cursor levelScreenCursor = Cursor.DEFAULT;
	private final Cursor scopeCursor = Cursor.NONE;
	private final String levelPassedFont = "Accord Heavy SF";
	private final String levelFailedFont = "Candara";
	private KeyHandler zoomer;
	private boolean isZoomedIn;
	private double lastPivotX, lastPivotY;
	private final Scale ZOOM_IN_SCALE = new Scale(2.0, 2.0);
	private double currX, currY;
	private Button exit, restart, next;
	private ButtonHandler btnHandler;
	private Stage winScreen, loseScreen;
	protected ImageView locImage;
	private boolean isStarted;
	private boolean isPaused = false;
	private final Label reloadLabel = new Label();
	private FadeTransition ft;
	private Label bulletLabel,cartridgeLabel;
	private VBox entireLabel;
	private int cartridgeSize; // number of bullets per cartridge
	private int numRemainingCartridges;
	private int numAvailableBullets;
	private String lostMessage;

	protected String levelMessage="";


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
		addAllHittables();
		scope = new Scope();
		addScope(scope);


		this.setCursor(scopeCursor);

		this.setCursor(scopeCursor);
		addBulletLabel();
		initReloadLabel();

		timer = new AnimationTimer() {

			@Override
			public void handle(long now) 
			{
				for(int i = 0;i<civilians.size();i++)
				{
					Hittable h = civilians.get(i);
					if (h.isWithinBounds())
						h.act(now);
				}
				for(int i = 0;i<targets.size();i++)
				{
					Hittable h = targets.get(i);
					if (h.isWithinBounds())
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
			thisLevel.setCursor(levelScreenCursor);
			thisLevel.setOnMouseMoved(null);
			thisLevel.setOnMousePressed(null);
			thisLevel.displayWinMessage();
		}
		if (isLost()) {
			thisLevel.stop();
			thisLevel.setCursor(levelScreenCursor);
			thisLevel.setOnMouseMoved(null);
			thisLevel.setOnMousePressed(null);
			thisLevel.displayLostMessage();
		}
	}

	protected abstract void addAllHittables();

	public void activateCustomBackground(Image background) {
		BackgroundImage myBI = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
	}

	public void pause()
	{
		timer.stop();
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
	}

	private boolean isWon() 
	{
		if(numCivilians==civilians.size()&&targets!=null&&targets.size()==0&&numRemainingBullets>0)
			return true;
		else
			return false;
	}

	private boolean isLost() 
	{
		for(Hittable h : targets)
			if(h.isWithinBounds()==false) {
				lostMessage = "A TARGET GOT AWAY";
				return true;
			}
		if(civilians.size()<numCivilians) {
			lostMessage = "YOU KILLED A CIVILIAN";
			return true;
		}
		else if (numRemainingBullets == 0 && targets.size() > 0) {
			lostMessage = "YOU RAN OUT OF BULLETS";
			return true;
		}
		else
			return false;

	}

	protected void displayLostMessage()
	{
		loseScreen = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1000, 600);
		loseScreen.setTitle("You Lose!");
		loseScreen.setResizable(false);

		root.setBackground(new Background(new BackgroundImage(new Image("file:sprites/backgrounds/lose_screen.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));
		
		Text t1 = new Text("MISSION FAILED");
		t1.setFont(new Font("American Typewriter", 35));
		
		Text t = new Text();
		t.setFont(new Font("American Typewriter", 25));

		DoubleProperty maxX = new SimpleDoubleProperty(900);
		t.wrappingWidthProperty().bind(maxX);
		t.setLineSpacing(10);

		exit = new Button("EXIT");	
		exit.setFont(new Font("American Typewriter", 20));
		exit.setStyle("-fx-background-color: transparent;");
		exit.setTextFill(Color.BLACK);
		exit.setOnMouseClicked(btnHandler);
		
		restart = new Button("RESTART");	
		restart.setFont(new Font("American Typewriter", 20));
		restart.setStyle("-fx-background-color: transparent;");
		restart.setTextFill(Color.BLACK);
		restart.setOnMouseClicked(btnHandler);

		HBox hb = new HBox();
		HBox.setMargin(exit,new Insets(10,10,10,10));
		HBox.setMargin(restart,new Insets(10,10,10,10));
		hb.getChildren().addAll(exit, restart);
		
		AnimationTimer timer = new AnimationTimer() 
		{
			int i = 0;
			@Override
			public void handle(long now) 
			{
				long start = 0;
				if(now-start>Math.pow(10, 9))
				{
					if(i<lostMessage.length())
					{
						t.setText(t.getText()+lostMessage.charAt(i));
						i++;
					}
					else
					{
						if(i==lostMessage.length())
							root.setBottom(hb);
						this.stop();
					}
					start = now;
				}
			}
		};

		root.setTop(t1);
		root.setCenter(t);
		root.setMargin(t1, new Insets(100,350,100,350));
		root.setMargin(t, new Insets(50,350,100,370));
		root.setMargin(hb, new Insets(50,350,100,370));
		
		loseScreen.setScene(scene);
		loseScreen.setAlwaysOnTop(true);
		loseScreen.show();
		timer.start();
	}

	protected void displayWinMessage() 
	{
		
		winScreen = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,1000,600);
		winScreen.setTitle("You Win!");
		winScreen.setResizable(false);
		
		root.setBackground(new Background(new BackgroundImage(new Image("file:sprites/backgrounds/win_screen.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));

		Text t1 = new Text("MISSION PASSED");
		t1.setFont(new Font("American Typewriter", 35));
		
		Text t = new Text();
		t.setFont(new Font("American Typewriter", 25));

		DoubleProperty maxX = new SimpleDoubleProperty(900);
		t.wrappingWidthProperty().bind(maxX);
		t.setLineSpacing(10);
		
		next = new Button("NEXT");	
		next.setFont(new Font("American Typewriter", 20));
		next.setStyle("-fx-background-color: transparent;");
		next.setTextFill(Color.BLACK);
		next.setOnMouseClicked(btnHandler);

		HBox hb = new HBox();
		HBox.setMargin(next,new Insets(10,10,10,10));
		hb.getChildren().add(next);
		
		String message = "YOU MANAGED TO EXTERMINATE ALL THE TARGETS!";
		
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
							root.setBottom(hb);
						this.stop();
					}
					start = now;
				}
			}
		};

		root.setTop(t1);
		root.setCenter(t);
		root.setMargin(t1, new Insets(100,350,100,350));
		root.setMargin(t, new Insets(50,50,100,150));
		root.setMargin(hb, new Insets(50,350,100,420));
		
		winScreen.setScene(scene);
		winScreen.setAlwaysOnTop(true);
		winScreen.show();
		timer.start();
	}

	public double getWindSpeed(){
		return windSpeed;
	}

	public void setWindSpeed(double speed){
		windSpeed = speed;
	}

	public void reduceNumBullets() {
		if (numRemainingBullets > 0) {
			if (numAvailableBullets == 0) {
				remindToReload();
			} else {
				numAvailableBullets--;
				numRemainingBullets--;
				updateBulletLabel();
			}
		} else {
			// game is lost
		}
	}

	private void updateBulletLabel() {

		bulletLabel.setText(":   "+numAvailableBullets);
		cartridgeLabel.setText(":   "+numRemainingCartridges);
	}

	private void addBulletLabel()
	{
		entireLabel = new VBox();

		HBox h = new HBox();
		ImageView bullets = new ImageView(SniperGame.bullet);
		bulletLabel = new Label();
		bulletLabel.setFont(new Font(12));
		bulletLabel.setTextFill(Color.WHITE);
		HBox.setMargin(bullets, new Insets(10, 10, 10, 10));
		HBox.setMargin(bulletLabel, new Insets(10, 10, 10, 10));
		h.getChildren().addAll(bullets,bulletLabel);

		HBox h1 = new HBox();
		ImageView magazines = new ImageView(SniperGame.magazine);
		cartridgeLabel = new Label();
		cartridgeLabel.setFont(new Font(12));
		cartridgeLabel.setTextFill(Color.WHITE);
		HBox.setMargin(magazines, new Insets(10, 10, 10, 10));
		HBox.setMargin(cartridgeLabel, new Insets(10, 10, 10, 10));
		h1.getChildren().addAll(magazines, cartridgeLabel);

		reloadBulletLabel();
		updateBulletLabel();

		entireLabel.getChildren().addAll(h,h1);
		this.getChildren().add(entireLabel);
	}

	public void reloadBulletLabel() {
		if (numRemainingCartridges > 0 && numAvailableBullets == 0) {
			numAvailableBullets = cartridgeSize;
			numRemainingCartridges--;
			updateBulletLabel();			
		}
	}

	private void remindToReload() {
		ft.play();
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
		getChildren().add(h);

		if (h.isTarget()) {
			targets.add(h);
		} else {
			civilians.add(h);
		}
	}

	protected void removeHittable(Hittable h) 
	{
		getChildren().removeAll(h,h.getHitbox());

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

	protected abstract String getName();

	@Override
	public int compareTo(Level other) {
		return this.getLevelNumber() - other.getLevelNumber();
	}

	private void initReloadLabel() {
		reloadLabel.setText("Press R to Reload");
		reloadLabel.setTextFill(Color.RED);
		reloadLabel.setFont(new Font("Monospaced Bold", 18));
		reloadLabel.setAlignment(Pos.CENTER);
		// this is hardcoded :(
		reloadLabel.setPadding(new Insets(20, 0, 0, 400));
		reloadLabel.setOpacity(0);
		this.getChildren().add(reloadLabel);
		ft = new FadeTransition(Duration.millis(1000), reloadLabel);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);
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
					if (numRemainingBullets > 0) {
						if (numAvailableBullets > 0) {
							scope.shoot();
							updateBulletLabel();
						} else {
							remindToReload();
						}
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
				SniperGame.setState();
				loseScreen.close();
				System.exit(0);
			}
			else if(event.getSource().equals(restart))
			{
				loseScreen.close();
				Stage s = (Stage) thisLevel.getScene().getWindow();
				s.close();
				SniperGame.startLevel(levelNumber-1); // This is due to the one offset betwixt the ArrayList levels index and the levelNumber
			}
			else if(event.getSource().equals(next))
			{
				SniperGame.setLevelPassed(levelNumber-1);
				SniperGame.setState();
				Stage s = (Stage) thisLevel.getScene().getWindow();
				s.close();
				winScreen.close();
				SniperGame.startLevel(levelNumber); // Same reason as above
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
					setUIScale(0.5);
					lastPivotX = scope.getX()+scope.getImage().getWidth()/2;
					lastPivotY = scope.getY()+scope.getImage().getHeight()/2;

				}
				else
				{
					isZoomedIn = false;
					thisLevel.getTransforms().remove(ZOOM_IN_SCALE);
					setUIScale(1);
					scope.move(scope.getX()+scope.getImage().getWidth()/2 - lastPivotX, scope.getY()+scope.getImage().getHeight()/2 - lastPivotY); // calibrate the scope
				}
			}
			else if(event.getCode() == KeyCode.SPACE)
			{
				if(!isPaused)
				{
					timer.stop();
					thisLevel.getChildren().remove(scope);
					thisLevel.getChildren().add(SniperGame.pause);
					SniperGame.pause.relocate(500-256,300-256);
					thisLevel.setOnMouseMoved(null);
					isPaused = true;
				}
				else
				{
					thisLevel.setOnMouseMoved(evHan);
					thisLevel.getChildren().add(scope);
					thisLevel.getChildren().remove(SniperGame.pause);
					timer.start();
					isPaused = false;
				}
			} else if (event.getCode() == KeyCode.R) {
				reloadBulletLabel();
				SniperGame.reloadPlayer.stop();
				SniperGame.reloadPlayer.play();
			}
		}
	}
	
	private void setUIScale(double scale) {
		scope.setScaleX(scale);
		scope.setScaleY(scale);
		entireLabel.setScaleX(scale);
		entireLabel.setScaleY(scale);
	}
}

