package engine;

import javax.media.opengl.GL;
//This class gives us an easy way to switch between drawing 2D and 3D. This is used for example when drawing the minimap over a 3D scene.
public class ChangeGL {

	public void GLTexture(GL gl) {
		gl.glEnable(GL.GL_TEXTURE_2D);
	}

	public static void GLto3D(GL gl) {
		// gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_LIGHT0);
	}

	public static void GLto2D(GL gl) {
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glDisable(GL.GL_LIGHT0);

	}

	public static void GLtoColoredItem(GL gl) {
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_LIGHTING);
	}

	public static void GLtoTexturedItem(GL gl) {
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL.GL_LIGHTING);
	}

}