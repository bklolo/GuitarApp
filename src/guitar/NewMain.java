package src.guitar;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
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
	
	// Stage vars
	int stageHeight = 400;
	int stageWidth = 1235;
	int imageHeight = 200;
	int imageWidth = stageWidth;
	String jpg = ".\\maple fretboard.jpg";
	String dirPath = "C:\\Users\\Bklolo\\Documents\\Git Repositories\\Music Apps\\GuitarApp";
	String dirName = "data";
	File file = new File(dirPath, dirName);

	public static void main(String[] args) 
	{ 
		launch(args);
	}

	@Override
	public void start(Stage stage) 
	{
		
		if(file.exists())
		{
			return;
		} 
		else
		{
			System.out.println("fafaf");
		}
		// Create Pane
		Pane pane = new Pane();
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		pane.setPrefHeight(stageHeight - 42);

		GridPane interfaceButtons = new GridPane();
		interfaceButtons.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		pane.getChildren().add(interfaceButtons);
		
		Scene scene = new Scene(pane, stageWidth, stageHeight, Color.BLACK);

		Guitar myGuitar = new Guitar(scene, pane, stageHeight, stageWidth, jpg, imageHeight, imageWidth);
		
		
		
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