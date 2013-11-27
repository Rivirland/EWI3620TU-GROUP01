package enemy;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import engine.*;

public class Enemy extends GameObject implements VisibleObject {
	private EnemyControl enemyControl;
	public double speed;
	public double begX, begY, begZ;
	private double horAngle;
	private boolean west = false;
	private boolean east = false;
	private boolean north = false;
	private boolean south = false;
	private int randomizer;

	public Enemy(double x, double y, double z, double speed, double h) {
		super(x, y, z);
		this.begX = x;
		this.begY = y;
		this.begZ = z;
		this.horAngle = h;
		this.speed = speed;
	}

	public void setControl(EnemyControl enemyControl) {
		this.enemyControl = enemyControl;
	}

	public EnemyControl getControl() {
		return this.enemyControl;
	}

	public void setRandomizer(int r) {
		this.randomizer = r;
	}

	public int getRandomizer() {
		return this.randomizer;
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
	
		
//		if (patrol) {
//			updateMovementPatrol(player);
//		}
//		
//		if (!patrol) {
//			updateMovementFollow(player);
//		}
		// Player player = new_default.MazeRunner.getPlayer();
		if (west) {
			locationX -= speed * deltaTime;
		}
		if (east) {
			locationX += speed * deltaTime;
		}
		if (north) {
			locationZ -= speed * deltaTime;
		}
		if (south) {
			locationZ += speed * deltaTime;
		}
		// System.out.print(getX());
		// System.out.println(" " + getZ());
	}

	public void updateMovementPatrol() {
		east = false;
		west = false;
		north = false;
		south = false;
		if (randomizer == 1) {
			north = true;
		} else if (randomizer == 2) {
			west = true;
		} else if (randomizer == 3) {
			south = true;
		} else {
			east = true;
		}
	}

	public void updateMovementFollow(Player player) {
		east = false;
		west = false;
		north = false;
		south = false;
		double xdiff = player.getLocationX() - getX();
		double zdiff = player.getLocationZ() - getZ();
		// System.out.println(player.getLocationX() + " " +
		// player.getLocationZ());
		// System.out.print(" " + getX());
		// System.out.println(" " + getZ());

		if (Math.abs(xdiff) > 0.2) {
			if (xdiff > 0) {
				east = true;
				randomizer = 0;
			} else if (xdiff < 0) {
				west = true;
				randomizer = 2;
			}
		}if (Math.abs(zdiff) > 0.2) {
			if (zdiff > 0) {
				south = true;
				randomizer = 3;
			} else if (zdiff < 0) {
				north = true;
				randomizer = 1;
			}
		}
//		System.out.print(" " + east + " " + west + " " + north + " " + south
//				+ " ");
//		System.out.println(xdiff + " " + zdiff);

		double angle = Math.tan(xdiff / zdiff);
		setHorAngle(angle);
	}

	@Override
	public void display(GL gl) {
		gl.glColor3d(0.0, 0.0, 1.0);
		gl.glPushMatrix();

		gl.glTranslated(getX(), 2.5, getZ());
		drawEnemy(gl);

		gl.glPopMatrix();
		gl.glFlush(); // Flush drawing routines
	}
	
	public void drawEnemy(GL gl){
		GLUT glut = new GLUT();
		glut.glutSolidSphere(1,10,10);
	}

}
