import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;

public abstract class World extends javafx.scene.layout.Pane {
	
	private AnimationTimer timer;

	public World() {
		timer = new AnimationTimer() {

			@Override
			public void handle(long now) {

				act(now);
				for(int i= 0; i < getChildren().size(); i++ ){
					((Actor)(getChildren().get(i))).act(now);
				}
				
				

			}
		};

	}

	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	public void add(Actor actor) {
		this.getChildren().add(actor);
	}

	public void remove(Actor actor) {
		this.getChildren().remove(actor);
	}

	public <A extends Actor> List<A> getObjects(Class<A> cls) {

		ArrayList<A> list = new ArrayList<A>();
		for (Node n : getChildren()) {
			if (cls.isInstance(n)) {
				list.add(cls.cast(n));
			}
		}
		return list;
	}

	public abstract void act(long now);

}
