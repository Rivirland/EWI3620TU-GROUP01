package items;

import javax.media.opengl.GL;

import engine.*;


public abstract class Item extends GameObject implements VisibleObject{
	public int mazeID;
	
	public Item(double x, double y, double z, int i) {
		super(x,y,z);
		mazeID = i;
	}
	
	public abstract double getGlobalX();
	public abstract double getGlobalY();
	public abstract double getGlobalZ();
	public abstract void display(GL gl);
	public abstract boolean touches(GameObject object);
	
}
