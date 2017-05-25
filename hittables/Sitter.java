import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Sitter extends Hittable {
	
	private double dx;
	private double dy;
	
	private final Image civImg = new Image("file:sprites/hittables/civilians/sitter_right.png");
	private final Image tgtImg = new Image("file:sprites/hittables/targets/sitter_right.png");
	private final Image[] startledCivImgs = SniperGame.decodeGifToImages("file:sprites/hittables/civilians/startled_sitter_right.gif");
	private final Image[] startledTgtImgs = SniperGame.decodeGifToImages("file:sprites/hittables/targets/startled_sitter_right.gif");
	
	public Sitter(boolean isTarget)
	{
		super(isTarget);
		if (isTarget)
			setGraphics(tgtImg);
		else
			setGraphics(civImg);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(250, 112, 20);
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}
	
	public Sitter(boolean isTarget, double scale)
	{
		super(isTarget);
		if (isTarget)
			setGraphics(tgtImg);
		else
			setGraphics(civImg);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(250, 112, 20);
		this.setScale(scale);
		setScale(scale);
		if(!isTarget)
			hitbox.setStroke(Color.BLACK);
	}
	
	public void shot() {
		super.shot();
		
		Level lvl = (Level)getParent();
		lvl.removeHittable(this);
	}
	
	@Override
	public void act(long now) 
	{
		// TODO Auto-generated method stub
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
		displayStartledAnimation(this, startledTgtImgs, 100);
		moveHitbox(4.8, 8.8);
	}
	
}