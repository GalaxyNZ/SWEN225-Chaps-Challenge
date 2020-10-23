package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import com.google.gson.Gson;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

/**
 * Replay class.
 * Responsible for moving things according to recorded movements.
 *
 * @author Philip
 */
public class Replay {
  Main main;
  ArrayList<String> moves;
  Timer timer;
  float timeElapsed;

  String path = "src/nz/ac/vuw/ecs/swen225/gp20/recnplay/replays/";

  /**
   * Constructor to move things in main.
   *
   * @param main from application to move entities
   */
  public Replay(Main main) {
    this.main = main;
  }

  /**
   * Clear movements for current recording and call selectFile.
   *
   * @return selected file
   */
  public Maze loadReplay() {
    moves = new ArrayList<>();
    return selectFile();
  }

  /**
   * Automate movements in replay.
   *
   * @param delayTime to move in time
   */
  public void autoStep(int delayTime) {
    timer = new Timer(delayTime, e -> iterateStep());
    timer.start();
  }

  /**
   * Do one step at every call.
   */
  public void iterateStep() {
    if (!main.gamePaused) {
      if (!moves.isEmpty() && main.currentState == Main.State.REPLAYING) {
        String nextMoves = moves.remove(0);
        if (nextMoves.equals("ENEMIES")) {
          main.moveEnemies();
        } else {
          main.movePlayerDirection(GraphicalUserInterface.Direction.valueOf(nextMoves));
        }
      } else {
        main.stopReplaying();
        if (timer != null) {
          timer.stop();
        }
        main.setTimeElapsed(timeElapsed);
      }
    }
  }

  /**
   * Allow a file to be selected as long as it is a Json file.
   *
   * @return selectedFile
   */
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
    } else {
      return null;
    }
  }

  /**
   * Load selected file and read the Json.
   *
   * @param file as string from selected
   * @return maze to be shown
   */
  public Maze loadFile(String file) { //read
    Maze maze = null;
    Map<?, ?> map;
    String path = "src/nz/ac/vuw/ecs/swen225/gp20/persistence/files/saves/";

    try {
      // create Gson instance
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get(file));
      map = gson.fromJson(reader, Map.class);
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        System.out.println(entry.getKey() + " = " + entry.getValue());

      }
      loadMoves(map.get("moves").toString());
      timeElapsed = Float.parseFloat(map.get("time").toString());
      maze = main.getPersistence().loadFile(path + map.get("replayFile").toString() + ".json");
      reader.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return maze;

  }

  /**
   * Load moves into ArrayList.
   *
   * @param moves from Json
   */
  public void loadMoves(String moves) {
    this.moves = new ArrayList<>();
    StringBuilder stringBuilder = new StringBuilder(moves);
    for (int i = 0; i < moves.length(); i++) {
      if (moves.charAt(i) == '[' || moves.charAt(i) == ']'
              || moves.charAt(i) == '\n' || moves.charAt(i) == ' ') {
        stringBuilder.deleteCharAt(i);
      }
      moves = stringBuilder.toString();
    }
    ArrayList<String> delimitedInput = new ArrayList<>(Arrays.asList(moves.split("[,]")));
    this.moves.addAll(delimitedInput);
  }

}