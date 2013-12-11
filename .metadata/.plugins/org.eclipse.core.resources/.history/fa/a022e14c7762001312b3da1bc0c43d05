package levelEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LevelEditorLevel {
	
	private int[][] gebouwen;
	private int[][] textures;
	
	public LevelEditorLevel(int[][] gebouwen, int[][] textures){
		this.setGebouwen(gebouwen);
		this.setTextures(textures);
	}
	
	public static LevelEditorLevel readLevel(String filename) throws FileNotFoundException{
		return new LevelEditorLevel(readGebouwen(filename), readTextures(filename));
	}
	
	public static int[][] readGebouwen(String filename) throws FileNotFoundException{
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\\s*,\\s*");
		int x = sc.nextInt();
		int y = sc.nextInt();
		int z = sc.nextInt();
		
		//tel het aantal rijen en kolommen in de matrix
		int rows=-1;
		int columns=0;
		String string = "";
		while (sc.hasNextLine()){
			string = sc.nextLine();
			rows++;
		}
		Scanner stringsc = new Scanner(string);
		stringsc.useDelimiter("\\s*,\\s*");
		while (stringsc.hasNext()){
			stringsc.nextInt();
			columns++;
		}

		//lees de matrix
		Scanner sc2 = new Scanner(file);
		sc2.useDelimiter("\\s*,\\s*");
		sc2.nextLine();
		int[][] res = new int[rows][columns];
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				res[i][j] = sc2.nextInt();
			}
		}
		System.out.println("file ingelezen");
		return res;
	}
	
	public static int[][] readTextures(String filename) throws FileNotFoundException{
		File file = new File(filename.substring(0, filename.length()-4) + "_t.txt");
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\\s*,\\s*");
		
		//tel het aantal rijen en kolommen in de matrix
		int rows=0;
		int columns=0;
		String string = "";
		while (sc.hasNextLine()){
			string = sc.nextLine();
			rows++;
		}
		Scanner stringsc = new Scanner(string);
		stringsc.useDelimiter("\\s*,\\s*");
		while (stringsc.hasNext()){
			stringsc.nextInt();
			columns++;
		}

		//lees de matrix
		Scanner sc2 = new Scanner(file);
		sc2.useDelimiter("\\s*,\\s*");
		int[][] res = new int[rows][columns];
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				res[i][j] = sc2.nextInt();
			}
		}
		System.out.println("file ingelezen");
		return res;
	}

	public int[][] getTextures() {
		return textures;
	}

	public void setTextures(int[][] textures) {
		this.textures = textures;
	}

	public int[][] getGebouwen() {
		return gebouwen;
	}

	public void setGebouwen(int[][] gebouwen) {
		this.gebouwen = gebouwen;
	}
}
