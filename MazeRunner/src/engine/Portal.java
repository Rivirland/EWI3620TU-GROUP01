package engine;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import menu.Main;
import menu.Teken;

import com.sun.opengl.util.GLUT;

public class Portal {

	// coordinaten van de portal
	private double x;
	private double y;
	private double z;
	
	
	private double border = 0.2;

	//two dimensional array that stores which portals are connected with which
	public static int mazeID;

	private int connectedmazeID;
	public static ArrayList<Portal> portalList;
	public static ArrayList<Maze> mazeList;
	private static boolean run = false;
	private static int pcounter = -1;

	private Camera portalcamera;

	private static int amountmazep = 2;
	private static int[] activep;

	// eigenschappen van de portal
	private static float breedte = 2;
	private static float hoogte = 5;
	private int facingdirection;

	private GLU glu = new GLU();
	private static GLUT glut = new GLUT();

	private Portal toportal;
	private boolean connected;
	// distance of the portal to the player
	private float dx, dy, dz;

	boolean teleportation;

	/*
	 * Creates a Portal object that has an position and a facing direction
	 */
	public Portal(double x, double y, double z, int facingdirection) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.facingdirection = facingdirection % 4;
		this.setisConnected(false);
		this.portalcamera = new Camera(0, 0, 0, facingdirection * -90, 0);
	}


	/**
	 * to calculate the corresponing worlds each portal is linked to, so that
	 * only one world to a portal has to be drawn
	 */
	public static void privatemazeID() {
		for (int i = 0; i < portalList.size(); i++) {
			for (int j = 0; j < portalList.size(); j++) {
				if (Portal.Equals(portalList.get(j).gettoPortal(), portalList.get(i))) {
					portalList.get(i).setConnectedlevelID(Math.round(j / amountmazep));
					System.out.println("het level waar portal " + i + " connected mee is: " + portalList.get(i).getConnectedlevelID());
				}
			}
		}
	}

/**
 * The method that is activated from mazerunner to display the portals, from here all the functions for Portal when playing the game are being called
 * @param gl
 */
	public static void activePortaldisplay(GL gl) {
		// finding the activeportals, portals are identified with an integer
		// number
		Portal.mazeID = MazeRunner.level.getCurrentMaze(MazeRunner.player);
		Portal.portalList = MazeRunner.portalList;
		Portal.mazeList = World.mazelist;
		
		// to calculate the corresponding worlds each portal is linked to
		if (!run) {
			privatemazeID();
			run = true;
		}
		
		//finding the activeportals through which maze(level) the player is in
		if (mazeID != -1 && portalList.size() > 0) {
			activep = new int[amountmazep];
			for (int i = 0; i < amountmazep; i++) {
				activep[i] = mazeID * amountmazep + i;
			}

			// all portal cameras are updated
			for (int i = 0; i < portalList.size(); i++) {
				portalList.get(i).calcPortaltoPlayer(MazeRunner.player);
				portalList.get(i).updateCamera(glut, gl, MazeRunner.getPlayer(), i);
			}

			// displaying all the portals that aren't active
			displayInactivePortals(gl, portalList);

			// the active portals are being calculated relative to the player,
			// and both are sequentially being stencilled
			for (int i = 0; i < amountmazep; i++) {
				portalList.get(activep[i]).stencilborder(gl);
				stencil(gl, portalList.get(activep[i]), activep[i]);
			}


		} else {
			// if mazeID =-1 then the player is not in a level and all the portals are inactive
			displayInactivePortals(gl, portalList);
		}

	}

	/**
	 * Here the inactivePortal is being drawn, and the border of the active Portal
	 * @param gl
	 */
	public void stencilborder(GL gl) {

		gl.glColor3d(1, 1, 1);
		gl.glClearColor(1, 1, 1, 0);
		ChangeGL.GLtoTexturedItem(gl);

		gl.glPushMatrix();
		gl.glTranslated(this.x, (this.y), this.z);
		gl.glRotatef(facingdirection * 90, 0, 1, 0);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 45);
		gl.glBegin(GL.GL_QUADS);

		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(0, 0, -breedte * 0.5 - border);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(0, hoogte + border, -breedte * 0.5 - border);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(0, hoogte + border, breedte * 0.5 + border);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(0, 0, breedte * 0.5 + border);
		gl.glEnd();
		gl.glPopMatrix();

	}
	
	
/**
 * Here the stencilbuffer is being used to be able to only draw what's at the other side of the portal
 * through the portal itself
 * @param gl
 * @param p Current active Portal being used
 * @param num, which of the portals are being processed right now. 2 Portals each Frame
 */
	public static void stencil(GL gl, Portal p, int num) {

		GLUT glut = new GLUT();

		// enable stenciltest
		gl.glEnable(GL.GL_STENCIL_TEST);

		// temporarily disable colormask because what is stencilled does not
		// need to have color
		gl.glColorMask(false, false, false, false);
		gl.glDepthMask(false);

		// glstencilfunc compares the ref and mask value with the stencilpixel
		// and mask value and
		// when it passes the pixel is passed to the next test or rasterization
		// process(glstencilop)

		// first parameter is the test function
		// second is the stencil value that is being compared
		// third is mask: a mask applied to both ref and the stencil pixel; you
		// can use 0xFF (if you have 8 bitplanes) to disable the mask
		gl.glStencilFunc(GL.GL_NEVER, 1, -1);
		// gl.glStencilFunc(GL.GL_NOTEQUAL, 1, -1);

		// 1. sfail: the test from glStencilFunc failed
		// 2. dpfail: the test from glStencilFunc passed, but the depth buffer
		// test failed
		// 3. dppass: the test from glStencilFunc passed, and the depth buffer
		// passed or is disabled
		gl.glStencilOp(GL.GL_REPLACE, GL.GL_REPLACE, GL.GL_KEEP);

		// control the bits of an operation
		gl.glStencilMask(-1);
		// clear the stencil buffer so everything else is unaffected
		gl.glClear(GL.GL_STENCIL_BUFFER_BIT);

		// Teken.rechthoek(gl, x1, y1, x2, y2);

		// portal die getest wordt op view
		p.stencilDisplay(gl);

		gl.glColorMask(true, true, true, true);
		gl.glDepthMask(true);

		// TODO hier moet de view voor de portal gegeven worden

		gl.glStencilMask(0);

		// if buffervalue is equal to 0, then it gets overwritten
		gl.glStencilFunc(GL.GL_EQUAL, 0, 0xFF);

		// if buffervalue equal to 1 then it gets overwritten
		gl.glStencilFunc(GL.GL_EQUAL, 1, 0xFF);

		portalView(gl, p.portalcamera, num);

		gl.glDisable(GL.GL_STENCIL_TEST);
		// displayInactivePortals(gl, MazeRunner.portalList);
	}
	/**
	 * Here the polygon is being drawn to define to clip what's at the connected portal with the borders of the activePortal
	 * @param gl
	 */
	public void stencilDisplay(GL gl) {

		gl.glPushMatrix();
		gl.glTranslated(this.x, (this.y), this.z);
		gl.glRotatef(facingdirection * 90, 0, 1, 0);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3d(0, 0, -breedte * 0.5);
		gl.glVertex3d(0, hoogte, -breedte * 0.5);
		gl.glVertex3d(0, hoogte, breedte * 0.5);
		gl.glVertex3d(0, 0, breedte * 0.5);

		gl.glEnd();

	}

	/**
	 * Here everything is drawn that is seen through a Portal at it's connected Portal
	 * @param gl
	 * @param portalcamera
	 * @param num
	 */
	public static void portalView(GL gl, Camera portalcamera, int num) {

		GLU glu = new GLU();
		GLUT glut = new GLUT();
		gl.glViewport(0, 0, MazeRunner.getScreenWidth(), MazeRunner.getScreenHeight());

		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		// gl.glClear(GL.GL)

		Skybox.displaySkybox(gl, (portalList.get(num).facingdirection - portalList.get(num).toportal.getFacingdirection()));

		glu.gluLookAt(portalcamera.getLocationX(), portalcamera.getLocationY(), portalcamera.getLocationZ(), portalcamera.getVrpX(), portalcamera.getVrpY(), portalcamera.getVrpZ(),
				portalcamera.getVuvX(), portalcamera.getVuvY(), portalcamera.getVuvZ());

		// Here only the maze should be shown that the portalview leads to...
		mazeList.get(portalList.get(num).getConnectedlevelID()).display(gl);

	}

	/**
	 * To display all inactive Portals
	 * @param gl
	 * @param portalList
	 */
	public static void displayInactivePortals(GL gl, ArrayList<Portal> portalList) {
		GLUT glut = new GLUT();
		if (mazeID != -1) {
			for (int i = 0; i < portalList.size(); i++) {
				boolean check = true;
				for (int j = 0; j < amountmazep; j++) {
					if (i == activep[j]) {
						check = false;
					}
				}
				if (check) {
					portalList.get(i).stencilborder( gl);
				}
			}
		} else {
			for (int i = 0; i < portalList.size(); i++) {
				portalList.get(i).stencilborder( gl);
			}
		}
	}

/**
 * When the world is intialized, here the Portals are connected to each other
 * @param mazes
 */
	public static void connectPortals(int[] mazes) {
		if (MazeRunner.portalList.size() > 0) {
			for (int i = 0; i < mazes.length - 1; i++) {
				portalConnection(MazeRunner.portalList.get(i * 2 + 1), MazeRunner.portalList.get((i + 1) * 2));
				System.out.println("Connecting portal " + (i * 2 + 1) + " with portal " + (i + 1) * 2);
			}
			portalConnection(MazeRunner.portalList.get(mazes.length * 2 - 1), MazeRunner.portalList.get(0));
			System.out.println("Connecting portal" + (mazes.length * 2 - 1) + " with portal " + 0);
		}
	}
	/**
	 * With toteleport, the Player is teleported from one Portal to it's connected Portal
	 * @param player
	 * @param teleportation
	 */
	public void toteleport(Player player, boolean teleportation) {
		if (teleportation && player.canTeleport) {
			// canteleport checks if The player is allowed to teleport, he's dead, and moves back to the starting position
			player.canTeleport = false;

			player.setLocationX(toportal.getX());
			player.setLocationY(toportal.getY() + 2.5);
			player.setLocationZ(toportal.getZ());
			player.setLocationY(player.getLocationY());

			player.setHorAngle(player.getHorAngle() + (facingdirection - toportal.getFacingdirection()) * 90);
			// teleportation boolean checks if The player is allowed to teleport, not allowed when he is already being teleported
			teleportation = false;
		}
	}

	// public void setPortalID(int i) {
	// portalID = i;
	// }

/**
 * Checks if the player from one frame to the other moves in the range of a Portal from one side to another,
 * then toteleport is called
 * @param player
 * @param previousX
 * @param previousY
 * @param previousZ
 */
	public void checkteleportation(Player player, float previousX, float previousY, float previousZ) {

		boolean teleportation = false;
		float playerx = (float) player.getLocationX();
		float playery = (float) player.getLocationY();
		float playerz = (float) player.getLocationZ();

		float range = (float) breedte; // deze waarde wordt de breedte van de
										// portal

		// als de speler van de ene kant van de portal naar de andere is gegaan
		// dan teleporteert hij aan de facingdirection bij de andere portal

		// TODO currentmaze moet extern verkregen worden als parameter
		if (MazeRunner.level.getCurrentMaze(player) != -1) {
			if (Math.abs(player.locationY - this.y) < 5) {
				if (facingdirection == 0 || facingdirection == 2) {
					if (playerz > this.z - range && playerz < this.z + range) {
						if (previousX < x && playerx > x || previousX > x && playerx < x) {
							teleportation = true;
						}
					}
				} else {
					if (playerx > this.x - range && playerx < this.x + range) {
						if (previousZ < z && playerz > z || previousZ > z && playerz < z) {
							teleportation = true;
						}
					}

				}
			}
		}
		//Here toteleport is called, after every check, if teleportation is true, then the player is send to the
		// coordinates of toportal
		toteleport(player, teleportation);

	}

/**
 * String representation of a portal
 */
	public String toString() {
		return "Portal at x=" + x + " y=" + y + " z=" + z + " Connected to: "// p2.toString()
		;
	}

	/**
	 * Checks if two Portals are the same, and therefore cannot be connected
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static boolean Equals(Portal p1, Portal p2) {
		// TODO: compare the portals
		boolean equal = false;
		if (p1.getX() == p2.getX() && p1.getY() == p2.getY() && p1.getX() == p2.getX() && p1.getFacingdirection() == p2.getFacingdirection()) {
			equal = true;
		}
		return equal;
	}

	public void setconnectedTo(Portal p) {
		this.toportal = p;
	}

	/**
	 * Connects two Portals to one another, in this version there is no check implemented to check if the portals to
	 * be connected are the same, this should be there though
	 * @param p1
	 * @param p2
	 */
	public static void portalConnection(Portal p1, Portal p2) {
	
		p1.setconnectedTo(p2);
		p2.setconnectedTo(p1);

		p1.setisConnected(true);
		p2.setisConnected(true);

		
	}

	/**
	 * Calculates the distance of the portal to the player and stores it in the Portal
	 * @param player
	 */
	public void calcPortaltoPlayer(Player player) {
		this.dx = (float) (player.getLocationX() - this.x);
		this.dy = (float) (player.getLocationY() - this.y);
		this.dz = (float) (player.getLocationZ() - this.z);

	}

/**
 * Updates the camera position of where the camera at the connected Portal should be relative to the distance of
 * the player to the portal
 * @param glut
 * @param gl
 * @param player
 * @param num
 */
	public void updateCamera(GLUT glut, GL gl, Player player, int num) {
		if (connected) {


			int facingdirection = this.facingdirection - toportal.getFacingdirection() + 2;

			double xtransform = Math.cos(Math.toRadians(90 * facingdirection)) * (player.getLocationX() - this.x) - Math.sin(Math.toRadians(90 * facingdirection)) * (player.getLocationZ() - this.z)
					+ toportal.getX() - mazeList.get(portalList.get(num).getConnectedlevelID()).mazeX;
			double ytransform = player.getLocationY() - this.y + toportal.getY() - mazeList.get(portalList.get(num).getConnectedlevelID()).mazeY;
			double ztransform = Math.sin(Math.toRadians(90 * facingdirection)) * (player.getLocationX() - this.x) + Math.cos(Math.toRadians(90 * facingdirection)) * (player.getLocationZ() - this.z)
					+ toportal.getZ() - mazeList.get(portalList.get(num).getConnectedlevelID()).mazeZ;

			portalcamera.setLocationX(xtransform);
			portalcamera.setLocationY(ytransform);
			portalcamera.setLocationZ(ztransform);
			portalcamera.setHorAngle(player.getHorAngle());
			portalcamera.setVerAngle(player.getVerAngle());
			portalcamera.calculateVRP(facingdirection + 2);

		}

	}


	public boolean getisConnected() {
		return connected;
	}

	public void setisConnected(boolean connected) {
		this.connected = connected;
	}

	public double getX() {
		return this.x;
	}

	public Camera getCamera() {
		return this.portalcamera;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public int getFacingdirection() {
		return this.facingdirection;
	}

	public float getPlayerdx() {
		return this.dx;
	}

	public float getPlayerdy() {
		return this.dy;
	}

	public float getPlayerdz() {
		return this.dz;
	}

	public Portal gettoPortal() {
		return toportal;
	}

	private void setConnectedlevelID(int ID) {
		this.connectedmazeID = ID;
	}

	private int getConnectedlevelID() {
		return this.connectedmazeID;
	}

	public static void Portaltoconnectionreset() {
		Portal.run = false;
	}
}
