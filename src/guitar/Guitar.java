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

public class Guitar 
{

	// // Event handler vars
	// private static double SceneX;
	// private static double SceneY;
	// private static double TranslateX;
	// private static double TranslateY;
	// Guitar vars
	private static int fretCount = 12;
	private static int stringCount = 6;
	private float[] fretLocation;
	private float[] noteLocation;

	Guitar(Scene scene, Pane pane, int stageHeight, int stageWidth, String image, int imageHeight, int imageWidth) 
	{
		// Method that applies an image to act as the fretboard
		FretboardImage(pane, image, stageHeight, imageHeight, imageWidth);
		// creates frets on top of fretboard image
		Fret fret = new Fret(fretCount, stageHeight, imageHeight, pane);
		pane.getChildren().add(fret);
		fretLocation = fret.getFretArray();
		
		noteLocation = notePosition(fretLocation, fretCount);
		// creates guitar Nut
		Nut(pane, stageHeight, imageHeight);
		// creates 3,5,7,9,12 inlays on fretboard
		Inlays(pane, stageHeight, imageHeight, fretCount);
		// for loop to create each guitar string
		for(int i = 0; i < stringCount; i++)
		{
			GuitarString guitarString = new GuitarString(pane, stageHeight, imageHeight, imageWidth, stringCount, noteLocation, i);
			pane.getChildren().add(guitarString);
		}
		
		InterfaceDisplay(pane);

	}
	// ComboBoxes, etc should be grouped here, in their own type of pane
	private void InterfaceDisplay(Pane pane)
	{
		UI userInterface = new UI(pane);
	}

	// Generates the background for the guitar fretboard
	private Image FretboardImage(Pane pane, String jpg, int stageHeight, int imageHeight, int imageWidth) 
	{

		Image test = null;
		try 
		{
			test = new Image(new FileInputStream(jpg));
		} catch (FileNotFoundException e) 
		{

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

	private void Nut(Pane pane, int stageHeight, int imageHeight) 
	{

		Line Nut = new Line();
		Nut.setStroke(Color.ANTIQUEWHITE);
		Nut.setStartX(0.0);
		Nut.setEndX(0.0);
		Nut.setStartY(stageHeight / 2 - imageHeight / 2);
		Nut.setEndY(stageHeight / 2 + imageHeight / 2);
		Nut.setScaleX(20);

		pane.getChildren().add(Nut);

	}
	// returns calculated locations of NoteBubbles from 1st - 12th frets
	public static float[] notePosition(float[] fretLocation, int frets) 
	{
		float[] noteLocation = new float[frets];
		noteLocation[0] = fretLocation[0] / 2;
		noteLocation[1] = fretLocation[1] - (fretLocation[1] - fretLocation[0]) / 2;
		noteLocation[2] = fretLocation[2] - (fretLocation[2] - fretLocation[1]) / 2;
		noteLocation[3] = fretLocation[3] - (fretLocation[3] - fretLocation[2]) / 2;
		noteLocation[4] = fretLocation[4] - (fretLocation[4] - fretLocation[3]) / 2;
		noteLocation[5] = fretLocation[5] - (fretLocation[5] - fretLocation[4]) / 2;
		noteLocation[6] = fretLocation[6] - (fretLocation[6] - fretLocation[5]) / 2;
		noteLocation[7] = fretLocation[7] - (fretLocation[7] - fretLocation[6]) / 2;
		noteLocation[8] = fretLocation[8] - (fretLocation[8] - fretLocation[7]) / 2;
		noteLocation[9] = fretLocation[9] - (fretLocation[9] - fretLocation[8]) / 2;
		noteLocation[10] = fretLocation[10] - (fretLocation[10] - fretLocation[9]) / 2;
		noteLocation[11] = fretLocation[11] - (fretLocation[11] - fretLocation[10]) / 2;

		return noteLocation;
	}

	private void Inlays(Pane pane, int stageHeight, int imageHeight, int fretCount) 
	{

		Inlay inlay3 = new Inlay(fretLocation, 2, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay5 = new Inlay(fretLocation, 4, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay7 = new Inlay(fretLocation, 6, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay9 = new Inlay(fretLocation, 8, 15, Color.ALICEBLUE, stageHeight);
		Inlay inlay12a = new Inlay(fretLocation, 11, 15, Color.ALICEBLUE, stageHeight - 74);
		Inlay inlay12b = new Inlay(fretLocation, 11, 15, Color.ALICEBLUE, stageHeight + 74);

		pane.getChildren().addAll(inlay3, inlay5, inlay7, inlay9, inlay12a, inlay12b);
	}

}
