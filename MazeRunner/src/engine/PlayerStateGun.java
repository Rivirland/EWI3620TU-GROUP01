package engine;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class PlayerStateGun extends PlayerState{

	@Override
	public void itemUse() {
		Player.nrOfBullets--;
		System.out.println("Bullet, motherfucker! nrOfBullets: " + Player.nrOfBullets);
		//Shoot bullet if nrOfBullets > 0
	}

	@Override
	public void entering() {

		System.out.println("Entering GunMode");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaving() {

		System.out.println("Leaving GunMode");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayItem(GL gl) {
//		System.out.println(MazeRunner.player.getHorAngle() + " " + MazeRunner.player.getVerAngle());
		gl.glPushMatrix();
		gl.glTranslated(MazeRunner.player.getLocationX(),MazeRunner.player.getLocationY(),MazeRunner.player.getLocationZ());
		gl.glTranslated(-Math.sin(Math.toRadians(MazeRunner.player.getHorAngle())),Math.sin(Math.toRadians(MazeRunner.player.getVerAngle())),-Math.cos(Math.toRadians(MazeRunner.player.getHorAngle())));
		gl.glRotated(MazeRunner.player.getHorAngle(),0,1,0);
		gl.glRotated(MazeRunner.player.getVerAngle(),1,0,0);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 5);
		MazeRunner.m21Model.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();	
	}

}
