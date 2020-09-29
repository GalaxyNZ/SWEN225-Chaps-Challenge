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
	
	public Player(Point startingLocation, int totalChips) {
		location = startingLocation;
		this.totalChips = totalChips;
	}
	
	public void move(Point newLocation) {
		 location = newLocation;
	}
	
	public Point getLocation() {
		return location;
	}
	
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
	
	public boolean addToInv(Item item) {
		if(inventory.size() < 8) {
			inventory.add(item);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void foundTreasure(TreasureItem newChip) {
		treasure.add(newChip);
	}
	
	public boolean treasureCheck() {
		if(treasure.size() == totalChips) {
			return true;
		}
		else {
			return false;
		}
	}
}
