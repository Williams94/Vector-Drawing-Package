package shapes;
import java.awt.Color;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Shape extends JPanel {
    private Point start;
    private Point end;
    private Color color;
    private int shapeWidth;
    private int shapeHeight;
    private String text;
    private boolean fill;
    private int thickness;
    private int opacity;
    
    public Shape(){
    	
    }

    // Constructor for Line
    public Shape(Point start, Point end, Color color, int thickness, int opacity){
        this.setStart(start);
        this.setEnd(end);
        this.setColor(color);
        this.setThickness(thickness);
        this.setOpacity(opacity);
    }
    
    // Constructor for Text
    public Shape(String text, Point end, Color color, int thickness, int opacity){
    	this.setText(text);
    	this.setEnd(end);
    	this.setColor(color);
    	this.setThickness(thickness);
    	this.setOpacity(opacity);
    }
    
    // Constructor for Rectangle & Oval
    public Shape(Point start, int width, int height, Color color, int thickness, boolean fill, int opacity){
        this.setStart(start);
        this.setShapeWidth(width);
        this.setShapeHeight(height);
        this.setColor(color);
        this.setThickness(thickness);
        this.setFill(fill);
        this.setOpacity(opacity);
    }
    
    // Constructor for Parallelogram
    public Shape(Point start, Point end, int width, int height, Color color, int thickness, boolean fill, int opacity) {
    	this.setStart(start);
    	this.setEnd(end);
    	this.setShapeWidth(width);
    	this.setShapeHeight(height);
    	this.setColor(color);
    	this.setThickness(thickness);
    	this.setFill(fill);
    	this.setOpacity(opacity);
    }

    @SuppressWarnings("unused")
	public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

	public int getShapeWidth() {
		return shapeWidth;
	}

	public void setShapeWidth(int shapeWidth) {
		this.shapeWidth = shapeWidth;
	}

	public int getShapeHeight() {
		return shapeHeight;
	}

	public void setShapeHeight(int shapeHeight) {
		this.shapeHeight = shapeHeight;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	public int getOpacity() {
		return opacity;
	}

	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
    

}
