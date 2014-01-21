package engine;

import javax.media.opengl.GL;

public class ChangeGL {

//GLAutoDrawable drawable;
//GL gl;

public void GLTexture (GL gl){
	gl.glEnable(GL.GL_TEXTURE_2D);
}
	
public static void GLto3D (GL gl){
	//gl.glEnable(GL.GL_TEXTURE_2D);
	gl.glEnable(GL.GL_LIGHTING);
	gl.glEnable(GL.GL_CULL_FACE);
	gl.glEnable(GL.GL_DEPTH_TEST);
	gl.glEnable(GL.GL_LIGHT0);	
}


public static void GLto2D (GL gl){
	gl.glDisable(GL.GL_TEXTURE_2D);
	gl.glDisable(GL.GL_LIGHTING);
	gl.glDisable(GL.GL_CULL_FACE);
	gl.glDisable(GL.GL_DEPTH_TEST);
	gl.glDisable(GL.GL_LIGHT0);
	
}
public static void GLtoColoredItem(GL gl){
	gl.glColor3d(1, 1, 1);
	gl.glDisable(GL.GL_TEXTURE_2D);
	gl.glDisable(GL.GL_LIGHTING);
}
public static void GLtoTexturedItem(GL gl){
	gl.glColor3d(1,1,1);
	gl.glEnable(GL.GL_TEXTURE_2D);
	gl.glDisable(GL.GL_LIGHTING);
	float matAmbient[] = { 0.8f, 0.8f, 0.8f, 1.0f };
	gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, matAmbient, 0);
}

}