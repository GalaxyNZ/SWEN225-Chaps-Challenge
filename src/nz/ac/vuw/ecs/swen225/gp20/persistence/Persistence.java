package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nz.ac.vuw.ecs.swen225.gp20.rendering.Rendering;

import javax.json.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


public class Persistence {
  public static void main(String[] args) { //read

    try {
      // create Gson instance
      Gson gson = new Gson();

      String path = "src/nz/ac/vuw/ecs/swen225/gp20/persistence/levels/";

      // create a reader
      Path pathToFile = Paths.get(path +"level1.json");
      System.out.println(pathToFile.toAbsolutePath());
      Reader reader = Files.newBufferedReader(Paths.get(path +"level1.json"));

      //convert to Gson
      // convert JSON file to map
      Map<?, ?> map = gson.fromJson(reader, Map.class);

      // print map entries
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        //System.out.println(entry.getKey() + "=" + entry.getValue());
        System.out.println(entry.getValue());
      }

      // close reader
      reader.close();

    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }
}
