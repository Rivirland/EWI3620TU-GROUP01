package engine;

import items.TrapDroppedGBS;
import enemies.Enemy;
import enemies.EnemySpooky;

public abstract class Animator {

	public static void trapAnimation(TrapDroppedGBS t) {
		long cT = MazeRunner.currentTime;
		// 1e Begin animatie
		if (t.StartTime + TrapDroppedGBS.animationTime > cT) {
			double gewenstH = MazeRunner.level.getMaze(t.mazeID).mazeY + t.getHeight();
			double curH = t.locationY;
			double deltaH = gewenstH - curH;
			double h = t.locationY + deltaH * (cT - t.StartTime) / TrapDroppedGBS.animationTime;
			t.setLocationY(h);
		}
		// 2e Begin animatie
		if (t.StartTime + TrapDroppedGBS.animationTime2 > cT && cT > t.StartTime + TrapDroppedGBS.animationTime) {
			double gewenstR = 0.9;
			double curR = t.getRadius();
			double dR = gewenstR - curR;
			double setR = t.getRadius() + dR * (cT - TrapDroppedGBS.animationTime - t.StartTime) / TrapDroppedGBS.animationTime2;
			t.setRadius(setR);
		}
		// 1e vang animatie
		if (t.StartTime + TrapDroppedGBS.animationTime3 > cT && cT > t.StartTime + TrapDroppedGBS.animationTime2) {
			double rotSpeed = 0.08;
			t.setAlpha(Math.toDegrees((cT)) * rotSpeed);

		}
		// 2e vang animatie - gelijktijdig met 1e vang animatie
		if (t.StartTime + TrapDroppedGBS.animationTime3 > cT && cT > t.StartTime + TrapDroppedGBS.animationTime2) {
			// TODO: mag eventueel weg, ik zat te denken om de hoogte van de
			// trap te varieren over een sinus, is me nog niet gelukt. Dus
			// overweeg om het niet te doen
		}
		// 1e verdwijn animatie
		if (t.StartTime + TrapDroppedGBS.animationTime4 > cT && cT > t.StartTime + TrapDroppedGBS.animationTime3) {
			double gewenstR = 0;
			double curR = t.getRadius();
			double dR = gewenstR - curR;
			double setR = t.getRadius() + dR * (cT - TrapDroppedGBS.animationTime3 - t.StartTime) / TrapDroppedGBS.animationTime4;
			t.setRadius(setR);
		}
		// 2e verdwijn animatie
		if (t.StartTime + TrapDroppedGBS.animationTime5 > cT && cT > t.StartTime + TrapDroppedGBS.animationTime4) {
			double gewenstH = MazeRunner.level.getMaze(t.mazeID).mazeY - t.getHeight();
			double curH = t.locationY;
			double deltaH = gewenstH - curH;
			double h = t.locationY + deltaH * (cT - TrapDroppedGBS.animationTime4 - t.StartTime) / TrapDroppedGBS.animationTime5;
			t.setLocationY(h);
		}
		if (t.StartTime + TrapDroppedGBS.animationTime5 < cT) {
			t.setUsed(true);
		}

	}

	public static void disappearIntoTrap(Enemy e) {
		double startSuck = TrapDroppedGBS.animationTime2;
		double shouldbeGone = TrapDroppedGBS.animationTime3;
		long cT = MazeRunner.currentTime;
		if (e.TOD + shouldbeGone > cT && cT > e.TOD + startSuck) {
			// naar beneden translaten
			double gewenstH = MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(e)).mazeY + 0.1;
			double curH = e.locationY;
			double deltaH = gewenstH - curH;
			double h = e.locationY + deltaH * (cT - startSuck - e.TOD) / shouldbeGone;
			e.setLocationY(h);

			// Down-scalen
			double gewenstS = 0;
			double curS = e.getSize();
			double deltaS = gewenstS - curS;
			double s = e.getSize() + deltaS * (cT - startSuck - e.TOD) / shouldbeGone;
			e.setSize(s);
		}
		if (e.TOD + shouldbeGone < cT) {
			e.setDead(true);

		}
	}

	
}


