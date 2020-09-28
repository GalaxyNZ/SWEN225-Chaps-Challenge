package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * This class represents the physical board of the game, and contains the 2D array of all Tiles as well as the Items
 */

public class Board {
	
	public Tile[][] boardMap;
	private String tileInformation;
	private int numChips;
	
	public Board(String input) {
		ArrayList<String> delimitedInput = new ArrayList<String>(Arrays.asList(input.split("[|]")));
		int xSize = Integer.parseInt(delimitedInput.remove(0));
		int ySize = Integer.parseInt(delimitedInput.remove(0));
		tileInformation = delimitedInput.remove(0);
		numChips = Integer.parseInt(delimitedInput.remove(0));
		boardMap = new Tile[ySize][xSize];
		makeTiles(delimitedInput, xSize, ySize);
	}
	
	private void makeTiles(ArrayList<String> input, int xSize, int ySize) {		
		int i;
		int j;
		int count = 0;
		for(i = 0; i < xSize; i++) {
			for(j = 0; j < ySize; j++) {
				boardMap[j][i] = textAssignmentTable(input.get(count), new Point(i, j));
				count++;
			}			
		}
	}
	
	private Tile textAssignmentTable(String input, Point location) {
		
		switch(input) {
		case "F":
			return new FreeTile(location, null);
		case "W":
			return new WallTile(location);
		case "EXT":
			return new ExitTile(location);		
		case "EXTLCK":
			return new FreeTile(location, new ExitLockItem(numChips));
		case "YK":
			return new FreeTile(location, new KeyItem("Y"));
		case "BK":
			return new FreeTile(location, new KeyItem("B"));
		case "RK":
			return new FreeTile(location, new KeyItem("R"));
		case "GK":
			return new FreeTile(location, new KeyItem("G"));
		case "YKD":
			return new FreeTile(location, new LockedDoorItem("Y"));
		case "BKD":
			return new FreeTile(location, new LockedDoorItem("B"));
		case "RKD":
			return new FreeTile(location, new LockedDoorItem("R"));			
		case "GKD":
			return new FreeTile(location, new LockedDoorItem("G"));
		case "I":
			return new InfoTile(location, tileInformation);
		case "T":
			return new FreeTile(location, new TreasureItem());
		case "CHAP":
			return new FreeTile(location, new Chap());
		default:
			System.out.println("DEFAULT CASE REACHED");
			return null;
		}		
	}

}
