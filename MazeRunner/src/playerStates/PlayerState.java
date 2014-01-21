package playerStates;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import engine.MazeRunner;
//This class is the baseline for all the playerstates. The entering and leaving methods are called when the player switches playerstates.
public abstract class PlayerState {
	
	
	public abstract void itemUse();
	public abstract void entering();
	public abstract void leaving();
	public abstract void displayItem(GL gl);
	public abstract void drawInfo(GLAutoDrawable autodrawable, GL gl);
	public static PlayerState getState(int input){
		if(input == 0 ){
			return MazeRunner.player.playerStateCloak;
		}
		if(input == 1){
			return MazeRunner.player.playerStateTrap;
		}
		if(input == 2){
			return MazeRunner.player.playerStateGun;
		}
		if(input == 3){
			return MazeRunner.player.playerStateDead;
		}
		if(input == 4){
			return MazeRunner.player.playerStateVictory;
		}
		if(input == 5){
			return MazeRunner.player.playerStateFly;
		}
		return null;
	}

}
