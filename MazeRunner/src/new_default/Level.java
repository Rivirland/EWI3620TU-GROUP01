package new_default;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Level {
	private ArrayList<Maze> mazelist;
	private int aantal;

	public Level(String string) {
		this.mazelist = new ArrayList<Maze>();
		this.aantal = 0;
		leesLevels(string);
	}

	public void voegToe(Maze maze) {
		mazelist.add(maze);
		aantal = aantal + 1;
	}
	public int getAantal(){
		return this.aantal;
	}
	public Maze getMaze(int i){
		return this.mazelist.get(i);
	}

	public void leesLevels(String filename) {
		String currentdir = System.getProperty("user.dir");

		filename = currentdir + "\\levels\\" + filename;

		int i = 1;
		boolean hasNext = true;
		while(hasNext){
			hasNext = false;
			File file = new File(filename + "_" + i + ".txt");

			Maze maze = new Maze(file, i);
			this.voegToe(maze);
			File fileNext = new File(filename + "_" + (i+1) + ".txt");
			if(fileNext.exists()){
				hasNext = true;
			}
			i++;
		}
	}
	public boolean collides(GameObject object) {
		double margin = 0.2;
		for (int i = 0; i < this.getAantal(); i++) {
			Maze maze = this.getMaze(i);
			if (object.locationX > maze.getMinX() && object.locationX < maze.getMaxX()
					&& object.locationZ > maze.getMinZ() && object.locationZ < maze.getMaxZ()
					&& object.locationY >= maze.getMazeY()
					&& object.locationY <= maze.getMazeY() + 5) {
				// Let op dat je dus als je teleporteert naar maximaal mazeY + 5
				// gaat!
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
				if ((!(newX1 % 2 == 1 && newZ1 % 2 == 1) && (maze.getCoords(
						newX1, newZ1) != 0))
						|| (!(newX2 % 2 == 1 && newZ2 % 2 == 1) && (maze
								.getCoords(newX2, newZ2) != 0))
						|| (!(newX3 % 2 == 1 && newZ3 % 2 == 1) && (maze
								.getCoords(newX3, newZ3) != 0))
						|| (!(newX4 % 2 == 1 && newZ4 % 2 == 1) && (maze
								.getCoords(newX4, newZ4) != 0))) {
					return true;
				}
			}
		}
		return false;
	}
}
