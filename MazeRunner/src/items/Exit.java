package items;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import engine.GameObject;
import engine.MazeRunner;
import engine.Player;

public class Exit extends Item {

	public Exit(double x, double y, double z, int i) {
		super(x, y, z, i);
	}

	@Override
	public void display(GL gl) {
		gl.glPushMatrix();
		gl.glTranslated(super.locationX, super.locationY, super.locationZ);
		GLUT glut = new GLUT();
		//glut.glutSolidSphere(2, 10, 10);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 20);
		
		gl.glScaled(2, 2, 2);
		gl.glRotated(-90, 1, 0, 0);
		gl.glDisable(GL.GL_CULL_FACE);
		
		MazeRunner.copterModel.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();

	}

	@Override
	public double getGlobalX() {
		// TODO Auto-generated method stub
		return locationX;
	}

	@Override
	public double getGlobalY() {
		// TODO Auto-generated method stub
		return locationY;
	}

	@Override
	public double getGlobalZ() {
		// TODO Auto-generated method stub
		return locationZ;
	}

	@Override
	public boolean touches(GameObject object) {
		double diffX = object.getGlobalX() - this.getGlobalX();
		double diffY = object.getGlobalY() - this.getGlobalY();
		double diffZ = object.getGlobalZ() - this.getGlobalZ();

		if (object instanceof Player) {
			if (Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ) < 2) {
				return true;
			}
		}
		return false;
	}

}
