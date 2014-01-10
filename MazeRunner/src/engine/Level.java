package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

//A level consists of several mazes. In this class, we can import mazes from a .txt file and store them into an ArrayList.
//Because this class contains all the mazes, we also implemented the collision detection here.
public class Level {
	public static ArrayList<Maze> mazelist;
	// The amount of mazes.
	private int aantal;
	private String naam;
	public double minGlobalY;
	private int[] orderedMazes;

	// The constructor.
	public Level(String string) {
		this.mazelist = new ArrayList<Maze>();
		this.aantal = 0;
		minGlobalY = Double.MAX_VALUE;
		naam = string;
		leesLevels(string);
		orderedMazes = orderMazesOnHeight();
	}

	private int[] orderMazesOnHeight() {
		int[] res = new int[this.aantal];
		double[] temp = new double[this.aantal];
		for (int i = 0; i < this.aantal; i++){
			temp[i] = mazelist.get(i).mazeY;
		}
		Arrays.sort(temp);
		
		for(int i = 0; i < this.aantal; i++){
			double height = temp[this.aantal-i-1];
			for (int j = 0; j < this.aantal; j++){
				if(mazelist.get(j).mazeY == height && !contains(res,j)){
					res[i] = j;
				}
			}
		}
		System.out.println("Ordering mazes on height: " + Arrays.toString(res));
		return res;
	}

	private boolean contains(int[] res, int j) {
		for (int i = 0; i < res.length; i++){
			if(res[i]==j){
				return true;
			}
		}
		return false;
	}

	// Adds a maze toe the mazelist.
	public void voegToe(Maze maze) {
		mazelist.add(maze);
		aantal = aantal + 1;
	}

	public int getAantal() {
		return this.aantal;
	}

	public String getNaam() {
		return naam;
	}

	public ArrayList<Maze> getMazeList() {
		return this.mazelist;
	}

	public Maze getMaze(int i) {
		if (i != -1) {
			return this.mazelist.get(i);
		}
		return null;
	}

	// Reads all the mazes from .txt files. If the base name is level1, it adds
	// level1_1.txt, level1_2.txt etc until
	// such files do not exist anymore.
	public void leesLevels(String filename) {
		BufferedReader bufRdr = null;
		String currentdir = System.getProperty("user.dir");

		filename = currentdir + "\\worlds\\" + filename;
		// System.out.println(filename);
		File worldFile = new File(filename + ".txt");
		try {
			bufRdr = new BufferedReader(new FileReader(worldFile));
		} catch (FileNotFoundException e) {
			System.out.println("WorldFile not found!");
			e.printStackTrace();
		}

		int i = 1;
		String line = null;
		try {
			while ((line = bufRdr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int z = Integer.parseInt(st.nextToken());
				String m = st.nextToken();
				System.out.println("Level loaded: " + m + " x: " + x + " y: " + y + " z: " + z);
				if(y<minGlobalY){
					minGlobalY = y;
				}
				Maze maze = new Maze(m, i, x, y, z);
				this.voegToe(maze);
				i++;
			}
		} catch (IOException e) {
			System.out.println("Fout bij inlezen");
			e.printStackTrace();
		}
	}

	// Returns true if two objects are in the same maze, otherwise it returns
	// false.
	public int inSameMaze(GameObject object1, GameObject object2) {
		if (getCurrentMaze(object1) == getCurrentMaze(object2)) {
			return getCurrentMaze(object1);
		}
		return -1;
	}

	public int getCurrentMaze(GameObject object) {
		for (int i = 0; i < this.getAantal(); i++) {
			Maze maze = this.getMaze(orderedMazes[i]);
			// You check if you are actually in the maze
			if (object.locationX > maze.getMinX() && object.locationX < maze.getMaxX() && object.locationZ > maze.getMinZ() && object.locationZ < maze.getMaxZ() && object.locationY >= maze.getMazeY()
					&& object.locationY <= maze.getMazeY() + Maze.ITEM_HEIGHT * maze.maxHeight) {
				return orderedMazes[i];
			}
		}
		return -1;
	}

	// This methods detects collision.
	public boolean[] collides(GameObject object, double margin) {
		// If you do not implement a margin (or set it to 0), you can still look
		// through the walls
		boolean[] res = { false, false, false, false };
		int i = getCurrentMaze(object);
		if (i != -1) {
			Maze maze = mazelist.get(i);
			// You check if there is a wall a non-zero entry on your position.
			// We translate the
			// position into the corresponding matrix element in the maze and
			// also make sure
			// you do not collide with a roof, since you can walk under them.
			double x = object.locationX - maze.getMinX();
			double z = object.locationZ - maze.getMinZ();
			int newX0 = maze.coordToMatrixElement(x + margin);
			int newZ0 = maze.coordToMatrixElement(z);
			int newX1 = maze.coordToMatrixElement(x);
			int newZ1 = maze.coordToMatrixElement(z - margin);
			int newX2 = maze.coordToMatrixElement(x - margin);
			int newZ2 = maze.coordToMatrixElement(z);
			int newX3 = maze.coordToMatrixElement(x);
			int newZ3 = maze.coordToMatrixElement(z + margin);
			if (!(newX0 % 2 == 1 && newZ0 % 2 == 1) && (maze.getElementOnCoords(newX0, newZ0) > 0)) {
				// System.out.println("hier0");

				// Checks if it's a wall
				if (maze.getTextureElementOnCoords(newX0, newZ0) % 2 == 1) {
					res[0] = true;
				}// Checks if it's a door
				else if (maze.getTextureElementOnCoords(newX0, newZ0) % 2 == 0) {
					double globX = object.locationX + margin;
					double globZ = object.locationZ;
					double[] wallXZ = maze.MatrixElementToCoords(newX0, newZ0);
					// Wall in X-direction
					if (!(globZ > wallXZ[1] + ((Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2) && globZ < wallXZ[1] + Maze.WALL_LENGTH - (Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2)) {
						res[0] = true;
					}

				}
			}
			if (!(newX1 % 2 == 1 && newZ1 % 2 == 1) && (maze.getElementOnCoords(newX1, newZ1) > 0)) {
				if (maze.getTextureElementOnCoords(newX1, newZ1) % 2 == 1) {
					res[1] = true;
				}// Checks if it's a door
				else if (maze.getTextureElementOnCoords(newX1, newZ1) % 2 == 0) {

					double globX = object.locationX;
					double globZ = object.locationZ - margin;
					double[] wallXZ = maze.MatrixElementToCoords(newX1, newZ1);
					// System.out.println(globX + " " + globZ + ", " +
					// (wallXZ[0] + ((Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2))
					// + ", "
					// + (wallXZ[0] + Maze.WALL_LENGTH - (Maze.WALL_LENGTH -
					// Maze.DOOR_WIDTH) / 2));
					if (!(globX > wallXZ[0] + ((Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2) && globX < wallXZ[0] + Maze.WALL_LENGTH - (Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2)) {
						res[1] = true;
					}
				}
			}
			if (!(newX2 % 2 == 1 && newZ2 % 2 == 1) && (maze.getElementOnCoords(newX2, newZ2) > 0)) {
				if (maze.getTextureElementOnCoords(newX2, newZ2) % 2 == 1) {
					res[2] = true;
				}// Checks if it's a door
				else if (maze.getTextureElementOnCoords(newX2, newZ2) % 2 == 0) {
					double globX = object.locationX - margin;
					double globZ = object.locationZ;
					double[] wallXZ = maze.MatrixElementToCoords(newX2, newZ2);
					// System.out.println(wallXZ[0] + " " + wallXZ[1]);
					// Wall in X-direction
					if (!(globZ > wallXZ[1] + ((Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2) && globZ < wallXZ[1] + Maze.WALL_LENGTH - (Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2)) {
						res[2] = true;
					}
				}
			}
			if (!(newX3 % 2 == 1 && newZ3 % 2 == 1) && (maze.getElementOnCoords(newX3, newZ3) > 0)) {
				if (maze.getTextureElementOnCoords(newX3, newZ3) % 2 == 1) {
					res[3] = true;
				}// Checks if it's a door
				else if (maze.getTextureElementOnCoords(newX3, newZ3) % 2 == 0) {
					double globX = object.locationX;
					double globZ = object.locationZ + margin;
					double[] wallXZ = maze.MatrixElementToCoords(newX3, newZ3);
					// Wall in X-direction

					if (!(globX > wallXZ[0] + ((Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2) && globX < wallXZ[0] + Maze.WALL_LENGTH - (Maze.WALL_LENGTH - Maze.DOOR_WIDTH) / 2)) {
						res[3] = true;
					}

				}
			}
		}
		return res;
	}
}
