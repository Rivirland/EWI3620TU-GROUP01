package engine;

import items.BulletHolder;
import items.Exit;
import items.Item;
import items.Roof;
import items.TrapHolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.media.opengl.GL;

import menu.Teken;
import enemies.EnemySmart;
import enemies.EnemySpooky;

/**
 * Maze represents the maze used by MazeRunner.
 * <p>
 * The Maze is defined as an array of integers, where 0 equals nothing and 1
 * equals a wall. Note that the array is square and that MAZE_SIZE contains the
 * exact length of one side. This is to perform various checks to ensure that
 * there will be no ArrayOutOfBounds exceptions and to perform the calculations
 * needed by not only the display(GL) function, but also by functions in the
 * MazeRunner class itself.<br />
 * Therefore it is of the utmost importance that MAZE_SIZE is correct.
 * <p>
 * SQUARE_SIZE is used by both MazeRunner and Maze itself for calculations of
 * the display(GL) method and other functions. The larger this value, the larger
 * the world of MazeRunner will be.
 * <p>
 * This class implements VisibleObject to force the developer to implement the
 * display(GL) method, so the Maze can be displayed.
 * 
 * @author Bruno Scheele, revised by Mattijs Driel
 * 
 */
public class Maze implements VisibleObject {

	public int MAZE_SIZE_X = 0;
	public int MAZE_SIZE_Z = 0;
	public final static double CELL_SIZE = 7;
	public final double SQUARE_SIZE = 5;
	public final static double WALL_WIDTH = 0.5;
	public final static double WALL_LENGTH = CELL_SIZE - WALL_WIDTH;
	public final static double COLUMN_WIDTH = WALL_WIDTH;
	public final static double ITEM_HEIGHT = 5;
	public final static double DOOR_WIDTH = 2;
	public final static double DOOR_HEIGHT = 3.5;
	public int mazeX, mazeY, mazeZ;
	public int minX, minZ, mazeID;
	public double maxX, maxZ;
	public ArrayList<Item> itemList = new ArrayList<Item>();
	public int[][] maze = new int[MAZE_SIZE_X][MAZE_SIZE_Z];
	public int[][] textureMatrix = new int[MAZE_SIZE_X][MAZE_SIZE_Z];
	public int[][] visitedMatrix = new int[MAZE_SIZE_X][MAZE_SIZE_Z];
	public int maxHeight;

	public Maze(String filename, int i, int x, int y, int z) {
		mazeID = i - 1;
		mazeX = x;
		mazeY = y;
		mazeZ = z;
		maxHeight = 1;
		try {
			loadMaze(filename, i);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Loads the maze into an int[][]: it goes through the file to determine the
	// matrix size
	// and then actually fills the matrix with the correct elements. It also
	// stores several
	// useful properties such as the mazeX,Y and Z to determine where it will be
	// positioned
	// in the 3D space.
	public void loadMaze(String filename, int i) throws FileNotFoundException {
		String currentdir = System.getProperty("user.dir");
		filename = currentdir + "\\levels\\" + filename;
		File infile = new File(filename + ".txt");
		try {
			loadMazeSize(infile);
		} catch (IOException e) {
			System.out.println("Fout in loadMazeSize");
		}
		try {
			buildMaze(infile);
		} catch (IOException e) {
			System.out.println("Fout in buildMaze");
		}
		File infileTex = new File(filename + "_t.txt");
		try {
			buildTextureMatrix(infileTex);
		} catch (NumberFormatException e) {
			System.out.println("Fout in buildTextureMatrix - NFE");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Fout in buildTextureMatrix - IOE");
			e.printStackTrace();
		}
		File objectsFile = new File(filename + "_o.txt");
		readObjects(objectsFile);
		for (int row = 1; row < MAZE_SIZE_X; row += 2) {
			for (int col = 1; col < MAZE_SIZE_Z; col += 2) {
				if (maze[row][col] > 0) {
					double rx = Math.floor((row + 1) / 2) * COLUMN_WIDTH + Math.floor(row / 2) * WALL_LENGTH;
					double rz = Math.floor((col + 1) / 2) * COLUMN_WIDTH + Math.floor(col / 2) * WALL_LENGTH;
					Roof r = new Roof(rx + mazeX, ITEM_HEIGHT * maze[row][col] + mazeY, rz + mazeZ, mazeID, WALL_LENGTH, row, col);
					MazeRunner.roofList.add(r);
				}
			}
		}
	}

	private void readObjects(File objectsFile) {
		BufferedReader bufRdrTex = null;
		try {
			bufRdrTex = new BufferedReader(new FileReader(objectsFile));
			String line = null;
			try {
				while ((line = bufRdrTex.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line, ",");
					double objectNumber = Double.parseDouble(st.nextToken());
					if (objectNumber == 1) {
						double objectX = Double.parseDouble(st.nextToken());
						double objectZ = Double.parseDouble(st.nextToken());
						int fd = (int) Double.parseDouble(st.nextToken());
//						int portalID = (int) Double.parseDouble(st.nextToken());
//						int portalConID = (int) Double.parseDouble(st.nextToken());
						Portal portal = new Portal((float) (mazeX + objectX), mazeY, (float) (mazeZ + objectZ), fd);
						MazeRunner.portalList.add(portal);
						// Portal portal = new Portal((float)objectX,
						// (float)objectY, objectZ, fd);
						// System.out.println("Maakt portal " + portalID +
						// " naar " + portalConnectionID + " op " + objectX +
						// ", " + objectZ);
						continue;
					} else if (objectNumber == 2) {
						// EnemySpooky
						double objectX = Double.parseDouble(st.nextToken());
						double objectZ = Double.parseDouble(st.nextToken());
						EnemySpooky es = new EnemySpooky(mazeX + objectX, mazeY + 2.5, mazeZ + objectZ, 0.0015, mazeID);
						MazeRunner.enemyList.add(es);
						// System.out.println("Maakt enemySpooky op: " + objectX
						// + ", " + objectZ);
					} else if (objectNumber == 3) {
						// EnemySmart
						double objectX = Double.parseDouble(st.nextToken());
						double objectZ = Double.parseDouble(st.nextToken());
						EnemySmart es = new EnemySmart(mazeX + objectX, mazeY + 2.5, mazeZ + objectZ, 0.005, mazeID);
						MazeRunner.enemyList.add(es);
					} else if (objectNumber == 4) {
						double objectX = Double.parseDouble(st.nextToken());
						double objectZ = Double.parseDouble(st.nextToken());
						int amount = (int) Double.parseDouble(st.nextToken());
						BulletHolder bh = new BulletHolder(mazeX + objectX, mazeY, mazeZ + objectZ, mazeID, amount);
						itemList.add(bh);
						MazeRunner.visibleObjects.add(bh);
					} else if (objectNumber == 5) {
						double objectX = Double.parseDouble(st.nextToken());
						double objectZ = Double.parseDouble(st.nextToken());
						TrapHolder th = new TrapHolder(mazeX + objectX, mazeY, mazeZ + objectZ, mazeID);
						itemList.add(th);
						MazeRunner.visibleObjects.add(th);
					} else if (objectNumber == 6) {
						double objectX = Double.parseDouble(st.nextToken());
						double objectZ = Double.parseDouble(st.nextToken());
						Exit e = new Exit(mazeX + objectX, mazeY+2.5, mazeZ + objectZ, mazeID);
						itemList.add(e);
						MazeRunner.visibleObjects.add(e);
					}
				}
			} catch (IOException e) {
				System.out.println("Fout in readObjects");
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void buildTextureMatrix(File infile) throws NumberFormatException, IOException {
		int row = 0;
		int col = 0;
		BufferedReader bufRdrTex;
		try {
			bufRdrTex = new BufferedReader(new FileReader(infile));
			String line = null;
			while ((line = bufRdrTex.readLine()) != null && row < MAZE_SIZE_Z) {
				StringTokenizer st = new StringTokenizer(line, ",");
				while (st.hasMoreTokens()) {
					textureMatrix[MAZE_SIZE_X - 1 - row][col] = Integer.parseInt(st.nextToken());
					col++;
				}

				col = 0;
				row++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fout in buildTextureMatrix");
			e.printStackTrace();
		}

	}

	// Loads the maze's dimensions.
	private void loadMazeSize(File file) throws NumberFormatException, IOException {

		int row = 0;
		int col = 0;
		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		String line = null;
		while ((line = bufRdr.readLine()) != null) {
			col = 0;
			StringTokenizer st = new StringTokenizer(line, ",");
			while (st.hasMoreTokens()) {
				col++;
				st.nextToken();
			}
			row++;
		}
		bufRdr.close();
		MAZE_SIZE_X = row;
		MAZE_SIZE_Z = col;

		maze = new int[MAZE_SIZE_X][MAZE_SIZE_Z];
		textureMatrix = new int[MAZE_SIZE_X][MAZE_SIZE_Z];
		visitedMatrix = new int[MAZE_SIZE_X][MAZE_SIZE_Z];
	}

	public double getMaxX() {
		return this.maxX;
	}

	public int getMinX() {
		return this.minX;
	}

	public double getMaxZ() {
		return this.maxZ;
	}

	public int getMinZ() {
		return this.minZ;
	}

	public int getMazeID() {
		return this.mazeID;
	}

	public int getMazeX() {
		return this.mazeX;
	}

	public int getMazeY() {
		return this.mazeY;
	}

	public int getMazeZ() {
		return this.mazeZ;
	}

	// Translates a coordinate to the row/column this column will be found. This
	// method is kind of confusing,
	// because our matrix design is pretty weird: elements on (even,even) are
	// smaller than elements on (even,odd)
	// and (odd,even) and elements on (odd,odd) represent a roof.
	public int coordToMatrixElement(double input) {
		int res = -1;
		double sum = 0;
		int columnWallSwitcher = 0;
		while (sum <= input) {
			if (columnWallSwitcher == 0) {
				sum += COLUMN_WIDTH;
			}
			if (columnWallSwitcher == 1) {
				sum += WALL_LENGTH;
			}
			columnWallSwitcher = 1 - columnWallSwitcher;
			res++;
		}
		return res;
	}

	public double[] MatrixElementToCoords(int i, int j) {
		double[] res = { mazeX, mazeZ };
		while (i > 0) {
			if (i % 2 == 1) {
				res[0] += Maze.WALL_WIDTH;
			} else {
				res[0] += Maze.WALL_LENGTH;
			}
			i--;
		}
		while (j > 0) {
			if (j % 2 == 1) {
				res[1] += Maze.WALL_WIDTH;
			} else {
				res[1] += Maze.WALL_LENGTH;
			}
			j--;
		}
		return res;
	}

	// Fills maze[][] with the right elements.
	private void buildMaze(File file) throws IOException {
		int row = 0;
		int col = 0;
		BufferedReader bufRdr = new BufferedReader(new FileReader(file));
		String line = null;

		while ((line = bufRdr.readLine()) != null && row < MAZE_SIZE_Z) {
			StringTokenizer st = new StringTokenizer(line, ",");
			while (st.hasMoreTokens()) {
				// System.out.println(Integer.parseInt(st.nextToken()) +
				// " en row: " + row + " en col: " + col);
				maze[MAZE_SIZE_X - 1 - row][col] = Integer.parseInt(st.nextToken());
				if (maze[MAZE_SIZE_X - 1 - row][col] > maxHeight) {
					maxHeight = maze[MAZE_SIZE_X - 1 - row][col];
				}
				// System.out.println(maze[col][row]);
				col++;
			}
			col = 0;
			row++;
		}
		bufRdr.close();
		// printMatrix();
		minX = mazeX;
		minZ = mazeZ;
		maxX = minX + Math.floor(((double) MAZE_SIZE_X + 1) / 2) * COLUMN_WIDTH + Math.floor((double) MAZE_SIZE_X / 2) * WALL_LENGTH;
		maxZ = minZ + Math.floor(((double) MAZE_SIZE_Z + 1) / 2) * COLUMN_WIDTH + Math.floor((double) MAZE_SIZE_Z / 2) * WALL_LENGTH;
	}

	public int getElementOnCoords(int i, int j) {
		if (i >= 0 && j >= 0 && i < coordToMatrixElement(maxX - minX) && j < coordToMatrixElement(maxZ - minZ)) {
			return maze[i][j];
		}
		return 0;
	}

	public int getTextureElementOnCoords(int i, int j) {
		if (i >= 0 && j >= 0 && i < coordToMatrixElement(maxX - minX) && j < coordToMatrixElement(maxZ - minZ)) {
			return textureMatrix[i][j];
		}
		return 0;
	}
	// Prints the maze.
	public void printMaze() {
		String res = "";
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				res = res + maze[i][j] + ",";
			}
			res = res + "\n";
		}
		System.out.println(res);
	}

	/**
	 * isWall(int x, int z) checks for a wall.
	 * <p>
	 * It returns whether maze[x][z] contains a 1.
	 * 
	 * @param x
	 *            the x-coordinate of the location to check
	 * @param z
	 *            the z-coordinate of the location to check
	 * @return whether there is a wall at maze[x][z]
	 */
	public boolean isWall(int x, int z) {
		if (x >= 0 && x < MAZE_SIZE_X && z >= 0 && z < MAZE_SIZE_Z)
			return maze[x][z] > 0;
		else
			return false;
	}

	public boolean isDebris(int x, int z) {
		if (maze[x][z] == -1) {
			return true;
		} else {
			return false;
		}
	}

	// Basically the same as isWall;
	public boolean isExit(int x, int z) {
		if (x == MAZE_SIZE_X - 2 && z == MAZE_SIZE_Z - 2)
			return true;
		else
			return false;
	}

	/**
	 * isWall(double x, double z) checks for a wall by converting the double
	 * values to integer coordinates.
	 * <p>
	 * This method first converts the x and z to values that correspond with the
	 * grid defined by maze[][]. Then it calls upon isWall(int, int) to check
	 * for a wall.
	 * 
	 * @param x
	 *            the x-coordinate of the location to check
	 * @param z
	 *            the z-coordinate of the location to check
	 * @return whether there is a wall at maze[x][z]
	 */
	public boolean isWall(double x, double z) {
		int gX = convertToGridX(x);
		int gZ = convertToGridZ(z);
		return isWall(gX, gZ);
	}

	public boolean isExit(double x, double z) {
		return false;
		// int gX = convertToGridX(x);
		// int gZ = convertToGridZ(z);
		// return isExit(gX, gZ);
	}

	/**
	 * Converts the double x-coordinate to its correspondent integer coordinate.
	 * 
	 * @param x
	 *            the double x-coordinate
	 * @return the integer x-coordinate
	 */
	private int convertToGridX(double x) {
		return (int) Math.floor(x / SQUARE_SIZE);
	}

	/**
	 * Converts the double z-coordinate to its correspondent integer coordinate.
	 * 
	 * @param z
	 *            the double z-coordinate
	 * @return the integer z-coordinate
	 */
	private int convertToGridZ(double z) {
		return (int) Math.floor(z / SQUARE_SIZE);
	}
	public void display(GL gl) {
		gl.glPushMatrix();
		gl.glTranslated(mazeX, mazeY, mazeZ);
		// displayItems(gl);
		// draw the grid with the current material
		for (int i = 0; i < MAZE_SIZE_X; i++) {
			for (int j = 0; j < MAZE_SIZE_Z; j++) {
				if (isDebris(i, j)) {
					gl.glPushMatrix();
					gl.glBindTexture(GL.GL_TEXTURE_2D, 16);
					// Here you calculate the coordinates for which the
					// bottom left point of the element has to be drawn
					double xtrans = Math.floor(((double) i + 1) / 2) * COLUMN_WIDTH + Math.floor((double) i / 2) * WALL_LENGTH;
					double ztrans = Math.floor(((double) j + 1) / 2) * COLUMN_WIDTH + Math.floor((double) j / 2) * WALL_LENGTH;
					gl.glTranslated(xtrans, 0.0, ztrans);
					if (i % 2 == 0 && j % 2 == 0) {
						drawDebrisColumnFromQuad(gl);
					}
					// (odd,even) paints debris in the Z-direction
					if (i % 2 != 0 && j % 2 == 0) {
						drawDebrisZFromQuad(gl);
					}
					// (even,odd) paints debris in the X-direction
					if (i % 2 == 0 && j % 2 != 0) {
						drawDebrisXFromQuad(gl);
					}
					gl.glPopMatrix();
				}
				if (isWall(i, j)) {

					for (int height = 0; height < maze[i][j]; height++) {
						gl.glPushMatrix();
						// Here you calculate the coordinates for which the
						// bottom left point of the element has to be drawn
						double xtrans = Math.floor(((double) i + 1) / 2) * COLUMN_WIDTH + Math.floor((double) i / 2) * WALL_LENGTH;
						double ztrans = Math.floor(((double) j + 1) / 2) * COLUMN_WIDTH + Math.floor((double) j / 2) * WALL_LENGTH;
						gl.glTranslated(xtrans, 0.0, ztrans);
						if (textureMatrix[i][j] <= 2) {
							gl.glBindTexture(GL.GL_TEXTURE_2D, 15);
						} else if (textureMatrix[i][j] >= 3 && textureMatrix[i][j] <= 4) {
							gl.glBindTexture(GL.GL_TEXTURE_2D, 16);
						}

						// If it's (even,even), you paint a column
						if (i % 2 == 0 && j % 2 == 0) {
							drawColumnFromQuad(gl, height * ITEM_HEIGHT);
						}

						// (odd,even) paints a wall/door in the Z-direction
						if (i % 2 != 0 && j % 2 == 0) {
							if (height == 0 && textureMatrix[i][j] % 2 == 0) {
								drawDoorZFromQuad(gl, height * ITEM_HEIGHT);
							} else {
								drawWallZFromQuad(gl, height * ITEM_HEIGHT);
							}
						}

						// (even,odd) paints a wall in the X-direction
						if (i % 2 == 0 && j % 2 != 0) {
							if (height == 0 && textureMatrix[i][j] % 2 == 0) {
								paintDoorXFromQuad(gl, height * ITEM_HEIGHT, i, j);
							} else {
								drawWallXFromQuad(gl, height * ITEM_HEIGHT);
							}
						}
						// (odd,odd) paints a roof
						if (i % 2 != 0 && j % 2 != 0) {
							// paintRoof(gl, (height + 1) * ITEM_HEIGHT);
						}
						gl.glPopMatrix();

					}
				}
			}
		}

		// Calculates the size of the maze and then draws the floor tile
		double xsize = Math.floor(((double) MAZE_SIZE_X + 1) / 2) * COLUMN_WIDTH + Math.floor((double) MAZE_SIZE_X / 2) * WALL_LENGTH;
		double zsize = Math.floor(((double) MAZE_SIZE_Z + 1) / 2) * COLUMN_WIDTH + Math.floor((double) MAZE_SIZE_Z / 2) * WALL_LENGTH;
		
		drawSingleFloorTile(gl, xsize, zsize); // Paint the floor.
		gl.glPopMatrix();
	}

	static void setLighting(GL gl) {
		// Set the ambient and diffuse properties
		float matAmbient[] = { 0.8f,0.8f,0.8f, 1.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, matAmbient, 0);
	}



	public static void drawDebrisXFromQuad(GL gl) {
		Teken.drawCuboid(gl,0.0,WALL_WIDTH,0.0,ITEM_HEIGHT/10,0.0,WALL_LENGTH);

	}


	

	public static void drawDebrisColumnFromQuad(GL gl) {
		Teken.drawCuboid(gl,0.0,COLUMN_WIDTH,0.0,ITEM_HEIGHT/10,0.0,COLUMN_WIDTH);
		
	}





	public static void drawDebrisZFromQuad(GL gl) {
		Teken.drawCuboid(gl, 0.0, WALL_LENGTH, 0.0, ITEM_HEIGHT/10, 0.0, WALL_WIDTH);
	}


	
	public static void drawSingleFloorTile(GL gl, double size_x, double size_z) {

		setLighting(gl);

		double thick=1.0;
		// Apply texture.
		Teken.drawCuboid(gl, 0, size_x, -thick, 0, 0, size_z, 5);


	}

	

	public static void paintWallZFromQuad(GL gl, double h, int texture) {
		gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
		drawWallZFromQuad(gl, h);
	}

	public static void drawWallZFromQuad(GL gl, double h) {
		setLighting(gl);
		Teken.drawCuboid(gl, 0.0, WALL_LENGTH, h, ITEM_HEIGHT+h, 0.0, WALL_WIDTH);
	}

	private void paintDoorXFromQuad(GL gl, double h, int i, int j) {
		gl.glPushMatrix();
		gl.glRotated(90, 0, 1, 0);
		gl.glTranslated(-WALL_LENGTH, 0, 0);
		drawDoorZFromQuad(gl, h);
		gl.glPopMatrix();
	}



	public static void drawDoorZFromQuad(GL gl, double h) {
		// Links van de deur
		Teken.drawCuboid(gl, 0.0, (WALL_LENGTH-DOOR_WIDTH)/2, h, ITEM_HEIGHT+h, 0.0, WALL_WIDTH);
		// Rechts van de deur
		Teken.drawCuboid(gl, (WALL_LENGTH+DOOR_WIDTH)/2, WALL_LENGTH, h, ITEM_HEIGHT+h, 0.0, WALL_WIDTH);
		// Boven de deur
		Teken.drawCuboid(gl, (WALL_LENGTH-DOOR_WIDTH)/2, (WALL_LENGTH+DOOR_WIDTH)/2, DOOR_HEIGHT+h, ITEM_HEIGHT+h, 0.0, WALL_WIDTH);

	}



	public static void drawWallXFromQuad(GL gl, double h) {
		setLighting(gl);
		Teken.drawCuboid(gl, 0.0, WALL_WIDTH, h, ITEM_HEIGHT+h, 0.0, WALL_LENGTH);
		
	}



	public static void paintColumnFromQuad(GL gl, double h, int texture) {
		gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
		drawColumnFromQuad(gl, h);
	}

	public static void drawColumnFromQuad(GL gl, double h) {
		setLighting(gl);
		Teken.drawCuboid(gl, 0.0, COLUMN_WIDTH, h, ITEM_HEIGHT+h, 0.0, COLUMN_WIDTH);
	}

	public static double getItemHeight() {
		return ITEM_HEIGHT;
	}

	public static double getColumnWidth() {
		return COLUMN_WIDTH;
	}

	public static double getWallWidth() {
		return WALL_LENGTH;
	}
}
