package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.rendering.Rendering;

import javax.swing.*;
import java.awt.*;

public class Main extends GUI {
  private static final float timePerLevel = 60f;
  public float timeElapsed = 0f; // Current time elapsed since start
  public boolean gamePaused = false;
  static Rendering r;


  public static void main(String... args) {
    Main game = new Main();
    r = new Rendering();

  }

  @Override
  protected void redraw(Graphics g, Dimension d) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0,0,d.width,d.height);
    //r.testDrawingAnimation(g,null,"Down");
    redraw();
  }

  @Override
  protected void newGame(JLabel timeLeft) {
    System.out.println("Starts new game at level 1");

    // Resets timer if method is called again
    if (timeElapsed > 0f) {
      timer.stop();
      timeElapsed = 0f;
    }

    // Creates timer that increments every 0.1 seconds
    timer = new Timer(100, e -> {
      if (!gamePaused) {
        timeLeft.setText(String.valueOf(String.format("%.1f", timePerLevel - timeElapsed)));
        timeElapsed += 0.1f;
        if (timePerLevel - timeElapsed < 30) timeLeft.setForeground(new Color(227, 115, 14));
        if (timePerLevel - timeElapsed < 15) timeLeft.setForeground(Color.RED);
        if (timePerLevel - timeElapsed < 0) timer.stop();
      }
    });
    timer.start();
  }

  @Override
  protected void resumeGame() {
    System.out.println("Resume the game");
    gamePaused = false;
  }

  @Override
  protected void pauseGame() {
    System.out.println("Pauses the game");
    gamePaused = true;
  }

}
