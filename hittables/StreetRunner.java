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
		dx = 1.5;
		dy = -1;
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
		count++;
		if(count%100==0)
			this.scale(0.5);
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
