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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

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

		// Guitar vars
		int frets = 12;
		float stringY = stageHeight / 2 - imageHeight / 2;

		// Create Pane
		Pane pane1 = new Pane();
		pane1.setBackground(null);

		// wrap the scene contents in a pannable scroll pane.
		ScrollPane scrollPane = createScrollPane(pane1);
		scrollPane.setBackground(null);

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

		// Create a tile pane
		Image image = new Image(new FileInputStream(".\\maple fretboard.jpg"));

		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setX(0);
		imageView.setY(stageHeight / 2 - imageHeight / 2);

		// setting the fit height and width of the image view
		imageView.setFitHeight(200);
		imageView.setFitWidth(stageWidth + 270);

		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(-.2);
		colorAdjust.setSaturation(0.3);
		colorAdjust.setContrast(-0.1);

		imageView.setEffect(colorAdjust);
		pane1.getChildren().add(imageView);

		// Creates guitar Nut and sets properties
		Line Nut = new Line();
		Nut.setStroke(Color.ANTIQUEWHITE);
		Nut.setStartX(0.0);
		Nut.setEndX(0.0);
		Nut.setStartY(stageHeight / 2 - imageHeight / 2);
		Nut.setEndY(stageHeight / 2 + imageHeight / 2);
		Nut.setScaleX(20);

		// Calculates and stores fret locations up to 12th fret
		// for (int i = 0; i < fretArray.length; i++) {
		// fretPosition += length / constant;
		// length -= length / constant;
		// int num = i;
		// System.out.print("fretPosition: " + num);
		// System.out.printf(" = [ %.4f - %.4f ] = %.4f\n", stdFretboardLength,
		// length, fretPosition);
		// fretArray[i] = fretPosition;
		// }
		
		float[] fretArray = calcFrets(frets);

		float inlay3Pos = (fretArray[2] - ((fretArray[2] - fretArray[1]) / 2)) * 100;
		float inlay5Pos = (fretArray[4] - ((fretArray[4] - fretArray[3]) / 2)) * 100;
		float inlay7Pos = (fretArray[6] - ((fretArray[6] - fretArray[5]) / 2)) * 100;
		float inlay9Pos = (fretArray[8] - ((fretArray[8] - fretArray[7]) / 2)) * 100;
		float inlay12Pos = (fretArray[11] - ((fretArray[11] - fretArray[10]) / 2)) * 100;

		// Creates INLAYS for fretboard
		Circle circle3 = new Circle(15, Color.ALICEBLUE);
		circle3.relocate(inlay3Pos - circle3.getRadius(), (stageHeight / 2 - circle3.getRadius()));
		Circle circle5 = new Circle(15, Color.ALICEBLUE);
		circle5.relocate(inlay5Pos - circle5.getRadius(), (stageHeight / 2 - circle5.getRadius()));
		Circle circle7 = new Circle(15, Color.ALICEBLUE);
		circle7.relocate(inlay7Pos - circle7.getRadius(), (stageHeight / 2 - circle7.getRadius()));
		Circle circle9 = new Circle(15, Color.ALICEBLUE);
		circle9.relocate(inlay9Pos - circle9.getRadius(), (stageHeight / 2 - circle9.getRadius()));
		Circle circle12a = new Circle(15, Color.ALICEBLUE);
		circle12a.relocate(inlay12Pos - circle12a.getRadius(), (stageHeight / 2 - circle12a.getRadius() - 44));
		Circle circle12b = new Circle(15, Color.ALICEBLUE);
		circle12b.relocate(inlay12Pos - circle12b.getRadius(), (stageHeight / 2 - circle12b.getRadius() + 44));

		//// FRETBOARD DECOR
		// Polygon polygon = new Polygon();
		// polygon.getPoints().addAll(new double[]{
		// 305.0, 160.0,
		// 305.0, 240.0,
		// 375.0, 270.0,
		// 375.0, 130.0
		// });
		// polygon.setFill(Color.CORNSILK);
		// polygon.setStroke(Color.BLACK);
		// polygon.setStrokeWidth(.5);

		pane1.getChildren().addAll(circle3, circle5, circle7, circle9, circle12a, circle12b, Nut);
		
		FretMethod(frets, stageHeight, imageHeight, pane1);
		StringMethod(fretArray, stringY, imageHeight, pane1);

		float[] noteLocation = new float[frets];
		noteLocation[0] = fretArray[0] / 2;
		noteLocation[1] = fretArray[1] - (fretArray[1] - fretArray[0]) / 2;
		noteLocation[2] = fretArray[2] - (fretArray[2] - fretArray[1]) / 2;
		noteLocation[3] = fretArray[3] - (fretArray[3] - fretArray[2]) / 2;
		noteLocation[4] = fretArray[4] - (fretArray[4] - fretArray[3]) / 2;
		noteLocation[5] = fretArray[5] - (fretArray[5] - fretArray[4]) / 2;
		noteLocation[6] = fretArray[6] - (fretArray[6] - fretArray[5]) / 2;
		noteLocation[7] = fretArray[7] - (fretArray[7] - fretArray[6]) / 2;
		noteLocation[8] = fretArray[8] - (fretArray[8] - fretArray[7]) / 2;
		noteLocation[9] = fretArray[9] - (fretArray[9] - fretArray[8]) / 2;
		noteLocation[10] = fretArray[10] - (fretArray[10] - fretArray[9]) / 2;
		noteLocation[11] = fretArray[11] - (fretArray[11] - fretArray[10]) / 2;

		ArrayList<Circle> noteArray = new ArrayList<>();
		// Creates # of NoteBubbles with set properties and adds to Pane
		for (int i = 0; i < noteLocation.length; i++) {
			Circle bubble = new Circle(12, Color.GREEN);
			float bubbleX = noteLocation[i] * 100 - 12;
			double bubbleY = stageHeight / 2 - stringY + bubble.getRadius() / 2;
			bubble.relocate(bubbleX, bubbleY);
			bubble.setStroke(Color.BLACK);
			pane1.getChildren().add(bubble);
			noteArray.add(bubble);
		}

		// Create a combo box (drop down menu)
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Major", "Minor");
		comboBox.autosize();
		comboBox.setPromptText("Scale");
		comboBox.layoutXProperty().bind(scrollPane.hvalueProperty()
				.multiply(pane1.widthProperty().subtract(new ScrollPaneViewPortWidthBinding(scrollPane))));
		scrollPane.setHvalue(0);
		pane1.getChildren().add(comboBox);
		// comboBox.addEventHandler(eventType, eventHandler);

		stage.setTitle("Guitar Scales");
		stage.setScene(scene);
		stage.show();

	}

	public void FretMethod(int numberOfFrets, int stageHeight, int imageHeight, Pane pane1) {
		
		float[] array = calcFrets(numberOfFrets);
		
		for (int i = 0; i < array.length; i++) {
			Line fret = new Line();
			fret.setStroke(Color.SILVER);
			fret.setStartX((array[i]) * 100);
			fret.setEndX((array[i]) * 100);
			fret.setStartY(stageHeight / 2 - imageHeight / 2);
			fret.setEndY(stageHeight / 2 + imageHeight / 2);
			fret.setScaleX(5);
			pane1.getChildren().add(fret);
		}
	}

	public float[] calcFrets(int numberOfFrets) {
		
		float[] fretArray = new float[numberOfFrets];
		float fretPosition = 0;
		float fretBoardLength = 25.40f; // Total length of guitar neck
		float constant = 17.817f; // Constant used for calculating frets
		float length = fretBoardLength;
		
		for (int i = 0; i < fretArray.length; i++) {
			fretPosition += length / constant;
			length -= length / constant;
//			int num = i;
//			System.out.print("fretPosition: " + num);
//			System.out.printf(" = [ %.4f - %.4f ] = %.4d\n", fretBoardLength, length, fretPosition);
			fretArray[i] = fretPosition;
		}

		return fretArray;
	}

	public void StringMethod(float[] fretArray, float stringY, int imageHeight, Pane pane1) {
		
		for (int i = 0; i < 6; i++) {
			Line string = new Line();
			string.setStroke(Color.GOLD);
			string.setStartX(0);
			string.setEndX(fretArray[11] * 100);
			string.setStartY(stringY);
			string.setEndY(stringY);
			string.setLayoutY(20 + i * ((imageHeight - 5) / 6));
			string.setScaleY(5 + .5 * i);

			DropShadow shadow = new DropShadow();
			shadow.setOffsetX(11.0);
			shadow.setOffsetY(1.0);
			shadow.setHeight(1);
			shadow.setColor(Color.BLACK);
			shadow.setRadius(.5);

			string.setEffect(shadow);

			pane1.getChildren().add(string);

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