
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NoteBubble extends Circle {

	private double radius = 13;
	private Color color;
	private float bubbleX, bubbleY;
	private boolean noteOn = false;
	private Text text = new Text();

	NoteBubble(int offset, float bubbleX, float stringYPos, String noteLetter, Color color) 
	{
		super();
		this.color = color;
		super.setRadius(radius);
		super.setStroke(Color.BLACK);
		super.setFill(color);
		this.bubbleX = bubbleX;
		super.setCenterX(bubbleX);
		bubbleY = (float) (stringYPos + offset + (getRadius() / 2));
		super.setCenterY(bubbleY-5);
		super.setOpacity(.75);
		textProperties(noteLetter);
	}

	private void textProperties(String noteLetter){
		text.setX(bubbleX-3);
		text.setY(bubbleY-3);
		text.setText(noteLetter);
		text.setFill(Color.BLACK);
		text.setFont(Font.font ("Calibri", FontWeight.BOLD, 10.5));
		text.toFront();
	}
	public Color getColor() 
	{
		return color;
	}
	
	public void setColor(Color newColor){
		this.color = newColor;
	}
	
	public Text getText(){
		return text;
	}
	
	public void setText(String note){
		this.text.setText(note);
	}
	
    @Override
    public String toString() { 
        return "color: " + color.toString() + "\tnote: " + text.getText(); 
    } 

}