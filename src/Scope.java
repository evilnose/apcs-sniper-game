import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Scope extends Circle {
	// Use Circle method getCenterX() and getCenterY() to get coordinates of crosshair.
	private Color RIM_COLOR = Color.RED;
	
	public Scope(int radius)
	{
		super(SniperGame.LEVEL_WIDTH / 2, SniperGame.LEVEL_HEIGHT / 2, radius);
		this.setOnMouseMoved(new ScopeMouseHandler());
		this.setStroke(RIM_COLOR);
		this.setFill(Color.TRANSPARENT);
	}
	
	public Scope(double centerX, double centerY, int radius)
	{
		super(centerX, centerY, radius);
		this.setOnMouseMoved(new ScopeMouseHandler());
		this.setStroke(RIM_COLOR);
		this.setFill(Color.TRANSPARENT);
	}
	
	private class ScopeMouseHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			setCenterX(event.getSceneX());
			setCenterY(event.getSceneY());
		}
		
	}
	
	
}
