package menu;

import java.awt.Font;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.j2d.TextRenderer;

import engine.Vector;
import engine.Vertex;

public class Teken {

	public static TextRenderer startText(GLAutoDrawable drawable, String font, double size) {
		int sizeint = (int) size;
		TextRenderer renderer = new TextRenderer(new Font(font, Font.BOLD, sizeint));
		renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		return renderer;
	}

	public static void textDraw(GL gl, String str, double x, double y, TextRenderer renderer) {
		int xint = (int) x;
		int yint = (int) y;
		renderer.setColor(1.0f, 1f, 1f, 1f);
		renderer.draw(str, xint, yint);

	}

	public static void endText(TextRenderer renderer) {
		renderer.endRendering();
	}

	public static void textDraw(GLAutoDrawable drawable, GL gl, String str, float x, float y, float size) {
		int xint = (int) x;
		int yint = (int) y;
		int sizeint = (int) size;

		TextRenderer renderer = new TextRenderer(new Font("Arial", Font.BOLD, sizeint));
		renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		// optionally set the color
		renderer.setColor(1.0f, 1f, 1f, 1f);
		renderer.draw(str, xint, yint);

		// ... more draw commands, color changes, etc.
		renderer.endRendering();
	}

	public static void textDraw(GLAutoDrawable drawable, GL gl, String str, float x, float y, float size, String font) {
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

	public static void textDrawMetKleur(GLAutoDrawable drawable, GL gl, String str, float x, float y, float size, float r,
			float g, float b) {
		int xint = (int) x;
		int yint = (int) y;
		int sizeint = (int) size;

		TextRenderer renderer = new TextRenderer(new Font("Arial", Font.BOLD, sizeint));
		renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		// optionally set the color
		renderer.setColor(r, g, b, 1f);
		renderer.draw(str, xint, yint);

		// ... more draw commands, color changes, etc.
		renderer.endRendering();
	}

	public static void tekenButton(GL gl, float xmin, float ymin, float xmax, float ymax) {
		gl.glColor3f(0.16f, 0.16f, 0.16f);
		rechthoek(gl, xmin, ymin, xmax, ymax);

		gl.glLineWidth(3);
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		lineOnScreen(gl, xmin, ymin, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmax, ymin);
		lineOnScreen(gl, xmax, ymax, xmin, ymax);
		lineOnScreen(gl, xmin, ymin, xmin, ymax);
	}

	public static void tekenButtonMetKleur(GL gl, float xmin, float ymin, float xmax, float ymax, float r, float g, float b) {
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

	public static void drawQuad(GL gl, Vertex v1, Vertex v2, Vertex v3, Vertex v4, Vector n1) {
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3d(n1.x, n1.y, n1.z);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(v1.x, v1.y, v1.z);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(v2.x, v2.y, v2.z);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(v3.x, v3.y, v3.z);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(v4.x, v4.y, v4.z);
		gl.glEnd();
	}

	public static void drawQuad(GL gl, Vertex v1, Vertex v2, Vertex v3, Vertex v4) {
		Vector n1 = Vector.calcNormal(v1, v2, v3);
		drawQuad(gl, v1, v2, v3, v4, n1);
	}

	public static void drawCuboid(GL gl, double xmin, double xmax, double ymin, double ymax, double zmin, double zmax, int tex) {
		drawCuboid(gl, xmin, xmax, ymin, ymax, zmin, zmax, new int[] { tex, tex, tex, tex, tex, tex });
	}

	public static void drawCuboid(GL gl, double xmin, double xmax, double ymin, double ymax, double zmin, double zmax,
			int[] texList) {
		Vertex v1 = new Vertex(xmin, ymin, zmin);
		Vertex v2 = new Vertex(xmin, ymin, zmax);
		Vertex v3 = new Vertex(xmax, ymin, zmax);
		Vertex v4 = new Vertex(xmax, ymin, zmin);
		Vertex v5 = new Vertex(xmin, ymax, zmin);
		Vertex v6 = new Vertex(xmin, ymax, zmax);
		Vertex v7 = new Vertex(xmax, ymax, zmax);
		Vertex v8 = new Vertex(xmax, ymax, zmin);

		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);
		
		// Floor plane
		gl.glBindTexture(GL.GL_TEXTURE_2D, texList[0]);
		drawQuad(gl, v4, v3, v2, v1);

		// Top plane
		gl.glBindTexture(GL.GL_TEXTURE_2D, texList[1]);
		drawQuad(gl, v5, v6, v7, v8);

		// Back plane
		gl.glBindTexture(GL.GL_TEXTURE_2D, texList[2]);
		drawQuad(gl, v7, v3, v4, v8);

		// Right side plane
		gl.glBindTexture(GL.GL_TEXTURE_2D, texList[3]);
		drawQuad(gl, v6, v2, v3, v7);

		// Front plane
		gl.glBindTexture(GL.GL_TEXTURE_2D, texList[4]);
		drawQuad(gl, v5, v1, v2, v6);

		// Left plane
		gl.glBindTexture(GL.GL_TEXTURE_2D, texList[5]);
		drawQuad(gl, v8, v4, v1, v5);

		gl.glDisable(GL.GL_CULL_FACE);
	}

	public static void drawCuboid(GL gl, double xmin, double xmax, double ymin, double ymax, double zmin, double zmax) {
		Vertex v1 = new Vertex(xmin, ymin, zmin);
		Vertex v2 = new Vertex(xmin, ymin, zmax);
		Vertex v3 = new Vertex(xmax, ymin, zmax);
		Vertex v4 = new Vertex(xmax, ymin, zmin);
		Vertex v5 = new Vertex(xmin, ymax, zmin);
		Vertex v6 = new Vertex(xmin, ymax, zmax);
		Vertex v7 = new Vertex(xmax, ymax, zmax);
		Vertex v8 = new Vertex(xmax, ymax, zmin);

		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);

		drawQuad(gl, v4, v3, v2, v1); // Floor plane
		drawQuad(gl, v5, v6, v7, v8); // Top plane
		drawQuad(gl, v7, v3, v4, v8); // Back plane
		drawQuad(gl, v6, v2, v3, v7); // Right side plane
		drawQuad(gl, v5, v1, v2, v6); // Front plane
		drawQuad(gl, v8, v4, v1, v5); // Left plane

		gl.glDisable(GL.GL_CULL_FACE);
	}

	public static void kruis(GL gl, float x1, float y1, float x2, float y2) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		gl.glEnd();
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(x1, y2);
		gl.glVertex2f(x2, y1);
		gl.glEnd();
	}

	public static void pijlboven(GL gl, float xportal, float yportal, float distance) {
		lineOnScreen(gl, xportal, yportal, xportal, yportal + distance / 2);
		lineOnScreen(gl, xportal - distance / 10, yportal + distance / 2 - distance / 10, xportal, yportal + distance / 2);
		lineOnScreen(gl, xportal + distance / 10, yportal + distance / 2 - distance / 10, xportal, yportal + distance / 2);
	}

	public static void pijlonder(GL gl, float xportal, float yportal, float distance) {
		lineOnScreen(gl, xportal, yportal, xportal, yportal - distance / 2);
		lineOnScreen(gl, xportal - distance / 10, yportal - distance / 2 + distance / 10, xportal, yportal - distance / 2);
		lineOnScreen(gl, xportal + distance / 10, yportal - distance / 2 + distance / 10, xportal, yportal - distance / 2);
	}

	public static void pijlrechts(GL gl, float xportal, float yportal, float distance) {
		lineOnScreen(gl, xportal, yportal, xportal + distance / 2, yportal);
		lineOnScreen(gl, xportal + distance / 2 - distance / 10, yportal - distance / 10, xportal + distance / 2, yportal);
		lineOnScreen(gl, xportal + distance / 2 - distance / 10, yportal + distance / 10, xportal + distance / 2, yportal);
	}

	public static void pijllinks(GL gl, float xportal, float yportal, float distance) {
		lineOnScreen(gl, xportal, yportal, xportal - distance / 2, yportal);
		lineOnScreen(gl, xportal - distance / 2 + distance / 10, yportal - distance / 10, xportal - distance / 2, yportal);
		lineOnScreen(gl, xportal - distance / 2 + distance / 10, yportal + distance / 10, xportal - distance / 2, yportal);
	}
}
