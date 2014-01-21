package levelEditor;

import items.Exit;
import items.Roof;

import java.awt.event.MouseEvent;

import javax.media.opengl.GL;

import menu.Teken;
import enemies.EnemySmart;
import enemies.EnemySpooky;
import engine.Maze;
import engine.Portal;

public class LevelEditorModelViewer extends LevelEditorViewer {

	private static int previousMode = 0;;
	private static final byte NIETS = 0;
	private static final byte KOLOM = 1;
	private static final byte MUUR = 2;
	private static final byte DAK = 3;
	private static final byte ITEM = 4;
	private static final byte WORLD = 5;

	public LevelEditorModelViewer(int screenWidth, int screenHeight, double x1, double y1, double x2, double y2) {
		super(screenWidth, screenHeight, x1, y1, x2, y2);
		scalef = screenWidth * 50 / 782;
	}

//	public void stencil(GL gl) {
//		gl.glEnable(GL.GL_STENCIL_TEST);
//		gl.glColorMask(false, false, false, false);
//		gl.glDepthMask(false);
//		gl.glStencilFunc(GL.GL_NEVER, 1, 0xFF);
//		gl.glStencilOp(GL.GL_REPLACE, GL.GL_KEEP, GL.GL_KEEP);
//
//		gl.glStencilMask(0xFF);
//		gl.glClear(GL.GL_STENCIL_BUFFER_BIT);
//
//		// Teken.rechthoek(gl, x1, y1, x2, y2);
//
//		gl.glBegin(GL.GL_TRIANGLE_FAN);
//
//		gl.glVertex2f(x1, y1);
//		gl.glVertex2f(x1, y2);
//		gl.glVertex2f(x2, y2);
//		gl.glVertex2f(x2, y1);
//
//		gl.glEnd();
//
//		// float xc,yc,angle;
//		// gl.glBegin(GL.GL_TRIANGLE_FAN);
//		// gl.glVertex2f((x2+x1)/2,(y2+y1)/2);
//		//
//		// for (angle=1.0f; angle <361; angle=0.2f+angle){
//		// xc = (float) ((x2+x1)/2+Math.sin(angle)*100);
//		// yc = (float) ((float) ((y2+y1)/2)+Math.cos(angle)*100);
//		//
//		// gl.glVertex2f(xc,yc);
//		// }
//		//
//		// gl.glEnd();
//		//
//
//		gl.glColorMask(true, true, true, true);
//		gl.glDepthMask(true);
//		gl.glStencilMask(0);
//
//		gl.glStencilFunc(GL.GL_EQUAL, 0, 0xFF);
//
//		gl.glStencilFunc(GL.GL_EQUAL, 1, 0xFF);
//
//	}

	public void display(GL gl, boolean catalogus, byte drawMode, int textureMode, int hoogteMode) {
		// init(gl);
		gl.glEnable(GL.GL_DEPTH_TEST);
		// gl.glEnable(GL.GL_SCISSOR_TEST);

		stencil(gl);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT); // zat eerst hieronder
		// gl.glScissor(x1,y1,(int) (x2*0.85),(int) (y2*0.89));

		// gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		update();

		gl.glPushMatrix();

		gl.glTranslated(xmidden, ymidden, 0);

		double height;
		double width;
		double length;
		// double scalef = screenWidth*50/782;

		if (!catalogus) {
			switch (drawMode) {
			case NIETS: // niets
				break;
			case KOLOM: // kolom
				if (previousMode != drawMode) {
					scalef = screenWidth * 50 / 782;
					previousMode = drawMode;
				}

				height = Maze.getItemHeight();
				width = Maze.getColumnWidth();

				gl.glTranslated(width / 2, height / 2, width / 2);
				gl.glRotated(rotationY, 0.25, 0, 0);
				gl.glRotated(rotationX, 0, 1, 0);
				gl.glScaled(scalef, scalef, scalef);
				gl.glTranslated(-width / 2, -height / 2, -width / 2);

				if (textureMode > 200) {
					Maze.paintColumnFromQuad(gl, 0, textureMode - 200);
				} else {
					Maze.paintColumnFromQuad(gl, 0, textureMode - 100);
				}
				break;
			case MUUR:
				if (previousMode != drawMode) {
					scalef = screenWidth * 50 / 782;
					previousMode = drawMode;
				}

				height = Maze.getItemHeight();
				width = Maze.getColumnWidth();
				length = Maze.getWallWidth();

				gl.glTranslated(length / 2, height / 2, width / 2);
				gl.glRotated(rotationY, 0.25, 0, 0);
				gl.glRotated(rotationX, 0, 1, 0);
				gl.glScaled(scalef / 2, scalef / 2, scalef / 2);
				gl.glTranslated(-length / 2, -height / 2, -width / 2);
				if (textureMode > 200) {
					Maze.paintDoorZFromQuad(gl, 0, textureMode - 200);
				} else {
					Maze.paintWallZFromQuad(gl, 0, textureMode - 100);
				}
				break;

			case DAK: // gebouw

				if (previousMode != drawMode) {
					scalef = screenWidth * 50 / 782;
					previousMode = drawMode;
				}

				height = Roof.getRoofHeight();
				length = Roof.getRoofLength();
				gl.glTranslated(length / 2, height / 2, length / 2);
				gl.glRotated(rotationY, 0.25, 0, 0);
				gl.glRotated(rotationX, 0, 1, 0);
				gl.glScaled(scalef / 2, scalef / 2, scalef / 2);
				gl.glTranslated(-length / 2, -height / 2, -length / 2);

				items.Roof.drawRoof(gl);
				break;
			case ITEM:

				if (previousMode != drawMode) {
					scalef = screenWidth * 50 / 782;
					previousMode = drawMode;
				}

				gl.glTranslated(0, -scalef, 0);

				gl.glRotated(rotationY, 0.25, 0, 0);
				gl.glRotated(rotationX, 0, 1, 0);

				gl.glScaled(scalef * 2, scalef * 2, scalef * 2);
				
				if(textureMode == 129){
	//				Portal.drawPortal(gl);
				}else if (textureMode == 229){
					EnemySpooky.showEnemy(gl);
				}else if (textureMode == 130){
					EnemySmart.showEnemy(gl);
				}else if (textureMode == 230){
					Teken.drawCuboid(gl, -0.5, 0.5, -0.5, 0.5, -0.5, 0.5, 27);
				}else if (textureMode == 131){
					Teken.drawCuboid(gl, -0.5, 0.5, -0.5, 0.5, -0.5, 0.5, 14);
				}else if (textureMode == 231){
					Exit.drawExit(gl);
				}
				
				// MazeRunner.visibleIterator(gl);
				break;

			case WORLD:

				// MazeRunner.visibleIterator( gl);

				break;
			}
		}

		gl.glPopMatrix();
//		gl.glPopMatrix();
		// gl.glOrtho(0,0,screenWidth,screenHeight, -1, 1);
		// gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		// gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_LIGHT0);
		gl.glDisable(GL.GL_DEPTH_TEST);
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_SCISSOR_TEST);
		gl.glDisable(GL.GL_STENCIL_TEST);

	}

	public void mousePressed(MouseEvent e) {
		superMousehandling(e);

		if (e.getClickCount() >= 2 && e.getButton() == 2 || e.getButton() == 3) {
			scalef = screenWidth * 50 / 782;
		}

	}

	public void reshape(int screenWidth, int screenHeight, int x1, int y1, int x2, int y2) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		xmidden = (x2 + x1) / 2;
		ymidden = (y2 + y1) / 2;

		scalef = screenWidth * 50 / 782;
	}

}
