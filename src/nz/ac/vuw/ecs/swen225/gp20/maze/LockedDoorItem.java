package nz.ac.vuw.ecs.swen225.gp20.maze;

/*
 * This item represents a locked door that requires a key of the same color as it in order to be passed. Once key is used on the door the door disappears from the game.
 */

public class LockedDoorItem extends Item{
	
	private String color;
	
	/*
	 * Basic constructor, only needs the doors color as an input.
	 */
	
	public LockedDoorItem(String color) {
		this.color = color;
	}
	
	/*
	 * Returns this doors color.
	 */
	
	public String getColor() {
		return color;
	}
	
	/*
	 * Returns the print string for printBoard() calls.
	 */

	public String getChar() {
		return "LKD";
	}
	public String toString() {
		return color.substring(0, 1).toUpperCase();
	}
	
}
