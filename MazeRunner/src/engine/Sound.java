package engine;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//This class is responsible for all the sounds.

public class Sound implements Runnable{
	
	public static Map<String, Sound> sounds = new HashMap<String, Sound>();
	private AudioClip clip;
	
	public Sound(String filename){
		try{
			clip = Applet.newAudioClip(Sound.class.getResource(filename));
		}
		catch(Exception e){
			System.out.println("error in Sound constructor with file: " + filename);
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
		} catch (FileNotFoundException e){
			System.out.println("Error in Sound.init(): FileNotFoundException: " + e.getMessage());
		} catch (IOException e){
			System.out.println("Error in Sound.init(): IO Exception: " + e.getMessage());
		}
		System.out.println("Sounds loaded succesfully\n");
	}
	@Override
	public void run() {
		try{
			clip.play();
		}catch(Exception e){
			System.out.println("error in Sound.Play()");
		}
	}
	public static void play(String string) {
		Sound sound = new Sound("/" + string);
		Thread thread = new Thread(sound);
		thread.start();
		
	}
	
}
