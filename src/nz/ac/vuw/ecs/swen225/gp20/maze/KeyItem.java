package nz.ac.vuw.ecs.swen225.gp20.maze;

public class KeyItem extends Item{
	
		public String color;
		public int used = 0;
		public int maxUses;
		
		public KeyItem(String color, int maxUses) {
			this.color = color;
			this.maxUses = maxUses;
		}
		
		public void increment() {
			used++;
		}
		
		public String getChar() {
			return "KCL";
		}
		
	}
