package src.guitar;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	
	int stageHeight = 400;
	int stageWidth = 1235;
	int imageHeight = 200;
	int imageWidth = stageWidth;
	String imageDir = ".\\maple fretboard.jpg";
//	String imageDir = "";
	String dirPath = "C:\\Users\\Bklolo\\Documents\\Git Repositories\\Music Apps\\GuitarApp";
	String dirName = "data";
	File file = new File(dirPath, dirName);

	public static void main(String[] args){ 
		launch(args);
	}

	@Override
	public void start(Stage stage){
		
		Group group = new Group();
		
		Pane pane = new Pane();
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		pane.setPrefHeight(stageHeight - 42);
		
		Pane guitarStringsPane = new Pane();
		guitarStringsPane.autosize();
		guitarStringsPane.setMouseTransparent(true);												// disables mouse events for entire pane
		
		group.getChildren().addAll(pane, guitarStringsPane);
		
		new Guitar(pane, guitarStringsPane, stageHeight, stageWidth, imageHeight, imageWidth, imageDir);
		
		Scene scene = new Scene(group, stageWidth, stageHeight, Color.BLACK);
		scene.setFill(Color.BLACK);
		
		stage.setMinWidth(stageWidth + 8);
		stage.setMinHeight(stageHeight);
		stage.setMaxHeight(stageHeight);
		stage.setMaxWidth(stageWidth);
		stage.setTitle("Guitar Scales");
		stage.setScene(scene);
		stage.show();
		
	}
}