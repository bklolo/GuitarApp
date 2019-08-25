package src.guitar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Inlays extends Circle {
	
	float position;
	int radius = 15;
	Color color;
	
	Inlays(float[] fretArray, int index, int radius, Color color, Pane pane){
		this.position = Inlays.inlayPosition(fretArray, index);
		this.radius = radius;
		this.color = color;
		
	}
	
	public static float inlayPosition(float[] fretArray, int index){
		float inlayPos = (fretArray[index] - ((fretArray[index] - fretArray[index - 1]) / 2)) * 100;
		
		return inlayPos;
	}
	

}
