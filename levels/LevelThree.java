import javafx.scene.image.Image;

public class LevelThree extends Level
{
	public LevelThree(int numLevel) 
	{
		super(numLevel);		
		this.setDefaultBackgroundImage(new Image("file:sprites/backgrounds/level_three.jpg"));
		levelMessage = "MISSION 3!\nA MYSTERIOUS STORMTROOPER CARRYING A GUN IS WALKING DOWN THIS BUSTLING MARKET IN JAPAN. PUT HIM TO SLEEP BEFORE HE STRIKES OFF THE NEXT ITEM ON HIS CHECKLIST.";
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
