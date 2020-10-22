package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import java.io.*;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

/**
 *This class represents the recording aspect of the game.
 */
public class Record {
  public Queue<String> q = new ArrayDeque<>();
  private Boolean isRecording = false;
  private Object Persistence;
  private String fileName;


  /**
   * Begins recording of gameplay by saving current state
   * of board and recording movements, then saving to Json file.
   */
  public void record(Maze maze) {
    HashMap<String, Integer> config = new HashMap();
    JsonBuilderFactory factory = Json.createBuilderFactory(config);

    JsonArrayBuilder array = factory.createArrayBuilder();
    while (!q.isEmpty()) {
      array.add(q.poll().toString());
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

      Writer writer = new BufferedWriter(new FileWriter(fileName(fileName) + ".json"));

      for (int i = 0; i < saveLength; i++) {
        char next = savedGame.charAt(i);
        if (next == ',' || next == '{') writer.write(next + "\n\t");
        else if (next == '}') writer.write("\n" + next);
        else writer.write(next);
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
   * @param direction
   */
  public void addMove(GraphicalUserInterface.Direction direction) {
    q.add(direction.name());
  }

  public void addBugMove() {
    q.add("ENEMIES");
  }

  /**
   * Stops recording the movement so we can save to Json file.
   */
  public void stopRecording() {
    isRecording = false;
  }

  /**
   * Sets the recording name to "yyyy/MM/dd-HH:mm:ss" so that most file names are unique.
   *
   * @return
   */
  public String fileName(String fileName) {
    return "src/nz/ac/vuw/ecs/swen225/gp20/recnplay/replays/" + fileName + "_moves";
  }
}