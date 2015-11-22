package MouseListener;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import models.Model;
import shapes.Shape;

public class ObjectsMouseListener extends MouseInputAdapter {

	private Model model;
	private Shape shape;
	private Image image;
	private int x;
	private int y;

	public ObjectsMouseListener(Model model) {
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (Shape s : model.getShapes()) {
			if (e.getX() > s.getStart().getX() && e.getX() < (s.getStart().getX() + s.getShapeWidth())
					&& (e.getY() > s.getStart().getY() && e.getY() < (s.getStart().getY() + s.getShapeHeight()))) {
				this.shape = s;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (shape != null) {
			x = e.getX();
			y = e.getY();
			shape.setStart(new Point(x, y));
			model.updateShapeStart(shape);
		} else {
			System.out.println("No shape selected");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (shape != null) {
			// model.removeShape(shape);
			model.addShape(shape);
			this.shape = null;
		} else {
			System.out.println("No shape selected");
		}
	}

}
