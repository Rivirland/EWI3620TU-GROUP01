package menu;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;

import javax.media.opengl.*;

public class LevelMenu {
	
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
		Teken.textDraw(drawable, gl, "Z@idm@n The G@me", 300f/1920f*screenWidth, 830f/1080f*screenHeight, 200f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "New World", 750f/1920f*screenWidth, 680f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Load World", 750f/1920f*screenWidth, 530f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Delete World", 750f/1920f*screenWidth, 380f/1080f*screenHeight, 80f/1080f*screenHeight);
		//Teken.textDraw(drawable, gl, "Quit Game", 750f/1920f*screenWidth, 230f/1080f*screenHeight, 80f/1080f*screenHeight);
		
		//ik beweeg over de eerste knop => deze licht rood op
		if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
			if (350f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 450f/1080f*screenHeight) {
				Teken.textDrawMetKleur(drawable, gl, "New World", 750f/1920f*screenWidth, 680f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
			}
		}

		//ik beweeg over de tweede knop => deze licht rood op
		if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
			if (500f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 600f/1080f*screenHeight) {
				Teken.textDrawMetKleur(drawable, gl, "Load World", 750f/1920f*screenWidth, 530f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
			}
		}
		//ik beweeg over de derde knop => deze licht rood op
		if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
			if (650f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 750f/1080f*screenHeight) {
				Teken.textDrawMetKleur(drawable, gl, "Delete World", 750f/1920f*screenWidth, 380f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
			}
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
					gamestate = LOADLEVEL;
				}
				else if (600f/1080f*screenHeight < me.getY() && me.getY() < 700f/1080f*screenHeight) {
					// The third button is clicked
					gamestate = LEVELMENU;
				}
			}
		}
		return gamestate;
	}
}