package engine;
import java.sql.SQLException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Main;
import menu.Teken;

public class PlayerStateVictory extends PlayerState{

	@Override
	public void itemUse() {
//		MazeRunner.player.reset();
////		MazeRunner.player.score = 1000;
//		for(int e = 0; e < MazeRunner.enemyList.size(); e++){
//			MazeRunner.enemyList.get(e).reset();
//		}
//		Player.canTeleport = false;
//		Player.playerStateInt = 0;
//		Player.canMove = true;
	}

	@Override
	public void entering() {
		Player.canMove = false;
//		System.out.println("Victory! Score: " + MazeRunner.player.score);	
		try {
			Main.db.stat.executeUpdate("DROP TABLE IF EXISTS highscores" + MazeRunner.level.getNaam());
			// stat.executeUpdate("CREATE TABLE students (studentnumber INT, name STRING);");
			Main.db.stat.executeUpdate("CREATE TABLE IF NOT EXISTS highscores" + MazeRunner.level.getNaam()
					+ " (naam STRING, score INT);");
			Main.db.stat.executeUpdate("INSERT INTO highscores" + MazeRunner.level.getNaam()
					+ " (naam, score) values ('Joris', " + MazeRunner.player.score + ");");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Main.db.printHighscores("SELECT * FROM highscores" + MazeRunner.level.getNaam() + " ORDER BY score DESC LIMIT 0,4");
	}

	@Override
	public void leaving() {
		//nothing
		
	}

	@Override
	public void drawInfo(GLAutoDrawable autodrawable, GL gl){
		String st= "SELECT * FROM highscores" + MazeRunner.level.getNaam() + " ORDER BY score DESC LIMIT 0,4";
		Main.db.printHighscores(autodrawable, gl, st);

	}

	@Override
	public void displayItem(GL gl) {
		// nothing		
	}

}
