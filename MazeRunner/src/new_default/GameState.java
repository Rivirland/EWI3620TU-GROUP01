package new_default;

import javax.media.opengl.GLAutoDrawable;

public interface GameState {
	public String getStringOfState();
	public void doAction(GLAutoDrawable drawable);
}
