package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
public class OBJLoaderTest extends TestCase {
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Test
	public void testParseVertex() {
		String line = "v 5 4 3";
		Vector3d v = OBJLoader.parseVertex(line);
		assertEquals(v.x, 5.0);
		assertEquals(v.y, 4.0);
		assertEquals(v.z, 3.0);
	}
	@Test
	public void testParseNormal() {
		String line = "vn 5 4 3";
		Vector3d vn = OBJLoader.parseNormal(line);
		assertEquals(vn.x, 5.0);
		assertEquals(vn.y, 4.0);
		assertEquals(vn.z, 3.0);
	}
	@Test
	public void testParseFace() {
		String line = "f 1//1 2//1 4//1";
		Model.Face Mf = OBJLoader.parseFace(true, line);
		assertEquals(Mf.hasNormals(), true);
		Model.Face Mf2 = OBJLoader.parseFace(false, line);
		assertEquals(Mf2.hasNormals(), false);
	}
	@Test
	public void testLoadModel() {
		String dir = System.getProperty("user.dir") + "\\bin\\model\\tests\\";
		// With faces
		try {
			File newFile = new File(dir + "testLoadModel_withFaces.obj");
			Model m = OBJLoader.loadModel(newFile);
			File newFile2 = new File(dir + "thisShouldNotWork.txt");
			Model m2 = OBJLoader.loadModel(newFile2);
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Without faces
		try {
			File newFile = new File(dir + "testLoadModel_withoutFaces.obj");
			Model m = OBJLoader.loadModel(newFile);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	@Test
	public void testTexturedModel() {
		String dir = System.getProperty("user.dir") + "\\bin\\model\\tests\\";
		// With Texture coordinates
		try {
			File newFile = new File(dir + "testLoadTexturedModel_withTC.obj");
			Model m = OBJLoader.loadTexturedModel(newFile);
			File newFile2 = new File(dir + "thisShouldNotWork.txt");
			Model m2 = OBJLoader.loadTexturedModel(newFile2);
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Without Texture coordinats and normals
		try {
			File newFile = new File(dir + "testLoadTexturedModel_withoutTC.obj");
			Model m = OBJLoader.loadTexturedModel(newFile);
			File newFile2 = new File(dir + "thisShouldNotWork.txt");
			Model m2 = OBJLoader.loadTexturedModel(newFile2);
		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}