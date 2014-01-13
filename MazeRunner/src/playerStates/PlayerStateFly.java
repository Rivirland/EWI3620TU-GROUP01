package playerStates;

import items.Bullet;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;

import engine.MazeRunner;
import engine.Sound;

public class PlayerStateFly extends PlayerState {

	@Override
	public void itemUse() {
		if (MazeRunner.player.nrOfBullets > 0) {
			Sound.sounds.get("Gunfire").play();
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
			Sound.sounds.get("noBullets").play();
		}
		System.out.println(MazeRunner.player.nrOfBullets);

		// Shoot bullet if nrOfBullets > 0
	}

	

	@Override
	public void entering() {
		System.out.println("Entering GunMode");
		try {
			Sound.sounds.get("reload").play();
		} catch (NullPointerException e) {
			System.out.println("no Reload sound");
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

	@Override
	public void displayItem(GL gl) {
		// System.out.println(MazeRunner.player.getHorAngle() + " " +
		// MazeRunner.player.getVerAngle());
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
		// MazeRunner.copterModel.display(gl);
		MazeRunner.uh60body.display(gl);
		MazeRunner.uh60rotor.display(gl);
		MazeRunner.uh60backrotor.display(gl);

		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();
	}

}
