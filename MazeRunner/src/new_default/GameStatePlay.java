package new_default;

import javax.media.opengl.GLAutoDrawable;

public class GameStatePlay implements GameState{

	@Override
	public void doAction(GLAutoDrawable drawable) {
//		System.out.println("Op play gezet");
	}

	@Override
	public String getStringOfState() {
		return "play";
	}
}
