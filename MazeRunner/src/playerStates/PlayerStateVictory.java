package playerStates;

import java.sql.SQLException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import engine.InputDialog;
import engine.MazeRunner;
import menu.Main;
import menu.Teken;
//This class is responsible for what happens once you reach the finish: this takes care of drawing the highscores, being invulnerable and having the possibility to transition into flymode.
public class PlayerStateVictory extends PlayerState {

	private static int highscoreMode = 1;
	@Override
	public void itemUse() {
		leaving();
		MazeRunner.player.playerStateInt = 5;
	}

	//Once you enter this state, your performance is put into the database
	@Override
	public void entering() {
		MazeRunner.player.getControl().info = true;
		MazeRunner.player.getControl().minimap = false;
		MazeRunner.player.canMove = false;
		String name = Main.accName;
		try {
//			Main.db.stat.executeUpdate("DROP TABLE IF EXISTS highscores" + MazeRunner.level.getNaam());
			Main.db.stat.executeUpdate("CREATE TABLE IF NOT EXISTS highscores" + MazeRunner.level.getNaam() + " (naam STRING, score INT, time INT);");
			Main.db.stat.executeUpdate("INSERT INTO highscores" + MazeRunner.level.getNaam() + " (naam, score, time) values ('" + name + "', " + MazeRunner.player.score + ", "
					+ MazeRunner.playingTime + ");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void leaving() {
		MazeRunner.player.canMove = true;
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl) {
		if(highscoreMode == 1){
			drawScoreHighscores(autodrawable, gl);
		}else if(highscoreMode == 2){
			drawTimeHighscores(autodrawable, gl);
		}else if (highscoreMode == 3){
			drawPersonalScoreHighscores(autodrawable, gl);
		}else{
			drawPersonalTimeHighscores(autodrawable, gl);
		}
	}

	@Override
	public void displayItem(GL gl) {
		// nothing
	}
	
	//These are the four different highscores we show: global and personal time- and score-based highscores.
	public static void drawScoreHighscores(GLAutoDrawable autodrawable, GL gl){
		String st = "SELECT * FROM highscores" + MazeRunner.level.getNaam() + " ORDER BY score DESC, time ASC LIMIT 0,5";
		Main.db.printHighscores(autodrawable, gl, st);
		Teken.textDraw(autodrawable, gl, "All-around Highscores", 0.32f * MazeRunner.screenWidth, 0.92f * MazeRunner.screenHeight, 60);	
		Teken.textDraw(autodrawable, gl, "Click to fly, press Escape to return to the Menu, press TAB to switch highscores", 0.15f * MazeRunner.screenWidth, 0.05f * MazeRunner.screenHeight, 30);
	}
	
	public static void drawPersonalScoreHighscores(GLAutoDrawable autodrawable, GL gl) {
		String st = "SELECT * FROM highscores" + MazeRunner.level.getNaam() + " WHERE naam == '" + Main.accName + "' ORDER BY score DESC, time ASC LIMIT 0,5";
		Main.db.printHighscores(autodrawable, gl, st);
		Teken.textDraw(autodrawable, gl, "Personal Highscores", 0.35f * MazeRunner.screenWidth, 0.92f * MazeRunner.screenHeight, 60);
		Teken.textDraw(autodrawable, gl, "Click to fly, press Escape to return to the Menu, press TAB to switch highscores", 0.15f * MazeRunner.screenWidth, 0.05f * MazeRunner.screenHeight, 30);

	}

	public static void drawTimeHighscores(GLAutoDrawable autodrawable, GL gl) {
		String st = "SELECT * FROM highscores" + MazeRunner.level.getNaam() + " ORDER BY time ASC, score DESC LIMIT 0,5";
		Main.db.printHighscores(autodrawable, gl, st);
		Teken.textDraw(autodrawable, gl, "All-around Timebased Highscores", 0.25f * MazeRunner.screenWidth, 0.92f * MazeRunner.screenHeight, 60);
		Teken.textDraw(autodrawable, gl, "Click to fly, press Escape to return to the Menu, press TAB to switch highscores", 0.15f * MazeRunner.screenWidth, 0.05f * MazeRunner.screenHeight, 30);
	}
	
	public static void drawPersonalTimeHighscores(GLAutoDrawable autodrawable, GL gl) {
		String st = "SELECT * FROM highscores" + MazeRunner.level.getNaam() + " WHERE naam == '" + Main.accName + "' ORDER BY time ASC, score DESC LIMIT 0,5";
		Main.db.printHighscores(autodrawable, gl, st);
		Teken.textDraw(autodrawable, gl, "Personal Timebased Highscores", 0.22f * MazeRunner.screenWidth, 0.92f * MazeRunner.screenHeight, 60);
		Teken.textDraw(autodrawable, gl, "Click to fly, press Escape to return to the Menu, press TAB to switch highscores", 0.15f * MazeRunner.screenWidth, 0.05f * MazeRunner.screenHeight, 30);
	}
	
	public static void increaseHighscoreMode(){
		highscoreMode++;
		highscoreMode = highscoreMode % 4;
	}

}
