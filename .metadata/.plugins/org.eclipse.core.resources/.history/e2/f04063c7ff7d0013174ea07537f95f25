package engine;

public class Vertex {
	public double x;
	public double y;
	public double z;

	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector substract(Vertex v1) {
		double diffX = this.x - v1.x;
		double diffY = this.y - v1.y;
		double diffZ = this.z - v1.z;
		return new Vector(diffX, diffY, diffZ);
	}

	public String toString() {
		return "Vertex: " + this.x + " " + this.y + " " + this.z;
	}

}
