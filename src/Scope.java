import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Scope extends Circle {
	// Use Circle method getCenterX() and getCenterY() to get coordinates of crosshair.
	private Color RIM_COLOR = Color.RED;
	private int RIM_WIDTH = 2;
	
	public Scope(int radius)
	{
		super(SniperGame.LEVEL_WIDTH / 2, SniperGame.LEVEL_HEIGHT / 2, radius);
		this.setStroke(RIM_COLOR);
		this.setStrokeWidth(RIM_WIDTH);
		this.setFill(Color.TRANSPARENT);
	}
	
	public Scope(double centerX, double centerY, int radius)
	{
		super(centerX, centerY, radius);
		this.setStroke(RIM_COLOR);
		this.setStrokeWidth(RIM_WIDTH);
		this.setFill(Color.TRANSPARENT);
	}
	
/*	public double getCenterX(){
		
	}
	
	public double getCenterY(){
		
	}*/
	
	public void move(int Dx, int Dy){
		this.setCenterX(this.getCenterX()+Dx);
		this.setCenterY(this.getCenterY()+Dy);
	}
	
	public void displayRecoil(int Dx,int Dy){
		
	}
	public void displayReload(){
		
	}
	
	
}
