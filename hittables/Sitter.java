import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sitter extends Hittable {
	
	private double dx;
	private double dy;
	
	public Sitter(boolean isTgt, Image img, int hbXPos, int hbYPos, int hbLength, int hbWidth) {
		super(isTgt, img, hbXPos, hbYPos, hbLength, hbWidth);
	}
	
	
}
