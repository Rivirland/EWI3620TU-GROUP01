package engine;

import items.Bullet;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;

import com.sun.opengl.util.GLUT;

public class PlayerStateGun extends PlayerState{

	public static void shootGun(){
		if(Player.nrOfBullets > 0){
			Sound.gunfire.play();
			Player.nrOfBullets--;
			double hAngle = MazeRunner.player.getHorAngle();
			double vAngle = MazeRunner.player.getVerAngle();
			double x = MazeRunner.player.getLocationX()-Math.cos(Math.toRadians(vAngle))*Math.sin(Math.toRadians(hAngle))*3;
			double y = MazeRunner.player.getLocationY()+Math.sin(Math.toRadians(vAngle))*3;
			double z = MazeRunner.player.getLocationZ()-Math.cos(Math.toRadians(vAngle))*Math.cos(Math.toRadians(hAngle))*3;
			Bullet bullet = new Bullet(x, y, z, hAngle, vAngle, 0.02);
			MazeRunner.bulletList.add(bullet);
			MazeRunner.visibleObjects.add(bullet);
		}
		else{
			Sound.noBullets.play();
		}
		System.out.println(Player.nrOfBullets);

		//Shoot bullet if nrOfBullets > 0
	}
	@Override
	public void itemUse() { // kan dit een reload worden voor gun?
		if(Player.nrOfBullets > 0){
		System.out.println("RELOADING");
		System.out.println("WE'VE BEEN OVERRUN!!!");
		System.out.println("PRESS MOUSE BUTTON BEFORE TOO LATE!");
		}
		else{
			Sound.noBullets.play();
		}
		System.out.println(Player.nrOfBullets);

		//Shoot bullet if nrOfBullets > 0
	}

	@Override
	public void entering() {

Sound.reload.play();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaving() {

		System.out.println("Leaving GunMode");
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl){
		Teken.textDraw(autodrawable, gl, "Number of bullets: " + Player.nrOfBullets, 0.8f*MazeRunner.screenWidth, 0.05f*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));
	}

	@Override
	public void displayItem(GL gl) {
//		System.out.println(MazeRunner.player.getHorAngle() + " " + MazeRunner.player.getVerAngle());
		gl.glPushMatrix();
		gl.glTranslated(MazeRunner.player.getLocationX(),MazeRunner.player.getLocationY(),MazeRunner.player.getLocationZ());
		double s = Math.toRadians(MazeRunner.player.getHorAngle());
		double t = Math.toRadians(MazeRunner.player.getVerAngle());
		double x = -Math.sin(s)*Math.cos(t);
		double y = Math.sin(t);
		double z = -Math.cos(s)*Math.cos(t);
		gl.glTranslated(x, y, z);
		gl.glRotated(MazeRunner.player.getHorAngle(),0,1,0);
		gl.glRotated(MazeRunner.player.getVerAngle(),1,0,0);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 6);
		MazeRunner.m21Model.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();	
	}

}
