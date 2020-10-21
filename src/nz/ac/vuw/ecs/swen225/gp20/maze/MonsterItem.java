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
	 * Returns a version of moveset that moves the starting point to where the bug currently is for saved games.
	 */
	
	public ArrayList<String> getMoveset() {
        ArrayList<String> newMoveSet = new ArrayList<>();
        int count = this.count;
        for (int i = 0; i < moveset.size(); i++) {
            if ((count + i) >= moveset.size()) count = -i;
            newMoveSet.add(moveset.get(count + i));
        }
        return newMoveSet;
    }

	public String getNextMove(){
		String returnValue = moveset.get(count);
		count++;
		if(count >= moveset.size()) {
			count = 0;
		}
		return returnValue;
	}
	
	/*
	 * Returns the String that represents this Monster.
	 */
	
	public String getChar() {
		return thisChar;
	}
	
	/*
	 * Base Item contains no color.
	 */
	
	public String getColor() {
		return "VOID COLOR";
	}
	
	/*
	 * Returns the String that represents this Monster.
	 */
	
	public String toString() {
		return thisChar;
	}
	
	/*
	 * Returns the next move the bug will do.
	 */
	
	public String peekThisMove() {
		return moveset.get(count);
	}
	
}