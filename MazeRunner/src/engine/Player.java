package engine;

import javax.media.opengl.GLAutoDrawable;

/**
 * Player represents the actual player in MazeRunner.
 * <p>
 * This class extends GameObject to take advantage of the already implemented
 * location functionality. Furthermore, it also contains the orientation of the
 * Player, ie. where it is looking at and the player's speed.
 * <p>
 * For the player to move, a reference to a Control object can be set, which can
 * then be polled directly for the most recent input.
 * <p>
 * All these variables can be adjusted freely by MazeRunner. They could be
 * accessed by other classes if you pass a reference to them, but this should be
 * done with caution.
 * 
 * @author Bruno Scheele
 * 
 */
public class Player extends GameObject {
	private double horAngle, verAngle;
	private double speed;
	public double begX, begY, begZ, begV, begH;
	public static boolean canTeleport = true;
	public static boolean canMove = true;
	public static int nrOfTraps;
	public static int nrOfBullets;
	public static int playerStateInt;
	private Control control = null;
	public int score;

	/**
	 * The Player constructor.
	 * <p>
	 * This is the constructor that should be used when creating a Player. It
	 * sets the starting location and orientation.
	 * <p>
	 * Note that the starting location should be somewhere within the maze of
	 * MazeRunner, though this is not enforced by any means.
	 * 
	 * @param x
	 *            the x-coordinate of the location
	 * @param y
	 *            the y-coordinate of the location
	 * @param z
	 *            the z-coordinate of the location
	 * @param h
	 *            the horizontal angle of the orientation in degrees
	 * @param v
	 *            the vertical angle of the orientation in degrees
	 */
	public Player(double x, double y, double z, double h, double v) {
		// Set the initial position and viewing direction of the player.
		super(x, y, z);
		begX = x;
		begY = y;
		begZ = z;
		begH = h;
		begV = v;
		horAngle = h;
		verAngle = v;
		speed = .01;
		nrOfTraps = 1000;
		nrOfBullets = 200;
		playerStateInt = 0;
		score = 0;
	}

	/**
	 * Sets the Control object that will control the player's motion
	 * <p>
	 * The control must be set if the object should be moved.
	 * 
	 * @param input
	 */
	public void setControl(Control control) {
		this.control = control;
	}

	/**
	 * Gets the Control object currently controlling the player
	 * 
	 * @return
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * Returns the horizontal angle of the orientation.
	 * 
	 * @return the horAngle
	 */
	public double getHorAngle() {
		return horAngle;
	}

	/**
	 * Sets the horizontal angle of the orientation.
	 * 
	 * @param horAngle
	 *            the horAngle to set
	 */
	public void setHorAngle(double horAngle) {
		this.horAngle = horAngle;
	}

	/**
	 * Returns the vertical angle of the orientation.
	 * 
	 * @return the verAngle
	 */
	public double getVerAngle() {
		return verAngle;
	}

	/**
	 * Sets the vertical angle of the orientation.
	 * 
	 * @param verAngle
	 *            the verAngle to set
	 */
	public void setVerAngle(double verAngle) {
		this.verAngle = verAngle;
	}

	/**
	 * Returns the speed.
	 * 
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * Updates the physical location and orientation of the player
	 * 
	 * @param deltaTime
	 *            The time in milliseconds since the last update.
	 */
	public void update(int deltaTime, GLAutoDrawable drawable) {
		minimapUpdate();
		if (canMove) {
			playerStateUpdate();
			double previousX = this.getLocationX();
			double previousY = this.getLocationY();
			double previousZ = this.getLocationZ();

			if (control != null) {
				control.update(drawable);

				double i = -1;
				horAngle = horAngle % 360;
				this.horAngle = this.getHorAngle() - i * control.getdX();
				this.verAngle = this.getVerAngle() - i * control.getdY();
				// make sure the camera doesn't turn

				// System.out.println(control.forward);
				control.setdX(0);
				control.setdY(0);
				if (this.getVerAngle() > 89) {
					this.verAngle = 89;
				} else if (this.getVerAngle() < -89) {
					this.verAngle = -89;
				}

				if (control.forward) {
					locationX -= Math.sin(Math.toRadians(getHorAngle()))
							* speed * deltaTime;
					locationZ -= Math.cos(Math.toRadians(getHorAngle()))
							* speed * deltaTime;
					// System.out.println(locationX+"  "+locationZ);
				}
				if (control.back) {
					locationX -= Math.sin(Math.toRadians(getHorAngle() + 180))
							* speed * deltaTime;
					locationZ -= Math.cos(Math.toRadians(getHorAngle() + 180))
							* speed * deltaTime;
				}
				if (control.left) {
					locationX -= Math.sin(Math.toRadians(getHorAngle() + 90))
							* speed * deltaTime;
					locationZ -= Math.cos(Math.toRadians(getHorAngle() + 90))
							* speed * deltaTime;
				}
				if (control.right) {
					locationX -= Math.sin(Math.toRadians(getHorAngle() + 270))
							* speed * deltaTime;
					locationZ -= Math.cos(Math.toRadians(getHorAngle() + 270))
							* speed * deltaTime;
				}
				if (control.up) {
					locationY += speed * deltaTime;
				}
				if (control.down) {
					locationY -= speed * deltaTime;
					if (locationY < 2.5) {
						locationY = 2.5;
					}
				}
				boolean[] playerCollide = MazeRunner.level.collides(this, 0.2);
				if (playerCollide[0] || playerCollide[2]) {
					this.setLocationX(previousX);
				}
				if (playerCollide[1] || playerCollide[3]) {
					this.setLocationZ(previousZ);
				}
			}

			if (control.itemUse) {
				PlayerState.getState(playerStateInt).itemUse();
				control.itemUse = false;
			}
			
			if(control.gunShoot && playerStateInt==2){
				System.out.println(playerStateInt);
				PlayerStateGun.shootGun();
				control.gunShoot=false;
			}
			
		/*}else{
			if (control.itemUse) {
				PlayerState.getState(playerStateInt).itemUse();
				control.itemUse = false;}*/
			
		}
	}

	public void minimapUpdate() {
		if (control.toggleMinimap) {
			control.minimap = !control.minimap;
			control.toggleMinimap = false;
			System.out.println("minimap:  " + control.minimap);
		}

		int mazeID = MazeRunner.level.getCurrentMaze(this);
		if (mazeID != -1) {
			Maze curMaze = MazeRunner.level.getMaze(mazeID);
			int playerXM = curMaze.coordToMatrixElement(getLocalX());
			int playerZM = curMaze.coordToMatrixElement(getLocalZ());
			if (curMaze.visitedMatrix[playerXM][playerZM] != 1) {
				curMaze.visitedMatrix[playerXM-1][playerZM+1] = 1;
				curMaze.visitedMatrix[playerXM][playerZM+1] = 1;
				curMaze.visitedMatrix[playerXM+1][playerZM+1] = 1;
				curMaze.visitedMatrix[playerXM-1][playerZM] = 1;
				curMaze.visitedMatrix[playerXM][playerZM] = 1;
				curMaze.visitedMatrix[playerXM+1][playerZM] = 1;
				curMaze.visitedMatrix[playerXM-1][playerZM-1] = 1;
				curMaze.visitedMatrix[playerXM][playerZM-1] = 1;
				curMaze.visitedMatrix[playerXM+1][playerZM-1] = 1;
			}
		}
	}

	public void playerStateUpdate() {
		if (control.playerStateUp) {
			control.playerStateUp = false;
			PlayerState.getState(playerStateInt).leaving();
			playerStateInt++;
			playerStateInt = playerStateInt % 3;
			PlayerState.getState(playerStateInt).entering();

		}
		if (control.playerStateDown) {
			control.playerStateDown = false;
			PlayerState.getState(playerStateInt).leaving();
			playerStateInt--;
			playerStateInt = (playerStateInt + 3) % 3;
			PlayerState.getState(playerStateInt).entering();
		}

	}

	public void reset() {
		setLocationX(begX);
		setLocationY(begY);
		setLocationZ(begZ);
		setHorAngle(begH);
		setVerAngle(begV);
	}

	public void noMousechange() {
		control.setdX(0);
		control.setdY(0);

	}
	public double getLocalX(){
		return this.locationX-MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this)).mazeX;
	}
	public double getLocalY(){
		return this.locationY-MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this)).mazeY;
	}
	public double getLocalZ(){
		return this.locationZ-MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this)).mazeZ;
	}

}
