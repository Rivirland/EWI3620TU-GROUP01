package levelEditor;

import javax.media.opengl.GL;
import menu.Teken;

public class Catalogus {
	
	private static final byte NIETS = 0;
	private static final byte KOLOM = 1;
	private static final byte MUUR = 2;
	private static final byte DAK = 3;
	private static final byte ITEM = 4;
	
	public static void drawCatalogus(GL gl, boolean catalogus, byte drawMode, float screenWidth, float screenHeight, LevelEditor leveleditor){
		if (catalogus){
			if (drawMode == KOLOM){
				//Teken.tekenButtonMetKleur(gl, 109.8f/1920*screenWidth, 751.25f/1080*screenHeight, 209.8f/1920*screenWidth, 851.25f/1080*screenHeight, 1f, 0f, 0f);
				leveleditor.plaatsTexture(gl, 109.8f, 751.25f, 209.8f, 851.25f, 3);
				leveleditor.plaatsTexture(gl, 229.6f, 751.25f, 329.6f, 851.25f, 3);
				leveleditor.plaatsTexture(gl, 349.4f, 751.25f, 449.4f, 851.25f, 4);
				leveleditor.plaatsTexture(gl, 469.2f, 751.25f, 569.2f, 851.25f, 4);
				//Teken.tekenButtonMetKleur(gl, 229.6f/1920*screenWidth, 751.25f/1080*screenHeight, 329.6f/1920*screenWidth, 851.25f/1080*screenHeight, 0f, 1f, 0f);
			
			}
			if (drawMode == MUUR){
				//Teken.tekenButtonMetKleur(gl, 109.8f/1920*screenWidth, 751.25f/1080*screenHeight, 209.8f/1920*screenWidth, 851.25f/1080*screenHeight, 1f, 0f, 0f);
				//Teken.tekenButtonMetKleur(gl, 229.6f/1920*screenWidth, 751.25f/1080*screenHeight, 329.6f/1920*screenWidth, 851.25f/1080*screenHeight, 0f, 1f, 0f);
				leveleditor.plaatsTexture(gl, 109.8f, 751.25f, 209.8f, 851.25f, 29);
				leveleditor.plaatsTexture(gl, 229.6f, 751.25f, 329.6f, 851.25f, 29);
				leveleditor.plaatsTexture(gl, 254.6f, 751.25f, 304.6f, 801.25f, 16);
				leveleditor.plaatsTexture(gl, 349.4f, 751.25f, 449.4f, 851.25f, 30);
				leveleditor.plaatsTexture(gl, 469.2f, 751.25f, 569.2f, 851.25f, 30);
				leveleditor.plaatsTexture(gl, 494.6f, 751.25f, 544.6f, 801.25f, 16);
				
				leveleditor.plaatsTexture(gl, 109.8f, 642.50f, 209.8f, 742.50f, 31);
				leveleditor.plaatsTexture(gl, 229.6f, 642.50f, 329.6f, 742.50f, 31);
				leveleditor.plaatsTexture(gl, 254.6f, 642.50f, 304.6f, 692.50f, 16);
				leveleditor.plaatsTexture(gl, 349.4f, 642.50f, 449.4f, 742.50f, 32);
				leveleditor.plaatsTexture(gl, 469.2f, 642.50f, 569.2f, 742.50f, 32);
				leveleditor.plaatsTexture(gl, 494.6f, 642.50f, 544.6f, 692.50f, 16);
				
				leveleditor.plaatsTexture(gl, 109.8f, 533.75f, 209.8f, 633.75f, 33);
				leveleditor.plaatsTexture(gl, 229.6f, 533.75f, 329.6f, 633.75f, 33);
				leveleditor.plaatsTexture(gl, 254.6f, 533.75f, 304.6f, 583.75f, 16);
				leveleditor.plaatsTexture(gl, 349.4f, 533.75f, 449.4f, 633.75f, 34);
				leveleditor.plaatsTexture(gl, 469.2f, 533.75f, 569.2f, 633.75f, 34);
				leveleditor.plaatsTexture(gl, 494.6f, 533.75f, 544.6f, 583.75f, 16);
				
				leveleditor.plaatsTexture(gl, 109.8f, 425.00f, 209.8f, 525.00f, 35);
				leveleditor.plaatsTexture(gl, 229.6f, 425.00f, 329.6f, 525.00f, 35);
				leveleditor.plaatsTexture(gl, 254.6f, 425.00f, 304.6f, 475.00f, 16);
				leveleditor.plaatsTexture(gl, 349.4f, 425.00f, 449.4f, 525.00f, 36);
				leveleditor.plaatsTexture(gl, 469.2f, 425.00f, 569.2f, 525.00f, 36);
				leveleditor.plaatsTexture(gl, 494.6f, 425.00f, 544.6f, 475.00f, 16);
				
				leveleditor.plaatsTexture(gl, 109.8f, 316.25f, 209.8f, 416.25f, 37);
				leveleditor.plaatsTexture(gl, 229.6f, 316.25f, 329.6f, 416.25f, 37);
				leveleditor.plaatsTexture(gl, 254.6f, 316.25f, 304.6f, 366.25f, 16);
				leveleditor.plaatsTexture(gl, 349.4f, 316.25f, 449.4f, 416.25f, 38);
				leveleditor.plaatsTexture(gl, 469.2f, 316.25f, 569.2f, 416.25f, 38);
				leveleditor.plaatsTexture(gl, 494.6f, 316.25f, 544.6f, 366.25f, 16);
				
				leveleditor.plaatsTexture(gl, 109.8f, 207.50f, 209.8f, 307.50f, 39);
				leveleditor.plaatsTexture(gl, 229.6f, 207.50f, 329.6f, 307.50f, 39);
				leveleditor.plaatsTexture(gl, 254.6f, 207.50f, 304.6f, 257.50f, 16);
				leveleditor.plaatsTexture(gl, 349.4f, 207.50f, 449.4f, 307.50f, 40);
				leveleditor.plaatsTexture(gl, 469.2f, 207.50f, 569.2f, 307.50f, 40);
				leveleditor.plaatsTexture(gl, 494.6f, 207.50f, 544.6f, 257.50f, 16);
				
				leveleditor.plaatsTexture(gl, 109.8f, 98.75f, 209.8f, 198.75f, 41);
				leveleditor.plaatsTexture(gl, 229.6f, 98.75f, 329.6f, 198.75f, 41);
				leveleditor.plaatsTexture(gl, 254.6f, 98.75f, 304.6f, 148.75f, 16);
				leveleditor.plaatsTexture(gl, 349.4f, 98.75f, 449.4f, 198.75f, 42);
				leveleditor.plaatsTexture(gl, 469.2f, 98.75f, 569.2f, 198.75f, 42);
				leveleditor.plaatsTexture(gl, 494.6f, 98.75f, 544.6f, 148.75f, 16);
			}
			if (drawMode == DAK){
				leveleditor.plaatsTexture(gl, 109.8f, 751.25f, 209.8f, 851.25f, 2);
				//leveleditor.plaatsTexture(gl, 229.6f, 751.25f, 329.6f, 851.25f, 11);
			}
			if (drawMode == ITEM){
				leveleditor.plaatsTexture(gl, 109.8f, 751.25f, 209.8f, 851.25f, 26);
				leveleditor.plaatsTexture(gl, 229.6f, 751.25f, 329.6f, 851.25f, 17);
				leveleditor.plaatsTexture(gl, 349.4f, 751.25f, 449.4f, 851.25f, 18);
				leveleditor.plaatsTexture(gl, 469.2f, 751.25f, 569.2f, 851.25f, 57);
				
				leveleditor.plaatsTexture(gl, 109.8f, 642.50f, 209.8f, 742.50f, 14);
				leveleditor.plaatsTexture(gl, 229.6f, 642.50f, 329.6f, 742.50f, 20);
			}
		}
	}
}