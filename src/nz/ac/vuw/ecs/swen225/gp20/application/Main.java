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
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Record;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Replay;
import nz.ac.vuw.ecs.swen225.gp20.rendering.Rendering;

public class Main extends GraphicalUserInterface {
  private static final float timePerLevel = 60f;
  private final Persistence persistence;
  private final Rendering renderer;
  public boolean gamePaused = false;
  public float timeElapsed = 0f;
  public State currentState;
  private Timer timer;
  private Record recorder;
  private Replay replay;
  private Maze maze;
  private int count = 0;
  private JLabel timeLeft;
  private JLabel lvlNumber;

  public enum State {
    INITIAL,
    RUNNING,
    GAME_OVER,
    GAME_WON,
    REPLAYING
  }


  public static void main(String... args) {
    new Main();
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

  /**
   * Gets the current maze object in the game.
   *
   * @return current current maze object
   */
  public Maze getMaze() {
    return this.maze;
  }

  /**
   * Get method to return persistence so recnplay
   * can utilize its methods.
   *
   * @return the persistence object
   */
  public Persistence getPersistence() {
    return this.persistence;
  }

  /**
   * Sets the JLabel so it can be used to update time
   * display throughout the game.
   *
   * @param timeElapsed is the JLable displaying time
   */
  public void setTimeElapsed(float timeElapsed) {
    this.timeElapsed = timeElapsed;
  }

  /**
   * Player has won the game so change the game
   * state to GAME_WON.
   */
  public void gameWon() {
    currentState = State.GAME_WON;
  }

  /**
   * Moves all the enemies on the board when called.
   * All enemies are moved based on the timer, which
   * updates every 0.1 seconds.
   */
  public void moveEnemies() {
    // Only records bug move if there are bugs
    if (recorder != null && maze.getNumMonsters() > 0) {
      recorder.addBugMove();
    }
    // Moves enemies and ends game if enemies killed player
    if (maze.moveBugs()) {
      endRec();
      currentState = State.GAME_OVER;
    }
  }

  /**
   * Sets the current state to running and sets replay
   * to null so the game can continue right after a
   * recording has finished replaying.
   */
  public void stopReplaying() {
    // Stops recording and changes game state
    currentState = State.RUNNING;
    replay = null;
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
  protected void setTimeLeft(JLabel timeLeft) {
    this.timeLeft = timeLeft;
  }

  @Override
  protected void setLvlLabel(JLabel lvlNumber) {
    this.lvlNumber = lvlNumber;
  }

  @Override
  protected ArrayList<Item> getItems() {
    if (currentState == State.INITIAL || maze == null) {
      return null;
    }
    return maze.getPlayerInv();
  }

  @Override
  protected int getChipsRemaining() {
    if (currentState == State.INITIAL || maze == null) {
      return 0;
    }
    return maze.chipsRemaining();
  }

  @Override
  protected State getCurrentState() {
    return currentState;
  }

  @Override
  protected Tile getCurrentTile() {
    return maze.getBoardTile(maze.getPlayerLocation());
  }

  @Override
  protected void movePlayer(GraphicalUserInterface.Direction dir) {
    if (currentState != State.RUNNING || gamePaused) {
      return;
    }
    maze.executeMove(dir);
    // If player has won level proceed to the next level
    // or end game
    if (maze.levelWonChecker()) {
      endRec();
      Maze newMaze = persistence.nextLevel(this);
      if (newMaze != null) {
        maze = newMaze;
        startTimer(this.timeLeft);
      }
    }
    // If recording add player move to recording
    if (recorder != null) {
      recorder.addMove(dir);
    }
  }

  @Override
  protected void newGame(JLabel timeLeft) {
    // Creates new game and initialised info
    lvlNumber.setText("1");
    gamePaused = false;
    maze = persistence.newGame();
    currentState = State.RUNNING;
    this.timeLeft = timeLeft;
    startTimer(timeLeft);
  }

  @Override
  protected void loadGame(JLabel timeLeft) {
    if (timer != null) {
      timer.stop();
    }
    Maze newMaze = persistence.selectFile();

    // If player canceled load game, continue game
    if (newMaze == null) {
      gamePaused = false;
      if (timer != null) {
        timer.start();
      }
      return;
    }

    // Loads game selected
    maze = newMaze;
    lvlNumber.setText("" + maze.getLevel());
    startTimer(timeLeft);
    currentState = State.RUNNING;
  }

  @Override
  protected void exitSaveGame() {
    if (currentState != State.RUNNING) {
      return;
    }
    timer.stop();
    // Asks if user wants to exit game or not
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
    // Only restarts if a game is already loaded
    if (currentState == State.RUNNING || currentState == State.GAME_OVER
            || currentState == State.GAME_WON) {
      currentState = State.RUNNING;
      maze = persistence.restart();
      startTimer(timeLeft);
    }
  }

  @Override
  protected void replayGame(JLabel timeLeft) {
    replay = new Replay(this);
    if (timer != null) {
      timer.stop();
    }
    Maze newMaze = replay.loadReplay();
    // If user does not cancel replay load it
    if (newMaze != null) {
      maze = newMaze;
      currentState = State.REPLAYING;
      startTimer(timeLeft);
    // Continue game is player does cancel
    } else if (timer != null) {
      timer.start();
    }
  }

  @Override
  protected void iterateReplay() {
    if (replay == null) {
      return;
    }
    replay.iterateStep();
  }

  @Override
  protected void autoReplay(int delay) {
    if (replay != null) {
      replay.autoStep(delay);
    }
  }

  @Override
  protected void resumeGame() {
    gamePaused = false;
  }

  @Override
  protected void pauseGame() {
    gamePaused = true;
  }

  @Override
  protected void startRec() {
    if (currentState == State.INITIAL || currentState == State.REPLAYING || recorder != null) {
      return;
    }
    recorder = new Record();
    recorder.startRec(maze);

  }

  @Override
  protected void endRec() {
    if (currentState == State.INITIAL || currentState == State.REPLAYING || recorder == null) {
      return;
    }
    recorder.record(maze);
    recorder = null;
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
    if (timeElapsed > 0f || timer != null) {
      timer.stop();
      timeElapsed = 0f;
    }
    timeElapsed = maze.getTimeElapsed();

    // Creates timer that increments every 0.1 seconds
    timer = new Timer(100, e -> {

      if (!gamePaused && currentState != State.REPLAYING) {
        // Update JLabel to display correct time.
        maze.setTimeElapsed(timeElapsed);
        timeLeft.setText(String.valueOf(String.format("%.1f", timePerLevel - timeElapsed)));
        timeElapsed += 0.1f;

        // Count increase every 0.1 seconds and enemies move every 0.5 seconds
        count++;
        if (count == 5) {
          count = 0;
          moveEnemies();
        }

        float timeRemaining = timePerLevel - timeElapsed;
        // Changes colour depending on how much time remains to give
        // user a extra warning
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

        // Updates renderer to animate character
        if (renderer.updateFrame(String.format("%.1f", timeElapsed))) {
          redraw();
        }
      // Keeps timer going if replaying to have renderer working properly
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
