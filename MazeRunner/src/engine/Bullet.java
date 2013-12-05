package engine;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Bullet extends GameObject implements VisibleObject {
	public double horAngle;
	public double verAngle;
	public double speed;
	
	public Bullet(double x, double y, double z, double hAngle, double vAngle, double sp){
		super(x,y,z);
		horAngle = hAngle;
		verAngle = vAngle;		
		speed = sp;
	}
	
	public void display(GL gl){
		GLUT glut = new GLUT();
		gl.glColor3d(0.0, 0.0, 1.0);
		gl.glPushMatrix();
		gl.glTranslated(getLocationX(), getLocationY(), getLocationZ());
		glut.glutSolidSphere(0.05, 10, 10);
		gl.glPopMatrix();
	}
	
	public void update(int deltaTime){
		locationX -= Math.sin(Math.toRadians(horAngle)) * Math.cos(Math.toRadians(verAngle)) * speed
				* deltaTime;
		locationY += Math.sin(Math.toRadians(verAngle)) * speed 
				* deltaTime;
		locationZ -= Math.cos(Math.toRadians(horAngle)) * Math.cos(Math.toRadians(verAngle)) * speed
				* deltaTime;
	}
}
