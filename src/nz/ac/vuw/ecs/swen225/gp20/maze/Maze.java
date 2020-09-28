package nz.ac.vuw.ecs.swen225.gp20.maze;

/*
 * The main class behind the gameplay, this constructs a board and is responsible for validating and executing a Players inputs from the GUI or keyboard.
 */

public class Maze {
	
	/*
	 * Test String for making a board from an input String.
	 * TODO: Application implemented with other classes, main changed to Maze constructor so multiple levels can be implemented.
	 * Coords are stored as X,Y
	 */
	private static String mapTester = "17|16|SAMPLE TILE INFO|11|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|"
			+ "F|F|F|W|W|W|W|W|F|W|W|W|W|W|F|F|F|"
			+ "F|F|F|W|F|F|F|W|W|W|F|F|F|W|F|F|F|"
			+ "F|F|F|W|F|T|F|W|EXT|W|F|T|F|W|F|F|F|"
			+ "F|W|W|W|W|W|GKD|W|EXTLCK|W|GKD|W|W|W|W|W|F|"
			+ "F|W|F|YK|F|BKD|F|F|F|F|F|RKD|F|YK|F|W|F|"
			+ "F|W|F|T|F|W|BK|F|I|F|RK|W|F|T|F|W|F|"
			+ "F|W|W|W|W|W|T|F|CHAP|F|T|W|W|W|W|W|F|"
			+ "F|W|F|T|F|W|BK|F|F|F|RK|W|F|T|F|W|F|"
			+ "F|W|F|F|F|RKD|F|F|T|F|F|BKD|F|F|F|W|F|"
			+ "F|W|W|W|W|W|W|YKD|W|YKD|W|W|W|W|W|W|F|"
			+ "F|F|F|F|F|W|F|F|W|F|F|W|F|F|F|F|F|"
			+ "F|F|F|F|F|W|F|T|W|T|F|W|F|F|F|F|F|"
			+ "F|F|F|F|F|W|F|F|W|GK|F|W|F|F|F|F|F|"
			+ "F|F|F|F|F|W|W|W|W|W|W|W|F|F|F|F|F|"
			+ "F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|"
			+ "BK1|YK1|RK1|GK2";
	
	private static Board board;
	
	/*
	 * Temporary Main for testing purposes.
	 */
	
	public static void main(String[] args) {
		board = new Board(mapTester);
		gameplayLoop();
	}
	
	/*
	 * Executes move if valid then returns true. If move invalid returns false.
	 */
	
	public boolean makeAMove(String direction) {
		return false;
	}
	
	public static void gameplayLoop() {
		
		System.out.println("DONE");
	}
}
