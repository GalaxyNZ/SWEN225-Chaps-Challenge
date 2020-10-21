package nz.ac.vuw.ecs.swen225.gp20.maze;

/*
 * Basic Item overlay.
 */

public class Item {
	
	/*
	 * Base Item should not be reachable, feeds printMap() an invalid indicator.
	 */
	
	public String getChar() {
		return "INV";
	}
	
	/*
	 * Base Item contains no color.
	 */
	
	public String getColor() {
		return "VOID COLOR";
	}
	
	/*
	 * MonsterItem only call.
	 */

	public String getNextMove() {
		// TODO Auto-generated method stub
		return null;
	}
}
