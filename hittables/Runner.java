
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Runner extends Hittable 
{
	private static Image img = new Image("file:sprites/hittables/stick man.gif");

	public Runner(boolean isTarget,double scale)
	{
		super(isTarget,img,scale);
		setImage(img);
		dx = 2;
		dy = 0;
		setHitboxCircle(47.5, 20, 20);
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}
	
	@Override
	public void act(long now) 
	{ 
		int random = (int)(Math.random()*35)-1;
		if(random < 0)
			dx = -dx;
		this.move(dx,dy);
		if(this.isWithinBounds()==false)
		{
			dx = 0;
//			Level lev = (Level) this.getParent();
//			lev.removeHittable(this);
		}
	}
	
	@Override
	public void shot() {
		super.shot();
		
		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
}