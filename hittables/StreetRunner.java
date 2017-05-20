import javafx.scene.image.Image;

public class StreetRunner extends Runner
{
	private double scale;
	int count = 0;
	private static Image img = new Image("file:sprites/stick man.gif");
	public StreetRunner(boolean isTarget, double scale)
	{
		//Line l = new Line()
		super(isTarget, scale);
		this.scale = scale;
		dx = 1.8;
		dy = -0.9;
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
//		count++;
//		if(count%100==0)
		scale-=0.00002;
			this.scale(scale);
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
