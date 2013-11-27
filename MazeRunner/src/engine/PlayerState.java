package engine;

public abstract class PlayerState {
	
	public abstract void itemUse();
	public abstract void entering();
	public abstract void leaving();
	public static PlayerState getState(int input){
		if(input == 0 ){
			return new PlayerStateTorch();
		}
		if(input == 1){
			return new PlayerStateTrap();
		}
		if(input == 2){
			return new PlayerStateGun();
		}
		return null;
	}

}
