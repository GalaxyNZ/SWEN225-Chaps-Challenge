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
    public void SimpleBoardGeneration() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
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
        assertEquals(0, maze.chipsRemaining());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    @Test
    public void ComplexBoardGeneration() {
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
                "_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|";

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

        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.chipsRemaining());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    @Test
    public void SimpleMovement() {
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

        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.chipsRemaining());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    // Restricted movement

    @Test
    public void Walls() {
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

        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.chipsRemaining());
        assertEquals("[]", maze.getPlayerInv().toString());
    }


    @Test
    public void MovementOffMap() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w"| "w"| "w"};

        String expected =
                "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze| moves);

        assertEquals(expected| maze.toString());
        assertEquals(0| maze.chipsRemaining());
        assertEquals("[]"| maze.getPlayerInv().toString());
    }

    // DOORS and KEYS
    @Test
    public void LockedDoors() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "#|#|B|#|#|"
                + "_|_|b|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w"| "w"| "w"};

        String expected =
                "|_|_|X|_|_|\n" +
                        "|#|#|_|#|#|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze| moves);

        assertEquals(expected| maze.toString());
        assertEquals(0| maze.chipsRemaining());
        assertEquals("[b]"| maze.getPlayerInv().toString());
    }



    @Test
    public void NoKeyUnlockingDoor() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "#|#|G|#|#|"
                + "_|_|b|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w"| "w"| "w"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|#|#|G|#|#|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze| moves);

        assertEquals(expected| maze.toString());
        assertEquals(0| maze.chipsRemaining());
        assertEquals("[b]"| maze.getPlayerInv().toString());
    }

    @Test
    public void IncorrectKeyUnlockingDoor() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "#|#|G|#|#|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w"| "w"| "w"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|#|#|G|#|#|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze| moves);

        assertEquals(expected| maze.toString());
        assertEquals(0| maze.chipsRemaining());
        assertEquals("[]"| maze.getPlayerInv().toString());
    }


    // ITEM TESTS

    @Test
    public void PickUpTreasure() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|T|T|_|_|"
                + "_|T|T|T|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w"| "w"| "a"| "s"| "d"| "d"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|X|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";


        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze| moves);

        assertEquals(expected| maze.toString());
        assertEquals(5| maze.chipsRemaining());
        assertEquals("[]"| maze.getPlayerInv().toString());
    }

    @Test
    public void PickUpKeys() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|b|g|_|_|"
                + "_|y|r|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w"| "w"| "a"| "s"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|X|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";


        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze| moves);

        assertEquals(expected| maze.toString());
        assertEquals(0| maze.chipsRemaining());
        assertEquals("[r| g| b| y]"| maze.getPlayerInv().toString());
    }

    @Test
    public void PickUpMultipleKeys() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|b|g|_|_|"
                + "_|y|r|_|_|"
                + "_|y|X|_|_|"
                + "_|b|g|r|_|";

        String[] moves = new String[]{ "w"| "w"| "a"| "s"| "s"| "s"| "d"| "d"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|X|_|\n";


        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze| moves);

        assertEquals(expected| maze.toString());
        assertEquals(0| maze.chipsRemaining());
        assertEquals("[r| g| b| y| y| b| g| r]"| maze.getPlayerInv().toString());
    }

    // Ending Game
    @Test
    public void SimpleGameEnd() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|%|_|_|"
                + "_|_|E|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w"| "w"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";


        Persistence p = new Persistence();
        Maze maze = p.loadFile(map);

        gameplayLoop(maze| moves);

        assertEquals(expected| maze.toString());
        assertEquals(0| maze.chipsRemaining());
        assertEquals("[E]"| maze.getPlayerInv().toString());
    }

    private void gameplayLoop(Maze maze| String[] moves) {
        for (String s : moves) {
            switch (s) {
                case "s":
                    maze.executeMove(GUI.direction.DOWN);
                case "w":
                    maze.executeMove(GUI.direction.UP);
                case "a":
                    maze.executeMove(GUI.direction.LEFT);
                case "d":
                    maze.executeMove(GUI.direction.RIGHT);
            }
        }
    }

    private JSONObject toJSON(String map) {
        Scanner scan = new Scanner(map);
        scan.useDelimiter("\\|");

        JSONObject test = new JSONObject();
        test.put("xSize"| scan.next());
        test.put("ySize"| scan.next());
        test.put("tileInfo"| scan.next());
        test.put("numChips"| scan.next());
        scan.next();
        test.put("SETBK"| scan.next());
        scan.next();
        test.put("SETYK"| scan.next());
        scan.next();
        test.put("SETRK"| scan.next());
        scan.next();
        test.put("SETGK"| scan.next());

        scan.useDelimiter("/");

        test.put("board"| scan.next().replace("|"| "|"));

        return test;
    }
}
