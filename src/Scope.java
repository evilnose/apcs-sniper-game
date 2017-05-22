import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class Scope extends ImageView {
	// Use Circle method getCenterX() and getCenterY() to get coordinates of crosshair.
	private final Scope thisScope;
	private static final Image SCOPE = new Image("file:sprites/scopes/redscope_framed.png");
	private final String RECOIL_URL = "file:sprites/scopes/recoil.gif";
	private Image[] recoilSequence;
	public static final double SCOPE_WIDTH = SCOPE.getWidth();
	public static final double SCOPE_HEIGHT = SCOPE.getHeight();
	private boolean isInCooldown;
	private final long RELOAD_TIME = 2000;
	private final double MAX_SHAKE_DISTANCE = 3;
	private final double MAX_SHAKE_SPEED = 0.1;
	private double shakeSpeed;
	private final double ACCELERATION_FACTOR = 50;
	private boolean movingUp;
	private boolean initialSetup = true;
	private double dy;
	private AnimationTimer timer;
	
	public Scope() {
		super(SCOPE);
		thisScope = this;
		isInCooldown = false;
		shakeSpeed = MAX_SHAKE_SPEED;
		movingUp = true;
		GifDecoder gd = new GifDecoder();
		gd.read(RECOIL_URL);
		recoilSequence = new Image[gd.getFrameCount()];
        for(int i=0; i < gd.getFrameCount(); i++) {

            WritableImage wimg = null;
            BufferedImage bimg = gd.getFrame(i);
            recoilSequence[i] = SwingFXUtils.toFXImage(bimg, wimg);
        }

	}
	
	public void shoot() {
		if (!isInCooldown) {
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
		isInCooldown = true;
		displayImageSequence(recoilSequence, 100);
	
		
	}
	public void displayReload(){
		isInCooldown = true;
		
	}
	
	private void displayImageSequence(Image[] sequence, double delay) {
		final int TOTAL_FRAMES = sequence.length;
		timer = new AnimationTimer() {
			long lastTime = 0;
			int currFrame = 0;
			@Override
			public void handle(long now) {
				if (System.currentTimeMillis() - lastTime >= 100) {
					thisScope.setImage(recoilSequence[currFrame]);
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
	
	
//	public class AnimatedGif extends Animation {
//
//        public AnimatedGif(String filename, double durationMs) {
//
//            GifDecoder d = new GifDecoder();
//            d.read(filename);
//
//            Image[] sequence = new Image[d.getFrameCount()];
//            for( int i=0; i < d.getFrameCount(); i++) {
//
//                WritableImage wimg = null;
//                BufferedImage bimg = d.getFrame(i);
//                sequence[i] = SwingFXUtils.toFXImage(bimg, wimg);
//
//            }
//
//            super.init(sequence, durationMs);
//        }
//
//    }
//
//    public class Animation extends Transition {
//
//        private ImageView imageView;
//        private int count;
//
//        private int lastIndex;
//
//        private Image[] sequence;
//
//        private Animation() {
//        }
//
//        public Animation(Image[] sequence, double durationMs) {
//            init( sequence, durationMs);
//        }
//
//        private void init(Image[] sequence, double durationMs) {
//            this.imageView = new ImageView(sequence[0]);
//            this.sequence = sequence;
//            this.count = sequence.length;
//
//            setCycleCount(1);
//            setCycleDuration(Duration.millis(durationMs));
//            setInterpolator(Interpolator.LINEAR);
//
//        }
//
//        protected void interpolate(double k) {
//
//            final int index = Math.min((int) Math.floor(k * count), count - 1);
//            if (index != lastIndex) {
//                imageView.setImage(sequence[index]);
//                lastIndex = index;
//            }
//
//        }
//
//        public ImageView getView() {
//            return imageView;
//        }
//
//    }
	
}
