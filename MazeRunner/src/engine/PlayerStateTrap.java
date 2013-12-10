package engine;

import items.Item;
import items.TrapDropped;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;

public class PlayerStateTrap extends PlayerState {

	@Override
	public void itemUse() {
		if (Player.nrOfTraps > 0) {
			Player.nrOfTraps--;

//			Maze curMaze = MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(MazeRunner.player));
			double trapX=MazeRunner.player.getLocationX();
			double trapY=MazeRunner.player.getLocationY();
			double trapZ=MazeRunner.player.getLocationZ();

			TrapDropped trapDropped = new TrapDropped(trapX, trapY, trapZ, MazeRunner.level.getCurrentMaze(MazeRunner.player));
			trapDropped.setThrown(true);
			trapDropped.setT0(MazeRunner.currentTime);
			
			MazeRunner.visibleObjects.add(trapDropped);
			MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(MazeRunner.player)).itemList.add(trapDropped);
			
			System.out.println("Trap dropped, motherfucker! nrOfTraps: "
					+ Player.nrOfTraps);
		} else {
			System.out.println("out of traps");
		}// Drop trap
			// TODO Auto-generated method stub

	}

	@Override
	public void entering() {
		System.out.println("Entering TrapMode");
		// Show Trap if nrOfTraps > 0
		// TODO Auto-generated method stub

	}

	@Override
	public void leaving() {
		System.out.println("Leaving TrapMode");
		// Remove Trap
		// TODO Auto-generated method stub

	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl) {
		Teken.textDraw(autodrawable, gl,
				"Number of traps: " + Player.nrOfTraps,
				0.8f * MazeRunner.screenWidth, 0.05f * MazeRunner.screenHeight,
				0.05f * Math.min(MazeRunner.screenHeight,
						MazeRunner.screenWidth));
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
		
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, 4);
		MazeRunner.trapModel.display(gl);
		gl.glPopMatrix();

	}

}
