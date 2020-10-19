package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.rendering.Rendering;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends GUI {
  private static final float timePerLevel = 60f;
  public float timeElapsed = 0f; // Current time elapsed since start
  public boolean gamePaused = false;
  private Timer timer;
  private Rendering renderer;
  private Record recorder;
  private Persistence p;
  Maze maze;
  private enum states {
    INTIAL,
    RUNNING
  }
  private states currentState;


  public static void main(String... args) {
    Main game = new Main();
  }

  public Main() {
    renderer = new Rendering();
    recorder = new Record();
    p = new Persistence();
    currentState = states.INTIAL;
  }

  @Override
  protected ArrayList<Item> getItems() {
    if (currentState == states.INTIAL || maze == null) return null;
    return maze.getPlayerInv();
  }

  @Override
  protected void redraw(Graphics g, Dimension d) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, d.width, d.height);
    if (maze != null) renderer.testDrawingAnimation(g, "Down", d, maze);
  }

  @Override
  protected void movePlayer(GUI.direction dir) {
    if (currentState == states.INTIAL) return;
    maze.executeMove(dir);
    if (maze.levelWonChecker()) {
      // Player has won
    }
    recorder.addMove(dir);
  }

  @Override
  protected int getChipsRemaining() {
    if (currentState == states.INTIAL) return 0;
    return maze.chipsRemaining();
  }

  @Override
  protected void newGame(JLabel timeLeft) {
    System.out.println("Starts new game at level 1");
    maze = p.newGame();
    currentState = states.RUNNING;
    startTimer(timeLeft);
  }

  @Override
  protected void exitSaveGame() {
    if (currentState == states.INTIAL) return;
    System.out.println("Save and exit");
    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit? Your game will be saved", "Save & Exit Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      p.saveGame(maze);
      System.exit(0); // cleanly end the program.
    }
  }

  @Override
  protected void replayGame() {

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
    Maze newMaze = p.selectFile();
    if (newMaze == null) return;
    if (currentState == states.INTIAL) {
      maze = newMaze;
      startTimer(timeLeft);
    } else {
      if (timer != null) timer.stop();
      maze = newMaze;
      timer.start();
    }
    currentState = states.RUNNING;
  }

  @Override
  protected void endRec() {
    if (currentState == states.INTIAL) return;
    System.out.println("End Recording");
    //recorder.stopRecording();
    recorder.record(maze);
  }

  @Override
  protected void startRec() {
    if (currentState == states.INTIAL) return;
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
