import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

public class Tester extends Hittable {
	
	private final Image img = new Image("file:sprites/stick man.gif");

	public Tester(boolean isTarget) {
		super(isTarget);
		dy = 0;
		Tester obj = this;
		setGraphics(img);
		setHitboxCircle(47.5, 20, 20);
		AnimationTimer timer = new AnimationTimer() 
		{
			@Override
			public void handle(long now) 
			{
				dx = 3;
				int timeInSeconds = (int)(now*Math.pow(10,-9));
				if(timeInSeconds%5==0)
					dx = -dx;
				obj.move(dx,dy);
			}
		};
		timer.start();
	}
	

}
