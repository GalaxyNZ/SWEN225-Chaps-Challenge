package nz.ac.vuw.ecs.swen225.gp20.rendering;

public class ErrorMessage extends Exception{
    public ErrorMessage(String s){
        System.out.println(s);
        System.exit(1);
    }

}
