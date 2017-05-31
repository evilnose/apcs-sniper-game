import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class StreetRunner extends Hittable
{
	private double scale,quadrant;
	int count = 0;
	private double slope;

	public StreetRunner(boolean isTarget, double scale,double slope,double q)
	{
		super(isTarget);

		this.scale = scale;

		if (isTarget)
		{
			if(q>0)
				setGraphics(SniperGame.sitterTgtR);
			else
				setGraphics(SniperGame.sitterTgtL);
			this.setHitboxCircle(250, 114, 20);
		}
		else
		{
			if(q>0)
				setGraphics(SniperGame.sitterCivR);
			else
				setGraphics(SniperGame.sitterCivL);

			this.setHitboxCircle(250, 114, 20);
		}
		setScale(scale);

		dx = 0;
		dy = 0;
		quadrant = q;
		this.slope = slope;
	}

	@Override
	public void act(long now)
	{ 
		if(isStartled==true)
		{
			this.move(dx,dy);


			Circle c = (Circle)hitbox.getChildren().get(0);

			if(graphics.getX()+185<=boundX1)
			{
				dx = Math.abs(dx);
				dy = Math.abs(dy);
				if (isTarget)
					this.setGraphics(SniperGame.runnerTgtR);
				else
					this.setGraphics(SniperGame.runnerCivR);
				this.moveHitbox(graphics.getX()+255-c.getCenterX(),0);

				while(graphics.getX()+185<=boundX1)
					this.move(dx, dy);
			}

			else if(graphics.getX()+graphics.getImage().getWidth()-185 >= boundX2)
			{
				dx = -1*Math.abs(dx);
				dy = -Math.abs(dy);
				if (isTarget)
					this.setGraphics(SniperGame.runnerTgtL);
				else
					this.setGraphics(SniperGame.runnerCivL);
				this.moveHitbox(graphics.getX()+245-c.getCenterX(),0);

				while(graphics.getX()+graphics.getImage().getWidth()-185>=boundX2)
					this.move(dx, dy);
			}
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
			Circle c = (Circle)hitbox.getChildren().get(0);

			double deltaX = (1-scale)*10;
			if(quadrant>0)
			{
				if (isTarget)
					this.setGraphics(SniperGame.runnerTgtR);
				else
					this.setGraphics(SniperGame.runnerCivR);
				this.moveHitbox(scale*10-10,scale*19-20);
			}
			else
			{
				if (isTarget)
					this.setGraphics(SniperGame.runnerTgtL);
				else
					this.setGraphics(SniperGame.runnerCivL);
				this.moveHitbox(scale*10-5,scale*19-20);
			}
			isStartled = true;
			dx = 2;
			dy = dx*slope;
			dx = dx*quadrant;
			this.moveHitbox(10, 20);
		}
	}
}
