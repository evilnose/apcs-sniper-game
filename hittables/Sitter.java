import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sitter extends Hittable {
	
	private double dx;
	private double dy;
	
	public Sitter(boolean isTgt) 
	{
		super(isTgt, new Image("file:sprites/sitter")); // TODO: Sitter not included yet
//		this.setGraphics();
//		this.setHitbox();
	}

	@Override
	public void act(long now) 
	{
		// TODO Auto-generated method stub
		
	}
}
