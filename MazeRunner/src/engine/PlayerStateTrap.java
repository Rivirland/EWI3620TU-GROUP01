package engine;

import items.TrapDropped;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class PlayerStateTrap extends PlayerState {

	@Override
	public void itemUse() {
		if (Player.nrOfTraps > 0) {
			Player.nrOfTraps--;
//			Maze curMaze = MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(MazeRunner.player));
			double trapX=MazeRunner.player.getLocationX();
			double trapY=MazeRunner.player.getLocationY();
			double trapZ=MazeRunner.player.getLocationZ();

			TrapDropped trapDropped = new TrapDropped(trapX, trapY, trapZ, MazeRunner.level.getCurrentMaze(MazeRunner.player));
			trapDropped.setThrown(true);
			trapDropped.setT0(MazeRunner.currentTime);
			
			MazeRunner.visibleObjects.add(trapDropped);
			MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(MazeRunner.player)).itemList.add(trapDropped);
			
			System.out.println("Trap dropped, motherfucker! nrOfTraps: "
					+ Player.nrOfTraps);
		} else {
			System.out.println("out of traps");
		}// Drop trap
			// TODO Auto-generated method stub

	}

	@Override
	public void entering() {
		System.out.println("Entering TrapMode");
		// Show Trap if nrOfTraps > 0
		// TODO Auto-generated method stub

	}

	@Override
	public void leaving() {
		System.out.println("Leaving TrapMode");
		// Remove Trap
		// TODO Auto-generated method stub

	}
	
	public void displayItem(GL gl) {
		
	}

}
