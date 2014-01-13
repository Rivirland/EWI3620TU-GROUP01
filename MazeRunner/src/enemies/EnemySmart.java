package enemies;

import javax.media.opengl.GL;

import engine.Maze;
import engine.MazeRunner;
import engine.Player;
import playerStates.*;
import engine.VisibleObject;

public class EnemySmart extends Enemy implements VisibleObject {

	public EnemySmart(double x, double y, double z, double speed, double h) {
		super(x, y, z, speed, h);
	}

	public void update(int deltaTime, Player player) {
		alert = false;
		int currentMazeID = MazeRunner.level.getCurrentMaze(this);
		Maze currentMaze = MazeRunner.level.getMaze(currentMazeID);
		if (MazeRunner.level.inSameMaze(this, player) != -1 && !player.invisible) {
			int enemyMatrixX = currentMaze.coordToMatrixElement(locationX-currentMaze.mazeX);
			int enemyMatrixZ = currentMaze.coordToMatrixElement(locationZ-currentMaze.mazeZ);

			double playerX = player.getLocalX();
			double playerZ = player.getLocalZ();

			int playerMatrixX = currentMaze.coordToMatrixElement(playerX);
			int playerMatrixZ = currentMaze.coordToMatrixElement(playerZ);
			
			if (enemyMatrixX != playerMatrixX && enemyMatrixZ != playerMatrixZ) {
				this.updateMovementPatrol();
				
			}
			if (enemyMatrixX == playerMatrixX && enemyMatrixZ == playerMatrixZ) {
				if (locationX-currentMaze.mazeX > playerX) {
					this.locationX -= this.speed * deltaTime;
				}
				if (locationX-currentMaze.mazeX < playerX) {
					this.locationX += this.speed * deltaTime;
				}
				if (locationZ-currentMaze.mazeZ > playerZ) {
					this.locationZ -= this.speed * deltaTime;
				}
				if (locationZ-currentMaze.mazeZ < playerZ) {
					this.locationZ += this.speed * deltaTime;
				}
				if (Math.sqrt(Math.pow(locationZ-currentMaze.mazeZ - playerZ, 2)
						+ Math.pow(locationX-currentMaze.mazeX- playerX, 2)) < 1 && player.playerStateInt != 4 && player.playerStateInt != 5) {
					PlayerState.getState(player.playerStateInt).leaving();
					player.playerStateInt = 3;
					PlayerState.getState(player.playerStateInt).entering();
				}
			}
			if (enemyMatrixX == playerMatrixX && enemyMatrixZ != playerMatrixZ) {
				int diffZ = enemyMatrixZ - playerMatrixZ;
				if (diffZ > 0) {
					boolean wallDetected = false;
					for (int i = enemyMatrixZ; i > playerMatrixZ; i--) {
						if (currentMaze.getElementOnCoords(enemyMatrixX, i) > 0
								&& !(enemyMatrixX % 2 == 1 && i % 2 == 1)) {
							wallDetected = true;
						}
					}
					if (wallDetected) {
						this.updateMovementPatrol();
					} else {
						this.updateMovementFollow(player);
						alert = true;
					}
				} else {
					boolean wallDetected = false;
					for (int i = enemyMatrixZ; i < playerMatrixZ; i++) {
						if (currentMaze.getElementOnCoords(enemyMatrixX, i) > 0
								&& !(enemyMatrixX % 2 == 1 && i % 2 == 1)) {
							wallDetected = true;
						}
					}
					if (wallDetected) {
						this.updateMovementPatrol();
					} else {
						this.updateMovementFollow(player);
						alert = true;
					}
				}
			}
			if (enemyMatrixX != playerMatrixX && enemyMatrixZ == playerMatrixZ) {
				int diffX = enemyMatrixX - playerMatrixX;
				if (diffX > 0) {
					boolean wallDetected = false;
					for (int i = enemyMatrixX; i > playerMatrixX; i--) {
						if (currentMaze.getElementOnCoords(i, enemyMatrixZ) > 0
								&& !(enemyMatrixZ % 2 == 1 && i % 2 == 1)) {
							wallDetected = true;
						}
					}
					if (wallDetected) {
						this.updateMovementPatrol();
					} else {
						this.updateMovementFollow(player);
						alert = true;
					}
				} else {
					boolean wallDetected = false;
					for (int i = enemyMatrixX; i < playerMatrixX; i++) {
						if (currentMaze.getElementOnCoords(i, enemyMatrixZ) > 0
								&& !(enemyMatrixZ % 2 == 1 && i % 2 == 1)) {
							wallDetected = true;
						}
					}
					if (wallDetected) {
						this.updateMovementPatrol();
					} else {
						this.updateMovementFollow(player);
						alert = true;
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
		
		if (locationX > currentMaze.maxX - 1){
			east = false;
			this.setRandomizer((int) (1 + 3 * Math.random()));
		}
		if (locationX < currentMaze.minX + 1){
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
		if (locationZ > currentMaze.maxZ - 1){
			west = false;
			this.setRandomizer((int) (3 * Math.random()));
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
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glScaled(0.5,0.5,0.5);
		if(alert){
			gl.glBindTexture(GL.GL_TEXTURE_2D, 24);
		}else{
			gl.glBindTexture(GL.GL_TEXTURE_2D, 22);
		}
		MazeRunner.spookyModel.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
	}

}
