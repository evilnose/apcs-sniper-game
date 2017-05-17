import javafx.scene.image.Image;

public class LevelTwo extends Level 
{

	public LevelTwo(int numLevel) {
		super(numLevel);
		
		Tester tester1 = new Tester(false);
		addHittable(tester1);
		tester1.setPos(200, 350);
		
		Tester tester2 = new Tester(true);
		addHittable(tester2);
		tester2.setPos(450, 350);
		
		Tester tester3 =new Tester(false); // we can add more tester or runners here because they should be
		                                 //     distracting from the target that are sitting
		addHittable(tester3);
		tester3.setPos(550, 350);
		
		Sitter sitter1 = new Sitter(false);
		addHittable(sitter1);
		sitter1.setPos(550,100);
		
		this.setDefaultBackgroundImage(new Image("file:sprites/cafe.jpg"));
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
