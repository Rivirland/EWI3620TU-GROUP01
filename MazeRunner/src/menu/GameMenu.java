package menu;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;

import javax.media.opengl.*;

import engine.MazeRunner;

public class GameMenu {

	private int screenWidth, screenHeight;

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
		Teken.plaatsTexture(gl, 100, 100, 200, 200, 19);
		Teken.textDraw(drawable, gl, "Z@idm@n The G@me", 300f / 1920f * screenWidth, 830f / 1080f * screenHeight, 90);
		Teken.textDraw(drawable, gl, "Resume Game", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60);
		Teken.textDraw(drawable, gl, "Load Game", 750f / 1920f * screenWidth, 530f / 1080f * screenHeight, 60);
		Teken.textDraw(drawable, gl, "Delete Game", 750f / 1920f * screenWidth, 380f / 1080f * screenHeight, 60);
		// Teken.textDraw(drawable, gl, "Quit Game", 750f/1920f*screenWidth,
		// 230f/1080f*screenHeight, 80f/1080f*screenHeight);

		// ik beweeg over de eerste knop => deze licht rood op
		if (750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth) {
			if (350f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 450f / 1080f * screenHeight) {
				Teken.textDrawMetKleur(drawable, gl, "Resume Game", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60, 1f, 0f, 0f);
			}
		}

		// ik beweeg over de tweede knop => deze licht rood op
		if (750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth) {
			if (500f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 600f / 1080f * screenHeight) {
				Teken.textDrawMetKleur(drawable, gl, "Load Game", 750f / 1920f * screenWidth, 530f / 1080f * screenHeight, 60, 1f, 0f, 0f);
			}
		}
		// ik beweeg over de derde knop => deze licht rood op
		if (750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth) {
			if (650f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 750f / 1080f * screenHeight) {
				Teken.textDrawMetKleur(drawable, gl, "Delete Game", 750f / 1920f * screenWidth, 380f / 1080f * screenHeight, 60, 1f, 0f, 0f);
			}
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
						System.out.println("You're not logged in");
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
						gamestate = LOADGAME;
					} else {
						System.out.println("You're not logged in");
					}
				} else if (600f / 1080f * screenHeight < me.getY() && me.getY() < 700f / 1080f * screenHeight) {
					// The third button is clicked
					gamestate = GAMEMENU;
				}
			}
		}
		return gamestate;
	}

}
