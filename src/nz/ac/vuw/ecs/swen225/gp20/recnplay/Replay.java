package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Replay {
  Main main;
  ArrayList<GraphicalUserInterface.Direction> moves;
  Timer timer;
  float timeElapsed;

  String path = "src/replays/";

  public Replay(Main main){
    this.main = main;

  }

  public Maze loadReplay(){
    //main.persistence.loadFile();
   // selectFile();
    moves = new ArrayList<>();
    return selectFile();
  }

  public void autoStep(int delayTime){
    //while (!moves.isEmpty()){
    //  iterateStep();

   // }
    timer = new Timer(delayTime, e -> {
      iterateStep();
    });
    timer.start();
  }

  public void iterateStep(){
    if (!moves.isEmpty() && main.currentState == Main.State.REPLAYING){
      main.getMaze().executeMove(moves.remove(0));
    }
    else {
      main.stopReplaying();
      if (timer != null) timer.stop();
      main.setTimeElapsed(timeElapsed);
    }
  }

  public Maze selectFile() {

    String selectedFile = "";

    JFileChooser chooser = new JFileChooser(path);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JSON files", "json");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(null);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      selectedFile += chooser.getSelectedFile().toString();
      return loadFile(selectedFile);
    } else return null;
  }

  public Maze loadFile(String file) { //read
    Maze maze = null;
    Map<?, ?> map;
    String path = "src/nz/ac/vuw/ecs/swen225/gp20/persistence/levels/";

    try {
      // create Gson instance
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get(file));
      map = gson.fromJson(reader, Map.class);
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        String varName = entry.getKey().toString();
        System.out.println(entry.getKey() + " = " + entry.getValue());

      }
      loadMoves(map.get("moves").toString());
      timeElapsed = Float.parseFloat(map.get("time").toString());
      maze = main.getPersistence().loadFile(path + map.get("replayFile").toString() + ".json");
      reader.close();
      //maze = new Maze(map);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return maze;

  }

  public void loadMoves(String moves){
    this.moves = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder(moves);
    for (int i = 0; i < moves.length(); i++){
      if (moves.charAt(i) == '[' || moves.charAt(i) == ']' || moves.charAt(i) == '\n' || moves.charAt(i) == ' '){
        stringBuilder.deleteCharAt(i);
      }
      moves = stringBuilder.toString();
    }
    ArrayList<String> delimitedInput = new ArrayList<String>(Arrays.asList(moves.split("[,]")));
    for (String s : delimitedInput){
      this.moves.add(GraphicalUserInterface.Direction.valueOf(s));
    }
  }

}