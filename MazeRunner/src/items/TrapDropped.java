package items;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import enemy.Enemy;
import engine.GameObject;
import engine.MazeRunner;
import engine.Player;

public class TrapDropped extends Item {

	public TrapDropped(double x, double y, double z, int i) {
		super(x, y, z, i);
	}

	@Override
	public void display(GL gl) {

		GLUT glut = new GLUT();
		gl.glPushMatrix();
		gl.glTranslated(super.locationX, super.locationY, super.locationZ);
		glut.glutSolidCube(0.5f);
		gl.glPopMatrix();
	}

	@Override
	public boolean touches(GameObject object) {
		// System.out.println(player.getLocationX());
		// System.out.println(this.locationX +
		// MazeRunner.level.getMaze(mazeID).getMazeX());
		double diffX = object.getLocationX() - this.getGlobalX();
		double diffY = object.getLocationY() - this.getGlobalY();
		double diffZ = object.getLocationZ() - this.getGlobalZ();
		if (Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ) < 2) {
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
