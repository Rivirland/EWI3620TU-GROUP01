package engine;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public abstract class PlayerState {
	
	public abstract void itemUse();
	public abstract void entering();
	public abstract void leaving();
	public abstract void displayItem(GL gl);
	public abstract void drawInfo(GLAutoDrawable autodrawable, GL gl);
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
		if(input == 3){
			return new PlayerStateDead();
		}
		if(input == 4){
			return new PlayerStateVictory();
		}
		return null;
	}

}
