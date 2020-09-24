package nz.ac.vuw.ecs.swen225.gp20.rendering;

import java.awt.*;

public class Rendering {
    Dimension size;
    Point position;

    public void setSize(Dimension d){size = d;}
    public void reCenter(Point p){position = p;}
    public void draw(Graphics g){}


    private boolean runLeft(){return false;}
    private boolean runRight(){return false;}
    private boolean runUp(){return false;}
    private boolean runDown(){return false;}
}
