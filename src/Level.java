import java.util.ArrayList;

public abstract class Level extends World {
	
	private ArrayList<Hittable> target;
	private ArrayList<Hittable> civilian;
	private int numMaxBullets;
	
	public Level() {
		// Use the "super" keyword in subclass constructors to invoke this.
		target = new ArrayList<Hittable>();
		civilian = new ArrayList<Hittable>();
		numMaxBullets = 10; // Default value
	}
	
	public void setNumMaxBullets(int n) {
		numMaxBullets = n;
	}

}
