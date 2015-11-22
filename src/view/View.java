package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import MouseListener.DrawingAreaMouseListener;
import MouseListener.ObjectsMouseListener;
import enums.ShapeToDraw;
import models.Model;
import shapes.Shape;

@SuppressWarnings("serial")
public class View implements Observer {

	private static final int FRAME_HEIGHT = 600;
	private static final int FRAME_WIDTH = 800;
	private static final int DEAFULT_OPACITY = 100;

	private Model model;

	private JFrame mainFrame;
	private DrawPanel drawPanel;

	// Toolbars
	private JToolBar toolbar;
	private JToolBar bottomBar;

	// Action Buttons
	private JButton lineBtn;
	private JButton rectangleBtn;
	private JButton circleBtn;
	private JButton triangleBtn;
	private JButton parallelogramBtn;
	private JButton addText;
	private JButton dragBtn;
	private JButton drawBtn;
	private JButton colorBtn;
	private JCheckBox fillBtn;

	// Other Swing Components
	private JMenuBar menu;
	private JLabel textLabel;
	private JTextField inputField;
	private JSlider sizeSlider;
	private JSlider opacitySlider;

	// Menu
	private JMenu file;
	private JMenu edit;
	private JMenuItem load;
	private JMenuItem save;
	private JMenuItem clear;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem importImage;

	// Operation variables
	private static ShapeToDraw shape;
	private static Color color;
	private static String textToDraw;
	private static int thickness = 1;
	private static boolean fill = false;
	private boolean drag = false;
	private boolean draw = false;
	private static int opacity = DEAFULT_OPACITY;

	private DrawingAreaMouseListener ml;
	private ObjectsMouseListener oml;

	private JFileChooser fileChooser;
	private static BufferedImage imageToImport;

	public View(Model model) {
		this.model = model;

		this.mainFrame = new JFrame();
		this.drawPanel = new DrawPanel(model);

		menu = new JMenuBar();
		toolbar = new JToolBar();
		bottomBar = new JToolBar();
		inputField = new JTextField(10);
		setupComponents();

		ml = new DrawingAreaMouseListener(this.model);
		oml = new ObjectsMouseListener(this.model);

		model.addObserver(this);
	}

	private void setupComponents() {
		setupMenu();
		setupBottomBar();
		setupToolbar();
		mainFrame.add(drawPanel, BorderLayout.CENTER);
		mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setupToolbar() {

		lineBtn = new JButton("Line");
		lineBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape(ShapeToDraw.LINE);
			}
		});

		rectangleBtn = new JButton("Rectangle");
		rectangleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape(ShapeToDraw.RECTANGLE);
			}
		});

		circleBtn = new JButton("Circle");
		circleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape(ShapeToDraw.CIRCLE);
			}
		});

		triangleBtn = new JButton("Triangle");
		triangleBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setShape(ShapeToDraw.TRIANGLE);
			}
			
		});		
		parallelogramBtn = new JButton("Parallelogram");
		parallelogramBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape(ShapeToDraw.PARALLELOGRAM);
			}
		});
		

		textLabel = new JLabel("Enter Text: ");
		

		addText = new JButton("Add Text");
		addText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape(ShapeToDraw.TEXT);
				setTextToDraw(inputField.getText());
			}
		});

		dragBtn = new JButton("Drag");
		dragBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isDrag()) {
					drawPanel.addMouseListener(oml);
					drawPanel.addMouseMotionListener(oml);
					setDrag(true);
					System.out.println("Dragging");
					setDraw(false);
					drawPanel.removeMouseListener(ml);
					drawPanel.removeMouseMotionListener(ml);
					System.out.println("Not Drawing");
					drawPanel.repaint();
				} else if (isDrag()) {
					setDrag(false);
					System.out.println("Not Dragging");
					drawPanel.removeMouseListener(oml);
					drawPanel.removeMouseMotionListener(oml);
					drawPanel.repaint();
				}
			}
		});

		drawBtn = new JButton("Draw");
		drawBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isDraw()) {
					drawPanel.addMouseListener(ml);
					drawPanel.addMouseMotionListener(ml);
					setDraw(true);
					System.out.println("Drawing");
					setDrag(false);
					System.out.println("Not Dragging");
					drawPanel.removeMouseListener(oml);
					drawPanel.removeMouseMotionListener(oml);
					drawPanel.repaint();
				} else if (isDraw()) {
					setDraw(false);
					drawPanel.removeMouseListener(ml);
					drawPanel.removeMouseMotionListener(ml);
					System.out.println("Not Drawing");
					drawPanel.repaint();
				}
			}
		});
		colorBtn = new JButton("Color");
		colorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setColor(colorPicker());
				fillBtn.setBackground(getColor());
			}

		});
		fillBtn = new JCheckBox("Fill");
		fillBtn.setBackground(getColor());
		fillBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isFill()) {
					setFill(false);
				} else {
					setFill(true);
				}
			}
		});

		toolbar.add(drawBtn);
		toolbar.add(dragBtn);
		toolbar.add(colorBtn);
		toolbar.add(fillBtn);
		toolbar.add(lineBtn);
		toolbar.add(rectangleBtn);
		toolbar.add(circleBtn);
		toolbar.add(triangleBtn);
		toolbar.add(parallelogramBtn);
		toolbar.add(textLabel);
		toolbar.add(inputField);
		toolbar.add(addText);
		mainFrame.add(toolbar, BorderLayout.NORTH);
	}

	public void setupBottomBar() {
		sizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 1);
		sizeSlider.setToolTipText("Increase stroke thickness");
		sizeSlider.setPaintLabels(true);
		Hashtable<Integer, JLabel> table1 = new Hashtable<Integer, JLabel>();
		table1.put (1, new JLabel("Thin"));
		table1.put (20, new JLabel("Thick        "));
		sizeSlider.setLabelTable (table1);  
		
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				setThickness(sizeSlider.getValue());
			}
		});
		
		opacitySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		opacitySlider.setPaintLabels(true);
		Hashtable<Integer, JLabel> table2 = new Hashtable<Integer, JLabel>();
		table2.put (0, new JLabel("     Transparent"));
		table2.put (100, new JLabel("Opaque"));
		opacitySlider.setLabelTable (table2);
		opacitySlider.setToolTipText("Increase opacity");
		opacitySlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setOpacity(opacitySlider.getValue());
			}
		});

		bottomBar.add(sizeSlider);
		bottomBar.add(opacitySlider);
		mainFrame.add(bottomBar, BorderLayout.SOUTH);
	}

	private void setupMenu() {
		file = new JMenu("File");
		edit = new JMenu("Edit");
		load = new JMenuItem("Load");
		save = new JMenuItem("Save");
		clear = new JMenuItem("Clear");
		undo = new JMenuItem("Undo");
		redo = new JMenuItem("Redo");
		importImage = new JMenuItem("Import Image");

		file.add(load);
		file.add(save);
		file.add(importImage);
		edit.add(undo);
		edit.add(redo);
		edit.add(clear);
		menu.add(file);
		menu.add(edit);
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.load(getFile(true));
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.save(getFile(false));
			}
		});
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.clearShapes();
			}
		});
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.undoShape();
			}
		});
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.redoShape();
			}
		});
		importImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setShape(ShapeToDraw.IMAGE);
				importImage();
			}
		});

		// add menubar to frame
		mainFrame.setJMenuBar(menu);
	}
	
	public File getFile(Boolean load){
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("./drawings"));
		File selectedFile = null;
		int result;
		
		if (load){
			result = fileChooser.showOpenDialog(drawPanel);			
		} else {
			result = fileChooser.showSaveDialog(drawPanel);
		}

		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		}
		return selectedFile;
	}

	@Override
	public void update(Observable o, Object arg) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				drawPanel.setShape((Shape)arg);										
				drawPanel.repaint();
			}
		});
	}
	
	public void importImage() {
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("./images"));

		int result = fileChooser.showOpenDialog(drawPanel);
		File selectedFile = null;

		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		}

		try {
			imageToImport = ImageIO.read(selectedFile);
		} catch (IOException e) {
			System.out.println(e);
		}

		drawPanel.repaint();

	}

	public Color colorPicker() {
		Color c = JColorChooser.showDialog(drawPanel, "Choose Background Color", drawPanel.getBackground());
		return c;
	}

	public static ShapeToDraw getShape() {
		return shape;
	}

	public void setShape(ShapeToDraw shape) {
		View.shape = shape;
	}

	public static String getTextToDraw() {
		return textToDraw;
	}

	public static void setTextToDraw(String textToDraw) {
		View.textToDraw = textToDraw;
	}

	public static Color getColor() {
		return color;
	}

	public static void setColor(Color color) {
		View.color = color;
	}

	public static boolean isFill() {
		return fill;
	}

	private static void setFill(boolean fill) {
		View.fill = fill;
	}

	public static int getThickness() {
		return thickness;
	}

	private static void setThickness(int thickness) {
		View.thickness = thickness;
	}

	private boolean isDrag() {
		return drag;
	}

	private void setDrag(boolean drag) {
		this.drag = drag;
	}

	private boolean isDraw() {
		return draw;
	}

	private void setDraw(boolean draw) {
		this.draw = draw;
	}

	public static int getOpacity() {
		return opacity;
	}

	private static void setOpacity(int opacity) {
		View.opacity = opacity;
	}

	public static BufferedImage getImageToImport() {
		return imageToImport;
	}

	private void setImageToImport(BufferedImage imageToImport) {
		this.imageToImport = imageToImport;
	}

}
