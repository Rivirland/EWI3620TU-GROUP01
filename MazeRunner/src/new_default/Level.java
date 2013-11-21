package new_default;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;

//A level consists of several mazes. In this class, we can import mazes from a .txt file and store them into an ArrayList.
//Because this class contains all the mazes, we also implemented the collision detection here.
public class Level {
	private ArrayList<Maze> mazelist;
	// The amount of mazes.
	private int aantal;

	// The constructor.
	public Level(String string) {
		this.mazelist = new ArrayList<Maze>();
		this.aantal = 0;
		leesLevels(string);
	}

	// Adds a maze toe the mazelist.
	public void voegToe(Maze maze) {
		mazelist.add(maze);
		aantal = aantal + 1;
	}

	public int getAantal() {
		return this.aantal;
	}

	public Maze getMaze(int i) {
		return this.mazelist.get(i);
	}

	// Reads all the mazes from .txt files. If the base name is level1, it adds
	// level1_1.txt, level1_2.txt etc until
	// such files do not exist anymore.
	public void leesLevels(String filename) {
		String currentdir = System.getProperty("user.dir");

		filename = currentdir + "\\levels\\" + filename;

		int i = 1;
		boolean hasNext = true;
		while (hasNext) {
			hasNext = false;
			File file = new File(filename + "_" + i + ".txt");

			Maze maze = new Maze(file, i);
			this.voegToe(maze);
			File fileNext = new File(filename + "_" + (i + 1) + ".txt");
			if (fileNext.exists()) {
				hasNext = true;
			}
			i++;
		}
	}

	public int whichMazeIsThisObjectIn(GameObject object) {
		for (int i = 0; i < this.getAantal(); i++) {
			Maze maze = this.getMaze(i);
			// You check if you are actually in the maze
			if (object.locationX > maze.getMinX()
					&& object.locationX < maze.getMaxX()
					&& object.locationZ > maze.getMinZ()
					&& object.locationZ < maze.getMaxZ()
					&& object.locationY >= maze.getMazeY()
					&& object.locationY <= maze.getMazeY() + 5) {
				return i;
			}
		}
		return -1;
	}

	// This methods detects collision.
	public boolean collides(GameObject object) {
		// If you do not implement a margin (or set it to 0), you can still look
		// through the walls
		double margin = 0.2;
		int i = whichMazeIsThisObjectIn(object);
		if (i != -1) {
			Maze maze = mazelist.get(i);
			// You check if there is a wall a non-zero entry on your position.
			// We translate the
			// position into the corresponding matrix element in the maze and
			// also make sure
			// you do not collide with a roof, since you can walk under them.
			double x = object.locationX - maze.getMinX();
			double z = object.locationZ - maze.getMinZ();
			int newX1 = maze.coordToMatrixElement(x + margin);
			int newZ1 = maze.coordToMatrixElement(z);
			int newX2 = maze.coordToMatrixElement(x - margin);
			int newZ2 = maze.coordToMatrixElement(z);
			int newX3 = maze.coordToMatrixElement(x);
			int newZ3 = maze.coordToMatrixElement(z + margin);
			int newX4 = maze.coordToMatrixElement(x);
			int newZ4 = maze.coordToMatrixElement(z - margin);
			if ((!(newX1 % 2 == 1 && newZ1 % 2 == 1) && (maze.getCoords(newX1,
					newZ1) != 0))
					|| (!(newX2 % 2 == 1 && newZ2 % 2 == 1) && (maze.getCoords(
							newX2, newZ2) != 0))
					|| (!(newX3 % 2 == 1 && newZ3 % 2 == 1) && (maze.getCoords(
							newX3, newZ3) != 0))
					|| (!(newX4 % 2 == 1 && newZ4 % 2 == 1) && (maze.getCoords(
							newX4, newZ4) != 0))) {
				return true;
			}
		}
		return false;
	}
}
