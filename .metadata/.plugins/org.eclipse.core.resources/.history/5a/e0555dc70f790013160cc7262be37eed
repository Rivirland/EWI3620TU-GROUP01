package menu;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;

import javax.media.opengl.*;



public class Quit {
	
	final byte MAINMENU = 0;
	final byte QUIT = 4;
	
	private int screenWidth = 800, screenHeight = 600;
	
	public Quit(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void setScreen(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void display(GLAutoDrawable drawable, GL gl){
		Teken.textDraw(drawable, gl, "Z@idm@n The G@me", 300f/1920f*screenWidth, 830f/1080f*screenHeight, 200f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Are you sure you want to quit the game?", 450f/1920f*screenWidth, 680f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Yes", 850f/1920f*screenWidth, 530f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "No", 865f/1920f*screenWidth, 380f/1080f*screenHeight, 80f/1080f*screenHeight);
		//Teken.textDraw(drawable, gl, "Quit Game", 750f/1920f*screenWidth, 230f/1080f*screenHeight, 80f/1080f*screenHeight);
		
		//ik beweeg over de 'YES' knop => deze licht rood op
				if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
					if (500f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 600f/1080f*screenHeight) {
						Teken.textDrawMetKleur(drawable, gl, "Yes", 850f/1920f*screenWidth, 530f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
					}
				}
		//ik beweeg over de 'NO' knop => deze licht rood op
				if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
					if (650f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 750f/1080f*screenHeight) {
						Teken.textDrawMetKleur(drawable, gl, "No", 865f/1920f*screenWidth, 380f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
					}
				}
	}

	public int mouseReleased(MouseEvent me) {
		int gamestate = QUIT;
		if (me.getButton()==1){	
			if (750f/1920f*screenWidth < me.getX() && me.getX() < 1170f/1920f*screenWidth) {
				if (450f/1080f*screenHeight < me.getY() && me.getY() < 550f/1080f*screenHeight) {
					// The 'YES' button is clicked
					System.exit(0);
				}
				else if (600f/1080f*screenHeight < me.getY() && me.getY() < 700f/1080f*screenHeight) {
					// The 'NO' button is clicked
					gamestate = MAINMENU;
				}
			}
		}
		return gamestate;
	}
}
