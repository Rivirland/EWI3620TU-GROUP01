package engine;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;

public class PlayerStateVictory extends PlayerState{

	@Override
	public void itemUse() {
		MazeRunner.player.reset();
//		MazeRunner.player.score = 1000;
		for(int e = 0; e < MazeRunner.enemyList.size(); e++){
			MazeRunner.enemyList.get(e).reset();
		}
		Player.canTeleport = false;
		Player.playerStateInt = 0;
		Player.canMove = true;
	}

	@Override
	public void entering() {
		Player.canMove = false;
	}

	@Override
	public void leaving() {
		//nothing
		
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl){
		Teken.textDraw(autodrawable, gl, "Victory", 0.35f*MazeRunner.screenWidth, 0.05f*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));

	}

	@Override
	public void displayItem(GL gl) {
		// nothing		
	}

}
