import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private int remainingBullets;
	private double windSpeed;
	private AnimationTimer timer;
	private Image defaultBackground;
	protected Scope scope;
	private Level thisLevel;
	private MyEventHandler evHan;
	private Cursor MY_CURSOR = Cursor.DEFAULT;

	public Level(int numLevel) 
	{
		// Use the "super" keyword in subclass constructors to invoke this.
		super();
		thisLevel = this;
		evHan = new MyEventHandler();
		thisLevel.setOnMousePressed(evHan);
		thisLevel.setOnMouseMoved(evHan);

		levelNumber = numLevel;

		targets = new ArrayList<Hittable>();
		civilians = new ArrayList<Hittable>();

		numMaxBullets = 10; // Default value
		remainingBullets = numMaxBullets;
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) 
			{
				for(int i = 0;i<civilians.size();i++)
				{
					Hittable h = civilians.get(i);
					h.act(now);
					if(civilians.contains(h)==false)
						i--;
				}
				for(int i = 0;i<targets.size();i++)
				{
					Hittable h = targets.get(i);
					h.act(now);
					if(targets.contains(h)==false)
						i--;
				}
				act(now);
			}

		};

		scope = new Scope();
		addScope(scope);
		this.setCursor(Cursor.NONE);
	}


	private void act(long now) 
	{
		if(isWon())
		{
			thisLevel.stop();
			thisLevel.setCursor(MY_CURSOR);
			thisLevel.setOnMouseMoved(null);
			thisLevel.displayWinMessage();
			return;
		}
		if (isLost()) {
			thisLevel.stop();
			thisLevel.setCursor(MY_CURSOR);
			thisLevel.setOnMouseMoved(null);
			thisLevel.displayLostMessage();
			return;
		}
	}

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
		this.stop();
		// TODO maybe add s'more later
	}

	public void start() {
		thisLevel.getScene().setOnKeyPressed(new ZoomHandler());
		numCivilians = civilians.size();
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	private boolean isWon() 
	{
		if(numCivilians==civilians.size()&&targets!=null&&targets.size()==0&&remainingBullets>0)
			return true;
		else
			return false;
	}

	private boolean isLost() {
		if(civilians.size()<numCivilians||remainingBullets<0)
			return true;
		else
			return false;

	}

	protected void displayLostMessage()
	{
		Stage message = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 650);
		message.setTitle("You Lose!");
		message.setResizable(false);

		Text t = new Text("MISSION\n       "+ getLevelNumber() + " \nFAILED");
		t.setFill(Color.WHITE);
		t.setFont(Font.font("Monospaced Bold", FontWeight.BOLD, 35));

		ImageView img = new ImageView(new Image("file:sprites/lose.gif"));

		HBox hb = new HBox();
		Button exit = new Button("Exit");
		Button restart = new Button("Retry Level");
		HBox.setMargin(exit,new Insets(10,10,10,10));
		HBox.setMargin(restart,new Insets(10,10,10,10));
		hb.getChildren().addAll(exit, restart);

		root.setLeft(img);
		root.setRight(t);
		root.setBottom(hb);

		root.setStyle("-fx-background-color: #24ff21;");

		message.setScene(scene);
		HBox.setMargin(exit, new Insets(0,0,exit.getScene().getHeight() / 5,(exit.getScene().getWidth() - exit.getPrefWidth()) / 2));
		HBox.setMargin(restart, new Insets(0,0,restart.getScene().getHeight() / 5,(restart.getScene().getWidth() - restart.getPrefWidth()) / 2));
		message.setAlwaysOnTop(true);
		message.show();
	}

	protected void displayWinMessage() 
	{
		Stage message = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 650, 270);
		message.setTitle("You Win!");
		message.setResizable(false);

		Text t = new Text("MISSION\n       "+ getLevelNumber() + " \nPASSED");
		t.setFill(Color.WHITE);
		t.setFont(Font.font("Monospaced Bold", FontWeight.BOLD, 35));

		ImageView img = new ImageView(new Image("file:sprites/win.gif"));

		HBox hb = new HBox();
		Button next = new Button("Next Level");
		next.setPrefSize(125, 30);
		hb.getChildren().addAll(next);

		root.setLeft(img);
		root.setRight(t);
		root.setBottom(hb);

		root.setStyle("-fx-background-color: #24ff21;");

		message.setScene(scene);
		HBox.setMargin(next, new Insets(0,0,next.getScene().getHeight() / 5,(next.getScene().getWidth() - next.getPrefWidth()) / 2));
		message.setAlwaysOnTop(true);
		message.show();
	}

	public double getWindSpeed(){
		return windSpeed;
	}

	public void setWindSpeed(double speed){
		windSpeed = speed;
	}

	public int getNumMaxBullets(){
		return numMaxBullets;
	}

	public void setNumMaxBullets(int n) {
		numMaxBullets = n;
	}

	public int getRemainingBullets(){
		return remainingBullets;
	}

	public int getLevelNumber(){
		return levelNumber;
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
			if(event.getEventType()==MouseEvent.MOUSE_PRESSED)
			{
				if (event.getButton() == MouseButton.PRIMARY) 
				{
					if (remainingBullets > 0) {
						scope.shoot();
						remainingBullets--;
					} else {
						// TODO alert player: out of bullets
					}
				}	
			}
			else if(event.getEventType()==MouseEvent.MOUSE_MOVED)
			{
				scope.moveTo(event.getX(), event.getY());
			}
		}

	}


	private class ZoomHandler implements EventHandler<KeyEvent> 
	{

		@Override
		public void handle(KeyEvent event) 
		{
			if(event.getText().equals("Z")||event.getText().equals("z"))
			{
				double currX = scope.getX()+Scope.SCOPE_WIDTH/2;
				double currY = scope.getY()+Scope.SCOPE_HEIGHT/2;
				Scale scale = new Scale(2,2);
				scale.setPivotX(currX);
				scale.setPivotY(currY);
				thisLevel.getTransforms().add(scale);
				thisLevel.getChildren().remove(scope);
				scope = new Scope();
				scope.setX(currX);
				scope.setX(currY);
				addScope(scope);
			}
		}
	}
}