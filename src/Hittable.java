import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Hittable extends Actor {

	private boolean isTarget;
	private ImageView graphics;
	private double hbXPos;
	private double hbYPos;
	private double hbLength;
	private double hbWidth;
	
	private int dx;
	private int dy;
	
	public Hittable(boolean isTarget, Image graphics, double hbXPos, double hbYPos, double hbLength, double hbWidth) {
		this.isTarget = isTarget;
		this.graphics = new ImageView(graphics);
		
	}
	
	@Override
	public abstract void act(long now);
}
