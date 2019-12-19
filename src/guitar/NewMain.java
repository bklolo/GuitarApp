package src.guitar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class NewMain extends Application 
{

	// Event Handler vars
	double SceneX;
	double SceneY;
	double TranslateX;
	double TranslateY;

	public static void main(String[] args) 
	{ 
		launch(args);
	}

	@Override
	public void start(Stage stage) 
	{

		// Stage vars
		int stageHeight = 400;
		int stageWidth = 1235;
		int imageHeight = 200;
		int imageWidth = stageWidth;
		String jpg = ".\\maple fretboard.jpg";
		
		// Create Pane
		Pane pane = new Pane();
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		pane.setPrefHeight(stageHeight - 42);

		Scene scene = new Scene(pane, stageWidth, stageHeight, Color.BLACK);

		Guitar myGuitar = new Guitar(scene, pane, stageHeight, stageWidth, jpg, imageHeight, imageWidth);
		
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Major", "Minor");
		comboBox.autosize();
		comboBox.setPromptText("Scale");

		pane.getChildren().add(comboBox);

		scene.setFill(Color.BLACK);
		stage.setMinWidth(stageWidth - 300);
		stage.setMinHeight(stageHeight - 100);
		stage.setMaxHeight(stageHeight);
		stage.setMaxWidth(stageWidth + 205);

		stage.setTitle("Guitar Scales");
		stage.setScene(scene);
		stage.show();

	}

}