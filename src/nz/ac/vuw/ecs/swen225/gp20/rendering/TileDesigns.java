package nz.ac.vuw.ecs.swen225.gp20.rendering;

import java.awt.*;

public class TileDesigns {
    public TileDesigns(Graphics2D g, Point p, int wh, Classification cl){

        switch (cl){
            default:
                wallTile(cl);
                break;
            case Floor:
                floorTile(g,p,wh);
                break;

            case Info:
                infoTile(g,p,wh);
                break;
            case Treasure:
                treasureTile(g,p,wh);

        }
    }
    public void wallTile(Classification cl){

    }
    public void floorTile(Graphics2D g, Point p, int wh){
        g.setColor(Color.GRAY);
        g.fillRect(p.x,p.y,wh, wh);
    }
    public void infoTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
    }
    public void treasureTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
    }


}
