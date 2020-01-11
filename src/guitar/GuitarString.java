package src.guitar;

import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

// the class that creates each guitar string. 
// string number dependent

public class GuitarString extends Line 
{

	private float stringYPos;
	String[] notes = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
	

	// creates a guitar string object that is determined by stage height and
	// imageWidth
	// image chosen by user feature?

	public GuitarString()
	{
		
	}
	
	GuitarString(Pane pane, int stageHeight, int imageHeight, int imageWidth, int stringCount, 
			float[] noteLocation, int index) 
	{
		stringYPos = stageHeight / 2 - imageHeight / 2;
		int offset = 20 + index * ((imageHeight - 5) / stringCount);
		Line guitarString = new Line();
		
		StringProperties(pane, guitarString, stageHeight, imageHeight, imageWidth, stringCount, 
				noteLocation, index, offset);

//		RootNote(stringY, pane);
	}

	private void StringProperties(Pane pane, Line guitarString, int stageHeight, int imageHeight, int imageWidth, int stringCount, 
			float[] noteLocation, int index, int offset) 
	{
			guitarString.setScaleY(2.5 + .5 * index);
			guitarString.setLayoutY(offset);
			guitarString.setStroke(Color.rgb(255, 175, 51));
			guitarString.setStartX(0);
			guitarString.setEndX(imageWidth);
			guitarString.setStartY(stringYPos);
			guitarString.setEndY(stringYPos);
			guitarString.toBack();
			shadow(guitarString);
			
			pane.getChildren().add(guitarString);
			// menubox needs to be created with correct Note letter EADGBe
			// default NoteBubble array should reflect menubox's Note letter
			// create list of arrays from which they would be chosen
			// create true/false arrays, send to NoteBubble to create that sequence of NoteBubble if 1, no if 0

	}

	private DropShadow shadow(Line line) {
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(1.0);
		shadow.setHeight(1);
		shadow.setColor(Color.BLACK);
		shadow.setRadius(.5);
		line.setEffect(shadow);
		return shadow;
	}



}
