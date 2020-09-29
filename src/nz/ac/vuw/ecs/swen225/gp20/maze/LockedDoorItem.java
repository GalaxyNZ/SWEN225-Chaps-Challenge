package nz.ac.vuw.ecs.swen225.gp20.maze;

public class LockedDoorItem extends Item{
	
	private String color;
	
	public LockedDoorItem(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getChar() {
		return "LKD";
	}
	
}
