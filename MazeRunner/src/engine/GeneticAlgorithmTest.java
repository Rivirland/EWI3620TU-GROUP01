package engine;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GeneticAlgorithmTest {
	
	Maze maze1 = new Maze("basiclevel1", 1, 0, 0, 0);
	Maze maze2 = new Maze("basiclevel3", 1, 0, 0, 0);
	Maze maze3 = new Maze("basiclevel2", 1, 0, 0, 0);
	Maze maze4 = new Maze("jumpworldplatform", 1, 0, 0, 0);

	@Test
	public void constructorTestNotAllWithPortals() {
		ArrayList<Maze> mazelistnotallwithportals = new ArrayList<Maze>();
		mazelistnotallwithportals.add(0, maze1);
		mazelistnotallwithportals.add(1, maze2);
		mazelistnotallwithportals.add(2, maze3);
		mazelistnotallwithportals.add(3, maze4);
	GeneticAlgorithm algorithm = new GeneticAlgorithm(mazelistnotallwithportals, 2);
	
	ArrayList<Maze> testmazelist = new ArrayList<Maze>();
	testmazelist.add(0, maze1);
	testmazelist.add(1, maze2);
	testmazelist.add(2, maze3);
	
	assertEquals(algorithm.getMazes(), testmazelist);
	}

	@Test
	public void solveTest() {
		ArrayList<Maze> mazelistnotallwithportals = new ArrayList<Maze>();
		mazelistnotallwithportals.add(0, maze1);
		mazelistnotallwithportals.add(1, maze2);
		mazelistnotallwithportals.add(2, maze3);
		mazelistnotallwithportals.add(3, maze4);
		GeneticAlgorithm algorithm = new GeneticAlgorithm(mazelistnotallwithportals, 2);
		int i[] = algorithm.solve();
		
		int[] parents = {1,3,2};
		//assertTrue(algorithm.distances[0][0] == 0.0);
		
		for (int j = 0; j<3;j++){
			assertEquals(i[j], parents[j]);
		}
	}
	
	@Test
	public void solveTestParents2() {
		ArrayList<Maze> mazelistnotallwithportals = new ArrayList<Maze>();
		mazelistnotallwithportals.add(0, maze1);
		mazelistnotallwithportals.add(1, maze2);
		mazelistnotallwithportals.add(2, maze3);
		mazelistnotallwithportals.add(3, maze4);
		GeneticAlgorithm algorithm = new GeneticAlgorithm(mazelistnotallwithportals, 2);
		int i[] = algorithm.solve();
		
		int[] parents2 = {1,3,2};
		//assertTrue(algorithm.distances[0][0] == 0.0);
		System.out.println(parents2[0]);
		System.out.println(parents2[1]);
		System.out.println(parents2[2]);
		for (int j = 0; j<3;j++){
			assertEquals(i[j], parents2[j]);
		}
	}
	
	@Test
	public void getNrOfLevels() {

			ArrayList<Maze> mazelistnotallwithportals = new ArrayList<Maze>();
			mazelistnotallwithportals.add(0, maze1);
			mazelistnotallwithportals.add(1, maze2);
			mazelistnotallwithportals.add(2, maze3);
			mazelistnotallwithportals.add(3, maze4);
			GeneticAlgorithm algorithm = new GeneticAlgorithm(mazelistnotallwithportals, 2);
			int nroflevels = algorithm.getNrOfLevels();
			assertEquals(nroflevels,3);
	}
		

	
}
