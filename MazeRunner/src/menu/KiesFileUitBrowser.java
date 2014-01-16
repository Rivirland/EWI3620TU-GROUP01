package menu;

import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFileChooser;

import engine.Maze;

public class KiesFileUitBrowser {

	
	@SuppressWarnings("deprecation")
	public String loadFile(Frame f, String title, String defDir, String fileType) {
		f.setAlwaysOnTop(true);
		f.setUndecorated(true);
		f.requestFocus();
		f.toFront();
		f.repaint();
		FileDialog fd = new FileDialog(f, title, FileDialog.LOAD);
		fd.requestFocus();
		fd.toFront();
		fd.repaint();
		fd.setFile(fileType);
		fd.setDirectory(defDir);
		fd.setLocation(50, 50);
		fd.show();
		return fd.getFile();

	}

	@SuppressWarnings("deprecation")
	public String saveFile(Frame f, String title, String defDir, String fileType) {
		f.setAlwaysOnTop(true);
		f.setUndecorated(true);
		f.requestFocus();
		f.toFront();
		f.repaint();
		FileDialog fd = new FileDialog(f, title, FileDialog.SAVE);
		fd.requestFocus();
		fd.toFront();
		fd.repaint();
		fd.setFile(fileType);
		fd.setDirectory(defDir);
		fd.setLocation(50, 50);
		fd.show();
		return fd.getFile();
	}

}