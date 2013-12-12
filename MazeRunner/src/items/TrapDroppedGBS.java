package items;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import engine.Animator;
import engine.MazeRunner;

public class TrapDroppedGBS extends TrapDropped {
	private double radius = 0.0;
	private double height = 2.5;
	public long StartTime;
	private double alpha;
	public static double animationTime = 600; // Trap up
	public static double animationTime2 = 300 + animationTime; // Trap scale
	public static double animationTime3 = 600 + animationTime2; // Trap rotate & bounce
	public static double animationTime4 = 900 + animationTime3; // Trap de-scale
	public static double animationTime5 = 300 + animationTime4; // Trap down

	public TrapDroppedGBS(double x, double y, double z, int i, long time) {
		super(x, y, z, i);
		StartTime = time;
	}

	public void display(GL gl) {
		if (MazeRunner.currentTime>StartTime){
			Animator.trapAnimation(this);
		}
		
		gl.glPushMatrix();
		gl.glTranslated(super.locationX, super.locationY, super.locationZ);
		gl.glRotated(90, 1, 0, 0);
		gl.glRotated(getAlpha(), 0, 0, 1);
		drawTrap(gl);
		gl.glPopMatrix();
		gl.glFlush();
	}

	
	public void drawTrap(GL gl){
		GLUT glut = new GLUT();
		gl.glLineWidth(1);
		glut.glutWireCone(getRadius(), getHeight(), 15, 15);
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
}