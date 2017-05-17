import javafx.scene.image.Image;

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
			Runner t = new Runner(isTarget);
			addHittable(t);
			t.setPos(xPos, yPos);

		}

		this.setDefaultBackgroundImage(new Image("file:sprites/skyscrapers.jpg"));
	}

	@Override
	protected String getDescription() 
	{
		return "Level 1: The Start";
	}

	@Override
	protected String getName() {
		return "Level 1";
	}

}
