
public class LevelOne extends Level 
{
	
	public LevelOne(int numLevel) 
	{
		super(numLevel);
		levelMessage = "A NEWLY ARRIVED ALIEN IS WAITING AT THE BUS STOP. ELIMINATE IT BEFORE IT GETS AWAY AND INFILTRATE THE CITY."
				+ " BEWARE: THEY RUN FAST.";	
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
		return "This is the tutorial.";
	}

	@Override
	protected String getName() {
		return "Tutorial";
	}

}