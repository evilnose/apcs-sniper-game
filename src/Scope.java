import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				getOneShotHittable();
			}
			
		});
	}
	
	public Scope(double centerX, double centerY, int radius)
	{
		super(centerX, centerY, radius);
		this.setStroke(RIM_COLOR);
		this.setStrokeWidth(RIM_WIDTH);
		this.setFill(Color.TRANSPARENT);
	}
	
	private ArrayList<Hittable> getAllShotHittables() {
		List<Hittable> list = this.getLevel().getObjects(Hittable.class);
		ArrayList<Hittable> resultingList = new ArrayList<Hittable>();
		for (Hittable h : list) {
			if (h.getHitbox().getBoundsInLocal().contains(getCenterX(), getCenterY()))
				resultingList.add(h);
		}
		return resultingList;
	}
	
	private Hittable getOneShotHittable() {
		List<Hittable> list = this.getLevel().getObjects(Hittable.class);
		for (int i = list.size() - 1; i >= 0; i--) {
//			System.out.println(list.get(i).intersects(getCenterX(), getCenterY(), 1, 1)); // TODO delete this DEBUG
			System.out.println(list.get(i).getHitbox().getBoundsInParent().intersects(getCenterX(), getCenterY(), 1, 1));
			if (list.get(i).getHitbox().getBoundsInLocal().contains(getCenterX(), getCenterY()))
				return list.get(i);
		}
		return null;
	}
	
	private Level getLevel() {
		return (Level)getParent();
	}
	
	public void move(int Dx, int Dy){
		this.setCenterX(this.getCenterX()+Dx);
		this.setCenterY(this.getCenterY()+Dy);
	}
	
	public void displayRecoil(int Dx,int Dy){
		
	}
	public void displayReload(){
		
	}
	
	
}
