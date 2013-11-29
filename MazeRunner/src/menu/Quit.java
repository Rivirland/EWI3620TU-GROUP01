package menu;

import javax.media.opengl.*;

public class Quit {
	
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
		Teken.textDraw(drawable, gl, "Are you sure you want to quit the game?", 250f/1920f*screenWidth, 680f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "Yes", 850f/1920f*screenWidth, 530f/1080f*screenHeight, 80f/1080f*screenHeight);
		Teken.textDraw(drawable, gl, "No", 850f/1920f*screenWidth, 380f/1080f*screenHeight, 80f/1080f*screenHeight);
		//Teken.textDraw(drawable, gl, "Quit Game", 750f/1920f*screenWidth, 230f/1080f*screenHeight, 80f/1080f*screenHeight);
	}

}
