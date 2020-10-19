package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;

/*
 * Skeleton Class for Tile system, establishes all Tile methods to keep coherence. 
 */

public class Tile {
	private String tileChar = "NUL";
	private Point boardLocation;
	private boolean isObstacle;
	private Item containedItem;	
	
	/*
	 * Tells this Tile where on the Board array it is.
	 */
	
	public void setBoardLocation(Point location) {
		boardLocation = location;
	}
	
	/*
	 * Returns board array location of this Tile.
	 */
	
	public Point getBoardLocation() {
		return boardLocation;
	}
	
	/*
	 * Updates Tile to either contain an Item or null. Returns the old item in-case it needs to be added to Chap's inventory.
	 */
	
	public Item addItem(Item newItem) {
		Item oldItem = containedItem;
		containedItem = newItem;
		return oldItem;
	}
	
	/*
	 * Returns the Item that this tile is holding.
	 */
	
	public Item getItem() { 
		return containedItem;
	}
	
	/*
	 * Returns a boolean based on whether or not this tile can be passed through by the player.
	 */
	
	public Boolean getObstacle() {
		return isObstacle;
	}
	
	/*
	 * Allows the move through boolean of this tile to be changed (If Player has all the keys or treasure).
	 * This is only valid for FreeTile, but is introduced here to make error tracking easier.
	 */
	
	public void changeObstacle(Boolean newState) {
		isObstacle = newState;
	}
	
	/*
	 * Returns a representative Char for this tile as determined by the type of Tile.
	 * Used for text based version of the game (Testing implementation only).
	 */
	
	public String toString() {
		return tileChar;
	}
}
