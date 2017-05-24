import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelOne extends Level 
{
	private MediaPlayer alienSound= new MediaPlayer(new Media(new File("sounds/alien_level1.wav").toURI().toString()));
	
	
	public LevelOne(Integer numLevel) 
	{
		super(numLevel);		
		levelMessage = "THIS IS SOME ALIEN JARGON I AM TYPING FOR TRYING OUT THIS FEATURE";
		this.setDefaultBackgroundImage(new Image("file:sprites/backgrounds/level_one.jpg"));
		alienSound.setCycleCount(20);
		alienSound.play();
		
	}

	@Override
	protected void addAllHittables() {
		// TODO Auto-generated method stub
		Runner tester1 = new Runner(true,1);
		addHittable(tester1);
		tester1.setPos(200, 250);
		
//		Tester tester2= new Tester(true);
//		addHittable(tester2);
//		tester2.setPos(450, 350);
//		
//		Tester tester3=new Tester(false);
//		addHittable(tester3);
//		tester3.setPos(550, 350);
	}
	
	@Override
	protected String getDescription() {
		return "Level 1: The Start";
	}

	@Override
	protected String getName() {
		return "Level 1";
	}


}