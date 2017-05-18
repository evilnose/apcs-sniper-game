import javafx.scene.image.Image;

public class LevelThree extends Level
{
	public LevelThree(int numLevel) 
	{
		super(numLevel);		
		this.setDefaultBackgroundImage(new Image("file:sprites/level_three.jpg"));
		Sitter r1 = new Sitter(true,2);
//		Runner r1 = new Runner(true,2);
		addHittable(r1);
		r1.setPos(500, 200);
	}

	@Override
	protected String getDescription() {
		
		return "Level 3: The Dark Alleys";
	}

	@Override
	protected String getName() {
		
		return "Level 3";
	}

}
