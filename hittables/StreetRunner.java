import javafx.scene.image.Image;

public class StreetRunner extends Runner
{
	private double scale;
	int count = 0;
	private static Image img = new Image("file:sprites/stick man.gif");
	public StreetRunner(boolean isTarget, double scale)
	{
		super(isTarget, scale);
		this.scale = scale;
		dx = 0;
		dy = 0;
	}

	@Override
	public void act(long now)
	{ 
		if(isStartled==true)
			this.move(dx,dy);
		if(this.isWithinBounds()==false)
		{
			Level lev = (Level) this.getParent();
			lev.removeHittable(this);
		}
	}

	@Override
	public void startle() 
	{
		if (!isStartled && isAlive)
		{
			isStartled = true;
			dx = 2;
			dy = 2;
		}
	}
}
