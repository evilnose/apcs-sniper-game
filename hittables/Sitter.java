import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Sitter extends Hittable {
	
	private double dx;
	private double dy;
	
	private static Image img = new Image("file:sprites/hittables/civilians/sitter_right.png");
	
	public Sitter(boolean isTarget, double scale)
	{
		super(isTarget,img,scale);
		setGraphics(img);
		dx = 0;
		dy = 0;
		this.setHitboxCircle(250, 112, 20);
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
		
	}
}