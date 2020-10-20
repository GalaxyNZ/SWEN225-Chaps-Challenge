package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
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
  //private ArrayList<String> moves = new ArrayList<>();
  public Queue<GraphicalUserInterface.Direction> q = new ArrayDeque<>();
  private Boolean isRecording = false;


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
      moves.add(q.poll());
    }
    //}

    file.put("xSize", maze.getBoardSize().getX());
    file.put("ySize", maze.getBoardSize().getY());
    file.put("tileInfo", "something");
    file.put("SETGK", 3);
    file.put("SETBK", 2);
    file.put("SETYK", 1);
    file.put("SETRK", 2);
    file.put("board", maze.toString());
    file.put("moves", moves);

    try (FileWriter recFile = new FileWriter(fileName() + ".json")) {

      recFile.write(file.toJSONString());
      recFile.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
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
  public String fileName(){
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    return dateFormat.format(date);
  }

  public static void main(String[] args) {
    Record r = new Record();
    // r.record();
  }

}