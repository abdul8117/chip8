package chip8;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Chip8 extends Application {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Heyo");
		System.out.println(":3");

		launch();


	}

	@Override
	public void start(Stage stage) throws Exception {
		// create display, initialise keyboard and memory, load ROM (?)

		VBox root = new VBox();
		Scene sc = new Scene(root);

	}
}
