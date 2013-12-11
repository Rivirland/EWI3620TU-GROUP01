package menu;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.j2d.TextRenderer;

public class MainMenu {
	
	final byte MAINMENU = 0;
	final byte GAMEMENU = 1;
	final byte LEVELMENU = 2;
	final byte SETTINGS = 3;
	final byte QUIT = 4;
	
	TextRenderer renderer;
	
	private boolean StartGameEntered = false;
	
	private int screenWidth, screenHeight;
	
	public MainMenu(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void setScreen(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void display(GLAutoDrawable drawable, GL gl){
		
		renderer =Teken.startText(drawable, "Agency FB", 200f/1080f*screenHeight); 
		Teken.textDraw(gl, "Z@idm@n The G@me", 300f/1920f*screenWidth, 830f/1080f*screenHeight, renderer);
		Teken.endText(renderer);
		renderer =Teken.startText(drawable, "Agency FB", 80f/1080f*screenHeight);
		Teken.textDraw(  gl, "Start Game", 750f/1920f*screenWidth, 680f/1080f*screenHeight, renderer);
		Teken.textDraw(  gl, "Level Editor", 750f/1920f*screenWidth, 530f/1080f*screenHeight, renderer);
		Teken.textDraw(  gl, "Settings", 750f/1920f*screenWidth, 380f/1080f*screenHeight, renderer);
		Teken.textDraw(  gl, "Quit Game", 750f/1920f*screenWidth, 230f/1080f*screenHeight, renderer);
		Teken.endText(renderer);
		
		//ik beweeg over de eerste knop => deze licht rood op
				if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
					if (350f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 450f/1080f*screenHeight) {
						Teken.textDrawMetKleur(drawable, gl, "Start Game", 750f/1920f*screenWidth, 680f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
					}
				}
		
		//ik beweeg over de tweede knop => deze licht rood op
				if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
					if (500f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 600f/1080f*screenHeight) {
						Teken.textDrawMetKleur(drawable, gl, "Level Editor", 750f/1920f*screenWidth, 530f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
					}
				}
		//ik beweeg over de derde knop => deze licht rood op
				if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
					if (650f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 750f/1080f*screenHeight) {
						Teken.textDrawMetKleur(drawable, gl, "Settings", 750f/1920f*screenWidth, 380f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
					}
				}
		//ik beweeg over de vierde knop => deze licht rood op
				if (750f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f/1920f*screenWidth){
					if (800f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 900f/1080f*screenHeight) {
						Teken.textDrawMetKleur(drawable, gl, "Quit Game", 750f/1920f*screenWidth, 230f/1080f*screenHeight, 80f/1080f*screenHeight, 1f, 0f, 0f);	
					}
				}
				
				if (1820f/1920f*screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1920f/1920f*screenWidth){
					if (980f/1080f*screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 1080f/1080f*screenHeight) {
						Teken.textDraw(drawable, gl, "Z@IDM@N RULES!", 50f/1920f*screenWidth, 1000f/1080f*screenHeight, 80f/1080f*screenHeight);	
					}
				}

	}
	
	public int mouseReleased(MouseEvent me) {
		int gamestate = MAINMENU;
		if (me.getButton()==1){	
			if (750f/1920f*screenWidth < me.getX() && me.getX() < 1170f/1920f*screenWidth) {
				if (300f/1080f*screenHeight < me.getY() && me.getY() < 400f/1080f*screenHeight) {
					// The first button is clicked
					gamestate = GAMEMENU;
				}
				else if (450f/1080f*screenHeight < me.getY() && me.getY() < 550f/1080f*screenHeight) {
					// The second button is clicked
					gamestate = LEVELMENU;
				}
				else if (600f/1080f*screenHeight < me.getY() && me.getY() < 700f/1080f*screenHeight) {
					// The third button is clicked
					gamestate = SETTINGS;
				}
				else if (750f/1080f*screenHeight < me.getY() && me.getY() < 850f/1080f*screenHeight) {
					// The fourth button is clicked
					gamestate = QUIT;
				}
			}
		}
		return gamestate;
	}

}