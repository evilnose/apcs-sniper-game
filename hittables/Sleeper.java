import javafx.scene.paint.Color;

public class Sleeper extends Hittable {

	protected int i = 3;
	
	public Sleeper(boolean isTarget) {
		super(isTarget);
		System.out.println(i);
		if (isTarget)
			setGraphics(SniperGame.sleeperTgtR);
		else
			setGraphics(SniperGame.sleeperCivR);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(255, 120, 19);
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}
	
	public Sleeper(boolean isTarget, double scale) {
		super(isTarget);
		if (isTarget)
			setGraphics(SniperGame.sleeperTgtR);
		else
			setGraphics(SniperGame.sleeperTgtL);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(255, 120, 19);
		this.setScale(scale);
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}

	@Override
	public void act(long now) {
		// TODO this dude is sleeping so he does absolutely nothing lol
	}
}
