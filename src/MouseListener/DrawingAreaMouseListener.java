package MouseListener;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.event.MouseInputAdapter;

import enums.ShapeToDraw;
import models.Model;
import shapes.Circle;
import shapes.Line;
import shapes.Parallelogram;
import shapes.Rect;
import shapes.Text;
import shapes.Triangle;
import view.View;

public class DrawingAreaMouseListener extends MouseInputAdapter {

	private Point start;
	private Point end;
	private int width;
	private int height;
	
	

	private ShapeToDraw shape;
	private Color color;
	private int thickness;
	private boolean fill;
	private int opacity;

	private Model model;

	private Rect rect;
	private Circle circle;
	private Line line;
	private Parallelogram p;
	private Text text;
	private Triangle tri;
	private Image img;
	private BufferedImage image;

	private int x;
	private int y;
	
	public static boolean clicked;

	public DrawingAreaMouseListener(Model model) {
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		shape = View.getShape();
		color = View.getColor();
		thickness = View.getThickness();
		fill = View.isFill();
		opacity = View.getOpacity();
		image = View.getImageToImport();

		this.start = e.getPoint();

		if (shape != null) {
			switch (shape) {
			case RECTANGLE:
				rect = new Rect();
				break;
			case CIRCLE:
				circle = new Circle();
				break;
			case LINE:
				line = new Line();
				line.setStart(start);
				break;
			case PARALLELOGRAM:
				p = new Parallelogram();
				p.setStart(start);
				break;
			case TEXT:
				break;
			case TRIANGLE:
				tri = new Triangle();
				tri.setStart(start);
				break;
			default:
				break;

			}
		}
	}

	public void mouseDragged(MouseEvent e) {

		this.width = (int) Math.abs(getStart().getX() - e.getX());
		this.height = (int) Math.abs(getStart().getY() - e.getY());

		x = Math.min(start.x, e.getX());
		y = Math.min(start.y, e.getY());

		if (shape != null) {
			switch (shape) {
			case RECTANGLE:
				rect.setStart(new Point(x, y));
				rect.setShapeWidth(width);
				rect.setShapeHeight(height);
				rect.setColor(color);
				rect.setThickness(thickness);
				rect.setFill(fill);
				rect.setOpacity(opacity);
				model.update(rect);
				break;
			case CIRCLE:
				circle.setColor(color);
				circle.setThickness(thickness);
				circle.setFill(fill);
				circle.setStart(new Point(x, y));
				circle.setShapeWidth(width);
				circle.setShapeHeight(height);
				circle.setOpacity(opacity);
				model.update(circle);
				break;
			case LINE:
				line.setColor(color);
				line.setThickness(thickness);
				line.setFill(fill);
				line.setOpacity(opacity);
				line.setEnd(new Point(e.getX(), e.getY()));
				model.update(line);
				break;
			case PARALLELOGRAM:
				p.setColor(color);
				p.setThickness(thickness);
				p.setFill(fill);
				p.setOpacity(opacity);
				p.setShapeWidth((int) (getStart().getX() - e.getX()));
				p.setShapeHeight((int) (getStart().getY() - e.getY()));
				p.setEnd(new Point(e.getX(), e.getY()));
				model.update(p);
				break;
			case TEXT:
				break;
			case TRIANGLE:
				tri.setColor(color);
				tri.setThickness(thickness);
				tri.setFill(fill);
				tri.setOpacity(opacity);
				tri.setShapeWidth((int) (getStart().getX() - e.getX()));
				tri.setShapeHeight((int) (getStart().getY() - e.getY()));
				tri.setEnd(new Point(e.getX(), e.getY()));
				model.update(tri);
				break;
			default:
				break;

			}
		}

	}

	public void mouseReleased(MouseEvent e) {

		if (shape != null) {
			switch (shape) {
			case RECTANGLE:
				model.addShape(rect);
				rect = null;
				break;
			case CIRCLE:
				model.addShape(circle);
				circle = null;
				break;
			case LINE:
				model.addShape(line);
				line = null;
				break;
			case PARALLELOGRAM:
				model.addShape(p);
				p = null;
				break;
			case TEXT:
				Text text = new Text(View.getTextToDraw(), new Point(e.getX(), e.getY()), color, thickness, opacity);
				model.addShape(text);
				break;
			case TRIANGLE:
				model.addShape(tri);
				tri = null;
				break;
			default:
				break;

			}
		}

	}

	private Point getStart() {
		return start;
	}

	private void setStart(Point start) {
		this.start = start;
	}

	private Point getEnd() {
		return end;
	}

	private void setEnd(Point end) {
		this.end = end;
	}

	@SuppressWarnings("unused")
	private int getWidth() {
		return width;
	}

	private void setWidth(int width) {
		this.width = width;
	}

	@SuppressWarnings("unused")
	private int getHeight() {
		return height;
	}

	private void setHeight(int height) {
		this.height = height;
	}
}
