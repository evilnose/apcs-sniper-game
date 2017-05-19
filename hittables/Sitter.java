import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Sitter extends Hittable {
	
	private double dx;
	private double dy;
	
	private static Image img = new Image("file:sprites/hittables/sitter_civilian_right.png");
	
	public Sitter(boolean isTarget, double scale)
	{
		super(isTarget,img,scale);
		setImage(img);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(200, 40, 17);
//	if(!isTarget)
//			hitbox.setStroke(Color.RED);
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
		
	}
}