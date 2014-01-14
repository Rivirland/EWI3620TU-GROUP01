package items;

import javax.media.opengl.GL;

import menu.Teken;
import engine.GameObject;
import engine.MazeRunner;
import engine.Player;

public class TrapHolder extends Item {

	public TrapHolder(double x, double y, double z, int i) {
		super(x, y, z, i);
	}

	@Override
	public void display(GL gl) {
		double sizeX = 1;
		double sizeY = sizeX;
		double sizeZ = sizeX;
		double xmin = 0;
		double xmax = sizeX;
		double ymin = 0;
		double ymax = sizeY;
		double zmin = 0;
		double zmax = sizeZ;
		gl.glPushMatrix();
		gl.glTranslated(super.locationX, super.locationY, super.locationZ);
		// drawCuboid
		Teken.drawCuboid(gl, xmin, xmax, ymin, ymax, zmin, zmax, 14);
		gl.glPopMatrix();

	}

	@Override
	public boolean touches(GameObject object) {
		double diffX = object.getGlobalX() - this.getGlobalX();
		double diffY = object.getGlobalY() - this.getGlobalY();
		double diffZ = object.getGlobalZ() - this.getGlobalZ();

		if (Math.sqrt(diffX * diffX + diffZ * diffZ) < 2 && diffY<5) {
			if (object instanceof Player) {
				return true;
			} else {
				// do nothing
			}
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
