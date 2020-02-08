package game.frontend;

import game.backend.CandyGame;
import game.backend.level.Level1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		// Creation of game
		CandyGame game = new CandyGame(Level1.class);

		// UI Generation
		CandyFrame frame = new CandyFrame(game);
		Scene scene = new Scene(frame);

		// Put on stage
		primaryStage.setResizable(true);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
