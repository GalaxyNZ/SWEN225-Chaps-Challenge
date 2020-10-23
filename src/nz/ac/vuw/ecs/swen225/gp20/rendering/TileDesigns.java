package nz.ac.vuw.ecs.swen225.gp20.rendering;

import javax.swing.*;
import java.awt.*;

public class TileDesigns {
    /**
     * Secondary Constructor Made for doors
     *
     * @param g, Graphics 2D
     * @param p, Position on the Board
     * @param wh, The width/height of the Tiles
     * @param s, Size of the displayed board
     * @param index, Position of the displayed tiles
     * @param cl, Enum identity
     * @param ot, Orientation
     * @param draw, Checks whether to draw or fill
     */
    public TileDesigns(Graphics2D g, Point p, int wh, int s, Point index, CL cl, CL ot, boolean draw){
        doorTile(g,p,wh,s,index,cl,ot, draw);
    }

    /**
     * Primary Constructor with Enum Assignment, made for easier access in rendering
     *
     * @param g, Graphics 2D
     * @param p, Position on the Board
     * @param wh, The width/height of the Tiles
     * @param s, Size of the displayed board
     * @param index, Position of the displayed tiles
     * @param cl, Enum identity
     * @param draw, Checks whether to draw or fill
     */
    public TileDesigns(Graphics2D g, Point p, int wh, int s, Point index, CL cl, boolean draw){
        g.setColor(Color.BLACK);
        switch (cl){
            default:
                wallTile(g,p,wh,s, index,cl, draw);
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
                break;
            case GK:
                if(draw)return;
                gk(g,p,wh);
                break;
            case BK:
                if(draw)return;
                bk(g,p,wh);
                break;
            case RK:
                if(draw)return;
                rk(g,p,wh);
                break;
            case YK:
                if(draw)return;
                yk(g,p,wh);
                break;
        }
    }

    /**
     * Creates a new door tile according to the enum
     * @param g Graphics 2D
     * @param p Position on Board
     * @param wh width/ height of tiles
     * @param s size of displayed board
     * @param tp Position of tile in the displayed board
     * @param cl Enum assignment
     * @param ot  Enum Orientation
     * @param draw draw or fill
     */
    private void doorTile(Graphics2D g, Point p, int wh, int s, Point tp, CL cl, CL ot, boolean draw){
        Color c = Color.BLACK;
        switch (cl){
            case RD:
                c = new Color(255,0,0,150);
                break;
            case B:
                c= new Color(0,0,255,150);
                break;
            case G:
                c= new Color(0,255,0,150);
                break;
            case Y:
                c = new Color(255,255,0,150);
                break;
            case Exit:
                c = new Color(30,30,30,150);

        }
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
            g.setColor(c );
            g.drawImage(new ImageIcon("res/Wall.png").getImage(), p.x,p.y, wh,wh, null);
            g.fillRect(p.x, p.y, wh, wh);
        }else{
            switch (ot) {
                case DU:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,p.y,p.y,p.y+wh},
                            4);
                    g.setColor(Color.BLACK);
                    g.drawLine(x1,p.y,x1,p.y+wh);
                    g.drawLine(x2,p.y,x2,p.y+wh);
                    g.drawLine(p.x,p.y,p.x,p.y+wh);
                    g.drawLine(p.x+wh,p.y,p.x+wh,p.y+wh);
                    break;
                case LR:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{p.x+wh,p.x,p.x,p.x+wh},
                            new int[]{y1,y1,y2,y2},
                            4);
                    g.setColor(Color.BLACK);
                    g.drawLine(p.x,y1,p.x+wh,y1);
                    g.drawLine(p.x,y2,p.x+wh,y2);
                    g.drawLine(p.x,p.y,p.x+wh,p.y);
                    g.drawLine(p.x,p.y+wh,p.x+wh,p.y+wh);

            }
            g.drawRect(p.x,p.y,wh,wh);
        }
    }

    /**
     * draws wall tiles according to enum
     * @param g Graphics 2D
     * @param p Position on Board
     * @param wh width/ height of tiles
     * @param s size of displayed board
     * @param tp Position of tile in the displayed board
     * @param cl Enum assignment
     * @param draw draw or fill
     */
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
            g.setColor(new Color(240,240,240, 200) );
            g.drawImage(new ImageIcon("res/Wall.png").getImage(), p.x,p.y, wh,wh, null);
            g.fillRect(p.x, p.y, wh, wh);
            switch(cl){
                case U:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y,y2,y2,p.y},
                            4);
                    break;
                case D:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,y1,y1,p.y+wh},
                            4);
                    break;
                case DU:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,p.y,p.y,p.y+wh},
                            4);
                    break;
                case DUR:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,p.y,p.y,p.y+wh},
                            4);
                    g.fillPolygon(new int[]{p.x+wh,p.x+wh,x2,x2},
                            new int[]{y1,y2,y2,y1},
                            4);
                    break;
                case DUL:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,p.y,p.y,p.y+wh},
                            4);
                    g.fillPolygon(new int[]{p.x,p.x,x1,x1},
                            new int[]{y1,y2,y2,y1},
                            4);
                    break;
                case LR:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{p.x+wh,p.x,p.x,p.x+wh},
                            new int[]{y1,y1,y2,y2},
                            4);
                    break;
                case ULR:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,p.x,p.x,p.x+wh,p.x+wh,x2,x2},
                            new int[]{p.y,y1,y1,y2,y2,y1,y1,p.y,p.y},
                            8);
                    break;
                case DLR:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x1,p.x,p.x,p.x+wh,p.x+wh,x2,x2},
                            new int[]{p.y+wh,y2,y2,y1,y1,y2,y2,p.y+wh,p.y+wh},
                            8);
                    break;
                case L:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{p.x,x2,x2,p.x},
                            new int[]{y1,y1,y2,y2},
                            4);
                    break;
                case R:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{p.x+wh,x1,x1,p.x+wh},
                            new int[]{y1,y1,y2,y2},
                            4);
                    break;
                case DL:

                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x2,x1,x1,p.x,p.x,x2},
                            new int[]{p.y+wh,p.y+wh,y2,y2,y1,y1},
                            6);
                    break;
                case DR:

                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x2,x2,p.x+wh,p.x+wh,x1},
                            new int[]{p.y+wh,p.y+wh,y2,y2,y1,y1},
                            6);
                    break;
                case UL:

                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x2,x1,x1,p.x,p.x,x2},
                            new int[]{p.y,p.y,y1,y1,y2,y2},
                            6);
                    break;
                case UR:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{x1,x2,x2,p.x+wh,p.x+wh,x1},
                            new int[]{p.y,p.y,y1,y1,y2,y2},
                            6);
                    break;
                case DULR:
                    g.setColor(Color.GRAY);
                    g.fillPolygon(new int[]{p.x,x1,x1,x2,x2,p.x+wh,p.x+wh,x2,x2,x1,x1,p.x},
                            new int[]{y1,y1,p.y,p.y,y1,y1,y2,y2,p.y+wh, p.y+wh, y2,y2},
                            12);

            }
        }else{
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setStroke(new BasicStroke(1));
            switch(cl){
                case DL:
                    g.drawPolyline(new int[]{p.x,x1,x1},
                            new int[]{y2,y2,p.y+wh},
                            3);
                    g.drawPolyline(new int[]{p.x,p.x+wh,p.x+wh},
                            new int[]{p.y,p.y,p.y+wh},
                            3);
                    g.drawPolyline(new int[]{p.x,x2,x2},
                            new int[]{y1,y1,p.y+wh},
                            3);
                    g.drawLine(x1,y2,p.x,p.y+wh);
                    break;
                case DR:
                    g.drawPolyline(new int[]{p.x+wh,x2,x2},
                            new int[]{y2,y2,p.y+wh},
                            3);
                    g.drawPolyline(new int[]{p.x+wh,p.x,p.x},
                            new int[]{p.y,p.y,p.y+wh},
                            3);
                    g.drawPolyline(new int[]{p.x+wh,x1,x1},
                            new int[]{y1,y1,p.y+wh},
                            3);
                    g.drawLine(x2,y2,p.x+wh,p.y+wh);

                    break;
                case UL:
                    g.setColor(Color.BLACK);
                    g.drawPolyline(new int[]{p.x,x1,x1},
                            new int[]{y1,y1,p.y},
                            3);
                    g.drawPolyline(new int[]{p.x,p.x+wh,p.x+wh},
                            new int[]{p.y+wh,p.y+wh,p.y},
                            3);
                    g.drawPolyline(new int[]{p.x,x2,x2},
                            new int[]{y2,y2,p.y},
                            3);
                    g.drawLine(x1,y1,p.x,p.y);
                    break;
                case UR:
                    g.drawPolyline(new int[]{p.x+wh,x2,x2},
                            new int[]{y1,y1,p.y},
                            3);
                    g.drawPolyline(new int[]{p.x+wh,p.x,p.x},
                            new int[]{p.y+wh,p.y+wh,p.y},
                            3);
                    g.drawPolyline(new int[]{p.x+wh,x1,x1},
                            new int[]{y2,y2,p.y},
                            3);
                    g.drawLine(x2,y1,p.x+wh,p.y);
                    break;
                case DULR:
                    g.setColor(Color.BLACK);
                    g.drawLine(x2,y2,p.x+wh,p.y+wh);
                    g.drawLine(x1,y2,p.x,p.y+wh);
                    g.drawLine(x1,y1,p.x,p.y);
                    g.drawLine(x2,y1,p.x+wh,p.y);
                    g.drawPolyline(new int[]{p.x,x1,x1},
                            new int[]{y1,y1,p.y},
                            3);
                    g.drawPolyline(new int[]{p.x+wh,x2,x2},
                            new int[]{y1,y1,p.y},
                            3);
                    g.drawPolyline(new int[]{p.x,x1,x1},
                            new int[]{y2,y2,p.y+wh},
                            3);
                    g.drawPolyline(new int[]{p.x+wh,x2,x2},
                            new int[]{y2,y2,p.y+wh},
                            3);
                    break;
                case U:

                    g.setColor(Color.BLACK);

                    g.drawPolyline(new int[]{x1,x1,x2,x2},
                            new int[]{p.y,y2,y2,p.y},
                            4);
                    g.drawPolyline(new int[]{p.x,p.x,p.x+wh,p.x+wh},
                            new int[]{p.y,p.y+wh,p.y+wh,p.y},
                            4);
                    g.drawLine(x2,y2,p.x+wh,p.y+wh);
                    g.drawLine(x1,y2,p.x,p.y+wh);
                    break;
                case D:

                    g.setColor(Color.BLACK);
                    g.drawPolyline(new int[]{x1,x1,x2,x2},
                            new int[]{p.y+wh,y1,y1,p.y+wh},
                            4);
                    g.drawPolyline(new int[]{p.x,p.x,p.x+wh,p.x+wh},
                            new int[]{p.y+wh,p.y,p.y,p.y+wh},
                            4);
                    g.drawLine(x2,y1,p.x+wh,p.y);
                    g.drawLine(x1,y1,p.x,p.y);
                    break;
                case DU:

                    g.setColor(Color.BLACK);
                    g.drawLine(x1,p.y,x1,p.y+wh);
                    g.drawLine(x2,p.y,x2,p.y+wh);
                    g.drawLine(p.x,p.y,p.x,p.y+wh);
                    g.drawLine(p.x+wh,p.y,p.x+wh,p.y+wh);
                    break;
                case DUR:
                    g.setColor(Color.BLACK);
                    g.drawPolyline(new int[]{x2,x2,p.x+wh},
                            new int[]{p.y,y1,y1},
                            3);
                    g.drawPolyline(new int[]{x2,x2,p.x+wh},
                            new int[]{p.y+wh,y2,y2},
                            3);
                    g.drawLine(x1,p.y,x1,p.y+wh);
                    g.drawLine(p.x,p.y,p.x,p.y+wh);
                    g.drawLine(x2,y1,p.x+wh,p.y);
                    g.drawLine(x2,y2,p.x+wh,p.y+wh);
                    break;
                case DUL:
                    g.setColor(Color.BLACK);

                    g.drawPolyline(new int[]{x1,x1,p.x},
                            new int[]{p.y,y1,y1},
                            3);
                    g.drawPolyline(new int[]{x1,x1,p.x},
                            new int[]{p.y+wh,y2,y2},
                            3);
                    g.drawLine(x2,p.y,x2,p.y+wh);
                    g.drawLine(p.x+wh,p.y,p.x+wh,p.y+wh);
                    g.drawLine(x1,y1,p.x,p.y);
                    g.drawLine(x1,y2,p.x,p.y+wh);
                    break;
                case LR:
                    g.setColor(Color.BLACK);
                    g.drawLine(p.x,y1,p.x+wh,y1);
                    g.drawLine(p.x,y2,p.x+wh,y2);
                    g.drawLine(p.x,p.y,p.x+wh,p.y);
                    g.drawLine(p.x,p.y+wh,p.x+wh,p.y+wh);
                    break;
                case ULR:
                    g.setColor(Color.BLACK);

                    g.drawPolyline(new int[]{x1,x1,p.x},
                            new int[]{p.y,y1,y1},
                            3);
                    g.drawPolyline(new int[]{x2,x2,p.x+wh},
                            new int[]{p.y,y1,y1},
                            3);
                    g.drawLine(p.x,y2,p.x+wh,y2);
                    g.drawLine(p.x,p.y+wh,p.x+wh,p.y+wh);
                    g.drawLine(x1,y1,p.x,p.y);
                    g.drawLine(x2,y1,p.x+wh,p.y);
                    break;
                case DLR:
                    g.setColor(Color.BLACK);

                    g.drawPolyline(new int[]{x1,x1,p.x},
                            new int[]{p.y+wh,y2,y2},
                            3);
                    g.drawPolyline(new int[]{x2,x2,p.x+wh},
                            new int[]{p.y+wh,y2,y2},
                            3);

                    g.drawLine(p.x,y1,p.x+wh,y1);
                    g.drawLine(p.x,p.y,p.x+wh,p.y);

                    g.drawLine(x1,y2,p.x,p.y+wh);
                    g.drawLine(x2,y2,p.x+wh,p.y+wh);
                    break;
                case L:
                    g.setColor(Color.BLACK);
                    g.drawPolyline(new int[]{p.x,x2,x2,p.x},
                            new int[]{y1,y1,y2,y2},
                            4);
                    g.drawPolyline(new int[]{p.x,p.x+wh,p.x+wh,p.x},
                            new int[]{p.y,p.y,p.y+wh,p.y+wh},
                            4);
                    g.drawLine(x2,y2,p.x+wh,p.y+wh);
                    g.drawLine(x2,y1,p.x+wh,p.y);
                    break;
                case R:
                    g.setColor(Color.BLACK);
                    g.drawPolyline(new int[]{p.x+wh,x1,x1,p.x+wh},
                            new int[]{y1,y1,y2,y2},
                            4);
                    g.drawPolyline(new int[]{p.x+wh,p.x,p.x,p.x+wh},
                            new int[]{p.y,p.y,p.y+wh,p.y+wh},
                            4);
                    g.drawLine(x1,y2,p.x,p.y+wh);
                    g.drawLine(x1,y1,p.x,p.y);
                    break;
            }
        }
    }

    /**
     * draws floor tile
     * @param g, Graphics 2D
     * @param p, position in board
     * @param wh, width/height
     */
    private void floorTile(Graphics2D g, Point p, int wh){
        g.setColor(new Color(240,240,240));
        g.fillRect(p.x,p.y,wh, wh);
        g.drawImage(new ImageIcon("res/Floor.png").getImage(), p.x,p.y, wh,wh, null);


    }
    /**
     * draws info tile
     * @param g, Graphics 2D
     * @param p, position in board
     * @param wh, width/height
     */
    private void infoTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
        g.drawImage(new ImageIcon("res/Info.png").getImage(), p.x,p.y, wh,wh, null);

    }
    /**
     * draws treasure tile
     * @param g, Graphics 2D
     * @param p, position in board
     * @param wh, width/height
     */
    private void treasureTile(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
        g.drawImage(new ImageIcon("res/Treasure.png").getImage(), p.x,p.y, wh,wh, null);

    }
    /**
     * draws green key
     * @param g, Graphics 2D
     * @param p, position in board
     * @param wh, width/height
     */
    private void gk(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
        g.drawImage(new ImageIcon("res/GreenKey.png").getImage(), p.x+wh/2 -25,p.y+wh/2 -15, 50,30, null);

    }
    /**
     * draws red key
     * @param g, Graphics 2D
     * @param p, position in board
     * @param wh, width/height
     */
    private void rk(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
        g.drawImage(new ImageIcon("res/RedKey.png").getImage(), p.x+wh/2 -25,p.y+wh/2 -15, 50,30, null);

    }
    /**
     * draws blue key
     * @param g, Graphics 2D
     * @param p, position in board
     * @param wh, width/height
     */
    private void bk(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
        g.drawImage(new ImageIcon("res/BlueKey.png").getImage(), p.x+wh/2 -25,p.y+wh/2 -15, 50,30, null);

    }
    /**
     * draws yellow key
     * @param g, Graphics 2D
     * @param p, position in board
     * @param wh, width/height
     */
    private void yk(Graphics2D g, Point p, int wh){
        floorTile(g,p,wh);
        g.drawImage(new ImageIcon("res/YellowKey.png").getImage(), p.x+wh/2 -25,p.y+wh/2 -15, 50,30, null);

    }

}
