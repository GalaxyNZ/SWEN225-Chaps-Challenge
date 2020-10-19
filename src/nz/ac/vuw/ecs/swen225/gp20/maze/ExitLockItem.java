package nz.ac.vuw.ecs.swen225.gp20.maze;

/*
 * Basic item that represents the door leading the the level exit. This is just a shell for instanceof calls.
 */

public class ExitLockItem extends Item{
	
	/*
	 * Constructor method, input currently unused as for now this Item is a shell.
	 */
	
	public ExitLockItem(int numOfChips) {
		
	}
	
	/*
	 * Returns the print string for printBoard() calls.
	 */

	public String getChar() {
		return "ELK";
	}
	public String toString() {
		return "E";
	}
	
}
