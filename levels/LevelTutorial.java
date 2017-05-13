
public class LevelTutorial extends Level {

	public LevelTutorial(int numLevel) {
		super(numLevel);
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
