package items;

import javax.media.opengl.GL;

import enemies.Enemy;
import engine.GameObject;
import engine.MazeRunner;

public class TrapDropped extends Item {
	protected boolean used;
	private long timeUsed;
	private boolean legal;

	public TrapDropped(double x, double y, double z, int i) {
		super(x, y, z, i);
		this.legal = true;
		this.used = false;
	}

	@Override
	public void display(GL gl) {
		if (this.used) {
			if (this.timeUsed + TrapDroppedGBS.animationTime5 < MazeRunner.currentTime) {
				this.setLegal(false);
			}
		}
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
		gl.glTranslated(super.locationX - sizeX / 2, super.locationY, super.locationZ - sizeZ / 2);
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

		if (object instanceof Enemy) {
			// System.out.println("enemy: " + object.getGlobalX());
			// System.out.println("trap: " + this.getGlobalX());

		}
		if (Math.sqrt(diffX * diffX + diffZ * diffZ) < 1 && diffY < 10) {
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

	public void setUsed(boolean b) {
		this.used = b;
	}

	public boolean getUsed() {
		return this.used;
	}

	public void setTimeUsed(long currentTime) {
		this.timeUsed = currentTime;
	}

	public long getTimeUsed() {
		return this.timeUsed;
	}

	public boolean getLegal() {
		return legal;
	}

	public void setLegal(boolean legal) {
		this.legal = legal;
	}

}
