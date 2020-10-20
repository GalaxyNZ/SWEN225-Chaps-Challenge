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

        int center = s/2 ;
        int x = (tp.x - center)*10, y = (tp.y - center)*10;
        int x01 = Math.max(p.x + 10 + x, p.x);
        int x02 = Math.max(p.x + 60 + x, p.x);
        int y01 = Math.max(p.y + 10 + y, p.y);
        int y02 = Math.max(p.y + 60 + y, p.y);
        int x1 = Math.min(x01, p.x + wh);
        int x2 = Math.min(x02, p.x + wh);
        int y1 = Math.min(y01, p.y + wh);
        int y2 = Math.min(y02, p.y + wh);
        if(!draw) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(p.x, p.y, wh, wh);
        }else{
            g.setStroke(new BasicStroke(1.2f));
            switch(cl){
                case U:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y,y2,y2,p.y},
                            4);
                    g.setColor(Color.BLACK);
                    g.drawPolyline(new int[]{x1,x1,x2,x2},
                            new int[]{p.y,y2,y2,p.y},
                            4);
                    g.drawPolyline(new int[]{p.x,p.x,p.x+wh,p.x+wh},
                            new int[]{p.y,p.y+wh,p.y+wh,p.y},
                            4);
                    break;
                case D:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,y1,y1,p.y+wh},
                            4);
                    g.setColor(Color.BLACK);
                    g.drawPolyline(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,y1,y1,p.y+wh},
                            4);
                    g.drawPolyline(new int[]{p.x,p.x,p.x+wh,p.x+wh},
                            new int[]{p.y+wh,p.y,p.y,p.y+wh},
                            4);
                    break;
                case DU:
                    g.setColor(Color.BLACK);
                    g.drawLine(p.x,p.y,p.x,p.y+wh);
                    g.drawLine(p.x+wh,p.y,p.x+wh,p.y+wh);
                    break;
                case DUR:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,p.y,p.y,p.y+wh},
                            4);
                    g.setColor(Color.BLACK);
                    g.drawLine(x1,p.y,x1,p.y+wh);
                    g.drawLine(p.x,p.y,p.x,p.y+wh);
                    break;
                case DUL:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,p.y,p.y,p.y+wh},
                            4);
                    g.setColor(Color.BLACK);
                    g.drawLine(x2,p.y,x2,p.y+wh);
                    g.drawLine(p.x+wh,p.y,p.x+wh,p.y+wh);
                    break;
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
