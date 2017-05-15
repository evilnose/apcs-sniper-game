import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView {
	
	public double getHeight() {
		return getImage().getHeight();
	}
	
	public double getWidth() {
		return getImage().getWidth();
	}

	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls) {
		ArrayList<A> list = new ArrayList<A>();
		for (Node actor : getWorld().getObjects(cls)) {
			if (actor != this)
				if (cls.isInstance(actor))
					if (intersects(actor.getBoundsInParent()))
						list.add(cls.cast(actor));
		}
		return list;
	}
	
	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
		List<A> list = getWorld().getObjects(cls);
		for (Node actor : list) {
			if (actor != this) {
				if (cls.isInstance(actor)) {
					if (intersects(actor.getBoundsInParent())) {
						return (cls.cast(actor));
					}
				}
			}
		}
		return null;
	}
	
	public World getWorld() {
		return (World)getParent();
	}
	
	public void move(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
	
	public abstract void act(long now);
}