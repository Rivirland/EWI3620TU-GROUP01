package engine;

import items.*;
import enemies.Enemy;
//This class takes care of the animations for throwing a trap and the beam that appears when an enemy is sucked into a trap.
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
			// x translaten
			double gewenstX = e.getTrappedX();
			double curX = e.locationX;
			double deltaX = gewenstX - curX;
			double x = e.locationX + deltaX * (cT - startSuck - e.TOD) / shouldbeGone;
			e.setLocationX(x);

			// y translaten
			double gewenstY = MazeRunner.level.getMaze(MazeRunner.level.getCurrentMaze(e)).mazeY + 0.1;
			double curY = e.locationY;
			double deltaY = gewenstY - curY;
			double y = e.locationY + deltaY * (cT - startSuck - e.TOD) / shouldbeGone;
			e.setLocationY(y);

			// z translaten
			double gewenstZ = e.getTrappedZ();
			double curZ = e.locationZ;
			double deltaZ = gewenstZ - curZ;
			double z = e.locationZ + deltaZ * (cT - startSuck - e.TOD) / shouldbeGone;
			e.setLocationZ(z);

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

	public static void thrownTrapDropped(TrapDropped t) {
		long cT = MazeRunner.currentTime;
		long dT = cT - t.getT0();
		double i = 0.003;
		if (t.inair) {
			Maze curMaze = MazeRunner.level.getMaze(t.mazeID);
			double previousX = t.getLocationX();
			double previousZ = t.getLocationZ();
			
			t.setLocationX(t.getLocationX() - (Math.sin(Math.toRadians(t.horAngle)) * t.vx0 * 0.07));
			t.setLocationY(t.getLocationY() + t.vy0  * dT * i + t.ay * dT * dT * i * i);
			t.setLocationZ(t.getLocationZ() - (Math.cos(Math.toRadians(t.horAngle)) * t.vx0 * 0.07));
			
			boolean[] trapCollide = MazeRunner.level.collides(t, 0.25);
			if (trapCollide[0] || trapCollide[2]) {
				t.setLocationX(previousX);
				t.horAngle=-t.horAngle;
				t.vx0=t.vx0*0.5;
			}
			if (trapCollide[1] || trapCollide[3]) {
				t.setLocationZ(previousZ);
				t.horAngle=-180-t.horAngle;
				t.vx0=t.vx0*0.5;
			}
			trapCollide[0]=false;
			trapCollide[1]=false;
			trapCollide[2]=false;
			trapCollide[3]=false;
			
			if (t.getLocationY() <= curMaze.mazeY) {
				t.inair = false;
				t.setLocationY(curMaze.mazeY);
				t.onground = true;
			}
		} else if (t.onground) {
			// TODO: rotate
			// TODO: also rotate according to horangle
		}
		// System.out.println("y; " + t.getLocationY() + "  vy0: " + t.vy0 * dT
		// * i + " ay " + t.ay * dT * dT * i * i + " dT " + dT
		// + " " + t.inair);

	}
}
