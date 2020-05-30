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
		Pane notesPane = new Pane();
		group.getChildren().addAll(pane, notesPane);		
//		notesPane.setStyle("-fx-border-color: white");	// used to determine pane width/height and location
//		notesPane.setMaxSize(50, 50);
		notesPane.autosize();
		notesPane.setMouseTransparent(true);	// disables mouse events for pane
		
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		pane.setPrefHeight(stageHeight - 42);
		
		new Guitar(pane, notesPane, stageHeight, stageWidth, imageHeight, imageWidth, imageDir);
		
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

//Each string root note chosen generates/points to a chromatic array starting from that note
//
//depending on the scale and key chosen, will omit certain notes
//
//if scale is Major, {1,1,0,1,1,1,0} WWhWWWh used to display or not
// if Minor, use Major scale at index 5: {1,0,1,1,0,1,1} WhWWhWW 
//use hash map to store "note", 0 or 1 to display or no
//
//
//string root: B {B C C# D D# E F F# G G# A A# B}
//scale: Major {1,1,0,1,1,1,0}
//key: C
//
//B C C# D D# E F F# G G# A A# B
//1 1    1    1 1    1    1    1


