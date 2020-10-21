package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.KeyItem;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import org.json.simple.JSONObject;



public class Persistence {
  Map<?, ?> map;
  String path = "src/nz/ac/vuw/ecs/swen225/gp20/persistence/levels/";
  String selectedFile = "";
  int fileCount = 0;

  /**
   * Creates the Persistence.
   * initializes fileCount to end game.
   */
  public Persistence() {
    File directory = new File(path);
    String[] list = directory.list();
    if (list != null) {
      fileCount = list.length;
    }
  }

  /**
   * Create file chooser so that the user is able to select the file that they want to load.
   *
   * @return maze created.
   */
  public Maze selectFile() {

    JFileChooser chooser = new JFileChooser(path);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JSON files", "json");
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(null);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      selectedFile = chooser.getSelectedFile().toString();
      return loadFile(chooser.getSelectedFile().toString());
    } else {
      return null;
    }
  }

  /**
   * Restarts the game from the last file selected.
   *
   * @return maze created.
   */
  public Maze restart() {
    return loadFile(selectedFile);
  }

  /**
   * Loads the next level after the current by grabbing
   * the current level number and adding one to it.
   *
   * @param main object.
   *
   * @return next level.
   */
  public Maze nextLevel(Main main) {
    int level = main.getMaze().getLevel();
    if (level < fileCount) {
      selectedFile = path + "level" + (level + 1) + ".json";
      return loadFile(selectedFile);
    }
    main.gameWon();
    return null;
  }

  /**
   * Some method I mad fro Philip and he didnt even use it :(.
   *
   * @return string of moves.
   */
  public String getMoves() {
    if (map != null && map.containsKey("moves")) {
      return map.get("moves").toString();
    }
    return null;
  }

  /**
   * Loads first level in the game.
   *
   * @return selected file.
   */
  public Maze newGame() {
    selectedFile = path + "level1.json";
    return loadFile(selectedFile);
  }


  /**
   * Loads the json file reads it into a map.
   *
   * @param file that is being loaded into maze.
   *
   * @return maze created.
   */
  public Maze loadFile(String file) { //read
    Maze maze = null;
    selectedFile = file;

    try {
      // create Gson instance
      Gson gson = new Gson();

      // create a reader
      Reader reader = Files.newBufferedReader(Paths.get(file));

      //convert to Gson
      // convert JSON file to map
      map = gson.fromJson(reader, Map.class);

      // print map entries

      reader.close();
      maze = new Maze(map);
      maze.setTimeElapsed(Float.parseFloat(map.get("time").toString()));


    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return maze;
  }

  /**
   * Loads the string reads it into a map (for testing).
   *
   * @param file in string form that is being loaded into maze.
   *
   * @return maze created.
   */
  public Maze loadJsonString(String file) {

    Maze maze = null;

    try {
      // create Gson instance
      Gson gson = new Gson();

      //convert to Gson
      // convert JSON file to map
      map = gson.fromJson(file, Map.class);

      // print map entries
      maze = new Maze(map);


    } catch (Exception ex) {
      System.out.println("something went wrong");
      ex.printStackTrace();
    }
    return maze;
  }

  /**
   * Saves the current maze and its information into a json file.
   *
   * @param maze object to save.
   *
   * @return file name.
   */
  public String saveGame(Maze maze) {

    JSONObject file = new JSONObject();

    file.put("xSize", maze.getBoardSize().getX());
    file.put("ySize", maze.getBoardSize().getY());
    file.put("tileInfo", "something");
    file.put("SETGK", 2);
    file.put("SETBK", 1);
    file.put("SETYK", 1);
    file.put("SETRK", 1);
    file.put("numChips", maze.chipsRemaining());
    file.put("time", maze.getTimeElapsed());
    file.put("level", maze.getLevel());
    file.put("board", maze.toString());
    ArrayList<Item> inv = maze.getPlayerInv();
    if (!inv.isEmpty()) {
      StringBuilder inventory = new StringBuilder();
      for (Item i : inv) {
        if (i instanceof KeyItem) {
          inventory.append(i.getColor()).append("|");
        }
      }
      file.put("inventory", inventory.toString());
    }
    if (maze.getNumMonsters() > 0) {
      file.put("numBugs", maze.getNumMonsters());
    }
    for (HashMap<Integer, ArrayList<String>> map : maze.getBugMoves()) {
      for (Map.Entry<Integer, ArrayList<String>> monster : map.entrySet()) {
        StringBuilder moves = new StringBuilder();
        for (String move : monster.getValue()) {
          moves.append(move);
          moves.append("|");
        }
        file.put("enemy" + monster.getKey(), moves.toString());
      }
    }


    String fileName = fileName();
    try (FileWriter saveFile = new FileWriter(path + fileName + ".json")) {
      String fileString = file.toJSONString();
      for (int i = 0; i < fileString.length(); i++) {
        char next = fileString.charAt(i);
        if (next == ',' || next == '{') {
          saveFile.write(next + "\n\t");
        } else if (next == '}') {
          saveFile.write("\n" + next);
        } else {
          saveFile.write(next);
        }
      }

      saveFile.flush();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileName;
  }

  /**
   * gates the date to be used as the file name.
   *
   * @return date
   */
  public static String fileName() {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    return dateFormat.format(date);
  }

}