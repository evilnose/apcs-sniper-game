import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class LevelThree extends Level 
{
	
	public LevelThree(int numLevel) 
	{
		super(numLevel);
		levelMessage = "TWO ALIENS FROM THE RIVAL PLANET HAVE COME TO THIS PARK TO DISCUSS PLANS FOR STEALING THIS PLANET'S CLASSIFIED DOCUMENTS. TAKE THEM OUT BEFORE THEY LEAVE!";
		this.setBackground(SniperGame.levelThreeBack);
	}
	
	@Override
	protected void addAllHittables()
	{

		Runner r1 = new Runner(false, 0.5);
		r1.setPos(355, 270);
		r1.setBounds(205, 660);
		this.addHittable(r1);
		
		StreetRunner r2 = new StreetRunner(false, 0.7,1.5,-1);
		r2.setPos(200, 300);
		this.addHittable(r2);
		
		Sitter s1 = new Sitter(false, 0.4);
		this.addHittable(s1);
		s1.setPos(540, 250);
		
		Sitter s2 = new Sitter(true, 0.4);
		this.addHittable(s2);
		s2.setPos(550, 250);
		
		Sleeper s3 = new Sleeper(true,0.3);
		this.addHittable(s3);
		s3.setPos(465, 190);
	}
	
	@Override
	protected String getName() {
		return "Three";
	}

}