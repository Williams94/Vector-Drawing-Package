package shapes;

import java.awt.Color;
import java.awt.Point;

@SuppressWarnings("serial")
public class Circle extends Shape {

	public Circle(){
		
	}
	
	public Circle(Point start, int width, int height, Color color, int thickness, boolean fill, int opacity) {
        super(start, width, height, color, thickness, fill, opacity);
    }
}
