
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
				this.moveHitbox(3.8, 4.8);
				dx = 5;
			} else {
				if (isTarget)
					setGraphics(SniperGame.runnerTgtL);
				else
					setGraphics(SniperGame.runnerCivL);
				this.moveHitbox(-3.8, 4.8);
				dx = -5;
			}
		}
	}
	
	@Override
	public void act(long now) {
		if (isFacingRight)
			move(dx * scale, dy);
		else
			move (dx * scale, dy);
		
	}
}
