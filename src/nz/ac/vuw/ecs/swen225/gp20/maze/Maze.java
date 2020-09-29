package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		//printGame();
		System.out.println(player.getLocation());
		gameplayLoop();
	}
	
	/*
	 * The main gameplay loop, this repeats until end game condition is met.
	 * TODO: Finish implementation of movement and checks.
	 */
	
	public static void gameplayLoop() {
		int count = 0;
		while(!endGameCondition) {
			printGame();
			boolean validMove = false;
			while(!validMove) {
				//System.out.println("ENTER MOVE (W,A,S,D)");
				//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				//String input= "";
				//try {
				//	input = reader.readLine();
				//} catch (IOException e) {
				//	e.printStackTrace();
				//}
				//switch(input.toLowerCase()){
				switch(3){
				case 1:
					count++;
					if(count > 1) {
						endGameCondition = true;
					}
					validMove = checkValidMove(new Point(player.getLocation().x, player.getLocation().y-1));
					if(!validMove) {
						System.out.println("Movement in this direction is obstructed");
					}
					break;
				case 2:
					count++;
					if(count > 1) {
						endGameCondition = true;
					}
					validMove = checkValidMove(new Point(player.getLocation().x-1, player.getLocation().y));
					if(!validMove) {
						System.out.println("Movement in this direction is obstructed");
					}
					break;
				case 3:
					count++;
					if(count > 1) {
						endGameCondition = true;
					}
					validMove = checkValidMove(new Point(player.getLocation().x, player.getLocation().y+1));
					if(!validMove) {
						System.out.println("Movement in this direction is obstructed");
					}
					break;
				case 4:
					count++;
					if(count > 1) {
						endGameCondition = true;
					}
					validMove = checkValidMove(new Point(player.getLocation().x+1, player.getLocation().y));
					if(!validMove) {
						System.out.println("Movement in this direction is obstructed");
					}
					break;
				default:
					if(!validMove) {
						//System.out.println("You typed the following invalid input: \"" + input + "\"");
					}
					break;
				}
			}
		}
		
		System.out.println("DONE");
	}
	
	/*
	 * Checks if the Player inputed move is valid and executes it.
	 */
	
	public static boolean checkValidMove(Point newLocation) {
		int oldX = player.getLocation().x;
		int oldY = player.getLocation().y;
		Tile oldLocation = board.boardMap[oldY][oldX];
		int x = newLocation.x;
		int y = newLocation.y;
		if(x >= board.xSize || y >= board.ySize) {
			System.out.println("OUT OF BOARD PARAMETERS");
			return false;
		}
		Tile toBeMovedTo = board.boardMap[y][x];
		System.out.println(toBeMovedTo);
		if(toBeMovedTo instanceof WallTile) {
			System.out.println("WALLTILE");
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
		else { //Only remaining option is FreeTile.			
			if(toBeMovedTo.getItem() instanceof ExitLockItem) {
				if(player.treasureCheck()) {
					toBeMovedTo.addItem(oldLocation.addItem(null));
					return true;
				}
				else {
					System.out.println("NOT ENOUGH TREASURE");
					return false;
				}
			}
			else if(toBeMovedTo.getItem() instanceof LockedDoorItem) {
				String doorColor = toBeMovedTo.getItem().getColor();
				if(player.keyCheck(doorColor)) {
					player.getKey(doorColor).increment();
					player.removeUsedKeys();
					toBeMovedTo.addItem(oldLocation.addItem(null));
					return true;
				}
				else {
					System.out.println("NEED KEY");
					return false;
				}
			}
			else if(toBeMovedTo.getItem() instanceof KeyItem) {
				if(!player.addToInv(toBeMovedTo.getItem())) {
					System.out.println("TOO MANY ITEMS");
					return false;
				}
				else {
					toBeMovedTo.addItem(oldLocation.addItem(null));
					return true;
				}
			}
			else { //Tile contains either null or treasure.
				if(toBeMovedTo.getItem() instanceof TreasureItem) {
					player.foundTreasure(new TreasureItem());
				}
				toBeMovedTo.addItem(oldLocation.addItem(null));
				return true;
			}
		}
	}
	
	private static void printGame() {
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < board.ySize; i++) {			
			for(int j = 0; j < board.xSize; j++) {
				output.append(board.boardMap[i][j].getPrintChar());
			}
			output.append("\n");
		}
		System.out.println(output.toString());
	}
}
