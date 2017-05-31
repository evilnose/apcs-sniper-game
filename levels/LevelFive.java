
public class LevelFive extends Level 
{
	
	public LevelFive(int numLevel) 
	{
		super(numLevel);
		levelMessage = "SOME ALIENS HAVE BEEN SPOTTED ON NATURE TREK. ELIMINATE THEM AND SAVE THE TREKKERS!";	
		this.setBackground(SniperGame.levelFiveBack);
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
	protected String getName() {
		return "Five";
	}

}