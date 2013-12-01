package engine;
import engine.*;

public class PlayerStateDead extends PlayerState{

	@Override
	public void itemUse() {
		System.out.println("You died :(");
		MazeRunner.player.reset();
		for(int e = 0; e < MazeRunner.enemyList.size(); e++){
			MazeRunner.enemyList.get(e).reset();
		}
		Player.canTeleport = false;
		Player.playerStateInt = 0;
	}

	@Override
	public void entering() {
		//nothing
		
	}

	@Override
	public void leaving() {
		//nothing
		
	}

}