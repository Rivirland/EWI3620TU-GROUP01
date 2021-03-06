package model;

import engine.Vertex;

public class Vector3d {
	public double x,y,z;
	
	
	public Vector3d(double x,double y,double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3d crossproduct(Vector3d v1) {
		double i = this.y * v1.z - this.z*v1.y;
		double j = this.x * v1.z - this.z*v1.x;
		double k = this.x * v1.y - this.y*v1.x;
		return new Vector3d(i,-j,k);
	}
	
	public String toString(){
		return "Vector: " + this.x + " " + this.y + " " + this.z;
	}

	public void normalize() {
		double length = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
		this.x = this.x / length;
		this.y = this.y / length;
		this.z = this.z / length;
	}

	public static Vector3d calcNormal(Vector3d v1v2, Vector3d v1v3) {
		Vector3d n = v1v2.crossproduct(v1v3);
		n.normalize();
		return n;
	}
	public static Vector3d calcNormal(Vertex v1, Vertex v2, Vertex v3){
		Vector3d v1v2 = v2.substract(v1);
		Vector3d v1v3 = v3.substract(v1);
		return calcNormal(v1v2, v1v3);
	}
}
