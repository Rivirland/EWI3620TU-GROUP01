package items;

import javax.media.opengl.GL;

import menu.Teken;
import enemies.Enemy;
import engine.Animator;
import engine.GameObject;
import engine.MazeRunner;

public class TrapDropped extends Item {
	protected boolean used;
	private long timeUsed;
	private boolean legal;
	private boolean thrown;
	private long t0;
	public static double vy0=.11;
	public static double vx0=1.0;
	public static double ay=-0.1;
	public static double ax=0;
	public double horAngle;
	public boolean inair;
	public boolean onground;

	public TrapDropped(double x, double y, double z, int i) {
		super(x, y, z, i);
		this.legal = true;
		this.used = false;
		this.horAngle=MazeRunner.player.getHorAngle();
		this.inair=true; 
		this.onground=false;
//		System.out.println("TrapDropped@global: x: " + this.getGlobalX() + " y: " + this.getGlobalY() + " z: "+ this.getGlobalZ());
//		System.out.println("TrapDropped@local: x: " + this.getLocalX() + " y: " + this.getLocalY() + " z: "+ this.getLocalZ());
	}

	@Override
	public void display(GL gl) {
		if (this.inair){
			Animator.thrownTrapDropped(this);
		}
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
		
//		Maze curMaze = MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this));
		gl.glPushMatrix();
		gl.glTranslated(getGlobalX() - sizeX / 2, getGlobalY(), getGlobalZ() - sizeZ / 2);
		// drawCuboid
		Teken.drawCuboid(gl, xmin, xmax, ymin, ymax, zmin, zmax,28);
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

	public void setThrown(boolean b) {
		this.thrown=b;
		
	}
	public boolean getThrown(){
		return this.thrown;
	}

	public void setT0(long currentTime) {
		this.t0=currentTime;
		
	}
	public long getT0(){
		return this.t0;
	}
}
