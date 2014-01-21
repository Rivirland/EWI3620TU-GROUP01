package engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;

//This class is responsible for managing the database and printing the highscores from the database.
public class Database {
	public Connection conn;
	public Statement stat;
	public ResultSet rs;

	public Database() {
		try {
			Class.forName("org.sqlite.JDBC");
			/*
			 * Create a connection to a database stored in a local file. The
			 * DriverManager will select the driver that we just loaded. To
			 * initially create a database file,
			 */
			conn = DriverManager.getConnection("jdbc:sqlite:db/mydatabase.db");

			/*
			 * A Statement object is used to send SQL statements and retrieve
			 * its results.
			 */
			stat = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeUpdate(String st) {
		try {
			stat.executeUpdate(st);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//This prints the highscores.
	public void printHighscores(GLAutoDrawable autodrawable, GL gl, String st) {
		float h = 0.75f;
		
		//Prints the titles for the different columns.
		Teken.textDraw(autodrawable, gl, "Player Name", 0.1f * MazeRunner.screenWidth, 0.85f * MazeRunner.screenHeight, 60);
		Teken.textDraw(autodrawable, gl, "Score", 0.5f * MazeRunner.screenWidth, 0.85f * MazeRunner.screenHeight, 60);
		Teken.textDraw(autodrawable, gl, "Time", 0.8f * MazeRunner.screenWidth, 0.85f * MazeRunner.screenHeight, 60);
		try {
			rs = stat.executeQuery(st);
			while (rs.next()) {

				// Retrieve the values from the rows, by specifying the column
				// name.
				String name = rs.getString("naam");
				//If the name is too long, it is cut off. This prevents the name from drawing over the scores.
				if (name.length() > 10) {
					name = name.substring(0, 9);
				}
				
				//Prints the name, score and time on the screen on the specified height. 
				Teken.textDraw(autodrawable, gl, name, 0.1f * MazeRunner.screenWidth, h * MazeRunner.screenHeight, 60);
				Teken.textDraw(autodrawable, gl, Integer.toString(rs.getInt("score")), 0.5f * MazeRunner.screenWidth, h * MazeRunner.screenHeight, 60);
				Teken.textDraw(autodrawable, gl, Integer.toString(rs.getInt("time") / 1000), 0.8f * MazeRunner.screenWidth, h * MazeRunner.screenHeight, 60);

				//We decrease h, so the next player's stats will be drawn below the current one's.
				h -= 0.1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			rs.close();
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
