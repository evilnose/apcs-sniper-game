import javafx.scene.image.Image;

public class LevelOne extends Level 
{
	
	public LevelOne(int numLevel) 
	{
		super(numLevel);
		levelMessage = "A SUICIDE BOMBER WITH EXPLOSIVES STRAPPED TO HIS CHEST IS STANDING AT THIS BUS STOP. KILL HIM BEFORE HE BOARDS HIS TARGET BUS. BEWARE! HE RUNS FAST.";	
	}
	
	@Override
	protected void addAllHittables()
	{

		Sitter sitter = new Sitter(true, 0.4);
		this.addHittable(sitter);
		sitter.setPos(175, 220);
		
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