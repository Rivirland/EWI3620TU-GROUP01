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
	// public int portalID;
	// public int portalConnectionID;
	private double border = 0.2;

	private static int[][] portalconnectionlist;
	// private int[] connectedportals;

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

	// een waarde {0,... 3} de portal bevindt zich altijd in het xz vlak,
	// loodrecht op het grondvlak
	// 0 = positieve x richting, 1 = positieve z richting enz....

	private GLU glu = new GLU();
	private static GLUT glut = new GLUT();

	private Portal toportal;
	private boolean connected;
	// afstand tussen portal en de speler, nodig voor het berekenen van de
	// camera positie
	private float dx, dy, dz;
	// private float playerhorAngle, playerverAngle;

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

	// public Portal(float x, float y, float z, int facingdirection, int ID, int
	// cID) {
	// this.x = x;
	// this.y = y;
	// this.z = z;
	// this.facingdirection = facingdirection % 4; // als hoger dan 3 dan komt
	// // het toch goed
	// this.setisConnected(false);
	// this.portalID = ID;
	// this.portalConnectionID = cID;
	// }

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
	 * Portals vinden voor het level waar de player nu in zit, dit werkt omdat
	 * de levels in zelfde sequentie worden geladen als de portals
	 */
	public static void activePortaldisplay(GL gl) {
		// gl.glLoadIdentity();

		// finding the activeportals, portals are identified with an integer
		// number
		Portal.mazeID = MazeRunner.level.getCurrentMaze(MazeRunner.player);
		Portal.portalList = MazeRunner.portalList;
		Portal.mazeList = World.mazelist;
		// MazeRunner.setEventMessage("mazeID "+mazeID);

		// to calculate the corresponing worlds each portal is linked to
		if (!run) {
			privatemazeID();
			run = true;
		}

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

			// TODO tijdelijk om 1 keer stencil te testen
			// stencil(gl, portalList.get(activep[0]), 0);

		} else {
			displayInactivePortals(gl, portalList);
		}

	}

	public void stencilborder(GL gl) {

		// gl.glColor3d(1, 1, 1);
		gl.glClearColor(1, 1, 1, 0);

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

	public static void portalView(GL gl, Camera portalcamera, int num) {

		GLU glu = new GLU();
		GLUT glut = new GLUT();
		gl.glViewport(0, 0, MazeRunner.getScreenWidth(), MazeRunner.getScreenHeight());

		// glu.gluLookAt(portalcamera.getLocationX(),
		// portalcamera.getLocationY(), portalcamera.getLocationZ(),
		// portalcamera.getVrpX(), portalcamera.getVrpY(),
		// portalcamera.getVrpZ(), portalcamera.getVuvX(),
		// portalcamera.getVuvY(), portalcamera.getVuvZ());
		// method to only use use gl clear only once each portal flow
		// if (num>pcounter){
		// gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		// pcounter = num +1;
		// }else {
		// pcounter = -1;
		// }
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		// gl.glClear(GL.GL)

		Skybox.displaySkybox(gl,(portalList.get(num).facingdirection - portalList.get(num).toportal.getFacingdirection() ));

		glu.gluLookAt(portalcamera.getLocationX(), portalcamera.getLocationY(), portalcamera.getLocationZ(), portalcamera.getVrpX(), portalcamera.getVrpY(), portalcamera.getVrpZ(),
				portalcamera.getVuvX(), portalcamera.getVuvY(), portalcamera.getVuvZ());

		// Here only the maze should be shown that the portalview leads to...
		mazeList.get(portalList.get(num).getConnectedlevelID()).display(gl);
		// MazeRunner.setEventMessage("portalnum: "+num+"level to be drawn:"+portalList.get(num).getConnectedlevelID());

		// MazeRunner.visibleIterator(gl);
		// gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
	}

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
					portalList.get(i).displayPortal(glut, gl);
				}
			}
		} else {
			for (int i = 0; i < portalList.size(); i++) {
				portalList.get(i).displayPortal(glut, gl);
			}
		}
	}

	public static void connectPortals(int[] mazes) {
		if (MazeRunner.portalList.size() > 0) {
			// portalconnectionlist = new
			// int[MazeRunner.portalList.size()][amountmazep-1];
			for (int i = 0; i < mazes.length - 1; i++) {
				portalConnection(MazeRunner.portalList.get(i * 2 + 1), MazeRunner.portalList.get((i + 1) * 2));
				System.out.println("Connecting portal " + (i * 2 + 1) + " with portal " + (i + 1) * 2);
				// for (int j=0; j<amountmazep; j++){

				// }
			}
			portalConnection(MazeRunner.portalList.get(mazes.length * 2 - 1), MazeRunner.portalList.get(0));
			System.out.println("Connecting portal" + (mazes.length * 2 - 1) + " with portal " + 0);
		}
	}

	public void toteleport(Player player, boolean teleportation) {
		if (teleportation) {

			player.setLocationX(toportal.getX());
			player.setLocationY(toportal.getY() + 2.5);
			player.setLocationZ(toportal.getZ());
			player.setLocationY(player.getLocationY());
			// player.setLocationY(toportal.getY());

			// de kijkrichting wordt het verschil in hoek tussen de twee
			// portalen + de huidige kijkrichting

			// verschil in facing direction
			// facingdirection - toportal.getFacingdirection() *90

			player.setHorAngle(player.getHorAngle() + (facingdirection - toportal.getFacingdirection()) * 90);
			teleportation = false;
		}
	}

	// public void setPortalID(int i) {
	// portalID = i;
	// }

	// deze functie moet voor een bepaalde portal in klasse MazeRunner, methode
	// updatemovement
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
							// System.out.println(teleportation);
						}
					}
					// kijken of bij van z< z van portal naar z > z van portal
					// is
					// gegaan terwijl x binnen de boundary is

				}
			}
		}
		// hier wordt toteleport aangeroepen na elke check, als teleportation
		// true is wordt de speler naar de coordinaten van de toportal gestuurd
		toteleport(player, teleportation);

	}

	public void displayPortal(GLUT glut, GL gl) {
		// gl.glColor4d(1, 1, 1, 1);

		gl.glPushMatrix();

		gl.glClearColor(0, 0, 0, 0);
		gl.glTranslated(this.x, (this.y), this.z);

		// lineOnScreen(gl, this.x, (float) (this.y+0.5*hoogte), this.z,
		// this.x+5, (float) (this.y+0.5*hoogte), this.z);

		gl.glRotatef(facingdirection * 90, 0, 1, 0);
		// gl.glColor3f(1, 1, 1);

		gl.glColor4d(1, 1, 1, 1);

		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBegin(GL.GL_QUADS);

		gl.glVertex3d(0, 0, -breedte * 0.5);
		gl.glVertex3d(0, hoogte, -breedte * 0.5);
		gl.glVertex3d(0, hoogte, breedte * 0.5);
		gl.glVertex3d(0, 0, breedte * 0.5);

		gl.glEnd();
		gl.glPopMatrix();

		/*
		 * gl.glPushMatrix(); gl.glRotatef(facingdirection*90, 0, 1, 0);
		 * gl.glBegin(GL.GL_LINES); gl.glVertex3f(this.x, (float)
		 * (this.y+0.5*hoogte), this.z); gl.glVertex3f(this.x+5, (float)
		 * (this.y+0.5*hoogte), this.z); gl.glEnd(); gl.glPopMatrix();
		 */
		gl.glPushMatrix();
		gl.glColor3f(1, 1, 1);
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		// gl.glCullFace(GL.GL_BACK);
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glColor4d(0, 0, 0, 0);
		gl.glPopMatrix();

		// gl.glLoadIdentity();

	}

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

	public static void portalConnection(Portal p1, Portal p2) {
		// if (!Equals(p1, p2)) {
		p1.setconnectedTo(p2);
		p2.setconnectedTo(p1);
		// System.out.println("Connected portal: " + p1.portalID +
		// " and portal: " + p2.portalID);
		p1.setisConnected(true);
		p2.setisConnected(true);

		// p1.}
		// } else {
		// System.out.println("Connected portals cannot be the same portal, dummiexD \n No PortalConnection is made");

	}

	public void calcPortaltoPlayer(Player player) {

		this.dx = (float) (player.getLocationX() - this.x);
		// TODO testmodus, y moet weer aan
		this.dy = (float) (player.getLocationY() - this.y);
		this.dz = (float) (player.getLocationZ() - this.z);

		// System.out.println("x "+dx+"y "+dy+"z "+dz);
		// this.playerhorAngle = (float) player.getHorAngle();
		// this.playerverAngle = (float) player.getVerAngle();
	}

	// make vector between positions of playerobject and portal object

	public void updateCamera(GLUT glut, GL gl, Player player, int num) {
		if (connected) {
			// de camera die wordt vertoont op p1 staat op evengrote omgekeerde
			// afstand van de facing direction van portal p2 als de speler tot
			// portal p1
			// de camera staat gericht in dezelfde facing direction als de
			// facing direction van p2

			// double x = this.toportal.getX() + this.dx;
			// double y = this.toportal.getY() + this.dy;
			// double z = this.toportal.getZ() + this.dz;

			int facingdirection = this.facingdirection - toportal.getFacingdirection()+2;

			double xtransform = Math.cos(Math.toRadians(90 * facingdirection)) * (player.getLocationX() - this.x) - Math.sin(Math.toRadians(90 * facingdirection)) * (player.getLocationZ() - this.z)
					+ toportal.getX()- mazeList.get(portalList.get(num).getConnectedlevelID()).mazeX;
			double ytransform = player.getLocationY() - this.y + toportal.getY() - mazeList.get(portalList.get(num).getConnectedlevelID()).mazeY;
			double ztransform = Math.sin(Math.toRadians(90 * facingdirection)) * (player.getLocationX() - this.x) + Math.cos(Math.toRadians(90 * facingdirection)) * (player.getLocationZ() - this.z)
					+ toportal.getZ()- mazeList.get(portalList.get(num).getConnectedlevelID()).mazeZ;

//			MazeRunner.setEventMessage("hcurrent: " + mazeList.get(mazeID).mazeY + " hconnected: " + mazeList.get(portalList.get(num).getConnectedlevelID()).mazeY + "connectedmazeID"
//					+ portalList.get(num).getConnectedlevelID());

			gl.glPushMatrix();
			gl.glTranslated(this.toportal.getX(), this.toportal.getY(), this.toportal.getZ());
			gl.glRotated(90 * facingdirection, 0, 1, 0);
			gl.glTranslated(-this.x, -this.y, -this.z);
			gl.glTranslated(player.getLocationX(), player.getLocationY(), player.getLocationZ());
			// gl.glRotated(90, 0, 1, 0);
			// gl.glTranslated(xtransform, ytransform, ztransform);
			glut.glutSolidCube(1);
			gl.glPopMatrix();

			// gl.glPushMatrix();
			//
			//
			// //TODO: x en z moeten keer de facingdirection 90 graden draaien
			// (clockwise)
			// // de translatie moet geflipt worden op de facing direction van
			// de
			// // portal
			//
			// // juiste coordinaat = geflipte coordinaat - portal coordinaat
			//
			// gl.glRotatef(facingdirection * -90, 0, 1, 0);
			// gl.glTranslated(x, y, z);
			// // System.out.println("x "+x+"y "+y+"z "+z);
			//
			// // voor nu is een wirecube gebruikt om de camera te representeren
			//
			// glut.glutSolidCube(2);
			// gl.glPopMatrix();
			// // Teken.lineOnScreen(gl, x, z, this.toportal.getX(),
			// this.toportal.getZ());

			portalcamera.setLocationX(xtransform);
			portalcamera.setLocationY(ytransform);
			portalcamera.setLocationZ(ztransform);
			portalcamera.setHorAngle(player.getHorAngle());
			portalcamera.setVerAngle(player.getVerAngle());
			portalcamera.calculateVRP(facingdirection+2);
			// portalcamera.calculateVRP(facingdirection);

		}

	}

	public static void displayCamera(Camera portalcamera) {

	}

	// create new camera with the same distance and direction as the camera at
	// p1, but this distance and
	// direction at p2, the opposite way of the normal(the facingdirection) of
	// p2

	// get information of current camera

	// Camera camera1= Camera.get

	// map the new camera on the windowview, and clip the camera on the
	// boundary's of the surface of p1

	// make it so that if the camera at p1 moves or changes direction, this
	// movement is replicated by
	// p2

	public static void Connection(Portal p1) {
		// a new camera is made with the same distance and direction from portal
		// (1) connected to,
		// as the camera at the other portal (portal 2) (see sketch) this
		// position is constantly changed when the
		// the camera moves
		// if the player is transported to the other place, the other camera
		// becomes the current camera
		// that is only clipped on the window, the camera that was the current
		// camera before is clipped
		// on portal 2
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
