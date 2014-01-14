package menu;

import javax.media.opengl.*;

public class Settings {
	
	private int screenWidth = 800, screenHeight = 600;
	
	public Settings(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void setScreen(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	public void display(GLAutoDrawable drawable, GL gl){
		Teken.textDraw(drawable, gl, "Z@idm@n The G@me", 300f/1920f*screenWidth, 830f/1080f*screenHeight, 90);
		Teken.textDraw(drawable, gl, "Difficulty: Levi (EASY)", 750f/1920f*screenWidth, 680f/1080f*screenHeight, 60);
		Teken.textDraw(drawable, gl, "Difficulty: Joris (MEDIUM)", 750f/1920f*screenWidth, 530f/1080f*screenHeight, 60);
		Teken.textDraw(drawable, gl, "Difficulty: Peter (HARD)", 750f/1920f*screenWidth, 380f/1080f*screenHeight, 60);
		Teken.textDraw(drawable, gl, "Difficulty: Martijn (IMPOSSIBLE)", 750f/1920f*screenWidth, 230f/1080f*screenHeight, 60);
	}

}
