

package test.nz.ac.vuw.ecs.swen225.gp20.monkey;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.vuw.ecs.swen225.gp20.maze.Board;
import org.junit.jupiter.api.Test;

public class FixedTests {

    @Test
    public void test1_ConstructBoard() {
        int[] board = {
                9, 9, 9,
                9, 0, 9,
                9, 9, 9
        };

        int[] expected = {
                9, 9, 9,
                9, 2, 9,
                9, 9, 9
        };

        runTest("|_|_|_|_|_|", "abc", "123");
    }

    private void runTest(String OrigBoard, String inputs, String ExpOutput) {
        // Create the game board and simulation
        Board board = new Board(inputs);
    }
}
