package engine;

import items.TrapDropped;

public class PlayerStateTrap extends PlayerState {

	@Override
	public void itemUse() {
		if (Player.nrOfTraps > 0) {
			Player.nrOfTraps--;
			TrapDropped trapDropped = new TrapDropped(MazeRunner.player.getLocationX(), 1.25f, MazeRunner.player.getLocationZ(), MazeRunner.level.getCurrentMaze(MazeRunner.player));
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

}
