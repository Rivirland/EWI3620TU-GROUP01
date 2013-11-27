package engine;

import javax.media.opengl.GL;

import com.sun.opengl.util.GLUT;

public class Portal {
	
	// coordinaten van de portal
	private float x;
	private float y;
	private float z;
	
	// eigenschappen van de portal
	private static float breedte = 2;
	private static float hoogte = 5;
	private int facingdirection; 
	
	// een waarde {0,... 3} de portal bevindt zich altijd in het xz vlak, loodrecht op het grondvlak
	// 0 = positieve x richting, 1 = positieve z richting enz....
	
	private Portal toportal;
	private boolean connected;
	// afstand tussen portal en de speler, nodig voor het berekenen van de camera positie
	private float dx, dy ,dz;
	//private float playerhorAngle, playerverAngle;
	
	boolean teleportation;
	
	/*
	 * Creates a Portal object that has an position and a facing direction
	 */
	public Portal(float x, float y, float z, int facingdirection){
		this.x=x;
		this.y=y;
		this.z=z;
		this.facingdirection=facingdirection%3; // als hoger dan 3 dan komt het toch goed
		this.setisConnected(false);
		
	}
	
	/*public Player toteleport (Player player, boolean teleportation){
		if (teleportation){
			
		}
	}*/
	
	public void toteleport (Player player, boolean teleportation){
		if (teleportation){
			player.setLocationX(toportal.getX());
			player.setLocationZ(toportal.getZ());
			player.setLocationY(player.getLocationY());
			//player.setLocationY(toportal.getY());
			
			// de kijkrichting wordt  het verschil in hoek tussen de twee portalen + de huidige kijkrichting
			
			// verschil in facing direction
			// facingdirection - toportal.getFacingdirection() *90
			
			player.setHorAngle(player.getHorAngle()+(facingdirection-toportal.getFacingdirection())*90);
			teleportation = false;
		}
	}
		
	
	// deze functie moet voor een bepaalde portal in klasse MazeRunner, methode updatemovement
	public void checkteleportation (Player player, float previousX, float previousY, float previousZ){
		
		boolean teleportation = false;
		float playerx = (float) player.getLocationX() ;
		float playery = (float) player.getLocationY() ;
		float playerz = (float) player.getLocationZ() ;
		
		float range = (float) breedte; // deze waarde wordt de breedte van de portal
		
		
		//als de speler van de ene kant van de portal naar de andere is gegaan dan teleporteert hij aan de facingdirection bij de andere portal
		
		//if()
		if(playery > 0 && playery < hoogte){
		
		if (facingdirection== 0 || facingdirection ==2){
			
			if (playerz>this.z-range && playerz < this.z+range){
				
				
				if (previousX<x && playerx>x || previousX>x && playerx<x){ // kijken of bij van x< x van portal naar x > x van portal is gegaan terwijl z binnen de boundary is
					teleportation = true;
//					System.out.println(teleportation);
				}
				
			}
			
		}else{
			
			if (playerx>this.x-range && playerx < this.x+range){
			
			if (previousZ<z && playerz>z || previousZ>z && playerz<z){
				
				teleportation = true;
//				System.out.println(teleportation);
			}
			}
			// kijken of bij van z< z van portal naar z > z van portal is gegaan terwijl x binnen de boundary is
			
			
		}
		}
		// hier wordt toteleport aangeroepen na elke check, als teleportation true is wordt de speler naar de coordinaten van de toportal gestuurd
		
		toteleport(player, teleportation);
		
	}
	
	public void displayPortal (GLUT glut, GL gl){
		gl.glPushMatrix();
		
		gl.glTranslatef(this.x, (float) (this.y-0.5*hoogte), this.z);
	
		//lineOnScreen(gl, this.x, (float) (this.y+0.5*hoogte), this.z, this.x+5, (float) (this.y+0.5*hoogte), this.z);
		
		
		
		gl.glRotatef(facingdirection*90, 0, 1, 0); 
		
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
//		gl.glDisable(GL.GL_CULL_FACE);
		gl.glBegin(GL.GL_QUADS);
		
		
		gl.glVertex3d(0, 0, -breedte*0.5); 
		
		gl.glVertex3d(0, hoogte, -breedte*0.5);
		
		gl.glVertex3d(0, hoogte, breedte*0.5);
		
		gl.glVertex3d(0, 0, breedte*0.5);
		gl.glEnd();
		gl.glPopMatrix();
		
		/*gl.glPushMatrix();
		gl.glRotatef(facingdirection*90, 0, 1, 0); 
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(this.x, (float) (this.y+0.5*hoogte), this.z);
		gl.glVertex3f(this.x+5, (float) (this.y+0.5*hoogte), this.z);
		gl.glEnd();
		gl.glPopMatrix();*/
		gl.glPushMatrix();
		
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glCullFace(GL.GL_BACK);
//		gl.glEnable(GL.GL_CULL_FACE);
		gl.glPopMatrix();
		//gl.glLoadIdentity();
		
	}
	
	public String toString(){
		return "Portal at x="+x+" y="+y+" z="+z+" Connected to: "// p2.toString()
		;
	}
	
	/**
	 * Checks if two Portals are the same, and therefore cannot be connected
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static boolean Equals (Portal p1, Portal p2){
		//@TODO compare the portals
		return false;
	}
	
	public void setconnectedTo (Portal p){
		this.toportal = p;
	}
	//public static float Dis
	
	public static void portalConnection(Portal p1, Portal p2){
		if (!Equals(p1,p2)){
		p1.setconnectedTo(p2);
		p2.setconnectedTo(p1);
		p1.setisConnected(true);
		p2.setisConnected(true);
		
		
		
		//p1.}
	} else {System.out.println("Connected portals cannot be the same portal, dummiexD \n No PortalConnection is made");}
		}
	
	public void calcPortaltoPlayer (Player player){
		
		this.dx = (float) (player.getLocationX() - this.x);
		this.dy = (float) (player.getLocationY() - this.y);
		this.dz = (float) (player.getLocationZ() - this.z);
		//this.playerhorAngle = (float) player.getHorAngle();
		//this.playerverAngle = (float) player.getVerAngle();
	}
		
		// make vector between positions of playerobject and portal object

	public void createCamera(GLUT glut,GL gl){
		if (connected){
			
			float x = this.toportal.getX() + this.dx;
			float y = this.toportal.getY() + this.dy;
			float z = this.toportal.getZ() + this.dz;
			gl.glPushMatrix();
			
			gl.glRotatef(facingdirection*90, 0, 1, 0);
			gl.glTranslatef(x, y, z);
			
			// camera van een portal moet altijd in de richting van de portal wijzen
			//+1/(Math.tan(dy/dx))?
			// de hoek is theta= tan-1(yd/xd)
			//gl.glRotatef((float) ((float)(toportal.getFacingdirection()*90)+1/(Math.tan(dz/dx))), 0, 1, 0);
			
			//float veclength= (float) Math.sqrt(dx*dx+dy*dy+dz*dz);
			/*
			if (facingdirection == 0 || facingdirection ==2){
				gl.glRotatef((float) (facingdirection*90+ Math.tan(dz/dx)), 0, 1, 0);
			}
			else {gl.glRotatef((float) (facingdirection*90+ Math.tan(dx/dz)), 0, 1, 0);}
			*/
			//float straightlength= (float) Math.sqrt(dx*dx+dy*dy+dz*dz);
			//gl.glRotatef((float) (facingdirection*90+ Math.cos(straightlength/veclength)), 0, 1, 0);
			
			// voor nu is een wirecube gebruikt om de camera te representeren
			glut.glutWireCube(1);
			gl.glPopMatrix();
			
			
		}
		
		
		
	}

		// create new camera with the same distance and direction as the camera at p1, but this distance and
		// direction at p2, the opposite way of the normal(the facingdirection) of p2
		
		
		
		// get information of current camera
		
		//Camera camera1= Camera.get
		
		// map the new camera on the windowview, and clip the camera on the boundary's of the surface of p1
		
		//make it so that if the camera at p1 moves or changes direction, this movement is replicated by
		// p2
		
	
	
	public static void Connection(Portal p1){
		// a new camera is made with the same distance and direction from portal (1) connected to,
		// as the camera at the other portal (portal 2) (see sketch) this position is constantly changed when the
		// the camera moves
		// if the player is transported to the other place, the other camera becomes the current camera
		// that is only clipped on the window, the camera that was the current camera before is clipped
		// on portal 2
	}
	
	

	public boolean getisConnected() {
		return connected;
	}

	public void setisConnected(boolean connected) {
		this.connected = connected;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getZ() {
		return this.z;
	}
	
	public int getFacingdirection(){
		return this.facingdirection;
	}
	
	public float getPlayerdx (){
		return this.dx;
	}
	
	public float getPlayerdy (){
		return this.dy;
	}
	
	public float getPlayerdz (){
		return this.dz;
	}
	
}
