import javafx.scene.paint.Color;

public class SimpleRunner extends Hittable {

	private double scale;
	private double dx;
	
	public SimpleRunner(boolean isTarget, double scale, boolean isFacingRight) {
		super(isTarget);
		
		this.scale = scale;
		this.setHitboxCircle(262.2, 133, 20);
		if (isFacingRight) {
			if (isTarget)
				setGraphics(SniperGame.runnerTgtR);
			else
				setGraphics(SniperGame.runnerCivR);
			dx = 5;
		} else {
			if (isTarget)
				setGraphics(SniperGame.runnerTgtL);
			else
				setGraphics(SniperGame.runnerCivL);
			dx = -5;
			this.flipHitboxPos();
		}
		setScale(scale);
		dy = 0;
		if(!isTarget)
			hitbox.setStroke(Color.GREEN);
	}
	
	@Override
	public void act(long now) {
		move(dx * scale, dy);
	}

	public double getScale() {
		return scale;
	}
}
