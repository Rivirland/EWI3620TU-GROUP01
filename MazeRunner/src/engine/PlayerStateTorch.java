package engine;

import javax.media.opengl.GL;

public class PlayerStateTorch extends PlayerState{

	@Override
	public void itemUse() {
		System.out.println("This does nothing, silly!");
	}

	@Override
	public void entering() {

		System.out.println("Entering TorchMode");
		//Increase Light, Decrease movement speed, Show torch
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaving() {
		System.out.println("Leaving TorchMode");
		//Decrease Light, Increase movement speed, Remove torch
		// TODO Auto-generated method stub
		
	}
	
	public void displayItem(GL gl) {
//		System.out.println(MazeRunner.player.getHorAngle() + " " + MazeRunner.player.getVerAngle());
		gl.glPushMatrix();
		gl.glTranslated(MazeRunner.player.getLocationX(),MazeRunner.player.getLocationY(),MazeRunner.player.getLocationZ());
		gl.glTranslated(-Math.sin(Math.toRadians(MazeRunner.player.getHorAngle())),Math.sin(Math.toRadians(MazeRunner.player.getVerAngle())),-Math.cos(Math.toRadians(MazeRunner.player.getHorAngle())));
		gl.glRotated(MazeRunner.player.getHorAngle(),0,1,0);
		gl.glRotated(MazeRunner.player.getVerAngle(),1,0,0);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 5);
		MazeRunner.torchModel.display(gl);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();	
	}
}
