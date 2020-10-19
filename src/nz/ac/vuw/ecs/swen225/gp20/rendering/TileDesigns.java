package nz.ac.vuw.ecs.swen225.gp20.rendering;

import java.awt.*;

public class TileDesigns {
    Classification cl;
    Graphics2D g;
    Point p;
    int wh;
    public TileDesigns(Graphics2D g, Point p, int wh, Classification cl){

        switch (cl){
            default:
                this.cl = cl;
                this.g = g;
                this.p = p;
                this.wh = wh;
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
    public void wallTile(Point chunkSize, Point pos, Dimension d){
        g.setColor(Color.GRAY);
        g.fillRect(p.x,p.y,wh, wh);
        switch(cl){
            case U:

            case D:

            case L:


        }
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
