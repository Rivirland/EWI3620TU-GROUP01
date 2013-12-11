package levelEditor;

import java.awt.event.MouseEvent;
import items.Roof;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.GLUT;

import menu.Teken;
import engine.ChangeGL;
import engine.Maze;

public class LevelEditorModelViewer {

	private static final byte NIETS = 0;
	private static final byte KOLOM = 1;
	private static final byte MUUR = 2;
	private static final byte DAK = 3;
	private static final byte GEBOUW = 4;
	
	double rotationX;
	double rotationY;
	
	
	GLUT glut;
	int screenWidth, screenHeight;
	//GLAutoDrawable drawable;
		
	int x1,y1,x2,y2;
	int x1mouse, y1mouse, x2mouse, y2mouse;
	int dX, dY;
	
	public LevelEditorModelViewer(int screenWidth, int screenHeight, int x1, int y1, int x2, int y2){
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
		
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
		
	}
	
	private void init(GL gl){
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glOrtho(0,0,screenWidth,screenHeight, -1000, 1000);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glDisable(GL.GL_CULL_FACE);
		//gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_TEXTURE_2D);
	}
	
	public void display(GL gl, boolean catalogus, byte drawMode, byte textureMode, int hoogteMode){
		init(gl);
		GLUT glut=new GLUT();
		gl.glEnable(GL.GL_SCISSOR_TEST);
		gl.glScissor(x1,y1,(int) (x2*0.85),(int) (y2*0.89));
		//gl.glScissor(x1,y1,x2,y2);
		int xmidden= (x2+x1)/2;
		int ymidden= (y2+y1)/2;
		//gl.glViewport(x1+xmidden-ymidden, y1, x1+xmidden+ymidden, ymidden*2);
		//gl.glViewport(x1,y1,x2,y2);
		gl.glViewport(0, 0, screenWidth, screenHeight);
		
	gl.glClearColor(0,0,0,0);
	gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	
		gl.glPushMatrix();
		update();
		gl.glPushMatrix();
		
		gl.glTranslated(xmidden,y1, -10);
		gl.glRotated(360,rotationX,rotationY,0);
		//gl.glRotated(45,1,1,0);
		gl.glScaled(50,50,50);
		
		
		if (!catalogus){
			switch (drawMode) {
			case NIETS: //niets
				break;
			case KOLOM: //kolom
				switch (textureMode) {
				case 1:
					Maze.paintColumnFromQuad(gl, 1,2);
					break;
				case 2:
					Maze.paintColumnFromQuad(gl, 1,6);
					break;
				}
				break;
			case MUUR: //dak
				switch (textureMode) {
				case 1:
					Maze.paintWallZFromQuad(gl,1,2);
					break;
				case 2:
					Maze.paintWallZFromQuad(gl, 1, 6);
					break;
				}
				break;
			case DAK: //gebouw
					items.Roof.drawRoof(gl);
				break;
			case GEBOUW:
				
				break;
			}
		} 
		
		gl.glPopMatrix();
		//gl.glOrtho(0,0,screenWidth,screenHeight, -1, 1);
		//gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		//gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_LIGHT0);
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_SCISSOR_TEST);
	}
	
	public void reshape(int screenWidth, int screenHeight, int x1, int y1, int x2, int y2){
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
		
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
	}
	
	public void mouseReleased(MouseEvent e){
		
	}

	public void mouseDragged(MouseEvent e) {
		if (e.getX()>x1 && e.getX()<x2 && e.getY()>y1 && e.getY()<y2){
			x2mouse = e.getX();
			y2mouse = e.getY();
		}
		
		
	}
	
	public void mousePressed(MouseEvent e){
		if (e.getX()>x1 && e.getX()<x2 && e.getY()>y1 && e.getY()<y2){
			System.out.println("partystyle");
			System.out.println(e.getX());
			System.out.println(e.getY());
		x1mouse = e.getX();
		y1mouse = e.getY();
		x2mouse = x1;
		y2mouse = y1;
		}
	}
	
	public void update (){
		System.out.println(dX);
		dX = x1mouse - x2mouse;
		dY = y1mouse - y2mouse;
		x1mouse = x2mouse;
		y1mouse=y2mouse;
		rotateObject();
	}
	
	public void rotateObject (){
		
		rotationX= rotationX+dX;
		rotationY= rotationY+dY;
		
		
		dX=0;
		dY=0;
		
	}
	
}
	
	

