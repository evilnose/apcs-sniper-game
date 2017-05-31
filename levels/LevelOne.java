import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.shape.Circle;

public class LevelOne extends Level 
{
	
	public LevelOne(int numLevel) 
	{
		super(numLevel);
		levelMessage = "A NEWLY ARRIVED ALIEN IS WAITING AT THE BUS STOP. ELIMINATE IT BEFORE IT GETS AWAY AND INFILTRATE THE CITY."
				+ " BEWARE: THEY RUN FAST.";	
		this.setBackground(SniperGame.levelOneBack);
	}
	
	@Override
	protected void addAllHittables()
	{
		Sitter sitter = new Sitter(true, 0.5);
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
		return "One";
	}

}