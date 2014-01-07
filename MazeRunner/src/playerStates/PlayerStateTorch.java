package playerStates;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import engine.MazeRunner;
import engine.Sound;

public class PlayerStateTorch extends PlayerState{

	@Override
	public void itemUse() {
		System.out.println("This does nothing, silly!");
	}

	@Override
	public void entering() {
		Sound.fire.play();

		System.out.println("Entering TorchMode");
		
		//Increase Light,
		//Decrease movement speed, Show torch
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaving() {
		System.out.println("Leaving TorchMode");
		//Decrease Light, Increase movement speed, Remove torch
		// TODO Auto-generated method stub
//		Sound.fire.stop();
	}
	
	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl){
		//
	}
	
	public void displayItem(GL gl) {
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
		gl.glBindTexture(GL.GL_TEXTURE_2D, 7);
		MazeRunner.torchModel.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();	
	}
}