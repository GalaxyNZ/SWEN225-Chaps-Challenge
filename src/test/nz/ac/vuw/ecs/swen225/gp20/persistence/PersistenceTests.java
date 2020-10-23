package test.nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import org.junit.jupiter.api.Test;

public class PersistenceTests {
  @Test
  public void t01_loadNewGame() {
    Persistence p = new Persistence();
    try {
      p.newGame();
      assert(true);
    } catch (Exception e) {
      assert(false);
      e.printStackTrace();
    }
  }

  @Test
  public void t02_restartGame() {
    Persistence p = new Persistence();
    try {
      p.newGame();
      p.restart();
      assert(true);
    } catch (Exception e) {
      assert(false);
      e.printStackTrace();
    }
  }

  @Test
  public void t03_saveGame() {
    Persistence p = new Persistence();
    try {
      Maze maze = p.newGame();
      maze.executeMove(GraphicalUserInterface.Direction.RIGHT);
      p.saveGame(maze);
      assert(true);
    } catch (Exception e) {
      assert(false);
      e.printStackTrace();
    }
  }

  @Test
  public void t04_saveNullGame() {
    Persistence p = new Persistence();
    try {
      Maze maze = null;
      p.saveGame(maze);
      assert(true);
    } catch (Exception e) {
      assert(false);
      e.printStackTrace();
    }
  }
  @Test
  public void t05_saveLevelTwo() {
    Persistence p = new Persistence();
    try {
      Maze maze = p.loadLevelTwo();
      maze.executeMove(GraphicalUserInterface.Direction.LEFT);
      maze.executeMove(GraphicalUserInterface.Direction.LEFT);
      p.saveGame(maze);
      assert(true);
    } catch (Exception e) {
      assert(false);
      e.printStackTrace();
    }
  }

  @Test
  public void t06_saveInventory() {
    Persistence p = new Persistence();
    try {
      Maze maze = p.newGame();
      maze.executeMove(GraphicalUserInterface.Direction.LEFT);
      maze.executeMove(GraphicalUserInterface.Direction.LEFT);
      maze.executeMove(GraphicalUserInterface.Direction.UP);
      p.saveGame(maze);
      assert(true);
    } catch (Exception e) {
      assert(false);
      e.printStackTrace();
    }
  }

  /*@Test
  public void t07_selectFile() {
    Persistence p = new Persistence();
    try {
      Maze maze = p.selectFile();
      p.saveGame(maze);
      assert(true);
    } catch (Exception e) {
      assert(false);
      e.printStackTrace();
    }
  }*/

}
