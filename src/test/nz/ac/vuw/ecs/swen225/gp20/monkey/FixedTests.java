package test.nz.ac.vuw.ecs.swen225.gp20.monkey;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.vuw.ecs.swen225.gp20.maze.Board;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import org.junit.jupiter.api.Test;

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
        *
     */

    @Test
    public void test1_ConstructBoard() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "#|_|_|_|#|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|";

        String moves = "";

        String expected =
                "|#| | | |#|\n" +
                        "| | | | | |\n" +
                        "| | |X| | |\n" +
                        "| | | | | |\n" +
                        "| | | | | |\n";

        assertEquals(expected, runTest(map));
    }

    @Test
    public void test2_ComplexBoard() {
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

        String moves = "";

        String expected =
                        "| | | | | | | | | | | | | | | | | |\n" +
                        "| | | |#|#|#|#|#| |#|#|#|#|#| | | |\n" +
                        "| | | |#| | | |#|#|#| | | |#| | | |\n" +
                        "| | | |#| |T| |#|%|#| |T| |#| | | |\n" +
                        "| |#|#|#|#|#|G|#|E|#|G|#|#|#|#|#| |\n" +
                        "| |#| |y| |B| | | | | |R| |y| |#| |\n" +
                        "| |#| |T| |#|b| |I| |r|#| |T| |#| |\n" +
                        "| |#|#|#|#|#|T| |X| |T|#|#|#|#|#| |\n" +
                        "| |#| |T| |#|b| | | |r|#| |T| |#| |\n" +
                        "| |#| | | |R| | |T| | |B| | | |#| |\n" +
                        "| |#|#|#|#|#|#|Y|#|Y|#|#|#|#|#|#| |\n" +
                        "| | | | | |#| | |#| | |#| | | | | |\n" +
                        "| | | | | |#| |T|#|T| |#| | | | | |\n" +
                        "| | | | | |#| | |#|g| |#| | | | | |\n" +
                        "| | | | | |#|#|#|#|#|#|#| | | | | |\n" +
                        "| | | | | | | | | | | | | | | | | |\n";

        assertEquals(expected, runTest(map));
    }

    @Test
    public void test3_SimpleMovement() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "a", "a", "s", "s", "d"};

        String expected =
                "| | | | | |\n" +
                        "| | | | | |\n" +
                        "| |X| | | |\n" +
                        "| | | | | |\n" +
                        "| | | | | |\n";

        assertEquals(expected, runTest(map, moves));
    }

    @Test
    public void test4_lockedDoors() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "#|#|B|#|#|"
                + "_|_|B|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "w"};

        String expected =
                "| | |X| | |\n" +
                        "|#|#| |#|#|\n" +
                        "| | | | | |\n" +
                        "| | | | | |\n" +
                        "| | | | | |\n";

        assertEquals(expected, runTest(map, moves));
    }



    private String runTest(String map) {
        // Create the game board and simulation
        Maze maze = new Maze(map);
        // Somehow run inputs

        return maze.getBoard().toString();
    }

    private String runTest(String map, String[] moves) {
        // Create the game board and simulation
        Maze maze = new Maze(map, moves);


        // Somehow run inputs

        return maze.getBoard().toString();
    }
}
