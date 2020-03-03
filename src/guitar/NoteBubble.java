package src.guitar;

import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NoteBubble extends Circle {

	private static double radius = 13;
	private static Color color = Color.ALICEBLUE;
	private float bubbleX, bubbleY;
	private boolean[] display;

	NoteBubble(Pane pane, MenuButton mButton, int offset, float stringYPos, float[] noteLocation) 
	{

		super(radius, color);

		// circle features: size, shape, color
		// button feature: clickable, plays note
		super.setFill(color);
		super.setStroke(Color.BLACK);

		
		bubbleY = (float) (stringYPos + offset + getRadius() / 2);

		super.setCenterY(bubbleY);
		super.setCenterX(bubbleX);
		super.setOpacity(.75);
		pane.getChildren().add(this);

	}

	public Color getColor() 
	{
		return color;
	}

	public boolean displayNote(Control scale, Control key) 
	{
		return true;
	}

}