package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.rendering.Rendering;

import java.awt.*;

public class Main extends GUI {
  private static Rendering r;
  public static void main(String... args) {
    new Main();
    r = new Rendering();
  }

  @Override
  protected void redraw(Graphics g, Dimension d) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0,0,d.width,d.height);
    r.testDrawingAnimation(g,this,"Down");
    redraw();
  }
}
