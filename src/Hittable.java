import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Hittable extends Group {

	private boolean isTarget;
	private ImageView graphics;
	private Rectangle hitbox;
	
	private int dx;
	private int dy;
	
	private boolean isAlive;
	private boolean isStartled;
	
	public Hittable(boolean isTgt) {
		isTarget = isTgt;
		
		isAlive = true;
		isStartled = false;
	}
	
	public void setGraphics(Image img) {
		graphics = new ImageView(img);
		getChildren().add(graphics);
	}
	
	public void setHitbox(double x, double y, double width, double height) {
		hitbox = new Rectangle(x, y, width, height);
		hitbox.setFill(Color.TRANSPARENT);
		hitbox.setStroke(Color.RED);
		getChildren().add(hitbox);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public boolean isTarget() {
		return isTarget;
	}
	
	private void shot() {
		if (isAlive)
			isAlive = !isAlive;
			
	}
	
}
