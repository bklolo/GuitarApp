package src.guitar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Inlay extends Circle {
	
	float position;
	
	Inlay(float[] fretArray, int index, int radius, Color color, int stageHeight){
		super(radius, color);
		super.setCenterX(Inlay.inlayPosition(fretArray, index));
		super.setCenterY(stageHeight / 2);

	}
	
	public static float inlayPosition(float[] fretArray, int index){
		float inlayPos = (fretArray[index] - ((fretArray[index] - fretArray[index - 1]) / 2)) * 100;
		
		return inlayPos;
	}
	

}
