package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.ArrayList;

import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface.Direction;

public class MonsterItem extends Item{
	
	private ArrayList<String> moveset;
	private String thisChar;
	private int count = 0;

	public MonsterItem(ArrayList<String> moveset, int bugNumber) {
		this.moveset = moveset;
		thisChar = bugNumber + "";
	}
	
	/*
	 * Returns the next direction that a monster should move in as determined
	 * by its moveset. Returns to top of the list once a whole loop completed.
	 */
	
	public String getNextMove(){
		String returnValue = moveset.get(count);
		count++;
		if(count >= moveset.size()) {
			count = 0;
		}
		return returnValue;
	}
	
	public String getChar() {
		return thisChar;
	}
	
	/*
	 * Base Item contains no color.
	 */
	
	public String getColor() {
		return "VOID COLOR";
	}
	
	public String toString() {
		return thisChar;
	}
	
}