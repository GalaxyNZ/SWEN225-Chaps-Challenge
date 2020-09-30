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
	private Point playerLocation;
	protected int xSize;
	protected int ySize;
	private int YKMax;
	private int BKMax;
	private int GKMax;
	private int RKMax;
	private int extraDataSize;
	
	/*
	 * Constructor method. Runs mapString through a delimiter, creating an array. 
	 * Removes and stores boardMaps size parameters, gets the total number of chips for the ExitLockItem and the Information stored in the InfoTile.
	 */
	
	public Board(String input) {
		ArrayList<String> delimitedInput = new ArrayList<String>(Arrays.asList(input.split("[|]")));
		xSize = Integer.parseInt(delimitedInput.remove(0));
		ySize = Integer.parseInt(delimitedInput.remove(0));
		tileInformation = delimitedInput.remove(0);
		numChips = Integer.parseInt(delimitedInput.remove(0));
		boardMap = new Tile[ySize][xSize];
		delimitedInput = getKeyInfo(delimitedInput);
		makeTiles(delimitedInput, xSize, ySize);
	}
	
	/*
	 * After universal variables are taken from the ArrayList of delimited mapString all extra information is processed.
	 * This method sets the amount of uses per different color of Key.
	 */
	
	private ArrayList<String> getKeyInfo(ArrayList<String> input) {
		String token = "";
		floop: while(!token.equals("F")) {
			token = input.get(0);
			switch(token) {
			case "SETGK":
				input.remove(0);
				GKMax = Integer.parseInt(input.remove(0));
				break;
			case "SETBK":
				input.remove(0);
				BKMax = Integer.parseInt(input.remove(0));
				break;
			case "SETRK":
				input.remove(0);
				RKMax = Integer.parseInt(input.remove(0));
				break;
			case "SETYK":
				input.remove(0);
				YKMax = Integer.parseInt(input.remove(0));
				break;
			default:
				break floop; // NOTE: Added to stop a infinite loop if no keys were present
			}
		}
		return input;
	}
	
	/*
	 * Takes the delimited mapString and populates the board with Tiles based off of feedback from textAssignmentTable.
	 * Input ArrayList should no longer contain anything but raw Tile data.
	 */

	private void makeTiles(ArrayList<String> input, int xSize, int ySize) {
		int i;
		int j;
		int count = 0;
		for(i = 0; i < ySize; i++) {
			for(j = 0; j < xSize; j++) {
				boardMap[i][j] = textAssignmentTable(input.get(count+extraDataSize), new Point(j, i));
				count++;
			}			
		}
	}
	
	/*
	 * Returns the location of the player in the boardMap array.
	 */

	public Point findPlayer() {		
		return playerLocation;
	}
	
	/*
	 * Returns the total number of treasure in the Tiles of boardMap.
	 */
	
	public int getChips() {
		return numChips;
	}
	
	/*
	 * Returns the information contained in the InfoTile.
	 */
	
	public String getInfo() {
		return tileInformation;
	}
	
	/*
	 * Takes raw input data and creates Tiles based off of the input to create the Tiles that populate boardMap.
	 */
	
	private Tile textAssignmentTable(String input, Point location) {
		
		switch(input) {
		case "F":
			return new FreeTile(location, null);
		case "W":
			return new WallTile(location);
		case "EXT":
			return new ExitTile(location);		
		case "EXTLCK":
			return new FreeTile(location, new ExitLockItem(numChips)); //Provides the ExitLock with a number of treasure the player must have to pass through this Item.
		case "YK":
			return new FreeTile(location, new KeyItem("Y", YKMax)); //All keys are given the predetermined value of uses that they have, so that when used the correct number of time they are removed from player inv.
		case "BK":
			return new FreeTile(location, new KeyItem("B", BKMax));
		case "RK":
			return new FreeTile(location, new KeyItem("R", RKMax));
		case "GK":
			return new FreeTile(location, new KeyItem("G", GKMax));
		case "YKD":
			return new FreeTile(location, new LockedDoorItem("Y"));
		case "BKD":
			return new FreeTile(location, new LockedDoorItem("B"));
		case "RKD":
			return new FreeTile(location, new LockedDoorItem("R"));			
		case "GKD":
			return new FreeTile(location, new LockedDoorItem("G"));
		case "I":
			return new InfoTile(location);
		case "T":
			return new FreeTile(location, new TreasureItem());
		case "CHAP":
			playerLocation = location;
			return new FreeTile(location, new Chap());
		default:
			System.out.println("DEFAULT CASE REACHED"); //Indicates an error in the mapString in Maze.java.
			return null;
		}		
	}

	public String toString() {
		String mapString= "";
		for (int y = 0; y < ySize; y++) {
			mapString += "|";
			for (int x = 0; x < xSize; x++) {
				mapString += boardMap[y][x].toString() + "|";
			}
			mapString += "\n";
		}
		return mapString;
	}

}
