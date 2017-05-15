
public class LevelTutorial extends Level {

	public LevelTutorial(int numLevel) {
		super(numLevel);
		
		Tester tester = new Tester(true);
		addHittable(tester);
		tester.setPos(200, 350);
		
		Scope scope = new Scope(100);
		addScope(scope);
		setOnAutoCenter(scope);
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
