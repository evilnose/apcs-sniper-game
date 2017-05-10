import java.util.ArrayList;

public abstract class Level extends World {
	
	private ArrayList<Hittable> target= new ArrayList<Hittable>();
	private ArrayList<Hittable> civilian= new ArrayList<Hittable>();
	private int numBullet= 10; //Change every level
	
	
	int numTargets=target.size();
	int numCivilians=civilian.size();
	
	

}
