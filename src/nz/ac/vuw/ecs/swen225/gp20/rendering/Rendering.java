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
    private String action = "Down", lastAction = "Down", prevTime = "";
    private boolean acting = false;
    private String[] list = {"Down" , "Left", "Right", "Run-Down", "Run-Left", "Run-Right", "Run-Up" , "Up"};
    //private float time = 1.0f;
    public Rendering(){}

    public void testDrawingAnimation (Graphics g, String actor, Dimension d, Maze m){
        size = d;
        center = new Point(size.width/2, size.height/2);

        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(new ImageIcon("res/Background.png").getImage(), -(int)(size.width*0.1)/2,-(int)(size.height*0.1)/2, (int)(size.width*1.1), (int)(size.height*1.1), null);

        if(m != null){
            findPlayerPos(m);
            findChunk(g2, m);
        }
                switch (actor) {
                    case "Down":
                    case "Up":
                    case "Left":
                    case "Right":
                        still(g2, actor);
                        break;
                    default:
                        try{
                            StringBuilder message = new StringBuilder();
                            for (String s : list) {
                                message.append("\n").append(s);
                            }
                            throw new ErrorMessage("Error\n" +
                                    "Check Actor is listed below: " + message);
                        }catch (Exception ignored){}

                }
    }

    public void update(Graphics g, Dimension d, Maze m){//board

            size = d;
            center = new Point(size.width/2, size.height/2);

            findPlayerPos(m);//board
            checkPosition(position);
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
            switch (lastAction){
                case "Run-Left":
                case "Left":
                    changeAction("Left");
                    break;

                case "Run-Right":
                case "Right":
                    changeAction("Right");
                    break;

                case "Run-Up":
                case "Up":
                    changeAction("Up");
                    break;
                case "Run-Down":
                case "Down":
                    changeAction("Down");
                    break;
            }
        }


    }
    private void changeAction(String act){
        if(!acting){
            lastAction = action;
            action = act;
            count = 0;
        }
    }
    private void findChunk(Graphics2D g, Maze m){
        int chunkSize = 3 + 2*(((size.width/2)-35)/70);
        int chunkCenter = (((chunkSize * 70) - size.width)/2)+1;//Center is always off by 1
        drawBackgroundInChunk(g, chunkSize, chunkCenter, m);

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
            case "Down":
            case "Left":
            case "Up" :
            case "Right":
                acting = still(g2, action);
                break;

            case "Run-Left":
                acting = runLeft(g2);
                break;
            case "Run-Right":
                acting = runRight(g2);
                break;
            case "Run-Up":
                acting = runUp(g2);
                break;
            case "Run- Down":
                acting= runDown(g2);

        }
        if(acting){
            soundOnRun();
        }

    }
    private void findPlayerPos(Maze m){//Board here
        for(int j = 0; j < m.getBoardSize().y; j++){
            for(int i = 0; i < m.getBoardSize().x; i++) {
                Tile t = m.getBoardTile(new Point(i,  j));
                 String tileChar = t.toString();
                    if(tileChar.equals("X")){
                        position = new Point(i,j);
                    }
            }}
        //nested for loop
    }

    private void drawBackgroundInChunk(Graphics2D g, int chunkSize, int chunkCenter, Maze m){
        int indexX = position.x - chunkSize/2 ;
        int indexY = position.y - chunkSize/2 ;

        for(int j = 0; j < m.getBoardSize().y; j++){
            for(int i = 0; i < m.getBoardSize().x; i++){
                if(indexX+i < 0 || indexX+i > m.getBoardSize().x-1 || indexY+j < 0 || indexY+j >= m.getBoardSize().y-1) continue;
                String tileChar = m.getBoardTile(new Point(indexX+i, indexY+j)).toString();
                g.setColor(Color.GRAY);
                switch (tileChar) {
                    case "_":
                        g.setColor(Color.WHITE);
                        break;
                    case "#":
                        g.setColor(Color.GRAY);
                        break;
                    case "%":
                        g.setColor(Color.MAGENTA);
                        break;
                    case "E":
                        g.setColor(new Color(40, 104, 90));
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
                }
                g.fillRect((i* 70)-chunkCenter, (j*70)-chunkCenter, 70, 70);
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

    public void drawBoard(Graphics g, Dimension d, Maze maze) {
       // Board b = maze.getBoard();
        int size = maze.getBoardSize().x;
        int tileSize = (int) d.getWidth()/size;

        for (int y = 0; y < maze.getBoardSize().y; y++) {
            for (int x = 0; x < size; x++) {
                String tileChar = maze.getBoardTile(new Point(x,y)).toString();
                g.setColor(Color.GRAY);
                switch (tileChar) {
                    case "_":
                        g.setColor(Color.WHITE);
                        break;
                    case "#":
                        g.setColor(Color.GRAY);
                        break;
                    case "%":
                        g.setColor(Color.MAGENTA);
                        break;
                    case "E":
                        g.setColor(new Color(40, 104, 90));
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
                    case "X":
                        g.setColor(Color.black);
                        break;
                }
                g.fillRect(x*tileSize, y*tileSize, tileSize, tileSize);
            }
        }
    }

}
