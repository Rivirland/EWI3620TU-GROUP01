package levelEditor;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;


import engine.MazeRunner;

import engine.Maze;

public class LevelEditorWorldViewer extends LevelEditorViewer{

	private static  ArrayList<Maze> mazelist;

	private static final byte WORLD = 1;
	private static final byte CURRENTLEVEL = 2;
	
	private double xcenter;
	private double ycenter;
	private double zcenter;
	
	private int minX, minZ, maxX, maxZ, minY, maxY;	
	
	public LevelEditorWorldViewer(int screenWidth, int screenHeight, double x1,
			double y1, double x2, double y2) {
		super(screenWidth, screenHeight, x1, y1, x2, y2);
		scalef=1;
	}

	
	public void minvalueX(){
		
	}

	public void centerLength(){
		LevelEditorWorldViewer.mazelist = MazeRunner.level.getMazeList();
		
		int size = mazelist.size();
		
		int minX = 0;
		int current = 0;
		// to calculate the min value
		minX = mazelist.get(0).mazeX;
		for (int i=1; i<size; i++){
			current =mazelist.get(i).mazeX;
			if (current < minX){
				minX = current;
			}
			this.minX = minX;
		}
		
		int minZ = 0;
		minZ = mazelist.get(0).mazeZ;
		for (int i=1; i<size; i++){
			current =mazelist.get(i).mazeZ;
			if (current < minZ){
				minZ = current;
			}
			this.minZ = minZ;
		}
		
		int maxX = 0;
		maxX = (int) mazelist.get(0).maxX;
		for (int i=1; i<size; i++){
			current =(int) mazelist.get(i).maxX;
			if (current > maxX){
				maxX = current;
			}
			this.maxX = maxX;
		}
		
		int maxZ = 0;
		minZ =(int) mazelist.get(0).maxZ;
		for (int i=1; i<size; i++){
			current =(int) mazelist.get(i).maxZ;
			if (current > maxZ){
				maxZ = current;
			}
			this.maxZ = maxZ;
		}
		
		int minY = 0;
		// to calculate the min value
		minY = mazelist.get(0).mazeY;
		for (int i=1; i<size; i++){
			current =mazelist.get(i).mazeY;
			if (current < minY){
				minY = current;
			}
			this.minY = minY;
		}
		
		int maxY = 0;
		// to calculate the max value
		maxY = mazelist.get(0).mazeY;
		for (int i=1; i<size; i++){
			current =mazelist.get(i).mazeY;
			if (current < maxY){
				maxY = current;
			}
			this.maxY = maxY;
		}
		
		this.xcenter = (minX+maxX)/2;
		this.ycenter = (minY+maxY)/2;
		this.zcenter = (minZ+maxZ)/2;
	}

	
	public void display(GL gl){
		init(gl);
		
		gl.glPushMatrix();
		
		gl.glTranslated(xmidden, ymidden, 0);
		gl.glTranslated(2*panX,2*panY, 0);
		//gl.glTranslated(2*panX,2*panY, 0);
		gl.glTranslated(xcenter*2, ycenter*2, zcenter*2); // center van rotatie veranderen
		
		gl.glRotated(rotationY, 0.25, 0, 0);
		gl.glRotated(rotationX, 0, 0.25, 0);
		gl.glScaled(scalef,scalef,scalef);
		
		gl.glTranslated(-xcenter, -ycenter,-zcenter);
		//gl.glTranslated(-2*panX,-2*panY, 0);
		
		MazeRunner.visibleIterator(gl);
		
		gl.glPopMatrix();
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_LIGHT0);
		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_SCISSOR_TEST);
		gl.glDisable(GL.GL_STENCIL_TEST);
	}

	public void mousePressed(MouseEvent e){
		superMousehandling(e);
		if (e.getClickCount()>=2 && e.getButton()==2 || e.getButton()==3) {
			mousemode = PANMODE;
		}
		System.out.println(mousemode +" this");
	}
}

