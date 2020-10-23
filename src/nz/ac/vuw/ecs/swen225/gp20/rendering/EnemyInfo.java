package nz.ac.vuw.ecs.swen225.gp20.rendering;

import javax.swing.*;
import java.awt.*;

public class EnemyInfo {
    private Point curr, prev, pos;
    private CL  action = CL.Up;
    private boolean exposed = false;
    private int c = 0;
    private int[] xz = {1,1,1,2,2,2};

    public EnemyInfo(){}
    public void showExposed(Boolean b){
        exposed = b;
    }
    public boolean isExposed(){return exposed;}
    public void changePos(Point p){
        if(prev == null) prev = p;
        prev = curr;
        curr = p;
        determineAction();

    }
    public void setPos(Point p){
        pos = p;
    }
    private void determineAction(){
        if(prev == null || curr == null) return;
        if(prev.x < curr.x) {
            action = CL.Right;
        }else if(prev.x > curr.x){
            action = CL.Left;
        }else if(prev.y < curr.y){
            action = CL.Down;
        }else if(prev.y > curr.y){
            action = CL.Up;
        }
    }
    public void draw(Graphics2D g){
        if(prev == null) return;
        if(exposed){
            if(c > 5) c = 0;
            g.drawImage(new ImageIcon("res/Enemy-"+action.toString()+"-i"+ xz[c]+".png").getImage(), pos.x+15,pos.y+15, 40,40, null);
            c++;

        }

    }
}
