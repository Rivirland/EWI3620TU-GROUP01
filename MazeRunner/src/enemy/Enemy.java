package enemy;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

import new_default.*;

public class Enemy extends GameObject implements VisibleObject{
	private EnemyControl enemyControl;
	private double speed;
	private boolean left = true;
	private boolean right = true;
	private boolean forward = true;
	private boolean back = true;
	
	public Enemy(double x, double y, double z, double speed) {
		super(x,y,z);
		this.speed=speed;
	}
	public void setControl(EnemyControl enemyControl){
		this.enemyControl=enemyControl;
	}
	public EnemyControl getControl(){
		return this.enemyControl;
	}
	public void setSpeed(double speed){
		this.speed=speed;
	}
	public double getSpeed(){
		return this.speed;
	}
	
	public double getX(){
		return super.locationX;
	}
	
	public void setX(double x){
		super.locationX = x;
	}
	
	public double getZ(){
		return super.locationZ;
	}
	
	public void setZ(double z){
		super.locationZ = z;
	}
	
	public void update(int deltaTime){
		//if(enemyControl!=null){
			//enemyControl.update();
			
			if(left){
				setX(getX()-speed*deltaTime);
			}
			if(right){
				setX(getX()+speed*deltaTime);
			}
			if(forward){
				setX(getZ()-speed*deltaTime);
			}
			if(back){
				setX(getZ()+speed*deltaTime);
			}
			//}
	}
	@Override
	public void display(GL gl) {
		
		GLUT glut = new GLUT();
		gl.glColor3d(0.0,0.0,1.0);
		gl.glPushMatrix();
		
		gl.glTranslated(super.locationX,super.locationY,super.locationZ);
		glut.glutSolidSphere(1,30,30);
		
		gl.glPopMatrix();
		gl.glFlush();			      // Flush drawing routines
		  
		  		
	}

}
