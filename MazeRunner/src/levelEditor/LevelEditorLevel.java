package levelEditor;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

import engine.Maze;
import menu.KiesFileUitBrowser;

public class LevelEditorLevel {

	private String name;
	private static int rows, columns;
	public double[] location;
	private int[][] gebouwen;
	private int[][] textures;
	public ArrayList<double[]> itemlist;

	public LevelEditorLevel(double[] location, String name, int[][] gebouwen, int[][] textures, ArrayList<double[]> itemList) {
		this.setLocation(location);
		this.setName(name);
		this.setGebouwen(gebouwen);
		this.setTextures(textures);
		this.setItemList(itemList);
	}
	

	public static LevelEditorLevel readLevel(String naam, double[] location, String filename) throws FileNotFoundException {

		return new LevelEditorLevel(location, naam, readGebouwen(filename), readTextures(filename), readObjects(filename));
	}

	public static int[] readLocation(String filename) throws FileNotFoundException {
		int[] location = new int[3];
		Scanner sc = new Scanner(new File(filename));
		sc.useDelimiter("\\s*,\\s*");
		location[0] = sc.nextInt();
		location[1] = sc.nextInt();
		location[2] = sc.nextInt();
		return location;
	}

	public static int[][] readGebouwen(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\\s*,\\s*");
		// sc.nextLine(); TODO: ???vreemd

		// tel het aantal rijen en kolommen in de matrix
		rows = 0;
		columns = 0;
		String string = "";
		String line = sc.nextLine();
		while (!line.equals("t")) {
			string = line;
			line = sc.nextLine();
			rows++;
		}
		Scanner stringsc = new Scanner(string);
		stringsc.useDelimiter("\\s*,\\s*");
		while (stringsc.hasNext()) {
			stringsc.nextInt();
			columns++;
		}
		System.out.println("Rows: "+ rows + ", columns: " + columns);
		// lees de matrix
		Scanner sc2 = new Scanner(file);
		sc2.useDelimiter("\\s*,\\s*");
		// sc2.nextLine();
		int[][] res = new int[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				res[i][j] = sc2.nextInt();
				System.out.println(res[i][j]);
			}
		}
		Maze.printMatrix(res);
		return res;
	}

	public static int[][] readTextures(String filename) throws FileNotFoundException {
		File file = new File(filename.substring(0, filename.length() - 4) + ".txt");
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\\s*,\\s*");

		// tel het aantal rijen en kolommen in de matrix

		while(!sc.nextLine().equals("t")){}
		int[][] res = new int[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				res[i][j] = sc.nextInt();
			}
		}
		Maze.printMatrix(res);
		return res;
	}

	public static ArrayList<double[]> readObjects(String filename) {
		ArrayList<double[]> itemlist = new ArrayList<double[]>();

		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(new File(filename.substring(0, filename.length() - 4) + ".txt")));
			String line = null;
			try {
				while(!bufRdr.readLine().equals("o")){}
				while ((line = bufRdr.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line, ",");
					double objectNumber = Double.parseDouble(st.nextToken());
					if (objectNumber == 129) {
						// Portal
						double[] portal = new double[4];
						portal[0] = objectNumber;
						portal[1] = Double.parseDouble(st.nextToken()); // objectX
						portal[2] = Double.parseDouble(st.nextToken()); // objectZ
						portal[3] = Double.parseDouble(st.nextToken()); // facing direction
						itemlist.add(portal);
						continue;
					} else if (objectNumber == 229) {
						// EnemySpooky
						double[] enemyspooky = new double[3];
						enemyspooky[0] = objectNumber;
						enemyspooky[1] = Double.parseDouble(st.nextToken()); // objectX
						enemyspooky[2] = Double.parseDouble(st.nextToken()); // objectZ
						itemlist.add(enemyspooky);
					} else if (objectNumber == 130) {
						// EnemySmart
						double[] enemysmart = new double[3];
						enemysmart[0] = objectNumber;
						enemysmart[1] = Double.parseDouble(st.nextToken()); // objectX
						enemysmart[2] = Double.parseDouble(st.nextToken()); // objectZ
						itemlist.add(enemysmart);
					} else if (objectNumber == 230) {
						// Bullets
						double[] bullets = new double[4];
						bullets[0] = objectNumber;
						bullets[1] = Double.parseDouble(st.nextToken()); // objectX
						bullets[2] = Double.parseDouble(st.nextToken()); // objectZ
						bullets[3] = Double.parseDouble(st.nextToken()); // amount
						itemlist.add(bullets);
					} else if (objectNumber == 131) {
						// Trapholder
						double[] trapholder = new double[3];
						trapholder[0] = objectNumber;
						trapholder[1] = Double.parseDouble(st.nextToken()); // objectX
						trapholder[2] = Double.parseDouble(st.nextToken()); // objectZ
						itemlist.add(trapholder);
					} else if (objectNumber == 231) {
						// Exit
						double[] exit = new double[3];
						exit[0] = objectNumber;
						exit[1] = Double.parseDouble(st.nextToken()); // objectX
						exit[2] = Double.parseDouble(st.nextToken()); // objectZ
						itemlist.add(exit);
					} else if (objectNumber == 132) {
						// Starting Point
						double[] start = new double[4];
						start[0] = objectNumber;
						start[1] = Double.parseDouble(st.nextToken()); // objectX
						start[2] = Double.parseDouble(st.nextToken()); // objectZ
						start[3] = Double.parseDouble(st.nextToken()); // horAngle
						itemlist.add(start);
					}
				}
			} catch (IOException e) {
				System.out.println("Fout in readObjects");
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return itemlist;
	}

	public int countPortals(ArrayList<double[]> list) {
		int res = 0;
		for (int i = 0; i < list.size(); i++) {
			double[] item = list.get(i);
			if (item[0] == 129) {
				res++;
			}
		}
		return res;
	}

	public void saveAs() throws FileNotFoundException {
		if (countPortals(itemlist) != 2) {
			LevelEditor.setErrMsg("You need exactly two portals in a level!");
			return;
		}
		// PrintWriter bestand = new
		// PrintWriter("C:\\Users\\Martijn\\Dropbox\\EWI3620TU Minorproject SOT Groep 01\\Level1_1_l.txt");
		KiesFileUitBrowser kfub = new KiesFileUitBrowser();
		String currentdir = System.getProperty("user.dir");
		String filename = kfub.saveFile(new Frame(), "Save level as...", currentdir + "\\levels\\", "*");
		if (filename == null) {
			filename = "basename";
		}
		// als de bestandsnaam al eindigt op .txt , knip dat er dan af
		if (filename.length() > 4 && filename.substring(filename.length() - 4, filename.length()).equals(".txt")) {
			filename = filename.substring(0, filename.length() - 4);
		}
		setName(filename);

		saveAs(filename);
//		PrintWriter bestand2 = new PrintWriter(currentdir + "\\levels\\" + filename + "_t.txt");
//		for (int i = 0; i != textures.length; i++) {
//			for (int j = 0; j != textures[0].length; j++) {
//				bestand2.print(textures[i][j] + ",");
//			}
//			bestand2.println();
//		}
//		bestand2.close();
//
//		PrintWriter bestand3 = new PrintWriter(currentdir + "\\levels\\" + filename + "_o.txt");
//		for (int item = 0; item != itemlist.size(); item++) {
//			for (int itemcontent = 0; itemcontent != itemlist.get(item).length; itemcontent++) {
//				bestand3.print(itemlist.get(item)[itemcontent] + ",");
//			}
//			bestand3.println();
//		}
//		bestand3.close();
	}
	
	public void saveAs(String filename) throws FileNotFoundException {
		if (countPortals(itemlist) != 2) {
			LevelEditor.setErrMsg("You need exactly two portals in a level!");
			return;
		}
		String currentdir = System.getProperty("user.dir");
		PrintWriter bestand = new PrintWriter(currentdir + "\\levels\\" + filename + ".txt");
		for (int i = 0; i != gebouwen.length; i++) {
			for (int j = 0; j != gebouwen[0].length; j++) {
				bestand.print(gebouwen[i][j] + ",");
			}
			bestand.println("");
		}
		bestand.println("t");
		for (int i = 0; i != textures.length; i++) {
			for (int j = 0; j != textures[0].length; j++) {
				bestand.print(textures[i][j] + ",");
			}
			bestand.println("");
		}
		bestand.println("o");
		for (int item = 0; item != itemlist.size(); item++) {
			for (int itemcontent = 0; itemcontent != itemlist.get(item).length; itemcontent++) {
				bestand.print(itemlist.get(item)[itemcontent] + ",");
			}
			bestand.println();
		}
		bestand.close();	}

	public void open() throws FileNotFoundException {
		KiesFileUitBrowser kfub = new KiesFileUitBrowser();
		String currentdir = System.getProperty("user.dir");
		String filename = kfub.loadFile(new Frame(), "Open level...", currentdir + "\\levels\\", "*.txt");
		if(filename == null){
			filename = "standardmaze.txt";
		}
		 
		name = filename.substring(0, filename.length() - 4);
		filename = currentdir + "\\levels\\" + filename;
		System.out.println(filename);
		gebouwen = readGebouwen(filename);
		textures = readTextures(filename);
		itemlist = readObjects(filename);
	}
	
	public void open(String filename) throws FileNotFoundException {
		String currentdir = System.getProperty("user.dir");
		name = filename.substring(0, filename.length() - 4);
		filename = currentdir + "\\levels\\" + filename;
		System.out.println(filename);
		gebouwen = readGebouwen(filename);
		textures = readTextures(filename);
		itemlist = readObjects(filename);
	}

	public double[] getLocation() {
		return location;
	}

	public void setLocation(double[] location) {
		this.location = location;
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

	public ArrayList<double[]> getItemList() {
		return itemlist;
	}

	public void setItemList(ArrayList<double[]> itemlist) {
		this.itemlist = itemlist;
	}

	public double[] getItem(int i) {
		return itemlist.get(i);
	}
}
