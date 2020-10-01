package nz.ac.vuw.ecs.swen225.gp20.recnplay;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;


public class Record {
  //private ArrayList<String> moves = new ArrayList<>();
  public Queue<String> q = new ArrayDeque<>();
  private Boolean isRecording = false;

  public void record() {

    q.add("a");q.add("b");q.add("c");q.add("d");q.add("e");

    JSONArray moves = new JSONArray();

    JSONObject file = new JSONObject();

    while (isRecording){
      if (!q.isEmpty()) {
        moves.add(q.poll());
      }
    }

    file.put("xSize", 50);
    file.put("ySize", 50);
    file.put("tileInfo", "boop beep boop");
    file.put("SETGK", 3);
    file.put("SETBK", 2);
    file.put("SETYK", 1);
    file.put("SETRK", 2);
    file.put("board", "boop beep boop");
    file.put("moves", moves);

    try (FileWriter recFile = new FileWriter("recording.json")) {

      recFile.write(file.toJSONString());
      recFile.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  public static void main(String[] args) {
    Record r = new Record();
    r.record();
  }

}