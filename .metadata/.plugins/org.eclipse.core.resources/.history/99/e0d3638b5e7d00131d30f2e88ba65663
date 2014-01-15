package menu;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import engine.InputDialog;

public class LogIn {

	private int screenWidth = 800, screenHeight = 600;

	public LogIn(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void setScreen(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}

	public void display(GLAutoDrawable drawable, GL gl) {
		Teken.startText(drawable, "Arial", 60);
		Teken.endText(60);
		Teken.textDraw(drawable, gl, "Z@idm@n The G@me", 300f / 1920f * screenWidth, 830f / 1080f * screenHeight, 90);
		if ((750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth)
				&& (350f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 450f / 1080f * screenHeight)) {
			Teken.textDrawMetKleur(drawable, gl, "Log In", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60, 1f, 0f, 0f);
		} else {
			Teken.textDraw(gl, "Log In", 750f / 1920f * screenWidth, 680f / 1080f * screenHeight, 60);
		}

		// ik beweeg over de tweede knop => deze licht rood op
		if ((750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth)
				&& (500f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 600f / 1080f * screenHeight)) {
			Teken.textDrawMetKleur(drawable, gl, "Log out", 750f / 1920f * screenWidth, 530f / 1080f * screenHeight, 60, 1f, 0f, 0f);
		} else {
			Teken.textDraw(gl, "Log out", 750f / 1920f * screenWidth, 530f / 1080f * screenHeight, 60);
		}
		// ik beweeg over de derde knop => deze licht rood op
		if (750f / 1920f * screenWidth < MouseInfo.getPointerInfo().getLocation().getX() && MouseInfo.getPointerInfo().getLocation().getX() < 1170f / 1920f * screenWidth
				&& (650f / 1080f * screenHeight < MouseInfo.getPointerInfo().getLocation().getY() && MouseInfo.getPointerInfo().getLocation().getY() < 750f / 1080f * screenHeight)) {
			Teken.textDrawMetKleur(drawable, gl, "Create Account", 750f / 1920f * screenWidth, 380f / 1080f * screenHeight, 60, 1f, 0f, 0f);
		} else {
			Teken.textDraw(gl, "Create Account", 750f / 1920f * screenWidth, 380f / 1080f * screenHeight, 60);
		}

	}

	public void mouseReleased(MouseEvent me) {
		if ((750f / 1920f * screenWidth < me.getX() && me.getX() < 1170f / 1920f * screenWidth) && (350f / 1080f * screenHeight < me.getY() && me.getY() < 450f / 1080f * screenHeight)) {
			// System.out.println("login");
			InputDialog ID = new InputDialog();
			String name = ID.getString("What's your name?");
			try {
				Main.db.rs = Main.db.stat.executeQuery("SELECT COUNT(*) AS result FROM accounts WHERE name == '" + name + "';");
				if (Main.db.rs.getInt("result") == 1) {
					String password = ID.getString("What's your password?");
					Main.db.rs = Main.db.stat.executeQuery("SELECT password FROM accounts WHERE name == '" + name + "';");
					if (password.equals(Main.db.rs.getString("password"))) {
						Main.loggedIn = true;
						Main.accName = name;
						Main.setCurrentstate(0);
					} else {
						System.out.println("Passwords do not match");
					}
				} else {
					System.out.println("No account with that name exists");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if ((750f / 1920f * screenWidth < me.getX() && me.getX() < 1170f / 1920f * screenWidth) && (500f / 1080f * screenHeight < me.getY() && me.getY() < 600f / 1080f * screenHeight)) {
			// System.out.println("logout");
			Main.loggedIn = false;
			Main.setCurrentstate(0);
		}
		if (750f / 1920f * screenWidth < me.getX() && me.getX() < 1170f / 1920f * screenWidth && (650f / 1080f * screenHeight < me.getY() && me.getY() < 750f / 1080f * screenHeight)) {
			// System.out.println("create");
			InputDialog ID = new InputDialog();
			boolean accepted = false;
			String name = null;
			while (!accepted) {
				name = ID.getString("What's your name?");
				try {
					Main.db.rs = Main.db.stat.executeQuery("SELECT COUNT(*) AS result FROM accounts WHERE name == '" + name + "';");
					if (Main.db.rs.getInt("result") == 0 && !name.equals(null)) {
						accepted = true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String password = ID.getString("What's your password?");
			try {
				if (!password.equals(null)) {
					Main.db.stat.executeUpdate("CREATE TABLE IF NOT EXISTS accounts (name STRING, password STRING);");
					Main.db.stat.executeUpdate("INSERT INTO accounts (name, password) values ('" + name + "', '" + password + "');");
					Main.loggedIn = true;
					Main.accName = name;
					Main.setCurrentstate(0);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

}
