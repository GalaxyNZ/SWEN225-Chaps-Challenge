package nz.ac.vuw.ecs.swen225.gp20.rendering;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import nz.ac.vuw.ecs.swen225.gp20.maze.Board;

import javax.swing.*;
import java.awt.*;


/**
 *
 */
public class Rendering {
    private Dimension size;
    private Point position, prev;
    private int count  = 0, frames = 5,aniValX = 0, aniValY = 0;
    private String  prevTime = "";
    private boolean acting = false;
    private CL action = CL.Down, lastAct = CL.Down;
    Maze m;
    //private float time = 1.0f;
    public Rendering(){}

    public void testDrawingAnimation (Graphics g, String actor, Dimension d, Maze m){
        size = d;
        this.m = m;
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(new ImageIcon("res/Background.png").getImage(), -(int)(size.width*0.1)/2,-(int)(size.height*0.1)/2, (int)(size.width*1.1), (int)(size.height*1.1), null);
        if(m != null){
            prev = position;
            position =  new Point(m.getPlayerLocation().x,m.getPlayerLocation().y);
            findChunk(g2);
        }
                switch (actor) {
                    case "Down":
                    case "Up":
                    case "Left":
                    case "Right":
                        still(g2, actor);
                        break;
                    default:

                }
    }

    public void update(Graphics g, Dimension d, Maze m){//board
        if(m == null) return;
        size = d;
        prev = position;
        position = m.getPlayerLocation();
        determineAction();
        draw(g);

    }

    //Getter Method
    public boolean isActing(){return acting;}
    /*
    public void changeTiming(float i){
        if(i > 0.0f && i <= 5.0f){
            time = i;
            return;
        }
        try{
            throw new NumberFormatException("Is not within 1 and 5");
        }catch (Exception ignored){}
    }
     */

    //Helper Methods
    public boolean updateFrame(String currentTime){
        int i = (int) (Float.parseFloat(currentTime.substring(currentTime.indexOf("."))) * 10);
        if(!prevTime.equals(currentTime)) {
            prevTime = currentTime;
            if (i % 2 == 0) {
                count++;
                if (count > frames) count = 1;
                return true;
            }
        }

        return false;
    }
    private void determineAction(){
        if(prev == position){
            frames = 5;
            switch (lastAct){
                case RunLeft:
                case Left:
                    changeAction(CL.Left);
                    break;
                case RunRight:
                case Right:
                    changeAction(CL.Right);
                    break;
                case RunDown:
                case Down:
                    changeAction(CL.Down);
                    break;
                case RunUp:
                case Up:
                    changeAction(CL.Up);

            }
        }


    }
    private void changeAction(CL act){
        if(!acting){
            lastAct = action;
            action = act;
            count = 0;
        }
    }
    private void findChunk(Graphics2D g){
        int chunkSize = 3 + 2*(((size.width/2)-35)/70);
        int center = (((chunkSize * 70) - size.width)/2);
        drawBackgroundInChunk(g, chunkSize, center, false);
        drawBackgroundInChunk(g, chunkSize, center, true);

    }
    private void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        count++;

        //Draw Board
        switch (action){
            default:
                acting = still(g2, action.toString());
                break;
            case RunLeft:
                acting = runLeft(g2);
                break;
            case RunRight:
                acting = runRight(g2);
                break;
            case RunUp:
                acting = runUp(g2);
                break;
            case RunDown:
                acting= runDown(g2);

        }
        if(acting){
            soundOnRun();
        }

    }
    private void drawBackgroundInChunk(Graphics2D g, int chunkSize, int center, boolean draw){
        int indexX = prev == null ? position.x :(aniValX ==0? position.x:prev.x) - chunkSize/2 ;
        int indexY = prev == null ? position.y: (aniValY ==0? position.y:prev.y)  - chunkSize/2 ;

        for(int j = 0; j < chunkSize +(aniValY != 0?1:0); j++){
            for(int i = 0; i < chunkSize+(aniValX != 0?1:0); i++){
                int x = indexX+i, y = indexY+j;
                if(x < 0 || x >= m.getBoardSize().x || y < 0 || y >= m.getBoardSize().y) continue;

                Point defaultP = new Point((i* 70)-center-aniValX,(j* 70)-center-aniValY);
                int wh = 70;
                Tile t =  m.getBoardTile(new Point(x,y));

                String tileChar = t == null ? "_":t.toString();

                switch (tileChar) {
                    default:
                        new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.Floor, draw);
                        break;
                    case "#":
                        String tile = "";
                        if(checkTile(x,y+1)) tile += CL.D.toString();
                        if(checkTile(x,y-1)) tile += CL.U.toString();
                        if(checkTile(x-1,y)) tile += CL.L.toString();
                        if(checkTile(x+1,y)) tile += CL.R.toString();
                        switch(tile){
                            case "U":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.U, draw);
                                continue;
                            case "D":
                                new TileDesigns(g,defaultP,wh,chunkSize, new Point(i,j), CL.D, draw);
                                continue;
                            case "L":
                                new TileDesigns(g,defaultP, wh, chunkSize, new Point(i,j), CL.L, draw);
                                continue;
                            case "R":
                                new TileDesigns(g,defaultP,wh,  chunkSize, new Point(i,j), CL.R, draw);
                                continue;
                            case "DU":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.DU, draw);
                                continue;
                            case "LR":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.LR, draw);
                                continue;
                            case "DUL":
                                new TileDesigns(g,defaultP,wh,  chunkSize, new Point(i,j), CL.DUL, draw);
                                continue;
                            case "DUR":
                                new TileDesigns(g,defaultP,wh,  chunkSize, new Point(i,j), CL.DUR, draw);
                                continue;
                            case "DLR":
                                new TileDesigns(g,defaultP,wh,  chunkSize, new Point(i,j), CL.DLR, draw);
                                continue;
                            case "ULR":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.ULR, draw);
                                continue;
                            case "DULR":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.DULR, draw);
                                continue;
                            case "DR":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.DR, draw);
                                continue;
                            case "DL":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.DL, draw);
                                continue;
                            case "UR":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.UR, draw);
                                continue;
                            case "UL":
                                new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.UL, draw);
                                continue;
                        }
                    case "g":

                        new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.GK, draw);
                        continue;
                    case "b":
                        new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.BK, draw);
                        continue;
                    case "r":
                        new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.RK, draw);
                        continue;
                    case "y":
                        new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.YK, draw);
                        continue;
                    case "T":
                        new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.Treasure, draw);
                        continue;
                    case "I":
                        new TileDesigns(g,defaultP,wh, chunkSize, new Point(i,j), CL.Info, draw);
                        break;
                    case "R":
                        tile = "";
                        if(checkTile(x,y+1)) tile += CL.D.toString();
                        if(checkTile(x,y-1)) tile += CL.U.toString();
                        if(checkTile(x-1,y)) tile += CL.L.toString();
                        if(checkTile(x+1,y)) tile += CL.R.toString();

                        new TileDesigns(g,defaultP,wh, chunkSize,  new Point(i,j), CL.RD,  tile.equals("DU"));
                    break;
                    case"G":
                        tile = "";
                        if(checkTile(x,y+1)) tile += CL.D.toString();
                        if(checkTile(x,y-1)) tile += CL.U.toString();
                        if(checkTile(x-1,y)) tile += CL.L.toString();
                        if(checkTile(x+1,y)) tile += CL.R.toString();

                        new TileDesigns(g,defaultP,wh, chunkSize,  new Point(i,j), CL.G,  tile.equals("DU") );
                    break;
                    case"B" :
                        tile = "";
                        if(checkTile(x,y+1)) tile += CL.D.toString();
                        if(checkTile(x,y-1)) tile += CL.U.toString();
                        if(checkTile(x-1,y)) tile += CL.L.toString();
                        if(checkTile(x+1,y)) tile += CL.R.toString();

                        new TileDesigns(g,defaultP,wh, chunkSize,  new Point(i,j), CL.B,  tile.equals("DU") );
                    break;
                    case"Y":
                        tile = "";
                        if(checkTile(x,y+1)) tile += CL.D.toString();
                        if(checkTile(x,y-1)) tile += CL.U.toString();
                        if(checkTile(x-1,y)) tile += CL.L.toString();
                        if(checkTile(x+1,y)) tile += CL.R.toString();

                        new TileDesigns(g,defaultP,wh,chunkSize,  new Point(i,j), CL.Y,  tile.equals("DU"));


                }
            }
        }
    }
    private boolean checkTile(int x, int y){
        if(x < 0 || x >= m.getBoardSize().x || y < 0 || y >= m.getBoardSize().y) return false;
        Tile t = m.getBoardTile(new Point(x,y));
        if( t == null) return false;
        return t.toString().equals("#")||t.toString().equals("G")||t.toString().equals("B")||
                t.toString().equals("R")||t.toString().equals("Y")||t.toString().equals("E");
    }
    private boolean still(Graphics2D g, String str){
        try{
            Image im = new ImageIcon("res/" + str + "-i" + count +".png").getImage();
            g.drawImage(im, size.width/2 - 35,size.height/2 - 35,70,70, null);

        }catch (Exception ignored){}
        return false;
    }
    private boolean runLeft(Graphics2D g){
        if(count == 5){
            return false;
        }
        try{
            Image im = new ImageIcon("/res/Run-Left-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
        return true;
    }
    private boolean runRight(Graphics2D g){
        if(count == 5){
            return false;
        }
        try{
            Image im = new ImageIcon("/res/Run-Right-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
        return true;
    }
    private boolean runUp(Graphics2D g){
        if(count == 5){
            return false;
        }
        try{
            Image im = new ImageIcon("/res/Run-Up-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
        return true;
    }
    private boolean runDown(Graphics2D g){
        if(count == 5){

            return false;
        }
        try{
            Image im = new ImageIcon("/res/Run-Down-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}

        return true;
    }

    private void soundOnRun(){ }

}
