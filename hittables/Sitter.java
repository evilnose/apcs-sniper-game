import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sitter extends Hittable {
	
	private double dx;
	private double dy;
	
	public Sitter(boolean isTarget, Image graphics, int hbXPos, int hbYPos, int hbLength, int hbWidth) {
		super(isTarget, graphics, hbXPos, hbYPos, hbLength, hbWidth);
	}
	
	@Override
	public void act(long now) {
		
	}
	
	
}
