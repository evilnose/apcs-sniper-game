import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Hittable extends ImageView {

	protected boolean isTarget;
	protected Shape hitbox;

	protected int dx;
	protected int dy;

	protected boolean isAlive;
	protected boolean isStartled;

	public Hittable(boolean isTgt, Image img) {
		super(img);
		
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
	}

	public abstract void act(long now);

	public void setHitboxRect(double x, double y, double width, double height) {
		hitbox = new Rectangle(x, y, width, height);
		((Rectangle) hitbox).setFill(Color.TRANSPARENT);
		((Rectangle) hitbox).setStroke(Color.RED);
	}

	public void setHitboxCircle(double x, double y, double radius) {
		hitbox = new Circle(x, y, radius);
		((Circle) hitbox).setFill(Color.TRANSPARENT);
		((Circle) hitbox).setStroke(Color.RED);
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
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
		hitbox.setLayoutX(hitbox.getLayoutX() + dx);
		hitbox.setLayoutY(hitbox.getLayoutY() + dy);
	}

	public void setPos(int x, int y) {
		this.setX(x);
		this.setY(y);
		hitbox.setLayoutX(x);
		hitbox.setLayoutY(y);
	}

	protected boolean withinBounds(double d,double e)
	{
		if(d<0||d>SniperGame.LEVEL_WIDTH||e<0||e>SniperGame.LEVEL_HEIGHT)
			return false;
		else
			return true;
	}
	
	

}
