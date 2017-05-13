import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class Level extends World implements Comparable<Level> {
	
	private ArrayList<Hittable> targets;
	private ArrayList<Hittable> civilians;
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
		numMaxBullets = 10; // Default value
		Button btn = new Button("TESTING");
		this.getChildren().add(btn);
	}
	
	public void load() {
		Stage lvlStage = new Stage();
		
	}
	
	@Override
	public void act(long now) {
		if (isLost()) {
			
		}
	}
	
	private boolean isWon() {
		// TODO
		return false;
	}
	
	private boolean isLost() {
		// TODO
		return false;
		
	}
	
	protected String getLostMessage() {
		return "Mission Failed...";
	}
	
	protected String getWonMessage() {
		return "Mission Passed!";
	}
	
	public void pause(){
		
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
	
	protected abstract String getDescription();
	
	protected abstract String getName();
	
	@Override
	public int compareTo(Level other) {
		return this.getLevelNumber() - other.getLevelNumber();
	}

}
