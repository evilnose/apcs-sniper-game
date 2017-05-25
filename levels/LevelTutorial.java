import javafx.scene.image.Image;

public class LevelTutorial extends Level 
{
	
	public LevelTutorial(int numLevel) 
	{
		super(numLevel);
		levelMessage = "THIS IS SOME ALIEN JARGON I AM TYPING FOR TRYING OUT THIS FEATURE";	
		this.setDefaultBackgroundImage(new Image("file:sprites/backgrounds/bus_stop.jpg"));
	}
	
	@Override
	protected void addAllHittables()
	{

		Sitter sitter = new Sitter(true, 0.4);
		this.addHittable(sitter);
		sitter.setPos(175, 220);
		
		Sleeper sleeper = new Sleeper(true, 0.5);
		this.addHittable(sleeper);
		sleeper.faceLeft();
	}
	
	@Override
	protected String getDescription() {
		return "Tutorial Level. Designed to show the player around.";
	}
		
	@Override
	protected String getName() {
		return "Tutorial";
	}

}