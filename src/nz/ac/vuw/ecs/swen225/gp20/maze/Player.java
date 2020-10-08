package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;
import java.util.ArrayList;

/*
 * This class represents the Player of the game, a new Player is created for every Map. 
 */

public class Player {
	private ArrayList<TreasureItem> treasure = new ArrayList<TreasureItem>();
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private Point location;
	private int totalChips;
	
	/*
	 * Constructor method, establishes players starting location and the total number of treasure the need to unlock ExitLockItem.
	 */
	
	public Player(Point startingLocation, int totalChips) {
		location = startingLocation;
		this.totalChips = totalChips;
	}
	
	/*
	 * Returns the number of chips player has collected. Called for GUI printing.
	 */
	
	public int getTreasure() {
		return treasure.size();
	}
	
	/*
	 * Updates player location when a valid move is made in Maze.gameplayLoop().
	 */
	
	public void move(Point newLocation) {
		 location = newLocation;
	}
	
	/*
	 * Returns players current location.
	 */
	
	public Point getLocation() {
		return location;
	}
	
	/*
	 * Returns the whole inventory of the player.
	 */
	
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	
	/*
	 * Returns true if player has a key that matches incoming door color, then increments the keys number of uses.
	 */
	
	public boolean getKey(String doorColor) {
		for(Item i : inventory) {
			if(i instanceof KeyItem) {
				if(i.getColor() == doorColor) {
					KeyItem key = (KeyItem)i;
					if(key.increment()) { //If this key has been used it maximum amount of times remove it from the players inventory.
						inventory.remove(i);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * Returns a boolean based off an item input. Will decline input if the players inventory is full and will not add null items to the players inventory.
	 * Handles treasure items into a different list.
	 */
	
	public boolean addToInv(Item item) {
		if(inventory.size() < 8) {
			if(item != null) {
				if(item instanceof KeyItem) {
					inventory.add(item);
				}
				else if(item instanceof TreasureItem) {
					treasure.add((TreasureItem)item);
				}
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Returns true is player has enough chips to unlock ExitLockItem, false if player needs more chips.
	 */
	
	public boolean treasureCheck() {
		if(treasure.size() == totalChips) {
			return true;
		}
		else {
			return false;
		}
	}
}
