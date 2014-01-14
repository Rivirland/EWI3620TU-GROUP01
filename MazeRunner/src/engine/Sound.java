package engine;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.opengl.util.texture.Texture;

public class Sound {
	
	public static Map<String, Sound> sounds = new HashMap<String, Sound>();
	private AudioClip clip;
	
	public Sound(String filename){
		try{
			clip = Applet.newAudioClip(Sound.class.getResource(filename));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void init(){
		System.out.println("\nLoading sounds..");
		String curDir = System.getProperty("user.dir");
		File f = new File(curDir + "\\bin\\sounds.txt");
		
		try{
			BufferedReader bufRdr = new BufferedReader(new FileReader(f));
			String line = null;
			while ((line = bufRdr.readLine()) != null) {
				Sound temp = new Sound("/" + line + ".wav");
				sounds.put(line, temp);
				System.out.println("Sound loaded succesfully: " + line);
			}
		} catch (Exception e){
			System.out.println("Error in Sound.init()");
			e.printStackTrace();
		}
		System.out.println("Sounds loaded succesfully\n");
	}
	
	public void play2(){
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
	public void play(){
		try{
			new Thread(
					new Runnable(){
						public void run(){
							clip.play();
						}
			}).start();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}	
}
