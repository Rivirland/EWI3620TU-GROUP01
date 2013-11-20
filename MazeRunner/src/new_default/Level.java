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

			Maze maze = new Maze(file);
			this.voegToe(maze);
			File fileNext = new File(filename + "_" + (i+1) + ".txt");
			if(fileNext.exists()){
				hasNext = true;
			}
			i++;
		}
//		
//		Maze maze1 = new Maze(file1);
//		this.voegToe(maze1);
//		File file2 = new File(filename+"_2.txt");
//		Maze maze2 = new Maze(file2);
//		this.voegToe(maze2);
	}
}
