package src.guitar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MessyMain extends Application {

	// Event Handler vars
	double SceneX;
	double SceneY;
	double TranslateX;
	double TranslateY;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage stage) throws FileNotFoundException {

		// Stage vars
		int stageHeight = 400;
		int stageWidth = 1000;
		int imageHeight = 200;
		int imageWidth = stageWidth + 235;
		String jpg = ".\\maple fretboard.jpg";

		// Guitar vars
		int fretCount = 12;
		float[] fretArray = new float[fretCount];
		int stringCount = 6;

		// Create Pane
		Pane pane = new Pane();
		pane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		pane.setPrefHeight(stageHeight - 42);

		// wrap the scene contents in a pannable scroll pane.
		ScrollPane scrollPane = createScrollPane(pane);
		
		// Create Scene
		Scene scene = new Scene(scrollPane, stageWidth, stageHeight, Color.BLACK);
		
		scene.setFill(Color.BLACK);
		stage.setMinWidth(stageWidth - 300);
		stage.setMinHeight(stageHeight - 100);
		stage.setMaxHeight(stageHeight);
		stage.setMaxWidth(stageWidth + 205);

		// bind the preferred size of the scroll area to the size of the scene.
		scrollPane.prefWidthProperty().bind(scene.widthProperty());
		scrollPane.prefHeightProperty().bind(scene.widthProperty());

		// center the scroll contents.
		scrollPane.setHvalue(scrollPane.getHmin() + (scrollPane.getHmax() - scrollPane.getHmin()) / 2);
		scrollPane.setVvalue(scrollPane.getVmin() + (scrollPane.getVmax() - scrollPane.getVmin()) / 2);

		// create image object
		Image image = new Image(new FileInputStream(jpg));

		// create imageview object
		ImageView imageView = new ImageView(image);
		imageView.setX(0);
		imageView.setY(stageHeight / 2 - imageHeight / 2);
		imageView.setFitHeight(200);
		imageView.setFitWidth(imageWidth);

		// Overall color palette
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(-0.2);
		colorAdjust.setSaturation(0.3);
		colorAdjust.setContrast(-0.1);

		imageView.setEffect(colorAdjust);
		pane.getChildren().add(imageView);

		// Creates Guitar Nut and sets properties
		Line Nut = new Line();
		Nut.setStroke(Color.ANTIQUEWHITE);
		Nut.setStartX(0.0);
		Nut.setEndX(0.0);
		Nut.setStartY(stageHeight / 2 - imageHeight / 2);
		Nut.setEndY(stageHeight / 2 + imageHeight / 2);
		Nut.setScaleX(20);

		// Creates Fret and Fret Inlays
		Fret fret = new Fret(fretCount, stageHeight, imageHeight, pane);
		Inlay inlay3 = new Inlay(fret.getFretArray(), 2, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay5 = new Inlay(fret.getFretArray(), 4, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay7 = new Inlay(fret.getFretArray(), 6, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay9 = new Inlay(fret.getFretArray(), 8, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay12a = new Inlay(fret.getFretArray(), 11, 15, Color.ALICEBLUE, stageHeight - 74);
		Inlay inlay12b = new Inlay(fret.getFretArray(), 11, 15, Color.ALICEBLUE, stageHeight + 74);

		// Add Inlays and Nut to Pane
		pane.getChildren().addAll(inlay3, inlay5, inlay7, inlay9, inlay12a, inlay12b, Nut);

		// create guitar strings and add to Pane
		GuitarString guitarString = new GuitarString(pane, stageHeight, imageHeight, imageWidth, stringCount);
		
		// stores calculated fret positions
		float[] noteLocation = fret.getFretArray();
		
		// create note bubbles
		NoteBubble noteBubble = new NoteBubble(pane, imageHeight, stageHeight, noteLocation, fretCount);

		// Create a combo box (drop down menu)
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Major", "Minor");
		comboBox.autosize();
		comboBox.setPromptText("Scale");
		comboBox.layoutXProperty().bind(scrollPane.hvalueProperty()
				.multiply(pane.widthProperty().subtract(new ScrollPaneViewPortWidthBinding(scrollPane))));
		scrollPane.setHvalue(0);
		pane.getChildren().add(comboBox);
		// comboBox.addEventHandler(eventType, eventHandler);



		stage.setTitle("Guitar Scales");
		stage.setScene(scene);
		stage.show();

	}

	public void NoteBubble(float[] noteLocation, int stageHeight, float stringY, Pane pane) {

		ArrayList<Circle> noteArray = new ArrayList<>();
		// Creates # of NoteBubbles with set properties and adds to Pane
		for (int i = 0; i < noteLocation.length; i++) {
			Circle bubble = new Circle(12, Color.GREEN);
			float bubbleX = noteLocation[i] * 100 - 12;
			double bubbleY = stageHeight / 2 - stringY + bubble.getRadius() / 2;
			bubble.relocate(bubbleX, bubbleY);
			bubble.setStroke(Color.BLACK);
			pane.getChildren().add(bubble);
			noteArray.add(bubble);
		}

	}

	private static class ScrollPaneViewPortWidthBinding extends DoubleBinding {

		private final ScrollPane root;

		public ScrollPaneViewPortWidthBinding(ScrollPane root) {
			this.root = root;
			super.bind(root.viewportBoundsProperty());
		}

		@Override
		protected double computeValue() {
			return root.getViewportBounds().getWidth();
		}
	}

	private ScrollPane createScrollPane(Pane layout) {
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setPannable(true);
		scroll.setPrefSize(800, 600);
		scroll.setContent(layout);
		return scroll;
	}

	EventHandler<MouseEvent> fretBoardOnMousePressed = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			SceneX = t.getSceneX();
			SceneY = t.getSceneY();
			TranslateX = ((Circle) (t.getSource())).getTranslateX();
			TranslateY = ((Circle) (t.getSource())).getTranslateY();
		}
	};

	EventHandler<MouseEvent> fretBoardOnMouseDragged = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double offsetX = t.getSceneX() - SceneX;
			double offsetY = t.getSceneY() - SceneY;
			double newTranslateX = TranslateX + offsetX;
			double newTranslateY = TranslateY + offsetY;

			((Rectangle) t.getSource()).setTranslateX(newTranslateX);
			((Rectangle) (t.getSource())).setTranslateY(newTranslateY);
		}
	};

}