import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class StreetWalker extends Hittable
{
	private double scale,quadrant;
	int count = 0;
	private double slope;

	public StreetWalker(boolean isTarget, double scale,double slope,double q)
	{
		super(isTarget);

		this.scale = scale;

		if(q>0)
		{
			if (isTarget)
				this.setGraphics(SniperGame.runnerTgtR);
			else
				this.setGraphics(SniperGame.runnerCivR);
			this.setHitboxCircle(260, 135, 20);
		}
		else
		{
			if (isTarget)
				this.setGraphics(SniperGame.runnerTgtL);
			else 
				this.setGraphics(SniperGame.runnerCivL);
			this.setHitboxCircle(240, 135, 20);
		}
		setScale(scale);

		dx = 0.5;
		dy = slope*dx;
		dx = q*dx;
		if(!isTarget)
			hitbox.setStroke(null);
	}

	@Override
	public void act(long now)
	{ 
			this.move(dx,dy);


			Circle c = (Circle)hitbox;

			if(graphics.getX()+185<=boundX1)
			{
				dx = Math.abs(dx);
				dy = Math.abs(dy);
				this.setGraphics(SniperGame.runnerTgtR);
				this.moveHitbox(graphics.getX()+255-c.getCenterX(),0);

				while(graphics.getX()+185<=boundX1)
					this.move(dx, dy);
			}

			else if(graphics.getX()+graphics.getImage().getWidth()-185 >= boundX2)
			{
				dx = -1*Math.abs(dx);
				dy = -Math.abs(dy);
				this.setGraphics(SniperGame.runnerTgtL);
				this.moveHitbox(graphics.getX()+245-c.getCenterX(),0);

				while(graphics.getX()+graphics.getImage().getWidth()-185>=boundX2)
					this.move(dx, dy);
			}
		if(this.isWithinBounds()==false)
		{
			dx = 0;
			dy = 0;
			Level lev = (Level) this.getParent();
			lev.getChildren().remove(this);
		}
	}

	@Override
	public void startle() 
	{
		if (!isStartled && isAlive)
		{
			isStartled = true;
			dx*=2;
			dy*=2;
		}
	}
}
