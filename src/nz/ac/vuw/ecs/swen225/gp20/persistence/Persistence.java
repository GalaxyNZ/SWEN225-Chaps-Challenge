package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import nz.ac.vuw.ecs.swen225.gp20.application.Main;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;
import nz.ac.vuw.ecs.swen225.gp20.recnplay.Record;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;


public class Persistence {
    Map<?, ?> map;
    int boardWidth;
    int boardHeight;
    Player player;
    String path = "src/nz/ac/vuw/ecs/swen225/gp20/persistence/levels/";
    String selectedFile = "";
    int fileCount = 0;

    public Persistence(){
        File directory = new File(path);
        fileCount = directory.list().length;
        System.out.println("File Count:"+fileCount);
    }

    /**
     * @return maze created.
     */
    public Maze selectFile() {

        JFileChooser chooser = new JFileChooser(path);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON files", "json");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile += chooser.getSelectedFile().toString();
            return loadFile(chooser.getSelectedFile().toString());
        } else return null;
    }

    public Maze restart(){
        return loadFile(selectedFile);
    }

    public Maze nextLevel(Main main){
        int level = main.getMaze().getLevel();
        if(level < fileCount) {
            selectedFile = path + "level" + level + ".json";
            return loadFile(selectedFile);
        }
        main.gameWon();
        return null;
    }

    public String getMoves(){
        if(map != null && map.containsKey("moves")){
            return map.get("moves").toString();
        }
        return null;
    }

    public Maze newGame(){
        selectedFile = path +"level1.json";
        return loadFile(selectedFile);
    }

    public int getLevelAmount(){ return fileCount; }

    public Maze loadFile(String file) { //read
        Maze maze = null;

        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(file));

            //convert to Gson
            // convert JSON file to map
            map = gson.fromJson(reader, Map.class);

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                String varName = entry.getKey().toString();
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            reader.close();
            boardWidth = (int) Double.parseDouble(map.get("xSize").toString());
            boardHeight = (int) Double.parseDouble(map.get("ySize").toString());
            //Board board = new Board(boardWidth, boardHeight);
            //readBoard(board);
            //maze = new Maze(board, player);
            maze = new Maze(map);
            maze.setTimeElapsed(Float.parseFloat(map.get("time").toString()));


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maze;

    }

    public String saveGame(Maze maze) {

        JSONObject file = new JSONObject();
        JSONArray playerInv = new JSONArray();


       // for(Item i: maze.getPlayerInv()){
        // playerInv.add(i.toString());
        //}

        file.put("xSize", maze.getBoardSize().getX());
        file.put("ySize", maze.getBoardSize().getY());
        file.put("tileInfo", "something");
        file.put("SETGK", 2);
        file.put("SETBK", 1);
        file.put("SETYK", 1);
        file.put("SETRK", 1);
        file.put("numChips", maze.chipsRemaining());
        //file.put("playerInv", playerInv);
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


        String fileName = fileName();
        try (FileWriter saveFile = new FileWriter(path + fileName + ".json")) {
            String fileString = file.toJSONString();
            for (int i = 0; i < fileString.length(); i++) {
                char next = fileString.charAt(i);
                if (next == ',' || next == '{') saveFile.write(next + "\n\t");
                else if (next == '}') saveFile.write("\n" + next);
                else saveFile.write(next);
            }

            //saveFile.write(file.toJSONString());
            saveFile.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static String fileName(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return dateFormat.format(date);
    }

}