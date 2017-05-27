import javafx.scene.image.Image;

public class LevelTwo extends Level 
{
	
	public LevelTwo(int numLevel) 
	{
		super(numLevel);
		levelMessage = "AN ALIEN IS TRYING TO RECRUIT THIS INNOCENT CITIZEN TO HIS ORGANIZATION IN THIS DARK ALLEY. KILL HIM BEFORE THE CIVILIAN MAKES A DECISION.";	
	}
	
	@Override
	protected void addAllHittables()
	{
		StreetRunner sitter = new StreetRunner(false, 1);
		sitter.setPos(175, 220);
		
		Runner runner = new Runner(true, 0.5);
		this.addHittable(runner);
		runner.setPos(165, 190);
		runner.setBounds(175, 470);

		this.addHittable(sitter);
	}
	
	@Override
	protected String getDescription() {
		
		return "Hey! You got through the first level! Let's see if you can deal with this alien trying to trap the citizens of Tatooine.";
	
	}

	@Override
	protected String getName() {
		return "Two";
	}

}