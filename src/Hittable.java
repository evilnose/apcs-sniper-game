import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public abstract class Hittable extends Group {

	private boolean isTarget;
	private ImageView graphics;
	private Node hitbox;
	
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
	
	public void setHitboxRect(double x, double y, double width, double height) {
		getChildren().remove(hitbox);
		hitbox = new Rectangle(x, y, width, height);
		((Rectangle) hitbox).setFill(Color.TRANSPARENT);
		((Rectangle) hitbox).setStroke(Color.RED);
		getChildren().add(hitbox);
	}
	
	public void setHitboxCircle(double x, double y, double radius) {
		getChildren().remove(hitbox);
		hitbox = new Circle(x, y, radius);
		((Circle) hitbox).setFill(Color.TRANSPARENT);
		((Circle) hitbox).setStroke(Color.RED);
		getChildren().add(hitbox);
	}
	
	public Node getHitbox() {
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
