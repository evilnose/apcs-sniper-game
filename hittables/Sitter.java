import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sitter extends Hittable {
	
	private double dx;
	private double dy;
	
	private Image img = new Image("file:sprites/sitter_civilian_right.png");
	
	public Sitter(boolean isTarget)
	{
		super(isTarget);
		setImage(img);
		
		dx = 0;
		dy = 0;
		this.setHitboxCircle(200, 40, 17);
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