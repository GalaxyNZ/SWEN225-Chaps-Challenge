package nz.ac.vuw.ecs.swen225.gp20.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.rendering.Rendering;

public class Main extends GraphicalUserInterface {
  private static final float timePerLevel = 60f;
  public float timeElapsed = 0f; // Current time elapsed since start
  public boolean gamePaused = false;
  private Timer timer;
  private Rendering renderer;
  private Record recorder;
  private Persistence persistence;
  Maze maze;

  private enum State {
    INTIAL,
    RUNNING
  }

  private State currentState;


  public static void main(String... args) {
    Main game = new Main();
  }

  /**
   * Creates the game and initialises the other packages such
   * as the renderer, recorder and persistence.
   */
  public Main() {
    renderer = new Rendering();
    recorder = new Record();
    persistence = new Persistence();
    currentState = State.INTIAL;
  }

  @Override
  protected ArrayList<Item> getItems() {
    if (currentState == State.INTIAL || maze == null) {
      return null;
    }
    return maze.getPlayerInv();
  }

  @Override
  protected void redraw(Graphics g, Dimension d) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, d.width, d.height);
    if (maze != null) {
      renderer.testDrawingAnimation(g, "Down", d, maze);
    }
  }

  @Override
  protected void movePlayer(GraphicalUserInterface.Direction dir) {
    if (currentState == State.INTIAL) {
      return;
    }
    maze.executeMove(dir);
    if (maze.levelWonChecker()) {
      // Player has won
    }
    recorder.addMove(dir);
  }

  @Override
  protected int getChipsRemaining() {
    if (currentState == State.INTIAL) {
      return 0;
    }
    return maze.chipsRemaining();
  }

  @Override
  protected void newGame(JLabel timeLeft) {
    System.out.println("Starts new game at level 1");
    maze = persistence.newGame();
    currentState = State.RUNNING;
    startTimer(timeLeft);
  }

  @Override
  protected void exitSaveGame() {
    if (currentState == State.INTIAL) {
      return;
    }
    System.out.println("Save and exit");
    int result = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to quit? Your game will be saved", "Save & Exit Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      persistence.saveGame(maze);
      System.exit(0); // cleanly end the program.
    }
  }

  @Override
  protected void replayGame() {

  }

  @Override
  protected void iterateReplay() {

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
    if (timer != null) {
      timer.stop();
    }
    Maze newMaze = persistence.selectFile();

    if (newMaze == null) {
      if (timer != null) {
        timer.start();
      }
      return;
    }

    maze = newMaze;
    if (currentState == State.INTIAL) {
      startTimer(timeLeft);
    } else {
      timer.start();
    }
    currentState = State.RUNNING;
  }

  @Override
  protected void endRec() {
    if (currentState == State.INTIAL) {
      return;
    }
    System.out.println("End Recording");
    //recorder.stopRecording();
    recorder.record(maze);
  }

  @Override
  protected void startRec() {
    if (currentState == State.INTIAL) {
      return;
    }
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

        float timeRemaining = timePerLevel - timeElapsed;
        if (timeRemaining < 30) {
          timeLeft.setForeground(new Color(227, 115, 14));
        } else if (timeRemaining < 15) {
          timeLeft.setForeground(Color.RED);
        } else if (timeRemaining < 0) {
          timer.stop();
        }

        if (renderer.updateFrame(String.format("%.1f", timeElapsed))) {
          redraw();
        }
      }
    });
    timer.start();
  }

}
