import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Scope extends ImageView {
	// Use Circle method getCenterX() and getCenterY() to get coordinates of crosshair.
	private Color RIM_COLOR = Color.RED;
	private int RIM_WIDTH = 2;
	private static final Image SCOPE = new Image("file:sprites/redscope_framed.png");
	public static final double SCOPE_WIDTH = SCOPE.getWidth();
	public static final double SCOPE_HEIGHT = SCOPE.getHeight();
	
	public Scope() 
	{
		super(SCOPE);
	}
	
	public double x()
	{
		return this.getX();
	}
	
	public double y()
	{
		return this.getY();
	}
	
	public void shoot() {
		Hittable victim = getOneShotHittable(this.getX() + SCOPE.getWidth()/2, this.getY() + SCOPE.getHeight()/2);
		if (victim != null)
			victim.shot();
		List<Hittable> list = this.getLevel().getObjects(Hittable.class);
		for (Hittable h : list)
			h.startle();
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
	
	public void moveTo(double x, double y){
		this.setX(x - SCOPE.getWidth() / 2);
		this.setY(y - SCOPE.getHeight() / 2);
	}
	
	private Level getLevel() {
		return (Level)getParent();
	}
	
	public void displayRecoil(int Dx,int Dy){
		
	}
	public void displayReload(){
		
	}
	
	
}
