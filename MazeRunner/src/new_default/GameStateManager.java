package new_default;

public class GameStateManager implements GameState {
	private GameState status;
	
	public void setState(GameState newState){
		status = newState;
	}
	public GameState getState(){
		return this.status;
	}
	
	@Override
	public void doAction() {
		this.status.doAction();
	}
	@Override
	public String getStringOfState() {
		return status.getStringOfState();
	}
	public void update() {
		status.doAction();
		
	}
	
	
}
