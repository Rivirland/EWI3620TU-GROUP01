package enemies;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import engine.Maze;
import engine.MazeRunner;
import engine.Player;
import engine.VisibleObject;

public class EnemySmart extends Enemy implements VisibleObject {

	public EnemySmart(double x, double y, double z, double speed, double h) {
		super(x, y, z, speed, h);
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
					Player.playerStateInt = 3;
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
				this.setLocationX(locationX);
				this.setRandomizer((int) (1 + 3 * Math.random()));
			}
			if (enemyCollide[1]) {
				this.setLocationZ(locationZ);
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
				this.setLocationX(locationX);
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
				this.setLocationZ(locationZ);
				this.setRandomizer((int) (3 * Math.random()));
			}

		} else {

				this.updateMovementPatrol();

				boolean[] enemyCollide = MazeRunner.level.collides(this, 1);
				if (enemyCollide[0]) {
					this.setLocationX(locationX);
					this.setRandomizer((int) (1 + 3 * Math.random()));
				}
				if (enemyCollide[1]) {
					this.setLocationZ(locationZ);
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
					this.setLocationX(locationX);
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
					this.setLocationZ(locationZ);
					this.setRandomizer((int) (3 * Math.random()));
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

}