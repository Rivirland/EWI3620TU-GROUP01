package items;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class TrapDroppedGBS extends TrapDropped{

	public TrapDroppedGBS(double x, double y, double z, int i) {
		super(x, y, z, i);

	}
	public void display(GL gl){
		GLUT glut = new GLUT();
		gl.glPushMatrix();
		gl.glRotated(90, 1, 0,0);
		gl.glTranslated(super.locationX, super.locationY, super.locationZ);
		glut.glutWireCone(2, 5, 15, 15);
		gl.glPopMatrix();
	}
}
