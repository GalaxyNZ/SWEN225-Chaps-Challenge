package nz.ac.vuw.ecs.swen225.gp20.rendering;

import nz.ac.vuw.ecs.swen225.gp20.application.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;


/**
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
    private String action = "Down", lastAction = "Down";
    private boolean acting = false;
    private float time = 1.0f;
    

    public Rendering(){}

    public void testDrawingAnimation (Graphics g, Main m, String actor){
        Graphics2D g2 = (Graphics2D) g;
        switch (actor){
            case "Down":
                count++;
                still(g2,actor);
                if(count >= 5) count = 0;
                try{
                    Thread.sleep((200));
                }catch(Exception ignored){}
                break;
            case "Up":
                break;
            case "Left":

        }
    }

    public void update(Graphics g, Dimension d){//board
        size = d;
        findPlayerPos();//board
        reCenter(position);
        determineAction();
        draw(g);
    }
    public void changeTiming(float i){
        if(i > 0.0f && i <= 5.0f){
            time = i;
            return;
        }
        try{
            throw new NumberFormatException("Is not within 1 and 5");
        }catch (Exception ignored){}
    }
    public void determineAction(){
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
    public void changeAction(String act){
        if(!acting){
            lastAction = action;
            action = act;
            count = 0;
        }
    }
    public void reCenter(Point p){
        prev = position;
        position = p;
    }
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        count++;

        //Draw Board
        switch (action){
            case "Down":
            case "Left":
            case "Up" :
            case "Right":
                still(g2, action);
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
        try{
            Thread.sleep((long)(200 * time));
        }catch(Exception ignored){}

        if(count >= 5) count = 0;

    }

    private void findPlayerPos(){//Board here
        //nested for loop
    }
    private void still(Graphics2D g, String str){
        g.setColor(new Color(0,0,0,40));
        g.fillOval(12,50,41,20);
        try{
            Image im = new ImageIcon("res/" + str + "-i" + count +".png").getImage();

            g.drawImage(im, 0,0,64,64, null);

        }catch (Exception ignored){}
    }
    private boolean runLeft(Graphics2D g){
        if(count > 5) return false;
        try{
            Image im = new ImageIcon("/res/Run-Left-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
        return true;
    }
    private boolean runRight(Graphics2D g){
        if(count > 5) return false;
        try{
            Image im = new ImageIcon("/res/Run-Right-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
        return true;
    }
    private boolean runUp(Graphics2D g){
        if(count > 5) return false;
        try{
            Image im = new ImageIcon("/res/Run-Up-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
        return true;
    }
    private boolean runDown(Graphics2D g){
        if(count > 5) return false;
        try{
            Image im = new ImageIcon("/res/Run-Down-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
        return true;
    }

    private void soundOnRun(){ }
}
