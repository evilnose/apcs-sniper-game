import javafx.scene.image.Image;

public class StreetRunner extends Hittable
{
	private double scale;
	int count = 0;
	private static Image tgtImg = new Image("file:sprites/hittables/civilians/sitter_right.png");
	private final Image civImg = new Image("file:sprites/hittables/civilians/sitter_right.png");
	private final Image startledImage = new Image("file:sprites/hittables/targets/runner_right.gif");
	
	public StreetRunner(boolean isTarget, double scale)
	{
		super(isTarget);
		if (isTarget)
			setGraphics(tgtImg);
		else
			setGraphics(civImg);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(250, 114, 20);
		if(!isTarget)
			hitbox.setStroke(null);
	}

	@Override
	public void act(long now)
	{ 
		if(isStartled==true)
			this.move(dx,dy);
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
			this.setGraphics(startledImage);
			isStartled = true;
			dx = 2;
			dy = 3;
			this.moveHitbox(10, 20);
		}
	}
}
