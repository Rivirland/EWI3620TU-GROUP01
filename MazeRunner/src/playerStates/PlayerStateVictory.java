package playerStates;

import java.sql.SQLException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import engine.InputDialog;
import engine.MazeRunner;
import menu.Main;
import menu.Teken;

public class PlayerStateVictory extends PlayerState {

	@Override
	public void itemUse() {
		leaving();
		MazeRunner.player.playerStateInt = 5;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Main.db.printHighscores("SELECT * FROM highscores" +
		// MazeRunner.level.getNaam() + " ORDER BY score DESC LIMIT 0,4");
	}

	@Override
	public void leaving() {
		MazeRunner.player.canMove = true;
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl) {
		String st = "SELECT * FROM highscores" + MazeRunner.level.getNaam() + " ORDER BY score DESC, time ASC LIMIT 0,5";
		Main.db.printHighscores(autodrawable, gl, st);
		Teken.textDraw(autodrawable, gl, "Click to fly, press Escape to return to the Menu, press TAB to switch highscores", 0.15f * MazeRunner.screenWidth, 0.05f * MazeRunner.screenHeight, 30);

	}

	@Override
	public void displayItem(GL gl) {
		// nothing
	}

	public static void drawPersonalHighscores(GLAutoDrawable autodrawable, GL gl) {
		String st = "SELECT * FROM highscores" + MazeRunner.level.getNaam() + " WHERE naam == '" + Main.accName + "' ORDER BY score DESC, time ASC LIMIT 0,5";
		Main.db.printHighscores(autodrawable, gl, st);
		Teken.textDraw(autodrawable, gl, "Click to fly, press Escape to return to the Menu, press TAB to switch highscores", 0.15f * MazeRunner.screenWidth, 0.05f * MazeRunner.screenHeight, 30);

	}

	public static void drawNonPersonalHighscores(GLAutoDrawable autodrawable, GL gl) {
		String st = "SELECT * FROM highscores" + MazeRunner.level.getNaam() + " WHERE naam <> '" + Main.accName + "' ORDER BY score DESC, time ASC LIMIT 0,5";
		Main.db.printHighscores(autodrawable, gl, st);
		Teken.textDraw(autodrawable, gl, "Click to fly, press Escape to return to the Menu, press TAB to switch highscores", 0.15f * MazeRunner.screenWidth, 0.05f * MazeRunner.screenHeight, 30);

	}

}
