package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;

public class FreeTile extends Tile{
	private String tileChar;
	private Point boardLocation;
	private boolean isObstacle = false;
	private Item containedItem;	
	
	/*
	 * Constructor method, requires the standard Point on the map as well as any Items that this Tile contains, default Item will be null.
	 */
	
	public FreeTile(Point location, Item item) {
		boardLocation = location;
		tileChar = "_";
		isObstacle = false;
		containedItem = item;
	}
	
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
	 * TODO: Possible change to Item system? Avoid returning null values and work off of an Item Boolean call?
	 */
	
	public Item addItem(Item newItem) {
		Item oldItem = containedItem;
		containedItem = newItem;
		return oldItem;
	}
	
	/*
	 * Returns the Item that this tile is holding.
	 * TODO: Currently only use is for GUI printing, may change to return Item.toString OR item Image reference for GUI printing.
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
	 * TODO: This method only applies to FreeTile, maybe make it limited to that Tile type? Re-implement with Door check system.
	 */
	
	public void changeObstacle(Boolean newState) {
		isObstacle = newState;
	}
	
	/*
	 * Returns a representative Char for this tile as determined by the type of Tile.
	 * Used for text based version of the game (Testing implementation only).
	 */
	
	public String toString() {
		if(containedItem == null) {
			return tileChar;
		}
		return containedItem.toString();
	}
}
