package nz.ac.vuw.ecs.swen225.gp20.maze;

/*
 * Item represents the different colors of keys on the map, these keys have a color that associates them with a particular door and an int that defines how many doors each key of this color can open.
 */

public class KeyItem extends Item{
	
		public String color;
		private int used = 0;
		private int maxUses;
		
		/*
		 * Constructor method, establishes this keys color and how many doors of that color it can open before being deleted from player inventory.
		 */
		
		public KeyItem(String color, int maxUses) {
			this.color = color;
			this.maxUses = maxUses;
		}
		
		/*
		 * Increments the number of times this key has been used to unlock a door.
		 */
		
		public boolean increment() {
			used++;
			if(used >= maxUses) {
				return true;
			}
			else {
				return false;
			}
		}

	public String getColor() {
		return this.color;
	}
		
		/*
		 * Returns the print string for printBoard() calls.
		 */

	public String getChar() {
		return "KCL";
	}
	public String toString() {
		return color.substring(0, 1).toLowerCase();
	}
		
	}
