package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.*;

public class Main extends GUI {
  public static void main(String... args) {
    Main game = new Main();
  }

  @Override
  protected void redraw(Graphics g, Dimension d) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0,0,d.width,d.height);
  }
}
