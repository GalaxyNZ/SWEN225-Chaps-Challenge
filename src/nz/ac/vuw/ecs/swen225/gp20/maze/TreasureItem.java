package nz.ac.vuw.ecs.swen225.gp20.maze;

/*
 * Treasure item is just a shell that is only unique to serve instanceof calls in order to populate the players total amount of treasure for ExitLock checks.
 */

public class TreasureItem extends Item{
	
	/*
	 * No constructor set up required, just a print string.
	 */
	
	public TreasureItem() {
		
	}
	
	/*
	 * Returns the print string for printBoard() calls.
	 */
	
	public String getChar() {
		return "|T|";
	}
}
