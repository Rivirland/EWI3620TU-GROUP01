package menu;

import java.awt.Font;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.j2d.TextRenderer;

public class Teken {


	public static void textDraw (GLAutoDrawable drawable, GL gl, String str, float x, float y, float size){
		int xint = (int) x;
		int yint = (int) y;
		int sizeint = (int) size;
		
		TextRenderer renderer = new TextRenderer(new Font("Agency FB", Font.BOLD, sizeint));
		renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
	    // optionally set the color
	    renderer.setColor(1.0f, 1f, 1f, 1f);
	    renderer.draw(str, xint, yint);
	    
	    // ... more draw commands, color changes, etc.
	    renderer.endRendering();
	}
	
	public static void textDraw (GLAutoDrawable drawable, GL gl, String str, float x, float y, float size, String font){
		int xint = (int) x;
		int yint = (int) y;
		int sizeint = (int) size;
		
		TextRenderer renderer = new TextRenderer(new Font(font, Font.BOLD, sizeint));
		renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
	    // optionally set the color
	    renderer.setColor(1.0f, 1f, 1f, 1f);
	    renderer.draw(str, xint, yint);
	    
	    // ... more draw commands, color changes, etc.
	    renderer.endRendering();
	}
	
	public static void textDrawMetKleur (GLAutoDrawable drawable, GL gl, String str, float x, float y, float size, float r, float g, float b){
		int xint = (int) x;
		int yint = (int) y;
		int sizeint = (int) size;
		
		TextRenderer renderer = new TextRenderer(new Font("Agency FB", Font.BOLD, sizeint));
		renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
	    // optionally set the color
	    renderer.setColor(r, g, b, 1f);
	    renderer.draw(str, xint, yint);
	    
	    // ... more draw commands, color changes, etc.
	    renderer.endRendering();
	}
	
public static void tekenButton(GL gl, float xmin, float ymin, float xmax, float ymax){
		gl.glColor3f(0.16f, 0.16f, 0.16f);
		rechthoek(gl, xmin, ymin, xmax, ymax);
		
		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, xmin, ymin, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmin, ymax);
		lineOnScreen(gl, xmin, ymin, xmin, ymax);
	}
	
public static void tekenButtonMetKleur(GL gl, float xmin, float ymin, float xmax, float ymax, float r, float g, float b){
		gl.glColor3f(r, g, b);
		rechthoek(gl, xmin, ymin, xmax, ymax);
		
		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, xmin, ymin, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmin, ymax);
		lineOnScreen(gl, xmin, ymin, xmin, ymax);
	}
	
public static void rechthoek(GL gl, float x, float y, float x2, float y2) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x2, y);
		gl.glVertex2f(x2, y2);
		gl.glVertex2f(x, y2);
		gl.glEnd();
	}

public static void lineOnScreen(GL gl, float x1, float y1, float x2, float y2) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		gl.glEnd();
	}
	
}
