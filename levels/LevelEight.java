import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LevelEight extends Level 
{
	
	public LevelEight(int numLevel) 
	{
		super(numLevel);
		levelMessage = "A GROUP OF ALIENS HAVE SET UP CAMP IN THIS HOTEL AND ARE DISCUSSING THEIR PLANS OF DISRUPTION. KILL THEM BEFORE THEY RUN AWAY!";
		this.setBackground(SniperGame.levelEightBack);
	}
	
	@Override
	protected void addAllHittables()
	{

		Walker w1 = new Walker(false, 0.8,false);
		w1.setPos(650, 20);
		
		Sitter s1 = new Sitter(false,0.8);
		s1.setPos(380, 20);
		
		Sitter s2 = new Sitter(true,0.8);
		s2.setPos(400, 20);
		
		Runner r1 = new Runner(true,1);
		r1.setPos(400, 320);
		
		Sleeper sl1 = new Sleeper(true,1);
		sl1.setPos(-200, 340);
		
		this.addHittable(w1);
		this.addHittable(s1);
		this.addHittable(s2);
		this.addHittable(r1);
		this.addHittable(sl1);
		
		ImageView img = new ImageView(new Image("file:sprites/backgrounds/level_8_framework.png"));	
		this.getChildren().add(img);
	}
	
	@Override
	protected String getName() {
		return "Eight";
	}

}