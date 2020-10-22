package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.KeyItem;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;


public class Persistence {
  Map<?, ?> map;
  String levelPath = "src/nz/ac/vuw/ecs/swen225/gp20/files/levels/";
  String loadPath = "src/nz/ac/vuw/ecs/swen225/gp20/files/";
  String savePath = "src/nz/ac/vuw/ecs/swen225/gp20/files/saves/";
  String selectedFile = "";
  int fileCount = 0;

  /**
   * Creates the Persistence.
   * initializes fileCount to end game.
   */
  public Persistence() {
    File directory = new File(loadPath);
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

    JFileChooser chooser = new JFileChooser(loadPath);
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JSON files", "json");          // only accepts json files.
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(null);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      selectedFile = chooser.getSelectedFile().toString();    // set selected file to exactly that.
      return loadFile(chooser.getSelectedFile().toString());  //
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
    int level = main.getMaze().getLevel(); //gets current level.
    if (level < fileCount) { //checks if there can be a next level comparing to total levels.
      selectedFile = levelPath + "level" + (level + 1) + ".json"; //gets next level number.
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
    selectedFile = levelPath + "level1.json";
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

      reader.close();
      maze = new Maze(map);
      maze.setTimeElapsed(Float.parseFloat(map.get("time").toString()));
      //gets the time elapsed and sets the current time.


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
    HashMap<String, Integer> config = new HashMap();
    JsonBuilderFactory factory = Json.createBuilderFactory(config);

    JsonObjectBuilder object = factory.createObjectBuilder();

    object.add("xSize", maze.getBoardSize().getX()); //add all of the variables the the json object.
    object.add("ySize", maze.getBoardSize().getY());
    object.add("tileInfo", "something");
    object.add("SETGK", 2);
    object.add("SETBK", 1);
    object.add("SETYK", 1);
    object.add("SETRK", 1);
    object.add("numChips", maze.chipsRemaining());
    object.add("time", maze.getTimeElapsed());
    object.add("level", maze.getLevel());
    object.add("board", maze.toString());

    if (maze.getNumMonsters() > 0) {      //adds number of monsters if there is supposed to be some.
      object.add("numBugs", maze.getNumMonsters());
    }
    //gets the current moves that a bug needs to do in its loop and saves the file.
    for (HashMap<Integer, ArrayList<String>> map : maze.getBugMoves()) {
      for (Map.Entry<Integer, ArrayList<String>> monster : map.entrySet()) {
        StringBuilder moves = new StringBuilder();
        for (String move : monster.getValue()) {
          moves.append(move);
          moves.append("|");
        }
        object.add("enemy" + monster.getKey(), moves.toString());
      }
    }
    ArrayList<Item> inv = maze.getPlayerInv(); //adds the players inventory to the object.
    if (!inv.isEmpty()) {
      StringBuilder inventory = new StringBuilder();
      for (Item i : inv) {
        if (i instanceof KeyItem) {
          inventory.append(i.getColor()).append("|");
        }
      }
      object.add("inventory", inventory.toString());
    }

    String fileName = fileName();
    try { //writes the json object to the file.
      Writer stringWriter = new StringWriter();
      Json.createWriter(stringWriter).write(object.build());
      String savedGame = stringWriter.toString();
      int saveLength = savedGame.length();
      stringWriter.close();

      Writer writer = new BufferedWriter(new FileWriter(savePath + fileName + ".json"));

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
    return fileName;
  }

  /**
   * gates the date to be used as the file name.
   *
   * @return date
   */
  public static String fileName() { // creates file name from the current date.
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    return dateFormat.format(date);
  }

}