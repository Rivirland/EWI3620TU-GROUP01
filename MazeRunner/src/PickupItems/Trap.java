package PickupItems;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import engine.*;

public class Trap extends Item {

	public Trap(double x, double y, double z) {
		super(x, y, z);
	}

	@Override
	public void display(GL gl) {

		GLUT glut = new GLUT();
		gl.glPushMatrix();
		gl.glTranslated(super.locationX, super.locationY, super.locationZ);
		glut.glutSolidCube(2);
		gl.glPopMatrix();
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean collides(Player player) {
		int mazeID = MazeRunner.level.getCurrentMaze(this);
		System.out.println(mazeID);
		System.out.print("x=" + this.locationX);
		System.out.print(" y= "+ this.locationY);
		System.out.println(" z= "+ this.locationZ);
		double diffX = player.getLocationX()
				- (this.locationX + MazeRunner.level.getMaze(mazeID).getMazeX());
		double diffY = player.getLocationY()
				- (this.locationY + MazeRunner.level.getMaze(mazeID).getMazeY());
		double diffZ = player.getLocationZ()
				- (this.locationZ + MazeRunner.level.getMaze(mazeID).getMazeZ());
		if (Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ) < 2) {
			return true;
		}
		return false;
	}

}
