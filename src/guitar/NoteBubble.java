package src.guitar;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class NoteBubble extends Circle {

	private static double radius = 13;
	private static Color color = Color.ALICEBLUE;
	private float bubbleX, bubbleY;
	private boolean noteOn = false;

	NoteBubble(int offset, float stringYPos) 
	{
		super(radius, color);
		super.setFill(color);
		super.setStroke(Color.BLACK);
		
		bubbleY = (float) (stringYPos + offset + (getRadius() / 2));
		super.setCenterY(bubbleY-5);
		super.setCenterX(bubbleX);
		super.setOpacity(.75);
	}

	public Color getColor() 
	{
		return color;
	}
	
	public void setColor(Color newColor){
		NoteBubble.color = newColor;
	}

	public boolean isNoteOn() {
		return noteOn;
	}

	public void setNoteOn(boolean noteOn) {
		this.noteOn = noteOn;
	}
	
    @Override
    public String toString() { 
        return String.format(this.getId()); 
    } 

}