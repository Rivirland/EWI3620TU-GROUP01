package engine;

import model.Vector3d;

public class Vertex {
	public double x;
	public double y;
	public double z;

	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3d substract(Vertex v1) {
		double diffX = this.x - v1.x;
		double diffY = this.y - v1.y;
		double diffZ = this.z - v1.z;
		return new Vector3d(diffX, diffY, diffZ);
	}

	public String toString() {
		return "Vertex: " + this.x + " " + this.y + " " + this.z;
	}

}
