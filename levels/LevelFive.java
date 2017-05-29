public class LevelFive extends Level 
{
	
	public LevelFive(int numLevel) 
	{
		super(numLevel);
		levelMessage = "THIS IS SOME ALIEN JARGON I AM TYPING FOR TRYING OUT THIS FEATURE";	
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
		return "This is level five.";
	}

	@Override
	protected String getName() {
		return "Level 5";
	}

}