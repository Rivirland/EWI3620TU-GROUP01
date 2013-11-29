import javax.media.opengl.GL;


public class MethodesVoorLevelEditor {
	private void rechthoek(GL gl, float x, float y, float x2, float y2) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x2, y);
		gl.glVertex2f(x2, y2);
		gl.glVertex2f(x, y2);
		gl.glEnd();
	}
	
	//achtergrondkleur
	//gl.glColor3f(0.34f, 0.11f, 0.13f);

	private void tekenLevelEditorAchtergrond(GL gl){
		
		//button1
		gl.glColor3f(0.16f, 0.16f, 0.16f);
		rechthoek(gl, 90f/1920f*screenWidth, 890f/1080f*screenHeight, 190f/1920f*screenWidth, 990f/1080f*screenHeight);
		
		//button2
		gl.glColor3f(0.16f, 0.16f, 0.16f);
		rechthoek(gl, 223f/1920f*screenWidth, 890f/1080f*screenHeight, 323f/1920f*screenWidth, 990f/1080f*screenHeight);
		
		//button3
		gl.glColor3f(0.16f, 0.16f, 0.16f);
		rechthoek(gl, 356f/1920f*screenWidth, 890f/1080f*screenHeight, 456f/1920f*screenWidth, 990f/1080f*screenHeight);
		
		//button4
		gl.glColor3f(0.16f, 0.16f, 0.16f);
		rechthoek(gl, 489f/1920f*screenWidth, 890f/1080f*screenHeight, 589f/1920f*screenWidth, 990f/1080f*screenHeight);
		
		//zwart onder de buttons
		gl.glColor3f(0f, 0f, 0f);
		rechthoek(gl, 90f/1920f*screenWidth, 90f/1080f*screenHeight, 589f/1920f*screenWidth, 860f/1080f*screenHeight);
		
		//grijs naast de buttons voor keuze van gridgrootte
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		rechthoek(gl, 622f/1920f*screenWidth, 890f/1080f*screenHeight, 737f/1920f*screenWidth, 990f/1080f*screenHeight);
		
		//grijs voor schuifbalk
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		rechthoek(gl, 659f/1920f*screenWidth, 90f/1080f*screenHeight, 700f/1920f*screenWidth, 860f/1080f*screenHeight);
		
		//grijs voor grid
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, 770f/1920f*screenWidth, 90f/1080f*screenHeight, 1830f/1920f*screenWidth, 990f/1080f*screenHeight);
		
	}
	
}
