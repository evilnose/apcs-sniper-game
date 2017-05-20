import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public class Map extends Pane
{
	private ArrayList<Level> levels;
	private ImageView level1, level2;
	private BackgroundImage img = new BackgroundImage(new Image("file:sprites/map.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);
	
	public Map(ArrayList<Level> obj)
	{
		super();
		
		levels = obj;
		
		for(int i = 0;i<levels.size();i++)
		{
			levels.get(i).getLocationImage().setOnMouseClicked(new MissionHandler());
			this.getChildren().add(levels.get(i).getLocationImage());
		}
	}
	
	public void activateDefaultBackground() 
	{
		this.setBackground(new Background(img));
		
		for(int i = 0;i<levels.size();i++)
		{
			levels.get(i).getLocationImage().relocate((int)(Math.random()*346),(int)(Math.random()*600));
		}
	}
	
	private class MissionHandler implements EventHandler<MouseEvent>
	{
		@Override
		public void handle(MouseEvent event) 
		{
			ImageView img = (ImageView) event.getSource();
			int levelNum = 0;
			for(Level l :levels)
			{
				if(l.getLocationImage() == img)
					break;
				levelNum++;
			}
			SniperGame.startLevel(levelNum);
		}
		
	}
}
