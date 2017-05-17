
public class Level1 extends Level
{

	public Level1(int numLevel) 
	{
		super(numLevel);
		
		for(int i = 0;i<5;i++)
		{
			int xPos = (int) (Math.random()*SniperGame.LEVEL_WIDTH);
			int yPos = 350;
			boolean isTarget = Math.round(Math.random())==0?true:false;
			Tester t = new Tester(isTarget);
			addHittable(t);
			t.setPos(xPos, yPos);
			
		}
		Scope scope = new Scope(150);
		addScope(scope);
		setOnMouseTracking(scope);
	}

	@Override
	protected String getDescription() {
		return "Level 1: The Start";
	}

	@Override
	protected String getName() {
		return "Level 1";
	}

}
