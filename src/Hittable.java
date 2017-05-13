import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public abstract class Hittable extends Group {

	private boolean isTarget;
	private ImageView graphics;
	private Rectangle hitbox;
	
	private int dx;
	private int dy;
	
	private boolean isAlive;
	private boolean isStartled;
	
	public Hittable(boolean isTgt, Image img, double hbXPos, double hbYPos, double hbWidth, double hbHeight) {
		isTarget = isTgt;
		graphics = new ImageView(img);
		hitbox = new Rectangle(hbWidth, hbHeight);
		getChildren().addAll(graphics, hitbox);
		isAlive = true;
		isStartled = false;
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	private void shot() {
		if (isAlive)
			isAlive = !isAlive;
		
		
	}
	
}
