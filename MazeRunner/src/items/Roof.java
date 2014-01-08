package items;

import javax.media.opengl.GL;

import engine.GameObject;
import engine.Maze;
import engine.MazeRunner;

public class Roof extends Item {

	public int matrixX, matrixZ;
	private static double ROOF_LENGTH;
	private static double ROOF_HEIGHT;
	public double fallingSpeed;
	public boolean legal;

	public Roof(double x, double y, double z, int i, double WL, int row, int col) {
		super(x, y, z, i);
//		Maze maze = MazeRunner.level.getMaze(mazeID);
		ROOF_LENGTH = WL;
		ROOF_HEIGHT= WL/2;
		matrixX = row;
		matrixZ = col;
		fallingSpeed = 0.01;
		this.legal=true;
	}

	@Override
	public void display(GL gl) {
		gl.glDisable(GL.GL_CULL_FACE);

		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 7);
		gl.glBegin(GL.GL_TRIANGLE_FAN);

		gl.glTexCoord2d(0.5, 1.0);
		gl.glVertex3d(locationX+ROOF_HEIGHT, locationY + 2, locationZ+ROOF_HEIGHT);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(locationX+ROOF_LENGTH+0.25, locationY, locationZ+ROOF_LENGTH+0.25);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(locationX-0.25, locationY, locationZ+ROOF_LENGTH+0.25);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(locationX-0.25, locationY, locationZ-0.25);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(locationX+ROOF_LENGTH+0.25, locationY, locationZ-0.25);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(locationX+ROOF_LENGTH+0.25, locationY, locationZ+ROOF_LENGTH+0.25);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glEnd();
		gl.glEnable(GL.GL_CULL_FACE);
	}

	public static void drawRoof(GL gl) {
		gl.glDisable(GL.GL_CULL_FACE);

		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 4);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 7);
		gl.glBegin(GL.GL_TRIANGLE_FAN);

		gl.glTexCoord2d(0.5, 1.0);
		gl.glVertex3d(0+ROOF_HEIGHT, 0 + 2, 0+ROOF_HEIGHT);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(0+ROOF_LENGTH, 0, 0+ROOF_LENGTH);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(0, 0, 0+ROOF_LENGTH);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(0, 0, 0);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(0+ROOF_LENGTH, 0, 0);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(0+ROOF_LENGTH, 0, 0+ROOF_LENGTH);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glEnd();
		gl.glEnable(GL.GL_CULL_FACE);
	}
	
	@Override
	public double getGlobalX() {
		// TODO Auto-generated method stub
		return locationX;
	}

	@Override
	public double getGlobalY() {
		// TODO Auto-generated method stub
		return locationY;
	}

	@Override
	public double getGlobalZ() {
		// TODO Auto-generated method stub
		return locationZ;
	}

	@Override
	public boolean touches(GameObject object) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isOnWalls() {
		Maze maze = MazeRunner.level.getMaze(mazeID);
		
		int nrOfCol=0;
		int nrOfWalls=0;
		if(maze.maze[matrixX - 1][matrixZ - 1]*Maze.ITEM_HEIGHT + maze.getMazeY() == locationY){
			nrOfCol++;
		}
		if(maze.maze[matrixX - 1][ matrixZ + 1]*Maze.ITEM_HEIGHT + maze.getMazeY() == locationY){
			nrOfCol++;
		}
		if(maze.maze[matrixX + 1][matrixZ - 1]*Maze.ITEM_HEIGHT + maze.getMazeY() == locationY){
			nrOfCol++;
		}
		if(maze.maze[matrixX + 1][matrixZ + 1]*Maze.ITEM_HEIGHT + maze.getMazeY() == locationY){
			nrOfCol++;
		}
		if(maze.maze[matrixX + 1][matrixZ]*Maze.ITEM_HEIGHT + maze.getMazeY() == locationY){
			nrOfWalls++;
		}
		if(maze.maze[matrixX - 1][matrixZ]*Maze.ITEM_HEIGHT + maze.getMazeY() == locationY){
			nrOfWalls++;
		}
		if(maze.maze[matrixX][matrixZ + 1]*Maze.ITEM_HEIGHT + maze.getMazeY() == locationY){
			nrOfWalls++;
		}
		if(maze.maze[matrixX][matrixZ - 1]*Maze.ITEM_HEIGHT + maze.getMazeY() == locationY){
			nrOfWalls++;
		}
		return (nrOfCol>2 || nrOfWalls>1);
	}

	public void setLegal(boolean b) {
		this.legal=b;
		
	}
	public boolean getLegal(){
		return this.legal;
	}
	
	public static double getRoofHeight(){
		return ROOF_HEIGHT;
	}
	
	public static double getRoofLength(){
		return ROOF_LENGTH;
	}
}
