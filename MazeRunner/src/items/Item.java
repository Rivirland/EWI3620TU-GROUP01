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
	public static void drawCuboid(GL gl, double xmin, double xmax, double ymin,
			double ymax, double zmin, double zmax) {
//		 Floor plane
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(0, -1, 0);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(xmin, ymin, zmin);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(xmin, ymin, zmax);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(xmax, ymin, zmax);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(xmax, ymin, zmin);
//		 Top plane
		gl.glNormal3d(0, 1, 0);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(xmin, ymax, zmin);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(xmin, ymax, zmax);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(xmax, ymax, zmax);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(xmax, ymax, zmin);
		gl.glEnd();
		gl.glBegin(GL.GL_QUAD_STRIP);
//		 Back plane
		gl.glNormal3d(0, 0, -1);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(xmax, ymin, zmin);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(xmax, ymax, zmin);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(xmax, ymin, zmax);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(xmax, ymax, zmax);
//		 Right side plane
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(xmin, ymin, zmax);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(xmin, ymax, zmax);
//		 Front plane
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(xmin, ymin, zmin);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(xmin, ymax, zmin);
//		 Left plane
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(xmax, ymin, zmin);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(xmax, ymax, zmin);
		gl.glEnd();
	}
}
