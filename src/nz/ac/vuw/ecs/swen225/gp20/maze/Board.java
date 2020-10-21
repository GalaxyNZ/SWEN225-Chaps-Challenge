package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
	private Map<Integer, ArrayList<String>> bugMoves = new HashMap<Integer, ArrayList<String>>();
	private ArrayList<Point> bugLocations = new ArrayList<Point>();
	
	/*
	 * Constructor method. Has an input of a Map with generic contents from Persistence. 
	 * Stores boardMaps size parameters, gets the total number of chips for the ExitLockItem and the Information stored in the InfoTile.
	 * Gets all key information from the map.
	 * Gets and bug details from map.
	 */	
	
	public Board(Map<?,?> map) {
		xSize = (int) Double.parseDouble(map.get("xSize").toString());
        ySize = (int) Double.parseDouble(map.get("ySize").toString());
        tileInformation = map.get("tileInfo").toString();
        numChips = (int) Double.parseDouble(map.get("numChips").toString());
        boardMap = new Tile[ySize][xSize];
        GKMax = (int) Double.parseDouble(map.get("SETGK").toString());
        BKMax = (int) Double.parseDouble(map.get("SETBK").toString());
        YKMax = (int) Double.parseDouble(map.get("SETRK").toString());
        RKMax = (int) Double.parseDouble(map.get("SETYK").toString());
        setBugs(map);
        ArrayList<String> delimitedInput = new ArrayList<String>(Arrays.asList(map.get("board").toString().split("[|]")));
        makeTiles(delimitedInput, xSize, ySize);
	}
	
	/*
	 * Creates a map that contains all of the bugs by number and their movesets by analyzing the input map entries.
	 */
	
	private void setBugs(Map<?,?> map) {
		if(map.containsKey("numBugs")) {
			int max = (int) Double.parseDouble(map.get("numBugs").toString());
			for(int i = 0; i < max; i++) {
				if(map.containsKey("enemy" + i)) {
					String movesetArray = map.get("enemy" + i).toString();
					bugMoves.put(i,new ArrayList<String>(Arrays.asList(movesetArray.split("[|]"))));					
				}
			}
		}
		
	}
	
	/*
	 * Gets every bug from the list of all bug locations and runs them through the bug mover, returns true if bug kills player.
	 */
	
	 public boolean moveBugs() {
	        ArrayList<Point> newBugLocations = new ArrayList<>();
	        boolean playerKilled = false;
	        for(Point pt: bugLocations) {
	            if(boardMap[pt.y][pt.x].getItem() instanceof MonsterItem) {
	                String move = boardMap[pt.y][pt.x].getItem().getNextMove();
	                if(move != null) {
	                    switch(move) {
	                    case "UP":
	                         playerKilled = moveThisBug(pt, new Point(pt.x, pt.y-1));
	                         newBugLocations.add(new Point(pt.x, pt.y-1));
	                         if(playerKilled) {
	                        	 return playerKilled;
	                         }
	                         break;
	                    case "DOWN":
	                    	playerKilled = moveThisBug(pt, new Point(pt.x, pt.y+1));
	                        newBugLocations.add(new Point(pt.x, pt.y+1));
	                        if(playerKilled) {
	                        	 return playerKilled;
	                         }
	                        break;
	                    case "LEFT":
	                    	playerKilled = moveThisBug(pt, new Point(pt.x-1, pt.y));
	                        newBugLocations.add(new Point(pt.x-1, pt.y));
	                        if(playerKilled) {
	                        	 return playerKilled;
	                         }
	                        break;
	                    case "RIGHT":
	                    	playerKilled = moveThisBug(pt, new Point(pt.x+1, pt.y));
	                        newBugLocations.add(new Point(pt.x+1, pt.y));
	                        if(playerKilled) {
	                        	 return playerKilled;
	                         }
	                        break;
	                    }
	                }
	            }
	        }
	        if (newBugLocations.size() < bugLocations.size()) return true;
	        bugLocations = newBugLocations;
	        return false;
	    }
	
	/*
	 * Moves the bug at oldLocation to newLocation on the board, returns true of the bug killed the player.
	 */
	
	private boolean moveThisBug(Point oldLocation, Point newLocation) {
		if(boardMap[oldLocation.y][oldLocation.x].getItem() instanceof Chap) {
			return true;
		}
		if(boardMap[newLocation.y][newLocation.x].getItem() instanceof Chap) {
			boardMap[newLocation.y][newLocation.x].addItem(boardMap[oldLocation.y][oldLocation.x].addItem(null));
			return true;
		}
		boardMap[newLocation.y][newLocation.x].addItem(boardMap[oldLocation.y][oldLocation.x].addItem(null));
		return false;
	}
	
	/*
	 * Returns an ArrayList<String> of the next move for all bugs.
	 */
	
	public ArrayList<String> getNextBugMoves(){
		ArrayList<String> moves = new ArrayList<String>();
		for(Point pt : bugLocations) {
			if(boardMap[pt.y][pt.x].getItem() instanceof MonsterItem) {
				MonsterItem thisMonster = (MonsterItem) boardMap[pt.y][pt.x].getItem();
				moves.add(thisMonster.peekThisMove());
			}
		}
		return moves;
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
			case "_":
				return new FreeTile(location, null);
			case "#":
				return new WallTile(location);
			case "%":
				return new ExitTile(location);
			case "E":
				return new FreeTile(location, new ExitLockItem(numChips)); //Provides the ExitLock with a number of treasure the player must have to pass through this Item.
			case "y":
				return new FreeTile(location, new KeyItem("Y", YKMax)); //All keys are given the predetermined value of uses that they have, so that when used the correct number of time they are removed from player inv.
			case "b":
				return new FreeTile(location, new KeyItem("B", BKMax));
			case "r":
				return new FreeTile(location, new KeyItem("R", RKMax));
			case "g":
				return new FreeTile(location, new KeyItem("G", GKMax));
			case "Y":
				return new FreeTile(location, new LockedDoorItem("Y"));
			case "B":
				return new FreeTile(location, new LockedDoorItem("B"));
			case "R":
				return new FreeTile(location, new LockedDoorItem("R"));
			case "G":
				return new FreeTile(location, new LockedDoorItem("G"));
			case "I":
				return new InfoTile(location, tileInformation);
			case "T":
				return new FreeTile(location, new TreasureItem());
			case "X":
				playerLocation = location;
				return new FreeTile(location, new Chap());
			case "0":
				bugLocations.add(location);
				return new FreeTile(location, new MonsterItem(bugMoves.get(0), 0));
			case "1":
				bugLocations.add(location);
				return new FreeTile(location, new MonsterItem(bugMoves.get(1), 1));
			case "2":
				bugLocations.add(location);
				return new FreeTile(location, new MonsterItem(bugMoves.get(2), 2));
			default:
				System.out.println("Default Case Reached with input: '" + input + "' at location " + location.x + " - " + location.y); //Indicates an error in the mapString in Maze.java.
				return null;
		}		
	}
	
	/*
	 * Writes the board and all of its elements into a string for printing purposes.
	 */

	public String toString() {
		String mapString= "";
		for (int y = 0; y < ySize; y++) {
			//mapString += "|";
			for (int x = 0; x < xSize; x++) {
				mapString += boardMap[y][x].toString() + "|";
			}
			//mapString += "\n";
		}
		return mapString;
	}
	
	/*
	 * Returns the location of the player on the board.
	 */

	public void setPlayerLocation(Point point) {
		this.playerLocation = point;
	}
	
	/*
	 * Sets the Tile at given location in the boardMap.
	 */

	public void setTileAt(int x, int y, Tile tile) {
		boardMap[y][x] = tile;
	}
	
	/*
	 * Get the width of the Board.
	 */

	public int getWidth() {
		return boardMap[0].length;
	}
	
	/*
	 * Get the height of the Board.
	 */

	public int getHeight() {
		return boardMap.length;
	}
	
	/*
	 * Returns the total number of chips on this map.
	 */
	
	public int getTotalChips() {
		return numChips;
	}
}
