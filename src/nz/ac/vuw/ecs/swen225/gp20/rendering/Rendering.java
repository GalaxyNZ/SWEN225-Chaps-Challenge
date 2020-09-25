package nz.ac.vuw.ecs.swen225.gp20.rendering;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;

public class Rendering {
    private Dimension size;
    private Point position, prev;
    private int count  = 0;
    private String action = "Still", lastAction = "Still";
    private boolean acting = false;
    double time = 1;
    int[][] board;

    public Rendering(Dimension d, Graphics g){//board
        size = d;
        findPlayerPos();//board
        reCenter(position);
        determineAction();
        draw(g);
    }
    public void updateBoard(Graphics g){//board
        findPlayerPos();//board
        reCenter(position);
        determineAction();
        draw(g);
    }
    public void changeTiming(int i){
        switch (i){
            case 1:
                time = 0.25;
            case 2:
                time = 0.5;
            case 3:
                time = 1;
            case 4:
                time = 1.5;
            case 5:
                time = 2;
            default:
                if(i < 1 || i > 5){
                    try{
                        throw new NumberFormatException("Must be within 1 - 5");
                    }catch (Exception ignored){}
                }
        }
    }
    public void determineAction(){
        if(prev == position && lastAction.equals("Still")) changeAction("Still");
    }
    public void changeAction(String act){
        if(!acting){
            lastAction = action;
            action = act;
            count = 0;
        }
    }
    public void setSize(Dimension d){size = d;}
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
                stillDown(g2);
                break;
            case "Left":
                stillLeft(g2);
                break;
            case "Up" :
                stillUp(g2);
                break;
            case "Right":
                stillRight(g2);
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
            Thread.sleep(200 * (long)time);
        }catch(Exception ignored){}

        if(count > 5) count = 0;

    }

    private void findPlayerPos(){//Board here
        //nested for loop
    }
    private void stillDown(Graphics2D g){
        try{
            Image im = new ImageIcon("/res/Still-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
    }
    private void stillUp(Graphics2D g){
        try{
            Image im = new ImageIcon("/res/Up-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
    }
    private void stillLeft(Graphics2D g){
        try{
            Image im = new ImageIcon("/res/Left-i" + count +".png").getImage();
            g.drawImage(im, 0,0,64,64, null);
        }catch (Exception ignored){}
    }
    private void stillRight(Graphics2D g){
        try{
            Image im = new ImageIcon("/res/Right-i" + count +".png").getImage();
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
