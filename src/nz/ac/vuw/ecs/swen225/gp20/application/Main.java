package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.rendering.Rendering;

import javax.swing.*;
import java.awt.*;

public class Main extends GUI {
  private static final float timePerLevel = 60f;
  public float timeElapsed = 0f; // Current time elapsed since start
  public boolean gamePaused = false;
  private Timer timer;
  private Rendering renderer;
  private Record recorder;
  Maze maze;


  public static void main(String... args) {
    Main game = new Main();
  }

  public Main() {
    renderer = new Rendering();
    recorder = new Record();
  }

  @Override
  protected void redraw(Graphics g, Dimension d) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, d.width, d.height);
    if (maze != null) renderer.testDrawingAnimation(g, "Down", d, maze);
  }

  @Override
  protected void movePlayer(GUI.direction dir) {
    if (maze == null) return;
    maze.executeMove(dir);
    if (maze.levelWonChecker()) {
      // Player has won
    }
    recorder.addMove(dir);
  }

  @Override
  protected void newGame(JLabel timeLeft) {
    System.out.println("Starts new game at level 1");
    startTimer(timeLeft);
  }

  @Override
  protected void exitSaveGame() {
    System.out.println("Save and exit");
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

  @Override
  protected void loadGame(JLabel timeLeft) {
    System.out.println("Loads a saved game");
    if (timer != null) timer.stop();
    Persistence persistence = new Persistence();
    maze = persistence.selctFile();
    startTimer(timeLeft);
  }

  @Override
  protected void endRec() {
    System.out.println("End Recording");
    //recorder.stopRecording();
    recorder.record(maze);
  }

  @Override
  protected void startRec() {
    System.out.println("Start Recording");
    //recorder.record(maze);
  }

  /**
   * Creates a timer that increases in intervals
   * of 0.1 seconds. Timer is then subtracted
   * from the total time for the current level
   * and displayed in the GUI.
   *
   * @param timeLeft is the Label where the timer is drawn
   */
  public void startTimer(JLabel timeLeft) {
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
        if (timeElapsed - (int) timeElapsed == 0) {
          // Move bugs
        }
        if (timePerLevel - timeElapsed < 30) timeLeft.setForeground(new Color(227, 115, 14));
        if (timePerLevel - timeElapsed < 15) timeLeft.setForeground(Color.RED);
        if (timePerLevel - timeElapsed < 0) timer.stop();

        if (renderer.updateFrame(String.format("%.1f", timeElapsed))) redraw();
      }
    });
    timer.start();
  }

}
