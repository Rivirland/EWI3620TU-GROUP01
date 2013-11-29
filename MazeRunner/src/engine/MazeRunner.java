package engine;

import items.Item;
import items.TrapDropped;
import items.TrapHolder;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import model.Model;
import model.OBJLoader;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import enemies.Enemy;
import enemies.EnemyControl;
import enemies.EnemySmart;
import enemies.EnemySpooky;

/**
 * MazeRunner is the base class of the game, functioning as the view controller
 * and game logic manager.
 * <p>
 * Functioning as the window containing everything, it initializes both JOGL,
 * the game objects and the game logic needed for MazeRunner.
 * <p>
 * For more information on JOGL, visit <a
 * href="http://jogamp.org/wiki/index.php/Main_Page">this page</a> for general
 * information, and <a
 * href="https://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/">this
 * page</a> for the specification of the API.
 * 
 * @author Bruno Scheele, revised by Mattijs Driel
 * 
 */
public class MazeRunner {

	/*
	 * **********************************************
	 * * Local variables * **********************************************
	 */

<<<<<<< HEAD

	private int screenWidth, screenHeight; // Screen size.
	private ArrayList<VisibleObject> visibleObjects; // A list of objects that
														// will be displayed on
														// screen.
=======
	private int screenWidth = 900, screenHeight = 900; // Screen size.
	public static ArrayList<VisibleObject> visibleObjects; // A list of objects
															// that
															// will be displayed
															// on
															// screen.
>>>>>>> a58643642e76276e2ab55d8012f48eef431e3495
	public static Player player; // The player object.
	public static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	// private ArrayList<Item> itemList = new ArrayList<Item>();
	// private int itemListLength;
	private Camera camera; // The camera object.
	//private UserInput input; // The user input object that controls the player.
	private EnemyControl enemyControl;
<<<<<<< HEAD
	private Level level;
	//private long previousTime = Calendar.getInstance().getTimeInMillis();
	//final private long startTime = Calendar.getInstance().getTimeInMillis();// Used
	
	private long previousTime = Calendar.getInstance().getTimeInMillis();
	private long startTime = Calendar.getInstance().getTimeInMillis();
	
	// to
=======
	public static Level level;
	private long previousTime = Calendar.getInstance().getTimeInMillis(); // Used
																			// to
>>>>>>> a58643642e76276e2ab55d8012f48eef431e3495
																			// calculate
																			// elapsed
																			// time.

	
	public static Model spookyModel, smartModel;

	public static Texture earthTexture, wallTexture, roofTexture, trapHolderTexture, oildrumTexture;

	public int mazeX, mazeY, mazeZ;
	private Portal portal1, portal2;
	
	private UserInput input;

	/*
	 * **********************************************
	 * * Initialization methods * **********************************************
	 */
	/**
	 * Initializes the complete MazeRunner game.
	 * <p>
	 * MazeRunner extends Java AWT Frame, to function as the window. It creates a
	 * canvas on itself where JOGL will be able to paint the OpenGL graphics. It
	 * then initializes all game components and initializes JOGL, giving it the
	 * proper settings to accurately display MazeRunner. Finally, it adds itself
	 * as the OpenGL event listener, to be able to function as the view
	 * controller.
	 */

	public MazeRunner(int screenWidth, int screenHeight, GLCanvas canvas, GLAutoDrawable drawable, GL gl, GLU glu,  UserInput userinput) {
		// Make a new window.

		// Let's change the window to our liking.
		setScreen(screenWidth, screenHeight);

		// The window also has to close when we want to.
		//canvas = new GLCanvas();
		//initJOGL();
		init(drawable , gl, glu);
		/**
		 * @@
		 */
		initObjects(canvas, userinput);
		
		//this.input = userinput;

		// Set the frame to visible. This automatically calls upon OpenGL to
		// prevent a blank screen.
	}

	public void setScreen(int screenWidth, int screenHeight) {
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
		

		
		
	}
	
/*	private void initJOGL() {
		// First, we set up JOGL. We start with the default settings.
		GLCapabilities caps = new GLCapabilities();
		// Then we make sure that JOGL is hardware accelerated and uses double
		// buffering.
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		// Now we add the canvas, where OpenGL will actually draw for us. We'll
		// use settings we've just defined.
		canvas = new GLCanvas(caps);
		//add(canvas);
		/*
		 * We need to add a GLEventListener to interpret OpenGL events for us.
		 * Since MazeRunner implements GLEventListener, this means that we add
		 * the necesary init(), display(), displayChanged() and reshape()
		 * methods to this class. These will be called when we are ready to
		 * perform the OpenGL phases of MazeRunner.
		 */
		//canvas.addGLEventListener(this);

		/*
		 * We need to create an internal thread that instructs OpenGL to
		 * continuously repaint itself. The Animator class handles that for
		 * JOGL.
		 */
	/*
		Animator anim = new Animator(canvas);
		anim.start();
	}
*/
	
	
public void setScreen(GLU glu, GL gl, int screenWidth, int screenHeight) {
		
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
		
	gl.glViewport(0, 0, screenWidth, screenHeight); // VOOR PORTAL!!!!!!!
	
	// Set the new projection matrix.
	gl.glMatrixMode(GL.GL_PROJECTION);
	gl.glLoadIdentity();
	glu.gluPerspective(60, screenWidth / screenHeight, .1, 200);
	gl.glMatrixMode(GL.GL_MODELVIEW);
	
	input.reshape();

	}

	/**
	 * initializeObjects() creates all the objects needed for the game to start
	 * normally.
	 * <p>
	 * This includes the following:
	 * <ul>
	 * <li>the default Maze
	 * <li>the Player
	 * <li>the Camera
	 * <li>the User input
	 * </ul>
	 * <p>
	 * Remember that every object that should be visible on the screen, should
	 * be added to the visualObjects list of MazeRunner through the add method,
	 * so it will be displayed automatically.
	 */
	public void initObjects(GLCanvas canvas, UserInput input) {
		// We define an ArrayList of VisibleObjects to store all the objects
		// that need to be
		// displayed by MazeRunner.
		visibleObjects = new ArrayList<VisibleObject>();
		// Add the maze that we will be using.

		level = new Level("level1");

		portal1 = new Portal(16, 2, 6, 2);
		portal2 = new Portal(56, 2, 16, 2);

		Portal.portalConnection(portal1, portal2);
		
		for (int i = 0; i < level.getAantal(); i++) {
			visibleObjects.add(level.getMaze(i));
		}

		// Initialize the player.
		player = new Player(5, 2.5, 5, -90, 0);

		camera = new Camera(player.getLocationX(), player.getLocationY(),
				player.getLocationZ(), player.getHorAngle(),
				player.getVerAngle());

		// Initialize the enemies.

		enemyList.add(new EnemySmart(20, 2.5, 20, 0.005, -90));
		enemyList.add(new EnemySpooky(70, 2.5, 30, 0.0015, -90));

		for (int i = 0; i < enemyList.size(); i++) {
			enemyList.get(i).setControl(enemyControl);
			visibleObjects.add(enemyList.get(i));
		}

		//input = new UserInput(canvas);
		
		this.input=input;
		player.setControl(input);
<<<<<<< HEAD
		
		
=======
		for (int mazeID = 0; mazeID < 2; mazeID++) {
			System.out.println("MazeID: "
					+ level.getMaze(mazeID).itemList.get(0).mazeID
					+ ", item 0 op: "
					+ level.getMaze(mazeID).itemList.get(0).getGlobalX() + " "
					+ level.getMaze(mazeID).itemList.get(0).getGlobalY() + " "
					+ level.getMaze(mazeID).itemList.get(0).getGlobalZ());
//			System.out.println("MazeID: "
//					+ level.getMaze(mazeID).itemList.get(1).mazeID
//					+ ", item 1 op: "
//					+ level.getMaze(mazeID).itemList.get(1).getGlobalX() + " "
//					+ level.getMaze(mazeID).itemList.get(1).getGlobalY() + " "
//					+ level.getMaze(mazeID).itemList.get(1).getGlobalZ());
		}
>>>>>>> a58643642e76276e2ab55d8012f48eef431e3495
	}

	/*
	 * **********************************************
	 * * OpenGL event handlers * **********************************************
	 */

	/**
	 * init(GLAutodrawable) is called to initialize the OpenGL context, giving
	 * it the proper parameters for viewing.
	 * <p>
	 * Implemented through GLEventListener. It sets up most of the OpenGL
	 * settings for the viewing, as well as the general lighting.
	 * <p>
	 * It is <b>very important</b> to realize that there should be no drawing at
	 * all in this method.
	 */
	public void init(GLAutoDrawable drawable, GL gl, GLU glu) {
		
		
		//GLU glu = new GLU();
		drawable.setGL(new DebugGL(drawable.getGL())); // We set the OpenGL
														// pipeline to Debugging
														// mode.
		
		/*
		gl.glClearColor(0, 0, 0, 0); // Set the background color.

		// Now we set up our viewpoint.
		gl.glMatrixMode(GL.GL_PROJECTION); // We'll use orthogonal projection.
		gl.glLoadIdentity(); // Reset the current matrix.
		*/
		//@gamestate switch
		glu.gluPerspective(60, screenWidth, screenHeight, 200); // Set up the
																// parameters
																// for
																// perspective
																// viewing.
		gl.glMatrixMode(GL.GL_MODELVIEW);

		// TODO: back-face weer aanzetten
		// Enable back-face culling.
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_FRONT_AND_BACK);
		gl.glDisable(GL.GL_CULL_FACE);

		// @Enable Z-buffering, gamestate switch
		gl.glEnable(GL.GL_DEPTH_TEST);

		
		//@ gamestate switch dit ook wanneer de mazerunner gebruikt wordt
		// Set and enable the lighting.
		float lightPosition[] = { 0.0f, 20.0f, 0.0f, 1.0f }; // High up in the
																// sky!
		float lightColour[] = { 1.0f, 1.0f, 1.0f, 0.0f }; // White light!
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPosition, 0); // Note
																		// that
																		// we're
																		// setting
																		// Light0.
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, lightColour, 0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);

		// Set the shading model.
		gl.glShadeModel(GL.GL_SMOOTH);
		loadTextures(gl);
		loadModels(gl);

		//@@
	}

	// Loads all the texture and stores them into the memory. We have to keep
	// track of the order ourselves.
	// Right now, it is stored like this:
	// 1: earthTexture
	// 2: wallTexture
	// 3: roofTexture
<<<<<<< HEAD
	
=======
	// 4: trapHolderTexture
>>>>>>> a58643642e76276e2ab55d8012f48eef431e3495
	public void loadTextures(GL gl) {
		gl.glEnable(GL.GL_TEXTURE_2D);
		try {
			String currentdir = System.getProperty("user.dir");
			String filename = "\\textures\\texture.jpg";

			filename = currentdir + filename;
			File file2 = new File(filename);
			System.out.println(filename);
			TextureData data2 = TextureIO.newTextureData(file2, false, "jpg");
			earthTexture = TextureIO.newTexture(data2);
		} catch (IOException exc) {
			System.out.println("niet gevonden - texture");
			exc.printStackTrace();
			System.exit(1);
		}

		try {
			String currentdir = System.getProperty("user.dir");
			String filename = "\\textures\\walltexture2.jpg";

			filename = currentdir + filename;
			File file1 = new File(filename);
			System.out.println(filename);
			// InputStream stream =
			// getClass().getResourceAsStream("texture.jpg");
			TextureData data1 = TextureIO.newTextureData(file1, false, "jpg");
			wallTexture = TextureIO.newTexture(data1);
		} catch (IOException exc) {
			System.out.println("niet gevonden - walltexture2");
			exc.printStackTrace();
			System.exit(1);
		}

		try {
			String currentdir = System.getProperty("user.dir");
			String filename = "\\textures\\rooftexture.jpg";

			filename = currentdir + filename;
			File file3 = new File(filename);
			System.out.println(filename);
			// InputStream stream =
			// getClass().getResourceAsStream("texture.jpg");
			TextureData data3 = TextureIO.newTextureData(file3, false, "jpg");
			roofTexture = TextureIO.newTexture(data3);
		} catch (IOException exc) {
			System.out.println("niet gevonden - roofTexture");
			exc.printStackTrace();
			System.exit(1);
		}
		try {
			String currentdir = System.getProperty("user.dir");
			String filename = "\\textures\\trapHolderTexture.jpg";

			filename = currentdir + filename;
			File file4 = new File(filename);
			System.out.println(filename);
			// InputStream stream =
			// getClass().getResourceAsStream("texture.jpg");
			TextureData data4 = TextureIO.newTextureData(file4, false, "jpg");
			trapHolderTexture = TextureIO.newTexture(data4);
		} catch (IOException exc) {
			System.out.println("niet gevonden - trapHolderTexture");
			exc.printStackTrace();
			System.exit(1);
		}
		try {
			String currentdir = System.getProperty("user.dir");
			String filename = "\\textures\\oildrum_col.jpg";

			filename = currentdir + filename;
			File file5 = new File(filename);
			System.out.println(filename);
			// InputStream stream =
			// getClass().getResourceAsStream("texture.jpg");
			TextureData data5 = TextureIO.newTextureData(file5, false, "jpg");
			oildrumTexture = TextureIO.newTexture(data5);
		} catch (IOException exc) {
			System.out.println("niet gevonden - oildrumTexture");
			exc.printStackTrace();
			System.exit(1);
		}

	}
	
<<<<<<< HEAD
	public void setTime (long time){
		this.previousTime = time;
	}

=======
	public void loadModels(GL gl){
		gl.glEnable(GL.GL_TEXTURE_2D);
		try {
            String currentdir = System.getProperty("user.dir");
			String filename = currentdir + "\\models\\oildrum.obj";
            spookyModel = OBJLoader.loadTexturedModel(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
>>>>>>> a58643642e76276e2ab55d8012f48eef431e3495

	/**
	 * display(GLAutoDrawable) is called upon whenever OpenGL is ready to draw a
	 * new frame and handles all of the drawing.
	 * <p>
	 * Implemented through GLEventListener. In order to draw everything needed,
	 * it iterates through MazeRunners' list of visibleObjects. For each
	 * visibleObject, this method calls the object's display(GL) function, which
	 * specifies how that object should be drawn. The object is passed a
	 * reference of the GL context, so it knows where to draw.
	 */
<<<<<<< HEAD
	public void display(GLAutoDrawable drawable, GL gl) {
		//System.out.println(input.getForward());
	//System.out.println(player.getLocationX() + " " + player.getLocationZ());
		//GL gl = drawable.getGL();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
=======
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
>>>>>>> a58643642e76276e2ab55d8012f48eef431e3495
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		// Calculating time since last frame.
		Calendar now = Calendar.getInstance();
		long currentTime = now.getTimeInMillis()-startTime;
		
		int deltaTime = (int) (currentTime - previousTime);
		previousTime = currentTime;
		//time = previousTime-startTime;
		
		//this.time;
		
		//System.out.println(previousTime);s


		// Update any movement since last frame.
		updateMovement(deltaTime, drawable);
		updateCamera();
		

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		glu.gluLookAt(camera.getLocationX(), camera.getLocationY(),
				camera.getLocationZ(), camera.getVrpX(), camera.getVrpY(),
				camera.getVrpZ(), camera.getVuvX(), camera.getVuvY(),
				camera.getVuvZ());

		// Display all the visible objects of MazeRunner.
		for (Iterator<VisibleObject> it = visibleObjects.iterator(); it
				.hasNext();) {
			it.next().display(gl);
		}
		
		
		
		portal1.displayPortal(glut, gl);
		portal2.displayPortal(glut, gl);
		portal1.calcPortaltoPlayer(player);
		portal2.calcPortaltoPlayer(player);
		gl.glLoadIdentity();
		// Flush the OpenGL buffer.
		
		gl.glFlush();
	}

	/**
	 * displayChanged(GLAutoDrawable, boolean, boolean) is called upon whenever
	 * the display mode changes.
	 * <p>
	 * Implemented through GLEventListener. Seeing as this does not happen very
	 * often, we leave this unimplemented.
	 */
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		// GL gl = drawable.getGL();
	}

	/**
	 * reshape(GLAutoDrawable, int, int, int, int, int) is called upon whenever
	 * the viewport changes shape, to update the viewport setting accordingly.
	 * <p>
	 * Implemented through GLEventListener. This mainly happens when the window
	 * changes size, thus changing the canvas (and the viewport that OpenGL
	 * associates with it). It adjust the projection matrix to accomodate the
	 * new shape.
	 */
	/*public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();

		// Setting the new screen size and adjusting the viewport.
		screenWidth = width;
		screenHeight = height;
		gl.glViewport(0, 0, screenWidth, screenHeight); // VOOR PORTAL!!!!!!!
		
		

		// Set the new projection matrix.
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60, screenWidth / screenHeight, .1, 200);
		gl.glMatrixMode(GL.GL_MODELVIEW);
	}*/

	/*
	 * **********************************************
	 * * Methods * **********************************************
	 */

	/**
	 * updateMovement(int) updates the position of all objects that need moving.
	 * This includes rudimentary collision checking and collision reaction.
	 */


	// Updates the player and the enemy movement
	private void updateMovement(int deltaTime, GLAutoDrawable drawable) {
		double previousX = player.getLocationX();
		double previousY = player.getLocationY();
		double previousZ = player.getLocationZ();
		
		player.update(deltaTime, drawable);

		int currentMazeID = level.getCurrentMaze(player);
		if (currentMazeID != -1) {
			Maze currentMaze = level.getMaze(currentMazeID);

			for (int i = 0; i < currentMaze.itemList.size(); i++) {
				Item item = currentMaze.itemList.get(i);
				if (item.touches(player)) {
					if (item instanceof TrapHolder) {
						Player.nrOfTraps++;
						visibleObjects.remove(currentMaze.itemList.get(i));
						currentMaze.itemList.remove(i);
					}
				}
			}
		}
		for (int e = 0; e < enemyList.size(); e++) {
			Enemy enemy = enemyList.get(e);
			currentMazeID = level.getCurrentMaze(enemy);
			if (currentMazeID != -1) {
				Maze currentMaze = level.getMaze(currentMazeID);
				// double enemyX = enemy.getX();
				// double enemyZ = enemy.getZ();
				enemy.update(deltaTime, player);
				for (int i = 0; i < currentMaze.itemList.size(); i++) {
					Item item = currentMaze.itemList.get(i);
					if (item.touches(enemy) && item instanceof TrapDropped) {
						System.out.println("trap werkt");
						visibleObjects.remove(enemy);
						enemyList.remove(enemy);
						visibleObjects.remove(item);
						currentMaze.itemList.remove(item);
					}
				}
			}

		}

		if (Player.canTeleport) {
			portal1.checkteleportation(player, (float) previousX,
					(float) previousY, (float) previousZ);
			portal2.checkteleportation(player, (float) previousX,
					(float) previousY, (float) previousZ);
		}else{
			Player.canTeleport = true;
		}
		// if (maze.isExit(player.locationX, player.locationZ)) {
		// Sound.applause.play();
		// player.locationX = maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2;
		// player.locationY = maze.SQUARE_SIZE / 2;
		// player.locationZ = maze.SQUARE_SIZE + maze.SQUARE_SIZE / 2;
		// try {
		// maze.loadMaze("level1");
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	/**
	 * updateCamera() updates the camera position and orientation.
	 * <p>
	 * This is done by copying the locations from the Player, since MazeRunner
	 * runs on a first person view.
	 */

	private void updateCamera() {
		camera.setLocationX(player.getLocationX());
		camera.setLocationY(player.getLocationY());
		camera.setLocationZ(player.getLocationZ());
		camera.setHorAngle(player.getHorAngle());
		camera.setVerAngle(player.getVerAngle());
		camera.calculateVRP();
	}

	public static Player getPlayer() {
		return player;
	}

	/*
	 * **********************************************
	 * * Main * **********************************************
	 */
	/**
	 * Program entry point
	 * 
	 * @param args
	 */

}