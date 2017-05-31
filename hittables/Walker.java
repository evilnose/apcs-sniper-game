import javafx.scene.shape.Circle;

public class Walker extends Hittable {

	private double scale;	
	
	public Walker(boolean isTgt, double scale, boolean isFacingRight) {
		super(isTgt);
		this.scale = scale;
		this.isFacingRight = isFacingRight;
		setHitboxCircle(250, 115, 20);
		if (isTarget)
		{
			if (isFacingRight) {
				setGraphics(SniperGame.walkerTgtR);
				dx = 1;
			}
			else {
				setGraphics(SniperGame.walkerTgtL);
				this.flipHitboxPos();
				dx = -1;
			}
		}
		else
		{
			if (isFacingRight) {
				setGraphics(SniperGame.walkerCivR);
				dx = 1;
			}
			else {
				setGraphics(SniperGame.walkerCivL);
				this.flipHitboxPos();
				dx = -1;
			}
		}
		this.setScale(scale);
	}
	
	@Override
	public void startle() {
		if (!isStartled && isAlive)
		{
			isStartled = true;
			if (isFacingRight) {
				if (isTarget)
					setGraphics(SniperGame.runnerTgtR);
				else
					setGraphics(SniperGame.runnerCivR);
				this.moveHitbox(3.8 * scale, 4.8 * scale);
				dx = 5;
			} else {
				if (isTarget)
					setGraphics(SniperGame.runnerTgtL);
				else
					setGraphics(SniperGame.runnerCivL);
				this.moveHitbox(-6.0 * scale, 14.8 * scale);
				dx = -5;
			}
		}
	}
	
	@Override
	public void act(long now) 
	{
		if (isFacingRight)
			move(dx * scale, dy);
		else
			move (dx * scale, dy);
		
		Circle c = (Circle)hitbox.getChildren().get(0);

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

	}
}
