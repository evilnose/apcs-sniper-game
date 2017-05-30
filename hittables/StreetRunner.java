import javafx.scene.image.Image;
import javafx.scene.shape.Circle;

public class StreetRunner extends Hittable
{
	private double scale,quadrant;
	int count = 0;
	private double slope;
	private static Image tgtImgRight = new Image("file:sprites/hittables/targets/sitter_right.png");
	private static Image tgtImgLeft = new Image("file:sprites/hittables/targets/sitter_left.png");
	private final Image civImgRight = new Image("file:sprites/hittables/civilians/sitter_right.png");
	private final Image civImgLeft = new Image("file:sprites/hittables/civilians/sitter_left.png");
	private final Image startledImageRight = new Image("file:sprites/hittables/targets/runner_right.gif");
	private final Image startledImageLeft = new Image("file:sprites/hittables/targets/runner_left.gif");
	
	public StreetRunner(boolean isTarget, double scale,double slope,double q)
	{
		super(isTarget);
		
		this.scale = scale;
		
		if (isTarget)
		{
			if(q>0)
				setGraphics(tgtImgRight);
			else
				setGraphics(tgtImgLeft);
			this.setHitboxCircle(250, 114, 20);
		}
		else
		{
			if(q>0)
				setGraphics(civImgRight);
			else
				setGraphics(civImgLeft);

			this.setHitboxCircle(250, 114, 20);
		}
		setScale(scale);
		
		dx = 0;
		dy = 0;
		quadrant = q;
		this.slope = slope;
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

	public void shot() 
	{
		super.shot();
		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
	
	@Override
	public void startle() 
	{
		if (!isStartled && isAlive)
		{
			Circle c = (Circle)hitbox;

			double deltaX = (1-scale)*10;
			if(quadrant>0)
			{
				this.setGraphics(startledImageRight);
				this.moveHitbox(scale*10-10,scale*19-20);
			}
			else
			{
				this.setGraphics(startledImageLeft);
				this.moveHitbox(scale*10-25,scale*19-20);
			}
			isStartled = true;
			dx = 2;
			dy = dx*slope;
			dx = dx*quadrant;
			this.moveHitbox(10, 20);
		}
	}
}
