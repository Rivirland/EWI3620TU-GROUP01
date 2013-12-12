package menu;



import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.Calendar;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import levelEditor.LevelEditor;
import levelEditor.LevelEditorWorld;

import com.sun.opengl.util.Animator;

import engine.Database;
import engine.Level;
import engine.MazeRunner;
import engine.UserInput;



public class Main extends Frame implements GLEventListener, MouseListener, KeyListener, MouseMotionListener {
	static final long serialVersionUID = 7526471155622776147L;

	// Screen size.
	private int screenWidth = 800, screenHeight = 600;
	private float buttonSize = screenHeight / 10.0f;
	
	// all the different states and their corresponding number
	final byte MAINMENU = 0;
	final byte GAMEMENU = 1;
	final byte LEVELMENU = 2;
	final byte SETTINGS = 3;
	final byte QUIT = 4;
	final byte INGAME = 5;
	final byte LEVELEDITOR = 6;
	final byte PAUZE = 7;
	final byte LOADGAME = 8;
	final byte DELETEGAME = 9;
	final byte LOADLEVEL = 10;
	
	final boolean fullscreenboolean= false;
	
	private boolean activate=false;
	
	private Cursor normalCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	
	private long previousTime = Calendar.getInstance().getTimeInMillis();
	// begintijd vastgesteld
	final private long startTime = Calendar.getInstance().getTimeInMillis();
	private long time;
	// is the delta of the time that pause is initaited to the time the game is resumed
	private long ingamepausetime;
	// time the pause is resumed
	private long pausedtime;
	
	//private boolean mouselookMode;
	
	// the gamestate
	private int gamestate;
	// to check if a gamestate was changed
	private int currentstate = gamestate;
	// if the ingame is started
	private boolean ingamestarted = false;
	
	// constructors for the different gamestates
	LevelEditor leveleditor; 
	MainMenu mainmenu;
	GameMenu gamemenu;
	LevelMenu levelmenu;
	Quit quit;
	Settings settings;
	UserInput userinput;
	MazeRunner mazerunner;
	public static Database db;
	
	// A GLCanvas is a component that can be added to a frame. The drawing
	// happens on this component.
	private GLCanvas canvas;
	
	Fullscreen fullscreen = new Fullscreen();
	
	GL gl;
	GLU glu;
	GLAutoDrawable drawable;
	

	public long getTime (){
		return time;
	}
	
	public long getNewTime () {
		return ingamepausetime;
	}
	
	/**
	 * The constructor of the window, in which all States can take place
	 */
	public Main() {
		super("naam van onze g@me");

		gamestate = MAINMENU;
	

		// Set the desired size and background color of the frame
		if (fullscreenboolean){
			  screenWidth = fullscreen.getWidth();
		      screenHeight = fullscreen.getHeight();
			}
		      setSize(screenWidth, screenHeight);
		    if (fullscreenboolean){
		      fullscreen.init(this);
		      setFocusable(true);
		    }
		    
		   
		setBackground(new Color(0f, 0f, 0f));
		

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
		canvas.addMouseMotionListener(this);
		canvas.addKeyListener(this);

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
	 * becomes visible. In this assignment, there is no moving ´camera´, so the view and projection can 
	 * be set at initialization. 
	 */
	public void init(GLAutoDrawable drawable) {
		// Retrieve the OpenGL handle, this allows us to use OpenGL calls.
		//GL gl = drawable.getGL();
		gl = drawable.getGL();
		glu = new GLU();
		
	
		

		// Set the matrix mode to GL_PROJECTION, allowing us to manipulate the
		// projection matrix
		gl.glMatrixMode(GL.GL_PROJECTION);

		// Always reset the matrix before performing transformations, otherwise
		// those transformations will stack with previous transformations!
		gl.glLoadIdentity();

		mainmenu = new MainMenu(screenWidth, screenHeight);
		try{leveleditor=new LevelEditor(gl, screenWidth, screenHeight, LevelEditorWorld.readWorld(System.getProperty("user.dir") + "\\worlds\\world.txt"));}
		catch (FileNotFoundException e){System.out.println("file niet gevonden: " + System.getProperty("user.dir") + "\\worlds\\world.txt");}
		gamemenu = new GameMenu(screenWidth, screenHeight);
		quit = new Quit(screenWidth, screenHeight);
		settings = new Settings(screenWidth, screenHeight);
		levelmenu = new LevelMenu(screenWidth, screenHeight);
		//mazerunner = new MazeRunner(screenWidth, screenHeight, canvas, drawable, gl, glu, userinput);
		userinput = new UserInput(canvas);
		mazerunner = new MazeRunner(screenWidth, screenHeight, canvas, drawable, gl, glu, userinput, new Level("world"));
		db = new Database();
		/*
		 * glOrtho performs an "orthogonal projection" transformation on the
		 * active matrix. In this case, a simple 2D projection is performed,
		 * matching the viewing frustum to the screen size.
		 */
		gl.glOrtho(0, screenWidth, 0, screenHeight, -10000, 10000);

		// Set the matrix mode to GL_MODELVIEW, allowing us to manipulate the
		// model-view matrix.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// We leave the model view matrix as the identity matrix. As a result,
		// we view the world 'looking forward' from the origin.
		gl.glLoadIdentity();

		// We have a simple 2D application, so we do not need to check for depth
		// when rendering.
		//gl.glDisable(GL.GL_DEPTH_TEST);
		
		
		
	}
	/*public void loadTextures(GL gl, String filename){
		//gl.glEnable(GL.GL_TEXTURE_1D);
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
	}*/

	@Override
	/**
	 * A function defined in GLEventListener. This function is called many times per second and should 
	 * contain the rendering code.
	 */
	public void display(GLAutoDrawable drawable) {
		
		if (gamestate != INGAME){
			
			userinput.setmouselookMode(false);
			canvas.setCursor(normalCursor);
		}
		
		
		
		// over de tijd
		
		this.time = previousTime - startTime;
		
		
		// check of andere gamestate dan INGAME vanuit pauze wordt aangeroepen
		/*
		if (currentstate == INGAME && gamestate != INGAME && ingamestarted){
			currentstate = gamestate;
			pausedtime=time;
		}
		
		if (currentstate != INGAME && gamestate == INGAME && ingamestarted){
			// calculate the time elapsed while the game was paused
			ingamepausetime= time - ingamepausetime;
		}
		*/
		
		
		GL gl = drawable.getGL();

				
		// Set the clear color and clear the screen.
		gl.glClearColor(0f, 0f, 0f, 1f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		
	
	switch(gamestate){
	case MAINMENU:
		if (currentstate != gamestate){
			mainmenu.setScreen(screenWidth, screenHeight);
		}
		mainmenu.display(drawable, gl);
		currentstate = gamestate;
		
		break;
	case GAMEMENU:
		if (currentstate != gamestate){
			gamemenu.setScreen(screenWidth, screenHeight);
		}
		gamemenu.display(drawable, gl);
		currentstate = gamestate;
		break;
	case LEVELMENU:
		if (currentstate != gamestate){
			gamemenu.setScreen(screenWidth, screenHeight);
		}
		levelmenu.display(drawable, gl);
		currentstate = gamestate;
		
		break;
	case SETTINGS:
		if (currentstate != gamestate){
			settings.setScreen(screenWidth, screenHeight);
		}
		settings.display(drawable, gl);
		currentstate = gamestate;
		
		break;
	case QUIT:
		if (currentstate != gamestate){
			quit.setScreen(screenWidth, screenHeight);
		}
		quit.display(drawable, gl);
		currentstate = gamestate;
		
		break;
	case INGAME:
		if (!ingamestarted){
			ingamestarted =true;
			mazerunner.init(drawable, gl, glu);
			
			// hier alle inits voor de mazerunner
			//mazerunner.initObjects(canvas, userinput);
			
		}
		if (currentstate != gamestate){
			userinput.setmouselookMode(true);
			mazerunner.setScreen(glu, gl, screenWidth, screenHeight);
		}
		gl = drawable.getGL();
		mazerunner.display(drawable, gl);
		currentstate = gamestate;
		break;
	
	case LOADGAME:			
		KiesFileUitBrowser kfub = new KiesFileUitBrowser();
		String currentdir = System.getProperty("user.dir");
		String filename = kfub.loadFile(new Frame(), "Open...", currentdir + "\\worlds\\", "*.txt");
		//filename = currentdir + "\\levels\\" + filename;
		System.out.println(filename.substring(0, filename.length()-6));
		mazerunner = new MazeRunner(screenWidth, screenHeight, canvas, drawable, gl, glu, userinput, new Level(filename.substring(0, filename.length()-4)));
		gamestate = INGAME;
		break;
		
	case LEVELEDITOR:
		//leveleditor = new LevelEditor(screenWidth, screenHeight);
		if (currentstate != gamestate){
			
			//try{leveleditor=new LevelEditor(screenWidth, screenHeight, LevelEditorWorld.readWorld(System.getProperty("user.dir") + "\\worlds\\world.txt"));}
			//catch (FileNotFoundException e){System.out.println("file niet gevonden");}
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrtho(0, screenWidth, 0, screenHeight, -10000, 10000);
			//leveleditor.setScreen(screenWidth, screenHeight);
		}
		//gl.glLoadIdentity();
		//gl.glOrtho(0, screenWidth, 0, screenHeight, -1, 1);
		gl = drawable.getGL();
		//gl.glLoadIdentity();
		
		leveleditor.display(drawable, gl);
		currentstate = gamestate;
		
		break;
	case PAUZE:
		
		break;
		
	case LOADLEVEL:
		//leveleditor = new LevelEditor(screenWidth, screenHeight);
		if (currentstate != gamestate){
			KiesFileUitBrowser kfub2 = new KiesFileUitBrowser();
			String currentdir2 = System.getProperty("user.dir");
			String filename2 = kfub2.loadFile(new Frame(), "Open world...", currentdir2 + "\\worlds\\", "*.txt");
			filename = currentdir2 + "\\worlds\\" + filename2;
			System.out.println(filename);
			try {leveleditor=new LevelEditor(gl, screenWidth, screenHeight, LevelEditorWorld.readWorld(filename));}
			catch (FileNotFoundException e){System.out.println("file niet gevonden");}	
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrtho(0, screenWidth, 0, screenHeight, -10000, 10000);
		}
		leveleditor.display(drawable, gl);
		currentstate = gamestate;
		break;
	}
	
		
	
		gl.glFlush();
	}


	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// Not needed.
	}

	@Override
	/**
	 * A function defined in GLEventListener. This function is called when the GLCanvas is resized or moved. 
	 * Since the canvas fills the frame, this event also triggers whenever the frame is resized or moved.
	 */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		//GL gl = drawable.getGL();
		
		// Set the new screen size and adjusting the viewport
		screenWidth = width;
		screenHeight = height;
		buttonSize = height / 10.0f;
		gl.glViewport(0, 0, screenWidth, screenHeight);
		userinput.reshape();
		
		mainmenu.setScreen(screenWidth, screenHeight);
		gamemenu.setScreen(screenWidth,screenHeight);
		levelmenu.setScreen(screenWidth,screenHeight);
		settings.setScreen(screenWidth,screenHeight);
		quit.setScreen(screenWidth,screenHeight);
		mazerunner.setScreen(screenWidth,screenHeight);
		//leveleditor.setScreen(screenWidth, screenHeight);
		leveleditor.setScreen(screenWidth,screenHeight);
		
		switch(gamestate){
		case MAINMENU: 
			mainmenu.setScreen(screenWidth, screenHeight);
			
			break;
		case GAMEMENU:
			gamemenu.setScreen(screenWidth,screenHeight);
			break;
		case LEVELMENU:
			levelmenu.setScreen(screenWidth,screenHeight);
			break;
		case SETTINGS:
			settings.setScreen(screenWidth,screenHeight);
			break;
		case QUIT:
			quit.setScreen(screenWidth,screenHeight);
			break;
		case INGAME:
			//ingame.setScreen(screenWidth,screenHeight);
			break;
		case LEVELEDITOR:
			//leveleditor.setScreen(screenWidth, screenHeight);
			
			// Update the projection to an orthogonal projection using the new screen size
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrtho(0, screenWidth, 0, screenHeight, -10000, 10000);

			break;
		case PAUZE:
			
			break;
			
		case LOADLEVEL:
			//leveleditor.setScreen(screenWidth,screenHeight);
			
			// Update the projection to an orthogonal projection using the new screen size
						gl.glMatrixMode(GL.GL_PROJECTION);
						gl.glLoadIdentity();
						gl.glOrtho(0, screenWidth, 0, screenHeight, -10000, 10000);
						
			break;
		}

	}

	
	/**
	 * A function defined in MouseListener. Is called when the pointer is in the GLCanvas, and a mouse button is released.
	 */
	@Override
public void mouseReleased(MouseEvent me) {
		
		//GL gl = drawable.getGL();
			
		switch(gamestate){
		case MAINMENU: 
			mainmenu.setScreen(screenWidth, screenHeight);
			gamestate = mainmenu.mouseReleased(me);
			
			
			break;
		case GAMEMENU:
			gamemenu.setScreen(screenWidth, screenHeight);
			gamestate = gamemenu.MouseReleased(me);
			break;
		case LEVELMENU:
			levelmenu.setScreen(screenWidth, screenHeight);
			gamestate = levelmenu.MouseReleased(me);
			
			break;
		case SETTINGS:
			
			break;
		case QUIT:
			quit.setScreen(screenWidth, screenHeight);
			gamestate = quit.mouseReleased(me);
			
			break;
		case INGAME:
			
			
			break;
		case LEVELEDITOR:
			leveleditor.setScreen(screenWidth, screenHeight);
			leveleditor.mouseReleased(me);
			
			break;
		case PAUZE:
			
			break;
		case LOADLEVEL:
			leveleditor.setScreen(screenWidth, screenHeight);
			leveleditor.mouseReleased(me);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		switch(gamestate){
		case MAINMENU: 
			
			break;
		case GAMEMENU:
			
			break;
		case LEVELMENU:
			
			break;
		case SETTINGS:
			
			break;
		case QUIT:
			
			break;
		case INGAME:
			
			break;
		case LEVELEDITOR:

			break;
		case PAUZE:
			
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(gamestate){
		case MAINMENU: 
			
			break;
		case GAMEMENU:
			
			break;
		case LEVELMENU:
			
			break;
		case SETTINGS:
			
			break;
		case QUIT:
			
			break;
		case INGAME:
			
			userinput.mousePressed(e);
			
			break;
			
		case LOADGAME:
			
			userinput.mousePressed(e);
			
			break;
		case LEVELEDITOR:
			try {leveleditor.mousePressed(e);} catch (FileNotFoundException e1) {e1.printStackTrace();}
			break;
		case PAUZE:
			
			break;
		case LOADLEVEL:
			try {leveleditor.mousePressed(e);} catch (FileNotFoundException e1) {e1.printStackTrace();}
			break;
		}
		
	}
	
	public static void main (String[] args){
		new Main();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (gamestate == INGAME){
			userinput.mouseDragged(e);
		}
		if (gamestate == LEVELEDITOR){
			leveleditor.mouseDragged(e);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (gamestate == INGAME){
			userinput.mouseMoved(e);
	}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
		if (gamestate == INGAME){
			userinput.keyPressed(e);
			
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gamestate = MAINMENU;
		}
		
		if (gamestate == INGAME){
			userinput.keyReleased(e);
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}