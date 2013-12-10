package engine;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

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
		//nothing0	
	}

	@Override
	public void leaving() {
		//nothing
		
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl){
		//
	}

	@Override
	public void displayItem(GL gl) {
		// nothing		
	}

}
