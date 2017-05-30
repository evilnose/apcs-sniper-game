
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Runner extends Hittable 
{
	private final Image img;
	private double scale;

	public Runner(boolean isTarget,double scale)
	{
		super(isTarget);

		this.scale = scale;
		if (isTarget)
		{
			img = SniperGame.runnerTgtR;
			setHitboxCircle(260,133, 20);
		}
		else
		{
			img = SniperGame.runnerCivR;
			setHitboxCircle(260,135,20);
		}
		setGraphics(img);
		setScale(scale);
		dx = 2;
		dy = 0;
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}

	@Override
	public void act(long now) 
	{ 

		Circle c = (Circle)hitbox;

		if(graphics.getX()+185<=boundX1)
		{
			dx = Math.abs(dx);

			this.setGraphics(SniperGame.runnerTgtR);
			this.moveHitbox(graphics.getX()+255-c.getCenterX(),0);

			while(graphics.getX()+185<=boundX1)
				this.move(dx, dy);
		}

		else if(graphics.getX()+graphics.getImage().getWidth()-185 >= boundX2)
		{
			dx = -1*Math.abs(dx);

			this.setGraphics(SniperGame.runnerTgtL);
			this.moveHitbox(graphics.getX()+245-c.getCenterX(),0);

			while(graphics.getX()+graphics.getImage().getWidth()-185>=boundX2)
				this.move(dx, dy);
		}

		int random = (int)(Math.random()*200)-5;
		if(random<0)
		{
			dx = -dx;
			double deltaX = (1-scale)*10;


			if(dx>0)
			{
				if (isTarget)
					this.setGraphics(SniperGame.runnerTgtR);
				else
					this.setGraphics(SniperGame.runnerCivR);
				this.moveHitbox(graphics.getX()+260-deltaX-c.getCenterX(),0);
			}
			else
			{
				if (isTarget)
					this.setGraphics(SniperGame.runnerTgtL);
				else 
					this.setGraphics(SniperGame.runnerCivL);
				this.moveHitbox(graphics.getX()+240+deltaX-c.getCenterX(),0);
			}
		}

		this.move(dx,dy);

		if(this.isWithinBounds()==false)
		{
			dx = 0;
			Level lev = (Level) this.getParent();
			lev.getChildren().remove(this);
		}
	}

	@Override
	protected void initialStartle() {
		isStartled = true;
		dx = 2*dx;
	}
}