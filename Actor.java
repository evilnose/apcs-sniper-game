package p4_Bali_Aditi_GameEngine;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView {
	
	
	public void move(double dx, double dy) {
    this.setX(this.getX()+dx);
    this.setY(this.getY()+dy);
	}
	
	public World getWorld(){
		
		if(this.getParent()==null){
			return null;
		}
		
		return  (World)(this.getParent());
		
	}
	
	double getWidth(){
		return this.getBoundsInLocal().getWidth(); // local or parents
	}
	
	double getHeight(){
		return this.getBoundsInLocal().getHeight();
	}
	
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
		
		ArrayList<A> list = new ArrayList<A>();
		for (A child : getWorld().getObjects(cls)) {
			if (this != child && this.intersects(child.getBoundsInLocal())) {
				list.add(child);
			}
		}

		return list;
	
	}
	
	
		
		public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls){
		A ret = null;
		for (A child : getWorld().getObjects(cls)) {
			if (this != child && this.intersects(child.getBoundsInLocal())) {
			  ret= child;
				break;
			}
		}
		return ret;
	}
		
		
			public abstract void act(long now);
			
		
	
	
}
