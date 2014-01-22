package levelEditor;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.media.opengl.GL;

import menu.Teken;
import engine.ChangeGL;
import engine.Maze;
import engine.MazeRunner;
import engine.VisibleObject;
import engine.World;

public class LevelEditorWorldViewer extends LevelEditorViewer {

	private static ArrayList<Maze> mazelist;

	private static final byte WORLD = 1;
	private static final byte CURRENTLEVEL = 2;

	private double xcenter;
	private double ycenter;
	private double zcenter;

	public boolean run = true;
	private double minX, maxX, minZ, maxZ, minY, maxY;
	public static ArrayList<VisibleObject> visibleObjects = new ArrayList<VisibleObject>();

	public LevelEditorWorldViewer(int screenWidth, int screenHeight, double x1, double y1, double x2, double y2) {
		super(screenWidth, screenHeight, x1, y1, x2, y2);

	}

	public void init() {
		visibleObjects.clear();
		MazeRunner.roofList.clear();
		MazeRunner.portalList.clear();
		MazeRunner.enemyList.clear();
		MazeRunner.bulletList.clear();
		String filename="temp.txt";
		try {
			filename = LevelEditor.levels.saveAsTemp();
		} catch (Exception e) {
			System.out.println("Temp.txt, cannot load WorldViewer");
		}
		World.clearMazeList();
		World.leesLevels(filename);
		mazelist = World.getMazeList();
		fillVisibleObjects();
	}


	public void fillVisibleObjects() {
		// Add enemies
		if (MazeRunner.enemyList.size() > 0) {
			for (int i = 0; i < MazeRunner.enemyList.size(); i++) {
				visibleObjects.add(MazeRunner.enemyList.get(i));
			}
		}else {
			System.out.println("enemylist is empty, no enemies added to visibleObjects");
		}
		// Add roofs
		if (MazeRunner.roofList.size() > 0) {
			for (int i = 0; i < MazeRunner.roofList.size(); i++) {
				visibleObjects.add(MazeRunner.roofList.get(i));
			}
		}else {
			System.out.println("rooflist is empty, no roofs added to visibleObjects");
		}
		// Add mazes and itemList of maze
		if (mazelist.size() > 0) {
			for (int i = 0; i < mazelist.size(); i++) {
				Maze maze = mazelist.get(i);
				if (maze.itemList.size() > 0){
					for (int j=0; j< maze.itemList.size(); j++){
						visibleObjects.add(maze.itemList.get(j));
					}
				}
				visibleObjects.add(maze);
				
			}
			centerLength();
		} else {
			System.out.println("mazelist is empty, no mazes added to visibleObjects");
		}
	}

	public void centerLength() {
		int size = mazelist.size();
		// System.out.println("mazelist.size: " + size);

		double current;

		double minX = 0;
		// to calculate the min value
		minX = mazelist.get(0).mazeX;
		for (int i = 1; i < size; i++) {
			current = mazelist.get(i).mazeX;
			if (current < minX) {
				minX = current;
			}
		}
		this.minX = minX;

		double minZ = 0;
		minZ = mazelist.get(0).mazeZ;
		for (int i = 1; i < size; i++) {
			current = mazelist.get(i).mazeZ;
			if (current < minZ) {
				minZ = current;
			}
		}
		this.minZ = minZ;

		double maxX = 0;
		maxX = (int) mazelist.get(0).maxX;
		for (int i = 1; i < size; i++) {
			current = (int) mazelist.get(i).maxX;
			if (current > maxX) {
				maxX = current;
			}
		}
		this.maxX = maxX;

		double maxZ = 0;
		maxZ = (int) mazelist.get(0).maxZ;
		for (int i = 1; i < size; i++) {
			current = (int) mazelist.get(i).maxZ;
			if (current > maxZ) {
				maxZ = current;
			}
		}
		this.maxZ = maxZ;

		double minY = 0;
		// to calculate the min value
		minY = mazelist.get(0).mazeY;
		for (int i = 1; i < size; i++) {
			current = mazelist.get(i).mazeY;
			if (current < minY) {
				minY = current;
			}
		}
		this.minY = minY;

		double maxY = 0;
		// to calculate the max value
		// for now it doesn't calculate in the height of structures on a maze
		maxY = mazelist.get(0).mazeY;
		for (int i = 1; i < size; i++) {
			current = mazelist.get(i).mazeY;
			if (current > maxY) {
				maxY = current;
			}

		}

		this.maxY = maxY;

		this.xcenter = (minX + maxX) / 2;
		this.ycenter = (minY + maxY) / 2;
		this.zcenter = (minZ + maxZ) / 2;

		scalef = Math.abs((x2 - x1) / (maxX - minX));

	}


	public void display(GL gl) {

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_SCISSOR_TEST);


		stencil(gl);
		update();
		// gl.glLoadIdentity();

		gl.glPushMatrix();

		gl.glTranslated(xmidden, ymidden, 0);

//<<<<<<< HEAD
		gl.glTranslated(-panX, panY, 0);

		gl.glRotated(rotationY, 1, 0, 0);
		gl.glRotated(rotationX, 0, 1, 0);
		gl.glScaled(scalef, scalef, scalef);
		if (LevelEditor.selectedLevel >= 0) {
			LevelEditorLevel le = LevelEditor.levels.get(LevelEditor.selectedLevel);
			double[] location = le.getLocation();
			double xtrans = location[0];
			double ytrans = location[1];
			double ztrans = location[2];
			gl.glTranslated(-xtrans, -ytrans, -ztrans);
		}
		ChangeGL.GLtoTexturedItem(gl);

		gl.glDisable(GL.GL_LIGHTING);
		// MazeRunner.visibleIterator(gl);

		for (Iterator<VisibleObject> it = visibleObjects.iterator(); it.hasNext();) {
			VisibleObject next = it.next();
			next.display(gl);
		}
//=======
////		gl.glTranslated(-2*panX,2*panY, 0);
//		//gl.glTranslated(2*panX,2*panY, 0);
//		
//		
//		
//		gl.glTranslated(-xcenter, -ycenter, -zcenter); // center van rotatie veranderen	
//		
//		gl.glRotated(rotationY, 0.25, 0, 0);
//		gl.glRotated(rotationX, 0, 0.25, 0);
//	
//		gl.glTranslated(xcenter, ycenter, zcenter);
//		
//		gl.glScaled(scalef,scalef,scalef);
//		
//		MazeRunner.visibleIterator(gl);
//		
//>>>>>>> e8c7d5a69602ad6924df49dcd2df6f411c63b147
		gl.glPopMatrix();
		ChangeGL.GLtoColoredItem(gl);
		gl.glColor3d(1, 1, 1);

		gl.glDisable(GL.GL_DEPTH_TEST);
//		Teken.rechthoek(gl, xmidden - 5, ymidden - 5, xmidden + 5, ymidden + 5);
		gl.glDisable(GL.GL_SCISSOR_TEST);
		gl.glDisable(GL.GL_STENCIL_TEST);
	}

	public void mousePressed(MouseEvent e) {
		superMousehandling(e);
		if (e.getClickCount() >= 2 && e.getButton() == 2 || e.getButton() == 3) {
			mousemode = PANMODE;
		}
	}
}
