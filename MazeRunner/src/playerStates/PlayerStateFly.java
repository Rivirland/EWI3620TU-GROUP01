package playerStates;

import items.Bullet;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;
import engine.MazeRunner;
import engine.Sound;
//Once you've finished, you can freely fly around the world if you want. This state is responsible for that.
public class PlayerStateFly extends PlayerState implements Runnable{

	//A helicopter can shoot bullets, of course!
	@Override
	public void itemUse() {
		if (MazeRunner.player.nrOfBullets > 0) {
			try{
				Sound.play("gunfire.wav");
			}catch (Exception e){
				System.out.println("no GunFire sound");
			}
			MazeRunner.player.nrOfBullets--;
			double hAngle = MazeRunner.player.getHorAngle();
			double vAngle = MazeRunner.player.getVerAngle();
			double x = MazeRunner.player.getLocationX() - Math.cos(Math.toRadians(vAngle)) * Math.sin(Math.toRadians(hAngle)) * 3;
			double y = MazeRunner.player.getLocationY() + Math.sin(Math.toRadians(vAngle)) * 3;
			double z = MazeRunner.player.getLocationZ() - Math.cos(Math.toRadians(vAngle)) * Math.cos(Math.toRadians(hAngle)) * 3;
			Bullet bullet = new Bullet(x, y, z, hAngle, vAngle, 0.02);
			MazeRunner.bulletList.add(bullet);
			MazeRunner.visibleObjects.add(bullet);
		} else {
			try{
				Sound.play("noBullets.wav");
			}catch (Exception e){
				System.out.println("no noBullets sound");
			}
		}

	}

	
	
	@Override
	public void entering() {
		//nothing
	}

	@Override
	public void leaving() {
		//nothing
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl) {
		Teken.textDraw(autodrawable, gl, "Number of bullets: " + MazeRunner.player.nrOfBullets, (float) (0.05 * MazeRunner.screenHeight), (float) (0.08 * MazeRunner.screenWidth), 30);
	}

	//Displays the helicopter.
	@Override
	public void displayItem(GL gl) {
		gl.glPushMatrix();
		gl.glTranslated(0, -10, -40);
		gl.glTranslated(MazeRunner.player.getLocationX(), MazeRunner.player.getLocationY(), MazeRunner.player.getLocationZ());
		double s = Math.toRadians(MazeRunner.player.getHorAngle());
		double t = Math.toRadians(MazeRunner.player.getVerAngle());
		double x = -Math.sin(s) * Math.cos(t);
		double y = Math.sin(t);
		double z = -Math.cos(s) * Math.cos(t);

		gl.glTranslated(x, y, z);
		gl.glTranslated(0, 10, 40);
		gl.glRotated(MazeRunner.player.getHorAngle(), 0, 1, 0);
		gl.glRotated(MazeRunner.player.getVerAngle(), 1, 0, 0);
		//Rotates the helicopter based on player input
		if (MazeRunner.player.getControl().getRight()) {
			gl.glRotated(-10, 0, 0, 1);
		}
		if (MazeRunner.player.getControl().getLeft()) {
			gl.glRotated(10, 0, 0, 1);
		}
		gl.glTranslated(0, -10, -40);
		if (MazeRunner.player.getControl().getForward()) {
			gl.glRotated(-10, 1, 0, 0);
		}
		if (MazeRunner.player.getControl().getBack()) {
			gl.glRotated(10, 1, 0, 0);
		}
		gl.glRotated(-90, 1, 0, 0);
		gl.glScaled(2, 2, 2);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 25);

		MazeRunner.uh60body.display(gl);
		
		MazeRunner.uh60backrotor.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();	
		
		
		//This takes care of the rotating rotor
		
		gl.glPushMatrix();
		gl.glTranslated(0, -10, -40);
		gl.glTranslated(MazeRunner.player.getLocationX(),MazeRunner.player.getLocationY(),MazeRunner.player.getLocationZ());

		gl.glTranslated(x, y, z);
		gl.glTranslated(0, 10, 40);
		gl.glRotated(MazeRunner.player.getHorAngle(), 0, 1, 0);
		gl.glRotated(MazeRunner.player.getVerAngle(), 1, 0, 0);
		if (MazeRunner.player.getControl().getRight()) {
			gl.glRotated(-10, 0, 0, 1);
		}
		if (MazeRunner.player.getControl().getLeft()) {
			gl.glRotated(10, 0, 0, 1);
		}
		gl.glTranslated(0, -10, -40);
		if (MazeRunner.player.getControl().getForward()) {
			gl.glRotated(-10, 1, 0, 0);
		}
		if (MazeRunner.player.getControl().getBack()) {
			gl.glRotated(10, 1, 0, 0);
		}

		gl.glRotated(MazeRunner.currentTime,0,1,0);
		gl.glRotated(-90,1,0,0);
		gl.glScaled(2,2,2);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 25);
		MazeRunner.uh60rotor.display(gl);

		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();
	}
	@Override
	public void run() {
		//nothing
		

	}

}
