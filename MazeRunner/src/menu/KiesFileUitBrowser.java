package menu;

import java.awt.*;
public class KiesFileUitBrowser {

  @SuppressWarnings("deprecation")
public String loadFile
      (Frame f, String title, String defDir, String fileType) {
    FileDialog fd = new FileDialog(f, title, FileDialog.LOAD);
    fd.setFile(fileType);
    fd.setDirectory(defDir);
    fd.setLocation(50, 50);
    fd.show();
    return fd.getFile();
    }

  @SuppressWarnings("deprecation")
public String saveFile
      (Frame f, String title, String defDir, String fileType) {
    FileDialog fd = new FileDialog(f, title,    FileDialog.SAVE);
    fd.setFile(fileType);
    fd.setDirectory(defDir);
    fd.setLocation(50, 50);
    fd.show();
    return fd.getFile();
    }
  
}