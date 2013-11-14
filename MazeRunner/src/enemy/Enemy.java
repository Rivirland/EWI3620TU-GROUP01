package enemy;

import new_default.Control;
import new_default.GameObject;

public class Enemy extends GameObject {
	private double speed;

	private Control control = null;

	public Enemy(double x, double y, double z) {
		// Set the initial position and viewing direction of the player.
		super(x, y, z);
		speed = 0.01;
	}

	/**
	 * Sets the Control object that will control the player's motion
	 * <p>
	 * The control must be set if the object should be moved.
	 * 
	 * @param input
	 */
	public void setControl(Control control) {
		this.control = control;
	}

	/**
	 * Gets the Control object currently controlling the player
	 * 
	 * @return
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * Returns the speed.
	 * 
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 * 
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void update(int deltaTime) {
		if (control != null) {
			control.update();
		}
	}
}
