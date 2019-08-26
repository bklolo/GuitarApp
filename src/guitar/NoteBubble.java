package src.guitar;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NoteBubble extends Circle {
	
	double radius;
	Color color;
	
	NoteBubble(double radius, Color color){
		super(radius, color);		
		
	}
	
	public static float notePosition(float[] fretArray, int index){
		float inlayPos = (fretArray[index] - ((fretArray[index] - fretArray[index - 1]) / 2)) * 100;
		
		return inlayPos;
	}
	
}
