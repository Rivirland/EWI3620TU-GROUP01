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
	private static final byte ITEM = 4;
	
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
		gl.glPushMatrix();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glOrtho(0,0,screenWidth,screenHeight, -1000, 1000);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_TEXTURE_2D);
	}
	
	private void stencilCut(GL gl){
		gl.glClearStencil( 0 );
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_STENCIL_BUFFER_BIT ); 
		gl.glLoadIdentity();
		gl.glColorMask(false, false, false, false);
		gl.glEnable(GL.GL_STENCIL_TEST);
		gl.glStencilFunc( GL.GL_ALWAYS, 1, 1 );
		gl.glStencilOp( GL.GL_REPLACE, GL.GL_REPLACE, GL.GL_REPLACE );
		
		//gl.glBindTexture (GL.GL_TEXTURE_2D, 2);
		gl.glColor3d(1,1,1);
		Teken.rechthoek(gl,x1,y1,(int) (x2*0.85),(int) (y2*0.89));
		int pixels = 0;
		//System.out.println(gl.glReadPixels(0, 0, 0, 0, GL.GL_STENCIL_INDEX, GL.GL_INT, null));
		System.out.println(pixels);
		gl.glColorMask(true,true,true,true);
		gl.glStencilFunc(GL.GL_NOTEQUAL, 1, 1);
		gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_KEEP);
		gl.glLoadIdentity();
		
	}
	
	public void display(GL gl, boolean catalogus, byte drawMode, byte textureMode, int hoogteMode){
		init(gl);
		GLUT glut=new GLUT();
		gl.glEnable(GL.GL_SCISSOR_TEST);
		gl.glScissor(x1,y1,(int) (x2*0.85),(int) (y2*0.89));
		//stencilCut(gl);
		//gl.glScissor(x1,y1,x2,y2);
		int xmidden= (x2+x1)/2;
		int ymidden= (y2+y1)/2;
		//gl.glViewport(x1+xmidden-ymidden, y1, x1+xmidden+ymidden, ymidden*2);
		//gl.glViewport(x1,y1,x2,y2);
		//gl.glViewport(0, 0, screenWidth, screenHeight);
		
	gl.glClearColor(0,0,0,0);
	gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
	update();
		
		gl.glPushMatrix();
		
		gl.glTranslated(xmidden,ymidden, 0);

		double height;
		double width;
		double length;
		System.out.println(screenWidth); // 782
		double scalef = screenWidth*50/782;

		
		if (!catalogus){
			switch (drawMode) {
			case NIETS: //niets
				break;
			case KOLOM: //kolom
				height = Maze.getItemHeight();
				width = Maze.getColumnWidth();
				
				gl.glTranslated(width/2, height/2, width/2);
				gl.glRotated(rotationY, 0.25,0,0);
				gl.glRotated(rotationX,0,1,0);
				gl.glScaled(scalef,scalef,scalef);
				gl.glTranslated(-width/2, -height/2, -width/2);
				
				
				switch (textureMode) {
				case 1:
					Maze.paintColumnFromQuad(gl, 0,15);
					break;
				case 2:
					Maze.paintColumnFromQuad(gl, 0,16);
					break;
				}
				break;
			case MUUR: 
				height = Maze.getItemHeight();
				width = Maze.getColumnWidth();
				length = Maze.getWallWidth();
				
				gl.glTranslated(length/2, height/2, width/2);
				gl.glRotated(rotationY, 0.25,0,0);
				gl.glRotated(rotationX,0,1,0);
				gl.glScaled(scalef/2,scalef/2,scalef/2);
				gl.glTranslated(-length/2, -height/2, -width/2);
				switch (textureMode) {
				case 1:
					Maze.paintWallZFromQuad(gl,0,15);
					break;
				case 2:
					Maze.paintWallZFromQuad(gl, 0, 16);
					break;
				}
				break;
			case DAK: //gebouw
					items.Roof.drawRoof(gl);
				break;
			case ITEM:
				
				break;
			}
		} 
		
		gl.glPopMatrix();
		gl.glPopMatrix();
		//gl.glOrtho(0,0,screenWidth,screenHeight, -1, 1);
		//gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		//gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_LIGHT0);
		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_SCISSOR_TEST);
		gl.glDisable(GL.GL_STENCIL_TEST);
		
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
			
		x1mouse = e.getX();
		y1mouse = e.getY();
		x2mouse = x1mouse;
		y2mouse = y1mouse;
		}
	}
	
	public void update (){
		dX = x1mouse - x2mouse;
		dY = y1mouse - y2mouse;
		x1mouse = x2mouse;
		y1mouse=y2mouse;
		rotateObject();
	}
	
	public void rotateObject (){
		if (dX>0){
		System.out.println("x"+dX);
		System.out.println("y"+dY);
		}
		
		rotationX= rotationX+dX;
		
		rotationY= rotationY+dY;
		
		
		dX=0;
		dY=0;
		
	}
	
}
	
	

