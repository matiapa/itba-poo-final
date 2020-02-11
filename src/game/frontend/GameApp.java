package game.frontend;

import game.backend.CandyGame;
import game.backend.level.Level2;
import game.backend.level.Level3;
import game.backend.level.Level4;
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

import java.util.HashMap;
import java.util.Map;

public class GameApp extends Application {

	private Stage stage;

	enum AppScene{ MENU, GAME_FRAME }
	Map<AppScene, Scene> scenes = new HashMap();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {

		this.stage = stage;

		// Main menu generation
		scenes.put(AppScene.MENU, createMainMenu());
		setScene(AppScene.MENU);

		stage.setResizable(true);
		stage.show();

	}


	private Scene createMainMenu(){
		Label title = new Label("Candy Crush");
		title.setFont(new Font("Arial", 24));

		ChoiceBox<String> levelChoice = new ChoiceBox<String>(FXCollections.observableArrayList("Level 1","Level 2", "Level 3", "Level 4", "Level 5"));

		Button playButton = new Button("Play");
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				playLevel(levelChoice.getValue());
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

		return new Scene(stackScreen);
	}


	private void playLevel(String levelName){

		Class levelClass;
		switch(levelName){
			case "Level 1":
				levelClass = Level2.class;
				break;
			case "Level 2":
				levelClass = Level2.class;
				break;
			case "Level 3":
				levelClass = Level3.class;
				break;
			case "Level 4":
				levelClass = Level4.class;
				break;
			case "Level 5":
				levelClass = Level3.class; //TODO: Update with new level
				break;
			default:
				levelClass = null;
				break;
		}

		// Creation of game backend.
		CandyGame game = new CandyGame(levelClass);

		// Game frame generation
		scenes.put(AppScene.GAME_FRAME, new Scene(new CandyFrame(this, game)));
		setScene(AppScene.GAME_FRAME);

	}

	public void setScene(AppScene sceneName){ stage.setScene(scenes.get(sceneName)); }



}