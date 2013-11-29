package menu;

import java.awt.event.MouseEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class MainMenu {
	
	final byte MAINMENU = 0;
	final byte GAMEMENU = 1;
	final byte LEVELMENU = 2;
	final byte SETTINGS = 3;
	final byte QUIT = 4;
	
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
		Teken.textDraw(drawable, gl, "Z@idm@n The G@me", 300f/1920f*screenWidth, 830f/1080f*screenHeight, 200f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Start Game", 750f/1920f*screenWidth, 680f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Level Editor", 750f/1920f*screenWidth, 530f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Settings", 750f/1920f*screenWidth, 380f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Quit Game", 750f/1920f*screenWidth, 230f/1080f*screenHeight, 80f/1080f*screenHeight);
		
		
	}
	
	public int mouseReleased(MouseEvent me) {
		int gamestate = 0;
		if (me.getButton()==1){	
			if (750f/1920f*screenWidth < me.getX() && me.getX() < 1170f/1920f*screenWidth) {
				if (300f/1080f*screenHeight < me.getY() && me.getY() < 400f/1080f*screenHeight) {
					// The first button is clicked
					gamestate = GAMEMENU;
				}
				else if (450f/1080f*screenHeight < me.getY() && me.getY() < 550f/1080f*screenHeight) {
					// The second button is clicked
					gamestate = 2;
				}
				else if (600f/1080f*screenHeight < me.getY() && me.getY() < 700f/1080f*screenHeight) {
					// The third button is clicked
					gamestate = 3;
				}
				else if (750f/1080f*screenHeight < me.getY() && me.getY() < 850f/1080f*screenHeight) {
					// The fourth button is clicked
					gamestate = 4;
				}
			}
		}
		return gamestate;
	}
}