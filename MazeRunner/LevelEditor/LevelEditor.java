import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sun.corba.se.impl.ior.ByteBuffer;
import com.sun.media.sound.Toolkit;
import com.sun.opengl.impl.packrect.Rect;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class LevelEditor extends Frame implements GLEventListener, MouseListener {
	static final long serialVersionUID = 7526471155622776147L;

	// Screen size.
	private int screenWidth = 800, screenHeight = 600;
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
	
	private int gridrows = 9; //beginwaarden grid
	private int gridcolumns = 11;
	
	private float gridklikx = 10000; //10000 is een default waarde buiten het scherm, omdat je die waarde nooit kan krijgen door ergens te klikken
	private float gridkliky = 10000;
	private float gridklikxrechts = 10000;
	private float gridklikyrechts = 10000;
	private boolean gridklik = false;
	
	private boolean catalogus = false;
	private Texture backTexture;
	private int[][] wereld = {
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
	
	private ArrayList<Point2D.Float> points;
	Array columncheckamount;
	

	/**
	 * When instantiating, a GLCanvas is added for us to play with. An animator
	 * is created to continuously render the canvas.
	 */
	public LevelEditor() {
		super("Level Editor van onze supervette game");

		points = new ArrayList<Point2D.Float>();

		// Set the desired size and background color of the frame
		setSize(screenWidth, screenHeight);
		
		setBackground(new Color(0.34f, 0.11f, 0.13f));

		// When the "X" close button is called, the application should exit.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// The OpenGL capabilities should be set before initializing the
		// GLCanvas. We use double buffering and hardware acceleration.
		GLCapabilities caps = new GLCapabilities();
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		// Create a GLCanvas with the specified capabilities and add it to this
		// frame. Now, we have a canvas to draw on using JOGL.
		canvas = new GLCanvas(caps);
		add(canvas);

		// Set the canvas' GL event listener to be this class. Doing so gives
		// this class control over what is rendered on the GL canvas.
		canvas.addGLEventListener(this);

		// Also add this class as mouse listener, allowing this class to react
		// to mouse events that happen inside the GLCanvas.
		canvas.addMouseListener(this);

		// An Animator is a JOGL help class that can be used to make sure our
		// GLCanvas is continuously being re-rendered. The animator is run on a
		// separate thread from the main thread.
		Animator anim = new Animator(canvas);
		anim.start();

		// With everything set up, the frame can now be displayed to the user.
		setVisible(true);
	}

	@Override
	/**
	 * A function defined in GLEventListener. It is called once, when the frame containing the GLCanvas 
	 * becomes visible. In this assignment, there is no moving �camera�, so the view and projection can 
	 * be set at initialization. 
	 */
	public void init(GLAutoDrawable drawable) {
		// Retrieve the OpenGL handle, this allows us to use OpenGL calls.
		GL gl = drawable.getGL();

		// Set the matrix mode to GL_PROJECTION, allowing us to manipulate the
		// projection matrix
		gl.glMatrixMode(GL.GL_PROJECTION);

		// Always reset the matrix before performing transformations, otherwise
		// those transformations will stack with previous transformations!
		gl.glLoadIdentity();

		/*
		 * glOrtho performs an "orthogonal projection" transformation on the
		 * active matrix. In this case, a simple 2D projection is performed,
		 * matching the viewing frustum to the screen size.
		 */
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);

		// Set the matrix mode to GL_MODELVIEW, allowing us to manipulate the
		// model-view matrix.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// We leave the model view matrix as the identity matrix. As a result,
		// we view the world 'looking forward' from the origin.
		gl.glLoadIdentity();

		// We have a simple 2D application, so we do not need to check for depth
		// when rendering.
		gl.glDisable(GL.GL_DEPTH_TEST);
		loadTextures(gl, "\\LevelEditor\\EditorBackgroundSmall.png");
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

	@Override
	/**
	 * A function defined in GLEventListener. This function is called many times per second and should 
	 * contain the rendering code.
	 */
	public void display(GLAutoDrawable drawable) {
		
		
		GL gl = drawable.getGL();

				
		// Set the clear color and clear the screen.
		gl.glClearColor(0.34f, 0.11f, 0.13f, 1);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
		//achtergrond texture
//		plaatsTexture(gl, 0, 0, 1920, 1080);
		
	

		// Draw the buttons.
		//drawButtons(gl);
		tekenLevelEditorAchtergrond(gl);
		drawGrid(gl, 770f/1920f*screenWidth, 90f/1080f*screenHeight, 1830f/1920f*screenWidth , 990f/1080f*screenHeight, gridcolumns, gridrows);
		drawGridInhoud(gl);
		
		veranderMatrixVolgensKlikInGrid(gl);
		
		drawCatalogus(gl);
		
		drawFigure(gl);

		// Flush the OpenGL buffer, outputting the result to the screen.
		gl.glFlush();
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
	
	/*private void gridCheckcolumn(float xmin, float ymin, float xmax, float ymax, int gridrows, int gridcolumns){
		
		// deze array moet straks als een attribuut van de klasse
		int amount = (gridrows+1)*(gridcolumns+1);
		
		ArrayList columncheckx = new ArrayList();
		ArrayList columnchecky = new ArrayList();
		
		float xd= xmax-xmin;
		float yd= ymax-ymin;
		
		float rowdist = xd/(gridrows);
		float columndist = yd/(gridcolumns);
		
		for (float i=xmin; i< xd; i=i+rowdist){
			for (float y = ymin; i<yd){
				
			
		}
		
		
		
		// column
		
		
		// wall
		// roof
		
		
		}
	*/	
	
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
		
		//tijdelijke savebutton
		tekenButtonMetKleur(gl, 10f/1920f*screenWidth, 1000f/1080f*screenHeight, 80f/1920f*screenWidth, 1070f/1080f*screenHeight, 0.3f, 0.3f, 0.8f);
		
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
		
		//grijs naast de buttons voor keuze van gridgrootte
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		rechthoek(gl, 622f/1920f*screenWidth, 890f/1080f*screenHeight, 737f/1920f*screenWidth, 990f/1080f*screenHeight);
		
		//minknop x
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, (20f+622f)/1920f*screenWidth, (70f+890f)/1080f*screenHeight, (40f+622f)/1920f*screenWidth, (90f+890f)/1080f*screenHeight);

		//grijs tussen x-knoppen
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, (10f+622f)/1920f*screenWidth, (40f+890f)/1080f*screenHeight, (50f+622f)/1920f*screenWidth, (60f+890f)/1080f*screenHeight);

		//plusknop x
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, (20f+622f)/1920f*screenWidth, (10f+890f)/1080f*screenHeight, (40f+622f)/1920f*screenWidth, (30f+890f)/1080f*screenHeight);
		
		//minknop y
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, (75f+622f)/1920f*screenWidth, (70f+890f)/1080f*screenHeight, (95f+622f)/1920f*screenWidth, (90f+890f)/1080f*screenHeight);

		//grijs tussen y-knoppen
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, (65f+622f)/1920f*screenWidth, (40f+890f)/1080f*screenHeight, (105f+622f)/1920f*screenWidth, (60f+890f)/1080f*screenHeight);

		//plusknop y
		gl.glColor3f(0.76f, 0.76f, 0.76f);
		rechthoek(gl, (75f+622f)/1920f*screenWidth, (10f+890f)/1080f*screenHeight, (95f+622f)/1920f*screenWidth, (30f+890f)/1080f*screenHeight);
		
		//grijs voor schuifbalk
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		rechthoek(gl, 659f/1920f*screenWidth, 90f/1080f*screenHeight, 700f/1920f*screenWidth, 860f/1080f*screenHeight);
		
		
		
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

	@Override
	/**
	 * A function defined in GLEventListener. This function is called when there is a change in certain 
	 * external display settings. 
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// Not needed.
	}

	@Override
	/**
	 * A function defined in GLEventListener. This function is called when the GLCanvas is resized or moved. 
	 * Since the canvas fills the frame, this event also triggers whenever the frame is resized or moved.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();

		// Set the new screen size and adjusting the viewport
		screenWidth = width;
		screenHeight = height;
		buttonSize = height / 10.0f;
		gl.glViewport(0, 0, screenWidth, screenHeight);

		// Update the projection to an orthogonal projection using the new screen size
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);
	}

	@Override
	/**
	 * A function defined in MouseListener. Is called when the pointer is in the GLCanvas, and a mouse button is released.
	 */
	public void mouseReleased(MouseEvent me) {
			// Check if the coordinates correspond to any of the top left buttons
		
		//afmetingen grid
		//klikken op grid
		float xmin = 770f/1920f*screenWidth;
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
			if ((20f+622f)/1920f*screenWidth < me.getX() && me.getX() < (40f+622f)/1920f*screenWidth){
				if ((1f-(70f+890f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(90f+890f)/1080f)*screenHeight){
					//minknop kolommen
					if (gridcolumns != 1){
						gridcolumns = gridcolumns - 1;
						System.out.println("Aantal kolommen: " + gridcolumns);
						wereld = veranderMatrixGrootte();
					}
				}
				else if ((1f-(10f+890f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(30f+890f)/1080f)*screenHeight){
					//plusknop kolommen
					gridcolumns = gridcolumns + 1;
					System.out.println("Aantal kolommen: " + gridcolumns);
					wereld = veranderMatrixGrootte();
				}
			}
			if ((75f+622f)/1920f*screenWidth < me.getX() && me.getX() < (95f+622f)/1920f*screenWidth){
				if ((1f-(70f+890f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(90f+890f)/1080f)*screenHeight){
					//minknop rijen
					if (gridrows != 1){
						gridrows = gridrows - 1;
						System.out.println("Aantal rijen: " + gridrows);
						wereld = veranderMatrixGrootte();
					}
				}
				else if ((1f-(10f+890f)/1080f)*screenHeight > me.getY() && me.getY() > (1f-(30f+890f)/1080f)*screenHeight){
					//plusknop rijen
					gridrows = gridrows + 1;
					System.out.println("Aantal rijen: " + gridrows);
					wereld = veranderMatrixGrootte();
				}
			}
			
			//gridklik
			
			if (xmin-distance/5 < me.getX() && me.getX() < xmax+distance/5 && screenHeight-ymax-distance/5 < me.getY() && me.getY() < screenHeight-ymin+distance/5){
				gridklikx = me.getX();
				gridkliky = screenHeight - me.getY();
				gridklik = true;
			}
			
			//save (tijdelijk)
			if (me.getX() < 80f/1920f*screenWidth && me.getY() < 80f/1080f*screenHeight){
				System.out.println("save");
				try {save();} catch (FileNotFoundException e) {}
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
			float xmin = 770f/1920f*screenWidth;
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
		float xmin = 770f/1920f*screenWidth;
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
	
	private void save() throws FileNotFoundException{
		//PrintWriter bestand = new PrintWriter("C:\\Users\\Martijn\\Dropbox\\EWI3620TU Minorproject SOT Groep 01\\Level1_1_l.txt");
		String currentdir = System.getProperty("user.dir");
		PrintWriter bestand = new PrintWriter(currentdir + "\\levels\\" + "level1_1_l.txt");
		
		bestand.println("0,0,0");
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
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// Not needed.
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Not needed.

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Not needed.

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Not needed.

	}
}
