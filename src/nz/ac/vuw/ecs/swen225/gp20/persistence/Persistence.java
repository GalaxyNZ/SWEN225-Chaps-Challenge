package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;


public class Persistence {
    Map<?, ?> map;
    int boardWidth;
    int boardHeight;
    Player player;

    public Maze selctFile() {

        String path = "src/nz/ac/vuw/ecs/swen225/gp20/persistence/levels/";

        JFileChooser chooser = new JFileChooser(path);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JSON files", "json");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return loadFile(chooser.getSelectedFile().toString());
        } else return null;
    }

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


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return maze;

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