package test.nz.ac.vuw.ecs.swen225.gp20.monkey;

import static org.junit.jupiter.api.Assertions.*;

import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import org.junit.jupiter.api.Test;

import javax.json.*;
import java.util.HashMap;
import java.util.Scanner;

/*
 * This class represents the fixed testing part of this project.
 * Here tests are run to ensure all core systems are running properly so the game can be played.
 *
 * @author Luke Catherall - catherluke
 */

public class FixedTests {

    /*
        * Key table for the board toString output

        * X = Chap
        * _ = Free Tile
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
    /*
     * Tests simple board generation
     */
    @Test
    public void t01_SimpleBoardGeneration() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "#|_|_|_|#|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_";

        JsonObject test = toJSON(map); // Convers map string to a JSON file

        String moves = "";

        String expected =
                "|#|_|_|_|#|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        Persistence p = new Persistence(); // Creates a persistence instance
        Maze maze = p.loadJsonString(test.toString()); // Loads the map json file

        assertEquals(expected, maze.toString()); // Checks the map is correct
        assertEquals(0, maze.getPlayerChips()); // Checks the number of chips is correct
        assertEquals("[]", maze.getPlayerInv().toString()); // Checks the inventory is correct
    }


    /*
     * Tests a more complex board generation with every available tile kind.
     */
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


        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }


    /*
     * Tests simple movement in all directions.
     */
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

        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    // Restricted movement

    /*
     * Ensures walls work properly.
     */
    @Test
    public void t04_Walls() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|#|#|#|_|"
                + "_|#|X|#|_|"
                + "_|#|#|#|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w", "w", "a", "a", "a", "s", "s", "s", "d", "d", "d"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|#|#|#|_|\n" +
                        "|_|#|X|#|_|\n" +
                        "|_|#|#|#|_|\n" +
                        "|_|_|_|_|_|\n";

        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    /*
     * Ensures you cannot walk off the map
     */
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


        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    // DOORS and KEYS

    /*
     * Tests that doors can be constructed, opened and remain open
     */
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


        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }


    /*
     * Checks that the correct key uis used to open the correct dooe
     */
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


        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[b]", maze.getPlayerInv().toString());
    }


    /*
     * Checks doors cannot be unlocked without the key
     */
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


        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }


    // ITEM TESTS

    /*
     * Checks whether treasure can be picked up
     */
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



        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(5, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    /*
     * Checks whether keys can be picked up
     */

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



        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[r, g, b, y]", maze.getPlayerInv().toString());
    }

    /*
     * Checks whether multiple of the same keys can be picked up.
     */
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



        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[r, g, b, y, y, b, g, r]", maze.getPlayerInv().toString());
    }


    /*
     * Checks the info tile can be walked on, and has the correct info.
     */
    @Test
    public void t12_InfoItem() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|1|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|I|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w" };

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";



        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(maze.getBoard().getInfo(), "SAMPLE TILE INFO");

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
    }

    // Ending Game
    /*
     * Makes sure the game state can be ended. and that the player cannot walk onto it
     */
    @Test
    public void t13_SimpleGameEnd() {
        String map =  "5|5|SAMPLE TILE INFO|0|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|%|_|_|"
                + "_|_|E|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|";

        String[] moves = new String[]{ "w", "w"};

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|%|_|_|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";



        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        Maze maze = p.loadJsonString(test.toString());

        gameplayLoop(maze, moves);

        assertEquals(expected, maze.toString());
        assertEquals(0, maze.getPlayerChips());
        assertEquals("[]", maze.getPlayerInv().toString());
        assertTrue(maze.levelWonChecker());
    }

    // Break it

    /*
     * Gives bad inputs and hopes a board cannot be constructed with them
     *
     * This will throw an IndexOutOfBoundsException and NullPointerException if it works correctly
     */
    @Test
    public void t14_badBoardConstruction() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + " |!|@|^|*|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_";

        JsonObject test = toJSON(map);

        String moves = "";

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        boolean catchError = false;
        try {
            Persistence p = new Persistence();
            Maze maze = p.loadJsonString(test.toString());
        }
        catch (IndexOutOfBoundsException|NullPointerException e) {
            System.out.println("Null pointer thrown and caught.");
            catchError = true;
        }
        assertTrue(catchError);
    }

    /*
     * Gives incorrect sizes in the board data. Claims it is 7x32 but board is only 5x5
     *
     * This will throw an IndexOutOfBoundsException if it works correctly
     */
    @Test
    public void t15_incorrectSize() {
        String map =  "7|32|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|X|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_";

        JsonObject test = toJSON(map);

        String moves = "";

        String expected =
                "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|X|_|_|\n" +
                        "|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|\n";

        boolean catchError = false;
        try {
            Persistence p = new Persistence();
            Maze maze = p.loadJsonString(test.toString());

            assertEquals(expected, maze.toString());
        } catch (IndexOutOfBoundsException e){
            catchError = true;
        }

        assertTrue(catchError);
    }


    /*
     * Ensures a map cannot be created without chap.
     */
    @Test
    public void t16_noChap() {
        String map =  "5|5|SAMPLE TILE INFO|11|SETBK|0|SETYK|0|SETRK|0|SETGK|0|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_|"
                + "_|_|_|_|_";

        JsonObject test = toJSON(map);


       boolean catchError = false;
        try {
            Persistence p = new Persistence();
            Maze maze = p.loadJsonString(test.toString());
        }
        catch (AssertionError e) {
            catchError = true;
        }

        assertTrue(catchError);

    }

    /*
     * Loops through the predefined movements
     */
    public void gameplayLoop(Maze maze, String[] moves) {
        for (String s : moves) {
            switch (s) {
                case "s":
                    maze.executeMove(GraphicalUserInterface.Direction.DOWN);
                    break;
                case "w":
                    maze.executeMove(GraphicalUserInterface.Direction.UP);
                    break;
                case "a":
                    maze.executeMove(GraphicalUserInterface.Direction.LEFT);
                    break;
                case "d":
                    maze.executeMove(GraphicalUserInterface.Direction.RIGHT);
                    break;
            }
        }
    }


    /*
     * turns the old map generation string into the new JSON generation file
     */
    public static JsonObject toJSON(String map) {
        Scanner scan = new Scanner(map);
        scan.useDelimiter("\\|");

        HashMap<String, Integer> config = new HashMap<>();
        JsonBuilderFactory factory = Json.createBuilderFactory(config);

        JsonObjectBuilder test = factory.createObjectBuilder();
        test.add("level", 1);
        test.add("xSize", scan.next());
        test.add("ySize", scan.next());
        test.add("tileInfo", scan.next());
        test.add("numChips", scan.next());        scan.next();
        test.add("SETBK", scan.next());           scan.next();
        test.add("SETYK", scan.next());           scan.next();
        test.add("SETRK", scan.next());           scan.next();
        test.add("SETGK", scan.next());
        scan.skip("\\|");
        scan.useDelimiter("\n");

        test.add("board", scan.next());

        return test.build();
    }
}
