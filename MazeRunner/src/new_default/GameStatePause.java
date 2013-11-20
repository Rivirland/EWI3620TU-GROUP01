package new_default;


import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class GameStatePause implements GameState {

	@Override
	public void doAction(GLAutoDrawable drawable) {
	     
    	MazeRunner.earthTexture.enable();
    	MazeRunner.earthTexture.bind();
//    	System.out.println("hoi");
		GL gl = drawable.getGL();
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBegin (GL.GL_QUADS);
		gl.glTexCoord2d(0.0,0.0);
		gl.glVertex3d(0, 0, 0); 
		gl.glTexCoord2d(1.0,0.0);
		gl.glVertex3d(0, 0, 25);
		gl.glTexCoord2d(0.0,1.0);
		gl.glVertex3d(25, 0, 25);
		gl.glTexCoord2d(1.0,1.0);
		gl.glVertex3d(25, 0, 0);
		gl.glEnd();

	}

	@Override
	public String getStringOfState() {
		return "pause";
	}
}
