package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;

public class InfoTile extends Tile{
	private String tileChar;
	private Point boardLocation;
	private boolean isObstacle = true;
	private Item containedItem;
	
	/*
	 * Constructor method, requires the standard Point on the map as well as the string of information that this Tile shows when it contains the Player.
	 */
	
	public InfoTile(Point location) {
		boardLocation = location;
		tileChar = "I";
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
	 * TODO: Work with GUI so that this method can make a GUI call to print out the info when given a string(this classes 'info').
	 */
	
	public Item addItem(Item newItem) {
		Item oldItem = containedItem;
		containedItem = newItem;
		return oldItem;
	}
	
	/*
	 * Returns the Item that this tile is holding.
	 * TODO: Currently only use is for GUI printing, may change to return Item.toString OR item Image reference for GUI printing.
	 * TODO: Update this to work with any proposed new item return system as mentioned in addItem comments.
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
	 * TODO: This method only applies to FreeTile, maybe make it limited to that Tile type?
	 */
	
	public void changeObstacle(Boolean newState) {
	}
	
	/*
	 * Returns a representative Char for this tile as determined by the type of Tile.
	 * Used for text based version of the game (Testing implementation only).
	 */
	
	public String toString() {
		if(containedItem instanceof Chap) {
			return "X"; // was CHP
		}
		return tileChar;
	}
}
