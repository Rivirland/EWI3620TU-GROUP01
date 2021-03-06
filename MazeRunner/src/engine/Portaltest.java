package engine;



import static org.junit.Assert.*;

import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import org.junit.Test;

import com.sun.opengl.util.GLUT;

import engine.Player;
import engine.Portal;

public class Portaltest {
	
	Portal p1= new Portal(5.0,0,5, 1);
	Portal p2= new Portal(15.0,0, 15, 0);
	Player player = new Player(5, 0, 15, 0, 0);
	
	GL gl;
	GLUT glut;
	GLU glu;
	GLAutoDrawable drawable;

	@Test
	public void Portalinitialisortest() {
		assertEquals(5.0, p1.getX(), Math.abs(5.0- p1.getX()));
	}
	
	@Test
	public void portalEqualstest(){
		assertEquals(Portal.Equals(p1, p1), true);
	}
	
	@Test
	public void Portalconnectiontest () {
		Portal.portalConnection(p1, p2);
		assertEquals(true, Portal.Equals(p1, p2.gettoPortal()));
		assertEquals(true, Portal.Equals(p2, p1.gettoPortal()));
	}
	
	@Test
	public void Portalteleportationtest() {
		//TODO hier moet de speler worden gesimuleerd die door een portal gaat
		// en hij moet correct geregistreerd worden bij de connected portal waar
		// hij naartoe is geteleporteerd	
	}
	
	public void calcPortaltoplayertest() {
	
		
	}
	@Test
	public void PortalCameralocationTest () {
		
		gl = drawable.getGL();
		glut = new GLUT();
		
		Portal.portalConnection(p1, p2);
		
		p1.calcPortaltoPlayer(player);
		p2.calcPortaltoPlayer(player);
		
//		p1.updateCamera(glut, gl);
//		p2.updateCamera(glut, gl);
		
		double p1camerapositionx=
		p1.getCamera().getLocationX()*Math.cos(90*p1.getFacingdirection())-
		p1.getCamera().getLocationZ()*Math.sin(90*p2.getFacingdirection());
		
		double p1camerapositionz=
				p1.getCamera().getLocationZ()*Math.cos(90*p2.getFacingdirection())+
				p1.getCamera().getLocationX()*Math.sin(90*p2.getFacingdirection());
		
		
		p1.getCamera().getLocationZ();
		
		//assertEquals ()
		p1.getCamera().getLocationX();
		
	}
	
	

}
