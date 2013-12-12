package engine;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import menu.Teken;

public class Skybox {	
	public static void displaySkybox(GL gl) {
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
	 
	    
	    // Just in case we set all vertices to white.
	    gl.glColor4f(1,1,1,1);
	 
	    // Render the front quad
	    
	    Teken.drawCuboid(gl,-1,1,-1,1,-1,1,new int[] {8,9,10,11,12,13});
//	    gl.glBegin(GL.GL_QUADS);
//	        gl.glTexCoord2f(0, 0); gl.glVertex3f(  0.5f, -0.5f, -0.5f );
//	        gl.glTexCoord2f(1, 0); gl.glVertex3f( -0.5f, -0.5f, -0.5f );
//	        gl.glTexCoord2f(1, 1); gl.glVertex3f( -0.5f,  0.5f, -0.5f );
//	        gl.glTexCoord2f(0, 1); gl.glVertex3f(  0.5f,  0.5f, -0.5f );
//	    gl.glEnd();
//	 
//	    // Render the left quad
//	    gl.glBindTexture(GL.GL_TEXTURE_2D, 2);
//	    gl.glBegin(GL.GL_QUADS);
//	        gl.glTexCoord2f(0, 0); gl.glVertex3f(  0.5f, -0.5f,  0.5f );
//	        gl.glTexCoord2f(1, 0); gl.glVertex3f(  0.5f, -0.5f, -0.5f );
//	        gl.glTexCoord2f(1, 1); gl.glVertex3f(  0.5f,  0.5f, -0.5f );
//	        gl.glTexCoord2f(0, 1); gl.glVertex3f(  0.5f,  0.5f,  0.5f );
//	    gl.glEnd();
//	 
//	    // Render the back quad
//	    gl.glBindTexture(GL.GL_TEXTURE_2D, 3);
//	    gl.glBegin(GL.GL_QUADS);
//	        gl.glTexCoord2f(0, 0); gl.glVertex3f( -0.5f, -0.5f,  0.5f );
//	        gl.glTexCoord2f(1, 0); gl.glVertex3f(  0.5f, -0.5f,  0.5f );
//	        gl.glTexCoord2f(1, 1); gl.glVertex3f(  0.5f,  0.5f,  0.5f );
//	        gl.glTexCoord2f(0, 1); gl.glVertex3f( -0.5f,  0.5f,  0.5f );
//	 
//	    gl.glEnd();
//	 
//	    // Render the right quad
//	    gl.glBindTexture(GL.GL_TEXTURE_2D, 4);
//	        gl.glTexCoord2f(0, 0); gl.glVertex3f( 10f, -0.5f, -0.5f );
//	        gl.glTexCoord2f(1, 0); gl.glVertex3f( -0.5f, -0.5f,  0.5f );
//	        gl.glTexCoord2f(1, 1); gl.glVertex3f( -0.5f,  0.5f,  0.5f );
//	        gl.glTexCoord2f(0, 1); gl.glVertex3f( -0.5f,  0.5f, -0.5f );
//	    gl.glEnd();
//	 
//	    // Render the top quad
//	    gl.glBindTexture(GL.GL_TEXTURE_2D, 5);
//	    gl.glBegin(GL.GL_QUADS);
//	        gl.glTexCoord2f(0, 1); gl.glVertex3f( -0.5f,  0.5f, -0.5f );
//	        gl.glTexCoord2f(0, 0); gl.glVertex3f( -0.5f,  0.5f,  0.5f );
//	        gl.glTexCoord2f(1, 0); gl.glVertex3f(  0.5f,  0.5f,  0.5f );
//	        gl.glTexCoord2f(1, 1); gl.glVertex3f(  0.5f,  0.5f, -0.5f );
//	    gl.glEnd();
//	 
//	    // Render the bottom quad
//	    gl.glBindTexture(GL.GL_TEXTURE_2D, 6);
//	    gl.glBegin(GL.GL_QUADS);
//	        gl.glTexCoord2f(0, 0); gl.glVertex3f( -0.5f, -0.5f, -0.5f );
//	        gl.glTexCoord2f(0, 1); gl.glVertex3f( -0.5f, -0.5f,  0.5f );
//	        gl.glTexCoord2f(1, 1); gl.glVertex3f(  0.5f, -0.5f,  0.5f );
//	        gl.glTexCoord2f(1, 0); gl.glVertex3f(  0.5f, -0.5f, -0.5f );
//	    gl.glEnd();
	 
	    // Restore enable bits and matrix
	    gl.glEnable(GL.GL_CULL_FACE);
	    gl.glEnable(GL.GL_DEPTH_TEST);
	    gl.glEnable(GL.GL_LIGHTING);
	    gl.glEnable(GL.GL_BLEND);
//	    gl.glDisable(GL.GL_TEXTURE_2D);
	    gl.glPopMatrix();

	}
}
