package enemies;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import engine.*;

public abstract class Enemy extends GameObject implements VisibleObject {
	private EnemyControl enemyControl;
	public double speed;
	public double begX, begY, begZ;
	private double horAngle;
	protected boolean west = false;
	protected boolean east = false;
	protected boolean north = false;
	protected boolean south = false;
	private int randomizer;

	public Enemy(double x, double y, double z, double speed, double h) {
		super(x, y, z);
		this.begX = x;
		this.begY = y;
		this.begZ = z;
		this.horAngle = h;
		this.speed = speed;
	}

	public void reset(){
		setLocationX(begX);
		setLocationY(begY);
		setLocationZ(begZ);
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

	public abstract void update(int deltaTime, Player player);

	public void updateMovementPatrol() {
		east = false;
		west = false;
		north = false;
		south = false;
		if (randomizer == 1) {
			north = true;
			setHorAngle(0);
		} else if (randomizer == 2) {
			west = true;
			setHorAngle(90);
		} else if (randomizer == 3) {
			south = true;
			setHorAngle(180);
		} else {
			east = true;
			setHorAngle(-90);
		}
	}

	public void updateMovementFollow(Player player) {
		east = false;
		west = false;
		north = false;
		south = false;
		double xdiff = player.getLocationX() - getLocationX();
		double zdiff = player.getLocationZ() - getLocationZ();
		double diff = Math.sqrt(xdiff*xdiff+zdiff*zdiff);
		
		double alpha = Math.toDegrees(Math.acos(Math.abs(zdiff/diff)));
		if (xdiff<0){
			if (zdiff<0){
				
			}else if (zdiff>0){
				alpha=180-alpha;
			}
		} else if (xdiff>0){
			if (zdiff<0){
				alpha=-alpha;
			}else if (zdiff>0){
				alpha+=180;
			}
		}
		setHorAngle(alpha);

		
		if (Math.abs(xdiff) > 0.2) {
			if (xdiff > 0) {
				east = true;
				randomizer = 0;
			} else if (xdiff < 0) {
				west = true;
				randomizer = 2;
			}
		}
		if (Math.abs(zdiff) > 0.2) {
			if (zdiff > 0) {
				south = true;
				randomizer = 3;
			} else if (zdiff < 0) {
				north = true;
				randomizer = 1;
			}
		}
	}
	public void rotateEnemy(GL gl){
		gl.glRotated(horAngle, 0,1, 0);
	}
	@Override
	public void display(GL gl) {
		gl.glColor3d(0.0, 0.0, 1.0);
		gl.glPushMatrix();

		gl.glTranslated(getLocationX(), 2.5, getLocationZ());
		drawEnemy(gl);

		gl.glPopMatrix();
		gl.glFlush(); // Flush drawing routines
	}

	public void drawEnemy(GL gl) {
		GLUT glut = new GLUT();
		glut.glutSolidSphere(1, 10, 10);
	}
	
	@Override
	public double getGlobalX(){
		return locationX+MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this)).mazeX;
	}
	
	@Override
	public double getGlobalY(){
		return locationY+MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this)).mazeY;
	}
	
	@Override
	public double getGlobalZ(){
		return locationZ+MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this)).mazeZ;
	}

}
