import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class World extends Pane {
	private AnimationTimer timer;
	
	public World() {
		timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				act(now);
				for (Actor actor : getObjects(Actor.class)) {
					actor.act(now);
				}
			}
		};
	}
	
	public abstract void act(long now);
	
	public void add(Actor actor) {
		getChildren().add(actor);
	}
	
	public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls) {
		ArrayList<A> verifiedList = new ArrayList<A>();
		for (Node actor : getChildren()) {
			if (cls.isInstance(actor))
				verifiedList.add(cls.cast(actor));
		}
		
		return verifiedList;
	}
	
	public void remove(Actor actor) {
		getChildren().remove(actor);
	}
	
	public void start() {
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}
}