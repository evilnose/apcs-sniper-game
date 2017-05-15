import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class Level extends World implements Comparable<Level> {

	private ArrayList<Hittable> targets;
	private ArrayList<Hittable> civilians;
	private int numCivilians;
	private int numMaxBullets;
	private int levelNumber;
	private int remainingBullets;
	private double windSpeed;

	public Level(int numLevel) {
		// Use the "super" keyword in subclass constructors to invoke this.
		super();
		levelNumber = numLevel;
		targets = new ArrayList<Hittable>();
		civilians = new ArrayList<Hittable>();
		numCivilians = civilians.size();
		numMaxBullets = 10; // Default value
	}

	public void load() {
		Stage lvlStage = new Stage();

	}

	@Override
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

	public void pause()
	{
		this.stop();
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
		getChildren().add(h);

		if (h.isTarget()) {
			targets.add(h);
		} else {
			civilians.add(h);
		}
	}
	
	protected void addScope(Scope s) {
		getChildren().add(s);
	}
	
	protected void setOnMouseTracking(Scope s) {
		this.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				s.setCenterX(event.getSceneX());
				s.setCenterY(event.getSceneY());
			}
			
		});
	}

	protected abstract String getDescription();

	protected abstract String getName();

	@Override
	public int compareTo(Level other) {
		return this.getLevelNumber() - other.getLevelNumber();
	}


}
