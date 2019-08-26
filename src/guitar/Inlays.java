package src.guitar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Inlays extends Circle {
	
	float position;
	
	Inlays(float[] fretArray, int index, int radius, Color color, int stageHeight, Pane pane){
		super(radius, color);
		super.setCenterX(Inlays.inlayPosition(fretArray, index));
		super.setCenterY(stageHeight / 2);

	}
	
	public static float inlayPosition(float[] fretArray, int index){
		float inlayPos = (fretArray[index] - ((fretArray[index] - fretArray[index - 1]) / 2)) * 100;
		
		return inlayPos;
	}
	

}
