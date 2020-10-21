package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.internal.$Gson$Preconditions;
import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;

/*
 * The main class behind the gameplay, this constructs a board and is responsible for validating and executing a Players inputs from the GUI or keyboard.
 */

public class Maze {
	
	private Board board;
	public String password;
	private Player player;
	private boolean endGameState = false;
	private float timeElapsed = 0;
	private int levelNumber = 0;
	
	/*
	 * Constructor class for a maze (1 Level of the game).
	 * Has an input of a JSON map.
	 * Creates player, board, sets level number and add saved items to players inv if the map provided is for a saved game.
	 */	
	
	public Maze (Map<?,?> boardMap) {
		board = new Board(boardMap);
		player = new Player(board.findPlayer(), board.getChips());
		levelNumber = Integer.parseInt(boardMap.get("level").toString());
		for(Item i : board.getLoadedInv()) { //Runs 0 times for a new game, will construct a players inventory from a loaded inventory.
			player.addToInv(i);
		}
	}
	
	/*
	 * Returns the number of this level.
	 */
	
	public int getLevel() {
		return levelNumber;
	}
	
	
	/*
	 * Executes movements and checks validity based off of input. Designed to be called by both Main and Monkey Testing.
	 * Returns true if movement is valid or false if movement is invalid.
	 */
	
	public boolean executeMove(GraphicalUserInterface.Direction movement) {
		switch(movement) {
		case UP:
			if(validMove(player.getLocation(), new Point(player.getLocation().x, player.getLocation().y-1))) { //Attempts move up 1 Tile.
				return true;
			}
			else {
				return false;
			}
		case DOWN:
			if(validMove(player.getLocation(), new Point(player.getLocation().x, player.getLocation().y+1))) { //Attempts move down 1 Tile.
				return true;
			}
			else {
				return false;
			}
		case LEFT:
			if(validMove(player.getLocation(), new Point(player.getLocation().x-1, player.getLocation().y))) { //Attempts move left 1 Tile.
				return true;
			}
			else {
				return false;
			}
		case RIGHT:
			if(validMove(player.getLocation(), new Point(player.getLocation().x+1, player.getLocation().y))) { //Attempts move right 1 Tile.
				return true;
			}
			else {
				return false;
			}
		default:
			return false;
		}
	}

	/*
	 * Takes an input of the players current location and the location they wish to move to.
	 * Responds with a boolean that either validates or invalidates the move based on the requested Tile being an obstacle of some kind.
	 */
	
	private boolean validMove(Point oldLocation, Point newLocation) {
		int newX = newLocation.x;
		int newY = newLocation.y;
		if(newLocation.x < 0 || newLocation.x >= board.xSize || newLocation.y < 0 || newLocation.y >= board.ySize) { //Out of bounds check.
			return false;
		}
		if(board.boardMap[newY][newX] instanceof WallTile) { //Walls are always an obstacle.
			return false;
		}
		else if(board.boardMap[newY][newX] instanceof InfoTile) { //InfoTiles are never an obstacle and can only contain Chap or null Item.
			board.boardMap[newY][newX].addItem(board.boardMap[oldLocation.y][oldLocation.x].addItem(null));
			player.move(newLocation);
			return true;
		}
		else if(board.boardMap[newY][newX] instanceof ExitTile) { //ExitTile is never an obstacle and requires no player movement call as this Tile ends the map.
			endGameState = true;
			return true;
		}
		else if(board.boardMap[newY][newX] instanceof FreeTile) { //FreeTiles are usually not an obstacle, but there are 3 conditions where they are.
			if(board.boardMap[newY][newX].getItem() instanceof LockedDoorItem) { //FreeTile obstacle if player does not have a key for the door Item in the Tile.
				LockedDoorItem thisDoor = (LockedDoorItem)board.boardMap[newY][newX].getItem();
				if (player.getKey(thisDoor.getColor())) {
					board.boardMap[newY][newX].addItem(board.boardMap[oldLocation.y][oldLocation.x].addItem(null));
					player.move(newLocation);
					return true;
				}
				return false;
			}
			else if(board.boardMap[newY][newX].getItem() instanceof ExitLockItem) { //FreeTile obstacle if player does not have enough treasure for the ExitLockDoor Item in the Tile.
				if(chipsRemaining() <= 0) {
					board.boardMap[newY][newX].addItem(board.boardMap[oldLocation.y][oldLocation.x].addItem(null));
					player.move(newLocation);
					return true;
				}
				else {
					return false;
				}
			}
			else {
				if(!player.addToInv(board.boardMap[newY][newX].getItem())) { //FreeTile obstacle if it contains a non treasure item and player inventory is full, otherwise adds item to player inv.
					return false;
				} //None of FreeTile == obstacle condition met, this move is therefore valid.
				board.boardMap[newY][newX].addItem(board.boardMap[oldLocation.y][oldLocation.x].addItem(null)); 
				player.move(newLocation);
				return true;
			}
		}
		else { //Should not be reachable, as the player should be contained within map by obstacle tiles and therefore cannot reach null location of the board.
			return false;
		}
	}
	
	/*
	 * Calls a Board method that moves all bugs and returns true if the bug killed the player.
	 */
	
	public boolean moveBugs() {		
		return board.moveBugs();
	}
	
	/*
	 * Returns a map of all the different bugs movesets.
	 */
	
	public ArrayList<HashMap<Integer, ArrayList<String>>> getBugMoves() {
        return board.getBugMoves();
    }
	
	/*
	 * Calls a method in board that returns the total number of monsters.
	 */

    public int getNumMonsters() {
        return board.getNumMonsters();
    }
	
	/*
	 * Returns true if the level has been completed, else returns false.
	 */
	
	public boolean levelWonChecker() {
		return endGameState;
	}
	
	/*
	 * Returns the Tile at location for GUI printing calls.
	 */

	public Tile getBoardTile(Point location) {
		return board.boardMap[location.y][location.x];
	}
	
	/*
	 * Returns the players location on the board for centering the GUI.
	 */
	
	public Point getPlayerLocation() {
		return player.getLocation();
	}

	/*
	 * return the boards
	 */

	public Board getBoard() {return board;}

	/*
	 * Returns the chips the player has
	 */

	public int getPlayerChips() { return player.getTreasure(); }
	
	/*
	 * Returns ArrayList of all items in the players inventory for GUI printing.
	 */
	
	public ArrayList<Item> getPlayerInv(){
		return player.getInventory();
	}
	
	/*
	 * Returns total number of chips for GUI printing.
	 */
	
	public int chipsTotal() {
		return board.getTotalChips();
	}
	
	/*
	 * Returns chips remaining on the board for exitLock access and GUI printing.
	 */
	
	public int chipsRemaining() {
		return board.getTotalChips() - player.getTreasure();
	}
	
	/*
	 * Returns the dimensions of the board for GUI printing.
	 */
	
	public Point getBoardSize() {
		return new Point(board.getWidth(), board.getHeight());
	}
	
	/*
	 * Returns a string representative of the board for Monkey Test calls.
	 */
	
	public String toString() {
		return board.toString();
	}
	
	/*
	 * Sets the amount of time that has taken place.
	 */
	
	public void setTimeElapsed(float time) {
		timeElapsed = time;
	}
	
	/*
	 * Returns the amount of time elapsed in this level.
	 */
	
	public float getTimeElapsed() {
		return timeElapsed;
	}


}
