package levelEditor;

import java.awt.Color;
import java.awt.Frame;
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

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import engine.ChangeGL;

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
	private static final byte GEBOUW = 4;
	private byte drawMode = NIETS;
	
	private byte textureMode = 0;
	
	private int gridrows; //afmetingen grid
	private int gridcolumns;
	
	private float gridklikx = 10000; //10000 is een default waarde buiten het scherm, omdat je die waarde nooit kan krijgen door ergens te klikken
	private float gridkliky = 10000;
	private float gridklikxrechts = 10000;
	private float gridklikyrechts = 10000;
	private boolean gridklik = false;
	
	private boolean catalogus = false;
	private Texture backTexture;
	
	private int[][] wereld;

	/*private int [][] wereld =  new int[gridrows][gridcolumns];
	
	private void calculateGrid(){
		for (int j=0; j<gridrows; j++){
			for (int i=0; i < gridcolumns; i++){
				wereld[i][j]=0;
			}}
	}*/
	
	
	
	private ArrayList<Point2D.Float> points;
	Array columncheckamount;
	

	
	
	/**
	 * When instantiating, a GLCanvas is added for us to play with. An animator
	 * is created to continuously render the canvas.
	 */
	public LevelEditor(int screenWidth, int screenHeight, int[][] wereld) {
		//calculateGrid();
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.wereld = wereld;
		gridrows= (wereld.length-1)/2;
		gridcolumns = (wereld[0].length-1)/2;
	}
	
	
	
	public void setScreen(int screenWidth, int screenHeight){
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	

	public void loadTextures(GL gl, String filename){
		gl.glEnable(GL.GL_TEXTURE_1D);
		Texture backTexture = null;
		try {
			String currentdir = System.getProperty("user.dir");
			//String filename = "EditorBackground.png";
			
			filename = currentdir+filename;
			File file = new File(filename);
			System.out.println(filename);
            //InputStream stream = getClass().getResourceAsStream("texture.jpg");
            TextureData data = TextureIO.newTextureData(file, false, "jpg");
            backTexture = TextureIO.newTexture(data);
        }
        catch (IOException exc) {
        	System.out.println("niet gevonden");
            exc.printStackTrace();
            System.exit(1);
        }
	}


	/**
	 * A function defined in GLEventListener. This function is called many times per second and should 
	 * contain the rendering code.
	 */
	public void display(GL gl){
		// Set the clear color and clear the screen.
		ChangeGL.GLto2D(gl);
		
				gl.glClearColor(0.34f, 0.11f, 0.13f, 1);
				gl.glClear(GL.GL_COLOR_BUFFER_BIT);
				
				tekenLevelEditorAchtergrond(gl);
				drawGrid(gl, 830f/1920f*screenWidth, 90f/1080f*screenHeight, 1830f/1920f*screenWidth , 990f/1080f*screenHeight, gridcolumns, gridrows);
				drawGridInhoud(gl);
				
				veranderMatrixVolgensKlikInGrid(gl);
				
				
				drawCatalogus(gl);
				
				drawFigure(gl);
				//gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);

	}

	/**
	 * A method that draws the top left buttons on the screen.
	 * 
	 * @param gl
	 */
	private void tekenButton(GL gl, float xmin, float ymin, float xmax, float ymax){
		gl.glColor3f(0.16f, 0.16f, 0.16f);
		rechthoek(gl, xmin, ymin, xmax, ymax);
		
		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, xmin, ymin, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmin, ymax);
		lineOnScreen(gl, xmin, ymin, xmin, ymax);
	}
	
	private void tekenButtonMetKleur(GL gl, float xmin, float ymin, float xmax, float ymax, float r, float g, float b){
		gl.glColor3f(r, g, b);
		rechthoek(gl, xmin, ymin, xmax, ymax);
		
		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, xmin, ymin, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmin, ymax);
		lineOnScreen(gl, xmin, ymin, xmin, ymax);
	}
	
	
	private void drawCatalogus(GL gl){
		if (catalogus){
			if (drawMode == KOLOM){
				tekenButtonMetKleur(gl, 109.8f/1920*screenWidth, 751.25f/1080*screenHeight, 209.8f/1920*screenWidth, 851.25f/1080*screenHeight, 1f, 0f, 0f);
				tekenButtonMetKleur(gl, 229.6f/1920*screenWidth, 751.25f/1080*screenHeight, 329.6f/1920*screenWidth, 851.25f/1080*screenHeight, 0f, 1f, 0f);
			}
			if (drawMode == MUUR){
				tekenButtonMetKleur(gl, 109.8f/1920*screenWidth, 751.25f/1080*screenHeight, 209.8f/1920*screenWidth, 851.25f/1080*screenHeight, 1f, 0f, 0f);
				tekenButtonMetKleur(gl, 229.6f/1920*screenWidth, 751.25f/1080*screenHeight, 329.6f/1920*screenWidth, 851.25f/1080*screenHeight, 0f, 1f, 0f);
			}
		}
	}
	
	/**
	 * If the window is updated, or the gridsize is changed, updateGrid will be called and call upon certain methods to make
	 * sure that the grid will grade the mouse coordinates when clicked appropriately
	 */ 
	private void updateGrid(){
		
	}
	
	private void drawGrid(GL gl, float xmin, float ymin, float xmax, float ymax, int gridcolumns, int gridrows ){
		
		//bepaal afmetingen gecentreerde grid
		float xmidden = (xmax+xmin)/2;
		float ymidden = (ymax+ymin)/2;
		
		float columndist = (xmax-xmin)/(gridcolumns);
		float rowdist = (ymax-ymin)/(gridrows);
		float distance = 1; //willekeurig
		
		if (columndist < rowdist){
			distance = columndist;
			ymin = ymidden-distance*gridrows/2;
			ymax = ymidden+distance*gridrows/2;
		}
		else if (columndist > rowdist){
			distance = rowdist;
			xmin = xmidden-(distance*gridcolumns)/2;
			xmax = xmidden+(distance*gridcolumns)/2;
		}
			
		//grijs voor grid
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, xmin, ymin, xmax, ymax);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
			
		//lijnen voor grid
		for (float i =xmin; i<= xmax+1;  i=i+distance){ //xmax is plus 1 omdat soms de laatste lijn niet getekend wordt
			lineOnScreen (gl, i, ymin, i, ymax);
		}
		
		for (float i =ymin; i<= ymax+1; i=i+distance){ //ymax is plus 1 omdat soms de laatste lijn niet getekend wordt
			lineOnScreen (gl, xmin, i, xmax, i);
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

	public void tekenLevelEditorAchtergrond(GL gl){
		
		// kolom knop
		float columnbxmin = 90f/1920f*screenWidth;
		float columnbymin = 890f/1080f*screenHeight;
		float columnbxmax = 190f/1920f*screenWidth;
		float columnbymax = 990f/1080f*screenHeight;
		
		// muur knop
		float wallbxmin = 223f/1920f*screenWidth;
		float wallbymin = 890f/1080f*screenHeight;
		float wallbxmax = 323f/1920f*screenWidth;
		float wallbymax = 990f/1080f*screenHeight;
		
		// dak knop
		float roofbxmin = 356f/1920f*screenWidth;
		float roofbymin = 890f/1080f*screenHeight;
		float roofbxmax = 456f/1920f*screenWidth;
		float roofbymax = 990f/1080f*screenHeight;
		
		// gebouw knop
		float buildbxmin = 489f/1920f*screenWidth;
		float buildbymin = 890f/1080f*screenHeight;
		float buildbxmax = 589f/1920f*screenWidth;
		float buildbymax = 990f/1080f*screenHeight;
		
		//homebutton
		tekenButtonMetKleur(gl, 15f/1920f*screenWidth, 1000f/1080f*screenHeight, 85f/1920f*screenWidth, 1070f/1080f*screenHeight, 0.3f, 0.3f, 0.8f);
		
		//button1
		tekenButton(gl, columnbxmin, columnbymin, columnbxmax, columnbymax);
		
		gl.glColor3f(0.71f, 0.90f, 0.11f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(128f/1920f*screenWidth, 904f/1080f*screenHeight);
		gl.glVertex2f(148f/1920f*screenWidth, 904f/1080f*screenHeight);
		gl.glVertex2f(148f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glVertex2f(128f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glEnd();
		gl.glColor3f(0.13f, 0.69f, 0.30f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(128f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glVertex2f(148f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glVertex2f(161f/1920f*screenWidth, 974f/1080f*screenHeight);
		gl.glVertex2f(141f/1920f*screenWidth, 974f/1080f*screenHeight);
		gl.glEnd();
		gl.glColor3f(0.42f, 0.68f, 0.14f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(148f/1920f*screenWidth, 904f/1080f*screenHeight);
		gl.glVertex2f(161f/1920f*screenWidth, 914f/1080f*screenHeight);
		gl.glVertex2f(161f/1920f*screenWidth, 974f/1080f*screenHeight);
		gl.glVertex2f(148f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glEnd();
		
		//button2
		tekenButton(gl, wallbxmin, wallbymin, wallbxmax, wallbymax);
		
		gl.glColor3f(0.71f, 0.90f, 0.11f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(241f/1920f*screenWidth, 911f/1080f*screenHeight);
		gl.glVertex2f(293f/1920f*screenWidth, 911f/1080f*screenHeight);
		gl.glVertex2f(293f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glVertex2f(241f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glEnd();
		gl.glColor3f(0.13f, 0.69f, 0.30f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(241f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glVertex2f(293f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glVertex2f(307f/1920f*screenWidth, 973f/1080f*screenHeight);
		gl.glVertex2f(253f/1920f*screenWidth, 973f/1080f*screenHeight);
		gl.glEnd();
		gl.glColor3f(0.42f, 0.68f, 0.14f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(293f/1920f*screenWidth, 911f/1080f*screenHeight);
		gl.glVertex2f(307f/1920f*screenWidth, 922f/1080f*screenHeight);
		gl.glVertex2f(307f/1920f*screenWidth, 973f/1080f*screenHeight);
		gl.glVertex2f(293f/1920f*screenWidth, 964f/1080f*screenHeight);
		gl.glEnd();
		
		//button3
		tekenButton(gl, roofbxmin, roofbymin, roofbxmax, roofbymax);
		
		//button4
		tekenButton(gl, buildbxmin, buildbymin, buildbxmax, buildbymax);
		
		//zwart onder de buttons
		gl.glColor3f(0f, 0f, 0f);
		rechthoek(gl, 90f/1920f*screenWidth, 90f/1080f*screenHeight, 589f/1920f*screenWidth, 860f/1080f*screenHeight);
		
		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, 90f/1920f*screenWidth, 90f/1080f*screenHeight, 589f/1920f*screenWidth, 90f/1080f*screenHeight);
		lineOnScreen(gl, 589f/1920f*screenWidth, 90f/1080f*screenHeight, 589f/1920f*screenWidth, 860f/1080f*screenHeight);
		lineOnScreen(gl, 589f/1920f*screenWidth, 860f/1080f*screenHeight, 90f/1920f*screenWidth, 860f/1080f*screenHeight);
		lineOnScreen(gl, 90f/1920f*screenWidth, 860f/1080f*screenHeight, 90f/1920f*screenWidth, 90f/1080f*screenHeight);
		
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		//grijs min boven
		rechthoek(gl, (1330f-28f)/1920f*screenWidth, (990f+20f)/1080f*screenHeight, (1330f-8f)/1920f*screenWidth, (990f+40f)/1080f*screenHeight);
		//grijs plus boven
		rechthoek(gl, (1330f+8f)/1920f*screenWidth, (990f+20f)/1080f*screenHeight, (1330f+28f)/1920f*screenWidth, (990f+40f)/1080f*screenHeight);
		//grijs min onder
		rechthoek(gl, (1330f-28f)/1920f*screenWidth, (90f-40f)/1080f*screenHeight, (1330f-8f)/1920f*screenWidth, (90f-20f)/1080f*screenHeight);
		//grijs plus onder
		rechthoek(gl, (1330f+8f)/1920f*screenWidth, (90f-40f)/1080f*screenHeight, (1330f+28f)/1920f*screenWidth, (90f-20f)/1080f*screenHeight);
		//grijs min links
		rechthoek(gl, (830f-40f)/1920f*screenWidth, (540f+28f)/1080f*screenHeight, (830f-20f)/1920f*screenWidth, (540f+8f)/1080f*screenHeight);
		//grijs plus links
		rechthoek(gl, (830f-40f)/1920f*screenWidth, (540f-8f)/1080f*screenHeight, (830f-20f)/1920f*screenWidth, (540f-28f)/1080f*screenHeight);
		//grijs min rechts
		rechthoek(gl, (1830f+20f)/1920f*screenWidth, (540f+28f)/1080f*screenHeight, (1830f+40f)/1920f*screenWidth, (540f+8f)/1080f*screenHeight);
		//grijs plus rechts
		rechthoek(gl, (1830f+20f)/1920f*screenWidth, (540f-8f)/1080f*screenHeight, (1830f+40f)/1920f*screenWidth, (540f-28f)/1080f*screenHeight);
		
		//grijs naast de buttons
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		rechthoek(gl, 622f/1920f*screenWidth, 948f/1080f*screenHeight, 740f/1920f*screenWidth, 990f/1080f*screenHeight);
		rechthoek(gl, 622f/1920f*screenWidth, 890f/1080f*screenHeight, 740f/1920f*screenWidth, 932f/1080f*screenHeight);
		
		//levelbuttons
		tekenButton(gl, 622f/1920f*screenWidth, (860f-51f)/1080f*screenHeight, (622f+51f)/1920f*screenWidth, 860f/1080f*screenHeight);
		tekenButton(gl, (740f-51f)/1920f*screenWidth, (860f-51f)/1080f*screenHeight, 740f/1920f*screenWidth, 860f/1080f*screenHeight);
		
		
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

	


	
	/**
	 * A function defined in MouseListener. Is called when the pointer is in the GLCanvas, and a mouse button is released.
	 */
	public void mouseReleased(MouseEvent me) {
			// Check if the coordinates correspond to any of the top left buttons
		
		//afmetingen grid
		//klikken op grid
		float xmin = 830f/1920f*screenWidth;
		float ymin = 90f/1080f*screenHeight;
		float xmax = 1830f/1920f*screenWidth;
		float ymax = 990f/1080f*screenHeight;
		
		float xmidden = (xmax+xmin)/2;
		float ymidden = (ymax+ymin)/2;
		
		float columndist = (xmax-xmin)/(gridcolumns);
		float rowdist = (ymax-ymin)/(gridrows);
		float distance = 1; //willekeurig
		
		if (columndist < rowdist){
			distance = columndist;
			ymin = ymidden-distance*gridrows/2;
			ymax = ymidden+distance*gridrows/2;
		}
		else if (columndist > rowdist){
			distance = rowdist;
			xmin = xmidden-(distance*gridcolumns)/2;
			xmax = xmidden+(distance*gridcolumns)/2;
		}
		
		
		//linkermuisknop
		if (me.getButton()==1){	
			//drawMode knoppen
			if (90f/1080f*screenHeight < me.getY() && me.getY() < 190f/1080f*screenHeight) {
				if (90f/1920f*screenWidth < me.getX() && me.getX() < 190f/1920f*screenWidth) {
					// The first button is clicked
					drawMode = KOLOM;
					System.out.println("Mode: KOLOM");
					catalogus = true;
				}
				else if (223f/1920f*screenWidth < me.getX() && me.getX() < 323f/1920f*screenWidth) {
					// The second button is clicked
					drawMode = MUUR;
					System.out.println("Mode: MUUR");
					catalogus = true;
				}
				else if (356f/1920f*screenWidth < me.getX() && me.getX() < 456f/1920f*screenWidth) {
					// The third button is clicked
					drawMode = DAK;
					System.out.println("Mode: DAK");
					catalogus = true;
				}
				else if (489f/1920f*screenWidth < me.getX() && me.getX() < 589f/1920f*screenWidth) {
					// The fourth button is clicked
					drawMode = GEBOUW;
					System.out.println("Mode: GEBOUW");
					catalogus = true;
				}

			}
			
			//catalogus knoppen
			
			if ((1-851.25f/1080)*screenHeight < me.getY() && me.getY() < (1-751.25f/1080)*screenHeight) {
				if (109.8f/1920*screenWidth < me.getX() && me.getX() < 209.8f/1920*screenWidth) {
					// The first button is clicked
					textureMode = 1;
					System.out.println("Catalogus: TEXTURE 1");
					catalogus = false;
				}
				if (229.6f/1920*screenWidth < me.getX() && me.getX() < 329.6f/1920*screenWidth) {
					// The second button is clicked
					textureMode = 2;
					System.out.println("Catalogus: TEXTURE 2");
					catalogus = false;
				}
			}
			
			//grid knoppen
			
			if ((1f-(990f+20f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(990f+40f)/1080f)*screenHeight){
				if ((1330f-28f)/1920f*screenWidth < me.getX() && me.getX() < (1330f-8f)/1920f*screenWidth){
					//minknop boven
					if (gridrows != 1){
						gridrows = gridrows - 1;
						System.out.println("Aantal rijen: " + gridrows);
						wereld = veranderMatrixGrootte2();
					}
				}
				else if ((1330f+8f)/1920f*screenWidth < me.getX() && me.getX() < (1330f+28f)/1920f*screenWidth){
					//plusknop boven
					gridrows = gridrows + 1;
					System.out.println("Aantal rijen: " + gridrows);
					wereld = veranderMatrixGrootte2();
				}
			}
			
			if ((1f-(90f-40f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(90f-20f)/1080f)*screenHeight){
				if ((1330f-28f)/1920f*screenWidth < me.getX() && me.getX() < (1330f-8f)/1920f*screenWidth){
					//minknop onder
					if (gridrows != 1){
						gridrows = gridrows - 1;
						System.out.println("Aantal rijen: " + gridrows);
						wereld = veranderMatrixGrootte();
					}
				}
				else if ((1330f+8f)/1920f*screenWidth < me.getX() && me.getX() < (1330f+28f)/1920f*screenWidth){
					//plusknop onder
					gridrows = gridrows + 1;
					System.out.println("Aantal rijen: " + gridrows);
					wereld = veranderMatrixGrootte();
				}
			}
			
			if ((830f-40f)/1920f*screenWidth < me.getX() && me.getX() < (830f-20f)/1920f*screenWidth){
				if ((1f-(540f+8f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(540f+28f)/1080f)*screenHeight){
					//minknop links
					if (gridcolumns != 1){
						gridcolumns = gridcolumns - 1;
						System.out.println("Aantal kolommen: " + gridcolumns);
						wereld = veranderMatrixGrootte2();
					}
				}
				else if ((1f-(540f-28f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(540f-8f)/1080f)*screenHeight){
					//plusknop links
					gridcolumns = gridcolumns + 1;
					System.out.println("Aantal kolommen: " + gridcolumns);
					wereld = veranderMatrixGrootte2();
				}
			}
			
			if ((1830f+20f)/1920f*screenWidth < me.getX() && me.getX() < (1830f+40f)/1920f*screenWidth){
				if ((1f-(540f+8f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(540f+28f)/1080f)*screenHeight){
					//minknop rechts
					if (gridcolumns != 1){
						gridcolumns = gridcolumns - 1;
						System.out.println("Aantal kolommen: " + gridcolumns);
						wereld = veranderMatrixGrootte();
					}
				}
				else if ((1f-(540f-28f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(540f-8f)/1080f)*screenHeight){
					//plusknop rechts
					gridcolumns = gridcolumns + 1;
					System.out.println("Aantal kolommen: " + gridcolumns);
					wereld = veranderMatrixGrootte();
				}
			}
			
			
			
			//gridklik
			
			if (xmin-distance/5 < me.getX() && me.getX() < xmax+distance/5 && screenHeight-ymax-distance/5 < me.getY() && me.getY() < screenHeight-ymin+distance/5){
				gridklikx = me.getX();
				gridkliky = screenHeight - me.getY();
				gridklik = true;
			}
			
			//saveAs (tijdelijk)
			if (10f/1080f*screenHeight < me.getY() && me.getY() < 80f/1080f*screenHeight)
			if (15f/1920f*screenWidth < me.getX() && me.getX() < 85f/1920f*screenWidth){
				System.out.println("save");
				try {saveAs();} catch (FileNotFoundException e) {}
			}
		}
		
		//rechtermuisknop
		else if (me.getButton()==3){
			if (xmin-distance/5 < me.getX() && me.getX() < xmax+distance/5 && screenHeight-ymax-distance/5 < me.getY() && me.getY() < screenHeight-ymin+distance/5){
				gridklikxrechts = me.getX();
				gridklikyrechts = screenHeight - me.getY();
				gridklik = true;
			}
		}
	
	}
	
	
	
	
	
	public void veranderMatrixVolgensKlikInGrid(GL gl){
		while (gridklik){
			float xmin = 830f/1920f*screenWidth;
			float ymin = 90f/1080f*screenHeight;
			float xmax = 1830f/1920f*screenWidth;
			float ymax = 990f/1080f*screenHeight;
			
			float xmidden = (xmax+xmin)/2;
			float ymidden = (ymax+ymin)/2;
			
			float columndist = (xmax-xmin)/(gridcolumns);
			float rowdist = (ymax-ymin)/(gridrows);
			float distance = 1; //willekeurig
			
			if (columndist < rowdist){
				distance = columndist;
				ymin = ymidden-distance*gridrows/2;
				ymax = ymidden+distance*gridrows/2;
			}
			else if (columndist > rowdist){
				distance = rowdist;
				xmin = xmidden-(distance*gridcolumns)/2;
				xmax = xmidden+(distance*gridcolumns)/2;
			}
			System.out.println("gridklik is " + gridklik);
			
			//LINKERMUISKNOP: plaats iets in de matrix
			
			//verander matrix tijdens mode DAK
			if (drawMode == DAK){
				for (int kolom=1; kolom <= gridcolumns; kolom++){
					for (int rij=1; rij <= gridrows; rij++){
						if (xmin+(kolom-1)*distance < gridklikx && gridklikx < xmin+kolom*distance && ymax-rij*distance < gridkliky && gridkliky < ymax-(rij-1)*distance){
							wereld[2*rij-1][2*kolom-1]=1;
							
							for (int i=0 ; i!=wereld.length ; i++){			//print matrix
								for (int j=0 ; j!=wereld[0].length ; j++){
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}
							
						}
					}
				}
			}
			
			//verander matrix tijdens mode KOLOM
			if (drawMode == KOLOM){
				for (int kolom=1; kolom <= gridcolumns+1; kolom++){
					for (int rij=1; rij <= gridrows+1; rij++){
						if (xmin+(kolom-1)*distance-distance/5 < gridklikx && gridklikx < xmin+(kolom-1)*distance+distance/5 && ymax-(rij-1)*distance-distance/5 < gridkliky && gridkliky < ymax-(rij-1)*distance+distance/5){
							wereld[2*rij-2][2*kolom-2] = textureMode;
							
							for (int i=0 ; i!=wereld.length ; i++){			//print matrix
								for (int j=0 ; j!=wereld[0].length ; j++){
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}
							
						}
					}
				}
			}
			 
			//verander matrix tijdens mode MUUR (verticaal)
			if (drawMode == MUUR){
				for (int kolom=1; kolom <= gridcolumns+1; kolom++){
					for (int rij=1; rij <= gridrows+1; rij++){
						if (xmin+(kolom-1)*distance-distance/5 < gridklikx && gridklikx < xmin+(kolom-1)*distance+distance/5 && ymax-rij*distance+distance/5 < gridkliky && gridkliky < ymax-(rij-1)*distance-distance/5){
							wereld[2*rij-1][2*kolom-2]=textureMode;
							
							checkVoorAangrenzendeMurenVerticaal(rij,kolom);
							
							for (int i=0 ; i!=wereld.length ; i++){			//print matrix
								for (int j=0 ; j!=wereld[0].length ; j++){
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}
							
						}
					}
				}
			}
			
			//verander matrix tijdens mode MUUR (horizontaal)
			if (drawMode == MUUR){
				for (int kolom=1; kolom <= gridcolumns+1; kolom++){
					for (int rij=1; rij <= gridrows+1; rij++){
						if (xmin+(kolom-1)*distance+distance/5 < gridklikx && gridklikx < xmin+kolom*distance-distance/5 && ymax-(rij-1)*distance-distance/5 < gridkliky && gridkliky < ymax-(rij-1)*distance+distance/5){
							wereld[2*rij-2][2*kolom-1]=textureMode;
							
							checkVoorAangrenzendeMurenHorizontaal(rij,kolom);
							
							for (int i=0 ; i!=wereld.length ; i++){			//print matrix
								for (int j=0 ; j!=wereld[0].length ; j++){
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}
							
						}
					}
				}
			}
			
			//teken kolom als 2 muren aan elkaar grenzen bij het plaatsen van een MUUR
			if (drawMode == MUUR)
				
			gridklikx = 10000;
			gridkliky = 10000;
			
			//RECHTERMUISKNOP: verwijder iets uit de matrix
			
			//verander matrix tijdens mode DAK
			
				for (int kolom=1; kolom <= gridcolumns; kolom++){
					for (int rij=1; rij <= gridrows; rij++){
						if (xmin+(kolom-1)*distance+distance/10 < gridklikxrechts && gridklikxrechts < xmin+kolom*distance-distance/10 && ymax-rij*distance+distance/10 < gridklikyrechts && gridklikyrechts < ymax-(rij-1)*distance-distance/10){
							wereld[2*rij-1][2*kolom-1]=0;
							
							for (int i=0 ; i!=wereld.length ; i++){			//print matrix
								for (int j=0 ; j!=wereld[0].length ; j++){
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}
							
						}
					}
				}
			
			
			//verander matrix tijdens mode KOLOM
			
				for (int kolom=1; kolom <= gridcolumns+1; kolom++){
					for (int rij=1; rij <= gridrows+1; rij++){
						if (xmin+(kolom-1)*distance-distance/10 < gridklikxrechts && gridklikxrechts < xmin+(kolom-1)*distance+distance/10 && ymax-(rij-1)*distance-distance/10 < gridklikyrechts && gridklikyrechts < ymax-(rij-1)*distance+distance/10){
							wereld[2*rij-2][2*kolom-2]=0;
							
							for (int i=0 ; i!=wereld.length ; i++){			//print matrix
								for (int j=0 ; j!=wereld[0].length ; j++){
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}
							
						}
					}
				}
			
			
			//verander matrix tijdens mode MUUR (verticaal)
			
				for (int kolom=1; kolom <= gridcolumns+1; kolom++){
					for (int rij=1; rij <= gridrows+1; rij++){
						if (xmin+(kolom-1)*distance-distance/10 < gridklikxrechts && gridklikxrechts < xmin+(kolom-1)*distance+distance/10 && ymax-rij*distance+distance/10 < gridklikyrechts && gridklikyrechts < ymax-(rij-1)*distance-distance/10){
							wereld[2*rij-1][2*kolom-2]=0;
							
							for (int i=0 ; i!=wereld.length ; i++){			//print matrix
								for (int j=0 ; j!=wereld[0].length ; j++){
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}
							
						}
					}
				}
			
			
			//verander matrix tijdens mode MUUR (horizontaal)
			
				for (int kolom=1; kolom <= gridcolumns+1; kolom++){
					for (int rij=1; rij <= gridrows+1; rij++){
						if (xmin+(kolom-1)*distance+distance/10 < gridklikxrechts && gridklikxrechts < xmin+kolom*distance-distance/10 && ymax-(rij-1)*distance-distance/10 < gridklikyrechts && gridklikyrechts < ymax-(rij-1)*distance+distance/10){
							wereld[2*rij-2][2*kolom-1]=0;
							
							for (int i=0 ; i!=wereld.length ; i++){			//print matrix
								for (int j=0 ; j!=wereld[0].length ; j++){
									System.out.print(wereld[i][j] + " ");
								}
								System.out.println();
							}
							
						}
					}
				}
			
			gridklikxrechts = 10000;
			gridklikyrechts = 10000;
			gridklik = false;
		}
	}
	
	public void checkVoorAangrenzendeMurenVerticaal(int rij, int kolom){
		if (checkIfExists((2*rij-1)+1,(2*kolom-2)-1) || checkIfExists((2*rij-1)+2,2*kolom-2) || checkIfExists((2*rij-1)+1,(2*kolom-2)+1)){
			if (wereld[(2*rij-1)+1][2*kolom-2] == 0){
				wereld[(2*rij-1)+1][2*kolom-2] = textureMode;
			}
		}
		if (checkIfExists((2*rij-1)-1,(2*kolom-2)-1) || checkIfExists((2*rij-1)-2,2*kolom-2) || checkIfExists((2*rij-1)-1,(2*kolom-2)+1)){
			if (wereld[(2*rij-1)-1][2*kolom-2] == 0){
				wereld[(2*rij-1)-1][2*kolom-2] = textureMode;
			}
		}
	}
	
	public void checkVoorAangrenzendeMurenHorizontaal(int rij, int kolom){
		if (checkIfExists((2*rij-2)-1,(2*kolom-1)-1) || checkIfExists(2*rij-2,(2*kolom-1)-2) || checkIfExists((2*rij-2)+1,(2*kolom-1)-1)){
			if (wereld[2*rij-2][(2*kolom-1)-1] == 0){
				wereld[2*rij-2][(2*kolom-1)-1] = textureMode;
			}
		}
		if (checkIfExists((2*rij-2)-1,(2*kolom-1)+1) || checkIfExists(2*rij-2,(2*kolom-1)+2) || checkIfExists((2*rij-2)+1,(2*kolom-1)+1)){
			if (wereld[2*rij-2][(2*kolom-1)+1] == 0){
				wereld[2*rij-2][(2*kolom-1)+1] = textureMode;
			}
		}
	}
	
	public boolean checkIfExists (int rij, int kolom){
		if (0 <= rij && rij <= wereld.length-1 && 0 <= kolom && kolom <= wereld[0].length-1 && wereld[rij][kolom] != 0){
			return true;
		}
		else return false;
	}
	
	public int[][] veranderMatrixGrootte(){
		int[][] res = new int[2*gridrows+1][2*gridcolumns+1];
		if (res.length < wereld.length || res[0].length < wereld[0].length){
			for (int i=0 ; i!=res.length ; i++){			//kopieert de oude matrix naar de nieuwe, met verlies van 1 van de zijkanten van de oude matrix
				for (int j=0 ; j!=res[0].length ; j++){
					res[i][j] = wereld[i][j];
				}
			}
		}
		if (res.length > wereld.length || res[0].length > wereld[0].length){
			for (int i=0 ; i!=res.length ; i++){			//zet nieuwe matrix vol nullen
				for (int j=0 ; j!=res[0].length ; j++){
					res[i][j] = 0;
				}
			}
			for (int i=0 ; i!=wereld.length ; i++){			//kopieert de oude matrix naar de nieuwe
				for (int j=0 ; j!=wereld[0].length ; j++){
					res[i][j] = wereld[i][j];
				}
			}
		}
		for (int i=0 ; i!=res.length ; i++){			//print matrix
			for (int j=0 ; j!=res[0].length ; j++){
				System.out.print(res[i][j] + " ");
			}
			System.out.println();
		}
		return res;
	}
	
	public int[][] veranderMatrixGrootte2(){
		int[][] res = new int[2*gridrows+1][2*gridcolumns+1];
		if (res.length < wereld.length){
			for (int i=0 ; i!=res.length ; i++){			//kopieert de oude matrix naar de nieuwe, met verlies van 1 van de zijkanten van de oude matrix
				for (int j=0 ; j!=res[0].length ; j++){
					res[i][j] = wereld[i+2][j];
				}
			}
		}
		if (res[0].length < wereld[0].length){
			for (int i=0 ; i!=res.length ; i++){			//kopieert de oude matrix naar de nieuwe, met verlies van 1 van de zijkanten van de oude matrix
				for (int j=0 ; j!=res[0].length ; j++){
					res[i][j] = wereld[i][j+2];
				}
			}
		}
		if (res.length > wereld.length){
			for (int i=0 ; i!=res.length ; i++){			//zet nieuwe matrix vol nullen
				for (int j=0 ; j!=res[0].length ; j++){
					res[i][j] = 0;
				}
			}
			for (int i=0 ; i!=wereld.length ; i++){			//kopieert de oude matrix naar de nieuwe
				for (int j=0 ; j!=wereld[0].length ; j++){
					res[i+2][j] = wereld[i][j];
				}
			}
		}
		if (res[0].length > wereld[0].length){
			for (int i=0 ; i!=res.length ; i++){			//zet nieuwe matrix vol nullen
				for (int j=0 ; j!=res[0].length ; j++){
					res[i][j] = 0;
				}
			}
			for (int i=0 ; i!=wereld.length ; i++){			//kopieert de oude matrix naar de nieuwe
				for (int j=0 ; j!=wereld[0].length ; j++){
					res[i][j+2] = wereld[i][j];
				}
			}
		}
		for (int i=0 ; i!=res.length ; i++){			//print matrix
			for (int j=0 ; j!=res[0].length ; j++){
				System.out.print(res[i][j] + " ");
			}
			System.out.println();
		}
		return res;
	}
	
	private void drawFigure(GL gl) {
		if (!catalogus){
			switch (drawMode) {
			case NIETS:
				break;
			case KOLOM:
				switch (textureMode) {
				case 1:
					tekenFakeKolom(gl);
				case 2:
					tekenFakeKolom(gl);
				}
				break;
			case MUUR:
				switch (textureMode) {
				case 1:
					tekenFakeMuur(gl);
				case 2:
					tekenFakeMuur(gl);
				}
				break;
			case DAK:
				
				break;
			case GEBOUW:
				
				break;
			}
		}
	}
	
	private void drawGridInhoud(GL gl){
		float xmin = 830f/1920f*screenWidth;
		float ymin = 90f/1080f*screenHeight;
		float xmax = 1830f/1920f*screenWidth;
		float ymax = 990f/1080f*screenHeight;
		
		float xmidden = (xmax+xmin)/2;
		float ymidden = (ymax+ymin)/2;
		
		float columndist = (xmax-xmin)/(gridcolumns);
		float rowdist = (ymax-ymin)/(gridrows);
		float distance = 1; //willekeurig
		
		if (columndist < rowdist){
			distance = columndist;
			ymin = ymidden-distance*gridrows/2;
			ymax = ymidden+distance*gridrows/2;
		}
		else if (columndist > rowdist){
			distance = rowdist;
			xmin = xmidden-(distance*gridcolumns)/2;
			xmax = xmidden+(distance*gridcolumns)/2;
		}
		
		//teken dak
		for (int rij=1; rij <= wereld.length-1; rij=rij+2){
			for (int kolom=1; kolom <= wereld[0].length-1; kolom=kolom+2){
				if (wereld[rij][kolom]==1){
					//onderstaande moet veranderen in een daktexture
					tekenButton(gl, xmin+(kolom/2)*distance, ymax-rij/2*distance ,xmin+(kolom/2+1)*distance, ymax-(rij/2+1)*distance);
				}
			}
		}	
		
		//teken kolom
		for (int rij=0; rij <= wereld.length; rij=rij+2){
			for (int kolom=0; kolom <= wereld[0].length; kolom=kolom+2){
				if (wereld[rij][kolom]==1){
					//onderstaande moet veranderen in een kolomtexture
					tekenButtonMetKleur(gl, xmin+((kolom+1)/2)*distance-distance/10, ymax-(rij+1)/2*distance+distance/10 ,xmin+((kolom+1)/2)*distance+distance/10, ymax-(rij+1)/2*distance-distance/10, 1f, 0f, 0f);
				}
				else if (wereld[rij][kolom]==2){
					//onderstaande moet veranderen in een kolomtexture
					tekenButtonMetKleur(gl, xmin+((kolom+1)/2)*distance-distance/10, ymax-(rij+1)/2*distance+distance/10 ,xmin+((kolom+1)/2)*distance+distance/10, ymax-(rij+1)/2*distance-distance/10, 0f, 1f, 0f);
				}
			}
		}	
		
		//teken verticale muur
		for (int rij=1; rij <= wereld.length-1; rij=rij+2){
			for (int kolom=0; kolom <= wereld[0].length; kolom=kolom+2){
				if (wereld[rij][kolom]==1){
					//onderstaande moet veranderen in een muurtexture
					tekenButtonMetKleur(gl, xmin+((kolom+1)/2)*distance-distance/10, ymax-(rij+1)/2*distance+distance*9/10 ,xmin+((kolom+1)/2)*distance+distance/10, ymax-(rij+1)/2*distance+distance/10, 1f ,0f, 0f);
				}
				if (wereld[rij][kolom]==2){
					//onderstaande moet veranderen in een muurtexture
					tekenButtonMetKleur(gl, xmin+((kolom+1)/2)*distance-distance/10, ymax-(rij+1)/2*distance+distance*9/10 ,xmin+((kolom+1)/2)*distance+distance/10, ymax-(rij+1)/2*distance+distance/10, 0f, 1f, 0f);
				}
			}
		}
		
		//teken horizontale muur
		for (int rij=0; rij <= wereld.length; rij=rij+2){
			for (int kolom=1; kolom <= wereld[0].length-1; kolom=kolom+2){
				if (wereld[rij][kolom]==1){
					//onderstaande moet veranderen in een muurtexture
					tekenButtonMetKleur(gl, xmin+((kolom+1)/2)*distance-distance*9/10, ymax-(rij+1)/2*distance+distance/10 ,xmin+((kolom+1)/2)*distance-distance/10, ymax-(rij+1)/2*distance-distance/10, 1f, 0f, 0f);
				}
				if (wereld[rij][kolom]==2){
					//onderstaande moet veranderen in een muurtexture
					tekenButtonMetKleur(gl, xmin+((kolom+1)/2)*distance-distance*9/10, ymax-(rij+1)/2*distance+distance/10 ,xmin+((kolom+1)/2)*distance-distance/10, ymax-(rij+1)/2*distance-distance/10, 0f, 1f, 0f);
				}
			}
		}
	}

	public void tekenFakeKolom(GL gl){
		gl.glColor3f(0.71f, 0.90f, 0.11f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(248f/1920f*screenWidth, 124f/1080f*screenHeight);
		gl.glVertex2f(348f/1920f*screenWidth, 124f/1080f*screenHeight);
		gl.glVertex2f(348f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glVertex2f(248f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glEnd();
		gl.glColor3f(0.13f, 0.69f, 0.30f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(248f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glVertex2f(348f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glVertex2f(415f/1920f*screenWidth, 572f/1080f*screenHeight);
		gl.glVertex2f(315f/1920f*screenWidth, 572f/1080f*screenHeight);
		gl.glEnd();
		gl.glColor3f(0.42f, 0.68f, 0.14f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(348f/1920f*screenWidth, 124f/1080f*screenHeight);
		gl.glVertex2f(415f/1920f*screenWidth, 192f/1080f*screenHeight);
		gl.glVertex2f(415f/1920f*screenWidth, 572f/1080f*screenHeight);
		gl.glVertex2f(348f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glEnd();
	}
	
	public void tekenFakeMuur(GL gl){
		gl.glColor3f(0.71f, 0.90f, 0.11f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(135f/1920f*screenWidth, 123f/1080f*screenHeight);
		gl.glVertex2f(489f/1920f*screenWidth, 123f/1080f*screenHeight);
		gl.glVertex2f(489f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glVertex2f(135f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glEnd();
		gl.glColor3f(0.13f, 0.69f, 0.30f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(135f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glVertex2f(489f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glVertex2f(557f/1920f*screenWidth, 570f/1080f*screenHeight);
		gl.glVertex2f(203f/1920f*screenWidth, 570f/1080f*screenHeight);
		gl.glEnd();
		gl.glColor3f(0.42f, 0.68f, 0.14f);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(489f/1920f*screenWidth, 123f/1080f*screenHeight);
		gl.glVertex2f(557f/1920f*screenWidth, 192f/1080f*screenHeight);
		gl.glVertex2f(557f/1920f*screenWidth, 570f/1080f*screenHeight);
		gl.glVertex2f(489f/1920f*screenWidth, 510f/1080f*screenHeight);
		gl.glEnd();
	}
	
	private void saveAs() throws FileNotFoundException{
		//PrintWriter bestand = new PrintWriter("C:\\Users\\Martijn\\Dropbox\\EWI3620TU Minorproject SOT Groep 01\\Level1_1_l.txt");
		KiesFileUitBrowser kfub = new KiesFileUitBrowser();
		String filename = kfub.saveFile(new Frame(), "Opslaan als...", ".\\", "*");
		//als de bestandsnaam al eindigt op .txt , knip dat er dan af
		if (filename.substring(filename.length()-4, filename.length()).equals(".txt")){
			filename = filename.substring(0, filename.length()-4);
		}
		String currentdir = System.getProperty("user.dir");
		PrintWriter bestand = new PrintWriter(currentdir + "\\levels\\" + filename + ".txt");
		bestand.println("0,0,0,");
		for (int i = 0; i != wereld.length; i++){
			for (int j = 0; j!=wereld[0].length; j++){
				bestand.print(wereld[i][j] + ",");
			}
			bestand.println();
		}
		bestand.close();

		PrintWriter bestand2 = new PrintWriter(currentdir + "\\levels\\" + "level1_1_h.txt");
		for (int i = 0; i != wereld.length; i++){
			for (int j = 0; j!=wereld[0].length; j++){
				bestand2.print(1 + ",");
			}
			bestand2.println();
		}
		bestand2.close();
	}
	
	public static int[][] read(String filename) throws FileNotFoundException{
		File file = new File(filename);
		Scanner sc = new Scanner(new File(filename));
		sc.useDelimiter("\\s*,\\s*");
		int x = sc.nextInt();
		int y = sc.nextInt();
		int z = sc.nextInt();
		
		//tel het aantal rijen en kolommen in de matrix
		int rows=-1;
		int columns=0;
		String string = "";
		while (sc.hasNextLine()){
			string = sc.nextLine();
			rows++;
		}
		Scanner stringsc = new Scanner(string);
		stringsc.useDelimiter("\\s*,\\s*");
		while (stringsc.hasNext()){
			stringsc.nextInt();
			columns++;
		}

		//lees de matrix
		Scanner sc2 = new Scanner(file);
		sc2.useDelimiter("\\s*,\\s*");
		sc2.nextLine();
		int[][] res = new int[rows][columns];
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				res[i][j] = sc2.nextInt();
			}
		}
		System.out.println("file ingelezen");
		return res;
	}
	
	public static int[][] defaultWorld(){
		 int[][] defaultworld = {
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					};
	  return defaultworld;
	}
	
	private void plaatsTexture(GL gl, double xmin, double ymin, double xmax, double ymax) {
		
		// Setting the floor color and material.
        //float[] rgba = {1f, 1f, 1f};
		//gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        //gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        //gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);

        // Apply texture.
        if(backTexture != null){
        	backTexture.enable();
        	gl.glBindTexture (GL.GL_TEXTURE_1D, 1);
        }
		
	//	gl.glNormal3d(0, 1, 0);
//        float wallColour[] = { 0.0f, 1.0f, 1.0f, 1.0f };
//    	gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, wallColour, 0);
        
		gl.glBegin(GL.GL_QUADS);
		gl.glTexCoord2d(0.0,0.0);
		gl.glVertex2d(xmin/1920*screenWidth, ymin/1080*screenHeight); 
		gl.glTexCoord2d(1.0,0.0);
		gl.glVertex2d(xmax/1920*screenWidth, ymin/1080*screenHeight);
		gl.glTexCoord2d(1.0,1.0);
		gl.glVertex2d(xmax/1920*screenWidth, ymax/1080*screenHeight);
		gl.glTexCoord2d(0.0,1.0);
		gl.glVertex2d(xmin/1920*screenWidth, ymax/1080*screenHeight);
		
		gl.glEnd();

	}

}