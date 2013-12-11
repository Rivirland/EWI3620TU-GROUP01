package enemies;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import engine.Animator;
import engine.Maze;
import engine.MazeRunner;
import engine.Player;
import engine.PlayerState;
import engine.VisibleObject;

public class EnemySpooky extends Enemy implements VisibleObject {

	public EnemySpooky(double x, double y, double z, double speed, double h) {
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

			this.updateMovementFollow(player);

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
				if (Math.sqrt(Math.pow(locationZ - playerZ, 2) + Math.pow(locationX - playerX, 2)) < 1 && player.playerStateInt != 4) {
					PlayerState.getState(Player.playerStateInt).leaving();
					Player.playerStateInt = 3;
					PlayerState.getState(Player.playerStateInt).entering();
				}
			}

		} else {
			this.updateMovementPatrol();
			Maze currentMaze = MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(this));

			if (locationX > currentMaze.maxX - 1) {
				east = false;
				this.setRandomizer((int) (1 + 3 * Math.random()));
			}
			if (locationX < currentMaze.minX + 1) {
				west = false;
				int randomNumber = (int) (3 * Math.random());
				if (randomNumber == 0) {
					this.setRandomizer(0);
				} else if (randomNumber == 1) {
					this.setRandomizer(1);
				} else {
					this.setRandomizer(3);
				}
			}
			if (locationZ < currentMaze.minZ + 1) {
				north = false;
				int randomNumber = (int) (3 * Math.random());
				if (randomNumber == 0) {
					this.setRandomizer(0);
				} else if (randomNumber == 1) {
					this.setRandomizer(2);
				} else {
					this.setRandomizer(3);
				}
			}
			if (locationZ > currentMaze.maxZ - 1) {
				west = false;
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
	public void drawEnemy(GL gl) {
		// GLUT glut = new GLUT();
		// glut.glutSolidTeapot(1);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 6);
		MazeRunner.spookyModel.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
	}

	
}
