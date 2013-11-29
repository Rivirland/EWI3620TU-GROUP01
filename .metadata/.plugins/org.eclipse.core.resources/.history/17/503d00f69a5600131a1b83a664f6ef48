package PickupItems;

import javax.media.opengl.GL;

import engine.*;


public abstract class Item extends GameObject implements VisibleObject{

	public Item(double x, double y, double z) {
		super(x,y,z);
	}
	public abstract void display(GL gl);
	public abstract double getX();
	public abstract double getY();
	public abstract double getZ();
	public abstract boolean collides(Player player);
	
}
