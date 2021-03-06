package menu;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

import javax.media.opengl.*;

import levelEditor.LevelEditor;
import levelEditor.LevelEditorWorld;

public class LevelMenu {
	
	final byte MAINMENU = 0;
	final byte LEVELMENU = 2;
	final byte LEVELEDITOR = 6;
	final byte LOADLEVEL = 10;
	
	private int screenWidth, screenHeight;
	
	public LevelMenu(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void setScreen(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void display(GLAutoDrawable drawable, GL gl){
		Main.drawMenuBackground3buttons(gl, screenWidth, screenHeight);

		Teken.startText(drawable, "Arial", 60);
		Teken.endText(60);
//		Teken.textDraw(drawable, gl, "Z@idm@n The G@me", 300f/1920f*screenWidth, 830f/1080f*screenHeight, 90);
		if ((750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth)
				&& (350f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 450f / 1080f * screenHeight)) {
			Teken.textDrawMetKleur(drawable, gl, "New World", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60, 0.4f, 0.75f, 1f);
		} else {
			Teken.textDraw(gl, "New World", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60);
		}

		// ik beweeg over de tweede knop => deze licht rood op
		if ((750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth)
				&& (500f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 600f / 1080f * screenHeight)) {
			Teken.textDrawMetKleur(drawable, gl, "Load World", 750f / 1920f * screenWidth, 530f / 1080f * screenHeight, 60, 0.4f, 0.75f, 1f);
		} else {
			Teken.textDraw(gl, "Load World", 750f / 1920f * screenWidth, 530f / 1080f * screenHeight, 60);
		}
		// ik beweeg over de derde knop => deze licht rood op
		if (750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth
				&& (650f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 750f / 1080f * screenHeight)) {
			Teken.textDrawMetKleur(drawable, gl, "Back", 750f / 1920f * screenWidth, 380f / 1080f * screenHeight, 60, 0.4f, 0.75f, 1f);
		} else {
			Teken.textDraw(gl, "Back", 750f / 1920f * screenWidth, 380f / 1080f * screenHeight, 60);
		}
	}

	public int MouseReleased(MouseEvent me){
		int gamestate = LEVELMENU;
		if (me.getButton()==1){	
			if (750f/1920f*screenWidth < me.getX() && me.getX() < 1170f/1920f*screenWidth) {
				if (300f/1080f*screenHeight < me.getY() && me.getY() < 400f/1080f*screenHeight) {
					// The first button is clicked
					gamestate = LEVELEDITOR;
				}
				else if (450f/1080f*screenHeight < me.getY() && me.getY() < 550f/1080f*screenHeight) {
					// The second button is clicked
//					gamestate = LOADLEVEL;
					

//					if (Main.currentstate != gamestate) {
						if (!Main.selectedL) {
							//TODO: dit zichtbaar maken!
							Main.selectedL = true;
							KiesFileUitBrowser kfub2 = new KiesFileUitBrowser();
							String currentdir2 = System.getProperty("user.dir");
							String filename2 = kfub2.loadFile(new Frame(), "Open world...", currentdir2 + "\\worlds\\", "*.txt");
							if (filename2 == null) {
								filename2 = "world.txt";
							}
							filename2 = currentdir2 + "\\worlds\\" + filename2;
							System.out.println(filename2);
							Main.loadLevelName = filename2;
						}
						gamestate = LOADLEVEL;
//					}
				}
				else if (600f/1080f*screenHeight < me.getY() && me.getY() < 700f/1080f*screenHeight) {
					// The third button is clicked
					gamestate = MAINMENU;
				}
			}
		}
		return gamestate;
	}
}