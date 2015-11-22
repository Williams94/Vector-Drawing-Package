package shapes;

import java.awt.Color;
import java.awt.Point;

@SuppressWarnings("serial")
public class Line extends Shape {
    
    public Line(){
    	
    }

    public Line(Point start, Point end, Color color, int thickness, int opacity){
        super(start, end, color, thickness, opacity);
    }
}
