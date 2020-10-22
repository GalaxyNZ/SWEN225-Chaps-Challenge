package test.nz.ac.vuw.ecs.swen225.gp20.monkey;

import nz.ac.vuw.ecs.swen225.gp20.application.GraphicalUserInterface;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;

import static test.nz.ac.vuw.ecs.swen225.gp20.monkey.FixedTests.toJSON;

public class MonkeyTest {

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

    @Test
    public void MonkeyTesting() {
        JsonObject test = toJSON(map);
        Persistence p = new Persistence();
        //Maze maze = p.loadJsonString(test.toString());
        Maze maze = p.newGame();

        int count = 0;
        while (!maze.levelWonChecker()) {
            int rand = (int) (Math.random() * 4);
            switch (rand) {
                case 0:
                    maze.executeMove(GraphicalUserInterface.Direction.DOWN);
                    break;
                case 1:
                    maze.executeMove(GraphicalUserInterface.Direction.UP);
                    break;
                case 2:
                    maze.executeMove(GraphicalUserInterface.Direction.LEFT);
                    break;
                case 3:
                    maze.executeMove(GraphicalUserInterface.Direction.RIGHT);
                    break;
            }
        count++;
        }
        System.out.println(count);
    }

}
