
import javafx.scene.image.Image;

public class Tester extends Hittable 
{
	private Image img = new Image("file:sprites/stick man.gif");

	public Tester(boolean isTarget)
	{
		super(isTarget);
		setImage(img);
		setHitboxCircle(47.5, 20, 20);
		dx = 2;
		dy = 0;
	}

	@Override
	public void act(long now) 
	{ 
		int random = (int)(Math.random()*35)-1;
		if(random < 0)
			dx = -dx;
		this.move(dx,dy);
		if(this.isWithinBounds()==false)
		{
			Level lev = (Level) this.getParent();
			lev.removeHittable(this);
		}
	}
	
	@Override
	public void shot() {
		super.shot();
		
		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
}