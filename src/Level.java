import java.util.ArrayList;

public abstract class Level extends World {
	
	private ArrayList<Hittable> targets;
	private ArrayList<Hittable> civilians;
	private int numMaxBullets;
	private int levelNumber;
	private int remainingBullets;
	private double windSpeed;

	public Level(int numLevel) {
		// Use the "super" keyword in subclass constructors to invoke this.
		super();
		levelNumber=numLevel;
		targets = new ArrayList<Hittable>();
		civilians = new ArrayList<Hittable>();
		numMaxBullets = 10; // Default value
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
	
	
	protected abstract String getLostMessage();
	
	protected abstract String getWonMessage();
	
	public void pause(){
		
	}
	public double getWindSpeed(){
		return windSpeed;
	}
	
	public void setWindSpeed(double speed){
		windSpeed=speed;
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
	
	

}
