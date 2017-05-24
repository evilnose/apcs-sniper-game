import javafx.animation.AnimationTimer;
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

	private double startX;
	private double startY;
	protected double dx;
	protected double dy;

	private boolean isFacingRight;
	
	private final Image leftRunnerCiv = new Image("file:sprites/hittables/civilians/runner_left.gif");
	private final Image rightRunnerCiv = new Image("file:sprites/hittables/civilians/runner_right.gif");
	private final Image leftRunnerTgt = new Image("file:sprites/hittables/targets/runner_left.gif");
	private final Image rightRunnerTgt = new Image("file:sprites/hittables/targets/runner_right.gif");
	
	public Hittable(boolean isTgt)
	{
		super();
		
		graphics = new ImageView();
		hitbox = new Circle();
		this.getChildren().addAll(graphics,hitbox);
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
		isFacingRight = true;
	}

	public Hittable(boolean isTgt, Image img) 
	{
		super();
		
		graphics = new ImageView(img);
		hitbox = new Circle();
		this.getChildren().addAll(graphics,hitbox);
		
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
		isFacingRight = true;
	}

	public abstract void act(long now);
	
	protected void setGraphics(Image img)
	{
		graphics.setImage(img);
	}
	
	protected void setScale(double scale)
	{
		// assume hitbox is circle
		Circle circle = Circle.class.cast(this.getHitbox());
		double dx = circle.getCenterX() - getPivotX();
		double dy = circle.getCenterY() - getPivotY();
		graphics.setScaleX(scale);
		graphics.setScaleY(scale);
		hitbox.setScaleX(scale);
		hitbox.setScaleY(scale);
		this.setHitboxPos(getPivotX() + dx * scale, getPivotY() + dy * scale);
		
	}
	
	private double getPivotX() {
		return graphics.getX() + graphics.getImage().getWidth()/2;
	}
	
	private double getPivotY() {
		return graphics.getY() + graphics.getImage().getHeight()/2;
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
	
	private void setHitboxPos(double x, double y) {
		if (Circle.class.isInstance(hitbox)) {
			Circle circle = Circle.class.cast(hitbox);
			circle.setCenterX(x);
			circle.setCenterY(y);
		} else if (Rectangle.class.isInstance(hitbox)) {
			Rectangle rect = Rectangle.class.cast(hitbox);
			rect.setX(x);
			rect.setY(y);
		}
	}
	
	protected void moveHitbox(double dx, double dy) {
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

	public Shape getHitbox() {
		return hitbox;
	}

	public boolean isTarget() {
		return isTarget;
	}
	
	protected void faceLeft() {
		isFacingRight = false;
	}
	
	protected void faceRight() {
		isFacingRight = true;
	}
	
	protected boolean isFacingRight() {
		return isFacingRight;
	}
	
	public void startle() {
		if (!isStartled && isAlive)
		{
			initialStartle();

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
	}

	protected boolean isWithinBounds()
	{
		double x, y;
		if (Circle.class.isInstance(hitbox)) {
			Circle circle = Circle.class.cast(hitbox);
			x = circle.getCenterX();
			y = circle.getCenterY();
			double r = circle.getRadius();
			if (x + r < 0 || x - r > SniperGame.LEVEL_WIDTH|| y + r < 0 || y - r > SniperGame.LEVEL_HEIGHT) {
				return false;
			}
			return true;
		} else if (Rectangle.class.isInstance(hitbox)) {
			Rectangle rect = Rectangle.class.cast(hitbox);
			x = rect.getX();
			y = rect.getY();
			double w = rect.getWidth();
			double h = rect.getHeight();
			if (x + w < 0 || x > SniperGame.LEVEL_WIDTH || y + h < 0 || y > SniperGame.LEVEL_HEIGHT)
				return false;
			return true;
		}
		return false;
	}
	
	protected void initialStartle() {
		isStartled = true;
	}
	
	protected void changeToStartledAnimation() {
		if (isTarget) {
			if (isFacingRight)
				this.setGraphics(rightRunnerTgt);
			else {
				this.setGraphics(leftRunnerTgt);
			}
		} else {
			if (isFacingRight)
				this.setGraphics(rightRunnerCiv);
		
			else
				this.setGraphics(leftRunnerCiv);
		}
	}
	
	protected void displayStartledAnimation(Hittable hittable, Image[] sequence, double delay) {
		final int TOTAL_FRAMES = sequence.length;
		AnimationTimer timer = new AnimationTimer() {
			long lastTime = 0;
			int currFrame = 0;
			@Override
			public void handle(long now) {
				if (System.currentTimeMillis() - lastTime >= delay) {
					hittable.setGraphics(sequence[currFrame]);
					currFrame++;
					lastTime = System.currentTimeMillis();
				}
				if (currFrame >= TOTAL_FRAMES) {
					isStartled = true;
					hittable.changeToStartledAnimation();
					this.stop();
				}
			}
			
		};
		timer.start();
		
	}

}
