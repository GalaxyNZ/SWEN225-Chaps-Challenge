package nz.ac.vuw.ecs.swen225.gp20.rendering;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;

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
    private Point position, prev;
    private int count  = 0;
    private String action = "Down", lastAction = "Down", prevTime = "";
    private boolean acting = false;
    private String[] list = {"Down" , "Left", "Right", "Run-Down", "Run-Left", "Run-Right", "Run-Up" , "Up"};
    //private float time = 1.0f;
    public Rendering(){}

    public void testDrawingAnimation (Graphics g, String actor, String currentTime){
            Graphics2D g2 = (Graphics2D) g;
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
                prevTime = currentTime;
    }

    public void update(Graphics g, Dimension d, String currentTime){//board

            size = d;
            findPlayerPos();//board
            reCenter(position);
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
            if (i % 2 == 0) {
                count++;
                if (count > 5) count = 1;
                return true;
            }
        }
        return false;
    }
    private void determineAction(){
        if(prev == position){
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
    private void reCenter(Point p){
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
        if(count == 5) count = 0;

    }
    private void findPlayerPos(){//Board here
        //nested for loop
    }

    private boolean still(Graphics2D g, String str){
        try{
            Image im = new ImageIcon("res/" + str + "-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);

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
        Board b = maze.getBoard();
        int size = b.getWidth();
        int tileSize = (int) d.getWidth()/size;

        for (int y = 0; y < b.getHeight(); y++) {
            for (int x = 0; x < size; x++) {
                String tileChar = b.getTile(x, y).toString();
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
