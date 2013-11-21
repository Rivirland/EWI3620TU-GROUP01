package enemy;

import javax.media.opengl.GL;

import new_default.*;

import com.sun.opengl.util.GLUT;

public class Enemy extends GameObject implements VisibleObject {
	private EnemyControl enemyControl;
	private double speed;
	private double horAngle;
	private boolean left = false;
	private boolean right = false;
	private boolean forward = false;
	private boolean back = false;

	public Enemy(double x, double y, double z, double speed, double h) {
		super(x, y, z);
		horAngle = h;
		this.speed = speed;
	}

	public void setControl(EnemyControl enemyControl) {
		this.enemyControl = enemyControl;
	}

	public EnemyControl getControl() {
		return this.enemyControl;
	}

	public void setHorAngle(double horangle) {
		this.horAngle = horangle;
	}

	public double getHorAngle() {
		return this.horAngle;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return this.speed;
	}

	public double getX() {
		return super.locationX;
	}

	public void setX(double x) {
		super.locationX = x;
	}

	public double getZ() {
		return super.locationZ;
	}

	public void setZ(double z) {
		super.locationZ = z;
	}

	public void update(int deltaTime) {
		updateMovement();
		Player player = new_default.MazeRunner.getPlayer();
		if (left) {
			locationX -= Math.sin(Math.toRadians(getHorAngle() + 90)) * speed
					* deltaTime;
			locationZ -= Math.cos(Math.toRadians(getHorAngle() + 90)) * speed
					* deltaTime;
		}
		if (right) {
			locationX -= Math.sin(Math.toRadians(getHorAngle() + 270)) * speed
					* deltaTime;
			locationZ -= Math.cos(Math.toRadians(getHorAngle() + 270)) * speed
					* deltaTime;
		}
		if (forward) {
			locationX -= Math.sin(Math.toRadians(getHorAngle())) * speed
					* deltaTime;
			locationZ -= Math.cos(Math.toRadians(getHorAngle())) * speed
					* deltaTime;
		}
		if (back) {
			locationX -= Math.sin(Math.toRadians(getHorAngle() + 180)) * speed
					* deltaTime;
			locationZ -= Math.cos(Math.toRadians(getHorAngle() + 180)) * speed
					* deltaTime;
		}
		// System.out.print(getX());
		// System.out.println(" " + getZ());
	}

	public void updateMovement() {
		right = false;
		left = false;
		forward = false;
		back = false;
		Player player = new_default.MazeRunner.getPlayer();
		double xdiff = player.getLocationX() - getX();
		double zdiff = player.getLocationZ() - getZ();
		// System.out.println(player.getLocationX() + " " +
		// player.getLocationZ());
		// System.out.print(" " + getX());
		// System.out.println(" " + getZ());

		if (Math.abs(xdiff) > 0.2) {
			if (xdiff > 0) {
				right = true;
			} else if (xdiff < 0) {
				left = true;
			}
		}
		if (Math.abs(zdiff) > 0.2) {
			if (zdiff > 0) {
				back = true;
			} else if (zdiff < 0) {
				forward = true;
			}
		}
		System.out.print(" " + right + " " + left + " " + forward + " " + back
				+ " ");
		System.out.println(xdiff + " " + zdiff);

		double angle = Math.tan(xdiff / zdiff);
		setHorAngle(angle);

		// else{
		// right=false;
		// left=false;
		// forward=false;
		// back=false;
		// }
	}

	// public boolean collides(Level level) {
	// double margin = 0.2;
	// for (int i = 0; i<level.getAantal(); i++){
	// Maze maze = level.getMaze(i);
	// if(locationX > maze.getMinX() && locationX < maze.getMaxX() && locationZ
	// > maze.getMinZ() && locationZ < maze.getMaxZ() && locationY >=
	// maze.getMazeY() && locationY <= maze.getMazeY() + 5){
	// //Let op dat je dus als je teleporteert naar maximaal mazeY + 5 gaat!
	// double x = locationX - maze.getMinX();
	// double z = locationZ - maze.getMinZ();
	// int newX1 = maze.coordToMatrixElement(x+margin);
	// int newZ1 = maze.coordToMatrixElement(z);
	// int newX2 = maze.coordToMatrixElement(x-margin);
	// int newZ2 = maze.coordToMatrixElement(z);
	// int newX3 = maze.coordToMatrixElement(x);
	// int newZ3 = maze.coordToMatrixElement(z+margin);
	// int newX4 = maze.coordToMatrixElement(x);
	// int newZ4 = maze.coordToMatrixElement(z-margin);
	// if((!(newX1%2==1 && newZ1%2==1) && (maze.getCoords(newX1,newZ1)!=0)) ||
	// (!(newX2%2==1 && newZ2%2==1) && (maze.getCoords(newX2,newZ2)!=0)) ||
	// (!(newX3%2==1 && newZ3%2==1) && (maze.getCoords(newX3,newZ3)!=0)) ||
	// (!(newX4%2==1 && newZ4%2==1) && (maze.getCoords(newX4,newZ4)!=0))){
	// return true;
	// }
	// }
	// }
	// return false;
	// }
	@Override
	public void display(GL gl) {

		GLUT glut = new GLUT();
		gl.glColor3d(0.0, 0.0, 1.0);
		gl.glPushMatrix();

		gl.glTranslated(getX(), 2.5, getZ());
		glut.glutSolidSphere(1, 30, 30);

		gl.glPopMatrix();
		gl.glFlush(); // Flush drawing routines

	}

}
