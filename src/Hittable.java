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
	
	public Hittable(boolean isTgt)
	{
		super();
		
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
	}

	public Hittable(boolean isTgt, Image img) 
	{
		super(img);
		
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
	}

	public abstract void act(long now);
	
	protected void setHitboxRect(double x, double y, double width, double height) {
		hitbox = new Rectangle(x, y, width, height);
		hitbox.setFill(Color.TRANSPARENT);
		hitbox.setStroke(Color.RED);
	}
	
	protected void setHitboxCircle(double x, double y, double radius) {
		hitbox = new Circle(x, y, radius);
		hitbox.setFill(Color.TRANSPARENT);
		hitbox.setStroke(Color.RED);
	}

	public Shape getHitbox() {
		return hitbox;
	}

	public boolean isTarget() {
		return isTarget;
	}
	
	public void startle() {
		if (!isStartled && isAlive)
		{
			isStartled = true;
			dx++;
			dx = -dx;
		}
	}

	public void shot() {
		if(isAlive)
			isAlive = !isAlive;
	}

	protected void move(double dx, double dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
		if (Circle.class.isInstance(hitbox)) {
			Circle circle = Circle.class.cast(hitbox);
			circle.setCenterX(circle.getCenterX() + dx);
			circle.setCenterY(circle.getCenterY() + dy);
		} else if (Rectangle.class.isInstance(hitbox)) {
			Rectangle rect = Rectangle.class.cast(hitbox);
			rect.setX(rect.getX() + dx);
			rect.setY(rect.getY() + dy);
		}
		
	}

	public void setPos(int x, int y) {
		double dx = x - getX();
		double dy = y - getY();
		move(dx, dy);
	}

	protected boolean isWithinBounds()
	{
		double x = this.getLayoutX();
		double y = this.getLayoutY();
		if(x<0||x>SniperGame.LEVEL_WIDTH||y<0||y>SniperGame.LEVEL_HEIGHT)
			return false;
		else
			return true;
	}

}
