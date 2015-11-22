package view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

import models.Model;
import shapes.Circle;
import shapes.Line;
import shapes.Parallelogram;
import shapes.Rect;
import shapes.Shape;
import shapes.Text;
import shapes.Triangle;

public class DrawPanel extends JPanel {

	private Model model;

	// Shapes for real-time drawing
	private Line line;
	private Rect rect;
	private Circle circle;
	private Parallelogram parall;
	private Text text;
	private Triangle tri;
	private Image img;

	private static final Color DEFAULT_COLOR = Color.BLACK;

	public DrawPanel(Model model) {
		this.model = model;
	}

	public void paint(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(DEFAULT_COLOR);
		
		if (View.getImageToImport() != null) {
			g.drawImage(View.getImageToImport(), 0, 0, this);
		}
		
		reDrawShapes(g2);

		realTimeDrawing(g2);
	}
	
	public void reDrawShapes(Graphics g2){
		// Re-Draw all shapes
				for (Shape shape : model.getShapes()) {
					g2.setColor(shape.getColor());
					((Graphics2D) g2).setStroke(new BasicStroke(shape.getThickness()));
					((Graphics2D) g2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float) shape.getOpacity() / 100)));

					try {
						if (shape instanceof Line) {

							g2.drawLine(shape.getStart().x, shape.getStart().y, shape.getEnd().x, shape.getEnd().y);

						} else if (shape instanceof Rect) {
							// g2.setColor(shape.getColor());
							((Graphics2D) g2).setStroke(new BasicStroke(shape.getThickness()));
							if (shape.isFill()) {
								g2.fillRect(shape.getStart().x, shape.getStart().y, shape.getShapeWidth(),
										shape.getShapeHeight());
							} else if (!shape.isFill()) {
								g2.drawRect(shape.getStart().x, shape.getStart().y, shape.getShapeWidth(),
										shape.getShapeHeight());
							}

						} else if (shape instanceof Circle) {
							if (shape.isFill()) {
								g2.fillOval(shape.getStart().x, shape.getStart().y, shape.getShapeWidth(),
										shape.getShapeHeight());
							} else if (!shape.isFill()) {
								g2.drawOval(shape.getStart().x, shape.getStart().y, shape.getShapeWidth(),
										shape.getShapeHeight());
							}

						} else if (shape instanceof Parallelogram) {

							Path2D.Double p = new Path2D.Double();
							p.moveTo(shape.getStart().getX(), shape.getStart().getY());
							p.lineTo(shape.getStart().getX() + shape.getShapeWidth(), shape.getStart().getY());
							p.lineTo(shape.getEnd().getX() + shape.getShapeWidth(), shape.getEnd().getY());
							p.lineTo(shape.getEnd().getX() - shape.getShapeWidth(), shape.getEnd().getY());
							p.lineTo(shape.getStart().getX() - shape.getShapeWidth(), shape.getStart().getY());
							p.closePath();

							if (shape.isFill()) {
								((Graphics2D) g2).fill(p);
							} else if (!shape.isFill()) {
								((Graphics2D) g2).draw(p);
							}

						} else if (shape instanceof Text) {
							g2.setFont(new Font("TimesRoman", Font.PLAIN, (shape.getThickness() * 10)));
							g2.drawString(shape.getText(), shape.getEnd().x, shape.getEnd().y);

						} else if (shape instanceof Triangle) {
							Path2D.Double p = new Path2D.Double();
							p.moveTo(shape.getStart().getX(), shape.getStart().getY());
							p.lineTo(shape.getStart().getX() + shape.getShapeWidth(),
									shape.getEnd().getY() - shape.getShapeHeight());
							p.lineTo(shape.getStart().getX() - shape.getShapeWidth(),
									shape.getEnd().getY() - shape.getShapeHeight());
							p.lineTo(shape.getStart().getX(), shape.getStart().getY());
							p.closePath();

							if (shape.isFill()) {
								((Graphics2D) g2).fill(p);
							} else if (!shape.isFill()) {
								((Graphics2D) g2).draw(p);
							}
						} 
						

					} catch (NullPointerException npe) {
					}
				}
	}
	
	public void realTimeDrawing(Graphics g2){

		// Draw shapes while moving mouse
		if (rect != null) {

			Rectangle r = new Rectangle(rect.getShapeWidth(), rect.getShapeHeight());
			r.setBounds((int) rect.getStart().getX(), (int) rect.getStart().getY(), rect.getShapeWidth(),
					rect.getShapeHeight());

			g2.setColor(rect.getColor());
			((Graphics2D) g2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float) rect.getOpacity() / 100)));
			((Graphics2D) g2).setStroke(new BasicStroke(rect.getThickness()));

			if (rect.isFill()) {
				((Graphics2D) g2).fill(r);
			} else {
				((Graphics2D) g2).draw(r);
			}

			rect = null;
		} else if (circle != null) {
			Ellipse2D.Double e = new Ellipse2D.Double(circle.getStart().getX(), circle.getStart().getY(),
					circle.getShapeWidth(), circle.getShapeHeight());

			g2.setColor(circle.getColor());
			((Graphics2D) g2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float) circle.getOpacity() / 100)));
			((Graphics2D) g2).setStroke(new BasicStroke(circle.getThickness()));

			if (circle.isFill()) {
				((Graphics2D) g2).fill(e);
			} else {
				((Graphics2D) g2).draw(e);
			}

			circle = null;
		} else if (parall != null) {
			Path2D.Double p = new Path2D.Double();
			p.moveTo(parall.getStart().getX(), parall.getStart().getY());
			p.lineTo(parall.getStart().getX() + parall.getShapeWidth(), parall.getStart().getY());
			p.lineTo(parall.getEnd().getX() + parall.getShapeWidth(), parall.getEnd().getY());
			p.lineTo(parall.getEnd().getX() - parall.getShapeWidth(), parall.getEnd().getY());
			p.lineTo(parall.getStart().getX() - parall.getShapeWidth(), parall.getStart().getY());
			p.closePath();

			g2.setColor(parall.getColor());
			((Graphics2D) g2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float) parall.getOpacity() / 100)));
			((Graphics2D) g2).setStroke(new BasicStroke(parall.getThickness()));

			if (parall.isFill()) {
				((Graphics2D) g2).fill(p);
			} else if (!parall.isFill()) {
				((Graphics2D) g2).draw(p);
			}
			parall = null;
		} else if (line != null) {
			g2.setColor(line.getColor());
			((Graphics2D) g2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float) line.getOpacity() / 100)));
			((Graphics2D) g2).setStroke(new BasicStroke(line.getThickness()));

			g2.drawLine(line.getStart().x, line.getStart().y, line.getEnd().x, line.getEnd().y);
			line = null;
		} else if (tri != null) {

			Path2D.Double p = new Path2D.Double();
			p.moveTo(tri.getStart().getX(), tri.getStart().getY());
			// p.lineTo(parall.getStart().getX() + parall.getShapeWidth(),
			// parall.getStart().getY());
			p.lineTo(tri.getStart().getX() + tri.getShapeWidth(), tri.getEnd().getY() - tri.getShapeHeight());
			p.lineTo(tri.getStart().getX() - tri.getShapeWidth(), tri.getEnd().getY() - tri.getShapeHeight());
			p.lineTo(tri.getStart().getX(), tri.getStart().getY());
			p.closePath();

			g2.setColor(tri.getColor());
			((Graphics2D) g2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float) tri.getOpacity() / 100)));
			((Graphics2D) g2).setStroke(new BasicStroke(tri.getThickness()));

			if (tri.isFill()) {
				((Graphics2D) g2).fill(p);
			} else if (!tri.isFill()) {
				((Graphics2D) g2).draw(p);
			}
			tri = null;

		}

	}

	public void setShape(Shape shape) {
		if (shape instanceof Rect) {
			rect = (Rect) shape;
		} else if (shape instanceof Circle) {
			circle = (Circle) shape;
		} else if (shape instanceof Parallelogram) {
			parall = (Parallelogram) shape;
		} else if (shape instanceof Line) {
			line = (Line) shape;
		} else if (shape instanceof Triangle) {
			tri = (Triangle) shape;
		}
	}

}
