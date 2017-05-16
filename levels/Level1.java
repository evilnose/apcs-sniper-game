
public class Level1 extends Level
{

	public Level1(int numLevel) 
	{
		super(numLevel);
		
		for(int i = 0;i<10;i++)
		{
			int xPos = (int) (Math.random()*SniperGame.LEVEL_WIDTH);
			int yPos = 350;
			boolean isTarget = Math.round(Math.random())==0?true:false;
			Tester t = new Tester(isTarget);
			addHittable(t);
			t.setPos(xPos, yPos);
			
		}
//		Tester tester1 = new Tester(false);
//		addHittable(tester1);
//		tester1.setPos(200, 350);
//		
//		Tester tester2= new Tester(true);
//		addHittable(tester2);
//		tester2.setPos(450, 350);
//		
//		Tester tester3=new Tester(false);
//		addHittable(tester3);
//		tester3.setPos(550, 350);
//		
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
