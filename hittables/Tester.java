import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

public class Tester extends Hittable 
{
	
	private final Image img = new Image("file:sprites/stick man.gif");

	public Tester(boolean isTarget)
	{
		super(isTarget);
		setGraphics(img);
		setHitboxCircle(47.5, 20, 20);
		dx = 3;
		dy = 0;
	}

	@Override
	public void act(long now) 
	{
		int timeInSeconds = (int)(now*Math.pow(10,-9));
		if(timeInSeconds%5==0)
			dx = -dx;
		this.move(dx,dy);
	}
}
