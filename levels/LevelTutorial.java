import javafx.scene.image.Image;

public class LevelTutorial extends Level 
{

	public LevelTutorial(int numLevel) {
		super(numLevel);
		
		Runner tester = new Runner(true,1);
		this.addHittable(tester);
		tester.setPos(200, 350);
		
		this.setDefaultBackgroundImage(new Image("file:sprites/level_one.jpg"));
		
	}

	@Override
	protected String getDescription() {
		return "Welcome! Here is a quick tutorial to get you started.";
	}

	@Override
	protected String getName() {
		return "Tutorial";
	}
}