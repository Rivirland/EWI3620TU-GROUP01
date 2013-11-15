package new_default;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

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

	private int[][] maze = new int[MAZE_SIZE_X][MAZE_SIZE_Z];

	public Maze(double n) {
		try {
			loadMaze();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public void loadMaze() throws FileNotFoundException{
		String currentdir = System.getProperty("user.dir");
		String filename = "\\levels\\level1.txt";
		
		filename = currentdir+filename;
		System.out.println(filename);
		File infile = new File(filename);
		InputStream is = new FileInputStream(infile);
		
		try {
			loadMazeSize(is);
			is.close();
			
		} catch (IOException e) {
			System.out.println("Fout in loadMazeSize");
		}
		is = new FileInputStream(infile);
		try {
			buildMaze(is);
		} catch (IOException e) {
			System.out.println("Fout in buildMaze");
		}
		
	}
	private void loadMazeSize(InputStream is) throws NumberFormatException, IOException{
		int x=0;
		int z=1;
		int i;
		char c;
		while((i=is.read())!=-1){
			c = (char)i;
			if((c=='\n')){
				x=0;
				z+=1;
			}
			else if ((c=='1' || c=='0' || c=='2' || c=='3' || c=='4')){
				x+=1;
			}
		}
//		System.out.println("x= " + x + "z= " + z);
		maze=new int[x][z];
		MAZE_SIZE_X=x;
		MAZE_SIZE_Z=z;
	}
	private void buildMaze(InputStream is) throws IOException{
		int i;
		char c;
		int x=0;
		int y=0;
		while((i=is.read())!=-1){
			c = (char)i;
			if((c=='\n')){
//				System.out.println("y=" + y);
				x=0;
				y+=1;
//				System.out.print(c);
			}
			else if ((c=='1' || c=='0') || c=='2' || c=='3' || c=='4'){
//				System.out.println("x=" + x);
				String res = Character.toString(c);
				int d = Integer.parseInt(res);
				maze[x][y] = d;
				x+=1;
//				System.out.print(d);
			}
			else if((c==',')){
//				System.out.print(",");
			}
		}
//		System.out.println();
	}
	public void printMatrix(){
		System.out.println("maze.length= " + maze.length);
		System.out.println("maze.[0]length= " + maze[0].length);
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
		int gX = convertToGridX(x);
		int gZ = convertToGridZ(z);
		return isExit(gX, gZ);
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
				gl.glPushMatrix();
				gl.glScaled(1, maze[i][j], 1);
				gl.glTranslated(i * SQUARE_SIZE + SQUARE_SIZE / 2,
						SQUARE_SIZE / 2, j * SQUARE_SIZE + SQUARE_SIZE / 2);
				if (isWall(i, j))
					glut.glutSolidCube((float) SQUARE_SIZE);
				gl.glPopMatrix();
			}
		}
		paintSingleFloorTile(gl, MAZE_SIZE_X * SQUARE_SIZE, MAZE_SIZE_Z * SQUARE_SIZE); // Paint the floor.
		paintExit(gl);
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
        gl.glEnable(GL.GL_TEXTURE_2D);
		Texture earthTexture = null;
		try {
			String currentdir = System.getProperty("user.dir");
			String filename = "\\textures\\texture.jpg";
			
			filename = currentdir+filename;
			File file = new File(filename);
			System.out.println(filename);
            //InputStream stream = getClass().getResourceAsStream("texture.jpg");
            TextureData data = TextureIO.newTextureData(file, false, "jpg");
            earthTexture = TextureIO.newTexture(data);
        }
        catch (IOException exc) {
        	System.out.println("niet gevonden");
            exc.printStackTrace();
            System.exit(1);
        }
		
		
		// Setting the floor color and material.
        float[] rgba = {1f, 1f, 1f};
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);

        // Apply texture.
        if(earthTexture != null){
        	earthTexture.enable();
        	earthTexture.bind();
        }
		
		/*float wallColour[] = { 0.0f, 0.0f, 1.0f, 1.0f }; // The floor is blue.
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0); // Set the
																	// materials
																	// used by
																	// the
																	// floor.
	*/	
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
		
		paintFloorTexture(gl, size_x, size_z);
	}
	private void paintFloorTexture(GL gl, double size_x, double size_z){
		
	}
	
	private void paintExit(GL gl) {
		float wallColour[] = { 1.0f, 0.0f, 0.0f, 1.0f }; // The end tile is red
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0); 
		
		gl.glNormal3d(0, 1, 0);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d((MAZE_SIZE_X-2)*SQUARE_SIZE, 0.01, (MAZE_SIZE_Z-2)*SQUARE_SIZE);
		gl.glVertex3d((MAZE_SIZE_X-2)*SQUARE_SIZE, 0.01, MAZE_SIZE_Z*SQUARE_SIZE);
		gl.glVertex3d(MAZE_SIZE_X*SQUARE_SIZE, 0.01, MAZE_SIZE_Z*SQUARE_SIZE);
		gl.glVertex3d(MAZE_SIZE_X*SQUARE_SIZE, 0.01, (MAZE_SIZE_Z-2)*SQUARE_SIZE);
		gl.glEnd();
	}
}
