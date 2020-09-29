package nz.ac.vuw.ecs.swen225.gp20.maze;

/*
 * Item represents where the player abstract object is contained on the board, only requires an output string to make it visible and an independent class to identify it by in instance checks.
 */

public class Chap extends Item{
	
	/*
	 * This object requires no info beyond is printing string, as it is a shell for the player object.
	 */
	
	public Chap() {
		
	}
	
	/*
	 * Returns the print string of this item to override the tile is is in's print string.
	 */
	
	public String getChar() {
		return "CHP";
	}
	
}
