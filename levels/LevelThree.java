import javafx.scene.image.Image;

public class LevelThree extends Level
{
	public LevelThree(int numLevel) 
	{
		super(numLevel);		
		this.setDefaultBackgroundImage(new Image("file:sprites/backgrounds/level_three.jpg"));
			}
	
	public void addAllHittables() {
		StreetRunner r1 = new StreetRunner(true,2);
		addHittable(r1);
		r1.setPos(100, 350);
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
