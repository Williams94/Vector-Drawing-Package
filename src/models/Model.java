package models;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;

import shapes.Shape;

public class Model extends Observable{
    
    private ArrayList<Shape> shapes;
    private ArrayList<Shape> removedShapes;
    
    

    public Model(){
        shapes = new ArrayList<Shape>();
        removedShapes = new ArrayList<Shape>();
    }
    
    public void addShape(Shape shape){
        shapes.add(shape);
        update();
    }
    
    public void clearShapes(){
    	shapes.clear();
    	update();
    }
    
    public void undoShape(){
    	if (!shapes.isEmpty()){
    		Shape shapeToRemove = shapes.get(shapes.size() - 1);
    		shapes.remove(shapeToRemove);
    		removedShapes.add(shapeToRemove);
    		update();    		
    	}
    }
    
    public void redoShape(){
    	if(!removedShapes.isEmpty()){
    		Shape shapeToRedraw = removedShapes.get(removedShapes.size() -1);
    		removedShapes.remove(shapeToRedraw);
    		shapes.add(shapeToRedraw);
    		update();
    	}
    }
    
    public void updateShapeStart(Shape shape){
    	for (Shape s : shapes){
    		if (shape.equals(s)){
    			s.setStart(shape.getStart());
    			update();
    		}
    	}
    }
    
    public void updateShapeEnd(Shape shape){
    	for (Shape s : shapes){
    		if (shape.equals(s)){
    			s.setEnd(shape.getEnd());
    			update();
    		}
    	}
    }
    
    public void removeShape(Shape shape){
    	for (Shape s : shapes){
    		if (s.equals(shape)){
    			shapes.remove(s);
    			update();
    		}
    	}
    }

    public void update(){
    	setChanged();
    	notifyObservers();
    }
    
    public void update(Shape shape){
    	setChanged();
    	notifyObservers(shape);
    }
    
    public ArrayList<Shape> getShapes(){
    	return this.shapes;
    }
    
    public void save(File fileToSave) {
			try {
				FileOutputStream fos = new FileOutputStream(fileToSave);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				
				for (Shape shape : getShapes()) {
					oos.writeObject(shape);
				}
				oos.close();
				fos.close();

			} catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e){
				System.out.println(e);
			}

	}

	public void load(File selectedFile) {
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(selectedFile);
			ois = new ObjectInputStream(fis);
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		} catch (IOException e) {
			System.out.println(e);
		}

			try {
				
				while(true){
					addShape((Shape) ois.readObject());
				}

			} catch (IOException e) {
				System.out.println(e);
			} catch (ClassNotFoundException e){
				System.out.println(e);
			} finally{
				try {
					if (ois != null){
						ois.close();						
					}
					if(fis != null){
						fis.close();						
					}
				} catch (IOException e) {
					System.out.println(e);
				}
			}

		update();
	}
    
}
