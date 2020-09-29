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
	 * Returns the number of chips player has collected.
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
	 * Checks if player has a valid key for a give locked door.
	 * TODO: Implement valid key check.
	 */
	
	public boolean keyCheck(String colorOfDoor) {
		for(Item i : inventory) {
			if(i instanceof KeyItem) {
				if(i.getColor() == colorOfDoor) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * Removes any keys that have been used from players inventory.
	 * TODO: Re-implement this feature to run more coherently in the gameplayLoop.
	 */
	
	public void removeUsedKeys() {
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i) instanceof KeyItem) {
				KeyItem thisKey = (KeyItem)inventory.get(i);
				if(thisKey.used >= thisKey.maxUses) {
					inventory.remove(i);
					break;
				}
			}
		}
	}
	
	/*
	 * Returns the key associated with a given color.
	 * TODO: Re-implement key checks to be more coherent.
	 */
	
	public KeyItem getKey(String color) {
		for(Item i : inventory) {
			if(i instanceof KeyItem) {
				if(i.getColor() == color) {
					return (KeyItem) i;
				}
			}
		}
		return null;
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
			System.out.println("INVENTORY FULL");
			return false;
		}
	}
	
	/*
	 * Returns true is player has enough chips to unlock ExitLockItem, false if player needs more chips.
	 * TODO: Re-implement Door check systems to be more coherent.
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
