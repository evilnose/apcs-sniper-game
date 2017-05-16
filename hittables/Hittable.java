import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Hittable extends Group {

	protected boolean isTarget;
	protected ImageView graphics;
	protected Shape hitbox;

	protected int dx;
	protected int dy;

	protected boolean isAlive;
	protected boolean isStartled;

	public Hittable(boolean isTgt) {
		isTarget = isTgt;

		isAlive = true;
		isStartled = false;
	}

	public abstract void act(long now);
	
	public void setGraphics(Image img)
	{
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

	protected void shot() {
		if (isAlive)
			isAlive = !isAlive;

	}

	protected void move(int dx, int dy) {
		graphics.setX(graphics.getX() + dx);
		graphics.setY(graphics.getY() + dy);
		hitbox.setLayoutX(hitbox.getLayoutX() + dx);
		hitbox.setLayoutY(hitbox.getLayoutY() + dy);
	}

	public void setPos(int x, int y) {
		graphics.setX(x);
		graphics.setY(y);
		hitbox.setLayoutX(x);
		hitbox.setLayoutY(y);
	}

	protected boolean isWithinBounds()
	{
		double currX = graphics.getX()+graphics.getFitWidth();
		double currY = graphics.getY()+graphics.getFitHeight();
		if(currX<0||currX>SniperGame.LEVEL_WIDTH||currY<0||currY>SniperGame.LEVEL_HEIGHT)
			return false;
		else
			return true;
	}
	
	

}
