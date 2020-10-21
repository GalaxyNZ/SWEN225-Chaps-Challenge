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

    public Maze newGame(){
        return loadFile(path +"level1.json");
    }

    public int getLevelAmount(){ return fileCount; }

    public Maze loadFile(String file) { //read
        Maze maze = null;

        File directory = new File(path);
        fileCount = directory.list().length;
        System.out.println("File Count:"+fileCount);

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


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maze;

    }

    public String saveGame(Maze maze) {

        JSONObject file = new JSONObject();
        JSONArray playerInv = new JSONArray();


        for(Item i: maze.getPlayerInv()){
         playerInv.add(i.toString());
        }

        file.put("xSize", maze.getBoardSize().getX());
        file.put("ySize", maze.getBoardSize().getY());
        file.put("tileInfo", "something");
        file.put("SETGK", 3);
        file.put("SETBK", 2);
        file.put("SETYK", 1);
        file.put("SETRK", 2);
        file.put("numChips", maze.chipsRemaining());
        file.put("playerInv", playerInv);
       //file.put("time", maze.timeElapsed);
        file.put("board", maze.toString());


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


    public void readBoard(Board board) {
        Scanner sc = new Scanner(map.get("board").toString()).useDelimiter(",");
        int numChips = (int) Double.parseDouble(map.get("numChips").toString());
        int x = 0;
        int y = 0;

        while (sc.hasNext()) {
            Point location = new Point(x, y);
            String character = sc.next();
            switch (character) {
                case "_":
                    board.setTileAt(x, y, new FreeTile(location, null));
                    break;
                case "#":
                    board.setTileAt(x, y, new WallTile(location));
                    break;
                case "E":
                    board.setTileAt(x, y, new ExitTile(location));
                    break;
                case "l":
                    board.setTileAt(x, y, new FreeTile(location, new ExitLockItem(numChips)));
                    break;
                case "i":
                    //board.setTileAt(x, y, new InfoTile(location));
                    break;
                case "T":
                    board.setTileAt(x, y, new FreeTile(location, new TreasureItem()));
                    break;
                case "G":
                    board.setTileAt(x, y, new FreeTile(location, new LockedDoorItem("G")));
                    break;
                case "g":
                    board.setTileAt(x, y, new FreeTile(location, new KeyItem("G", 1)));
                    break;
                case "R":
                    board.setTileAt(x, y, new FreeTile(location, new LockedDoorItem("R")));
                    break;
                case "r":
                    board.setTileAt(x, y, new FreeTile(location, new KeyItem("R", 1)));
                    break;
                case "Y":
                    board.setTileAt(x, y, new FreeTile(location, new LockedDoorItem("Y")));
                    break;
                case "y":
                    board.setTileAt(x, y, new FreeTile(location, new KeyItem("Y", 1)));
                    break;
                case "B":
                    board.setTileAt(x, y, new FreeTile(location, new LockedDoorItem("B")));
                    break;
                case "b":
                    board.setTileAt(x, y, new FreeTile(location, new KeyItem("B", 1)));
                    break;
                case "C":
                    board.setTileAt(x, y, new FreeTile(location, new Chap()));
                    Point point = new Point(x, y);
                    player = new Player(point, numChips);
                    board.setPlayerLocation(point);
                    break;
            }
            x++;
            if (x == boardWidth) {
                x = 0;
                y++;
            }
        }
    }
}