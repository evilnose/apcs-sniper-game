import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class LevelThree extends Level 
{
	
	public LevelThree(int numLevel) 
	{
		super(numLevel);
		levelMessage = "THIS IS SOME ALIEN JARGON I AM TYPING FOR TRYING OUT THIS FEATURE";	
	}
	
	@Override
	protected void addAllHittables()
	{

		Runner r1 = new Runner(false, 1);
		r1.setPos(375, 320);
		this.addHittable(r1);
//		Circle c = (Circle)(r1.getHitbox());
//		System.out.println(c.getCenterX()+" "+c.getCenterY());
//		System.out.println(r1.getX()+" "+r1.getY());
	}
	
	@Override
	protected String getDescription() {
		return "Hey rookie, let's make your first mission easy. You see that alien sitting alone at the bus stop? Eliminate it"+
				" before it gets away.";
	}

	@Override
	protected String getName() {
		return "Tutorial";
	}

}