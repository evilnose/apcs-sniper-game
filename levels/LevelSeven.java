import javafx.scene.image.Image;

public class LevelSeven extends Level 
{
	
	public LevelSeven(int numLevel) 
	{
		super(numLevel);
		levelMessage = "A GROUP OF ALIENS IS WREAKING HAVOC IN THIS MALL. AS YOU WILL SEE, THEY DO NOT QUITE UNDERSTAND THE CONCEPT OF SOCIAL ETIQUETTE. TRANQUILIZE THEM BEFORE THEY DRIVE EVERYONE CRAZY!";	

	}
	
	@Override
	protected void addAllHittables()
	{



		Sleeper sl1 = new Sleeper(true, 0.5);
		this.addHittable(sl1);
		sl1.setPos(240, 350);

		StreetRunner sr1 = new StreetRunner(false, 0.4,-0.1,-1);
		this.addHittable(sr1);
		sr1.setPos(700, 90);
		sr1.setBounds(600,1000);
	
		StreetRunner sr2 = new StreetRunner(false, 0.35,2.5,-1);
		this.addHittable(sr2);
		sr2.setPos(-7,270);
		
		Jumper s1 = new Jumper(true, 0.3,500);
		this.addHittable(s1);
		s1.setPos(45, 55);
		

		StreetWalker sw1 = new StreetWalker(false,0.4,0.6,1);
		this.addHittable(sw1);
	    sw1.setPos(350,260);
		sw1.setBounds(600,1000);
	}
	
	@Override
	protected String getDescription() {
		return "Hey rookie, let's make your first mission easy. You see that alien sitting alone at the bus stop? Eliminate it"+
				" before it gets away.";
	}

	@Override
	protected String getName() {
		return "Tutorial";
	}

}