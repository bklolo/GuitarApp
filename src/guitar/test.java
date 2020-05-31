package src.guitar;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class test extends Application 
{
	
	//stage vars
	int stageHeight = 400;
	int stageWidth = 1235;
	int imageHeight = 200;
	int imageWidth = stageWidth;
	Group group = new Group();
	Pane pane = new Pane();
	Pane notesPane = new Pane();

	public static void main(String[] args) 
	{ 
		launch(args);
	}

	@Override
	public void start(Stage stage) 
	{

		
		Scene scene = new Scene(pane);
		Circle circle = new Circle();
		circle.setRadius(200);
		circle.setFill(Color.GREEN);
		
		Rectangle rect = new Rectangle();
		rect.setHeight(200);
		rect.setWidth(200);
		rect.setFill(Color.RED);
		rect.setLayoutX(100);
		
		
		notesPane.getChildren().add(rect);
		pane.getChildren().addAll(notesPane, circle);
		
		
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