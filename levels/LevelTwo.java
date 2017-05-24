import javafx.scene.image.Image;

public class LevelTwo extends Level 
{

	public LevelTwo(Integer numLevel) {
		super(numLevel);
		levelMessage = "THIS IS SOME ALIEN JARGON I AM TYPING FOR TRYING OUT THIS FEATURE";
	}
	
	@Override
	protected void addAllHittables() {
		Runner tester1 = new Runner(false,1);
		addHittable(tester1);
		tester1.setPos(200, 250);
		
		Runner tester2 = new Runner(true,1);
		addHittable(tester2);
		tester2.setPos(450, 250);
		
		Runner tester3 =new Runner(false,1); // we can add more tester or runners here because they should be
		                                 //     distracting from the target that are sitting
		addHittable(tester3);
		tester3.setPos(550, 250);
		
		Sitter sitter1 = new Sitter(false,0.5);
		addHittable(sitter1);
		sitter1.setPos(550,100);
		this.setDefaultBackgroundImage(new Image("file:sprites/backgrounds/level_two.jpg"));
	}

	@Override
	protected String getDescription() {
		
		return "Level 2: The Jeopardy";
	}

	@Override
	protected String getName() {
		
		return "Level 2";
	}

}
