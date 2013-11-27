package items;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import engine.*;

public class TrapHolder extends Item {
		
	public TrapHolder(double x, double y, double z, int i) {
		super(x, y, z, i);
	}

	@Override
	public void display(GL gl) {

		GLUT glut = new GLUT();
		gl.glPushMatrix();
		gl.glTranslated(super.locationX, super.locationY, super.locationZ);
		glut.glutSolidCube(2);
		gl.glPopMatrix();
	}

	@Override
	public boolean touches(GameObject object) {
//		System.out.println(player.getLocationX());
//		System.out.println(this.locationX + MazeRunner.level.getMaze(mazeID).getMazeX());
		double diffX = object.getLocationX() - this.getGlobalX();
		double diffY = object.getLocationY() - this.getGlobalY();
		double diffZ = object.getLocationZ() - this.getGlobalZ();
		if (Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ) < 2) {
			if(object instanceof Player){
				Player.nrOfTraps++;
				
				return true;
			}
			else{
				
				//do nothing
			}
		}
		return false;
	}

	@Override
	public double getGlobalX() {
//		System.out.println(MazeRunner.level.getMaze(mazeID).getMazeX());
		return locationX+MazeRunner.level.getMaze(this.mazeID).getMazeX();
	}

	@Override
	public double getGlobalY() {
		return locationY+MazeRunner.level.getMaze(this.mazeID).getMazeY();
	}

	@Override
	public double getGlobalZ() {
		return locationZ+MazeRunner.level.getMaze(this.mazeID).getMazeZ();
	}

}
