package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
	private static String mapTester = "17|16|SAMPLE TILE INFO|11|SETBK|1|SETYK|1|SETRK|1|SETGK|2|"
			+ "F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|"
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
			+ "F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|";
	
	private Board board;
	public String password;
	private Player player;
	private boolean endGameState = false;
	private String[] testCases = new String[]{ "w", "a", "a", "s", "s", "d"}; //Manually programmable for simple testing.
	
	/*
	 * Temporary Main for testing purposes.
	 * TODO: Change to constructor method once testing is complete, introduce input of string for this maps password. Introduce input of string for map details. 
	 */
	
	/*public static void main(String[] args) { //THIS MAIN IS FOR TESTING ONLY, WHEN IMPLEMENTING USE CONSTRUCTORS BELOW
		board = new Board(mapTester);
		//printGame();
		player = new Player(board.findPlayer(), board.getChips());
		gameplayLoop();
	}*/
	
	
	
	/*
	 * Constructor class for a maze (1 Level of the game).
	 */
	
	public Maze(String mapString) {
		board = new Board(mapString);
		player = new Player(board.findPlayer(), board.getChips());
		gameplayLoop();
	}
	
	/*
	 * Constructor class for running Monkey Tests, takes an additional input of a list of moves to be executed.
	 */
	
	public Maze(String mapString, String[] moveList) {
		board = new Board(mapString);
		player = new Player(board.findPlayer(), board.getChips());
		testCases = moveList;
		gameplayLoop();
	}
	
	/*
	 * The main gameplay loop, this repeats until end game condition is met.
	 * Requests a move from getMove, then applies it to a switch case to attempt the move.
	 */
	
	public void gameplayLoop() {
		int count = 0;
		while(!endGameState) {
			boolean valid = false;
			while(!valid) {
				String move = getMove(count); //TESTING MOVE INPUT
				System.out.println(move); //TESTING, ALLOWS COMPARISON TO MOVE REQUEST VS ACTUAL BOARD IMPLEMENTATION
				switch(move) {
				case "w":
					if(validMove(player.getLocation(), new Point(player.getLocation().x, player.getLocation().y-1))) { //Attempts move up 1 Tile.
						valid = true;
					}
					else {
						System.out.println("INPUT MOVE " + move + " IS INVALID"); //Called is there is an obstruction in the Tile to be moved to.
					}
					break;
				case "a":
					if(validMove(player.getLocation(), new Point(player.getLocation().x-1, player.getLocation().y))) { //Attempts move left 1 Tile.
						valid = true;
					}
					else {
						System.out.println("INPUT MOVE " + move + " IS INVALID");
					}
					break;
				case "s":
					if(validMove(player.getLocation(), new Point(player.getLocation().x, player.getLocation().y+1))) { //Attempts move down 1 Tile.
						valid = true;
					}
					else {
						System.out.println("INPUT MOVE " + move + " IS INVALID");
					}
					break;
				case "d":
					if(validMove(player.getLocation(), new Point(player.getLocation().x+1, player.getLocation().y))) { //Attempts move right 1 Tile.
						valid = true;
					}
					else {
						System.out.println("INPUT MOVE " + move + " IS INVALID");
					}
					//endGameState = true; //TESTING APPLICATION
					break;
				default:

				}
				printGame();
			}
			count++; //TESTING, INCREMENTS SELECTED MOVE FROM getMove METHOD
		}
		System.out.println("Player Inventory = " + player.getInventory()); //TESTING CALL
		System.out.println("Player Chip Count = " + player.getTreasure()); //TESTING CALL
		System.out.println("GAME ENDED"); //TESTING CALL
	}
	
	/*
	 * Testing method for doing text based games, pre program the array with what moves are desired.
	 * TODO: Change implementation so game does not auto end when d is the given output
	 */
	
	public String getMove(int testState) {
		if(testState >= testCases.length-1) { //Catch so that request for next move does not go out of bounds.
			endGameState = true;
		}
		return testCases[testState];
	}
	
	/*
	 * Takes an input of the players current location and the location they wish to move to.
	 * Responds with a boolean that either validates or invalidates the move based on the requested Tile being an obstacle of some kind.
	 */
	
	private boolean validMove(Point oldLocation, Point newLocation) {
		int newX = newLocation.x;
		int newY = newLocation.y;
		if(board.boardMap[newY][newX] instanceof WallTile) { //Walls are always an obstacle.
			return false;
		}
		else if(board.boardMap[newY][newX] instanceof InfoTile) { //InfoTiles are never an obstacle and can only contain Chap or null Item.
			System.out.println(board.getInfo());
			board.boardMap[newY][newX].addItem(board.boardMap[oldLocation.y][oldLocation.x].addItem(null));
			player.move(newLocation);
			return true;
		}
		else if(board.boardMap[newY][newX] instanceof ExitTile) { //ExitTile is never an obstacle and requires no player movement call as this Tile ends the map.
			endGameState = true;
			return true;
		}
		else if(board.boardMap[newY][newX] instanceof FreeTile) { //FreeTiles are usually not an obstacle, but there are 3 conditions where they are.
			if(board.boardMap[newY][newX].getItem() instanceof LockedDoorItem) { //FreeTile obstacle if player does not have a key for the door Item in the Tile. TODO: Implement key check.
				return false;
			}
			else if(board.boardMap[newY][newX].getItem() instanceof ExitLockItem) { //FreeTile obstacle if player does not have enough treasure for the ExitLockDoor Item in the Tile. TODO: Implement treasure check.
				return false;
			}
			else {
				if(!player.addToInv(board.boardMap[newY][newX].getItem())) { //FreeTile obstacle if it contains a non treasure item and player inventory is full.
					return false;
				} //None of FreeTile == obstacle condition met, this move is therefore valid.
				board.boardMap[newY][newX].addItem(board.boardMap[oldLocation.y][oldLocation.x].addItem(null)); 
				player.move(newLocation);
				return true;
			}
		}
		else { //Should not be reachable, as the player should be contained within map by obstacle tiles and therefore cannot reach null location of the board.
			System.out.println("IMPOSSIBLE PARAMETER REACHED, PLEASE CONTACT SYSTEM ADMINISTRATOR");
			return false;
		}
	}
	
	/*
	 * Prints the board in it's current state for text based testing and implementation.
	 * TODO: Reconfigure to instead make a GUI update call.
	 */
	
	private void printGame() {
		StringBuilder output = new StringBuilder();
		for(int i = 0; i < board.ySize; i++) {			
			for(int j = 0; j < board.xSize; j++) {
				output.append(board.boardMap[i][j].toString());
			}
			output.append("\n");
		}
		System.out.println(output.toString());
	}

}
