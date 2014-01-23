package playerStates;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import engine.MazeRunner;
import engine.Sound;
import menu.Teken;

//You enter this state once you are killed.
public class PlayerStateDead extends PlayerState{

	//Upon clicking, everything gets reset.
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

	//Entering this state will make you unable to move and it will remove the minimap from view.
	@Override
	public void entering() {
		try {
//			Sound.play("death.wav");
		} catch (Exception e) {
			System.out.println("no death sound");
		}
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
		Teken.textDraw(autodrawable, gl, "You died! Click to start again", 0.35f*MazeRunner.screenWidth, 0.05f*MazeRunner.screenHeight, 30);

	}

	@Override
	public void displayItem(GL gl) {
		// nothing		
	}

}
