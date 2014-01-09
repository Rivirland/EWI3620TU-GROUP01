package engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import menu.Teken;

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
			conn = DriverManager
					.getConnection("jdbc:sqlite:db/mydatabase.db");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printHighscores(GLAutoDrawable autodrawable, GL gl,String st) {
		float h = 0.8f; 
		Teken.textDraw(autodrawable, gl, "Player Name", 0.35f*MazeRunner.screenWidth, 0.9f*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));
		Teken.textDraw(autodrawable, gl, "Score", 0.65f*MazeRunner.screenWidth, 0.9f*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));
		try {
			rs = stat.executeQuery(st);
			while (rs.next()) {
				
				// Retrieve the values from the rows, by specifying the column
				// name.
				Teken.textDraw(autodrawable, gl, rs.getString("naam"), 0.35f*MazeRunner.screenWidth, h*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));
				Teken.textDraw(autodrawable, gl, Integer.toString(rs.getInt("score")), 0.65f*MazeRunner.screenWidth, h*MazeRunner.screenHeight, 0.05f*Math.min(MazeRunner.screenHeight, MazeRunner.screenWidth));

				h-=0.1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			rs.close();
			stat.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// try{
	// Class.forName("org.sqlite.JDBC");
	// /* Create a connection to a database stored in a local file. The
	// DriverManager will select
	// * the driver that we just loaded. To initially create a database file, */
	// Connection conn =
	// DriverManager.getConnection("jdbc:sqlite:db/mydatabase.db");
	//
	// /* A Statement object is used to send SQL statements and retrieve its
	// results. */
	// Statement stat = conn.createStatement();
	//
	// /* A few example statements, these are executed at once. */
	//
	// // This sample recreates the database each time it is run.
	// stat.executeUpdate("DROP TABLE IF EXISTS students;");
	// stat.executeUpdate("CREATE TABLE students (studentnumber INT, name STRING);");
	//
	// // Insert some student data
	// stat.executeUpdate("INSERT INTO students (studentnumber, name) values (123456, 'Piet');");
	// stat.executeUpdate("INSERT INTO students (studentnumber, name) values (654321, 'Maria');");
	//
	//
	// // Select all columns from the table 'students'
	// ResultSet rs = stat.executeQuery("SELECT * FROM students");
	//
	// System.out.println("Students in database:");
	//
	// /* The result set contains multiple rows of results. rs.next() selects
	// the next row and returns true only if it exists.
	// * Initially, the cursor is placed before the first row. */
	//
	// // While there are rows of requested data left
	// while (rs.next()) {
	// // Retrieve the values from the rows, by specifying the column name.
	// System.out.println("Student number = " + rs.getInt("studentnumber"));
	// System.out.println("Name = " + rs.getString("name"));
	// }
	//
	// /* It is important to manually close the connection when we're done, so
	// that the database server is not overloaded
	// * with connections waiting to time out. */
	// rs.close();
	// stat.close();
	// conn.close();
	// }
	// catch(Exception e){
	// System.out.println("Zaidmanexception occured!");
	// e.printStackTrace();
	// }

}
