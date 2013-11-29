package menu;

import javax.media.opengl.*;

public class LevelMenu {
	
	private int screenWidth = 800, screenHeight = 600;
	
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
		Teken.textDraw(drawable, gl, "New world", 750f/1920f*screenWidth, 680f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Load world", 750f/1920f*screenWidth, 530f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Delete world", 750f/1920f*screenWidth, 380f/1080f*screenHeight, 80f/1080f*screenHeight);
		//Teken.textDraw(drawable, gl, "Quit Game", 750f/1920f*screenWidth, 230f/1080f*screenHeight, 80f/1080f*screenHeight);
	}

}