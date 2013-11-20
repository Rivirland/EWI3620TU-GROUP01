package new_default;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

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

	public int MAZE_SIZE_X=0;
	public int MAZE_SIZE_Z=0;

	public final double SQUARE_SIZE = 5;
	public final double WALL_LENGTH=6;
	public final double WALL_WIDTH=1;
	public final double COLUMN_WIDTH=1;
	public final double ITEM_HEIGHT=5;
	public int mazeX, mazeY, mazeZ;

	private int[][] maze = new int[MAZE_SIZE_X][MAZE_SIZE_Z];

	public Maze(File filename) {
		try {
			loadMaze(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public void loadMaze(File infile) throws FileNotFoundException{
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
		
	}
	private void loadMazeSize(File file) throws NumberFormatException, IOException{
		
		int row = -1;
        int col = 0;
        BufferedReader bufRdr  = new BufferedReader(new FileReader(file));
        String line = null;
        while((line = bufRdr.readLine()) != null){   
        	col = 0;
        	StringTokenizer st = new StringTokenizer(line,",");
        	while (st.hasMoreTokens()){
        		col++;
        		st.nextToken();
        	}
        	row++;
        }
        bufRdr.close();
        MAZE_SIZE_X = row;
        MAZE_SIZE_Z = col;
        System.out.println("Size x: " + MAZE_SIZE_X + " Size z: " + MAZE_SIZE_Z);
        maze=new int[MAZE_SIZE_X][MAZE_SIZE_Z]; 
	}
	
	private void buildMaze(File file) throws IOException{
        int row = -1;
        int col = 0;
        BufferedReader bufRdr  = new BufferedReader(new FileReader(file));
        String line = null;
        
        while((line = bufRdr.readLine()) != null && row < MAZE_SIZE_Z){   
        	StringTokenizer st = new StringTokenizer(line,",");
        	if(row == -1){
        		mazeX = Integer.parseInt(st.nextToken());
            	mazeY = Integer.parseInt(st.nextToken());
            	mazeZ = Integer.parseInt(st.nextToken());
        	}
        	while (st.hasMoreTokens()){
        		//System.out.println(Integer.parseInt(st.nextToken()) + " en row: " + row + " en col: " + col);
        		maze[row][col] = Integer.parseInt(st.nextToken());
        		//System.out.println(maze[col][row]);
        		col++;
        	}
        	col = 0;
        	row++;
        }
        System.out.println(mazeX + ", " + mazeY + ", " + mazeZ);
        bufRdr.close();
        printMatrix();

      }
		
		
		
		
	public void printMatrix(){
		//System.out.println("maze.length= " + maze.length);
		//System.out.println("maze.[0]length= " + maze[0].length);
		String res="";
		for(int i=0; i<maze.length; i++){
//			System.out.println("i= " + i);
			for(int j=0; j<maze[0].length; j++){
//				System.out.println("j= " + j);
				res=res + maze[i][j] + ",";
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
			return maze[x][z] != 0;
		else
			return false;
	}
	
	public boolean isExit(int x, int z) {
		if (x == MAZE_SIZE_X-2 && z == MAZE_SIZE_Z-2)
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
//		int gX = convertToGridX(x);
//		int gZ = convertToGridZ(z);
//		return isExit(gX, gZ);
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
		GLUT glut = new GLUT();
		gl.glPushMatrix();
		gl.glTranslated(mazeX, mazeY, mazeZ);
		// Setting the wall colour and material.
		float wallColour[] = { 1.0f, 0.0f, 1.0f, 1.0f }; // The walls are
															// purple.
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0); // Set the
																	// materials
																	// used by
																	// the wall.
		// draw the grid with the current material
		for (int i = 0; i < MAZE_SIZE_X; i++) {
			for (int j = 0; j < MAZE_SIZE_Z; j++) {
				if (isWall(i, j)){
					
					for (int height = 0; height < maze[i][j]; height++){		
						gl.glPushMatrix();
						double xtrans = Math.floor(((double)i+1)/2)*COLUMN_WIDTH + Math.floor((double)i/2)*WALL_LENGTH;
						double ztrans = Math.floor(((double)j+1)/2)*COLUMN_WIDTH + Math.floor((double)j/2)*WALL_LENGTH;
						gl.glTranslated(xtrans, 0.0, ztrans);
						if (i%2==0 && j%2==0){
							paintColumnFromQuad(gl, height*ITEM_HEIGHT);
						}
						if (i%2!=0 && j%2==0){
							paintWallZFromQuad(gl, height*ITEM_HEIGHT);
						}
						if (i%2==0 && j%2!=0){
							paintWallXFromQuad(gl, height*ITEM_HEIGHT);
						}
						gl.glPopMatrix();
						
					}
					// paintRoof(gl, maze[i][j] * SQUARE_SIZE);
				}
			}
		}
		
		double xsize=Math.floor(((double)MAZE_SIZE_X+1)/2)*COLUMN_WIDTH + Math.floor((double)MAZE_SIZE_X/2)*WALL_LENGTH;
		double zsize=Math.floor(((double)MAZE_SIZE_Z+1)/2)*COLUMN_WIDTH + Math.floor((double)MAZE_SIZE_Z/2)*WALL_LENGTH;
		paintSingleFloorTile(gl, xsize, zsize); // Paint the floor.
		gl.glPopMatrix();
	}

	/**
	 * paintSingleFloorTile(GL, double) paints a single floor tile, to represent
	 * the floor of the entire maze.
	 * 
	 * @param gl
	 *            the GL context in which should be drawn
	 * @param size_x
	 *            the size of the tile
	 */
	private void paintSingleFloorTile(GL gl, double size_x, double size_z) {
        
		// Setting the floor color and material.
        float[] rgba = {1f, 1f, 1f};
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);

        // Apply texture.
        if(MazeRunner.earthTexture != null){       
        	MazeRunner.earthTexture.enable();
        	MazeRunner.earthTexture.bind();
        }
       
		gl.glNormal3d(0, 1, 0);
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2d(0.0,0.0);
		gl.glVertex3d(0, 0, 0); 
		gl.glTexCoord2d(1.0,0.0);
		gl.glVertex3d(0, 0, size_z);
		gl.glTexCoord2d(0.0,1.0);
		gl.glVertex3d(size_x, 0, size_z);
		gl.glTexCoord2d(1.0,1.0);
		gl.glVertex3d(size_x, 0, 0);
		gl.glEnd();

	}
	
	private void paintRoof(GL gl, double h){
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture (GL.GL_TEXTURE_2D, 3);
		gl.glBegin (GL.GL_QUAD_STRIP);
		float wallColour[] = { 1.0f, 0.0f, 0.0f, 1.0f }; // The end tile is red
		// TODO: light shading on walls
//		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, wallColour, 0); 
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (0.0, h, 0.0);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (0.0, h, SQUARE_SIZE);
		gl.glTexCoord2d (1.0, 0.0);
		gl.glVertex3d (SQUARE_SIZE, h, 0.0);
		gl.glTexCoord2d (1.0, 1.0);
		gl.glVertex3d (SQUARE_SIZE, h, SQUARE_SIZE);
		gl.glNormal3d(0, 1, 0);	
		gl.glEnd();
	}
	private void paintWallZFromQuad(GL gl, double h){
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture (GL.GL_TEXTURE_2D, 2);
		gl.glBegin (GL.GL_QUAD_STRIP);
		float wallColour[] = { 1.0f, 0.0f, 0.0f, 1.0f }; // The end tile is red
		// TODO: light shading on walls
//		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, wallColour, 0); 
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (0.0, h, 0.0);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (0.0, ITEM_HEIGHT+h, 0.0);
		
		gl.glTexCoord2d (1.0, 0.0);
		gl.glVertex3d (WALL_LENGTH, h, 0.0);
		gl.glTexCoord2d (1.0, 1.0);
		gl.glVertex3d (WALL_LENGTH, ITEM_HEIGHT+h, 0.0);
		gl.glNormal3d(0, 0, -1);
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (WALL_LENGTH,+h,WALL_WIDTH);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (WALL_LENGTH,ITEM_HEIGHT+h,WALL_WIDTH);
		gl.glNormal3d(1, 0, 0);
		
		gl.glTexCoord2d (1.0, 0.0);
		gl.glVertex3d (0.0,h,WALL_WIDTH);
		gl.glTexCoord2d (1.0, 1.0);
		gl.glVertex3d (0.0,ITEM_HEIGHT+h,WALL_WIDTH);
		gl.glNormal3d(0, 0, 1);
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (0.0,h,0.0);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (0.0,ITEM_HEIGHT+h,0.0);
		gl.glNormal3d(-1, 0, 0);
		
		gl.glEnd ();
	}
	private void paintWallXFromQuad(GL gl, double h){
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture (GL.GL_TEXTURE_2D, 2);
		gl.glBegin (GL.GL_QUAD_STRIP);
		float wallColour[] = { 1.0f, 0.0f, 0.0f, 1.0f }; // The end tile is red
		// TODO: light shading on walls
//		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, wallColour, 0); 
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (0.0, h, 0.0);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (0.0, ITEM_HEIGHT+h, 0.0);
		
		gl.glTexCoord2d (1.0, 0.0);
		gl.glVertex3d (WALL_WIDTH, h, 0.0);
		gl.glTexCoord2d (1.0, 1.0);
		gl.glVertex3d (WALL_WIDTH, ITEM_HEIGHT+h, 0.0);
		gl.glNormal3d(0, 0, -1);
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (WALL_WIDTH,+h,WALL_LENGTH);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (WALL_WIDTH,ITEM_HEIGHT+h,WALL_LENGTH);
		gl.glNormal3d(1, 0, 0);
		
		gl.glTexCoord2d (1.0, 0.0);
		gl.glVertex3d (0.0,h,WALL_LENGTH);
		gl.glTexCoord2d (1.0, 1.0);
		gl.glVertex3d (0.0,ITEM_HEIGHT+h,WALL_LENGTH);
		gl.glNormal3d(0, 0, 1);
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (0.0,h,0.0);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (0.0,ITEM_HEIGHT+h,0.0);
		gl.glNormal3d(-1, 0, 0);
		
		gl.glEnd ();
	}
	private void paintColumnFromQuad(GL gl, double h){
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture (GL.GL_TEXTURE_2D, 2);
		gl.glBegin (GL.GL_QUAD_STRIP);
		float wallColour[] = { 1.0f, 0.0f, 0.0f, 1.0f }; // The end tile is red
		// TODO: light shading on walls
//		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, wallColour, 0); 
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (0.0, h, 0.0);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (0.0, ITEM_HEIGHT+h, 0.0);
		
		gl.glTexCoord2d (1.0, 0.0);
		gl.glVertex3d (COLUMN_WIDTH, h, 0.0);
		gl.glTexCoord2d (1.0, 1.0);
		gl.glVertex3d (COLUMN_WIDTH, ITEM_HEIGHT+h, 0.0);
		gl.glNormal3d(0, 0, -1);
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (COLUMN_WIDTH,+h,COLUMN_WIDTH);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (COLUMN_WIDTH,ITEM_HEIGHT+h,COLUMN_WIDTH);
		gl.glNormal3d(1, 0, 0);
		
		gl.glTexCoord2d (1.0, 0.0);
		gl.glVertex3d (0.0,h,COLUMN_WIDTH);
		gl.glTexCoord2d (1.0, 1.0);
		gl.glVertex3d (0.0,ITEM_HEIGHT+h,COLUMN_WIDTH);
		gl.glNormal3d(0, 0, 1);
		
		gl.glTexCoord2d (0.0, 0.0);
		gl.glVertex3d (0.0,h,0.0);
		gl.glTexCoord2d (0.0, 1.0);
		gl.glVertex3d (0.0,ITEM_HEIGHT+h,0.0);
		gl.glNormal3d(-1, 0, 0);
		
		gl.glEnd ();
	}
	
	private void paintExit(GL gl) {
		float wallColour[] = { 1.0f, 0.0f, 0.0f, 1.0f }; // The end tile is red
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0); 
		
		gl.glNormal3d(0, 1, 0);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d((MAZE_SIZE_X-1)*SQUARE_SIZE, 0.01, (MAZE_SIZE_Z-1)*SQUARE_SIZE);
		gl.glVertex3d((MAZE_SIZE_X-1)*SQUARE_SIZE, 0.01, MAZE_SIZE_Z*SQUARE_SIZE);
		gl.glVertex3d(MAZE_SIZE_X*SQUARE_SIZE, 0.01, MAZE_SIZE_Z*SQUARE_SIZE);
		gl.glVertex3d(MAZE_SIZE_X*SQUARE_SIZE, 0.01, (MAZE_SIZE_Z-1)*SQUARE_SIZE);
		gl.glEnd();
	}
}
