package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;


public class Persistence {
  public static void main(String[] args) { //read

    try {
      // create Gson instance
      Gson gson = new Gson();

      String path = "src/nz/ac/vuw/ecs/swen225/gp20/persistence/levels/";

      JFileChooser chooser = new JFileChooser(path);
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JSON files", "json");
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(null);
      if(returnVal == JFileChooser.APPROVE_OPTION) {
        System.out.println("You chose to open this file: " +
                chooser.getSelectedFile().getName());
      }
      else return;



      // create a reader
     // Path pathToFile = Paths.get(path +"level1.json");
      //System.out.println(pathToFile.toAbsolutePath());
      //Reader reader = Files.newBufferedReader(Paths.get(path +"level1.json"));
      //System.out.println(chooser.getSelectedFile().getPath());
      Reader reader = Files.newBufferedReader(Paths.get(chooser.getSelectedFile().getPath()));



      //convert to Gson
      // convert JSON file to map
      Map<String, String> map = gson.fromJson(reader, Map.class);

      // print map entries
      for (Map.Entry<String, String> entry : map.entrySet()) {

        String varName = entry.getKey();
        if(varName.equals("board")){
          readBoard(entry.getValue());
        }
        else System.out.println(entry.getKey() + " = " + entry.getValue());
      }

      reader.close();

    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  public static void readBoard(String maze){
    Scanner sc = new Scanner(maze).useDelimiter(",");
    int count = 0;

    while(sc.hasNext()){
      System.out.print("|");
      System.out.print(sc.next());
      count++;
      if( count == 15){
        System.out.print("|");
        System.out.println();
        count = 0;
      }
    }
  }
}
