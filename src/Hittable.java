import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;

public abstract class Hittable extends Group
{
	protected ImageView graphics;
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
		
		graphics = new ImageView();
		hitbox = new Circle();
		this.getChildren().addAll(graphics,hitbox);
		scale = 1;
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
	}
	
	public Hittable(boolean isTgt, double scale) {
		super();
		
		graphics = new ImageView();
		hitbox = new Circle();
		this.getChildren().addAll(graphics, hitbox);
		this.scale = scale;
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
	}

	public Hittable(boolean isTgt, Image img, double scale) 
	{
		super();
		
		graphics = new ImageView(img);
		hitbox = new Circle();
		this.getChildren().addAll(graphics,hitbox);
		
		this.scale = scale;
		
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
	}

	public abstract void act(long now);
	
	protected void setGraphics(Image img)
	{
		graphics.setImage(img);
	}
	
	protected void setScale(double scale)
	{
		Circle circle = (Circle)hitbox;
		Scale s = new Scale(scale,scale);
		s.setPivotX(circle.getCenterX());
		s.setPivotY(circle.getCenterY());
		graphics.getTransforms().add(s);
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
		graphics.setX(graphics.getX() + dx);
		graphics.setY(graphics.getY() + dy);
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
		double dx = x - graphics.getX();
		double dy = y - graphics.getY();
		move(dx, dy);
		this.setScale(scale);
	}

	protected boolean isWithinBounds()
	{
		double x = graphics.getX();
		double y = graphics.getY();
		if(x+graphics.getImage().getWidth()<0||x>SniperGame.LEVEL_WIDTH||y+graphics.getImage().getHeight()<0||y>SniperGame.LEVEL_HEIGHT)
			return false;
		else
			return true;
	}

}
