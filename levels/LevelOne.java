import javafx.scene.image.Image;

public class LevelOne extends Level 
{

	public LevelOne(int numLevel) 
	{
		super(numLevel);
		Runner tester1 = new Runner(true,1);
		addHittable(tester1);
		tester1.setPos(200, 350);
		
//		Tester tester2= new Tester(true);
//		addHittable(tester2);
//		tester2.setPos(450, 350);
//		
//		Tester tester3=new Tester(false);
//		addHittable(tester3);
//		tester3.setPos(550, 350);
		this.setDefaultBackgroundImage(new Image("file:sprites/backgrounds/old buildings.jpeg"));
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