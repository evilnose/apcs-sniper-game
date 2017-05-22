import javafx.scene.image.Image;

public class LevelOne extends Level 
{

	public LevelOne(Integer numLevel) 
	{
		super(numLevel);
		
		this.setDefaultBackgroundImage(new Image("file:sprites/backgrounds/level_one.jpg"));
	}

	@Override
	protected void addAllHittables() {
		// TODO Auto-generated method stub
		Runner tester1 = new Runner(true,1);
		addHittable(tester1);
		tester1.setPos(200, 250);
		
//		Tester tester2= new Tester(true);
//		addHittable(tester2);
//		tester2.setPos(450, 350);
//		
//		Tester tester3=new Tester(false);
//		addHittable(tester3);
//		tester3.setPos(550, 350);
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