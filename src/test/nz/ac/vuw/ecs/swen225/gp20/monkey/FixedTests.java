package test.nz.ac.vuw.ecs.swen225.gp20.monkey;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.Board;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import java.util.Scanner;

public class FixedTests {

    /*
        * X = Chap
        *   = Free Tile
        * # = Wall
        * % = Exit lock item
        * I = Info tile
        * E = Exit tile
        * K = Key Item
        * L = Locked Door
        * T = Treasure Item
        * G = Green locked door
        * g = green key
        * B = Blue locked door
        * b = blue key
        * R = Red locked door
        * r = red key
        * Y = Yellow locked door
        * y = yellow key
     */


    // BASIC TESTS

    @Test
    public void t01_SimpleBoardGeneration() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "#|_|_|_|#|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_";

        JSONObject test = toJSON(map);

        String moves = "";

        String expected =
                "|#|_|_|_|#|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    @Test
    public void t02_ComplexBoardGeneration() {
        String map =  "17|16|SAMPLE TILE INFO|11|SETBK|1|SETYK|1|SETRK|1|SETGK|2|" +
                "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|" +
                "_|_|_|#|#|#|#|#|_|#|#|#|#|#|_|_|_|" +
                "_|_|_|#|_|_|_|#|#|#|_|_|_|#|_|_|_|" +
                "_|_|_|#|_|T|_|#|%|#|_|T|_|#|_|_|_|" +
                "_|#|#|#|#|#|G|#|E|#|G|#|#|#|#|#|_|" +
                "_|#|_|y|_|B|_|_|_|_|_|R|_|y|_|#|_|" +
                "_|#|_|T|_|#|b|_|I|_|r|#|_|T|_|#|_|" +
                "_|#|#|#|#|#|T|_|X|_|T|#|#|#|#|#|_|" +
                "_|#|_|T|_|#|b|_|_|_|r|#|_|T|_|#|_|" +
                "_|#|_|_|_|R|_|_|T|_|_|B|_|_|_|#|_|" +
                "_|#|#|#|#|#|#|Y|#|Y|#|#|#|#|#|#|_|" +
                "_|_|_|_|_|#|_|_|#|_|_|#|_|_|_|_|_|" +
                "_|_|_|_|_|#|_|T|#|T|_|#|_|_|_|_|_|" +
                "_|_|_|_|_|#|_|_|#|g|_|#|_|_|_|_|_|" +
                "_|_|_|_|_|#|#|#|#|#|#|#|_|_|_|_|_|" +
                "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_";

        String expected =
                "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|#|#|#|#|#|_|#|#|#|#|#|_|_|_|\n" +
                        "|_|_|_|#|_|_|_|#|#|#|_|_|_|#|_|_|_|\n" +
                        "|_|_|_|#|_|T|_|#|%|#|_|T|_|#|_|_|_|\n" +
                        "|_|#|#|#|#|#|G|#|E|#|G|#|#|#|#|#|_|\n" +
                        "|_|#|_|y|_|B|_|_|_|_|_|R|_|y|_|#|_|\n" +
                        "|_|#|_|T|_|#|b|_|I|_|r|#|_|T|_|#|_|\n" +
                        "|_|#|#|#|#|#|T|_|X|_|T|#|#|#|#|#|_|\n" +
                        "|_|#|_|T|_|#|b|_|_|_|r|#|_|T|_|#|_|\n" +
                        "|_|#|_|_|_|R|_|_|T|_|_|B|_|_|_|#|_|\n" +
                        "|_|#|#|#|#|#|#|Y|#|Y|#|#|#|#|#|#|_|\n" +
                        "|_|_|_|_|_|#|_|_|#|_|_|#|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|#|_|T|#|T|_|#|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|#|_|_|#|g|_|#|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|#|#|#|#|#|#|#|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n";


        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    @Test
    public void t03_SimpleMovement() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "a", "a", "s", "s", "d"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|X|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    // Restricted movement

    @Test
    public void t04_Walls() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|#|#|#|_|"
                + "_|#|X|#|_|"
                + "_|#|#|#|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "w"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|#|#|#|_|\n" +
                        "|_|#|X|#|_|\n" +
                        "|_|#|#|#|_|\n" +
                        "|_|_|_|_|_|\n";

        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }


    @Test
    public void t05_MovementOffMap() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "w"};

        String expected =
                "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";


        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    // DOORS and KEYS
    @Test
    public void t06_LockedDoors() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "#|#|B|#|#|"
                + "_|_|b|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "w"};

        String expected =
                "|_|_|X|_|_|\n" +
                        "|#|#|_|#|#|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";


        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }


    @Test
    public void t07_NoKeyUnlockingDoor() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "#|#|G|#|#|"
                + "_|_|b|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "w"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|#|#|G|#|#|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";


        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[b]", maze.getPlayerInv().toString());
    }

    @Test
    public void t08_IncorrectKeyUnlockingDoor() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "#|#|G|#|#|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "w"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|#|#|G|#|#|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";


        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }


    // ITEM TESTS

    @Test
    public void t09_PickUpTreasure() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|T|T|_|_|"
                + "_|T|T|T|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "a", "s", "d", "d"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|X|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";



        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(5, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    @Test
    public void t10_PickUpKeys() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|b|g|_|_|"
                + "_|y|r|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "a", "s"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|X|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";



        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[r, g, b, y]", maze.getPlayerInv().toString());
    }

    @Test
    public void t11_PickUpMultipleKeys() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|b|g|_|_|"
                + "_|y|r|_|_|"
                + "_|y|X|_|_|"
                + "_|b|g|r|_|";

        String[] moves = new String[]{ "w", "w", "a", "s", "s", "s", "d", "d"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|X|_|\n";



        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[r, g, b, y, y, b, g, r]", maze.getPlayerInv().toString());
    }

    // Ending Game
    @Test
    public void t12_SimpleGameEnd() {
        String map =  "5|5|SAMPLE TILE INFO|0|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|%|_|_|"
                + "_|_|E|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";



        JSONObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJSONString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
        assertTrue(maze.levelWonChecker());
    }

    public void gameplayLoop(Maze maze, String[] moves) {
        for (String s : moves) {
            switch (s) {
                case "s":
                    maze.executeMove(GUI.direction.DOWN);
                    break;
                case "w":
                    maze.executeMove(GUI.direction.UP);
                    break;
                case "a":
                    maze.executeMove(GUI.direction.LEFT);
                    break;
                case "d":
                    maze.executeMove(GUI.direction.RIGHT);
                    break;
            }
        }
    }

    public static JSONObject toJSON(String map) {
        Scanner scan = new Scanner(map);
        scan.useDelimiter("\\|");

        JSONObject test = new JSONObject();
        test.put("xSize", scan.next());
        test.put("ySize", scan.next());
        test.put("tileInfo", scan.next());
        test.put("numChips", scan.next());        scan.next();
        test.put("SETBK", scan.next());           scan.next();
        test.put("SETYK", scan.next());           scan.next();
        test.put("SETRK", scan.next());           scan.next();
        test.put("SETGK", scan.next());
        scan.skip("\\|");
        scan.useDelimiter("\n");

        test.put("board", scan.next().replace("|", ","));

        return test;
    }
}
