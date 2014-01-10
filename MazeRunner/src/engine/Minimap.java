package engine;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import menu.Teken;

public class Minimap {
	private static int minimapX = (int) (.4 * MazeRunner.screenWidth);
	private static int minimapZ = (int) (.4 * MazeRunner.screenHeight);
	static double columnPercentageX, columnPercentageZ;
	static double wallPercentageX, wallPercentageZ;

	public static void displayMinimap(GL gl) {
		GLU glu = new GLU();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();

		glu.gluOrtho2D(0, MazeRunner.screenWidth, 0, MazeRunner.screenHeight);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_TEXTURE_2D);
		// gl.glDisable(GL.GL_DEPTH_TEST);
		
		iterateOverMatrix(gl);
		drawBackground(gl);
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60,
				MazeRunner.screenWidth / MazeRunner.screenHeight, .1, 200);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_TEXTURE_2D);
		// gl.glEnable(GL.GL_DEPTH_TEST);

		// gl.glLoadIdentity();
		gl.glPopMatrix();
	}

	public static void iterateOverMatrix(GL gl) {
		int mazeID = MazeRunner.level.getCurrentMaze(MazeRunner.player);
		if (mazeID != -1) {
			Maze curMaze = MazeRunner.level.getMaze(mazeID);
			double totalX = curMaze.maxX - curMaze.minX;
			double totalZ = curMaze.maxZ - curMaze.minZ;
			columnPercentageX = (curMaze.COLUMN_WIDTH / totalX);
			columnPercentageZ = (curMaze.COLUMN_WIDTH / totalZ);
			wallPercentageX = curMaze.WALL_LENGTH / totalX;
			wallPercentageZ = curMaze.WALL_LENGTH / totalZ;
			// System.out.println(wallPercentageX+ " " + wallPercentageZ);
			
			for (int i = 0; i < curMaze.visitedMatrix.length; i++) {
				for (int j = 0; j < curMaze.visitedMatrix[0].length; j++) {
					if (curMaze.visitedMatrix[i][j] == 1) {
						double xtrans = (Math.floor(((double) (i + 1)) / 2) * columnPercentageX * getMinimapX() + Math
								.floor((double) i / 2) * wallPercentageX * getMinimapX());
						double ztrans = (Math.floor(((double) (j + 1)) / 2) * columnPercentageZ * getMinimapZ() + Math
								.floor((double) j / 2) * wallPercentageZ * getMinimapZ());
						// System.out.println(xtrans + " " + ztrans);

						gl.glPushMatrix();
						// gl.glTranslated(xtrans, 0.0, ztrans);
						if (curMaze.maze[i][j] > 0) {
							gl.glColor3d(205f/255f,133f/255f,63f/255f);
						}else{
							gl.glColor3d(222f/255f,184f/255f,135f/255f);
						}
							// If it's (even,even), you paint a column
							if (i % 2 == 0 && j % 2 == 0) {
								// System.out.println("paint column");
								drawColumn(gl, xtrans, ztrans);
							}
							// (odd,even) paints a wall in the Z-direction
							if (i % 2 != 0 && j % 2 == 0) {
								// System.out.println("paint wallZ");
								drawWallz(gl, xtrans, ztrans);
							}
							// (even,odd) paints a wall in the X-direction
							if (i % 2 == 0 && j % 2 != 0) {

								drawWallx(gl, xtrans, ztrans);
							
						}
						if (i % 2 != 0 && j % 2 != 0 && curMaze.maze[i][j] == 0) {
							gl.glColor3d(222f/255f,184f/255f,135f/255f);

							drawFloor(gl, xtrans, ztrans);
						}
					}
					double x = MazeRunner.player.getLocalX() / totalX * getMinimapX();
					double z = MazeRunner.player.getLocalZ() / totalZ * getMinimapZ();
					drawPlayer(gl, x, z);
					gl.glColor3d(1, 1, 1);
					gl.glPopMatrix();
				}
			}
		}
	}

	public static void drawColumn(GL gl, double x, double z) {
		// System.out.println("1; " + columnPercentageX*minimapX + " 2: " +
		// columnPercentageZ*minimapZ);
		// Teken.rechthoek(gl, 0, 0, (float) columnPercentage*minimapX, (float)
		// columnPercentage*minimapZ);

		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2d(x, z);
		gl.glVertex2d(columnPercentageX * getMinimapX() + x, z);
		gl.glVertex2d(columnPercentageX * getMinimapX() + x, columnPercentageZ
				* getMinimapZ() + z);
		gl.glVertex2d(x, columnPercentageZ * getMinimapZ() + z);
		gl.glEnd();
	}

	public static void drawWallz(GL gl, double x, double z) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2d(x, z);
		gl.glVertex2d(wallPercentageX * getMinimapX() + x, z);
		gl.glVertex2d(wallPercentageX * getMinimapX() + x, columnPercentageZ
				* getMinimapZ() + z);
		gl.glVertex2d(x, columnPercentageZ * getMinimapZ() + z);
		gl.glEnd();
	}

	public static void drawWallx(GL gl, double x, double z) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2d(x, z);
		gl.glVertex2d(columnPercentageX * getMinimapX() + x, z);
		gl.glVertex2d(columnPercentageX * getMinimapX() + x, wallPercentageZ
				* getMinimapZ() + z);
		gl.glVertex2d(x, wallPercentageZ * getMinimapZ() + z);
		gl.glEnd();
	}

	public static void drawFloor(GL gl, double x, double z) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2d(x, z);
		gl.glVertex2d(wallPercentageX * getMinimapX() + x, z);
		gl.glVertex2d(wallPercentageX * getMinimapX() + x, wallPercentageZ
				* getMinimapZ() + z);
		gl.glVertex2d(x, wallPercentageZ * getMinimapZ() + z);
		gl.glEnd();
	}

	public static void drawPlayer(GL gl, double x, double z) {
		// System.out.println(x + " " + z);
		gl.glColor3d(0, 0, 1);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2d(x - 5, z - 5);
		gl.glVertex2d(5 + x, z - 5);
		gl.glVertex2d(5 + x, 5 + z);
		gl.glVertex2d(x - 5, 5 + z);
		gl.glEnd();
	}
	
	public static void drawBackground(GL gl){
		gl.glColor3d(0,0,0);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2d(0, 0);
		gl.glVertex2d(getMinimapX(), 0);
		gl.glVertex2d(getMinimapX(), getMinimapZ());
		gl.glVertex2d(0, getMinimapZ());
		gl.glEnd();

	}

	public static int getMinimapX() {
		return minimapX;
	}

	public static void setMinimapX(int minimapX) {
		Minimap.minimapX = minimapX;
	}

	public static int getMinimapZ() {
		return minimapZ;
	}

	public static void setMinimapZ(int minimapZ) {
		Minimap.minimapZ = minimapZ;
	}
}
