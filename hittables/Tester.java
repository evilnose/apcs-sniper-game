import javafx.scene.image.Image;

public class Tester extends Hittable {
	
	private final Image img = new Image("file:sprites/stick man.gif");

	public Tester(boolean isTarget) {
		super(isTarget);
		setGraphics(img);
		setHitboxCircle(47.5, 20, 20);
	}

}
