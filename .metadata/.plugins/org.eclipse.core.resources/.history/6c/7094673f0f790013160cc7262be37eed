package playerStates;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;
import engine.MazeRunner;

public class PlayerStateCloak extends PlayerState{
	public double duration;

	@Override
	public void itemUse() {
		System.out.println("Toggle Cloak!");
		MazeRunner.player.invisible=!MazeRunner.player.invisible;
	}

	@Override
	public void entering() {
		System.out.println("Entering Cloak mode");	
	}

	@Override
	public void leaving() {
		System.out.println("Leaving Cloak mode");
	}
	
	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl){
//		if (MazeRunner.player.invisible){
//			Teken.textDraw(autodrawable, gl, "Press Space again to stop using cloak", 0.34f*MazeRunner.screenWidth, 0.1f*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));
//			Teken.textDraw(autodrawable, gl, "Remaining cloak: " + MazeRunner.player.cloakSeconds, 0.4f*MazeRunner.screenWidth, 0.05f*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));
//		} else{
//			Teken.textDraw(autodrawable, gl, "Press Space to use cloak", 0.4f*MazeRunner.screenWidth, 0.1f*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));
//			Teken.textDraw(autodrawable, gl, "Remaining cloak: " + MazeRunner.player.cloakSeconds, 0.4f*MazeRunner.screenWidth, 0.05f*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));
//			
//		}
		if(MazeRunner.player.cloakSeconds > 0){
		Teken.textDraw(autodrawable, gl, "Seconds of invisibility left: " + MazeRunner.player.cloakSeconds, (float) (0.05 * MazeRunner.screenHeight), (float) (0.08 * MazeRunner.screenWidth), (float) (0.05 * MazeRunner.screenHeight));
		}else{
			Teken.textDraw(autodrawable, gl, "No more invisibility!", (float) (0.05 * MazeRunner.screenHeight), (float) (0.08 * MazeRunner.screenWidth), (float) (0.05 * MazeRunner.screenHeight));
				
		}
		}
	
	public void displayItem(GL gl) {
		if(MazeRunner.player.invisible) {
			MazeRunner.player.cloakSeconds-=MazeRunner.deltaTime;
			if (MazeRunner.player.cloakSeconds>0){
				System.out.println(MazeRunner.player.cloakSeconds);
			}
			else {
				MazeRunner.player.invisible=false;
			}
		}
	}
}
