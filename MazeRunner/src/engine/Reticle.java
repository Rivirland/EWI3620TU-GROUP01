package engine;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import menu.Teken;



public class Reticle {
	
	private static int screenWidth;
	private static int screenHeight;
	
	public static void reticle(GL gl) {
		screenWidth=MazeRunner.screenWidth;
		screenHeight=MazeRunner.screenHeight;
			GLU glu = new GLU();
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glPushMatrix();
			gl.glLoadIdentity();

			glu.gluOrtho2D(0, MazeRunner.screenWidth, 0, MazeRunner.screenHeight);
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glDisable(GL.GL_LIGHTING);
			gl.glDisable(GL.GL_TEXTURE_2D);
			// gl.glDisable(GL.GL_DEPTH_TEST);
			
			drawReticle(gl);
			
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			glu.gluPerspective(60,
					MazeRunner.screenWidth / MazeRunner.screenHeight, .1, 200);
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glEnable(GL.GL_LIGHTING);
			gl.glEnable(GL.GL_TEXTURE_2D);
			// gl.glEnable(GL.GL_DEPTH_TEST);

			// gl.glLoadIdentity();
			gl.glPopMatrix();
		}
	

private static void drawReticle(GL gl){
	
	gl.glColor3d(1, 1, 1);
	Teken.rechthoek(gl, screenWidth/2-100, screenHeight/2-100, screenWidth/2-100, screenHeight/2+100);
	gl.glClearColor(0, 0, 0, 0);
	
	
}
}