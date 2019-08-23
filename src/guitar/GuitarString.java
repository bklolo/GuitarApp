package src.guitar;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GuitarString extends Line {

	GuitarString(float[] fretArray, float stringY, int imageHeight, int frets, Pane pane) {

		Line string = new Line();
		string.setStroke(Color.GOLD);
		string.setStartX(0);
		string.setEndX(fretArray[frets - 1] * 100);
		string.setStartY(stringY);
		pane.getChildren().add(string);
		RootNote(stringY, pane);

	}

	public ComboBox<String> RootNote(float stringY, Pane pane) {

		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#");
		comboBox.setEditable(true);
		comboBox.setButtonCell(null);
		comboBox.setVisibleRowCount(5);			// max rows displayed
		comboBox.getSelectionModel().select(0); // default display
		comboBox.relocate(0, stringY );
		pane.getChildren().add(comboBox);
		return comboBox;

	}

}
