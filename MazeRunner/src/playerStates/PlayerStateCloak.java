package playerStates;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;
import engine.MazeRunner;

public class PlayerStateCloak extends PlayerState {
	public double duration;

	@Override
	public void itemUse() {
		System.out.println("Toggle Cloak!");
		MazeRunner.player.invisible = !MazeRunner.player.invisible;
	}

	@Override
	public void entering() {
		System.out.println("Entering Cloak mode");
	}

	@Override
	public void leaving() {
		MazeRunner.player.invisible = false;
		System.out.println("Leaving Cloak mode");
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl) {
		// }
		if (MazeRunner.player.cloakSeconds > 0) {
			Teken.textDraw(autodrawable, gl, "Seconds of invisibility left: " + MazeRunner.player.cloakSeconds / 1000, (float) (0.05 * MazeRunner.screenHeight),
					(float) (0.08 * MazeRunner.screenWidth), 30);
		} else {
			Teken.textDraw(autodrawable, gl, "No more invisibility!", (float) (0.05 * MazeRunner.screenHeight), (float) (0.08 * MazeRunner.screenWidth), 30);

		}
	}

	public void displayItem(GL gl) {
		if (MazeRunner.player.invisible) {
			MazeRunner.player.cloakSeconds -= MazeRunner.deltaTime;
			if (MazeRunner.player.cloakSeconds <= 0) {
				MazeRunner.player.invisible = false;
			}
		}
	}
}
