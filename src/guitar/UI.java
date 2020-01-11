package src.guitar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class UI {

	GuitarString guitar = new GuitarString();
	String[] notes = guitar.notes;

	public UI(Pane pane) 
	{

		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("Major", "Minor");
		comboBox.autosize();
		comboBox.setPromptText("Scale");

		pane.getChildren().add(comboBox);
		
	}
	
	

	private void CreateButton(Pane pane, int offset, int stringYPos) {

		float yLocation = stringYPos + offset - 10;

		MenuButton mButton = new MenuButton();
		// add all entries from local notes[] inserted as MenuItems into each
		// MenuButton options list
		for (int i = 0; i < notes.length; i++) {
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

	private void RootNote(float stringY) {

		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#");
		comboBox.isResizable();
		comboBox.setPrefWidth(5);
		// comboBox.setButtonCell("hi");
		comboBox.setVisibleRowCount(5); // max rows displayed
		comboBox.getSelectionModel().select(0); // default display
		comboBox.relocate(0, stringY);

	}

	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			System.out.println("asdfsadf");
		}
	};

}
