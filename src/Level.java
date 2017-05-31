import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
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
		BackgroundImage myBI = new BackgroundImage(new Image("file:sprites/backgrounds/level_"+levelNumber+".png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
			this.setBackground(new Background(myBI));

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

	private boolean isLost() {
		for(Hittable h : targets)
			if(h.isWithinBounds()==false) {
				System.out.println("a target got away");
				return true;
			}
		if(civilians.size()<numCivilians) {
			System.out.println("you killed a civilian");
			return true;
		}
		else if (numRemainingBullets == 0 && targets.size() > 0) {
			System.out.println("you ran out of bullets");
			return true;
		}
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
		t.setFont(Font.font(levelFailedFont, FontWeight.BOLD, 15));

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
		//		lostPlayer.stop();
		//		lostPlayer.play();
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
		//victoryPlayer.stop();
		//victoryPlayer.play();
		winScreen.show();
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

	protected abstract String getDescription();

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
				SniperGame.setClosingState();
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

