import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class LevelSeven extends Level 
{
	
	public LevelSeven(int numLevel) 
	{
		super(numLevel);		
		//levelMessage = "AN ALIEN SUSPECT IS SEEN IN THE MOVIE THEATER. PROTECT THE MOVIEGOERS AND ELIMINATE THE ALIEN"
		//+ " THE SUSPECT BEFORE IT ESCAPES THE SCENE.";	
	    levelMessage=".";
		BackgroundImage myBI = new BackgroundImage(new Image("file:sprites/backgrounds/level_7.jpeg"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
	}
	
	@Override
	protected void addAllHittables()
	{

		Sitter sitter = new Sitter(true, 0.4);
		this.addHittable(sitter);
		sitter.setPos(400, 100);
		
		Runner r1 = new Runner(false,1);
		r1.setPos(100, 10);
		this.addHittable(r1);
		
	
		
	}
	
	@Override
	protected String getDescription() {
		return "This is level seven";
	}

	@Override
	protected String getName() {
		return "Level 7";
	}

}