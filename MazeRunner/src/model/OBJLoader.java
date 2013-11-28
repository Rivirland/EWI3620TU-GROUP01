package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.Model.Face;

public class OBJLoader {
	public static Model loadModel(File file) throws FileNotFoundException, IOException{
		 BufferedReader reader = new BufferedReader(new FileReader(file));
	        Model m = new Model();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String prefix = line.split(" ")[0];
	            if (prefix.equals("v")) {
	                m.getVertices().add(parseVertex(line));
	            } else if (prefix.equals("vn")) {
	                m.getNormals().add(parseNormal(line));
	            } else if (prefix.equals("f")) {
	                m.getFaces().add(parseFace(true, line));
	            }
	        }
	        reader.close();
	        return m;
	}

	private static Vector3d parseVertex(String line) {
        String[] xyz = line.split(" ");
        float x = Float.valueOf(xyz[1]);
        float y = Float.valueOf(xyz[2]);
        float z = Float.valueOf(xyz[3]);
        return new Vector3d(x, y, z);
    }
	
	private static Vector3d parseNormal(String line) {
        String[] xyz = line.split(" ");
        float x = Float.valueOf(xyz[1]);
        float y = Float.valueOf(xyz[2]);
        float z = Float.valueOf(xyz[3]);
        return new Vector3d(x, y, z);
    }

    private static Model.Face parseFace(boolean hasNormals, String line) {
        String[] faceIndices = line.split(" ");
        int[] vertexIndicesArray = {Integer.parseInt(faceIndices[1].split("/")[0]),
                Integer.parseInt(faceIndices[2].split("/")[0]), Integer.parseInt(faceIndices[3].split("/")[0])};
        if (hasNormals) {
            int[] normalIndicesArray = new int[3];
            normalIndicesArray[0] = Integer.parseInt(faceIndices[1].split("/")[2]);
            normalIndicesArray[1] = Integer.parseInt(faceIndices[2].split("/")[2]);
            normalIndicesArray[2] = Integer.parseInt(faceIndices[3].split("/")[2]);
            return new Model.Face(vertexIndicesArray, normalIndicesArray);
        } else {
            return new Model.Face((vertexIndicesArray));
        }
    }
	

}
