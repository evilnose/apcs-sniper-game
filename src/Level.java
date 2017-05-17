import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
	private Scope hitbox;
	
	public Level(int numLevel)
	{
		// Use the "super" keyword in subclass constructors to invoke this.
		super();
		hitbox = new Scope();
		this.addScope(hitbox);
		setOnMouseTracking(hitbox);
		levelNumber = numLevel;
		targets = new ArrayList<Hittable>();
		civilians = new ArrayList<Hittable>();
		numCivilians = civilians.size();
		numMaxBullets = 10; // Default value
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
		this.setCursor(Cursor.NONE);
	}
	
	public class MyKeyListener implements EventHandler<KeyEvent>
	{
		@Override
		public void handle(KeyEvent event) 
		{
			hitbox.shoot();
		}
	}

	public void load() {
		Stage lvlStage = new Stage();

	}

	public void act(long now) {
		if(isWon())
		{
			this.stop();
			this.displayWinMessage();
		}
		if (isLost()) {
			this.stop();
			this.displayLostMessage();
		}
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
		this.getScene().setOnKeyPressed(new MyKeyListener());
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}

	private boolean isWon() 
	{
		if(targets!=null&&targets.size()!=0&&remainingBullets>0)
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
		Scene scene = new Scene(root, 200, 200);
		message.setTitle("You Lose!");
		message.setResizable(false);

		Text t = new Text("Mission "+ getLevelNumber() + "failed");

		HBox hb = new HBox();
		Button exit = new Button("Exit");
		Button restart = new Button("Retry Level");
		HBox.setMargin(exit,new Insets(10,10,10,10));
		HBox.setMargin(restart,new Insets(10,10,10,10));
		hb.getChildren().addAll(exit, restart);

		root.getChildren().addAll(t, hb);

		message.setScene(scene);
		message.show();

	}

	protected void displayWinMessage() 
	{

		Stage message = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 200,200);
		message.setTitle("You Win!");
		message.setResizable(false);

		Text t = new Text("Mission "+ getLevelNumber() + "passed");

		HBox hb = new HBox();
		Button next = new Button("Next Level");
		HBox.setMargin(next,new Insets(10,10,10,10));
		hb.getChildren().addAll(next);

		root.getChildren().addAll(t,hb);

		message.setScene(scene);
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
	
	protected void setOnMouseTracking(Scope s) {
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton() == MouseButton.PRIMARY) {
					s.shoot();
				}	
			}
			
		});
		
		this.setOnMouseMoved(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				s.moveTo(event.getX(), event.getY());
			}
			
		});
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
}
