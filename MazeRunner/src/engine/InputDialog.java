package engine;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
//This class makes the frame where you can enter a String pop up. The user can provide us their name, password, worlds starting locations etc.
public class InputDialog
{
	public JFrame frame;
	public InputDialog(){
		frame = new JFrame("Name");
	}
	
	public String getString(String text){
		String res = JOptionPane.showInputDialog(frame, text);
		return res;
	}
}