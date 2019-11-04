package src.guitar;

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class NewMain extends Application {

	// Event Handler vars
	double SceneX;
	double SceneY;
	double TranslateX;
	double TranslateY;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage stage) {

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
		
//		ScrollPane scrollPane = new ScrollPane();
//
//		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//		scrollPane.setPannable(true);
//		scrollPane.setPrefSize(800, 600);
//		scrollPane.setContent(pane);
//
//		scrollPane.prefWidthProperty().bind(scene.widthProperty());
//		scrollPane.prefHeightProperty().bind(scene.widthProperty());
//
//		// center the scroll contents.
//		scrollPane.setHvalue(scrollPane.getHmin() + (scrollPane.getHmax() - scrollPane.getHmin()) / 2);
//		scrollPane.setVvalue(scrollPane.getVmin() + (scrollPane.getVmax() - scrollPane.getVmin()) / 2);
		
		// Menu item to select overall scale
		// switch to Toggle BUtton? Tool Bar? 
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Major", "Minor");
		comboBox.autosize();
		comboBox.setPromptText("Scale");
//		comboBox.layoutXProperty().bind(scrollPane.hvalueProperty()
//				.multiply(pane.widthProperty().subtract(new ScrollPaneViewPortWidthBinding(scrollPane))));
//		scrollPane.setHvalue(0);
		pane.getChildren().add(comboBox);
		// comboBox.addEventHandler(eventType, eventHandler);

		scene.setFill(Color.BLACK);
		stage.setMinWidth(stageWidth - 300);
		stage.setMinHeight(stageHeight - 100);
		stage.setMaxHeight(stageHeight);
		stage.setMaxWidth(stageWidth + 205);

		stage.setTitle("Guitar Scales");
		stage.setScene(scene);
		stage.show();

	}
	
//	private static class ScrollPaneViewPortWidthBinding extends DoubleBinding {
//
//		private final ScrollPane root;
//
//		public ScrollPaneViewPortWidthBinding(ScrollPane root) {
//			this.root = root;
//			super.bind(root.viewportBoundsProperty());
//		}
//
//		@Override
//		protected double computeValue() {
//			return root.getViewportBounds().getWidth();
//		}
//		
//	}
	
//	EventHandler<MouseEvent> fretBoardOnMousePressed = new EventHandler<MouseEvent>() {
//
//		@Override
//		public void handle(MouseEvent t) {
//			SceneX = t.getSceneX();
//			SceneY = t.getSceneY();
//			TranslateX = ((Circle) (t.getSource())).getTranslateX();
//			TranslateY = ((Circle) (t.getSource())).getTranslateY();
//		}
//	};
//
//	EventHandler<MouseEvent> fretBoardOnMouseDragged = new EventHandler<MouseEvent>() {
//
//		@Override
//		public void handle(MouseEvent t) {
//			double offsetX = t.getSceneX() - SceneX;
//			double offsetY = t.getSceneY() - SceneY;
//			double newTranslateX = TranslateX + offsetX;
//			double newTranslateY = TranslateY + offsetY;
//
//			((Rectangle) t.getSource()).setTranslateX(newTranslateX);
//			((Rectangle) (t.getSource())).setTranslateY(newTranslateY);
//		}
//	};

}