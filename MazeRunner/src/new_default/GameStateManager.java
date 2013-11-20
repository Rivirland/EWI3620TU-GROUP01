package new_default;

import javax.media.opengl.GLAutoDrawable;

public class GameStateManager implements GameState {
	private GameState status;
	
	public void setState(GameState newState){
		status = newState;
	}
	public GameState getState(){
		return this.status;
	}
	
	@Override
	public void doAction(GLAutoDrawable drawable) {
		this.status.doAction(drawable);
	}
	@Override
	public String getStringOfState() {
		return status.getStringOfState();
	}
	public void update(GLAutoDrawable drawable) {
		status.doAction(drawable);
		
	}
	
	
}
