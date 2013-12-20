package levelEditor;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import menu.KiesFileUitBrowser;

public class LevelEditorLevel {
	
	private String name;
	public int[] location;
	private int[][] gebouwen;
	private int[][] textures;
	public ArrayList<double[]> itemlist;

	
	public LevelEditorLevel(int[] location, String name, int[][] gebouwen, int[][] textures, ArrayList<double[]> itemList){
		this.setLocation(location);
		this.setName(name);
		this.setGebouwen(gebouwen);
		this.setTextures(textures);
		this.setItemList(itemList);
	}
	
	public static LevelEditorLevel readLevel(String naam, int[] location, String filename) throws FileNotFoundException{

		return new LevelEditorLevel(location, naam, readGebouwen(filename), readTextures(filename), readObjects(filename));
	}
	
	public static int[] readLocation(String filename) throws FileNotFoundException{
		int[] location = new int[3];
		Scanner sc = new Scanner(new File(filename));
		sc.useDelimiter("\\s*,\\s*");
		location[0] = sc.nextInt();
		location[1] = sc.nextInt();
		location[2] = sc.nextInt();
		return location;
	}
	
	public static int[][] readGebouwen(String filename) throws FileNotFoundException{
		File file = new File(filename);
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\\s*,\\s*");
		//sc.nextLine(); ???vreemd
		
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
	
	public static ArrayList<double[]> readObjects(String filename){
		ArrayList<double[]> itemlist = new ArrayList<double[]>();
		
		BufferedReader bufRdrTex = null;
		try {
			bufRdrTex = new BufferedReader(new FileReader(new File(filename.substring(0, filename.length()-4) + "_o.txt")));
			String line = null;
			try {
				while ((line = bufRdrTex.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line, ",");
					double objectNumber = Double.parseDouble(st.nextToken());
					if (objectNumber == 1) {
						// Portal
						double[] portal = new double[6];
						portal[0] = objectNumber;
						portal[1] = Double.parseDouble(st.nextToken()); //objectX
						portal[2] = Double.parseDouble(st.nextToken()); //objectZ
						portal[3] = Double.parseDouble(st.nextToken()); //facing direction
						portal[4] = Double.parseDouble(st.nextToken()); //portalID
						portal[5] = Double.parseDouble(st.nextToken()); //portalConID
						itemlist.add(portal);
						continue;
					} else if (objectNumber == 2) {
						// EnemySpooky
						double[] enemyspooky = new double[3];
						enemyspooky[0] = objectNumber;
						enemyspooky[1] = Double.parseDouble(st.nextToken()); //objectX
						enemyspooky[2] = Double.parseDouble(st.nextToken()); //objectZ
						itemlist.add(enemyspooky);
					} else if (objectNumber == 3) {
						// EnemySmart
						double[] enemysmart = new double[3];
						enemysmart[0] = objectNumber;
						enemysmart[1] = Double.parseDouble(st.nextToken()); //objectX
						enemysmart[2] = Double.parseDouble(st.nextToken()); //objectZ
						itemlist.add(enemysmart);
					} else if (objectNumber == 4) {
						// Bullets
						double[] bullets = new double[4];
						bullets[0] = objectNumber;
						bullets[1] = Double.parseDouble(st.nextToken()); //objectX
						bullets[2] = Double.parseDouble(st.nextToken()); //objectZ
						bullets[3] = Integer.parseInt(st.nextToken()); //amount
						itemlist.add(bullets);
					} else if (objectNumber == 5) {
						// Trapholder
						double[] trapholder = new double[3];
						trapholder[0] = objectNumber;
						trapholder[1] = Double.parseDouble(st.nextToken()); //objectX
						trapholder[2] = Double.parseDouble(st.nextToken()); //objectZ
						itemlist.add(trapholder);
					} else if (objectNumber == 6){
						// Exit
						double[] exit = new double[3];
						exit[0] = objectNumber;
						exit[1] = Double.parseDouble(st.nextToken()); //objectX
						exit[2] = Double.parseDouble(st.nextToken()); //objectZ
						itemlist.add(exit);
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
		bestand.println();
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
		
		PrintWriter bestand3 = new PrintWriter(currentdir + "\\levels\\" + filename + "_o.txt");
		for (int item = 0; item != itemlist.size(); item++){
			for (int itemcontent = 0; itemcontent != itemlist.get(item).length; itemcontent++){
				bestand3.print(itemlist.get(item)[itemcontent] + ",");
			}
			bestand3.println();
		}
		bestand3.close();
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
		itemlist = readObjects(filename);
	}
	
	public int[] getLocation() {
		return location;
	}

	public void setLocation(int[] location) {
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
	
	public double[] getItem(int i){
		return itemlist.get(i);
	}
}
