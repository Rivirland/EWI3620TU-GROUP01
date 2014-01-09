package playerStates;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import engine.MazeRunner;
import menu.Teken;

public class PlayerStateDead extends PlayerState{

	@Override
	public void itemUse() {
		MazeRunner.player.reset();
		MazeRunner.player.score -= 1000;
		for(int e = 0; e < MazeRunner.enemyList.size(); e++){
			MazeRunner.enemyList.get(e).reset();
		}
		MazeRunner.player.canTeleport = false;
		MazeRunner.player.playerStateInt = 0;
		MazeRunner.player.canMove = true;
	}

	@Override
	public void entering() {
		MazeRunner.player.getControl().info = true;
		MazeRunner.player.getControl().minimap = false;
		MazeRunner.player.canMove = false;
	}

	@Override
	public void leaving() {
		//nothing
		
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl){
		Teken.textDraw(autodrawable, gl, "You died! Press space to start again", 0.35f*MazeRunner.screenWidth, 0.05f*MazeRunner.screenHeight, 30);

	}

	@Override
	public void displayItem(GL gl) {
		// nothing		
	}

}
