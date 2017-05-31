
public class LevelEight extends Level 
{
	
	public LevelEight(int numLevel) 
	{
		super(numLevel);
		levelMessage = "SOME ALIEN SUSPECTS HAVE BEEN SPOTTED ON NATURE TREK. ELIMINATE THEM AND SAVE THE TREKKERS!";	
		
	}
	
	@Override
	protected void addAllHittables()
	{

		Sitter sitter1 = new Sitter(true, 0.3);
		this.addHittable(sitter1);
		sitter1.setPos(5, 10);

		
		Runner r1 = new Runner(false, 0.4);
		this.addHittable(r1);
		r1.setPos(200, 200);
       
		Runner r2 = new Runner(false, 0.4);
		this.addHittable(r2);
		r2.setPos(200, 250);
		
		Runner r3 = new Runner(false, 0.4);
		this.addHittable(r3);
		r3.setPos(700, 100);
		
		Sitter sitter3 = new Sitter(true, 0.5);
		this.addHittable(sitter3);
		sitter3.setPos(5, 300);
		
		Sitter sitter4 = new Sitter(true, 0.4);
		this.addHittable(sitter4);
		sitter4.setPos(700, 50);		
		
		
		
		
		
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