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
