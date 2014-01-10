package playerStates;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import engine.MazeRunner;

public abstract class PlayerState {
	
	public abstract void itemUse();
	public abstract void entering();
	public abstract void leaving();
	public abstract void displayItem(GL gl);
	public abstract void drawInfo(GLAutoDrawable autodrawable, GL gl);
	public static PlayerState getState(int input){
		if(input == 0 ){
			return MazeRunner.player.PlayerStateCloak;
		}
		if(input == 1){
			return MazeRunner.player.PlayerStateTrap;
		}
		if(input == 2){
			return MazeRunner.player.PlayerStateGun;
		}
		if(input == 3){
			return MazeRunner.player.PlayerStateDead;
		}
		if(input == 4){
			return MazeRunner.player.PlayerStateVictory;
		}
		return null;
	}

}
