package shapes;

import java.awt.Color;
import java.awt.Point;

@SuppressWarnings("serial")
public class Rect extends Shape {
	
	public Rect(){
		
	}

    public Rect(Point start, int width, int height, Color color, int thickness, boolean fill, int opacity) {
        super(start, width, height, color, thickness, fill, opacity);
    }
    

}
