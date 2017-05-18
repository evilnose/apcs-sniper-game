import javafx.scene.image.Image;

public class StreetRunner extends Runner
{
	int scale = 1;
	private static Image img = new Image("file:sprites/stick man.gif");
	public StreetRunner(boolean isTarget, double scale)
	{
		super(isTarget, scale);
		dx = 0;
		dy = 0;
	}
	
	@Override
	public void act(long now) 
	{ 
		this.move(dx,dy);
		if(this.isWithinBounds()==false)
		{
			Level lev = (Level) this.getParent();
			lev.removeHittable(this);
		}
		this.scale(scale--);
	}
	
	@Override
	public void startle() {
		if (!isStartled && isAlive)
		{
			dx*=2;
			dy*=2;
		}
	}

}
