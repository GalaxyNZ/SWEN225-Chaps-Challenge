package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;

/**
 *This class represents the recording aspect of the game.
 */
public class Record {
  public Queue<String> movements = new ArrayDeque<>();
  private String fileName;


  /**
   * Begins recording of gameplay by saving current state
   * of board and recording movements, then saving to Json file.
   */
  public void record(Maze maze) {
    HashMap<String, Integer> config = new HashMap();
    JsonBuilderFactory factory = Json.createBuilderFactory(config);

    JsonArrayBuilder array = factory.createArrayBuilder();
    while (!movements.isEmpty()) {
      array.add(movements.poll().toString());
    }

    JsonObjectBuilder object = factory.createObjectBuilder();
    object.add("replayFile", fileName);
    object.add("time", maze.getTimeElapsed());
    object.add("moves", array);

    try {
      Writer stringWriter = new StringWriter();
      Json.createWriter(stringWriter).write(object.build());
      String savedGame = stringWriter.toString();
      int saveLength = savedGame.length();
      stringWriter.close();

      FileOutputStream fileStream = new FileOutputStream(fileName(fileName));
      Writer writer = new OutputStreamWriter(fileStream, "UTF-8");

      for (int i = 0; i < saveLength; i++) {
        char next = savedGame.charAt(i);
        if (next == ',' || next == '{') {
          writer.write(next + "\n\t");
        } else if (next == '}') {
          writer.write("\n" + next);
        } else {
          writer.write(next);
        }
      }

      writer.close();

    } catch (IOException e) {
      System.out.printf("Error saving game: " + e);
    }
  }

  public void startRec(Maze maze) {
    Persistence persistence = new Persistence();
    fileName = persistence.saveGame(maze);
  }

  /**
   * Adds moves to the queue for recording.
   *
   * @param direction from Main
   */
  public void addMove(GraphicalUserInterface.Direction direction) {
    movements.add(direction.name());
  }

  public void addBugMove() {
    movements.add("ENEMIES");
  }

  /**
   * Sets the recording name to "yyyy/MM/dd-HH:mm:ss" so that most file names are unique.
   *
   * @return
   */
  public String fileName(String fileName) {
    return "src/nz/ac/vuw/ecs/swen225/gp20/recnplay/replays/" + fileName + "_moves.json";
  }
}