package levelEditor;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.io.*;
import java.util.*;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.KiesFileUitBrowser;
import menu.Teken;

public class LevelEditorWorld {
	private ArrayList<LevelEditorLevel> levels;
	private static int startingBullets, startingTraps;
	
	private boolean popup=false;
	
	public LevelEditorWorld(){
		levels = new ArrayList<LevelEditorLevel>();
	}
	
	public static LevelEditorWorld readWorld(String filename) throws FileNotFoundException{
		LevelEditorWorld lijst = new LevelEditorWorld();
		Scanner scannames = new Scanner(new File(filename));
		String currentdir = System.getProperty("user.dir");
		String str = scannames.next();
		StringTokenizer StrTok = new StringTokenizer(str, ",");
		startingBullets = Integer.parseInt(StrTok.nextToken());
		startingTraps = Integer.parseInt(StrTok.nextToken());
		while (scannames.hasNext()){
			String string = scannames.next();
			StringTokenizer st = new StringTokenizer(string, ",");
			int lx = Integer.parseInt(st.nextToken());
			int ly = Integer.parseInt(st.nextToken());
			int lz = Integer.parseInt(st.nextToken());
			String name = st.nextToken();
			lijst.levels.add(LevelEditorLevel.readLevel(name, new int[] {lx,ly,lz}, currentdir + "\\levels\\" + name + ".txt"));
			System.out.println(name + " added to list of levels");
		}
		scannames.close();
		return lijst;
	}
	
	public void drawLevelList(GLAutoDrawable drawable, GL gl, float xmin, float ymin, float xmax, float ymax, float screenWidth, float screenHeight, int selectedLevel){
		//System.out.println(MouseInfo.getPointerInfo().getLocation().getX());
		//Teken.tekenButton(gl, xmin, ymin, xmax, ymax);
		for (int i=levels.size()-1; i > -1 ; i--){
			//lijst
			if (selectedLevel == i){
				Teken.tekenButtonMetKleur(gl, xmin, ymax-(ymax-ymin)*(i+1)/20, xmax, ymax-(ymax-ymin)*i/20, 0.76f, 0.76f, 0.76f);
				gl.glColor3f(0.16f, 0.16f, 0.16f);
				Teken.kruis(gl, xmax-24f/1920f*screenWidth, ymax-((ymax-ymin)*(i+1)/20)+10f/1080f*screenHeight, xmax-10f/1920f*screenWidth, ymax-((ymax-ymin)*i/20)-10f/1080f*screenHeight);
			}
			else{
				Teken.tekenButton(gl, xmin, ymax-((ymax-ymin)*(i+1)/20), xmax, ymax-((ymax-ymin)*i/20));
				gl.glColor3f(0.76f, 0.76f, 0.76f);
				Teken.kruis(gl, xmax-24f/1920f*screenWidth, ymax-((ymax-ymin)*(i+1)/20)+10f/1080f*screenHeight, xmax-10f/1920f*screenWidth, ymax-((ymax-ymin)*i/20)-10f/1080f*screenHeight);
			}
			Teken.textDrawMetKleur(drawable, gl, levels.get(i).getName(), xmin, ymax-((ymax-ymin)*(i+1)/20), 30, 1f, 1f, 1f);
			
			//popup menu
			if (xmin < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < xmax){
				if (screenHeight - (ymax-(ymax-ymin)*i/20) < MouseInfo.getPointerInfo().getLocation().getY()-30f && MouseInfo.getPointerInfo().getLocation().getY()-30f < screenHeight-(ymax-(ymax-ymin)*(i+1)/20)) {
					popup = true;
				}
			}
			if (popup){
				if (xmin < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < xmax+41f/1920f*screenWidth){
					if (screenHeight - (ymax-(ymax-ymin)*i/20) < MouseInfo.getPointerInfo().getLocation().getY()-30f && MouseInfo.getPointerInfo().getLocation().getY()-30f < screenHeight-(ymax-(ymax-ymin)*(i+1)/20)) {
						Teken.tekenButtonMetKleur(gl, xmax, ymax-(ymax-ymin)*(i+1)/20, xmax+34f/1920f*screenWidth, ymax-(ymax-ymin)*i/20, 0.3f, 0.3f, 0.8f);
					}
				}
				else{
					popup=false;
				}
			}
			//gl.glColor3f(0f, 0f, 0f);
		}
	}
	
	public void addLevel(){
		levels.add(new LevelEditorLevel(LevelEditor.defaultLocation(), "nieuw", LevelEditor.defaultMatrix(), LevelEditor.defaultMatrix(), new ArrayList<double[]>()));
	}
	
	public void saveAs() throws FileNotFoundException{
		//PrintWriter bestand = new PrintWriter("C:\\Users\\Martijn\\Dropbox\\EWI3620TU Minorproject SOT Groep 01\\Level1_1_l.txt");
		KiesFileUitBrowser kfub = new KiesFileUitBrowser();
		String currentdir = System.getProperty("user.dir");
		String filename = kfub.saveFile(new Frame(), "Save world as...", currentdir + "\\worlds\\", "*");
		//als de bestandsnaam al eindigt op .txt , knip dat er dan af
		if (filename.substring(filename.length()-4, filename.length()).equals(".txt")){
			filename = filename.substring(0, filename.length()-4);
		}
		
		PrintWriter bestand = new PrintWriter(currentdir + "\\worlds\\" + filename + ".txt");
		bestand.println(startingBullets + "," + startingTraps + ",");
		for (int i = 0; i != levels.size(); i++){
			for(int j = 0; j < 3; j++){
				bestand.print(levels.get(i).location[j] + ",");
			}
			bestand.println(levels.get(i).getName());
		}
		bestand.close();
	}
	
	public int mouseReleased(float x, float y, float xmin, float ymin, float xmax, float ymax, int selectedLevel){
		for (int i=0; i < levels.size(); i++){
			if (xmin < x && x < xmax){
				if (ymax-((ymax-ymin)*(i+1)/20) < y && y < ymax-(ymax-ymin)*i/20){
					selectedLevel = i;
				}
			}
		}
		return selectedLevel;
	}
	
	//save level As
	public void mouseReleased2(float x, float y, float xmin, float ymin, float xmax, float ymax, float screenWidth, float screenHeight) throws FileNotFoundException{
		for (int i=0; i < levels.size(); i++){
			if (xmax < x && x < xmax+34f/1920f*screenWidth){
				if (ymax-((ymax-ymin)*(i+1)/20) < y && y < ymax-(ymax-ymin)*i/20){
					if (popup){
						levels.get(i).saveAs();
					}
				}
			}
		}
	}
	
	//remove level
	public boolean mouseReleased3(float x, float y, float xmin, float ymin, float xmax, float ymax, float screenWidth, float screenHeight){
		boolean remove = false;
		for (int i=0; i < levels.size(); i++){
			if (xmax-24f/1920f*screenWidth < x && x < xmax){
				if (ymax-((ymax-ymin)*(i+1)/20) < y && y < ymax-(ymax-ymin)*i/20){
					levels.remove(i);
					System.out.println("remove");
					remove = true;
				}
			}
		}
		return remove;
	}
	
	//open level
	public boolean mousePressed(float x, float y, float xmin, float ymin, float xmax, float ymax, float screenWidth, float screenHeight) throws FileNotFoundException{
		boolean open = false;
		for (int i=0; i < levels.size(); i++){
			if (xmin < x && x < xmax-24f/1920f*screenWidth){
				if (ymax-((ymax-ymin)*(i+1)/20) < y && y < ymax-(ymax-ymin)*i/20){
					levels.get(i).open();
					open = true;
				}
			}
		}
		return open;
	}
	
	public LevelEditorLevel get(int i){
		return levels.get(i);
	}

	public int getSize() {
		return levels.size();
	}

	public int getStartingBullets() {
		return startingBullets;
	}
	
	public int getStartingTraps() {
		return startingTraps;
	}

	public void setStartingBullets(int i) {
		startingBullets = i;
	}
	
	public void setStartingTraps(int i) {
		startingTraps = i;
	}


}
