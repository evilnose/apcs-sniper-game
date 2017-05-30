
public class Walker extends Hittable {

	private double scale;	
	
	public Walker(boolean isTgt, double scale) {
		super(isTgt);
		this.scale = scale;
		if (isTarget)
		{
			setGraphics(SniperGame.walkerTgtR);
			setHitboxCircle(260,133, 20);
		}
		else
		{
			setGraphics(SniperGame.walkerCivR);
			setHitboxCircle(240,135,20);
		}
	}

	public void shot() 
	{
		super.shot();
		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}
}
