package src.guitar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Scene;
//import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Guitar {

	// // Event handler vars
	// private static double SceneX;
	// private static double SceneY;
	// private static double TranslateX;
	// private static double TranslateY;
	// Guitar vars
	private static int fretCount = 12;
	private float[] fretArray = new float[fretCount];
	private static int stringCount = 6;
	private float[] noteLocation;

	Guitar(Scene scene, Pane pane, int stageHeight, int stageWidth, String image, int imageHeight, int imageWidth) {

		Fretboard(pane, image, stageHeight, imageHeight, imageWidth);

		Fret fret = new Fret(fretCount, stageHeight, imageHeight, pane);
		noteLocation = fret.getFretArray();
		NoteBubble noteBubble = new NoteBubble(pane, imageHeight, stageHeight, noteLocation, fretCount);

		Nut(pane, stageHeight, imageHeight);

		Inlays(pane, stageHeight, imageHeight, fretCount);

		GuitarString guitarString = new GuitarString(pane, stageHeight, imageHeight, imageWidth, stringCount);
		// ScrollPane scrollPane = UseScrollPane(scene, pane);
		// ScaleSelect(pane, scrollPane);
		// pane.getChildren().addAll(scrollPane, fret, guitarString,
		// noteBubble);
		pane.getChildren().addAll(fret, guitarString, noteBubble);

	}

	// Generates the background for the guitar fretboard
	private Image Fretboard(Pane pane, String jpg, int stageHeight, int imageHeight, int imageWidth) {

		Image test = null;
		try {
			test = new Image(new FileInputStream(jpg));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		Image image = test;

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

		return image;

	}

	private void Nut(Pane pane, int stageHeight, int imageHeight) {

		Line Nut = new Line();
		Nut.setStroke(Color.ANTIQUEWHITE);
		Nut.setStartX(0.0);
		Nut.setEndX(0.0);
		Nut.setStartY(stageHeight / 2 - imageHeight / 2);
		Nut.setEndY(stageHeight / 2 + imageHeight / 2);
		Nut.setScaleX(20);

		pane.getChildren().add(Nut);

	}

	private void Inlays(Pane pane, int stageHeight, int imageHeight, int fretCount) {

		Inlay inlay3 = new Inlay(noteLocation, 2, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay5 = new Inlay(noteLocation, 4, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay7 = new Inlay(noteLocation, 6, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay9 = new Inlay(noteLocation, 8, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay12a = new Inlay(noteLocation, 11, 15, Color.ALICEBLUE, stageHeight - 74);
		Inlay inlay12b = new Inlay(noteLocation, 11, 15, Color.ALICEBLUE, stageHeight + 74);

		pane.getChildren().addAll(inlay3, inlay5, inlay7, inlay9, inlay12a, inlay12b);
	}

	// private ScrollPane UseScrollPane(Scene scene, Pane pane) {
	//
	// ScrollPane scrollPane = new ScrollPane();
	//
	// scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	// scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	// scrollPane.setPannable(true);
	// scrollPane.setPrefSize(800, 600);
	// scrollPane.setContent(pane);
	//
	// scrollPane.prefWidthProperty().bind(scene.widthProperty());
	// scrollPane.prefHeightProperty().bind(scene.widthProperty());
	//
	// // center the scroll contents.
	// scrollPane.setHvalue(scrollPane.getHmin() + (scrollPane.getHmax() -
	// scrollPane.getHmin()) / 2);
	// scrollPane.setVvalue(scrollPane.getVmin() + (scrollPane.getVmax() -
	// scrollPane.getVmin()) / 2);
	//
	// return scrollPane;
	// }

	// private void ScaleSelect(Pane pane, ScrollPane scrollPane) {
	//
	// ComboBox<String> comboBox = new ComboBox<>();
	// comboBox.getItems().addAll("Major", "Minor");
	// comboBox.autosize();
	// comboBox.setPromptText("Scale");
	// comboBox.layoutXProperty().bind(scrollPane.hvalueProperty()
	// .multiply(pane.widthProperty().subtract(new
	// ScrollPaneViewPortWidthBinding(scrollPane))));
	// scrollPane.setHvalue(0);
	// pane.getChildren().add(comboBox);
	// // comboBox.addEventHandler(eventType, eventHandler);
	// }

	// static class ScrollPaneViewPortWidthBinding extends DoubleBinding {
	//
	// private final ScrollPane root;
	//
	// public ScrollPaneViewPortWidthBinding(ScrollPane root) {
	// this.root = root;
	// super.bind(root.viewportBoundsProperty());
	// }
	//
	// @Override
	// protected double computeValue() {
	// return root.getViewportBounds().getWidth();
	// }
	//
	// }
	//
	// EventHandler<MouseEvent> fretBoardOnMousePressed = new
	// EventHandler<MouseEvent>() {
	//
	// @Override
	// public void handle(MouseEvent t) {
	// SceneX = t.getSceneX();
	// SceneY = t.getSceneY();
	// TranslateX = ((Circle) (t.getSource())).getTranslateX();
	// TranslateY = ((Circle) (t.getSource())).getTranslateY();
	// }
	// };
	//
	// EventHandler<MouseEvent> fretBoardOnMouseDragged = new
	// EventHandler<MouseEvent>() {
	//
	// @Override
	// public void handle(MouseEvent t) {
	// double offsetX = t.getSceneX() - SceneX;
	// double offsetY = t.getSceneY() - SceneY;
	// double newTranslateX = TranslateX + offsetX;
	// double newTranslateY = TranslateY + offsetY;
	//
	// ((Rectangle) t.getSource()).setTranslateX(newTranslateX);
	// ((Rectangle) (t.getSource())).setTranslateY(newTranslateY);
	// }
	// };

}
