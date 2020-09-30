package test.nz.ac.vuw.ecs.swen225.gp20.monkey;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.vuw.ecs.swen225.gp20.maze.Board;
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
                + "W|F|F|F|W|"
                + "F|F|F|F|F|"
                + "F|F|F|F|F|"
                + "F|F|F|F|F|"
                + "F|F|F|F|F|";

        String moves = "";

        String expected =
                "|#| | | |#|\n" +
                        "| | | | | |\n" +
                        "| | | | | |\n" +
                        "| | | | | |\n" +
                        "| | | | | |\n";

        assertEquals(expected, runTest(map));
    }

    @Test
    public void test2_ComplexBoard() {
        String map =  "17|16|SAMPLE TILE INFO|11|SETBK|1|SETYK|1|SETRK|1|SETGK|2|"
                + "F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|"
                + "F|F|F|W|W|W|W|W|F|W|W|W|W|W|F|F|F|"
                + "F|F|F|W|F|F|F|W|W|W|F|F|F|W|F|F|F|"
                + "F|F|F|W|F|T|F|W|EXT|W|F|T|F|W|F|F|F|"
                + "F|W|W|W|W|W|GKD|W|EXTLCK|W|GKD|W|W|W|W|W|F|"
                + "F|W|F|YK|F|BKD|F|F|F|F|F|RKD|F|YK|F|W|F|"
                + "F|W|F|T|F|W|BK|F|I|F|RK|W|F|T|F|W|F|"
                + "F|W|W|W|W|W|T|F|CHAP|F|T|W|W|W|W|W|F|"
                + "F|W|F|T|F|W|BK|F|F|F|RK|W|F|T|F|W|F|"
                + "F|W|F|F|F|RKD|F|F|T|F|F|BKD|F|F|F|W|F|"
                + "F|W|W|W|W|W|W|YKD|W|YKD|W|W|W|W|W|W|F|"
                + "F|F|F|F|F|W|F|F|W|F|F|W|F|F|F|F|F|"
                + "F|F|F|F|F|W|F|T|W|T|F|W|F|F|F|F|F|"
                + "F|F|F|F|F|W|F|F|W|GK|F|W|F|F|F|F|F|"
                + "F|F|F|F|F|W|W|W|W|W|W|W|F|F|F|F|F|"
                + "F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|F|";

        String moves = "";

        String expected =
                        "| | | | | | | | | | | | | | | | | |\n" +
                        "| | | |#|#|#|#|#| |#|#|#|#|#| | | |\n" +
                        "| | | |#| | | |#|#|#| | | |#| | | |\n" +
                        "| | | |#| |T| |#|E|#| |T| |#| | | |\n" +
                        "| |#|#|#|#|#|G|#|%|#|G|#|#|#|#|#| |\n" +
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
                + "F|F|F|F|F|"
                + "F|F|F|F|F|"
                + "F|F|X|F|F|"
                + "F|F|F|F|F|"
                + "F|F|F|F|F|";

        String[] moves = new String[]{ "w", "w", "a", "a", "s", "s", "d"};

        String expected =
                        "| | | | | |\n" +
                        "| | | | | |\n" +
                        "| |X| | | |\n" +
                        "| | | | | |\n" +
                        "| | | | | |\n";

        assertEquals(expected, runTest(map, moves));
    }

    private String runTest(String map) {
        // Create the game board and simulation
        Board board = new Board(map);
        // Somehow run inputs

        return board.toString();
    }

    private String runTest(String map, String[] moves) {
        // Create the game board and simulation
        Board board = new Board(map);


        // Somehow run inputs

        return board.toString();
    }
}
