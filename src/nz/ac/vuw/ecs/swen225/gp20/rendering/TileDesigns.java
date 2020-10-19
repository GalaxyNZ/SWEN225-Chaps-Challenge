package nz.ac.vuw.ecs.swen225.gp20.rendering;

import java.awt.*;

public class TileDesigns {

    public TileDesigns(Graphics2D g, Point p, int wh, int s, Point tp, CL cl, boolean draw){

        switch (cl){
            default:
                wallTile(g,p,wh,s, tp,cl, draw);
                break;
            case Floor:
                if(draw)return;
                floorTile(g,p,wh);
                break;
            case Info:
                if(draw)return;
                infoTile(g,p,wh);
                break;
            case Treasure:
                if(draw)return;
                treasureTile(g,p,wh);

        }
    }
    private void wallTile(Graphics2D g, Point p, int wh, int s, Point tp, CL cl, boolean draw){

        int center = s/2 +1;
        if(!draw) {
            g.setColor(Color.GRAY);
            g.fillRect(p.x, p.y, wh, wh);
        }else{
            switch(cl){
                case U:
                    g.setColor(Color.BLACK);
                    g.drawPolyline(new int[]{p.x,p.x,p.x+wh,p.x+wh},
                            new int[]{p.y,p.y+wh,p.y+wh,p.y},
                            4);
                case D:

                case L:


            }
        }
    }
    private void floorTile(Graphics2D g, Point p, int wh){
        g.setColor(Color.WHITE);
        g.fillRect(p.x,p.y,wh, wh);
    }
    private void infoTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
    }
    private void treasureTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
    }


}
