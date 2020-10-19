package test.nz.ac.vuw.ecs.swen225.gp20.monkey;

import nz.ac.vuw.ecs.swen225.gp20.application.GUI;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Persistence;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        Maze maze = new Maze(map);
        //while (true) {
        for (int i = 0; i < 10000; i++) {
            int rand = (int) (Math.random() * 4);
            System.out.println(rand);

            switch (rand) {
                case 0:
                    maze.executeMove(GUI.direction.DOWN);
                case 1:
                    maze.executeMove(GUI.direction.UP);
                case 2:
                    maze.executeMove(GUI.direction.LEFT);
                case 3:
                    maze.executeMove(GUI.direction.RIGHT);
            }

        }
    }

}
