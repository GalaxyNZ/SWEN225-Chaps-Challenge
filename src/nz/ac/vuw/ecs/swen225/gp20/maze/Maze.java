package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;
import java.util.Scanner;

/*
 * The main class behind the gameplay, this constructs a board and is responsible for validating and executing a Players inputs from the GUI or keyboard.
 * TODO: Remove all instances of static variables to allow multiple Mazes to be created.
 */

public class Maze {
	
	/*
	 * Test String for making a board from an input String.
	 * TODO: Application implemented with other classes, main changed to Maze constructor so multiple levels can be implemented.
	 * Coords are stored as X,Y
	 */
	private static String mapTester =
			"17|16|SAMPLE TILE INFO|11|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|"
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
	public String password;
	private static Player player;
	private static boolean endGameCondition = false;
	
	/*
	 * Temporary Main for testing purposes.
	 * TODO: Change to constructor method once testing is complete, introduce input of string for this maps password. Introduce input of string for map details. 
	 */
	
	public static void main(String[] args) {
		board = new Board(mapTester);
		player = new Player(board.findPlayer(), board.getChips());
		gameplayLoop();
	}
	
	/*
	 * The main gameplay loop, this repeats until end game condition is met.
	 * TODO: Finish implementation of movement and checks.
	 */
	
	public static void gameplayLoop() {
		printGame();
		while(!endGameCondition) {
			boolean validMove = false;
			while(!validMove) {
				Scanner s = new Scanner(System.in);
				System.out.println("ENTER MOVE (W,A,S,D)");
				String input = s.next();
				switch(input.toLowerCase()){
				case "w":
					validMove = checkValidMove(new Point(player.getLocation().x, player.getLocation().y+1));
					if(!validMove) {
						System.out.println("Movement in this direction is obstructed");
					}
				case "a":
					validMove = checkValidMove(new Point(player.getLocation().x-1, player.getLocation().y));
					if(!validMove) {
						System.out.println("Movement in this direction is obstructed");
					}
				case "s":
					validMove = checkValidMove(new Point(player.getLocation().x, player.getLocation().y-1));
					if(!validMove) {
						System.out.println("Movement in this direction is obstructed");
					}
				case "d":
					validMove = checkValidMove(new Point(player.getLocation().x+1, player.getLocation().y));
					if(!validMove) {
						System.out.println("Movement in this direction is obstructed");
					}
				default:
					if(!validMove) {
						System.out.println("You typed the following invalid input: \"" + input + "\"");
					}
				}
			}
			
		}
		
		System.out.println("DONE");
	}
	
	/*
	 * Checks if the Player inputed move is valid and executes it.
	 * TODO: Finish method, implement FreeTile checks.
	 */
	
	public static boolean checkValidMove(Point newLocation) {
		int oldX = player.getLocation().x;
		int oldY = player.getLocation().y;
		Tile oldLocation = board.boardMap[oldY][oldX];
		int x = newLocation.x;
		int y = newLocation.y;
		if(x >= board.xSize || y >= board.ySize) {
			return false;
		}
		Tile toBeMovedTo = board.boardMap[y][x];
		if(toBeMovedTo instanceof WallTile) {
			return false;
		}
		else if(toBeMovedTo instanceof InfoTile) {
			System.out.println(((InfoTile) toBeMovedTo).getInfo());
			toBeMovedTo.addItem(oldLocation.addItem(null));
			return true;
		}
		else if(toBeMovedTo instanceof ExitTile) {
			endGameCondition = true;
			return true;
		}
		else {
			if(toBeMovedTo.getItem() instanceof ExitLockItem) {
				
			}
			else if(toBeMovedTo.getItem() instanceof LockedDoorItem) {
				
			}
		}
		
		return false;
	}
	
	private static void printGame() {
		System.out.println("GAME MAP PRINT GOES HERE");
	}
}
