import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Sleeper extends Hittable {
	
	private final Image civImg = new Image("file:sprites/hittables/civilians/sleeper_right.png");
	private final Image civImgL = new Image("file:sprites/hittables/civilians/sleeper_left.png");
	private final Image tgtImg = new Image("file:sprites/hittables/targets/sleeper_right.png");
	private final Image tgtImgL = new Image("file:sprites/hittables/targets/sleeper_left.png");
	
	public Sleeper(boolean isTarget) {
		super(isTarget);
		if (isTarget)
			setGraphics(tgtImg);
		else
			setGraphics(civImg);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(255, 120, 19);
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}
	
	public Sleeper(boolean isTarget, double scale) {
		super(isTarget);
		if (isTarget)
			setGraphics(tgtImg);
		else
			setGraphics(civImg);
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
	
	@Override
	protected void faceLeft() {
		super.faceLeft();
		if (isTarget)
			setGraphics(tgtImgL);
		else 
			setGraphics(civImgL);
	}
}
