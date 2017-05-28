
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

public class Runner extends Hittable 
{
	private final Image img;
	private Runner thisRunner;
	private static long start;
	private long delay;

	public Runner(boolean isTarget,double scale)
	{
		super(isTarget);
		thisRunner = this;
		if (isTarget)
		{
			img = new Image("file:sprites/hittables/targets/runner_left.gif");
			setHitboxCircle(0,0, 20);
		}
		else
		{
			img = new Image("file:sprites/hittables/civilians/runner_right.gif");
			setHitboxCircle(260,135,20);
		}
		setGraphics(img);
		setScale(scale);
		//dx = 2;
		dy = 0;
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}

	@Override
	public void act(long now) 
	{ 
		Circle c = (Circle)hitbox;

		if(graphics.getX()<=boundX1)
		{
			dx = Math.abs(dx);
			while(graphics.getX()<=boundX1)
				this.move(dx, dy);
		}

		else if(graphics.getX() >= boundX2)
		{
			dx = -1*Math.abs(dx);
			while(graphics.getX()>=boundX2)
				this.move(dx, dy);
		}

		int random = (int)(Math.random()*100)-5;
		if(random<0)
		{
			dx = -dx;
		}
		
		if(dx>0)
		{
			this.setGraphics(new Image("file:sprites/hittables/targets/runner_right.gif"));
			this.moveHitbox(graphics.getX()+255-c.getCenterX(),0);
		}
		else
		{
			this.setGraphics(new Image("file:sprites/hittables/targets/runner_left.gif"));
			this.moveHitbox(graphics.getX()+245-c.getCenterX(),0);
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
		// TODO set animation
	}

	public void startDelay()
	{
		delay = (long) (5*Math.pow(10, 9));
	}


	@Override
	public void shot() {
		super.shot();

		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
}