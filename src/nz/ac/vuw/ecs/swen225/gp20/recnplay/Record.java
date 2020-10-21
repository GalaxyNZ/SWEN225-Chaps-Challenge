package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

/**
 *This class represents the recording aspect of the game.
 */
public class Record {
  public Queue<GraphicalUserInterface.Direction> q = new ArrayDeque<>();
  private Boolean isRecording = false;
  private Object Persistence;
  private String fileName;


  /**
   * Begins recording of gameplay by saving current state
   * of board and recording movements, then saving to Json file.
   */
  public void record(Maze maze) {

    JSONArray moves = new JSONArray();
    JSONObject file = new JSONObject();


    isRecording = true;
    //while (isRecording){
    while (!q.isEmpty()) {
      moves.add(q.poll().toString());
    }
    //}


    file.put("replayFile", fileName);
    file.put("moves", moves);
    file.put("time", maze.getTimeElapsed());


    try (FileWriter recFile = new FileWriter(fileName(fileName) + ".json")) {

      String fileString = file.toJSONString();
      for (int i = 0; i < fileString.length(); i++) {
        char next = fileString.charAt(i);
        if (next == ',' || next == '{') recFile.write(next + "\n\t");
        else if (next == '}') recFile.write("\n" + next);
        else recFile.write(next);

      }
      recFile.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void startRec(Maze maze){
    Persistence persistence = new Persistence();
    fileName = persistence.saveGame(maze);
  }

  /**
   * Adds moves to the queue for recording.
   * @param direction
   */
  public void addMove(GraphicalUserInterface.Direction direction){
    q.add(direction);
  }

  /**
   * Stops recording the movement so we can save to Json file.
   */
  public void stopRecording(){
    isRecording = false;
  }

  /**
   * Sets the recording name to "yyyy/MM/dd-HH:mm:ss" so that most file names are unique.
   * @return
   */
  public String fileName(String fileName){
    return "src/replays/" + fileName + "_moves";
  }

  public static void main(String[] args) {
    Record r = new Record();
  }

}