package levelEditor;

import javax.media.opengl.GL;
import menu.Teken;

public class Catalogus {
	
	private static final byte NIETS = 0;
	private static final byte KOLOM = 1;
	private static final byte MUUR = 2;
	private static final byte DAK = 3;
	private static final byte GEBOUW = 4;
	
	public static void drawCatalogus(GL gl, boolean catalogus, byte drawMode, float screenWidth, float screenHeight, LevelEditor leveleditor){
		if (catalogus){
			if (drawMode == KOLOM){
				//Teken.tekenButtonMetKleur(gl, 109.8f/1920*screenWidth, 751.25f/1080*screenHeight, 209.8f/1920*screenWidth, 851.25f/1080*screenHeight, 1f, 0f, 0f);
				leveleditor.plaatsTexture(gl, 109.8f, 751.25f, 209.8f, 851.25f, 3);
				leveleditor.plaatsTexture(gl, 229.6f, 751.25f, 329.6f, 851.25f, 4);
				//Teken.tekenButtonMetKleur(gl, 229.6f/1920*screenWidth, 751.25f/1080*screenHeight, 329.6f/1920*screenWidth, 851.25f/1080*screenHeight, 0f, 1f, 0f);
			
			}
			if (drawMode == MUUR){
				//Teken.tekenButtonMetKleur(gl, 109.8f/1920*screenWidth, 751.25f/1080*screenHeight, 209.8f/1920*screenWidth, 851.25f/1080*screenHeight, 1f, 0f, 0f);
				//Teken.tekenButtonMetKleur(gl, 229.6f/1920*screenWidth, 751.25f/1080*screenHeight, 329.6f/1920*screenWidth, 851.25f/1080*screenHeight, 0f, 1f, 0f);
				leveleditor.plaatsTexture(gl, 109.8f, 751.25f, 209.8f, 851.25f, 15);
				leveleditor.plaatsTexture(gl, 229.6f, 751.25f, 329.6f, 851.25f, 16);
			}
			if (drawMode == DAK){
				leveleditor.plaatsTexture(gl, 109.8f, 751.25f, 209.8f, 851.25f, 2);
				//leveleditor.plaatsTexture(gl, 229.6f, 751.25f, 329.6f, 851.25f, 11);
			}
		}
	}
}