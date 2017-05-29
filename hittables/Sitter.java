public class Sitter extends Hittable {
	
	private double dx;
	private double dy;

	private double scale;
	
	public Sitter(boolean isTarget)
	{
		super(isTarget);
		if (isTarget)
			setGraphics(SniperGame.sitterTgtR);
		else
			setGraphics(SniperGame.sitterCivR);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(250, 100, 20);
		if(!isTarget)
			hitbox.setStroke(null);
		scale = 1;
	}
	
	public Sitter(boolean isTarget, double scale)
	{
		super(isTarget);
		if (isTarget)
			setGraphics(SniperGame.sitterTgtR);
		else
			setGraphics(SniperGame.sitterCivR);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(250, 112, 20);
		setScale(scale);
		this.scale = scale;
		if(!isTarget)
			hitbox.setStroke(null);
	}
	
	public void shot() 
	{
		super.shot();
		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
	
	@Override
	public void act(long now) 
	{
		if (isStartled) {
			if (isFacingRight())
				move(3.5, 0);
			else
				move(-3.5, 0);
		}
	}
	
	@Override
	public void startle() {
		if (!isStartled && isAlive) {
			initialStartle();
		}
	}
	
	@Override
	public void initialStartle() {
		if (isTarget)
			displayStartledAnimation(this, SniperGame.startledSitterTgtR, 100);
		else
			displayStartledAnimation(this, SniperGame.startledSitterCivR, 100);
		moveHitbox(12.2 * scale, 21 * scale);
	}

	
}