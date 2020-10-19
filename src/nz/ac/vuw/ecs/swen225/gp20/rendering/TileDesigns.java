package nz.ac.vuw.ecs.swen225.gp20.rendering;

import java.awt.*;

public class TileDesigns {

    public TileDesigns(Graphics2D g, Point p, int wh, Dimension s, Classification cl){

        switch (cl){
            default:
                wallTile(g,p,wh,s,cl);
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
    private void wallTile(Graphics2D g, Point p, int wh, Dimension s, Classification cl){
        int center = s.height/2;

        g.setColor(Color.GRAY);
        g.fillRect(p.x,p.y,wh, wh);
        switch(cl){
            case U:

            case D:

            case L:


        }
    }
    private void floorTile(Graphics2D g, Point p, int wh){
        g.setColor(Color.GRAY);
        g.fillRect(p.x,p.y,wh, wh);
    }
    private void infoTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
    }
    private void treasureTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
    }


}
