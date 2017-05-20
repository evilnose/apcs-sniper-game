import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;

public abstract class Hittable extends Group
{
	protected ImageView person;
	protected Shape hitbox;
	
	protected boolean isTarget;
	protected boolean isAlive;
	protected boolean isStartled;

	protected double dx;
	protected double dy;

	private double scale;
	
	public Hittable(boolean isTgt)
	{
		super();
		
		person = new ImageView();
		hitbox = new Circle();
		this.getChildren().addAll(person,hitbox);
		scale = 1;
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
	}

	public Hittable(boolean isTgt, Image img, double scale) 
	{
		super();
		
		person = new ImageView(img);
		hitbox = new Circle();
		this.getChildren().addAll(person,hitbox);
		
		this.scale = scale;
		
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
	}

	public abstract void act(long now);
	
	protected void setImage(Image img)
	{
		person.setImage(img);
	}
	
	protected void scale(double scale)
	{
		Circle circle = (Circle)hitbox;
		Scale s = new Scale(scale,scale);
		s.setPivotX(circle.getCenterX());
		s.setPivotY(circle.getCenterY());
		person.getTransforms().add(s);
		hitbox.getTransforms().add(s);
	}
	
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
		person.setX(person.getX() + dx);
		person.setY(person.getY() + dy);
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

	public void setPos(int x, int y)
	{
		double dx = x - person.getX();
		double dy = y - person.getY();
		move(dx, dy);
		this.scale(scale);
	}

	protected boolean isWithinBounds()
	{
		double x = person.getX();
		double y = person.getY();
		if(x+person.getImage().getWidth()<0||x>SniperGame.LEVEL_WIDTH||y+person.getImage().getHeight()<0||y>SniperGame.LEVEL_HEIGHT)
			return false;
		else
			return true;
	}

}
