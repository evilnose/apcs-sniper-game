import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/*
 *  
Beach with one target running and a few civilians walking/runner/sitting around
To make the user git gud at shooting faster moving targets
Two targets with one sleeper and one sitter next to a highway
To introduce the idea of precedence of shooting
A two-story mall with two targets, one sitter and one walker, each at a different story.
To create a challenge by forcing the player to switch quickly between active targets

 */

public class LevelFive extends Level 
{
	
	public LevelFive(int numLevel) 
	{
		super(numLevel);
		levelMessage = "AN ALIEN SUSPECT IS LAST SEEN RUNNING TOWARDS A BEACH PARTY. GET TO THE WEST BEACH QUICKLY; THEN LOCATE AND ELIMINATE"
				+ " THE SUSPECT BEFORE IT ESCAPES THE SCENE.";	
		BackgroundImage myBI = new BackgroundImage(new Image("file:sprites/backgrounds/level_5.jpg"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		this.setBackground(new Background(myBI));
	}
	
	@Override
	protected void addAllHittables()
	{

		Sitter talkerOne = new Sitter(true, 0.3);
		this.addHittable(talkerOne);
		talkerOne.setPos(150, 240);
		
		Sitter talkerTwo = new Sitter(false, 0.3);
		talkerTwo.faceLeft();
		this.addHittable(talkerTwo);
		talkerTwo.setPos(175, 240);
		
		SimpleRunner target = new SimpleRunner(true, 0.3, false);
		this.addHittable(target);
		target.setPos(1000, 240);
		
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