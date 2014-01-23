package engine;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import menu.Teken;
//This method is a lot like the minimap, but this draws the crosshair instead.
public class Reticle {

	private static int screenWidth=MazeRunner.screenWidth;;
	private static int screenHeight=MazeRunner.screenHeight;

	public static void display(GL gl) {
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
		glu.gluPerspective(60, MazeRunner.screenWidth / MazeRunner.screenHeight, .1, 200);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_TEXTURE_2D);
		// gl.glEnable(GL.GL_DEPTH_TEST);

		// gl.glLoadIdentity();
		gl.glPopMatrix();
	}

	private static void drawReticle(GL gl) {
		gl.glLineWidth(2);
		gl.glColor3d(0.2f, 0.2f, 0.2f);
		Teken.lineOnScreen(gl, getScreenWidth() / 2f - 30, getScreenHeight() / 2f, getScreenWidth() / 2f - 10, getScreenHeight() / 2f);
		Teken.lineOnScreen(gl, getScreenWidth() / 2f + 30, getScreenHeight() / 2f, getScreenWidth() / 2f + 10, getScreenHeight() / 2f);
		Teken.lineOnScreen(gl, getScreenWidth() / 2f, getScreenHeight() / 2f - 30, getScreenWidth() / 2f, getScreenHeight() / 2f - 10);
		Teken.lineOnScreen(gl, getScreenWidth() / 2f, getScreenHeight() / 2f + 30, getScreenWidth() / 2f, getScreenHeight() / 2f + 10);
		gl.glClearColor(0, 0, 0, 0);

	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int screenWidth) {
		Reticle.screenWidth = screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int screenHeight) {
		Reticle.screenHeight = screenHeight;
	}
}