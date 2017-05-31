import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Scope extends ImageView {
	private final Scope thisScope;
	private static final Image SCOPE = new Image("file:sprites/scopes/redscope_framed.png");
	public static final double SCOPE_WIDTH = SCOPE.getWidth();
	public static final double SCOPE_HEIGHT = SCOPE.getHeight();
	private boolean isInCooldown;
	private final double MAX_SHAKE_DISTANCE = 3;
	private final double MAX_SHAKE_SPEED = 0.1;
	private double shakeSpeed;
	private final double ACCELERATION_FACTOR = 50;
	private boolean movingUp;
	private boolean initialSetup = true;
	private double dy;
	private AnimationTimer timer;

	private Media gunShot = new Media(new File("sounds/gunshot_sound.wav").toURI().toString());
    private MediaPlayer gunShotPlayer= new MediaPlayer(gunShot);
	
	
	public Scope() {
		super(SCOPE);
		thisScope = this;
		isInCooldown = false;
		shakeSpeed = MAX_SHAKE_SPEED;
		movingUp = true;

	}
	
	public void shoot() {
		if (!isInCooldown) {
			isInCooldown = true;
			this.getLevel().reduceNumBullets();
			displayRecoil();
			gunShotPlayer.stop();
			gunShotPlayer.play();
			
			Hittable victim = getOneShotHittable(this.getX() + thisScope.getImage().getWidth() / 2, this.getY() + thisScope.getImage().getHeight() / 2);
			if (victim != null)
				victim.shot();
			
			List<Hittable> list = this.getLevel().getObjects(Hittable.class);
			for (Hittable h : list)
				h.startle();
		}
	}
	
	private Hittable getOneShotHittable(double x, double y) {
		List<Hittable> list = this.getLevel().getObjects(Hittable.class);
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i).getHitbox().contains(x, y))
				return list.get(i);
		}
		return null;
	}
	
	private ArrayList<Hittable> getAllShotHittables(double x, double y) {
		List<Hittable> list = this.getLevel().getObjects(Hittable.class);
		ArrayList<Hittable> resultingList = new ArrayList<Hittable>();
		for (Hittable h : list) {
			if (h.getHitbox().contains(x, y));
				resultingList.add(h);
		}
		return resultingList;
	}
	
	public void move(double dx, double dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
	}
	
	public void moveTo(double x, double y) {

		if (initialSetup) {
			this.setX(x - thisScope.getImage().getWidth() / 2);
			this.setY(y - thisScope.getImage().getHeight() / 2);
			initialSetup = false;
		}
		else {
			this.setX(x - thisScope.getImage().getWidth() / 2);
			this.setY(y - thisScope.getImage().getHeight() / 2 + getDy());
		}
	}
	
	public void act(long now) {
		breathe();
	}
	
	public void breathe() {
		if (movingUp) {
			if (getDy() >= MAX_SHAKE_DISTANCE) {
				shakeSpeed = -shakeSpeed;
				movingUp = false;
			}
			else if (getDy() > 0)
				shakeSpeed -= MAX_SHAKE_SPEED / ACCELERATION_FACTOR;
			else
				shakeSpeed += MAX_SHAKE_SPEED / ACCELERATION_FACTOR;
		} else {
			if (getDy() <= - MAX_SHAKE_DISTANCE) {
				shakeSpeed = -shakeSpeed;
				movingUp = true;
			}
			else if (getDy() < 0)
				shakeSpeed += MAX_SHAKE_SPEED / ACCELERATION_FACTOR;
			else 
				shakeSpeed -= MAX_SHAKE_SPEED / ACCELERATION_FACTOR;
		}
		
		dy += shakeSpeed;
		move(0, shakeSpeed);
	}
	
	private double getDy() {
		return dy;
	}
	
	private Level getLevel() {
		return (Level)getParent();
	}
	
	public void displayRecoil(){
		displayScopeAnimation(SniperGame.recoilSequence, 100);		
	}
	public void displayReload(){
		isInCooldown = true;
		
	}
	
	private void displayScopeAnimation(Image[] sequence, double delay) {
		final int TOTAL_FRAMES = sequence.length;
		timer = new AnimationTimer() {
			long lastTime = 0;
			int currFrame = 0;
			@Override
			public void handle(long now) {
				if (System.currentTimeMillis() - lastTime >= 100) {
					thisScope.setImage(sequence[currFrame]);
					currFrame++;
					lastTime = System.currentTimeMillis();
				}
				if (currFrame >= TOTAL_FRAMES) {
					isInCooldown = false;
					this.stop();
				}
			}
			
		};
		timer.start();
		
	}
	
}
