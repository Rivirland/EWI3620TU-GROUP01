package menu;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;

import javax.media.opengl.*;

import engine.MazeRunner;

public class GameMenu {

	private int screenWidth, screenHeight;

	final byte MAINMENU = 0;
	final byte GAMEMENU = 1;
	final byte INGAME = 5;
	final byte LOADGAME = 8;

	public GameMenu(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void setScreen(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void display(GLAutoDrawable drawable, GL gl) {
		Main.drawMenuBackground3buttons(gl, screenWidth, screenHeight);
		Teken.startText(drawable, "Arial", 60);
		Teken.endText(60);

		if (Main.everplayed) {
			if (750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth
					&& (350f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 450f / 1080f * screenHeight)) {
				Teken.textDrawMetKleur(drawable, gl, "Resume Game", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60, 0.4f, 0.75f, 1f);
			} else {
				Teken.textDraw(gl, "Resume Game", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60);
			}
		}else{
			if (750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth
					&& (350f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 450f / 1080f * screenHeight)) {
				Teken.textDrawMetKleur(drawable, gl, "Start Game", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60, 0.4f, 0.75f, 1f);
			} else {
				Teken.textDraw(gl, "Start Game", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60);
			}
		}

		// ik beweeg over de tweede knop => deze licht rood op
		if ((750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth)
				&& (500f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 600f / 1080f * screenHeight)) {
			Teken.textDrawMetKleur(drawable, gl, "Load Game", 750f / 1920f * screenWidth, 530f / 1080f * screenHeight, 60, 0.4f, 0.75f, 1f);
		} else {
			Teken.textDraw(gl, "Load Game", 750f / 1920f * screenWidth, 530f / 1080f * screenHeight, 60);
		}
		// ik beweeg over de derde knop => deze licht rood op
		if (750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth
				&& (650f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 750f / 1080f * screenHeight)) {
			Teken.textDrawMetKleur(drawable, gl, "Back", 750f / 1920f * screenWidth, 380f / 1080f * screenHeight, 60, 0.4f, 0.75f, 1f);
		} else {
			Teken.textDraw(gl, "Back", 750f / 1920f * screenWidth, 380f / 1080f * screenHeight, 60);
		}

	}

	public int MouseReleased(MouseEvent me) {
		int gamestate = GAMEMENU;
		if (me.getButton() == 1) {
			if (750f / 1920f * screenWidth < me.getX() && me.getX() < 1170f / 1920f * screenWidth) {
				if (300f / 1080f * screenHeight < me.getY() && me.getY() < 400f / 1080f * screenHeight) {
					// The first button is clicked
					if (Main.loggedIn) {
						gamestate = INGAME;
					} else {
						Main.setMainErrorMessage("You need to log in before you can play");
					}
				} else if (450f / 1080f * screenHeight < me.getY() && me.getY() < 550f / 1080f * screenHeight) {

					// The second button is clicked
					if (Main.loggedIn) {
						MazeRunner.visibleObjects.clear();
						MazeRunner.portalList.clear();
						MazeRunner.enemyList.clear();
						MazeRunner.bulletList.clear();
						MazeRunner.roofList.clear();
						MazeRunner.playingTime = 0;
						if (!Main.selectedG) {
							Main.selectedG = true;
							KiesFileUitBrowser kfub = new KiesFileUitBrowser();
							String currentdir = System.getProperty("user.dir");
							String filename = kfub.loadFile(new Frame(), "Open...", currentdir + "\\worlds\\", "*.txt");
							// filename = currentdir + "\\levels\\" + filename;
							if (filename == null) {
								filename = "world.txt";
							}
							Main.loadGameName = filename.substring(0,filename.length()-4);
						}
						
						gamestate = LOADGAME;
					} else {
						Main.setMainErrorMessage("You need to log in before you can play");
					}
				} else if (600f / 1080f * screenHeight < me.getY() && me.getY() < 700f / 1080f * screenHeight) {
					// The third button is clicked
					gamestate = MAINMENU;
				}
			}
		}
		return gamestate;
	}

}
