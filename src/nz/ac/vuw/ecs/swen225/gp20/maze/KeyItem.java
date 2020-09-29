package nz.ac.vuw.ecs.swen225.gp20.maze;

/*
 * Item represents the different colors of keys on the map, these keys have a color that associates them with a particular door and an int that defines how many doors each key of this color can open.
 */

public class KeyItem extends Item{
	
		public String color;
		public int used = 0;
		public int maxUses;
		
		/*
		 * Constructor method, establishes this keys color and how many doors of that color it can open before being deleted from player inventory.
		 * TODO: Re-implement key and door check system.
		 */
		
		public KeyItem(String color, int maxUses) {
			this.color = color;
			this.maxUses = maxUses;
		}
		
		/*
		 * Increments the number of times this key has been used to unlock a door.
		 */
		
		public void increment() {
			used++;
		}
		
		/*
		 * Returns the print string for printBoard() calls.
		 */
		
		public String getChar() {
			return "KCL";
		}
		
	}
