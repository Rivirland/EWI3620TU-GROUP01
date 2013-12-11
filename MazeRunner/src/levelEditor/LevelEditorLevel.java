package levelEditor;

import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import menu.KiesFileUitBrowser;

public class LevelEditorLevel {
	
	private String name;
	private int[][] gebouwen;
	private int[][] textures;
	
	public LevelEditorLevel(String name, int[][] gebouwen, int[][] textures){
		this.setName(name);
		this.setGebouwen(gebouwen);
		this.setTextures(textures);
	}
	
	public static LevelEditorLevel readLevel(String naam, String filename) throws FileNotFoundException{
		return new LevelEditorLevel(naam, readGebouwen(filename), readTextures(filename));
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
	
	public void saveAs() throws FileNotFoundException{
		//PrintWriter bestand = new PrintWriter("C:\\Users\\Martijn\\Dropbox\\EWI3620TU Minorproject SOT Groep 01\\Level1_1_l.txt");
		KiesFileUitBrowser kfub = new KiesFileUitBrowser();
		String currentdir = System.getProperty("user.dir");
		String filename = kfub.saveFile(new Frame(), "Save level as...", currentdir + "\\levels\\", "*");
		//als de bestandsnaam al eindigt op .txt , knip dat er dan af
		if (filename.substring(filename.length()-4, filename.length()).equals(".txt")){
			filename = filename.substring(0, filename.length()-4);
		}
		setName(filename);
		PrintWriter bestand = new PrintWriter(currentdir + "\\levels\\" + filename + ".txt");
		bestand.println("0,0,0,");
		for (int i = 0; i != gebouwen.length; i++){
			for (int j = 0; j!=gebouwen[0].length; j++){
				bestand.print(gebouwen[i][j] + ",");
			}
			bestand.println();
		}
		bestand.close();

		PrintWriter bestand2 = new PrintWriter(currentdir + "\\levels\\" + filename + "_t.txt");
		for (int i = 0; i != textures.length; i++){
			for (int j = 0; j!=textures[0].length; j++){
				bestand2.print(textures[i][j] + ",");
			}
			bestand2.println();
		}
		bestand2.close();
	}
	
	public void open() throws FileNotFoundException {
		KiesFileUitBrowser kfub = new KiesFileUitBrowser();
		String currentdir = System.getProperty("user.dir");
		String filename = kfub.loadFile(new Frame(), "Open level...", currentdir + "\\levels\\", "*.txt");
		name = filename.substring(0, filename.length()-4);
		filename = currentdir + "\\levels\\" + filename;
		System.out.println(filename);
		gebouwen = readGebouwen(filename);
		textures = readTextures(filename);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
