import java.util.ArrayList;

public abstract class Level extends World {
	
	private ArrayList<Hittable> targets;
	private ArrayList<Hittable> civilians;
	private int numMaxBullets;
	
	public Level() {
		// Use the "super" keyword in subclass constructors to invoke this.
		super();
		targets = new ArrayList<Hittable>();
		civilians = new ArrayList<Hittable>();
		numMaxBullets = 10; // Default value
	}
	
	@Override
	public void act(long now) {
		if (isLost()) {
			
		}
	}
	
	public void setNumMaxBullets(int n) {
		numMaxBullets = n;
	}
	
	private boolean isWon() {
		return (targets.size() == 0);
	}
	
	private boolean isLost() {
		return false;
	}
	
	protected abstract String displayLostMessage();
	
	protected abstract String displayWonMessage();

}
