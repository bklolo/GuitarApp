package src.guitar;

import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GuitarString extends Line {

	private float stringYPos;
	
	GuitarString(Pane pane, int stageHeight, int imageHeight, int imageWidth, int stringCount) {
		
		stringYPos = stageHeight / 2 - imageHeight / 2;

		for(int i = 0; i < stringCount; i++){
		
		Line guitarString = new Line();
		
		int offset = 20 + i * ((imageHeight - 5) / 6);
		
		guitarString.setScaleY(2.5 + .5 * i);
		guitarString.setLayoutY(offset);
		
		guitarString.setStroke(Color.rgb(255, 175, 51));
		
		guitarString.setStartX(0);
		guitarString.setEndX(imageWidth);
		
		guitarString.setStartY(stringYPos);
		guitarString.setEndY(stringYPos);
		
		shadow(guitarString);
		
		MenuButton mButton = Menu(stringYPos + offset - 10);
		pane.getChildren().addAll(guitarString, mButton);
		}
		// RootNote(stringY, pane);
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

	private MenuButton Menu(float yLocation) {
		String[] notes = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
		MenuItem As = new MenuItem("A#");
		MenuItem B = new MenuItem("B");
		MenuItem C = new MenuItem("C");
		MenuItem Cs = new MenuItem("C#");
		MenuItem D = new MenuItem("D");
		MenuItem Ds = new MenuItem("D#");
		MenuItem E = new MenuItem("E");
		MenuItem F = new MenuItem("F");
		MenuItem Fs = new MenuItem("F#");
		MenuItem G = new MenuItem("G");
		MenuItem Gs = new MenuItem("G#");
		MenuItem A = new MenuItem("A");
		MenuButton menu = new MenuButton(notes[0]);
		menu.getItems().addAll(As,B,C,Cs,D,Ds,E,F,Fs,G,Gs,A);
		menu.relocate(0, yLocation);
		menu.autosize();
		menu.toFront();

		return menu;
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

}
