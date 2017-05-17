import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Scope extends ImageView {
	// Use Circle method getCenterX() and getCenterY() to get coordinates of crosshair.
	private Color RIM_COLOR = Color.RED;
	private int RIM_WIDTH = 2;
	private static final Image SCOPE = new Image("file:sprites/red scope.png");
	public static final double SCOPE_WIDTH = SCOPE.getWidth();
	public static final double SCOPE_HEIGHT = SCOPE.getHeight();
	
//	public Scope(int radius)
//	{
//		super(SniperGame.LEVEL_WIDTH / 2, SniperGame.LEVEL_HEIGHT / 2, radius);
//		this.setStroke(RIM_COLOR);
//		this.setStrokeWidth(RIM_WIDTH);
//		this.setFill(Color.TRANSPARENT);
////		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
////
////			@Override
////			public void handle(MouseEvent event) {
////				getOneShotHitbox();
////			}
////			
////		});
//	}
//	
//	public Scope(double centerX, double centerY, int radius)
//	{
//		super(centerX, centerY, radius);
//		this.setStroke(RIM_COLOR);
//		this.setStrokeWidth(RIM_WIDTH);
//		this.setFill(Color.TRANSPARENT);
//	}
	
	public Scope() {
		super(SCOPE);
	}

	
	public void moveTo(double x, double y){
		this.setX(x - SCOPE.getWidth() / 2);
		this.setY(y - SCOPE.getHeight() / 2);
	}
	
	public void displayRecoil(int Dx,int Dy){
		
	}
	public void displayReload(){
		
	}
	
	
}
