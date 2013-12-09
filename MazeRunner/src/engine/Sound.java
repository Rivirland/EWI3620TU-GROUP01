package engine;
import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	
	public static final Sound gunfire = new Sound("/Gunfire.wav");
	public static final Sound roofCrash = new Sound("/crash.wav");
	public static final Sound noBullets = new Sound("/noBullets.wav");
	public static final Sound reload = new Sound("/reload.wav");
	public static final Sound fire = new Sound("/fire.wav");
	
	private AudioClip clip;
	
	public Sound(String filename){
		try{
			clip = Applet.newAudioClip(Sound.class.getResource(filename));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void play(){
		try{
			new Thread(){
				public void run(){
					clip.play();
				}
			}.start();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}	
}
