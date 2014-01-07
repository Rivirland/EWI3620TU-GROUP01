package levelEditor;
import java.awt.event.MouseEvent;
import javax.media.opengl.GL;
import com.sun.opengl.util.GLUT;


public class LevelEditorViewer {

	protected static final byte DRAAIMODE = 0;
	protected static final byte ZOOMMODE = 1;
	protected static final byte PANMODE = 2;
	
	protected static int mousemode= DRAAIMODE;
	
	protected double rotationX;
	protected double rotationY;
	
	protected double panX;
	protected double panY;
	
	protected double scalef;
	
	protected int xmidden;
	protected int ymidden;
	
	
	GLUT glut;
	protected int screenWidth, screenHeight;
	//GLAutoDrawable drawable;
		
	protected int x1,y1,x2,y2;
	protected int x1mouse, y1mouse, x2mouse, y2mouse;
	protected int dX, dY;
	
	public LevelEditorViewer(int screenWidth, int screenHeight, double x1, double y1, double x2, double y2){
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
		this.x1= (int) x1;
		this.y1=(int) y1;
		this.x2=(int) x2;
		this.y2=(int) y2;
		
		xmidden= (int) (((x2-x1)/2)+x1);
		ymidden= (int) (((y2-y1)/2)+y1);
		
		//scalef = screenWidth*50/782;
		
	}
	
	protected void init(GL gl){
		//is niet goed zo
		/*gl.glPushMatrix();
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glOrtho(0,0,screenWidth,screenHeight, -1000, 1000);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		*/
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_SCISSOR_TEST);
		gl.glScissor(x1,y1,(int) (x2*0.60),(int) (y2*0.89));
		//gl.glViewport(0,0,screenWidth,screenHeight);
		gl.glClearColor(0.7f,0.7f,0.7f,0.3f);
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glFlush();
		update();
}
	
	public void mouseReleased(MouseEvent e){
		mousemode = DRAAIMODE;
	}

	public void mouseDragged(MouseEvent e) {
		if (e.getX()>x1 && e.getX()<x2 && e.getY()>y1 && e.getY()<y2){
			x2mouse = e.getX();
			y2mouse = e.getY();
		}
	}
	

	
	public void update (){

		dX = x1mouse - x2mouse;
		dY = y1mouse - y2mouse;
		x1mouse = x2mouse;
		y1mouse=y2mouse;
		moveObject();
	}
	
	
	
	public void moveObject (){
		if (dX>0){
		}
		
		if (mousemode==DRAAIMODE){
		rotationX= rotationX+dX;
		
		rotationY= rotationY+dY;
		}
		if (mousemode ==ZOOMMODE){
			//scalef=scalef+dX;
			scalef=scalef+dY;
			if (scalef<0){scalef=0;}
		}
		if (mousemode ==PANMODE){
			panX = panX+dX;
			panY = panY+dY;
		}
		dX=0;
		dY=0;
		
	}
	
	protected void superMousehandling(MouseEvent e){
		if (e.getX()>x1 && e.getX()<x2 && e.getY()>y1 && e.getY()<y2){
			x1mouse = e.getX();
			y1mouse = e.getY();
			x2mouse = x1mouse;
			y2mouse = y1mouse;
			
			if (e.getButton()==2 || e.getButton()==3){
				mousemode = ZOOMMODE;
			}
		}}

	public void reshape(int screenWidth, int screenHeight, double x1, double y1, double x2, double y2){
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
		
		this.x1=(int) x1;
		this.y1=(int) y1;
		this.x2=(int) x2;
		this.y2=(int) y2;
		
		xmidden= (int) (((x2-x1)/2)+x1);
		ymidden= (int) (((y2-y1)/2)+y1);
		
		
	}	
}