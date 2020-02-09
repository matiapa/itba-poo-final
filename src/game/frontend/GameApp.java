package game.frontend;

import game.backend.CandyGame;
import game.backend.level.Level1;
import game.backend.level.Level3;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.logging.Level;

public class GameApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		Label title = new Label("Candy Crush");
		title.setFont(new Font("Arial", 24));

		ChoiceBox<String> levelChoice = new ChoiceBox<String>(FXCollections.observableArrayList("Level 1", "Level 3"));

		Button playButton = new Button("Play");
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				playLevel(stage, levelChoice.getValue());
			}
		});

		VBox levelChooser = new VBox(
				title,
				new Label("Choose a level"),
				levelChoice,
				playButton
		);
		levelChooser.setSpacing(20);
		levelChooser.setAlignment(Pos.CENTER);

		StackPane stackScreen = new StackPane(
				new ImageView(new Image("images/background.jpg")),
				levelChooser
		);

		stage.setScene(new Scene(stackScreen));
		stage.show();

	}

	private void playLevel(Stage stage, String levelName){

		Class levelClass;
		switch(levelName){
			case "Level 1":
				levelClass = Level1.class;
				break;
			case "Level 3":
				levelClass = Level3.class;
				break;
			default:
				levelClass = null;
				break;
		}

		// Creation of game. Backend.
		CandyGame game = new CandyGame(levelClass);

		// UI Generation
		CandyFrame frame = new CandyFrame(game);
		Scene scene = new Scene(frame);

		// Put on stage
		stage.setResizable(true);
		stage.setScene(scene);

	}

}
