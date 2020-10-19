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
    private Classification action = Classification.Down, lastAct = Classification.Down;
    //private float time = 1.0f;
    public Rendering(){}

    public void testDrawingAnimation (Graphics g, String actor, Dimension d, Maze m){
        size = d;

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(new ImageIcon("res/Background.png").getImage(), -(int)(size.width*0.1)/2,-(int)(size.height*0.1)/2, (int)(size.width*1.1), (int)(size.height*1.1), null);

        if(m != null){
            prev = position;
            position = m.getBoard().findPlayer();
            findChunk(g2, m.getBoard());
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
        position = m.getBoard().findPlayer();
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
                    changeAction(Classification.Left);
                    break;
                case RunRight:
                case Right:
                    changeAction(Classification.Right);
                    break;
                case RunDown:
                case Down:
                    changeAction(Classification.Down);
                    break;
                case RunUp:
                case Up:
                    changeAction(Classification.Up);

            }
        }


    }
    private void changeAction(Classification act){
        if(!acting){
            lastAct = action;
            action = act;
            count = 0;
        }
    }
    private void findChunk(Graphics2D g, Board b){
        int chunkSize = 3 + 2*(((size.width/2)-35)/70);
        int center = (((chunkSize * 70) - size.width)/2);
        drawBackgroundInChunk(g, chunkSize, center, b);

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
    private void drawBackgroundInChunk(Graphics2D g, int chunkSize, int center, Board b){
        int indexX = aniValX ==0?position.x:prev.x - chunkSize/2 ;
        int indexY = aniValY ==0?position.y:prev.y  - chunkSize/2 ;

        for(int j = 0; j < chunkSize +(aniValY != 0?1:0); j++){
            for(int i = 0; i < chunkSize+(aniValX != 0?1:0); i++){
                int x = indexX+i, y = indexY+j;
                if(x < 0 || x >= b.getWidth() || y < 0 || y >= b.getHeight()) continue;

                Point defaultP = new Point((i* 70)-center-aniValX,(j* 70)-center-aniValY);
                int wh = 70;
                String tileChar = b.getTile(x,y).toString();

                switch (tileChar) {
                    case "#":
                        String tile = "";
                        if(checkTile(x,y+1,b)) tile += Classification.D.toString();
                        if(checkTile(x,y-1,b)) tile += Classification.U.toString();
                        if(checkTile(x-1,y,b)) tile += Classification.L.toString();
                        if(checkTile(x+1,y,b)) tile += Classification.R.toString();
                        switch(tile){
                            case "U":
                                new TileDesigns(g,defaultP,wh, size,Classification.U);
                                continue;
                            case "D":
                                new TileDesigns(g,defaultP,wh,size,Classification.D);
                                continue;
                            case "L":
                                new TileDesigns(g,defaultP, wh,size,Classification.L);
                                continue;
                            case "R":
                                new TileDesigns(g,defaultP,wh, size,Classification.R);
                                continue;
                            case "DU":
                                new TileDesigns(g,defaultP,wh,size,Classification.DU);
                                continue;
                            case "LR":
                                new TileDesigns(g,defaultP,wh,size,Classification.LR);
                                continue;
                            case "DUL":
                                new TileDesigns(g,defaultP,wh, size,Classification.DUL);
                                continue;
                            case "DUR":
                                new TileDesigns(g,defaultP,wh, size,Classification.DUR);
                                continue;
                            case "DLR":
                                new TileDesigns(g,defaultP,wh, size,Classification.DLR);
                                continue;
                            case "ULR":
                                new TileDesigns(g,defaultP,wh,size,Classification.ULR);
                                continue;
                            case "DULR":
                                new TileDesigns(g,defaultP,wh,size,Classification.DULR);
                        }
                        continue;
                    case "%":
                        new TileDesigns(g,defaultP,wh,size,Classification.Exit);
                        continue;
                    case "E":
                        new TileDesigns(g,defaultP,wh,size,Classification.ELI);
                        continue;
                    case "i":
                        g.setColor(new Color(92, 12, 144));
                        continue;
                    case "T":
                        g.setColor(Color.darkGray);
                        continue;
                    case "G":
                        g.setColor(Color.GREEN);
                        continue;
                    case "g":
                        g.setColor(new Color(13, 120, 6, 255));
                        continue;
                    case "R":
                        g.setColor(Color.RED);
                        continue;
                    case "r":
                        g.setColor(Color.pink);
                        continue;
                    case "Y":
                        g.setColor(Color.ORANGE);
                        continue;
                    case "y":
                        g.setColor(Color.YELLOW);
                        continue;
                    case "B":
                        g.setColor(Color.BLUE);
                        continue;
                    case "b":
                        g.setColor(Color.CYAN);
                        continue;
                    default:
                }
            }
        }
    }
    private boolean checkTile(int x, int y, Board b){
        if(x < 0 || x >= b.getWidth() || y < 0 || y >= b.getHeight()) return false;
        return b.getTile(x,y ).toString().equals("#");
    }
    private boolean still(Graphics2D g, String str){
        try{
            Image im = new ImageIcon("res/" + str + "-i" + count +".png").getImage();
            g.drawImage(im, size.width/2 - 32,size.height/2 - 32,64,64, null);

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
