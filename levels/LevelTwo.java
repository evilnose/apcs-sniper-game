import javafx.scene.image.Image;

public class LevelTwo extends Level 
{
	
	public LevelTwo(int numLevel) 
	{
		super(numLevel);
		levelMessage = "THIS IS SOME ALIEN JARGON I AM TYPING FOR TRYING OUT THIS FEATURE";	
	}
	
	@Override
	protected void addAllHittables()
	{
		Sitter sitter = new Sitter(true, 1);
		sitter.setPos(175, 220);
		
		Runner runner = new Runner(true, 0.5);
		this.addHittable(runner);
		runner.setPos(170, 190);
		runner.setBounds(163, 483);

		this.addHittable(sitter);
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