package nz.ac.vuw.ecs.swen225.gp20.rendering;

import java.awt.*;

public class TileDesigns {

    public TileDesigns(Graphics2D g, Point p, int wh, Classification cl){

        switch (cl){
            case Floor:
                floorTile(g,p,wh);
                break;
            case Wall:
                wallTile(g,p,wh);
                break;
            case Info:
                infoTile(g,p,wh);
                break;
            case Treasure:
                treasureTile(g,p,wh);

        }
    }
    public void floorTile(Graphics2D g, Point p, int wh){

    }
    public void wallTile(Graphics2D g, Point p, int wh){

    }
    public void infoTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
    }
    public void treasureTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
    }


}
