import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
 *	This class handles the UI, namely the main game interface.
 */

public class SniperGame extends Application {
	
	public static void main(String args[]) {
		launch(); // I have failed to move this into its own driver classes. You guys are welcome to try.
	}

	@Override
	public void start(Stage homeScreen) {
		
		homeScreen.setTitle("Sniper Game Alpha"); // TODO This Name is tentative. Need a cooler one.
		homeScreen.setResizable(false);
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 500); // The Golden Ratio
		
		Button startGameButton = new Button();
		startGameButton.setPrefSize(50, 50);
		startGameButton.setText("Start");
		root.setCenter(startGameButton);
		
		homeScreen.setScene(scene);
		homeScreen.show();
	}

}
