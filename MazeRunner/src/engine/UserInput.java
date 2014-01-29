package engine;

import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;

import playerStates.PlayerState;

/**
 * The UserInput class is an extension of the Control class. It also implements
 * three interfaces, each providing handler methods for the different kinds of
 * user input.
 * <p>
 * For making the assignment, only some of these handler methods are needed for
 * the desired functionality. The rest can effectively be left empty (i.e. the
 * methods under 'Unused event handlers').
 * <p>
 * Note: because of how java is designed, it is not possible for the game window
 * to react to user input if it does not have focus. The user must first click
 * the window (or alt-tab or something) before further events, such as keyboard
 * presses, will function.
 * 
 * @author Mattijs Driel
 * 
 */
public class UserInput extends Control implements Runnable {
	int x1, x2, y1, y2;

	// fields for mouselook, uit het boek Developing games in Java door David
	// Brackeen

	public Thread thread;
	private Robot robot;
	private Point mouseLocation;
	private Point centerLocation;

	private boolean relativeMouseMode; // to turn this mode (mouselook) off or
										// // on
	private boolean isRecentering;
	private boolean mouselookMode = true;
	private boolean flyMode = true;
	private Cursor cursor;
	public static boolean soundplaying = false;

	// private boolean relativeMouseMode; // to turn this mode (mouselook) off
	// or
	// on

	// private Cursor cursor;

	private boolean mousechange = false;
	private boolean startedinput = false;
	public static Sound footstep;
	// private Cursor invisibleCursor;

	Cursor invisibleCursor;
	Cursor normalCursor;

	private GLCanvas canvas;

	// TODO: Add fields to help calculate mouse movement

	/**
	 * UserInput constructor.
	 * <p>
	 * To make the new UserInput instance able to receive input, listeners need
	 * to be added to a GLCanvas.
	 * 
	 * @param canvas
	 *            The GLCanvas to which to add the listeners.
	 */
	public UserInput(GLCanvas canvas) {
		//These lines are needed, because otherwise TAB would not be a detectable key somehow.
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();

		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		kfm.setDefaultFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		// canvas.addMouseListener(this);
		// canvas.addMouseMotionListener(this);
		// canvas.addKeyListener(this);
		this.canvas = canvas;
		init(canvas);

	}

	// method to put in init

	// nog implimenteren als het window wordt verschoven via mouselook == false
	// en daarna mouselook ==true weer wordt ingevoerd, moet de
	// muis eerst naar het midden van het scherm verplaatst worden voordat de
	// rest doorgaat.
	public void init(GLCanvas canvas) {
		// window= canvas;

		invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(""), new Point(0, 0), "invisible");
		normalCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

		// canvas.setCursor(invisibleCursor);
		mouseLocation = new Point();
		centerLocation = new Point();
		// relativeMouseMode = true;
		isRecentering = true;

		// the robot is an object to bring the mouse back to the center of the
		// screen
		try {
			robot = new Robot();
			if (mouselookMode) {
				recenterMouse(canvas);
				mouseLocation.x = centerLocation.x;
				mouseLocation.y = centerLocation.y;
			}
		}

		catch (Exception e) {
			System.out.println("Robot couldn't be made and may not be supported by your system");
		}

	}

	// the robot is going to recenter the mouse
	private void recenterMouse(GLCanvas canvas) {
		if (robot != null && canvas.isVisible() && mouselookMode) {
			centerLocation.x = canvas.getWidth() / 2;
			centerLocation.y = canvas.getHeight() / 2;
			// hier swing utilities.convertPointToScreen(centerLocation, canvas)
			isRecentering = true;
			robot.mouseMove(centerLocation.x, centerLocation.y);

		}

	}

	/*
	 * **********************************************
	 * * Updating * **********************************************
	 */
	public void reshape() {
		// iets doen met als de window in zijn geheel wordt verplaatst
		if (startedinput) {
			centerLocation.x = canvas.getWidth() / 2;
			centerLocation.y = canvas.getHeight() / 2;
		}
	}

	@Override
	public void update(GLAutoDrawable drawable) {
		if (!mouselookMode) {

			dX = x1 - x2;
			dY = y1 - y2;
			x1 = x2;
			y1 = y2;

		}

		if (mousechange) {
			cursorChange(mouselookMode);
		}
	}

	public void cursorChange(boolean mouselookMode) {
		if (this.mouselookMode) {
			canvas.setCursor(invisibleCursor);
			mousechange = false;
		} else {
			canvas.setCursor(normalCursor);
			mousechange = false;
		}
	}

	/*
	 * **********************************************
	 * * Input event handlers * **********************************************
	 */

	public void mousePressed(MouseEvent event) {
		itemUse = true;

		if (!mouselookMode) {
			x1 = event.getX();
			y1 = event.getY();
			x2 = x1;
			y2 = y1;
		}

	}

	public void mouseDragged(MouseEvent event) {
		if (!mouselookMode) {
			x2 = event.getX();

			y2 = event.getY();
		} else {
			mouseMoved(event);
		}
	}

	public void mouseMoved(MouseEvent event) {
		// System.out.println("Mousemoved");
		/*
		 * if (isRecentering && centerLocation.x == event.getX() &&
		 * centerLocation.y ==event.getY()){ isRecentering = false;
		 */

		if (isRecentering) {
			isRecentering = false;
		} else {
			// maar als de muis terugbeweegt moet dit niet in dX dY gedirigeerd
			// worden
			dX = -(event.getX() - mouseLocation.x);
			dY = -(event.getY() - mouseLocation.y);

			// recenter the mouse
			recenterMouse(this.canvas);
			// isRecentering = false;
		}

		mouseLocation.x = event.getX();
		mouseLocation.y = event.getY();
	}

	public void keyPressed(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_W) {
			forward = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_A) {
			left = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_S) {
			back = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_D) {
			right = true;
		}
		if (event.getKeyCode() == KeyEvent.VK_Z) {
			if (MazeRunner.player.playerStateInt == 5) {
				up = true;
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_X) {
			if (MazeRunner.player.playerStateInt == 5) {
				down = true;
			}
		}

		if (event.getKeyCode() == KeyEvent.VK_P) {
			if (mouselookMode) {
				mouselookMode = false;
				mousechange = true;
			} else {
				mouselookMode = true;
				mousechange = true;
			}
//			MazeRunner.player.playerStateInt = 5;
		}

		if (event.getKeyCode() == KeyEvent.VK_L) {
			// hierin switch voor full screen
		}
		if (event.getKeyCode() == KeyEvent.VK_W) {
			if (MazeRunner.player.playerStateInt != 4){
			if (MazeRunner.player.playerStateInt != 5) {
				if (!soundplaying){
					try {
			            footstep = new Sound("/footstep.wav");
			            footstep.clip.play();
			        }
			        catch(Exception e) {}
					soundplaying = true;
				}
			} else {
				
			}
			}
		}
		else if(event.getKeyCode() == KeyEvent.VK_A) {
			if (MazeRunner.player.playerStateInt != 4){
			if (MazeRunner.player.playerStateInt != 5) {
				if (!soundplaying){
					try {
			            footstep = new Sound("/footstep.wav");
			            footstep.clip.play();
			        }
			        catch(Exception e) {}
					soundplaying = true;
				}
			} else {
				
			}
			}
		}
		else if(event.getKeyCode() == KeyEvent.VK_S) {
			if (MazeRunner.player.playerStateInt != 4){
			if (MazeRunner.player.playerStateInt != 5) {
				if (!soundplaying){
					try {
			            footstep = new Sound("/footstep.wav");
			            footstep.clip.play();
			        }
			        catch(Exception e) {}
					soundplaying = true;
				}
			} else {
				
			}
			}
		}
		else if(event.getKeyCode() == KeyEvent.VK_D) {
			if (MazeRunner.player.playerStateInt != 4){
			if (MazeRunner.player.playerStateInt != 5) {
				if (!soundplaying){
					try {
			            footstep = new Sound("/footstep.wav");
			            footstep.clip.play();
			        }
			        catch(Exception e) {}
					soundplaying = true;
				}
			} else {
				
			}
			}
		}
	}

	public void keyReleased(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_W) {
			forward = false;
		}
		if (event.getKeyCode() == KeyEvent.VK_A) {
			left = false;

		}
		if (event.getKeyCode() == KeyEvent.VK_S) {
			back = false;
			
		}
		if (event.getKeyCode() == KeyEvent.VK_D) {
			right = false;
			
		}
		if (event.getKeyCode() == KeyEvent.VK_Z) {
			if (MazeRunner.player.playerStateInt != 5) {
				playerStateUp = true;
			} else {
				up = false;
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_X) {
			if (MazeRunner.player.playerStateInt != 5) {
				playerStateDown = true;
			} else {
				down = false;
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_TAB) {
			tab = true;
		}
		if (!back && !right && !forward && !left) {
			//if (MazeRunner.player.playerStateInt !=5){
			soundplaying = false;
			try{footstep.clip.stop();} catch(Exception e){}
			//} else {};
		}
		if (MazeRunner.level.getCurrentMaze(MazeRunner.player) == -1){
			if (MazeRunner.player.playerStateInt !=5 && MazeRunner.player.playerStateInt !=4){
			soundplaying = false;
			try{footstep.clip.stop();} catch(Exception e){}
			} else {};
		}
	}

	public void setmouselookMode(boolean mouselookMode) {
		if (this.mouselookMode != mouselookMode) {

			this.mouselookMode = mouselookMode;
			mousechange = true;
		}

	}

	/*
	 * **********************************************
	 * * Unused event handlers * **********************************************
	 */

	public void noMousechange() {
		if (mouselookMode) {
			recenterMouse(this.canvas);
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
