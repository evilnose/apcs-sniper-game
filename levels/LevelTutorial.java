
public class LevelTutorial extends Level {

	public LevelTutorial(int numLevel) {
		super(numLevel);
		addHittable(new Tester(true));
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
