package engine;

import javax.media.opengl.GL;

public class Wall {
	
	private final static double DOOR_WIDTH = 1.5;
	private final static double DOOR_HEIGHT = 3;
	
	public static void paintWallZFromQuad_Door(GL gl, double h, double ITEM_HEIGHT, double WALL_LENGTH, double WALL_WIDTH) {
		gl.glDisable(GL.GL_CULL_FACE);
		
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 2);
		gl.glBegin(GL.GL_QUAD_STRIP);

		// TODO: light shading on walls
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, wallColour, 0);

		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(0.0, h, 0.0);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(0.0, ITEM_HEIGHT + h, 0.0);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(0.0, h, WALL_WIDTH);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(0.0, ITEM_HEIGHT + h, WALL_WIDTH);
		gl.glNormal3d(-1, 0, 0);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, h, WALL_WIDTH);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, ITEM_HEIGHT + h, WALL_WIDTH);
		gl.glNormal3d(0, 0, 1);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, h, 0.0);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, ITEM_HEIGHT + h, 0.0);
		gl.glNormal3d(1, 0, 0);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(0.0, h, 0.0);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(0.0, ITEM_HEIGHT + h, 0.0);
		gl.glNormal3d(0, 0, -1);
		gl.glEnd();
		
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 2);
		gl.glBegin(GL.GL_QUAD_STRIP);

		// TODO: light shading on walls
		// gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, wallColour, 0);

		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(WALL_LENGTH, h, 0.0);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(WALL_LENGTH, ITEM_HEIGHT + h, 0.0);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d(WALL_LENGTH, h, WALL_WIDTH);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d(WALL_LENGTH, ITEM_HEIGHT + h, WALL_WIDTH);
		gl.glNormal3d(1, 0, 0);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, h, WALL_WIDTH);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, ITEM_HEIGHT + h, WALL_WIDTH);
		gl.glNormal3d(0, 0, 1);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, h, 0.0);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, ITEM_HEIGHT + h, 0.0);
		gl.glNormal3d(-1, 0, 0);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d(WALL_LENGTH, h, 0.0);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d(WALL_LENGTH, ITEM_HEIGHT + h, 0.0);
		gl.glNormal3d(0, 0, -1);
		gl.glEnd();
		
		
		
//boven de deur
		gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
		gl.glBindTexture(GL.GL_TEXTURE_2D, 2);
		gl.glBegin(GL.GL_QUAD_STRIP);
		
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, ITEM_HEIGHT + h, 0.0);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, ITEM_HEIGHT + h, 0.0);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, DOOR_HEIGHT + h, 0.0);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, DOOR_HEIGHT + h, 0.0);
		gl.glNormal3d(0, 0, -1);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, DOOR_HEIGHT + h, WALL_WIDTH);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, DOOR_HEIGHT + h, WALL_WIDTH);
		gl.glNormal3d(0, 0, 1);
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, ITEM_HEIGHT + h, WALL_WIDTH);
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, ITEM_HEIGHT + h, WALL_WIDTH);
		gl.glNormal3d(0, -1, 0);
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex3d((WALL_LENGTH-DOOR_WIDTH)/2, ITEM_HEIGHT + h, 0.0);
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex3d((WALL_LENGTH+DOOR_WIDTH)/2, ITEM_HEIGHT + h, 0.0);
		gl.glNormal3d(0, 1, 0);
		
		gl.glEnd();
		
	}
}
