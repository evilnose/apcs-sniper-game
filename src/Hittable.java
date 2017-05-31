import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Hittable extends Group
{
	protected Group hitbox;
	protected ImageView graphics;

	protected boolean isTarget;
	protected boolean isAlive;
	protected boolean isStartled;

	protected double dx;
	protected double dy;

	protected Level parent;

	protected double boundX1;
	protected double boundX2;

	protected boolean isFacingRight;

	private final Image leftRunnerCiv = new Image("file:sprites/hittables/civilians/runner_left.gif");
	private final Image rightRunnerCiv = new Image("file:sprites/hittables/civilians/runner_right.gif");
	private final Image leftRunnerTgt = new Image("file:sprites/hittables/targets/runner_left.gif");
	private final Image rightRunnerTgt = new Image("file:sprites/hittables/targets/runner_right.gif");

	public Hittable(boolean isTgt)
	{
		super();
		
		graphics = new ImageView();
		hitbox = new Group(new Circle());
		
		this.getChildren().add(graphics);
		
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
		isFacingRight = true;
		boundX1 = 0;
		boundX2 = SniperGame.LEVEL_WIDTH;
	}

	public Hittable(boolean isTgt, Image img) 
	{
		super();

		graphics = new ImageView(img);
		hitbox = new Group(new Circle());

		this.getChildren().add(graphics);
		
		isTarget = isTgt;
		isAlive = true;
		isStartled = false;
		isFacingRight = true;
		
		boundX1 = 0;
		boundX2 = SniperGame.LEVEL_WIDTH;
	}

	public abstract void act(long now);

	protected void setGraphics(Image img)
	{
		graphics.setImage(img);
	}

	public void setBounds(double x,double y)
	{
		this.boundX1 = x;
		this.boundX2 = y;
	}

	protected void setScale(double scale)
	{
		Circle circle = (Circle)this.getHitbox().getChildren().get(0);
		this.getHitbox().getChildren().remove(0);
		double dx = circle.getCenterX() - getPivotX();
		double dy = circle.getCenterY() - getPivotY();

		graphics.setScaleX(scale);
		graphics.setScaleY(scale);

		circle.setScaleX(scale);
		circle.setScaleY(scale);
		this.getHitbox().getChildren().add(circle);

		this.setHitboxPos(getPivotX() + dx * scale, getPivotY() + dy * scale);
	}

	private double getPivotX() {
		return graphics.getX() + graphics.getImage().getWidth()/2;
	}

	private double getPivotY() {
		return graphics.getY() + graphics.getImage().getHeight()/2;
	}

	protected void setHitboxCircle(double x, double y, double radius) 
	{
		Circle c = new Circle(x , y, radius);
		c.setFill(Color.TRANSPARENT);
		c.setStroke(Color.RED);
		hitbox.getChildren().remove(0);
		hitbox.getChildren().add(c);
		this.getChildren().add(hitbox);
	}

	private void setHitboxPos(double x, double y) 
	{
		Circle circle = Circle.class.cast(hitbox.getChildren().get(0));
		circle.setCenterX(x);
		circle.setCenterY(y);
	}

	protected void moveHitbox(double dx, double dy) 
	{
		Circle circle = Circle.class.cast(hitbox.getChildren().get(0));
		circle.setCenterX(circle.getCenterX() + dx);
		circle.setCenterY(circle.getCenterY() + dy);
	}

	public Group getHitbox() {
		return hitbox;
	}

	public boolean isTarget() {
		return isTarget;
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

	public void shot() 
	{
		if(isAlive)
			isAlive = !isAlive;
		Level lvl = (Level)getParent();
		if (lvl != null && this != null)
			lvl.removeHittable(this);
	}

	protected void move(double dx, double dy) 
	{
		graphics.setX(graphics.getX() + dx);
		graphics.setY(graphics.getY() + dy);

		Circle circle = Circle.class.cast(hitbox.getChildren().get(0));
		circle.setCenterX(circle.getCenterX() + dx);
		circle.setCenterY(circle.getCenterY() + dy);

	}

	public void setPos(double x, double y)
	{
		double dx = x - graphics.getX();
		double dy = y - graphics.getY();
		this.move(dx, dy);
	}

	protected boolean isWithinBounds()
	{
		if (graphics.getX()+graphics.getImage().getWidth() < 0 || graphics.getX() > SniperGame.LEVEL_WIDTH|| graphics.getY()+graphics.getImage().getHeight() < 0 || graphics.getY()> SniperGame.LEVEL_HEIGHT) 
			return false;
		return true;
	}

	protected void initialStartle() {
		isStartled = true;
	}

	protected void flipHitboxPos() {
		double toCenter = graphics.getX() + graphics.getImage().getWidth()/2 - ((Circle)hitbox.getChildren().get(0)).getCenterX();
		this.moveHitbox(toCenter * 2, 0);
	}

	protected void changeToStartledAnimation() {
		if (isTarget) {
			if (isFacingRight)
				graphics.setImage(rightRunnerTgt);
			else {
				graphics.setImage(leftRunnerTgt);
			}
		} else {
			if (isFacingRight)
				graphics.setImage(rightRunnerCiv);

			else
				graphics.setImage(leftRunnerCiv);
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
					isStartled = true;
					this.stop();
				}
			}

		};
		timer.start();

	}

}
