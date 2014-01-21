package engine;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import menu.Teken;

//Displays the skybox! We draw this before anything else without any lighting/depth test etc and it is placed around the player. Then, the other visible objects are drawn over it. 
public class Skybox {
	public static void displaySkybox(GL gl) {
		GLU glu = new GLU();
		gl.glLoadIdentity();
		gl.glPushMatrix();

		// Reset and transform the matrix.
		gl.glLoadIdentity();

		double vrpSkyBoxX = -Math.sin(Math.PI * MazeRunner.player.getHorAngle() / 180) * Math.cos(Math.PI * MazeRunner.player.getVerAngle() / 180);
		double vrpSkyBoxY = Math.sin(Math.PI * MazeRunner.player.getVerAngle() / 180);
		double vrpSkyBoxZ = -Math.cos(Math.PI * MazeRunner.player.getHorAngle() / 180) * Math.cos(Math.PI * MazeRunner.player.getVerAngle() / 180);
		glu.gluLookAt(0, 0, 0, vrpSkyBoxX, vrpSkyBoxY, vrpSkyBoxZ, 0, 1, 0);
		// gl.glRotated(-Math.sin( Math.PI * MazeRunner.player.getHorAngle() /
		// 180 ) * Math.cos( Math.PI * MazeRunner.player.getVerAngle() / 180
		// ),1,0,0);
		// gl.glRotated(Math.sin( Math.PI * MazeRunner.player.getVerAngle() /
		// 180 ),0,1,0);
		// gl.glRotated(-Math.cos( Math.PI * MazeRunner.player.getHorAngle() /
		// 180 ) * Math.cos( Math.PI * MazeRunner.player.getVerAngle() / 180
		// ),0,0,1);

		// Enable/Disable features
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_BLEND);

		// Just in case we set all vertices to white.
		gl.glColor4f(1, 1, 1, 1);

		Teken.drawCuboidWithoutCulling(gl, -1, 1, -1, 1, -1, 1, new int[] { 8, 9, 10, 11, 12, 13 });

		// Restore enable bits and matrix
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_BLEND);
		// gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();

	}

	// de SkyBox heeft nog een displayfunctie specifiek voor portals

	public static void displaySkybox(GL gl, Camera portalcamera) {
		GLU glu = new GLU();
		gl.glLoadIdentity();
		gl.glPushMatrix();

		// Reset and transform the matrix.
		gl.glLoadIdentity();
		//
		double vrpSkyBoxX = -Math.sin(Math.PI * MazeRunner.player.getHorAngle() / 180) * Math.cos(Math.PI * MazeRunner.player.getVerAngle() / 180);
		double vrpSkyBoxY = Math.sin(Math.PI * MazeRunner.player.getVerAngle() / 180);
		double vrpSkyBoxZ = -Math.cos(Math.PI * MazeRunner.player.getHorAngle() / 180) * Math.cos(Math.PI * MazeRunner.player.getVerAngle() / 180);

		// System.out.println(vrpSkyBoxX);
		// System.out.println(vrpSkyBoxY);
		// System.out.println(vrpSkyBoxZ);

		// System.out.println(portalcamera.getVrpX());
		// System.out.println(portalcamera.getVrpY());
		// System.out.println(portalcamera.getVrpZ());

		System.out.println(portalcamera.getVuvX());
		System.out.println(portalcamera.getVuvY());
		System.out.println(portalcamera.getVuvZ());

		// glu.gluLookAt(0, 0, 0,portalcamera.getVrpX(),portalcamera.getVrpY(),
		// portalcamera.getVrpZ(), 0,1,0);
		glu.gluLookAt(0, 0, 0, vrpSkyBoxX, vrpSkyBoxY, vrpSkyBoxZ, 0, 1, 0);

		// gl.glRotated(-Math.sin( Math.PI * MazeRunner.player.getHorAngle() /
		// 180 ) * Math.cos( Math.PI * MazeRunner.player.getVerAngle() / 180
		// ),1,0,0);
		// gl.glRotated(Math.sin( Math.PI * MazeRunner.player.getVerAngle() /
		// 180 ),0,1,0);
		// gl.glRotated(-Math.cos( Math.PI * MazeRunner.player.getHorAngle() /
		// 180 ) * Math.cos( Math.PI * MazeRunner.player.getVerAngle() / 180
		// ),0,0,1);

		// Enable/Disable features
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_BLEND);

		// Just in case we set all vertices to white.
		gl.glColor4f(1, 1, 1, 1);

		Teken.drawCuboidWithoutCulling(gl, -1, 1, -1, 1, -1, 1, new int[] { 8, 9, 10, 11, 12, 13 });

		// Restore enable bits and matrix
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_BLEND);
		// gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();

	}

}
