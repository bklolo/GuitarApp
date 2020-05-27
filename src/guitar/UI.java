package src.guitar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

public class UI {

	public UI(Pane pane)
	{

		ComboBox<String> scales = new ComboBox<>();
		scales.getItems().addAll("Major", "Minor");
		scales.autosize();
		scales.setPromptText("Scale");
		scales.setLayoutX(450);
		
		ComboBox<String> keys = new ComboBox<>();
		keys.getItems().addAll("A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#");
		keys.autosize();
		keys.setPromptText("Key");
		keys.setLayoutX(650);
		keys.setVisibleRowCount(5);

		pane.getChildren().addAll(scales, keys);
		
	}
	
	//attempt to create a generic combo box handler
//	public void comboBox_OnSelectionChanged(Object sender, ActionEvent e)
//	{
//	    if (sender.equals(ComboBoxBase<T>) == ComboBox<T>)
//	    {
//	        ComboBox box = (ComboBox)sender;
//	        //do what you like with it
//	    }
//	}

	
	public static <T> ComboBox<T> CreateComboBox(T[] items, int select, int xLayout, int yLayout){
		
		ComboBox<T> box = new ComboBox<>();
		
		for(int i = 0; i < items.length; i++){
			box.getItems().add(items[i]);
		}
		
		box.autosize();
//		box.setPromptText();	// default displayed text
		box.getSelectionModel().select(items[select]);
		box.setLayoutX(xLayout);
		box.setLayoutY(yLayout);
		box.setVisibleRowCount(5);
		
		return box;
	}

	EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() 
	{
		public void handle(ActionEvent e) {
			
		}
	};

}
