package levelEditor;

import java.awt.Color;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;

import menu.KiesFileUitBrowser;
import menu.Teken;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import engine.ChangeGL;
import engine.InputDialog;
import engine.Maze;

public class LevelEditor {
	static final long serialVersionUID = 7526471155622776147L;

	// Screen size.
	private int screenWidth, screenHeight;
	private float buttonSize = screenHeight / 10.0f;

	// A GLCanvas is a component that can be added to a frame. The drawing
	// happens on this component.
	private GLCanvas canvas;

	private static final byte NIETS = 0;
	private static final byte KOLOM = 1;
	private static final byte MUUR = 2;
	private static final byte DAK = 3;
	private static final byte ITEM = 4;
	private byte drawMode = NIETS;

	private int textureMode = 0;
	private byte hoogteMode = 1;
	private boolean heightsOn = false;

	private int gridrows; // afmetingen grid
	private int gridcolumns;

	private float gridklikx = 10000; // 10000 is een default waarde buiten het
										// scherm, omdat je die waarde nooit kan
										// krijgen door ergens te klikken
	private float gridkliky = 10000;
	private float gridklikxrechts = 10000;
	private float gridklikyrechts = 10000;
	private boolean gridklik = false;

	private boolean catalogus = false;
	private Texture backTexture;

	private LevelEditorWorld levels;
	private double[] location;
	private int[][] wereld;
	private int[][] textures;
	private ArrayList<double[]> items;

	public static Texture wallTexture1;

	private int selectedLevel = 0;
	private int selectedLevelPrevious = 0;
	private boolean remove = false;
	private boolean open = false;
	private boolean worldview = false;

	private boolean addportaldirection = false;
	private float xportal;
	private float yportal;

	LevelEditorModelViewer modelviewer;
	LevelEditorWorldViewer worldviewer;

	/*
	 * private int [][] wereld = new int[gridrows][gridcolumns];
	 * 
	 * private void calculateGrid(){ for (int j=0; j<gridrows; j++){ for (int
	 * i=0; i < gridcolumns; i++){ wereld[i][j]=0; }} }
	 */

	private ArrayList<Point2D.Float> points;
	Array columncheckamount;

	/**
	 * When instantiating, a GLCanvas is added for us to play with. An animator
	 * is created to continuously render the canvas.
	 */
	public LevelEditor(GL gl, int screenWidth, int screenHeight, LevelEditorWorld levels) {
		// calculateGrid();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.levels = levels;
		this.location = levels.get(0).getLocation();
		this.wereld = levels.get(0).getGebouwen();
		this.textures = levels.get(0).getTextures();
		this.items = levels.get(0).getItemList();
		gridrows = (wereld.length - 1) / 2;
		gridcolumns = (wereld[0].length - 1) / 2;
		loadTextures(gl);
		modelviewer = new LevelEditorModelViewer(screenWidth, screenHeight, (90f / 1920f * screenWidth), (90f / 1080f * screenHeight), (589f / 1920f * screenWidth), (860f / 1080f * screenHeight));
		worldviewer = new LevelEditorWorldViewer(screenWidth, screenHeight, (775) / 1920f * screenWidth, (90f - 40f) / 1080f * screenHeight, 1880 / 1920f * screenWidth, 1050 / 1080f * screenHeight);
		// worldviewer = new LevelEditorWorldViewer(screenWidth, screenHeight,
		// screenWidth/2, screenHeight/2, screenWidth, screenHeight);
	}

	public void setScreen(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		modelviewer.reshape(screenWidth, screenHeight, (90f / 1920f * screenWidth), (90f / 1080f * screenHeight), (589f / 1920f * screenWidth), (860f / 1080f * screenHeight));
		worldviewer = new LevelEditorWorldViewer(screenWidth, screenHeight, (775) / 1920f * screenWidth, (90f - 40f) / 1080f * screenHeight, 1880 / 1920f * screenWidth, 1050 / 1080f * screenHeight);
	}

	/**
	 * A function defined in GLEventListener. This function is called many times
	 * per second and should contain the rendering code.
	 */
	public void display(GLAutoDrawable drawable, GL gl) {
		// Set the clear color and clear the screen.
		ChangeGL.GLto2D(gl);
		// gl.glClearColor(0, 0, 0, 0);
		gl.glViewport(0, 0, screenWidth, screenHeight);
		gl.glClearColor(0.34f, 0.11f, 0.13f, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		plaatsTexture(gl, 0f, 0f, 1920f, 1080f, 19);
		tekenLevelEditorAchtergrond(drawable, gl);

		levels.drawLevelList(drawable, gl, 622f / 1920f * screenWidth, 90f / 1080f * screenHeight, 740f / 1920f * screenWidth, 776f / 1080f * screenHeight, screenWidth, screenHeight, selectedLevel);
		updateLevel();

		// als er geen level is geselecteerd dan komt er geen grid

		if (selectedLevel >= 0 && worldview == false) {
			Teken.textDrawMetKleur(drawable, gl, (levels.getStartingBullets() + ", " + levels.getStartingTraps()), 622f / 1920f * screenWidth, 1010f / 1080f * screenHeight, 30, 1f, 1f, 1f);
			Teken.textDrawMetKleur(drawable, gl, (gridcolumns + " x " + gridrows), 622f / 1920f * screenWidth, 948f / 1080f * screenHeight, 30, 1f, 1f, 1f);
			Teken.textDrawMetKleur(drawable, gl, (location[0] + ", " + location[1] + ", " + location[2]), 622f / 1920f * screenWidth, 890f / 1080f * screenHeight, 30, 1f, 1f, 1f);
			Teken.textDrawMetKleur(drawable, gl, "DAK", 356f / 1920f * screenWidth, 890f / 1080f * screenHeight, 30, 1f, 1f, 1f);
			Teken.textDrawMetKleur(drawable, gl, "ITEM", 489f / 1920f * screenWidth, 890f / 1080f * screenHeight, 30, 1f, 1f, 1f);
			drawGrid(gl, 830f / 1920f * screenWidth, 90f / 1080f * screenHeight, 1830f / 1920f * screenWidth, 990f / 1080f * screenHeight, gridcolumns, gridrows);
			drawGridInhoud(drawable, gl);
			// veranderMatrixVolgensKlikInGrid(gl);
		}

		modelviewer.display(gl, catalogus, drawMode, textureMode, hoogteMode);
		Catalogus.drawCatalogus(gl, catalogus, drawMode, screenWidth, screenHeight, this);

		if (worldview) {
			worldviewer.display(gl);
		}
		// Teken.rechthoek(gl, (775)/1920f*screenWidth,
		// (90f-40f)/1080f*screenHeight, 1880/1920f*screenWidth,
		// 1050/1080f*screenHeight);
		gl.glFlush();
	}

	/**
	 * A method that draws the top left buttons on the screen.
	 * 
	 * @param gl
	 */
	private void tekenButton(GL gl, float xmin, float ymin, float xmax, float ymax) {
		gl.glColor3f(0.16f, 0.16f, 0.16f);
		rechthoek(gl, xmin, ymin, xmax, ymax);

		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, xmin, ymin, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmin, ymax);
		lineOnScreen(gl, xmin, ymin, xmin, ymax);
	}

	private void tekenButtonMetKleur(GL gl, float xmin, float ymin, float xmax, float ymax, float r, float g, float b) {
		gl.glColor3f(r, g, b);
		rechthoek(gl, xmin, ymin, xmax, ymax);

		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, xmin, ymin, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmin, ymax);
		lineOnScreen(gl, xmin, ymin, xmin, ymax);
	}

	/**
	 * If the window is updated, or the gridsize is changed, updateGrid will be
	 * called and call upon certain methods to make sure that the grid will
	 * grade the mouse coordinates when clicked appropriately
	 */

	private void drawGrid(GL gl, float xmin, float ymin, float xmax, float ymax, int gridcolumns, int gridrows) {

		// bepaal afmetingen gecentreerde grid
		float xmidden = (xmax + xmin) / 2;
		float ymidden = (ymax + ymin) / 2;

		float columndist = (xmax - xmin) / (gridcolumns);
		float rowdist = (ymax - ymin) / (gridrows);
		float distance = 1; // willekeurig

		if (columndist < rowdist) {
			distance = columndist;
			ymin = ymidden - distance * gridrows / 2;
			ymax = ymidden + distance * gridrows / 2;
		} else if (columndist > rowdist) {
			distance = rowdist;
			xmin = xmidden - (distance * gridcolumns) / 2;
			xmax = xmidden + (distance * gridcolumns) / 2;
		}

		// grijs voor grid
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, xmin, ymin, xmax, ymax);
		gl.glColor3f(0.5f, 0.5f, 0.5f);

		// lijnen voor grid
		for (float i = xmin; i <= xmax + 1; i = i + distance) { // xmax is plus
																// 1 omdat soms
																// de laatste
																// lijn niet
																// getekend
																// wordt
			lineOnScreen(gl, i, ymin, i, ymax);
		}

		for (float i = ymin; i <= ymax + 1; i = i + distance) { // ymax is plus
																// 1 omdat soms
																// de laatste
																// lijn niet
																// getekend
																// wordt
			lineOnScreen(gl, xmin, i, xmax, i);
		}

	}

	private void rechthoek(GL gl, float x, float y, float x2, float y2) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x2, y);
		gl.glVertex2f(x2, y2);
		gl.glVertex2f(x, y2);
		gl.glEnd();
	}

	public void tekenLevelEditorAchtergrond(GLAutoDrawable drawable, GL gl) {

		// kolom knop
		float columnbxmin = 90f / 1920f * screenWidth;
		float columnbymin = 890f / 1080f * screenHeight;
		float columnbxmax = 190f / 1920f * screenWidth;
		float columnbymax = 990f / 1080f * screenHeight;

		// muur knop
		float wallbxmin = 223f / 1920f * screenWidth;
		float wallbymin = 890f / 1080f * screenHeight;
		float wallbxmax = 323f / 1920f * screenWidth;
		float wallbymax = 990f / 1080f * screenHeight;

		// dak knop
		float roofbxmin = 356f / 1920f * screenWidth;
		float roofbymin = 890f / 1080f * screenHeight;
		float roofbxmax = 456f / 1920f * screenWidth;
		float roofbymax = 990f / 1080f * screenHeight;

		// gebouw knop
		float buildbxmin = 489f / 1920f * screenWidth;
		float buildbymin = 890f / 1080f * screenHeight;
		float buildbxmax = 589f / 1920f * screenWidth;
		float buildbymax = 990f / 1080f * screenHeight;

		// homebutton
		tekenButtonMetKleur(gl, 15f / 1920f * screenWidth, 1000f / 1080f * screenHeight, 85f / 1920f * screenWidth, 1070f / 1080f * screenHeight, 0.3f, 0.3f, 0.8f);

		// button1
		if (drawMode == 1)
			tekenButtonMetKleur(gl, columnbxmin, columnbymin, columnbxmax, columnbymax, 0.76f, 0.76f, 0.76f);
		else
			tekenButton(gl, columnbxmin, columnbymin, columnbxmax, columnbymax);

		gl.glColor3f(0.71f, 0.90f, 0.11f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(128f / 1920f * screenWidth, 904f / 1080f * screenHeight);
		gl.glVertex2f(148f / 1920f * screenWidth, 904f / 1080f * screenHeight);
		gl.glVertex2f(148f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glVertex2f(128f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glEnd();
		gl.glColor3f(0.13f, 0.69f, 0.30f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(128f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glVertex2f(148f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glVertex2f(161f / 1920f * screenWidth, 974f / 1080f * screenHeight);
		gl.glVertex2f(141f / 1920f * screenWidth, 974f / 1080f * screenHeight);
		gl.glEnd();
		gl.glColor3f(0.42f, 0.68f, 0.14f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(148f / 1920f * screenWidth, 904f / 1080f * screenHeight);
		gl.glVertex2f(161f / 1920f * screenWidth, 914f / 1080f * screenHeight);
		gl.glVertex2f(161f / 1920f * screenWidth, 974f / 1080f * screenHeight);
		gl.glVertex2f(148f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glEnd();

		// button2
		if (drawMode == 2)
			tekenButtonMetKleur(gl, wallbxmin, wallbymin, wallbxmax, wallbymax, 0.76f, 0.76f, 0.76f);
		else
			tekenButton(gl, wallbxmin, wallbymin, wallbxmax, wallbymax);

		gl.glColor3f(0.71f, 0.90f, 0.11f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(241f / 1920f * screenWidth, 911f / 1080f * screenHeight);
		gl.glVertex2f(293f / 1920f * screenWidth, 911f / 1080f * screenHeight);
		gl.glVertex2f(293f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glVertex2f(241f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glEnd();
		gl.glColor3f(0.13f, 0.69f, 0.30f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(241f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glVertex2f(293f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glVertex2f(307f / 1920f * screenWidth, 973f / 1080f * screenHeight);
		gl.glVertex2f(253f / 1920f * screenWidth, 973f / 1080f * screenHeight);
		gl.glEnd();
		gl.glColor3f(0.42f, 0.68f, 0.14f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(293f / 1920f * screenWidth, 911f / 1080f * screenHeight);
		gl.glVertex2f(307f / 1920f * screenWidth, 922f / 1080f * screenHeight);
		gl.glVertex2f(307f / 1920f * screenWidth, 973f / 1080f * screenHeight);
		gl.glVertex2f(293f / 1920f * screenWidth, 964f / 1080f * screenHeight);
		gl.glEnd();

		// button3
		if (drawMode == 3)
			tekenButtonMetKleur(gl, roofbxmin, roofbymin, roofbxmax, roofbymax, 0.76f, 0.76f, 0.76f);
		else
			tekenButton(gl, roofbxmin, roofbymin, roofbxmax, roofbymax);

		// button4
		if (drawMode == 4)
			tekenButtonMetKleur(gl, buildbxmin, buildbymin, buildbxmax, buildbymax, 0.76f, 0.76f, 0.76f);
		else
			tekenButton(gl, buildbxmin, buildbymin, buildbxmax, buildbymax);

		// zwart onder de buttons
		gl.glColor3f(0f, 0f, 0f);
		rechthoek(gl, 90f / 1920f * screenWidth, 90f / 1080f * screenHeight, 589f / 1920f * screenWidth, 860f / 1080f * screenHeight);

		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, 90f / 1920f * screenWidth, 90f / 1080f * screenHeight, 589f / 1920f * screenWidth, 90f / 1080f * screenHeight);
		lineOnScreen(gl, 589f / 1920f * screenWidth, 90f / 1080f * screenHeight, 589f / 1920f * screenWidth, 860f / 1080f * screenHeight);
		lineOnScreen(gl, 589f / 1920f * screenWidth, 860f / 1080f * screenHeight, 90f / 1920f * screenWidth, 860f / 1080f * screenHeight);
		lineOnScreen(gl, 90f / 1920f * screenWidth, 860f / 1080f * screenHeight, 90f / 1920f * screenWidth, 90f / 1080f * screenHeight);

		gl.glColor3f(0.76f, 0.76f, 0.76f);
		// grijs hoogte min
		rechthoek(gl, (340f - 55f) / 1920f * screenWidth, (90f - 40f) / 1080f * screenHeight, (340f - 35f) / 1920f * screenWidth, (90f - 20f) / 1080f * screenHeight);
		// grijs hoogte midden
		rechthoek(gl, (340f - 20f) / 1920f * screenWidth, (90f - 40f) / 1080f * screenHeight, (340f + 20f) / 1920f * screenWidth, (90f - 20f) / 1080f * screenHeight);
		Teken.textDrawMetKleur(drawable, gl, String.valueOf(hoogteMode), (340f - 15f) / 1920f * screenWidth, (90f - 38f) / 1080f * screenHeight, 30, 0, 0, 0);
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		// grijs hoogte plus
		rechthoek(gl, (340f + 35f) / 1920f * screenWidth, (90f - 40f) / 1080f * screenHeight, (340f + 55f) / 1920f * screenWidth, (90f - 20f) / 1080f * screenHeight);

		// grijs min boven
		rechthoek(gl, (1330f - 28f) / 1920f * screenWidth, (990f + 20f) / 1080f * screenHeight, (1330f - 8f) / 1920f * screenWidth, (990f + 40f) / 1080f * screenHeight);
		// grijs plus boven
		rechthoek(gl, (1330f + 8f) / 1920f * screenWidth, (990f + 20f) / 1080f * screenHeight, (1330f + 28f) / 1920f * screenWidth, (990f + 40f) / 1080f * screenHeight);
		// grijs min onder
		rechthoek(gl, (1330f - 28f) / 1920f * screenWidth, (90f - 40f) / 1080f * screenHeight, (1330f - 8f) / 1920f * screenWidth, (90f - 20f) / 1080f * screenHeight);
		// grijs plus onder
		rechthoek(gl, (1330f + 8f) / 1920f * screenWidth, (90f - 40f) / 1080f * screenHeight, (1330f + 28f) / 1920f * screenWidth, (90f - 20f) / 1080f * screenHeight);
		// grijs min links
		rechthoek(gl, (830f - 40f) / 1920f * screenWidth, (540f + 28f) / 1080f * screenHeight, (830f - 20f) / 1920f * screenWidth, (540f + 8f) / 1080f * screenHeight);
		// grijs plus links
		rechthoek(gl, (830f - 40f) / 1920f * screenWidth, (540f - 8f) / 1080f * screenHeight, (830f - 20f) / 1920f * screenWidth, (540f - 28f) / 1080f * screenHeight);
		// grijs min rechts
		rechthoek(gl, (1830f + 20f) / 1920f * screenWidth, (540f + 28f) / 1080f * screenHeight, (1830f + 40f) / 1920f * screenWidth, (540f + 8f) / 1080f * screenHeight);
		// grijs plus rechts
		rechthoek(gl, (1830f + 20f) / 1920f * screenWidth, (540f - 8f) / 1080f * screenHeight, (1830f + 40f) / 1920f * screenWidth, (540f - 28f) / 1080f * screenHeight);

		// grijs naast de buttons
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		rechthoek(gl, 622f / 1920f * screenWidth, 1010f / 1080f * screenHeight, 740f / 1920f * screenWidth, 1052f / 1080f * screenHeight);
		rechthoek(gl, 622f / 1920f * screenWidth, 948f / 1080f * screenHeight, 740f / 1920f * screenWidth, 990f / 1080f * screenHeight);
		rechthoek(gl, 622f / 1920f * screenWidth, 890f / 1080f * screenHeight, 740f / 1920f * screenWidth, 932f / 1080f * screenHeight);

		// levelbuttons
		tekenButton(gl, 622f / 1920f * screenWidth, (860f - 51f) / 1080f * screenHeight, (622f + 51f) / 1920f * screenWidth, 860f / 1080f * screenHeight);
		gl.glColor3f(1f, 1f, 1f);
		Teken.lineOnScreen(gl, 632f / 1920f * screenWidth, (860f - 25f) / 1080f * screenHeight, (622f + 41f) / 1920f * screenWidth, (860f - 25f) / 1080f * screenHeight);
		Teken.lineOnScreen(gl, (622f + 25f) / 1920f * screenWidth, (860f - 41f) / 1080f * screenHeight, (622f + 25f) / 1920f * screenWidth, 850f / 1080f * screenHeight);
		tekenButton(gl, (740f - 51f) / 1920f * screenWidth, (860f - 51f) / 1080f * screenHeight, 740f / 1920f * screenWidth, 860f / 1080f * screenHeight);

	}

	/**
	 * Help method that uses GL calls to draw a line.
	 */
	private void lineOnScreen(GL gl, float x1, float y1, float x2, float y2) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		gl.glEnd();
	}

	public void updateLevel() {
		if ((selectedLevelPrevious != selectedLevel || open || (remove && levels.getSize() != selectedLevel)) && levels.getSize() > 0) {
			this.location = levels.get(selectedLevel).getLocation();
			this.wereld = levels.get(selectedLevel).getGebouwen();
			this.textures = levels.get(selectedLevel).getTextures();
			this.items = levels.get(selectedLevel).getItemList();
			gridrows = (wereld.length - 1) / 2;
			gridcolumns = (wereld[0].length - 1) / 2;
			selectedLevelPrevious = selectedLevel;
			remove = false;
			open = false;
			System.out.println("update");
		} else {
			try {
				levels.get(selectedLevel).setLocation(this.location);
				levels.get(selectedLevel).setGebouwen(this.wereld);
				levels.get(selectedLevel).setTextures(this.textures);
				levels.get(selectedLevel).setItemList(this.items);
			} catch (IndexOutOfBoundsException e) {
				selectedLevel--;
				selectedLevelPrevious = selectedLevel;
			}
		}
	}

	/**
	 * A function defined in MouseListener. Is called when the pointer is in the
	 * GLCanvas, and a mouse button is released.
	 */
	public void mouseReleased(MouseEvent me) {
		// Check if the coordinates correspond to any of the top left buttons
		modelviewer.mouseReleased(me);
		if (worldview) {
			worldviewer.mouseReleased(me);
		}

		// afmetingen grid
		// klikken op grid
		float xmin = 830f / 1920f * screenWidth;
		float ymin = 90f / 1080f * screenHeight;
		float xmax = 1830f / 1920f * screenWidth;
		float ymax = 990f / 1080f * screenHeight;

		float xmidden = (xmax + xmin) / 2;
		float ymidden = (ymax + ymin) / 2;

		float columndist = (xmax - xmin) / (gridcolumns);
		float rowdist = (ymax - ymin) / (gridrows);
		float distance = 1; // willekeurig

		if (columndist < rowdist) {
			distance = columndist;
			ymin = ymidden - distance * gridrows / 2;
			ymax = ymidden + distance * gridrows / 2;
		} else if (columndist > rowdist) {
			distance = rowdist;
			xmin = xmidden - (distance * gridcolumns) / 2;
			xmax = xmidden + (distance * gridcolumns) / 2;
		}

		// linkermuisknop
		if (me.getButton() == 1) {
			// drawMode knoppen
			if (90f / 1080f * screenHeight < me.getY() && me.getY() < 190f / 1080f * screenHeight) {
				if (90f / 1920f * screenWidth < me.getX() && me.getX() < 190f / 1920f * screenWidth) {
					// The first button is clicked
					drawMode = KOLOM;
					System.out.println("Mode: KOLOM");
					catalogus = true;
				} else if (223f / 1920f * screenWidth < me.getX() && me.getX() < 323f / 1920f * screenWidth) {
					// The second button is clicked
					drawMode = MUUR;
					System.out.println("Mode: MUUR");
					catalogus = true;
				} else if (356f / 1920f * screenWidth < me.getX() && me.getX() < 456f / 1920f * screenWidth) {
					// The third button is clicked
					drawMode = DAK;
					System.out.println("Mode: DAK");
					catalogus = true;
				} else if (489f / 1920f * screenWidth < me.getX() && me.getX() < 589f / 1920f * screenWidth) {
					// The fourth button is clicked
					drawMode = ITEM;
					System.out.println("Mode: ITEM");
					catalogus = true;
				}

			}

			// catalogus knoppen
			if (catalogus) {
				if ((1 - 851.25f / 1080) * screenHeight < me.getY() && me.getY() < (1 - 751.25f / 1080) * screenHeight) {
					if (109.8f / 1920 * screenWidth < me.getX() && me.getX() < 209.8f / 1920 * screenWidth) {
						// The first button is clicked
						textureMode = 129;
						System.out.println("Catalogus: TEXTURE 1");
						catalogus = false;
					}
					if (229.6f / 1920 * screenWidth < me.getX() && me.getX() < 329.6f / 1920 * screenWidth) {
						// The second button is clicked
						textureMode = 229;
						System.out.println("Catalogus: TEXTURE 2");
						catalogus = false;
					}
					if (349.4f / 1920 * screenWidth < me.getX() && me.getX() < 449.4f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 130;
						System.out.println("Catalogus: TEXTURE 3");
						catalogus = false;
					}
					if (469.2f / 1920 * screenWidth < me.getX() && me.getX() < 569.2f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 230;
						System.out.println("Catalogus: TEXTURE 4");
						catalogus = false;
					}
				}
				if ((1 - 742.50f / 1080) * screenHeight < me.getY() && me.getY() < (1 - 642.50f / 1080) * screenHeight) {
					if (109.8f / 1920 * screenWidth < me.getX() && me.getX() < 209.8f / 1920 * screenWidth) {
						// The first button is clicked
						textureMode = 131;
						System.out.println("Catalogus: TEXTURE 5");
						catalogus = false;
					}
					if (229.6f / 1920 * screenWidth < me.getX() && me.getX() < 329.6f / 1920 * screenWidth) {
						// The second button is clicked
						textureMode = 231;
						System.out.println("Catalogus: TEXTURE 6");
						catalogus = false;
					}
					if (349.4f / 1920 * screenWidth < me.getX() && me.getX() < 449.4f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 132;
						System.out.println("Catalogus: TEXTURE 7");
						catalogus = false;
					}
					if (469.2f / 1920 * screenWidth < me.getX() && me.getX() < 569.2f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 232;
						System.out.println("Catalogus: TEXTURE 8");
						catalogus = false;
					}
				}
				if ((1 - 633.75f / 1080) * screenHeight < me.getY() && me.getY() < (1 - 533.75f / 1080) * screenHeight) {
					if (109.8f / 1920 * screenWidth < me.getX() && me.getX() < 209.8f / 1920 * screenWidth) {
						// The first button is clicked
						textureMode = 133;
						System.out.println("Catalogus: TEXTURE 9");
						catalogus = false;
					}
					if (229.6f / 1920 * screenWidth < me.getX() && me.getX() < 329.6f / 1920 * screenWidth) {
						// The second button is clicked
						textureMode = 233;
						System.out.println("Catalogus: TEXTURE 10");
						catalogus = false;
					}
					if (349.4f / 1920 * screenWidth < me.getX() && me.getX() < 449.4f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 134;
						System.out.println("Catalogus: TEXTURE 11");
						catalogus = false;
					}
					if (469.2f / 1920 * screenWidth < me.getX() && me.getX() < 569.2f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 234;
						System.out.println("Catalogus: TEXTURE 12");
						catalogus = false;
					}
				}
				if ((1 - 525f / 1080) * screenHeight < me.getY() && me.getY() < (1 - 425f / 1080) * screenHeight) {
					if (109.8f / 1920 * screenWidth < me.getX() && me.getX() < 209.8f / 1920 * screenWidth) {
						// The first button is clicked
						textureMode = 135;
						System.out.println("Catalogus: TEXTURE 13");
						catalogus = false;
					}
					if (229.6f / 1920 * screenWidth < me.getX() && me.getX() < 329.6f / 1920 * screenWidth) {
						// The second button is clicked
						textureMode = 235;
						System.out.println("Catalogus: TEXTURE 14");
						catalogus = false;
					}
					if (349.4f / 1920 * screenWidth < me.getX() && me.getX() < 449.4f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 136;
						System.out.println("Catalogus: TEXTURE 15");
						catalogus = false;
					}
					if (469.2f / 1920 * screenWidth < me.getX() && me.getX() < 569.2f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 236;
						System.out.println("Catalogus: TEXTURE 16");
						catalogus = false;
					}
				}
				if ((1 - 416.25f / 1080) * screenHeight < me.getY() && me.getY() < (1 - 316.25f / 1080) * screenHeight) {
					if (109.8f / 1920 * screenWidth < me.getX() && me.getX() < 209.8f / 1920 * screenWidth) {
						// The first button is clicked
						textureMode = 137;
						System.out.println("Catalogus: TEXTURE 17");
						catalogus = false;
					}
					if (229.6f / 1920 * screenWidth < me.getX() && me.getX() < 329.6f / 1920 * screenWidth) {
						// The second button is clicked
						textureMode = 237;
						System.out.println("Catalogus: TEXTURE 18");
						catalogus = false;
					}
					if (349.4f / 1920 * screenWidth < me.getX() && me.getX() < 449.4f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 138;
						System.out.println("Catalogus: TEXTURE 19");
						catalogus = false;
					}
					if (469.2f / 1920 * screenWidth < me.getX() && me.getX() < 569.2f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 238;
						System.out.println("Catalogus: TEXTURE 20");
						catalogus = false;
					}
				}
				if ((1 - 307.50f / 1080) * screenHeight < me.getY() && me.getY() < (1 - 207.50f / 1080) * screenHeight) {
					if (109.8f / 1920 * screenWidth < me.getX() && me.getX() < 209.8f / 1920 * screenWidth) {
						// The first button is clicked
						textureMode = 139;
						System.out.println("Catalogus: TEXTURE 21");
						catalogus = false;
					}
					if (229.6f / 1920 * screenWidth < me.getX() && me.getX() < 329.6f / 1920 * screenWidth) {
						// The second button is clicked
						textureMode = 239;
						System.out.println("Catalogus: TEXTURE 22");
						catalogus = false;
					}
					if (349.4f / 1920 * screenWidth < me.getX() && me.getX() < 449.4f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 140;
						System.out.println("Catalogus: TEXTURE 23");
						catalogus = false;
					}
					if (469.2f / 1920 * screenWidth < me.getX() && me.getX() < 569.2f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 240;
						System.out.println("Catalogus: TEXTURE 24");
						catalogus = false;
					}
				}
				if ((1 - 198.75f / 1080) * screenHeight < me.getY() && me.getY() < (1 - 98.75f / 1080) * screenHeight) {
					if (109.8f / 1920 * screenWidth < me.getX() && me.getX() < 209.8f / 1920 * screenWidth) {
						// The first button is clicked
						textureMode = 141;
						System.out.println("Catalogus: TEXTURE 25");
						catalogus = false;
					}
					if (229.6f / 1920 * screenWidth < me.getX() && me.getX() < 329.6f / 1920 * screenWidth) {
						// The second button is clicked
						textureMode = 241;
						System.out.println("Catalogus: TEXTURE 26");
						catalogus = false;
					}
					if (349.4f / 1920 * screenWidth < me.getX() && me.getX() < 449.4f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 142;
						System.out.println("Catalogus: TEXTURE 27");
						catalogus = false;
					}
					if (469.2f / 1920 * screenWidth < me.getX() && me.getX() < 569.2f / 1920 * screenWidth) {
						// The third button is clicked
						textureMode = 242;
						System.out.println("Catalogus: TEXTURE 28");
						catalogus = false;
					}
				}

			}

			// hoogte knoppen

			if ((1 - (90f - 20f) / 1080) * screenHeight < me.getY() && me.getY() < (1 - (90f - 40f) / 1080) * screenHeight) {
				// minknop
				if ((340f - 55f) / 1920 * screenWidth < me.getX() && me.getX() < (340f - 35f) / 1920 * screenWidth) {
					if (hoogteMode > 1) {
						hoogteMode--;
					}
					System.out.println("Hoogte: " + hoogteMode);
				}
				// plusknop
				if ((340f + 35f) / 1920 * screenWidth < me.getX() && me.getX() < (340f + 55f) / 1920 * screenWidth) {
					hoogteMode++;
					System.out.println("Hoogte: " + hoogteMode);
				}
			}

			// grid knoppen

			if ((1f - (990f + 20f) / 1080f) * screenHeight > me.getY() && me.getY() > (1f - (990f + 40f) / 1080f) * screenHeight) {
				if ((1330f - 28f) / 1920f * screenWidth < me.getX() && me.getX() < (1330f - 8f) / 1920f * screenWidth) {
					// minknop boven
					if (gridrows != 1) {
						gridrows = gridrows - 1;
						System.out.println("Aantal rijen: " + gridrows);
						wereld = veranderMatrixGrootte2(wereld);
						textures = veranderMatrixGrootte2(textures);
					}
				} else if ((1330f + 8f) / 1920f * screenWidth < me.getX() && me.getX() < (1330f + 28f) / 1920f * screenWidth) {
					// plusknop boven
					gridrows = gridrows + 1;
					System.out.println("Aantal rijen: " + gridrows);
					wereld = veranderMatrixGrootte2(wereld);
					textures = veranderMatrixGrootte2(textures);
				}
			}

			if ((1f - (90f - 40f) / 1080f) * screenHeight > me.getY() && me.getY() > (1f - (90f - 20f) / 1080f) * screenHeight) {
				if ((1330f - 28f) / 1920f * screenWidth < me.getX() && me.getX() < (1330f - 8f) / 1920f * screenWidth) {
					// minknop onder
					if (gridrows != 1) {
						gridrows = gridrows - 1;
						System.out.println("Aantal rijen: " + gridrows);
						wereld = veranderMatrixGrootte(wereld);
						textures = veranderMatrixGrootte(textures);
						for (int item = 0; item != items.size(); item++) {
							items.get(item)[1] -= 7d;
						}
					}
				} else if ((1330f + 8f) / 1920f * screenWidth < me.getX() && me.getX() < (1330f + 28f) / 1920f * screenWidth) {
					// plusknop onder
					gridrows = gridrows + 1;
					System.out.println("Aantal rijen: " + gridrows);
					wereld = veranderMatrixGrootte(wereld);
					textures = veranderMatrixGrootte(textures);
					for (int item = 0; item != items.size(); item++) {
						items.get(item)[1] += 7d;
					}
				}
			}

			if ((830f - 40f) / 1920f * screenWidth < me.getX() && me.getX() < (830f - 20f) / 1920f * screenWidth) {
				if ((1f - (540f + 8f) / 1080f) * screenHeight > me.getY() && me.getY() > (1f - (540f + 28f) / 1080f) * screenHeight) {
					// minknop links
					if (gridcolumns != 1) {
						gridcolumns = gridcolumns - 1;
						System.out.println("Aantal kolommen: " + gridcolumns);
						wereld = veranderMatrixGrootte2(wereld);
						textures = veranderMatrixGrootte2(textures);
						for (int item = 0; item != items.size(); item++) {
							items.get(item)[2] -= 7d;
						}
					}
				} else if ((1f - (540f - 28f) / 1080f) * screenHeight > me.getY() && me.getY() > (1f - (540f - 8f) / 1080f) * screenHeight) {
					// plusknop links
					gridcolumns = gridcolumns + 1;
					System.out.println("Aantal kolommen: " + gridcolumns);
					wereld = veranderMatrixGrootte2(wereld);
					textures = veranderMatrixGrootte2(textures);
					for (int item = 0; item != items.size(); item++) {
						items.get(item)[2] += 7d;
					}
				}
			}

			if ((1830f + 20f) / 1920f * screenWidth < me.getX() && me.getX() < (1830f + 40f) / 1920f * screenWidth) {
				if ((1f - (540f + 8f) / 1080f) * screenHeight > me.getY() && me.getY() > (1f - (540f + 28f) / 1080f) * screenHeight) {
					// minknop rechts
					if (gridcolumns != 1) {
						gridcolumns = gridcolumns - 1;
						System.out.println("Aantal kolommen: " + gridcolumns);
						wereld = veranderMatrixGrootte(wereld);
						textures = veranderMatrixGrootte(textures);
					}
				} else if ((1f - (540f - 28f) / 1080f) * screenHeight > me.getY() && me.getY() > (1f - (540f - 8f) / 1080f) * screenHeight) {
					// plusknop rechts
					gridcolumns = gridcolumns + 1;
					System.out.println("Aantal kolommen: " + gridcolumns);
					wereld = veranderMatrixGrootte(wereld);
					textures = veranderMatrixGrootte(textures);
				}
			}

			// portaldirection klik
			if (addportaldirection) {
				double portalID = 1;
				double connectionID = 1;
				if (Math.abs(me.getX() - xportal) >= Math.abs(screenHeight - me.getY() - yportal)) {
					// links
					if ((me.getX() - xportal) < 0) {
						items.add(new double[] { textureMode, (yportal - ymin) * 7f / distance, (xportal - xmin) * 7f / distance, 3, portalID, connectionID });
						addportaldirection = false;
						System.out.println("ik ben false: " + addportaldirection);
						// items.get(items.size()-1)[3]=2;
					}
					// rechts
					else if ((me.getX() - xportal) >= 0) {
						items.add(new double[] { textureMode, (yportal - ymin) * 7f / distance, (xportal - xmin) * 7f / distance, 1, portalID, connectionID });
						addportaldirection = false;
						System.out.println("ik ben false: " + addportaldirection);
						// items.get(items.size()-1)[3]=0;
					}
				} else if (Math.abs(me.getX() - xportal) < Math.abs(screenHeight - me.getY() - yportal)) {
					// onder
					if ((screenHeight - me.getY() - yportal) < 0) {
						items.add(new double[] { textureMode, (yportal - ymin) * 7f / distance, (xportal - xmin) * 7f / distance, 2, portalID, connectionID });
						addportaldirection = false;
						System.out.println("ik ben false: " + addportaldirection);
						// items.get(items.size()-1)[3]=1;
					}
					// boven
					else if ((screenHeight - me.getY() - yportal) >= 0) {
						items.add(new double[] { textureMode, (yportal - ymin) * 7f / distance, (xportal - xmin) * 7f / distance, 0, portalID, connectionID });
						addportaldirection = false;
						System.out.println("ik ben false: " + addportaldirection);
						// items.get(items.size()-1)[3]=3;
					}
				}
			} else {
				// gridklik
				if (xmin - distance / 5 < me.getX() && me.getX() < xmax + distance / 5 && screenHeight - ymax - distance / 5 < me.getY() && me.getY() < screenHeight - ymin + distance / 5) {
					float gridklikx = me.getX();
					float gridkliky = screenHeight - me.getY();
					gridklik = true;
					veranderMatrixVolgensKlikInGrid(gridklikx, gridkliky);
				}
			}

			// locatie knop
			if ((1 - 932f / 1080f) * screenHeight < me.getY() && me.getY() < (1 - 890f / 1080f) * screenHeight) {
				if (622f / 1920f * screenWidth < me.getX() && me.getX() < 740f / 1920f * screenWidth) {
					InputLocation il = new InputLocation();
					this.location = il.getLocation();
				}
			}
			
			if ((1 - 1052f / 1080f) * screenHeight < me.getY() && me.getY() < (1 - 1010f / 1080f) * screenHeight) {
				if (622f / 1920f * screenWidth < me.getX() && me.getX() < 740f / 1920f * screenWidth) {
					InputLocation il = new InputLocation();
					int[] info = il.getStartingInfo();
					levels.setStartingBullets(info[0]);
					levels.setStartingTraps(info[1]);
				}
			} 

			// level knoppen
			if (622f / 1920f * screenWidth < me.getX() && me.getX() < (622f + 51f) / 1920f * screenWidth) {
				// add level
				if ((1 - 860f / 1080f) * screenHeight < me.getY() && me.getY() < (1 - (860f - 51f) / 1080f) * screenHeight) {
					levels.addLevel();
					if (selectedLevel < 0) { // als er nog geen levels in deze
												// wereld waren, wordt de 0de
												// geselecteerd
						selectedLevel = 0;
					}
				}
			}

			// levellistklik
			selectedLevel = levels.mouseReleased(me.getX(), screenHeight - me.getY(), 622f / 1920f * screenWidth, 90f / 1080f * screenHeight, 740f / 1920f * screenWidth - 24f / 1920f * screenWidth,
					776f / 1080f * screenHeight, selectedLevel);

			// saveLevelAs
			try {
				levels.mouseReleased2(me.getX(), screenHeight - me.getY(), 622f / 1920f * screenWidth, 90f / 1080f * screenHeight, 740f / 1920f * screenWidth, 776f / 1080f * screenHeight,
						screenWidth, screenHeight);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// remove level from list
			remove = levels.mouseReleased3(me.getX(), screenHeight - me.getY(), 622f / 1920f * screenWidth, 90f / 1080f * screenHeight, 740f / 1920f * screenWidth, 776f / 1080f * screenHeight,
					screenWidth, screenHeight);

			// saveWorldAs (tijdelijk)
			if (10f / 1080f * screenHeight < me.getY() && me.getY() < 80f / 1080f * screenHeight)
				if (15f / 1920f * screenWidth < me.getX() && me.getX() < 85f / 1920f * screenWidth) {
					try {
						levels.saveAs();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println("World saved");
				}
		}

		// rechtermuisknop
		else if (me.getButton() == 3) {
			if (xmin - distance / 5 < me.getX() && me.getX() < xmax + distance / 5 && screenHeight - ymax - distance / 5 < me.getY() && me.getY() < screenHeight - ymin + distance / 5) {
				// gridklikxrechts = me.getX();
				// gridklikyrechts = screenHeight - me.getY();
				gridklik = true;
				veranderMatrixVolgensKlikInGridrechts(me.getX(), screenHeight - me.getY());
			} else {
				if (heightsOn) {
					heightsOn = false;
				} else {
					heightsOn = true;
				}
			}
		}

	}

	public void mousePressed(MouseEvent me) throws FileNotFoundException {
		// openlevel
		if (me.getClickCount() == 2) {
			open = levels.mousePressed(me.getX(), screenHeight - me.getY(), 622f / 1920f * screenWidth, 90f / 1080f * screenHeight, 740f / 1920f * screenWidth, 776f / 1080f * screenHeight,
					screenWidth, screenHeight);
		}

		// modelviewer
		modelviewer.mousePressed(me);
		if (worldview) {
			worldviewer.mousePressed(me);
		}
	}

	public void mouseDragged(MouseEvent me) {
		modelviewer.mouseDragged(me);
		if (worldview) {
			worldviewer.mouseDragged(me);
		}

	}

	public void veranderMatrixVolgensKlikInGrid(float gridklikx, float gridkliky) {
		while (gridklik) {
			float xmin = 830f / 1920f * screenWidth;
			float ymin = 90f / 1080f * screenHeight;
			float xmax = 1830f / 1920f * screenWidth;
			float ymax = 990f / 1080f * screenHeight;

			float xmidden = (xmax + xmin) / 2;
			float ymidden = (ymax + ymin) / 2;

			float columndist = (xmax - xmin) / (gridcolumns);
			float rowdist = (ymax - ymin) / (gridrows);
			float distance = 1; // willekeurig

			if (columndist < rowdist) {
				distance = columndist;
				ymin = ymidden - distance * gridrows / 2;
				ymax = ymidden + distance * gridrows / 2;
			} else if (columndist > rowdist) {
				distance = rowdist;
				xmin = xmidden - (distance * gridcolumns) / 2;
				xmax = xmidden + (distance * gridcolumns) / 2;
			}
			System.out.println("gridklik is " + gridklik);

			// LINKERMUISKNOP: plaats iets in de matrix

			// verander matrix tijdens mode DAK
			if (drawMode == DAK) {
				for (int kolom = 1; kolom <= gridcolumns; kolom++) {
					for (int rij = 1; rij <= gridrows; rij++) {
						if (xmin + (kolom - 1) * distance < gridklikx && gridklikx < xmin + kolom * distance && ymax - rij * distance < gridkliky && gridkliky < ymax - (rij - 1) * distance) {
							wereld[2 * rij - 1][2 * kolom - 1] = hoogteMode;
							textures[2 * rij - 1][2 * kolom - 1] = textureMode;

							for (int i = 0; i != wereld.length; i++) { // print
																		// matrix
								for (int j = 0; j != wereld[0].length; j++) {
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}

						}
					}
				}
			}

			// verander matrix tijdens mode KOLOM
			if (drawMode == KOLOM) {
				for (int kolom = 1; kolom <= gridcolumns + 1; kolom++) {
					for (int rij = 1; rij <= gridrows + 1; rij++) {
						if (xmin + (kolom - 1) * distance - distance / 5 < gridklikx && gridklikx < xmin + (kolom - 1) * distance + distance / 5
								&& ymax - (rij - 1) * distance - distance / 5 < gridkliky && gridkliky < ymax - (rij - 1) * distance + distance / 5) {
							wereld[2 * rij - 2][2 * kolom - 2] = hoogteMode;
							textures[2 * rij - 2][2 * kolom - 2] = textureMode;

							for (int i = 0; i != wereld.length; i++) { // print
																		// matrix
								for (int j = 0; j != wereld[0].length; j++) {
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}

						}
					}
				}
			}

			// verander matrix tijdens mode MUUR (verticaal)
			if (drawMode == MUUR) {
				for (int kolom = 1; kolom <= gridcolumns + 1; kolom++) {
					for (int rij = 1; rij <= gridrows + 1; rij++) {
						if (xmin + (kolom - 1) * distance - distance / 5 < gridklikx && gridklikx < xmin + (kolom - 1) * distance + distance / 5 && ymax - rij * distance + distance / 5 < gridkliky
								&& gridkliky < ymax - (rij - 1) * distance - distance / 5) {
							wereld[2 * rij - 1][2 * kolom - 2] = hoogteMode;
							textures[2 * rij - 1][2 * kolom - 2] = textureMode;

							checkVoorAangrenzendeMurenVerticaal(rij, kolom);

							for (int i = 0; i != wereld.length; i++) { // print
																		// matrix
								for (int j = 0; j != wereld[0].length; j++) {
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}

						}
					}
				}
			}

			// verander matrix tijdens mode MUUR (horizontaal)
			if (drawMode == MUUR) {
				for (int kolom = 1; kolom <= gridcolumns + 1; kolom++) {
					for (int rij = 1; rij <= gridrows + 1; rij++) {
						if (xmin + (kolom - 1) * distance + distance / 5 < gridklikx && gridklikx < xmin + kolom * distance - distance / 5 && ymax - (rij - 1) * distance - distance / 5 < gridkliky
								&& gridkliky < ymax - (rij - 1) * distance + distance / 5) {
							wereld[2 * rij - 2][2 * kolom - 1] = hoogteMode;
							textures[2 * rij - 2][2 * kolom - 1] = textureMode;

							checkVoorAangrenzendeMurenHorizontaal(rij, kolom);

							for (int i = 0; i != wereld.length; i++) { // print
																		// matrix
								for (int j = 0; j != wereld[0].length; j++) {
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}

						}
					}
				}
			}

			// plaats item
			if (drawMode == ITEM) {
				if (textureMode == 129 && !addportaldirection) {
					// double direction = 0;
					// double portalID = 1;
					// double connectionID = 1;
					// drawportal
					xportal = gridklikx;
					yportal = gridkliky;
					addportaldirection = true;
					System.out.println("ik ben true: " + addportaldirection);
				}
				if (textureMode == 229) {
					items.add(new double[] { textureMode, (gridkliky - ymin) * 7f / distance, (gridklikx - xmin) * 7f / distance });
				}
				if (textureMode == 130) {
					items.add(new double[] { textureMode, (gridkliky - ymin) * 7f / distance, (gridklikx - xmin) * 7f / distance });
				}
				if (textureMode == 230) {
					items.add(new double[] { textureMode, (gridkliky - ymin) * 7f / distance, (gridklikx - xmin) * 7f / distance, hoogteMode });
				}
				if (textureMode == 131) {
					items.add(new double[] { textureMode, (gridkliky - ymin) * 7f / distance, (gridklikx - xmin) * 7f / distance });
					// items.add(new double[]{textureMode,
					// (gridkliky-ymin)*7f/distance,
					// (gridklikx-xmin)*7f/distance, hoogteMode});
				}
				if (textureMode == 231) {
					items.add(new double[] { textureMode, (gridkliky - ymin) * 7f / distance, (gridklikx - xmin) * 7f / distance });
					// items.add(new double[]{textureMode,
					// (gridkliky-ymin)*7f/distance,
					// (gridklikx-xmin)*7f/distance, hoogteMode});
				}
			}

			gridklik = false;
		}
	}

	public void veranderMatrixVolgensKlikInGridrechts(float gridklikxrechts, float gridklikyrechts) {
		while (gridklik) {
			float xmin = 830f / 1920f * screenWidth;
			float ymin = 90f / 1080f * screenHeight;
			float xmax = 1830f / 1920f * screenWidth;
			float ymax = 990f / 1080f * screenHeight;

			float xmidden = (xmax + xmin) / 2;
			float ymidden = (ymax + ymin) / 2;

			float columndist = (xmax - xmin) / (gridcolumns);
			float rowdist = (ymax - ymin) / (gridrows);
			float distance = 1; // willekeurig

			if (columndist < rowdist) {
				distance = columndist;
				ymin = ymidden - distance * gridrows / 2;
				ymax = ymidden + distance * gridrows / 2;
			} else if (columndist > rowdist) {
				distance = rowdist;
				xmin = xmidden - (distance * gridcolumns) / 2;
				xmax = xmidden + (distance * gridcolumns) / 2;
			}
			System.out.println("gridklik is " + gridklik);

			// RECHTERMUISKNOP: verwijder iets uit de matrix

			if (drawMode != ITEM) {
				// verander matrix tijdens mode DAK

				for (int kolom = 1; kolom <= gridcolumns; kolom++) {
					for (int rij = 1; rij <= gridrows; rij++) {
						if (xmin + (kolom - 1) * distance + distance / 10 < gridklikxrechts && gridklikxrechts < xmin + kolom * distance - distance / 10
								&& ymax - rij * distance + distance / 10 < gridklikyrechts && gridklikyrechts < ymax - (rij - 1) * distance - distance / 10) {
							wereld[2 * rij - 1][2 * kolom - 1] = 0;
							textures[2 * rij - 1][2 * kolom - 1] = 0;

							for (int i = 0; i != wereld.length; i++) { // print
																		// matrix
								for (int j = 0; j != wereld[0].length; j++) {
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}

						}
					}
				}

				// verander matrix tijdens mode KOLOM

				for (int kolom = 1; kolom <= gridcolumns + 1; kolom++) {
					for (int rij = 1; rij <= gridrows + 1; rij++) {
						if (xmin + (kolom - 1) * distance - distance / 10 < gridklikxrechts && gridklikxrechts < xmin + (kolom - 1) * distance + distance / 10
								&& ymax - (rij - 1) * distance - distance / 10 < gridklikyrechts && gridklikyrechts < ymax - (rij - 1) * distance + distance / 10) {
							wereld[2 * rij - 2][2 * kolom - 2] = 0;
							textures[2 * rij - 2][2 * kolom - 2] = 0;

							for (int i = 0; i != wereld.length; i++) { // print
																		// matrix
								for (int j = 0; j != wereld[0].length; j++) {
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}

						}
					}
				}

				// verander matrix tijdens mode MUUR (verticaal)

				for (int kolom = 1; kolom <= gridcolumns + 1; kolom++) {
					for (int rij = 1; rij <= gridrows + 1; rij++) {
						if (xmin + (kolom - 1) * distance - distance / 10 < gridklikxrechts && gridklikxrechts < xmin + (kolom - 1) * distance + distance / 10
								&& ymax - rij * distance + distance / 10 < gridklikyrechts && gridklikyrechts < ymax - (rij - 1) * distance - distance / 10) {
							wereld[2 * rij - 1][2 * kolom - 2] = 0;
							textures[2 * rij - 1][2 * kolom - 2] = 0;

							for (int i = 0; i != wereld.length; i++) { // print
																		// matrix
								for (int j = 0; j != wereld[0].length; j++) {
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}

						}
					}
				}

				// verander matrix tijdens mode MUUR (horizontaal)

				for (int kolom = 1; kolom <= gridcolumns + 1; kolom++) {
					for (int rij = 1; rij <= gridrows + 1; rij++) {
						if (xmin + (kolom - 1) * distance + distance / 10 < gridklikxrechts && gridklikxrechts < xmin + kolom * distance - distance / 10
								&& ymax - (rij - 1) * distance - distance / 10 < gridklikyrechts && gridklikyrechts < ymax - (rij - 1) * distance + distance / 10) {
							wereld[2 * rij - 2][2 * kolom - 1] = 0;
							textures[2 * rij - 2][2 * kolom - 1] = 0;

							for (int i = 0; i != wereld.length; i++) { // print
																		// matrix
								for (int j = 0; j != wereld[0].length; j++) {
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}

						}
					}
				}
			} else {
				// verwijder items
				System.out.println("hoi");
				for (int item = 0; item != items.size(); item++) {
					float x = (float) items.get(item)[1];
					float z = (float) items.get(item)[2];
					if (xmin + z / 7f * distance - distance / 4 < gridklikxrechts && gridklikxrechts < xmin + z / 7f * distance + distance / 4) {
						if (ymin + x / 7f * distance - distance / 4 < gridklikyrechts && gridklikyrechts < ymin + x / 7f * distance + distance / 4) {
							items.remove(item);
							item--;
						}
					}
				}
			}
			gridklik = false;
		}
	}

	public void checkVoorAangrenzendeMurenVerticaal(int rij, int kolom) {
		if (checkIfExists((2 * rij - 1) + 1, (2 * kolom - 2) - 1) || checkIfExists((2 * rij - 1) + 2, 2 * kolom - 2) || checkIfExists((2 * rij - 1) + 1, (2 * kolom - 2) + 1)) {
			if (wereld[(2 * rij - 1) + 1][2 * kolom - 2] == 0) {
				wereld[(2 * rij - 1) + 1][2 * kolom - 2] = hoogteMode;
				textures[(2 * rij - 1) + 1][2 * kolom - 2] = textureMode;
			}
		}
		if (checkIfExists((2 * rij - 1) - 1, (2 * kolom - 2) - 1) || checkIfExists((2 * rij - 1) - 2, 2 * kolom - 2) || checkIfExists((2 * rij - 1) - 1, (2 * kolom - 2) + 1)) {
			if (wereld[(2 * rij - 1) - 1][2 * kolom - 2] == 0) {
				wereld[(2 * rij - 1) - 1][2 * kolom - 2] = hoogteMode;
				textures[(2 * rij - 1) - 1][2 * kolom - 2] = textureMode;
			}
		}
	}

	public void checkVoorAangrenzendeMurenHorizontaal(int rij, int kolom) {
		if (checkIfExists((2 * rij - 2) - 1, (2 * kolom - 1) - 1) || checkIfExists(2 * rij - 2, (2 * kolom - 1) - 2) || checkIfExists((2 * rij - 2) + 1, (2 * kolom - 1) - 1)) {
			if (wereld[2 * rij - 2][(2 * kolom - 1) - 1] == 0) {
				wereld[2 * rij - 2][(2 * kolom - 1) - 1] = hoogteMode;
				textures[2 * rij - 2][(2 * kolom - 1) - 1] = textureMode;
			}
		}
		if (checkIfExists((2 * rij - 2) - 1, (2 * kolom - 1) + 1) || checkIfExists(2 * rij - 2, (2 * kolom - 1) + 2) || checkIfExists((2 * rij - 2) + 1, (2 * kolom - 1) + 1)) {
			if (wereld[2 * rij - 2][(2 * kolom - 1) + 1] == 0) {
				wereld[2 * rij - 2][(2 * kolom - 1) + 1] = hoogteMode;
				textures[2 * rij - 2][(2 * kolom - 1) + 1] = textureMode;
			}
		}
	}

	public boolean checkIfExists(int rij, int kolom) {
		if (0 <= rij && rij <= wereld.length - 1 && 0 <= kolom && kolom <= wereld[0].length - 1 && wereld[rij][kolom] != 0) {
			return true;
		} else
			return false;
	}

	public int[][] veranderMatrixGrootte(int[][] matrix) {
		int[][] res = new int[2 * gridrows + 1][2 * gridcolumns + 1];
		if (res.length < matrix.length || res[0].length < matrix[0].length) {
			for (int i = 0; i != res.length; i++) { // kopieert de oude matrix
													// naar de nieuwe, met
													// verlies van 1 van de
													// zijkanten van de oude
													// matrix
				for (int j = 0; j != res[0].length; j++) {
					res[i][j] = matrix[i][j];
				}
			}
		}
		if (res.length > matrix.length || res[0].length > matrix[0].length) {
			for (int i = 0; i != res.length; i++) { // zet nieuwe matrix vol
													// nullen
				for (int j = 0; j != res[0].length; j++) {
					res[i][j] = 0;
				}
			}
			for (int i = 0; i != matrix.length; i++) { // kopieert de oude
														// matrix naar de nieuwe
				for (int j = 0; j != matrix[0].length; j++) {
					res[i][j] = matrix[i][j];
				}
			}
		}
		for (int i = 0; i != res.length; i++) { // print matrix
			for (int j = 0; j != res[0].length; j++) {
				System.out.print(res[i][j] + " ");
			}
			System.out.println();
		}
		return res;
	}

	public int[][] veranderMatrixGrootte2(int[][] matrix) {
		int[][] res = new int[2 * gridrows + 1][2 * gridcolumns + 1];
		if (res.length < matrix.length) {
			for (int i = 0; i != res.length; i++) { // kopieert de oude matrix
													// naar de nieuwe, met
													// verlies van 1 van de
													// zijkanten van de oude
													// matrix
				for (int j = 0; j != res[0].length; j++) {
					res[i][j] = matrix[i + 2][j];
				}
			}
		}
		if (res[0].length < matrix[0].length) {
			for (int i = 0; i != res.length; i++) { // kopieert de oude matrix
													// naar de nieuwe, met
													// verlies van 1 van de
													// zijkanten van de oude
													// matrix
				for (int j = 0; j != res[0].length; j++) {
					res[i][j] = matrix[i][j + 2];
				}
			}
		}
		if (res.length > matrix.length) {
			for (int i = 0; i != res.length; i++) { // zet nieuwe matrix vol
													// nullen
				for (int j = 0; j != res[0].length; j++) {
					res[i][j] = 0;
				}
			}
			for (int i = 0; i != matrix.length; i++) { // kopieert de oude
														// matrix naar de nieuwe
				for (int j = 0; j != matrix[0].length; j++) {
					res[i + 2][j] = matrix[i][j];
				}
			}
		}
		if (res[0].length > matrix[0].length) {
			for (int i = 0; i != res.length; i++) { // zet nieuwe matrix vol
													// nullen
				for (int j = 0; j != res[0].length; j++) {
					res[i][j] = 0;
				}
			}
			for (int i = 0; i != matrix.length; i++) { // kopieert de oude
														// matrix naar de nieuwe
				for (int j = 0; j != matrix[0].length; j++) {
					res[i][j + 2] = matrix[i][j];
				}
			}
		}
		for (int i = 0; i != res.length; i++) { // print matrix
			for (int j = 0; j != res[0].length; j++) {
				System.out.print(res[i][j] + " ");
			}
			System.out.println();
		}
		return res;
	}

	private void drawGridInhoud(GLAutoDrawable drawable, GL gl) {
		float xmin = 830f / 1920f * screenWidth;
		float ymin = 90f / 1080f * screenHeight;
		float xmax = 1830f / 1920f * screenWidth;
		float ymax = 990f / 1080f * screenHeight;

		float xmidden = (xmax + xmin) / 2;
		float ymidden = (ymax + ymin) / 2;

		float columndist = (xmax - xmin) / (gridcolumns);
		float rowdist = (ymax - ymin) / (gridrows);
		float distance = 1; // willekeurig

		if (columndist < rowdist) {
			distance = columndist;
			ymin = ymidden - distance * gridrows / 2;
			ymax = ymidden + distance * gridrows / 2;
		} else if (columndist > rowdist) {
			distance = rowdist;
			xmin = xmidden - (distance * gridcolumns) / 2;
			xmax = xmidden + (distance * gridcolumns) / 2;
		}

		// teken dak
		for (int rij = 1; rij < wereld.length - 1; rij = rij + 2) {
			for (int kolom = 1; kolom < wereld[0].length - 1; kolom = kolom + 2) {
				if (textures[rij][kolom] == 129) {
					// onderstaande moet veranderen in een daktexture
					plaatsTexture2(gl, xmin + (kolom / 2) * distance, ymax - rij / 2 * distance, xmin + (kolom / 2 + 1) * distance, ymax - (rij / 2 + 1) * distance, 7);
				}
				if (wereld[rij][kolom] != 0 && heightsOn) {
					Teken.textDrawMetKleur(drawable, gl, String.valueOf(wereld[rij][kolom]), xmin + (kolom / 2) * distance + distance / 2, ymax - (rij / 2 + 1) * distance + distance / 2, 30, 1f, 1f,
							1f);
				}
			}
		}

		// teken kolom
		for (int rij = 0; rij <= wereld.length; rij = rij + 2) {
			for (int kolom = 0; kolom <= wereld[0].length; kolom = kolom + 2) {
				if (textures[rij][kolom] > 200) {
					// onderstaande moet veranderen in een kolomtexture
					plaatsTexture2(gl, xmin + ((kolom + 1) / 2) * distance - distance / 10, ymax - (rij + 1) / 2 * distance + distance / 10, xmin + ((kolom + 1) / 2) * distance + distance / 10, ymax
							- (rij + 1) / 2 * distance - distance / 10, textures[rij][kolom] - 200);
				} else if (textures[rij][kolom] > 100) {
					plaatsTexture2(gl, xmin + ((kolom + 1) / 2) * distance - distance / 10, ymax - (rij + 1) / 2 * distance + distance / 10, xmin + ((kolom + 1) / 2) * distance + distance / 10, ymax
							- (rij + 1) / 2 * distance - distance / 10, textures[rij][kolom] - 100);
				}
			}
		}

		// teken verticale muur
		for (int rij = 1; rij <= wereld.length - 1; rij = rij + 2) {
			for (int kolom = 0; kolom <= wereld[0].length; kolom = kolom + 2) {
				if (textures[rij][kolom] > 200) {
					// onderstaande moet veranderen in een muurtexture
					plaatsTexture2(gl, xmin + ((kolom + 1) / 2) * distance - distance / 10, ymax - (rij + 1) / 2 * distance + distance * 9 / 10, xmin + ((kolom + 1) / 2) * distance + distance / 10,
							ymax - (rij + 1) / 2 * distance + distance * 6 / 10, textures[rij][kolom] - 200);
					plaatsTexture2(gl, xmin + ((kolom + 1) / 2) * distance - distance / 10, ymax - (rij + 1) / 2 * distance + distance * 4 / 10, xmin + ((kolom + 1) / 2) * distance + distance / 10,
							ymax - (rij + 1) / 2 * distance + distance / 10, textures[rij][kolom] - 200);
				} else if (textures[rij][kolom] > 100) {
					// onderstaande moet veranderen in een muurtexture
					plaatsTexture2(gl, xmin + ((kolom + 1) / 2) * distance - distance / 10, ymax - (rij + 1) / 2 * distance + distance * 9 / 10, xmin + ((kolom + 1) / 2) * distance + distance / 10,
							ymax - (rij + 1) / 2 * distance + distance / 10, textures[rij][kolom] - 100);
				}
				// if (textures[rij][kolom]==3){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 156f/255f, 133f/255f,
				// 95f/255f);
				// }
				// if (textures[rij][kolom]==4){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance*6/10, 156f/255f, 133f/255f,
				// 95f/255f);
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*4/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 156f/255f, 133f/255f,
				// 95f/255f);
				// }
				// if (textures[rij][kolom]==5){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==6){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==7){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==8){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==9){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==10){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==11){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==12){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==13){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==14){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==15){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==16){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==17){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==18){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==19){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==20){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==21){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==22){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==23){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==24){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==25){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==26){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==27){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
				// if (textures[rij][kolom]==28){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance+distance*9/10
				// ,xmin+((kolom+1)/2)*distance+distance/10,
				// ymax-(rij+1)/2*distance+distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
			}
		}

		// teken horizontale muur
		for (int rij = 0; rij <= wereld.length; rij = rij + 2) {
			for (int kolom = 1; kolom <= wereld[0].length - 1; kolom = kolom + 2) {
				if (textures[rij][kolom] > 200) {
					plaatsTexture2(gl, xmin + ((kolom + 1) / 2) * distance - distance * 9 / 10, ymax - (rij + 1) / 2 * distance + distance / 10, xmin + ((kolom + 1) / 2) * distance - distance * 6
							/ 10, ymax - (rij + 1) / 2 * distance - distance / 10, textures[rij][kolom] - 200);
					plaatsTexture2(gl, xmin + ((kolom + 1) / 2) * distance - distance * 4 / 10, ymax - (rij + 1) / 2 * distance + distance / 10, xmin + ((kolom + 1) / 2) * distance - distance / 10,
							ymax - (rij + 1) / 2 * distance - distance / 10, textures[rij][kolom] - 200);
				} else if (textures[rij][kolom] > 100) {
					// onderstaande moet veranderen in een muurtexture
					plaatsTexture2(gl, xmin + ((kolom + 1) / 2) * distance - distance * 9 / 10, ymax - (rij + 1) / 2 * distance + distance / 10, xmin + ((kolom + 1) / 2) * distance - distance / 10,
							ymax - (rij + 1) / 2 * distance - distance / 10, textures[rij][kolom] - 100);
				}
				// if (textures[rij][kolom]==2){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance*9/10,
				// ymax-(rij+1)/2*distance+distance/10
				// ,xmin+((kolom+1)/2)*distance-distance*6/10,
				// ymax-(rij+1)/2*distance-distance/10, 135f/255f, 22f/255f,
				// 16f/255f);
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance*4/10,
				// ymax-(rij+1)/2*distance+distance/10
				// ,xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance-distance/10, 135f/255f, 22f/255f,
				// 16f/255f);
				// }
				// if (textures[rij][kolom]==3){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance*9/10,
				// ymax-(rij+1)/2*distance+distance/10
				// ,xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance-distance/10, 156f/255f, 133f/255f,
				// 95f/255f);
				// }
				// if (textures[rij][kolom]==4){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance*9/10,
				// ymax-(rij+1)/2*distance+distance/10
				// ,xmin+((kolom+1)/2)*distance-distance*6/10,
				// ymax-(rij+1)/2*distance-distance/10, 156f/255f, 133f/255f,
				// 95f/255f);
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance*4/10,
				// ymax-(rij+1)/2*distance+distance/10
				// ,xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance-distance/10, 156f/255f, 133f/255f,
				// 95f/255f);
				// }
				// if (textures[rij][kolom]==5){
				// //onderstaande moet veranderen in een muurtexture
				// tekenButtonMetKleur(gl,
				// xmin+((kolom+1)/2)*distance-distance*9/10,
				// ymax-(rij+1)/2*distance+distance/10
				// ,xmin+((kolom+1)/2)*distance-distance/10,
				// ymax-(rij+1)/2*distance-distance/10, 20f/255f, 20f/255f,
				// 20f/255f);
				// }
			}
		}

		// teken items
		for (int item = 0; item < items.size(); item++) {
			// System.out.println(item);
			if (items.get(item)[0] == 129) {
				float x = (float) items.get(item)[1];
				float z = (float) items.get(item)[2];
				plaatsTexture2(gl, xmin + z / 7f * distance - distance / 4, ymin + x / 7f * distance - distance / 4, xmin + z / 7f * distance + distance / 4, ymin + x / 7f * distance + distance / 4,
						26);
				if (items.get(item)[3] == 0)
					Teken.pijlboven(gl, xmin + z / 7f * distance, ymin + x / 7f * distance, distance);
				else if (items.get(item)[3] == 1)
					Teken.pijlrechts(gl, xmin + z / 7f * distance, ymin + x / 7f * distance, distance);
				else if (items.get(item)[3] == 2)
					Teken.pijlonder(gl, xmin + z / 7f * distance, ymin + x / 7f * distance, distance);
				else if (items.get(item)[3] == 3)
					Teken.pijllinks(gl, xmin + z / 7f * distance, ymin + x / 7f * distance, distance);
			}
			if (items.get(item)[0] == 229) {
				float x = (float) items.get(item)[1];
				float z = (float) items.get(item)[2];
				plaatsTexture2(gl, xmin + z / 7f * distance - distance / 4, ymin + x / 7f * distance - distance / 4, xmin + z / 7f * distance + distance / 4, ymin + x / 7f * distance + distance / 4,
						17);
			}
			if (items.get(item)[0] == 130) {
				float x = (float) items.get(item)[1];
				float z = (float) items.get(item)[2];
				plaatsTexture2(gl, xmin + z / 7f * distance - distance / 4, ymin + x / 7f * distance - distance / 4, xmin + z / 7f * distance + distance / 4, ymin + x / 7f * distance + distance / 4,
						18);
			}
			if (items.get(item)[0] == 230) {
				float x = (float) items.get(item)[1];
				float z = (float) items.get(item)[2];
				plaatsTexture2(gl, xmin + z / 7f * distance - distance / 4, ymin + x / 7f * distance - distance / 4, xmin + z / 7f * distance + distance / 4, ymin + x / 7f * distance + distance / 4,
						27);
			}
			if (items.get(item)[0] == 131) {
				float x = (float) items.get(item)[1];
				float z = (float) items.get(item)[2];
				plaatsTexture2(gl, xmin + z / 7f * distance - distance / 4, ymin + x / 7f * distance - distance / 4, xmin + z / 7f * distance + distance / 4, ymin + x / 7f * distance + distance / 4,
						14);
			}
			if (items.get(item)[0] == 231) {
				float x = (float) items.get(item)[1];
				float z = (float) items.get(item)[2];
				plaatsTexture2(gl, xmin + z / 7f * distance - distance / 4, ymin + x / 7f * distance - distance / 4, xmin + z / 7f * distance + distance / 4, ymin + x / 7f * distance + distance / 4,
						20);
			}
		}

		// teken portal pijl
		if (addportaldirection) {
			plaatsTexture2(gl, xportal - distance / 4, yportal - distance / 4, xportal + distance / 4, yportal + distance / 4, 26);
			float dX = (float) ((MouseInfo.getPointerInfo().getLocation().getX()) - xportal);
			float dY = (float) (screenHeight + 30f - MouseInfo.getPointerInfo().getLocation().getY() - yportal);
			if (Math.abs(dX) >= Math.abs(dY)) {
				if (dX > 0)
					Teken.pijlrechts(gl, xportal, yportal, distance);
				else
					Teken.pijllinks(gl, xportal, yportal, distance);
			} else if (Math.abs(dY) > Math.abs(dX)) {
				if (dY > 0)
					Teken.pijlboven(gl, xportal, yportal, distance);
				else
					Teken.pijlonder(gl, xportal, yportal, distance);
			}
		}

	}

	private void saveAs() throws FileNotFoundException {
		// PrintWriter bestand = new
		// PrintWriter("C:\\Users\\Martijn\\Dropbox\\EWI3620TU Minorproject SOT Groep 01\\Level1_1_l.txt");
		KiesFileUitBrowser kfub = new KiesFileUitBrowser();
		String filename = kfub.saveFile(new Frame(), "Opslaan als...", ".\\", "*");
		// als de bestandsnaam al eindigt op .txt , knip dat er dan af
		if (filename.substring(filename.length() - 4, filename.length()).equals(".txt")) {
			filename = filename.substring(0, filename.length() - 4);
		}
		String currentdir = System.getProperty("user.dir");
		PrintWriter bestand = new PrintWriter(currentdir + "\\levels\\" + filename + ".txt");
		bestand.println("0,0,0,");
		for (int i = 0; i != wereld.length; i++) {
			for (int j = 0; j != wereld[0].length; j++) {
				bestand.print(wereld[i][j] + ",");
			}
			bestand.println();
		}
		bestand.close();

		PrintWriter bestand2 = new PrintWriter(currentdir + "\\levels\\" + filename + "_t.txt");
		for (int i = 0; i != textures.length; i++) {
			for (int j = 0; j != textures[0].length; j++) {
				bestand2.print(textures[i][j] + ",");
			}
			bestand2.println();
		}
		bestand2.close();
	}

	public static int[][] defaultMatrix() {
		int[][] defaultmatrix = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
		return defaultmatrix;
	}

	public static double[] defaultLocation() {
		double[] defaultlocation = { 0, 0, 0 };
		return defaultlocation;
	}

	public void loadTextures(GL gl) {
		gl.glEnable(GL.GL_TEXTURE_1D);

		try {
			String currentdir = System.getProperty("user.dir");
			String filename = "\\textures\\cataloguskolom1.jpg";

			filename = currentdir + filename;
			File file2 = new File(filename);
			TextureData data2 = TextureIO.newTextureData(file2, false, "jpg");
			wallTexture1 = TextureIO.newTexture(data2);
			System.out.println("gelukt");
		} catch (IOException exc) {
			System.out.println("niet gevonden - texture");
			exc.printStackTrace();
			System.exit(1);
		}
		gl.glDisable(GL.GL_TEXTURE_1D);
	}

	public void plaatsTexture(GL gl, float xmin, float ymin, float xmax, float ymax, int i) {

		// Setting the floor color and material.
		// float[] rgba = {1f, 1f, 1f};
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
		// gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);

		// Apply texture.
		if (wallTexture1 != null) {
			wallTexture1.enable();
			gl.glBindTexture(GL.GL_TEXTURE_2D, i);
		}

		// gl.glNormal3d(0, 1, 0);
		// float wallColour[] = { 0.0f, 1.0f, 1.0f, 1.0f };
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);

		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex2f(xmin / 1920 * screenWidth, ymin / 1080 * screenHeight);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex2f(xmax / 1920 * screenWidth, ymin / 1080 * screenHeight);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex2f(xmax / 1920 * screenWidth, ymax / 1080 * screenHeight);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex2f(xmin / 1920 * screenWidth, ymax / 1080 * screenHeight);

		gl.glEnd();

	}

	public void plaatsTexture2(GL gl, float xmin, float ymin, float xmax, float ymax, int i) {

		// Setting the floor color and material.
		// float[] rgba = {1f, 1f, 1f};
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
		// gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);

		// Apply texture.
		if (wallTexture1 != null) {
			wallTexture1.enable();
			gl.glBindTexture(GL.GL_TEXTURE_2D, i);
		}

		// gl.glNormal3d(0, 1, 0);
		// float wallColour[] = { 0.0f, 1.0f, 1.0f, 1.0f };
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);

		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex2f(xmin, ymin);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex2f(xmax, ymin);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex2f(xmax, ymax);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex2f(xmin, ymax);

		gl.glEnd();

	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			this.worldview = !worldview;
		}

	}

}
