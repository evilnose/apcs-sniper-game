public class LevelSeven extends Level 
{
	
	public LevelSeven(int numLevel) 
	{
		super(numLevel);
		levelMessage = "OUR ALIEN FRIENDS ARE HAVING A PICNIC ON THE HILLS. WELL APPARENTLY A LIVE HUMAN IS ON THE PICNIC MENU. "
				+ "TAKE THEM OUT BEFORE THEY DO ANY HARM.";	
		this.setBackground(SniperGame.levelSevenBack);
	}
	
	@Override
	protected void addAllHittables()
	{

		Sitter sitter = new Sitter(true, 0.1);
		this.addHittable(sitter);
		sitter.setPos(275, 320);
		
		Walker walker = new Walker(true, 0.1, false);
		this.addHittable(walker);
		walker.setPos(0, 320);
		
		Walker walkerTwo = new Walker(true, 0.1, true);
		this.addHittable(walkerTwo);
		walkerTwo.setPos(250, 320);
		
		Sleeper sleeper = new Sleeper(true, 0.05);
		this.addHittable(sleeper);
		sleeper.setPos(243, 260);
	}
	

	@Override
	protected String getName() {
		return "Seven";
	}

}