package levelEditor;

import java.awt.event.MouseEvent;

import items.Roof;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.GLUT;

import menu.Teken;
import engine.ChangeGL;
import engine.Maze;
import engine.MazeRunner;
import enemies.EnemySpooky;

public class LevelEditorModelVieweroud {
	
	private static int previousMode=0;;
	private static final byte NIETS = 0;
	private static final byte KOLOM = 1;
	private static final byte MUUR = 2;
	private static final byte DAK = 3;
	private static final byte ITEM = 4;
	private static final byte WORLD = 5;
	
	
	private static final byte DRAAIMODE = 0;
	private static final byte ZOOMMODE = 1;
	
	private static int mousemode= DRAAIMODE;
	
	double rotationX;
	double rotationY;
	
	double scalef;
	
	
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
		
		scalef = screenWidth*50/782;
		
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
	
	public void display(GL gl, boolean catalogus, byte drawMode, int textureMode, int hoogteMode){
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
		//double scalef = screenWidth*50/782;
		
		
		if (!catalogus){
			switch (drawMode) {
			case NIETS: //niets
				break;
			case KOLOM: //kolom
				if (previousMode != drawMode){
					scalef = screenWidth*50/782;
					previousMode = drawMode;
				}
				
				height = Maze.getItemHeight();
				width = Maze.getColumnWidth();
				
				gl.glTranslated(width/2, height/2, width/2);
				gl.glRotated(rotationY, 0.25,0,0);
				gl.glRotated(rotationX,0,1,0);
				gl.glScaled(scalef,scalef,scalef);
				gl.glTranslated(-width/2, -height/2, -width/2);
				if (textureMode > 200) {
					Maze.paintColumnFromQuad(gl, 0, textureMode - 200);
				} else {
					Maze.paintColumnFromQuad(gl, 0, textureMode - 100);
				}
				break;
			case MUUR: 
				if (previousMode != drawMode){
					scalef = screenWidth*50/782;
					previousMode = drawMode;
				}
				
				height = Maze.getItemHeight();
				width = Maze.getColumnWidth();
				length = Maze.getWallWidth();
				
				gl.glTranslated(length/2, height/2, width/2);
				gl.glRotated(rotationY, 0.25,0,0);
				gl.glRotated(rotationX,0,1,0);
				gl.glScaled(scalef/2,scalef/2,scalef/2);
				gl.glTranslated(-length/2, -height/2, -width/2);
				if (textureMode > 200) {
					Maze.paintColumnFromQuad(gl, 0, textureMode - 200);
				} else {
					Maze.paintColumnFromQuad(gl, 0, textureMode - 100);
				}
				break;
				
				
			case DAK: //gebouw
				
				if (previousMode != drawMode){
					scalef = screenWidth*50/782;
					previousMode = drawMode;
				}
				
				height = Roof.getRoofHeight();
				length= Roof.getRoofLength();
				gl.glTranslated(length/2, height/2, length/2);
				gl.glRotated(rotationY, 0.25,0,0);
				gl.glRotated(rotationX,0,1,0);
				gl.glScaled(scalef/2,scalef/2,scalef/2);
				gl.glTranslated(-length/2, -height/2, -length/2);
				
					items.Roof.drawRoof(gl);
				break;
			case ITEM:
				
				if (previousMode != drawMode){
					scalef = screenWidth*50/782;
					previousMode = drawMode;
				}
				
				gl.glTranslated(0, -scalef, 0);
				
				gl.glRotated(rotationY, 0.25,0,0);
				gl.glRotated(rotationX,0,1,0);
				
				gl.glScaled(scalef*2,scalef*2,scalef*2);
				EnemySpooky.showEnemy(gl);
				//MazeRunner.visibleIterator(gl);
				break;
				
			case WORLD:
				
			//MazeRunner.visibleIterator( gl);
				
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
		
		scalef = screenWidth*50/782;
	}
	
	public void mouseReleased(MouseEvent e){
		mousemode = DRAAIMODE;
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
		
		if (e.getButton()==2 || e.getButton()==3){
			mousemode = ZOOMMODE;
		}
		
		if (e.getClickCount()>=2 && e.getButton()==2 || e.getButton()==3) {
			scalef=screenWidth*50/782;
		}
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
		}
		
		if (mousemode==DRAAIMODE){
		rotationX= rotationX+dX;
		
		rotationY= rotationY+dY;
		}
		if (mousemode ==ZOOMMODE){
			//scalef=scalef+dX;
			scalef=scalef+dY;
			if (scalef<0){scalef=0;}
		}
		dX=0;
		dY=0;
		
	}
	
}
	
	

