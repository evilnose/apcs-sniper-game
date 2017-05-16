import javafx.scene.image.Image;

public class Tester extends Hittable 
{
	
	private static final Image IMG = new Image("file:sprites/stick man.gif");

	public Tester(boolean isTarget)
	{
		super(isTarget, IMG);
		setHitboxCircle(47.5, 20, 20);
		dx = 3;
		dy = 0;
	}

	@Override
	public void act(long now) 
	{ 
		int random = (int)(Math.random()*35)-1;
		if(random<0)
			dx = -dx;
//		this.move(dx,dy);
	}
}
