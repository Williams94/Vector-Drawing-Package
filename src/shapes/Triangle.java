package shapes;

import java.awt.Color;
import java.awt.Point;

public class Triangle extends Shape {
	
	public Triangle(){
		
	}

	public Triangle(Point start, Point end, Color color, int thickness, int opacity){
        super(start, end, color, thickness, opacity);
    }
}
