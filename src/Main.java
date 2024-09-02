import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//TODO clean up code and merge Tuner and Audio classes to make text based tuner

public class Main extends Application{
	
	int stageHeight = 400;
	int stageWidth = 1235;
	int imageHeight = 200;
	int imageWidth = stageWidth;
	int vBox = 5;
	Scene mainScene, guitarScene, tunerScene;
	Pane mainPane, tunerPane, guitarPane, fretboardPane;
	Button main2Tuner, main2Guitar, mainExit, tuner2Main, tuner2Guitar, tunerExit, guitar2Main, 
			guitar2Tuner, guitarExit;	
//	Tuner tuner;
//	tarsosTuner tTuner;
	
	public static void main(String[] args){ 
		launch(args);
	}

	@Override
	public void start(Stage stage){

		// Panes
		tunerPane = new Pane();
		guitarPane = new Pane();
		guitarPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		guitarPane.setPrefHeight(stageHeight - 42);
		
		fretboardPane = new Pane();
		fretboardPane.autosize();
		fretboardPane.setMouseTransparent(true);	// disables mouse events for entire pane
		
		new Guitar(guitarPane, fretboardPane, stageHeight, stageWidth, imageHeight, imageWidth);
		
		// App Buttons
		main2Tuner = new Button("Tuner");
		main2Tuner.setOnAction(e -> stage.setScene(tunerScene));
		
		main2Guitar = new Button("Guitar");
		main2Guitar.setOnAction(e -> stage.setScene(guitarScene));
		
		mainExit = new Button("Exit");
		mainExit.setOnAction(e -> stage.close());
		
		tuner2Main = new Button("Menu");
		tuner2Main.setOnAction(e -> stage.setScene(mainScene));
		
		tuner2Guitar = new Button("Guitar");
		tuner2Guitar.setOnAction(e -> stage.setScene(guitarScene));
		
		tunerExit = new Button("Exit");
		tunerExit.setOnAction(e -> stage.close());
		
		guitar2Main = new Button("Menu");
		guitar2Main.setOnAction(e -> stage.setScene(mainScene));
		
		guitar2Tuner = new Button("Tuner");
		guitar2Tuner.setOnAction(e -> stage.setScene(tunerScene));
		
		guitarExit = new Button("Exit");
		guitarExit.setOnAction(e -> stage.close());
		
		
		// Main scene properties
		Label mainLabel = new Label("This is the main scene");
		VBox mainLayout = new VBox(vBox);    
		mainLayout.getChildren().addAll(mainLabel, main2Guitar, main2Tuner, mainExit);
		mainScene = new Scene(mainLayout, 300, 250);
		
		// Tuner scene properties
		Label tunerLabel= new Label("This is the tuner scene");
		VBox tunerVBox = new VBox(vBox);     
		//tuner = new Tuner(tunerPane, stageHeight, stageWidth);
//		tTuner = new tarsosTuner(tunerPane, stageWidth, stageHeight);
		tunerVBox.getChildren().addAll(tunerLabel, tuner2Main, tuner2Guitar, tunerExit);
		tunerPane.getChildren().addAll(tunerVBox);
		tunerScene = new Scene(tunerPane, 300, 250);
		
		// Guitar scene properties
		Group guitarGroup = new Group();
		
		VBox guitarLayout = new VBox(vBox);
		guitarLayout.getChildren().addAll(guitar2Main, guitar2Tuner, guitarExit);
		guitarGroup.getChildren().addAll(guitarPane, fretboardPane, guitarLayout);
		guitarScene = new Scene(guitarGroup, stageWidth, stageHeight, Color.BLACK);
		guitarScene.setFill(Color.BLACK);
		
		// Stage properties
		stage.setMinWidth(stageWidth + 8);
		stage.setMinHeight(stageHeight);
		stage.setMaxHeight(stageHeight);
		stage.setMaxWidth(stageWidth);
		stage.setTitle("Guitar Scales");
		stage.setScene(mainScene);
		stage.show();
		
	}
}