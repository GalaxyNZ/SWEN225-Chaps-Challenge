package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;

public class WallTile extends Tile{
	private String tileChar;
	private Point boardLocation;
	private boolean isObstacle = true;
	private Item containedItem;	
	
	/*
	 * Constructor method, requires the standard Point on the map. All parameters are set on creation, this Tile can never be entered or contain an Item.
	 */
	
	public WallTile(Point location) {
		boardLocation = location;
		tileChar = "#";
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
		return null;
	}
	
	/*
	 * Returns the Item that this tile is holding.
	 * TODO: Currently only use is for GUI printing, may change to return Item.toString OR item Image reference for GUI printing.
	 */
	
	public Item getItem() { 
		return null;
	}
	
	/*
	 * Returns a boolean based on whether or not this tile can be passed through by the player.
	 */
	
	public Boolean getObstacle() {
		return true;
	}
	
	/*
	 * Returns a representative Char for this tile as determined by the type of Tile.
	 * Used for text based version of the game (Testing implementation only).
	 */
	
	public String toString() {
		return tileChar;
	}
}
