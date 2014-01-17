package playerStates;

import items.Bullet;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;
import engine.ChangeGL;
import engine.MazeRunner;
import engine.Sound;
//When in gunmode, you should be able to shoot bullets if you have them. A shooting sound is played if you shoot, otherwise you hear a vague clicking sound.
public class PlayerStateGun extends PlayerState {

	@Override
	public void itemUse() {
		if (MazeRunner.player.nrOfBullets > 0) {
			try {
				Sound.play("gunfire.wav");
			} catch (Exception e) {
				System.out.println("no gunfire sound");
			}

			MazeRunner.player.nrOfBullets--;
			double hAngle = MazeRunner.player.getHorAngle();
			double vAngle = MazeRunner.player.getVerAngle();
			double x = MazeRunner.player.getLocationX() - Math.cos(Math.toRadians(vAngle)) * Math.sin(Math.toRadians(hAngle)) * 1;
			double y = MazeRunner.player.getLocationY() + Math.sin(Math.toRadians(vAngle)) * 1;
			double z = MazeRunner.player.getLocationZ() - Math.cos(Math.toRadians(vAngle)) * Math.cos(Math.toRadians(hAngle)) * 1;
			Bullet bullet = new Bullet(x, y, z, hAngle, vAngle, 0.02);
			MazeRunner.bulletList.add(bullet);
			MazeRunner.visibleObjects.add(bullet);
		} else {
			try {
				Sound.play("noBullets.wav");
			} catch (NullPointerException e) {
				System.out.println("no noBullets sound");
			}
		}
		System.out.println(MazeRunner.player.nrOfBullets);

	}


	@Override
	public void entering() {
		try {
			Sound.play("reload.wav");
		} catch (NullPointerException e) {
			System.out.println("no reload sound");
		}

	}

	@Override
	public void leaving() {

		System.out.println("Leaving GunMode");

	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl) {
		Teken.textDraw(autodrawable, gl, "Number of bullets: " + MazeRunner.player.nrOfBullets, (float) (0.05 * MazeRunner.screenHeight), (float) (0.08 * MazeRunner.screenWidth), 30);
	}

	//Displaying the gun
	@Override
	public void displayItem(GL gl) {
		ChangeGL.GLtoTexturedItem(gl);
		
		gl.glPushMatrix();
		gl.glTranslated(MazeRunner.player.getLocationX(), MazeRunner.player.getLocationY(), MazeRunner.player.getLocationZ());
		double s = Math.toRadians(MazeRunner.player.getHorAngle());
		double t = Math.toRadians(MazeRunner.player.getVerAngle());
		double x = -Math.sin(s) * Math.cos(t);
		double y = Math.sin(t);
		double z = -Math.cos(s) * Math.cos(t);
		gl.glTranslated(x, y, z);
		gl.glRotated(MazeRunner.player.getHorAngle(), 0, 1, 0);
		gl.glRotated(MazeRunner.player.getVerAngle(), 1, 0, 0);
		gl.glScaled(0.5, 0.5, 0.5);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 25);
		MazeRunner.m21Model.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();
	}

}
