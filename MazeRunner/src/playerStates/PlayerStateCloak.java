package playerStates;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;
import engine.MazeRunner;
//Cloakmode makes you invisible to the enemy. You can use a total of 10 seconds of cloak, triggered by clicking the mouse. Clicking again will stop the timer and make you visible again.
public class PlayerStateCloak extends PlayerState {
	public double duration;

	@Override
	public void itemUse() {
		MazeRunner.player.invisible = !MazeRunner.player.invisible;
	}

	@Override
	public void entering() {
	}

	//When leaving this mode, you will automatically become visible again.
	@Override
	public void leaving() {
		MazeRunner.player.invisible = false;
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

	//TODO: Dit is zo fucking lelijk xD
	public void displayItem(GL gl) {
		if (MazeRunner.player.invisible) {
			MazeRunner.player.cloakSeconds -= MazeRunner.deltaTime;
			if (MazeRunner.player.cloakSeconds <= 0) {
				MazeRunner.player.invisible = false;
			}
		}
	}
}
