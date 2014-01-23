package items;

import javax.media.opengl.GL;

import menu.Teken;

import com.sun.opengl.util.GLUT;

import engine.ChangeGL;
import engine.GameObject;
import engine.MazeRunner;
import engine.Player;

public class Start extends Item {

	public Start(double x, double y, double z, int i) {
		super(x, y, z, i);
	}

	@Override
	public void display(GL gl) {
		gl.glPushMatrix();
		gl.glTranslated(super.locationX, super.locationY, super.locationZ);
		
		gl.glDisable(GL.GL_CULL_FACE);

		ChangeGL.GLtoColoredItem(gl);
		Teken.drawCuboid(gl, -2, 2, 0, 0.3, -2, 2);
		
		ChangeGL.GLtoTexturedItem(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();

	}

	@Override
	public double getGlobalX() {
		return locationX;
	}

	@Override
	public double getGlobalY() {
		return locationY;
	}

	@Override
	public double getGlobalZ() {
		return locationZ;
	}

	@Override
	public boolean touches(GameObject object) {
		double diffX = object.getGlobalX() - this.getGlobalX();
		double diffY = object.getGlobalY() - this.getGlobalY();
		double diffZ = object.getGlobalZ() - this.getGlobalZ();

		if (object instanceof Player) {
			if (Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ) < 3) {
				return true;
			}
		}
		return false;
	}
	
	public static void drawExit(GL gl){
		gl.glPushMatrix();
		gl.glScaled(0.5,0.5,0.5);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 20);
		MazeRunner.copterModel.display(gl);
		gl.glPopMatrix();
	}
}
