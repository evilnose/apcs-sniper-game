
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Runner extends Hittable 
{
	private final Image img;
	private Runner thisRunner;

	public Runner(boolean isTarget,double scale)
	{
		super(isTarget);
		thisRunner = this;
		if (isTarget)
		{
			img = new Image("file:sprites/hittables/targets/runner_left.gif");
			setHitboxCircle(240,135, 20);
		}
		else
		{
			img = new Image("file:sprites/hittables/civilians/runner_right.gif");
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
		int random = (int)(Math.random()*35)-1;
		if(random < 0)
		{
			dx = -dx;
		}
		this.move(dx,dy);
		if(this.isWithinBounds()==false)
		{
			dx = 0;
			Level lev = (Level) this.getParent();
			//lev.removeHittable(this);
			lev.getChildren().remove(this);
		}
	}
	
	@Override
	protected void initialStartle() {
		isStartled = true;
		// TODO set animation
	}

	@Override
	public void shot() {
		super.shot();

		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
}