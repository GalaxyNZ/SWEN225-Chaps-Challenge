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
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Replay;
import nz.ac.vuw.ecs.swen225.gp20.rendering.Rendering;

public class Main extends GraphicalUserInterface {
  private static final float timePerLevel = 60f;
  public float timeElapsed = 0f; // Current time elapsed since start
  public boolean gamePaused = false;
  private Timer timer;
  private Rendering renderer;
  private Record recorder;
  private Replay replay;
  private Persistence persistence;
  private Maze maze;
  private int count = 0;
  public State currentState;
  private JLabel timeLeft;
  public enum State {
    INITIAL,
    RUNNING,
    GAME_OVER,
    GAME_WON,
    REPLAYING
  }


  public static void main(String... args) {
    Main game = new Main();
  }

  /**
   * Creates the game and initialises the other packages such
   * as the renderer, recorder and persistence.
   */
  public Main() {
    renderer = new Rendering();
    persistence = new Persistence();
    currentState = State.INITIAL;
  }

  public Maze getMaze() {
    return this.maze;
  }

  public Persistence getPersistence() {
    return this.persistence;
  }

  public void setTimeElapsed(float timeElapsed) {
    this.timeElapsed = timeElapsed;
  }

  public void gameWon() {
    currentState = State.GAME_WON;
  }

  @Override
  protected ArrayList<Item> getItems() {
    if (currentState == State.INITIAL || maze == null) {
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
    if (currentState != State.RUNNING || gamePaused) {
      return;
    }
    maze.executeMove(dir);
    if (maze.levelWonChecker()) {
      maze = persistence.nextLevel(this);
      if (maze != null) {
        startTimer(this.timeLeft);
      }
    }
    if (recorder != null) {
      recorder.addMove(dir);
    }
  }

  @Override
  protected int getChipsRemaining() {
    if (currentState == State.INITIAL || maze == null) {
      return 0;
    }
    return maze.chipsRemaining();
  }

  @Override
  protected void newGame(JLabel timeLeft) {
    gamePaused = false;
    maze = persistence.newGame();
    currentState = State.RUNNING;
    this.timeLeft = timeLeft;
    startTimer(timeLeft);
  }

  @Override
  protected void exitSaveGame() {
    if (currentState != State.RUNNING) {
      return;
    }
    timer.stop();
    System.out.println("Save and exit");
    int result = JOptionPane.showConfirmDialog(null,
            "Are you sure you want to quit? Your game will be saved", "Save & Exit Game",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      persistence.saveGame(maze);
      System.exit(0); // cleanly end the program.
    }
    timer.start();
  }

  @Override
  protected void restartRound(JLabel timeLeft) {
    currentState = State.RUNNING;
    maze = persistence.restart();
    startTimer(timeLeft);
  }

  @Override
  protected void replayGame(JLabel timeLeft) {
    replay = new Replay(this);
    if (timer != null) {
      timer.stop();
    }
    Maze newMaze = replay.loadReplay();
    if (newMaze != null) {
      maze = newMaze;
      currentState = State.REPLAYING;
      startTimer(timeLeft);
    } else {
      timer.start();
    }
  }

  public void stopReplaying(){
    currentState = State.RUNNING;
    replay = null;
  }

  @Override
  protected void iterateReplay() {
    if (replay == null) {
      return;
    }
    replay.iterateStep();
  }

  @Override
  protected void autoReplay() {
    if (replay == null) {
      return;
    }
    replay.autoStep(1000);
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
      gamePaused = false;
      if (timer != null) {
        timer.start();
      }
      return;
    }

    maze = newMaze;
    startTimer(timeLeft);
    currentState = State.RUNNING;
  }

  @Override
  protected void endRec() {
    if (currentState == State.INITIAL || recorder == null) {
      return;
    }
    recorder.record(maze);
    recorder = null;
  }

  @Override
  protected void startRec() {
    if (currentState == State.INITIAL || recorder != null) {
      return;
    }
    recorder = new Record();
    recorder.startRec(maze);

  }

  @Override
  protected State getCurrentState() {
    return currentState;
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
    timeElapsed = maze.getTimeElapsed();

    // Creates timer that increments every 0.1 seconds
    timer = new Timer(100, e -> {

      if (!gamePaused && currentState != State.REPLAYING && currentState != State.GAME_WON) {
        maze.setTimeElapsed(timeElapsed);
        timeLeft.setText(String.valueOf(String.format("%.1f", timePerLevel - timeElapsed)));
        timeElapsed += 0.1f;
        count++;
        if (count == 5) {
          count = 0;
          if (maze.moveBugs()) {
            currentState = State.GAME_OVER;
          }
        }

        float timeRemaining = timePerLevel - timeElapsed;
        timeLeft.setForeground(Color.BLACK);
        if (timeRemaining < 30) {
          timeLeft.setForeground(new Color(227, 115, 14));
        }
        if (timeRemaining < 15) {
          timeLeft.setForeground(Color.RED);
        }
        if (timeRemaining < 0) {
          timeLeft.setText("0");
          currentState = State.GAME_OVER;
        }

        if (renderer.updateFrame(String.format("%.1f", timeElapsed))) {
          redraw();
        }
      } else if (currentState == State.REPLAYING) {
        timeLeft.setText("REPLAYING");
        timeElapsed += 0.1f;
        if (renderer.updateFrame(String.format("%.1f", timeElapsed))) {
          redraw();
        }
      }
    });
    timer.start();
  }

}
