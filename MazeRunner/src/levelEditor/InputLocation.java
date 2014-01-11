package levelEditor;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InputLocation

	{
		public JFrame frame;
		public InputLocation(){
			frame = new JFrame("Make your choice!");
		}
		
		public double[] getLocation(){
			String text = JOptionPane.showInputDialog(frame, "Insert the world's location, for example 0,0,0");
			Scanner sc = new Scanner(text);
			sc.useDelimiter("\\s*,\\s*");
			double[] res = new double[3];
			res[0] = sc.nextDouble();
			res[1] = sc.nextDouble();
			res[2] = sc.nextDouble();
			return res;
		}
		
		public int[] getStartingInfo(){
			String text = JOptionPane.showInputDialog(frame, "Insert the amount of starting bullets and traps, for example: 10,1");
			Scanner sc = new Scanner(text);
			sc.useDelimiter("\\s*,\\s*");
			int[] res = new int[2];
			res[0] = sc.nextInt();
			res[1] = sc.nextInt();
			return res; 
		}
//  public static void main(String[] args)
//  {
//    // a jframe here isn't strictly necessary, but it makes the example a little more real
//    JFrame 
//
//    // prompt the user to enter their name
//    String name = JOptionPane.showInputDialog(frame, "What's your name?");
//
//    // get the user's input. note that if they press Cancel, 'name' will be null
//    System.exit(0);
//  }
}