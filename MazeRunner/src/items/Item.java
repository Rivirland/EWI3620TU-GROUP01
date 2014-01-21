package items;

import javax.media.opengl.GL;

import engine.*;

//The abstract class all the items extend
public abstract class Item extends GameObject implements VisibleObject{
	public int mazeID;
	
	public Item(double x, double y, double z, int i) {
		super(x,y,z);
		mazeID = i;
	}
	
	public double getGlobalX(){
		return this.locationX;
	}
	public double getGlobalY(){
		return this.locationY;
	}
	public double getGlobalZ(){
		return this.locationZ;
	}
	public double getLocalX(){
		return this.locationX-MazeRunner.level.getMaze(mazeID).mazeX;
	}
	public double getLocalY(){
		return this.locationY-MazeRunner.level.getMaze(mazeID).mazeY;
	}
	public double getLocalZ(){
		return this.locationZ-MazeRunner.level.getMaze(mazeID).mazeZ;
	}
	public abstract void display(GL gl);
	public abstract boolean touches(GameObject object);
	
}
