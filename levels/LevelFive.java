import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;


public class LevelFive extends Level 
{
	
	public LevelFive(int numLevel) 
	{
		super(numLevel);
		levelMessage = "THREE ALIEN SUSPECTS ARE LAST SEEN RUNNING TOWARDS A BEACH PARTY. GET TO THE WEST BEACH QUICKLY; THEN LOCATE AND ELIMINATE"
				+ " THE SUSPECTS BEFORE THEY ESCAPES THE SCENE.";	
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
		
		Walker walker = new Walker(true, 0.3, false);
		this.addHittable(walker);
		walker.setPos(555, 240);
		
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