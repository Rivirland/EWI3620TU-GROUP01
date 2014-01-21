package playerStates;

import items.TrapDropped;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;
import engine.ChangeGL;
import engine.MazeRunner;

public class PlayerStateTrap extends PlayerState {

	@Override
	public void itemUse() {
		if (MazeRunner.player.nrOfTraps > 0 && MazeRunner.level.getCurrentMaze(MazeRunner.player) != -1) {
			//TODO: Play sound?
			MazeRunner.player.nrOfTraps--;
			double trapX = MazeRunner.player.getGlobalX();
			double trapY = MazeRunner.player.getGlobalY() - 0.2;
			double trapZ = MazeRunner.player.getGlobalZ();
			TrapDropped trapDropped = new TrapDropped(trapX, trapY, trapZ, MazeRunner.level.getCurrentMaze(MazeRunner.player));
			trapDropped.setThrown(true);
			trapDropped.setT0(MazeRunner.currentTime);

			MazeRunner.visibleObjects.add(trapDropped);
			MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(MazeRunner.player)).itemList.add(trapDropped);

			System.out.println("Trap dropped, motherfucker! nrOfTraps: " + MazeRunner.player.nrOfTraps);
		} else if (MazeRunner.level.getCurrentMaze(MazeRunner.player) != -1) {
			System.out.println("out of traps");
		} else {
			System.out.println("Not in a maze");
		}
	}

	@Override
	public void entering() {
		System.out.println("Entering TrapMode");
		// Show Trap if nrOfTraps > 0
	}

	@Override
	public void leaving() {
		System.out.println("Leaving TrapMode");
		// Remove Trap
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl) {
		Teken.textDraw(autodrawable, gl, "Number of traps: " + MazeRunner.player.nrOfTraps, (float) (0.05 * MazeRunner.screenHeight), (float) (0.08 * MazeRunner.screenWidth), 30);
	}

	public void displayItem(GL gl) {
		if (MazeRunner.player.nrOfTraps > 0) {
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
			gl.glDisable(GL.GL_CULL_FACE);

			gl.glBindTexture(GL.GL_TEXTURE_2D, 28);
			// MazeRunner.trapModel.display(gl);
			Teken.drawCuboid(gl, 0.3, 0.6, -0.6, -0.3, 0, 0.3);

			gl.glPopMatrix();
		}
	}

}
