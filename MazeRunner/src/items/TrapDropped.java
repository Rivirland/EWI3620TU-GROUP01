package items;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import enemies.Enemy;
import engine.GameObject;
import engine.MazeRunner;
import engine.Player;

public class TrapDropped extends Item {

	public TrapDropped(double x, double y, double z, int i) {
		super(x, y, z, i);
	}

	@Override
	public void display(GL gl) {
		double sizeX = 0.5;
		double sizeY = sizeX;
		double sizeZ = sizeX;
		double xmin = 0;
		double xmax = sizeX;
		double ymin = 0;
		double ymax = sizeY;
		double zmin = 0;
		double zmax = sizeZ;
		// Setting the trapHolder color and material.
		

		// Apply texture.
		if (MazeRunner.trapHolderTexture != null) {
			MazeRunner.trapHolderTexture.enable();
			gl.glBindTexture(GL.GL_TEXTURE_2D, 4);
		}
		gl.glPushMatrix();
		gl.glTranslated(super.locationX, 0, super.locationZ);
		// drawCuboid
		drawCuboid(gl, xmin, xmax, ymin, ymax, zmin, zmax);
		gl.glPopMatrix();
		
	}

	@Override
	public boolean touches(GameObject object) {
		// System.out.println(player.getLocationX());
		// System.out.println(this.locationX +
		// MazeRunner.level.getMaze(mazeID).getMazeX());
		double diffX = object.getGlobalX() - this.getGlobalX();
		double diffY = object.getGlobalY() - this.getGlobalY();
		double diffZ = object.getGlobalZ() - this.getGlobalZ();
		if (Math.sqrt(diffX * diffX  + diffZ * diffZ) < 2 && diffY<10) {
			return true;
		}
		return false;
	}

	@Override
	public double getGlobalX() {
		// System.out.println(MazeRunner.level.getMaze(mazeID).getMazeX());
		return locationX + MazeRunner.level.getMaze(this.mazeID).getMazeX();
	}

	@Override
	public double getGlobalY() {
		return locationY + MazeRunner.level.getMaze(this.mazeID).getMazeY();
	}

	@Override
	public double getGlobalZ() {
		return locationZ + MazeRunner.level.getMaze(this.mazeID).getMazeZ();
	}

}
