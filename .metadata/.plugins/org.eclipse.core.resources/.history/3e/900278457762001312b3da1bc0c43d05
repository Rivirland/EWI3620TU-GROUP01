package levelEditor;

import java.io.*;
import java.util.*;

public class LevelEditorWorld {
	private ArrayList<LevelEditorLevel> levels;
	
	
	public LevelEditorWorld(){
		levels = new ArrayList<LevelEditorLevel>();
	}
	
	public static LevelEditorWorld readWorld(String filename) throws FileNotFoundException{
		LevelEditorWorld lijst = new LevelEditorWorld();
		Scanner scannames = new Scanner(new File(filename));
		String currentdir = System.getProperty("user.dir");
		if (scannames.hasNext()){
			lijst.levels.add(LevelEditorLevel.readLevel(currentdir + "\\levels\\" + scannames.next() + ".txt"));
			System.out.println("added");
		}
		return lijst;
	}
	
	public LevelEditorLevel get(int i){
		return levels.get(i);
	}
}
