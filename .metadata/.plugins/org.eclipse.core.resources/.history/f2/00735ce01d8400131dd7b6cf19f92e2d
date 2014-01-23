package enemies;

import javax.media.opengl.GL;

import playerStates.PlayerState;

import engine.Maze;
import engine.MazeRunner;
import engine.Player;
import engine.VisibleObject;
//This class is responsible for the spooky enemies. They will run towards you if you are in the same maze as them and they can walk through walls! They can only be killed by traps, but move more slowly than smart enemies.

public class EnemySpooky extends Enemy implements VisibleObject {

	public EnemySpooky(double x, double y, double z, double speed, double h) {
		super(x, y, z, speed, h);
	}

	public void update(int deltaTime, Player player) {
		alert = false;
		if (MazeRunner.level.inSameMaze(this, player) != -1 && !player.invisible) {
			alert = true;
			if (MazeRunner.level.inSameMaze(this, player) != -1 && player.canMove) {

				int currentMazeID = MazeRunner.level.getCurrentMaze(this);
				Maze currentMaze = MazeRunner.level.getMaze(currentMazeID);

				int enemyMatrixX = currentMaze.coordToMatrixElement(locationX);
				int enemyMatrixZ = currentMaze.coordToMatrixElement(locationZ);

				double playerX = player.getLocationX();
				double playerZ = player.getLocationZ();

				int playerMatrixX = currentMaze.coordToMatrixElement(playerX);
				int playerMatrixZ = currentMaze.coordToMatrixElement(playerZ);
				if (MazeRunner.player.invisible) {
					this.updateMovementPatrol();
				} else {
					this.updateMovementFollow(player);
					if (enemyMatrixX == playerMatrixX && enemyMatrixZ == playerMatrixZ) {
						// Enemy speeds up if close to player.
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
					}
				}
				// The enemy is very close! Kill the player if he hasn't
				// finished yet.
				if (Math.sqrt(Math.pow(locationZ - playerZ, 2) + Math.pow(locationX - playerX, 2)) < 1 && player.playerStateInt != 4 && player.playerStateInt != 5) {
					PlayerState.getState(MazeRunner.player.playerStateInt).leaving();
					MazeRunner.player.playerStateInt = 3;
					PlayerState.getState(MazeRunner.player.playerStateInt).entering();
				}

			} else {
				this.updateMovementPatrol();
			}

			Maze currentMaze = MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this));

			// This makes sure the enemy stays in the maze. Also makes sure that
			// if he walks north f.e. and that if the maze stops there, he will
			// run into one of the other three directions.
			if (locationX > currentMaze.maxX - 1) {
				east = false;
				this.setDirection((int) (1 + 3 * Math.random()));
			}
			if (locationX < currentMaze.minX + 1) {
				west = false;
				int randomNumber = (int) (3 * Math.random());
				if (randomNumber == 0) {
					this.setDirection(0);
				} else if (randomNumber == 1) {
					this.setDirection(1);
				} else {
					this.setDirection(3);
				}
			}
			if (locationZ < currentMaze.minZ + 1) {
				north = false;
				int randomNumber = (int) (3 * Math.random());
				if (randomNumber == 0) {
					this.setDirection(0);
				} else if (randomNumber == 1) {
					this.setDirection(2);
				} else {
					this.setDirection(3);
				}
			}
			if (locationZ > currentMaze.maxZ - 1) {
				west = false;
				this.setDirection((int) (3 * Math.random()));
			}

		} else {

			this.updateMovementPatrol();

			boolean[] enemyCollide = MazeRunner.level.collides(this, 1);
			if (enemyCollide[0]) {
				this.setLocationX(locationX);
				this.setDirection((int) (1 + 3 * Math.random()));
			}
			if (enemyCollide[1]) {
				this.setLocationZ(locationZ);
				int randomNumber = (int) (3 * Math.random());
				if (randomNumber == 0) {
					this.setDirection(0);
				} else if (randomNumber == 1) {
					this.setDirection(2);
				} else {
					this.setDirection(3);
				}
			}
			if (enemyCollide[2]) {
				this.setLocationX(locationX);
				int randomNumber = (int) (3 * Math.random());
				if (randomNumber == 0) {
					this.setDirection(0);
				} else if (randomNumber == 1) {
					this.setDirection(1);
				} else {
					this.setDirection(3);
				}
			}
			if (enemyCollide[3]) {
				this.setLocationZ(locationZ);
				this.setDirection((int) (3 * Math.random()));
			}
		}
		Maze currentMaze = MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this));
		if (locationX > currentMaze.maxX - 1) {
			east = false;
			this.setDirection((int) (1 + 3 * Math.random()));
		}
		if (locationX < currentMaze.minX + 1) {
			west = false;
			int randomNumber = (int) (3 * Math.random());
			if (randomNumber == 0) {
				this.setDirection(0);
			} else if (randomNumber == 1) {
				this.setDirection(1);
			} else {
				this.setDirection(3);
			}
		}
		if (locationZ < currentMaze.minZ + 1) {
			north = false;
			int randomNumber = (int) (3 * Math.random());
			if (randomNumber == 0) {
				this.setDirection(0);
			} else if (randomNumber == 1) {
				this.setDirection(2);
			} else {
				this.setDirection(3);
			}
		}
		if (locationZ > currentMaze.maxZ - 1) {
			west = false;
			this.setDirection((int) (3 * Math.random()));
		}

		//Now that the direction is set in every possible case, we can actually move the enemy!
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

	public void drawEnemy(GL gl) {
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glScaled(0.3, 0.3, 0.3);
		if (alert) {
			gl.glBindTexture(GL.GL_TEXTURE_2D, 21);
		} else {
			gl.glBindTexture(GL.GL_TEXTURE_2D, 23);
		}
		MazeRunner.spookyModel.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
	}

	public static void showEnemy(GL gl) {
		gl.glBindTexture(GL.GL_TEXTURE_2D, 23);
		MazeRunner.spookyModel.display(gl);
	}

}
