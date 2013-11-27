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

	public void update(int deltaTime, Player player) {
		if (MazeRunner.level.inSameMaze(this, player) != -1) {

			int currentMazeID = MazeRunner.level.getCurrentMaze(this);
			Maze currentMaze = MazeRunner.level.getMaze(currentMazeID);

			int enemyMatrixX = currentMaze.coordToMatrixElement(locationX);
			int enemyMatrixZ = currentMaze.coordToMatrixElement(locationZ);

			double playerX = player.getLocationX();
			double playerZ = player.getLocationZ();

			int playerMatrixX = currentMaze.coordToMatrixElement(playerX);
			int playerMatrixZ = currentMaze.coordToMatrixElement(playerZ);

			if (enemyMatrixX != playerMatrixX && enemyMatrixZ != playerMatrixZ) {
				this.updateMovementPatrol();
			}
			if (enemyMatrixX == playerMatrixX && enemyMatrixZ == playerMatrixZ) {
				if (locationX > playerX) {
					this.locationX -= this.speed * deltaTime;
				}
				if (locationX < playerX) {
					this.locationX += this.speed * deltaTime;
				}
				if (locationZ > playerZ) {
					this.locationZ -= this.speed * deltaTime;
				}
				if (locationZ < playerZ) {
					this.locationZ += this.speed * deltaTime;
				}
				if (Math.sqrt(Math.pow(locationZ - playerZ, 2)
						+ Math.pow(locationX - playerX, 2)) < 1) {
					System.out.println("dood!");
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					player.setLocationX(player.begX);
					player.setLocationY(player.begY);
					player.setLocationZ(player.begZ);
					for (int resetEnemy = 0; resetEnemy < MazeRunner.enemyListLength; resetEnemy++) {
						Enemy resEnemy = MazeRunner.enemyList.get(resetEnemy);
						resEnemy.setX(resEnemy.begX);
						resEnemy.setZ(resEnemy.begZ);
					}
				}
			}
			if (enemyMatrixX == playerMatrixX && enemyMatrixZ != playerMatrixZ) {
				int diffZ = enemyMatrixZ - playerMatrixZ;
				if (diffZ > 0) {
					boolean wallDetected = false;
					for (int i = enemyMatrixZ; i > playerMatrixZ; i--) {
						if (currentMaze.getCoords(enemyMatrixX, i) != 0
								&& !(enemyMatrixX % 2 == 1 && i % 2 == 1)) {
							wallDetected = true;
						}
					}
					if (wallDetected) {
						this.updateMovementPatrol();
					} else {
						this.updateMovementFollow(player);
					}
				} else {
					boolean wallDetected = false;
					for (int i = enemyMatrixZ; i < playerMatrixZ; i++) {
						if (currentMaze.getCoords(enemyMatrixX, i) != 0
								&& !(enemyMatrixX % 2 == 1 && i % 2 == 1)) {
							wallDetected = true;
						}
					}
					if (wallDetected) {
						this.updateMovementPatrol();
					} else {
						this.updateMovementFollow(player);
					}
				}
			}
			if (enemyMatrixX != playerMatrixX && enemyMatrixZ == playerMatrixZ) {
				int diffX = enemyMatrixX - playerMatrixX;
				if (diffX > 0) {
					boolean wallDetected = false;
					for (int i = enemyMatrixX; i > playerMatrixX; i--) {
						if (currentMaze.getCoords(i, enemyMatrixZ) != 0
								&& !(enemyMatrixZ % 2 == 1 && i % 2 == 1)) {
							wallDetected = true;
						}
					}
					if (wallDetected) {
						this.updateMovementPatrol();
					} else {
						this.updateMovementFollow(player);
					}
				} else {
					boolean wallDetected = false;
					for (int i = enemyMatrixX; i < playerMatrixX; i++) {
						if (currentMaze.getCoords(i, enemyMatrixZ) != 0
								&& !(enemyMatrixZ % 2 == 1 && i % 2 == 1)) {
							wallDetected = true;
						}
					}
					if (wallDetected) {
						this.updateMovementPatrol();
					} else {
						this.updateMovementFollow(player);
					}
				}

			}

			boolean[] enemyCollide = MazeRunner.level.collides(this, 1);
			if (enemyCollide[0]) {
				this.setX(locationX);
				this.setRandomizer((int) (1 + 3 * Math.random()));
			}
			if (enemyCollide[1]) {
				this.setZ(locationZ);
				int randomNumber = (int) (3 * Math.random());
				if (randomNumber == 0) {
					this.setRandomizer(0);
				} else if (randomNumber == 1) {
					this.setRandomizer(2);
				} else {
					this.setRandomizer(3);
				}
			}
			if (enemyCollide[2]) {
				this.setX(locationX);
				int randomNumber = (int) (3 * Math.random());
				if (randomNumber == 0) {
					this.setRandomizer(0);
				} else if (randomNumber == 1) {
					this.setRandomizer(1);
				} else {
					this.setRandomizer(3);
				}
			}
			if (enemyCollide[3]) {
				this.setZ(locationZ);
				this.setRandomizer((int) (3 * Math.random()));
			}

		} else {
			for (int e = 0; e < MazeRunner.enemyListLength; e++) {
				Enemy enemy = MazeRunner.enemyList.get(e);
				double locationX = this.getX();
				double locationZ = this.getZ();
				this.updateMovementPatrol();

				boolean[] enemyCollide = MazeRunner.level.collides(enemy, 1);
				if (enemyCollide[0]) {
					this.setX(locationX);
					this.setRandomizer((int) (1 + 3 * Math.random()));
				}
				if (enemyCollide[1]) {
					this.setZ(locationZ);
					int randomNumber = (int) (3 * Math.random());
					if (randomNumber == 0) {
						this.setRandomizer(0);
					} else if (randomNumber == 1) {
						this.setRandomizer(2);
					} else {
						this.setRandomizer(3);
					}
				}
				if (enemyCollide[2]) {
					this.setX(locationX);
					int randomNumber = (int) (3 * Math.random());
					if (randomNumber == 0) {
						this.setRandomizer(0);
					} else if (randomNumber == 1) {
						this.setRandomizer(1);
					} else {
						this.setRandomizer(3);
					}
				}
				if (enemyCollide[3]) {
					this.setZ(locationZ);
					this.setRandomizer((int) (3 * Math.random()));
				}
			}

		}
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
		// System.out.print(" " + east + " " + west + " " + north + " " + south
		// + " ");
		// System.out.println(xdiff + " " + zdiff);

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

	public void drawEnemy(GL gl) {
		GLUT glut = new GLUT();
		glut.glutSolidSphere(1, 10, 10);
	}

}
