package shapes;

import java.awt.Color;
import java.awt.Point;

@SuppressWarnings("serial")
public class Text extends Shape {

	public Text(){
		
	}
	public Text(String text, Point end, Color color, int thickness, int opacity) {
		super(text, end, color, thickness, opacity);
	}

}
