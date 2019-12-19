package src.guitar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

	GuitarString(Pane pane, int stageHeight, int imageHeight, int imageWidth, int stringCount, 
			float[] noteLocation, int index) 
	{
		stringYPos = stageHeight / 2 - imageHeight / 2;
		int offset = 20 + index * ((imageHeight - 5) / stringCount);
		Line guitarString = new Line();
		
		StringProperties(pane, guitarString, stageHeight, imageHeight, imageWidth, stringCount, 
				noteLocation, index, offset);
		CreateButton(pane, offset);
		// RootNote(stringY, pane);
	}
	
	private void CreateButton(Pane pane, int offset)
	{
		
		float yLocation = stringYPos + offset - 10;
		
		MenuButton mButton = new MenuButton();
		// add all entries from local notes[] inserted as MenuItems into each MenuButton options list
		for(int i = 0; i < notes.length; i++)
		{
			MenuItem item = new MenuItem(notes[i]);
			mButton.getItems().add(item);
		}
		
		mButton.relocate(0, yLocation);
		mButton.isResizable();
		mButton.autosize();
		mButton.toFront();
		mButton.setOnAction(event);
		
		mButton.setOnAction(event);
		pane.getChildren().add(mButton);
		
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

	ComboBox<String> RootNote(float stringY) {

		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#");
		comboBox.isResizable();
		comboBox.setPrefWidth(5);
		// comboBox.setButtonCell("hi");
		comboBox.setVisibleRowCount(5); // max rows displayed
		comboBox.getSelectionModel().select(0); // default display
		comboBox.relocate(0, stringY);

		return comboBox;

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

    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>()
    {
        public void handle(ActionEvent e)
        {
        	System.out.println("asdfsadf");
        }
    };

}
