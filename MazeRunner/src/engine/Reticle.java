package engine;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

public class Reticle {
	public static void reticle(GL gl) {
		GLU glu = new GLU();
		gl.glLoadIdentity();
		gl.glPushMatrix();
		  
	    // Reset and transform the matrix.
	    gl.glLoadIdentity();
	    
	    double vrpSkyBoxX = -Math.sin( Math.PI * MazeRunner.player.getHorAngle() / 180 ) * Math.cos( Math.PI * MazeRunner.player.getVerAngle() / 180 );
	    double vrpSkyBoxY = Math.sin( Math.PI * MazeRunner.player.getVerAngle() / 180 );
	    double vrpSkyBoxZ = -Math.cos( Math.PI * MazeRunner.player.getHorAngle() / 180 ) * Math.cos( Math.PI * MazeRunner.player.getVerAngle() / 180 );
	    glu.gluLookAt(0, 0, 0,vrpSkyBoxX, vrpSkyBoxY, vrpSkyBoxZ, 0,1,0);
//	    gl.glRotated(-Math.sin( Math.PI * MazeRunner.player.getHorAngle() / 180 ) * Math.cos( Math.PI * MazeRunner.player.getVerAngle() / 180 ),1,0,0);
//	    gl.glRotated(Math.sin( Math.PI * MazeRunner.player.getVerAngle() / 180 ),0,1,0);
//	    gl.glRotated(-Math.cos( Math.PI * MazeRunner.player.getHorAngle() / 180 ) * Math.cos( Math.PI * MazeRunner.player.getVerAngle() / 180 ),0,0,1);

	    // Enable/Disable features
	    gl.glEnable(GL.GL_TEXTURE_2D);
	    gl.glDisable(GL.GL_CULL_FACE);
	    gl.glDisable(GL.GL_DEPTH_TEST);
	    gl.glDisable(GL.GL_LIGHTING);
	    gl.glDisable(GL.GL_BLEND);
	 
	}
}
