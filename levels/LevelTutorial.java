import javafx.scene.image.Image;

public class LevelTutorial extends Level 
{
	
	public LevelTutorial(int numLevel) 
	{
		super(numLevel);
		levelMessage = "THIS IS SOME ALIEN JARGON I AM TYPING FOR TRYING OUT THIS FEATURE";	
	}
	
	@Override
	protected void addAllHittables()
	{

		Sitter sitter = new Sitter(true, 0.4);
		this.addHittable(sitter);
		sitter.setPos(260, 260);
		this.setDefaultBackgroundImage(new Image("file:sprites/backgrounds/bus_stop.jpg"));
		
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