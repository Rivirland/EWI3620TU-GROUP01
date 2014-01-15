package levelEditor;

import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import engine.InputDialog;

public class InputLocation

{
	public JFrame frame;

	public InputLocation() {
		frame = new JFrame("Make your choice!");
	}

	public double[] getLocation() {
		InputDialog ID = new InputDialog();
		try {
			String name = ID.getString("Insert the world's location, for example 0,0,0?");
			StringTokenizer st = new StringTokenizer(name, ",");
			if (st.countTokens() == 3) {
				double[] res = new double[3];
				res[0] = Double.parseDouble(st.nextToken());
				res[1] = Double.parseDouble(st.nextToken());
				res[2] = Double.parseDouble(st.nextToken());
				return res;
			} else {
				System.out.println("Please enter according to format");
			}
		} catch (NullPointerException e) {
			System.out.println("User clicked cancel");
		} catch (NumberFormatException e) {
			System.out.println("Please enter according to format");
		}
		return new double[] { 0, 0 ,0 };
	}

	public int[] getStartingInfo() {
		InputDialog ID = new InputDialog();
		try {
			String name = ID.getString("Insert the amount of starting bullets and traps, for example: 10,1");
			StringTokenizer st = new StringTokenizer(name, ",");
			if (st.countTokens() == 2) {
				int[] res = new int[2];
				res[0] = Integer.parseInt(st.nextToken());
				res[1] = Integer.parseInt(st.nextToken());
				return res;
			} else {
				System.out.println("Please enter according to format");
			}
		} catch (NullPointerException e) {
			System.out.println("User clicked cancel");
		} catch (NumberFormatException e) {
			System.out.println("Please enter according to format");
		}
		return new int[] { 0, 0 };
	}

}