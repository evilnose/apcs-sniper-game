import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Jumper extends Hittable {

	protected int baseY;
	private double scale;
	
	public Jumper(boolean isTarget,int baseY) 
	{
		super(isTarget);
		scale = 1;
		this.baseY = baseY;
		if (isTarget)
			setGraphics(SniperGame.sleeperTgtR);
		else
			setGraphics(SniperGame.sleeperCivR);
		dx = 0;
		dy = 2;
		this.setHitboxCircle(255, 120, 19);
	}
	
	public Jumper(boolean isTarget, double scale,int baseY)
	{
		super(isTarget);
		this.baseY = baseY;
		this.scale = scale;
		if (isTarget)
			setGraphics(SniperGame.sleeperTgtR);
		else
		dx = 0;
		dy = 2;
		this.setHitboxCircle(255, 120, 19);
		this.setScale(scale);
	}
	@Override
	public void act(long now)
	{
		if(this.graphics.getY()+this.graphics.getImage().getHeight()-250<baseY)
			this.move(dx, dy);
		else
		{
			dy = 0;
			double x = this.graphics.getX();
			double y = this.graphics.getY();
			Level l = (Level) this.getParent();
			l.removeHittable(this);
			Walker w = new Walker(true,scale,false);
			w.setPos(x, y);
			l.addHittable(w);
			w.setBounds(100, 325);
			w.moveHitbox(-5, -20);
		}
	}
	
	
}
