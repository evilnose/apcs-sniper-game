import javafx.scene.paint.Color;

public class SimpleRunner extends Hittable
{

	private double scale;
	private double dx;
	
	public SimpleRunner(boolean isTarget, double scale, boolean isFacingRight) {
		super(isTarget);
		
		this.scale = scale;
		if (isFacingRight) {
			if (isTarget)
				setGraphics(SniperGame.runnerTgtR);
			else
				setGraphics(SniperGame.runnerCivR);
		} else {
			if (isTarget)
				setGraphics(SniperGame.runnerTgtL);
			else
				setGraphics(SniperGame.runnerCivL);
		}
		setScale(scale);
		if (isFacingRight)
			dx = 1;
		else
			dx = -1;
		dy = 0;
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}
	
	public void shot() 
	{
		super.shot();
		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
	
	@Override
	public void act(long now) {
		move(dx, dy);
	}

	public double getScale() {
		return scale;
	}
}
