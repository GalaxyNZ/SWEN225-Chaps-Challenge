package nz.ac.vuw.ecs.swen225.gp20.rendering;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import nz.ac.vuw.ecs.swen225.gp20.maze.Board;

import javax.swing.*;
import java.awt.*;


/**
 * FPS = 5fps
 *
 * Order of act-
 * 1.init class (done once)  or update board
 * 2.find player position and previous position
 * 3.determine the action and (maybe)change the action
 * 4.draw
 */
public class Rendering {
    private Dimension size;
    private Point position, prev, center;
    private int count  = 0, frames = 5;
    private String  prevTime = "";
    private boolean acting = false;
    private Classification action = Classification.Down, lastAct = Classification.Down;
    //private float time = 1.0f;
    public Rendering(){}

    public void testDrawingAnimation (Graphics g, String actor, Dimension d, Maze m){
        size = d;
        center = new Point(size.width/2, size.height/2);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(new ImageIcon("res/Background.png").getImage(), -(int)(size.width*0.1)/2,-(int)(size.height*0.1)/2, (int)(size.width*1.1), (int)(size.height*1.1), null);

        if(m != null){
            findPlayerPos(m.getBoard());
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
        center = new Point(size.width/2, size.height/2);
        findPlayerPos(m.getBoard());//board
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
        int chunkCenter = (((chunkSize * 70) - size.width)/2)+1;//Center is always off by 1
        drawBackgroundInChunk(g, chunkSize, chunkCenter, b);

    }
    private void checkPosition(Point p){
        prev = position;
        position = p;
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
    private void findPlayerPos(Board b){//Board here
        for(int j = 0; j < b.getHeight(); j++){
            for(int i = 0; i < b.getWidth(); i++) {
                 String tileChar = b.getTile( i,  j).toString();
                    if(tileChar.equals("X")){
                        checkPosition(new Point(i,j));
                    }
            }}
        //nested for loop
    }

    private void drawBackgroundInChunk(Graphics2D g, int chunkSize, int chunkCenter, Board b){
        int indexX = position.x - chunkSize/2 ;
        int indexY = position.y - chunkSize/2 ;

        for(int j = 0; j < chunkSize; j++){
            for(int i = 0; i < chunkSize; i++){
                if(indexX+i < 0 || indexX+i > b.getWidth()-1 || indexY+j < 0 || indexY+j >= b.getHeight()-1) continue;
                Point defaultP = new Point((i* 70)-chunkCenter,(j* 70)-chunkCenter);
                int wh = 70;
                String tileChar = b.getTile(indexX+i,indexY+j).toString();
                switch (tileChar) {
                    case "#":
                        new TileDesigns(g,defaultP,wh,Classification.Wall);
                        break;
                    case "%":
                        new TileDesigns(g,defaultP,wh,Classification.Exit);
                        break;
                    case "E":
                        new TileDesigns(g,defaultP,wh,Classification.ELI);
                        break;
                    case "i":
                        g.setColor(new Color(92, 12, 144));
                        break;
                    case "T":
                        g.setColor(Color.darkGray);
                        break;
                    case "G":
                        g.setColor(Color.GREEN);
                        break;
                    case "g":
                        g.setColor(new Color(13, 120, 6, 255));
                        break;
                    case "R":
                        g.setColor(Color.RED);
                        break;
                    case "r":
                        g.setColor(Color.pink);
                        break;
                    case "Y":
                        g.setColor(Color.ORANGE);
                        break;
                    case "y":
                        g.setColor(Color.YELLOW);
                        break;
                    case "B":
                        g.setColor(Color.BLUE);
                        break;
                    case "b":
                        g.setColor(Color.CYAN);
                        break;
                    default:
                }
            }
        }
    }
    private boolean still(Graphics2D g, String str){
        try{
            Image im = new ImageIcon("res/" + str + "-i" + count +".png").getImage();
            g.drawImage(im, center.x - 32,center.y - 32,64,64, null);

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
