package shapes;

import java.awt.Color;
import java.awt.Point;

@SuppressWarnings("serial")
public class Parallelogram extends Shape {
	
	public Parallelogram(){
		
	}

	public Parallelogram(Point start, Point end, int width, int height, Color color, int thickness, boolean fill, int opacity) {
		super(start, end, width, height, color, thickness, fill, opacity);
	}

}
