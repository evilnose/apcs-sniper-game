import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Scope extends ImageView {
	// Use Circle method getCenterX() and getCenterY() to get coordinates of crosshair.
	private Scope thisScope;
	private Color RIM_COLOR = Color.RED;
	private int RIM_WIDTH = 2;
	private static final Image SCOPE = new Image("file:sprites/redscope_framed.png");
	public static final double SCOPE_WIDTH = SCOPE.getWidth();
	public static final double SCOPE_HEIGHT = SCOPE.getHeight();
	private AnimationTimer recoilTimer;
	private AnimationTimer reloadTimer;
	private long startTime;
	private final long RECOIL_MOVE_UP_TIME = 1000;
	private final long RECOIL_MOVE_DOWN_TIME = 1500;
	private final double RECOIL_CONSTANT = 2000;
	private boolean canShoot;
	
	public Scope() {
		super(SCOPE);
		thisScope = this;
		canShoot = true;
		recoilTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// TODO
			}
			
		};
	}
	
	public void shoot() {
		if (canShoot) {
			displayRecoil();
			Hittable victim = getOneShotHittable(this.getX() + thisScope.getImage().getWidth() / 2, this.getY() + thisScope.getImage().getHeight() / 2);
			if (victim != null)
				victim.shot();
			List<Hittable> list = this.getLevel().getObjects(Hittable.class);
			for (Hittable h : list)
				h.startle();
		} else {
			// TODO show that you can't shoot
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
	
	private void move(double dx, double dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
	}
	
	public void moveTo(double x, double y){
		this.setX(x - thisScope.getImage().getWidth() / 2);
		this.setY(y - thisScope.getImage().getHeight() / 2);
	}
	
	private Level getLevel() {
		return (Level)getParent();
	}
	
	public void displayRecoil(){
		startTime = System.currentTimeMillis();
		recoilTimer.start();
	}
	public void displayReload(){
		
	}
	
}