package nz.ac.vuw.ecs.swen225.gp20.rendering;

import nz.ac.vuw.ecs.swen225.gp20.maze.*;

import javax.swing.*;
import java.awt.*;

public class Rendering {
    private Dimension size;
    private Point position, prev;
    private int count  = 0, aniValX = 0, aniValY = 0;
    private String  prevTime = "";
    private boolean acting = false, lr = false, pause = false;
    private CL action = CL.Down, lastAct = CL.Down;
    private final int[] XZMovements = {1,6,10,28,45,60};
    private final int[] imIndex = {1,2,2,3,4,4};
    private final EnemyInfo[] EI = {new EnemyInfo(), new EnemyInfo(), new EnemyInfo()};
    private Maze m;
    Sound sound;

    /**
     * Initialise new Sound
     */
    public Rendering(){
        sound = new Sound();
    }

    /**
     * pauses renderer if game paused
     * @param b, pause is true if b is true
     */
    public void setPause(boolean b){
        pause = b;
        if(pause) prevTime = "";
        else{
            if(prevTime.equals(""))
            sound.updateSound("/assets/Slow-Breathing.wav");
        }
    }

    /**
     * The core of the method, it handles the calling of methods to setup the canvas
     * @param g, Graphics 2d
     * @param d, board dimensions
     * @param m, maze
     */
    public void update(Graphics g, Dimension d, Maze m){//board
        Graphics2D g2 = (Graphics2D) g;
        if(pause){
            g2.drawImage(new ImageIcon("res/pause.png").getImage(),0,0,d.width,d.height, null);
            sound.updateSound("/assets/Elevator.wav");
            return;
        }
        g2.drawImage(new ImageIcon("res/Background.png").getImage(), -(int)(d.width*0.1)/2,-(int)(d.height*0.1)/2, (int)(d.width*1.1), (int)(d.height*1.1), null);
        if(m == null) return;
        if(!acting){
            aniValX = 0;
            aniValY = 0;
        }
        this.m = m;
        size = d;
        if(!acting) prev = position;
        position = m.getPlayerLocation();
        determineAction();
        if(action == CL.RunRight) aniValX = XZMovements[count-1];
        if(action == CL.RunLeft) aniValX = -XZMovements[count-1];
        if(action == CL.RunDown) aniValY = XZMovements[count-1];
        if(action == CL.RunUp) aniValY = -XZMovements[count-1];
        findChunk(g2);
        draw(g2);

    }

    //Getter Method
    public boolean isActing(){return acting;}

    //Helper Methods

    /**
     * A helper method used externally to reveal if the frame should be redrawn or not
     * @param currentTime, the current time in a string
     * @return true if the frame should be redrawn
     */
    public boolean updateFrame(String currentTime){
        int i = (int) (Float.parseFloat(currentTime.substring(currentTime.indexOf("."))) * 10);
        if(!prevTime.equals(currentTime)) {

            if((int)Float.parseFloat(currentTime) > 30 && sound.currentTrack().equals("/assets/Slow-Breathing.wav")){
                sound.updateSound("/assets/Hard-Breathing.wav");
            }
            else if((int)Float.parseFloat(currentTime) == 0) sound.end();
            prevTime = currentTime;
            if(acting){
                count++;
                return true;
            }
            if (i % 2 == 0) {
                count++;
                return true;
            }
        }

        return false;
    }

    /**
     * helper method for determining the direction and orientation of the players current action
     */
    private void determineAction(){
        if(prev == null || position == null || acting) return;
        if (prev == position){
            switch (lastAct){
                case RunLeft:
                case Left:
                    lastAct = action;
                    action = CL.Left;
                    break;
                case RunRight:
                case Right:
                    lastAct = action;
                    action = CL.Right;
                    break;
                case RunDown:
                case Down:
                    lastAct = action;
                    action = CL.Down;
                    break;
                case RunUp:
                case Up:
                    lastAct = action;
                    action = CL.Up;

            }
            return;
        }else if(prev.x < position.x) {
            count = 1;
            lastAct = CL.Right;
            action = CL.RunRight;
        }else if(prev.x > position.x){
            count = 1;
            lastAct = CL.Left;
            action = CL.RunLeft;
        }else if(prev.y < position.y){
            count = 1;
            lastAct = CL.Down;
            action = CL.RunDown;
        }else if(prev.y > position.y){
            count = 1;
            lastAct = CL.Up;
            action = CL.RunUp;
        }
        lr = !lr;
    }

    /**
     * calculates index size of the shown board
     * @param g, Graphics 2d
     */
    private void findChunk(Graphics2D g){
        int chunkSize = 5 + 2*(((size.width/2)-35)/70);
        int center = (((chunkSize * 70) - size.width)/2);
        drawBackgroundInChunk(g, chunkSize, center, false);
        drawBackgroundInChunk(g, chunkSize, center, true);
        drawMap(g);

    }

    /**
     * calls on methods to draw the player
     * @param g, Graphics 2d
     */
    private void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        //Draw Board
        switch (action){
            default:
                acting = still(g2, action.toString());
                return;
            case RunLeft:
                g2.drawImage(new ImageIcon("res/Shadow.png").getImage(),size.width/2 - 35,size.height/2 - 34,70,70, null);
                acting = runLeft(g2);
                break;
            case RunRight:
                g2.drawImage(new ImageIcon("res/Shadow.png").getImage(),size.width/2 - 35,size.height/2 - 34,70,70, null);
                acting = runRight(g2);
                break;
            case RunUp:
                g2.drawImage(new ImageIcon("res/Shadow.png").getImage(),size.width/2 - 35,size.height/2 - 34,70,70, null);
                acting = runUp(g2);
                break;
            case RunDown:
                g2.drawImage(new ImageIcon("res/Shadow.png").getImage(),size.width/2 - 35,size.height/2 - 34,70,70, null);
                acting= runDown(g2);

        }
        if(count >= imIndex.length -1) sound.walk(lr);

    }

    /**
     * draws the minimap
     * @param g, Graphics 2d
     */
    private void drawMap(Graphics2D g){
        for(int y = 0; y <= m.getBoardSize().y-1; y++){
            for(int x = 0; x <= m.getBoardSize().x-1; x++){
                Tile t =  m.getBoardTile(new Point(x,y));

                String tileChar = t == null ? "_":t.toString();
                Color c;
                switch (tileChar) {
                    default:
                        if(tileChar.equals("X")) c = new Color(255,255,255,70);
                        else c = new Color(100,100,100,150);
                        break;
                    case "#":
                        c = new Color(50,50,50,150);
                        break;
                    case "g":
                    case"G":
                        c = new Color(0,255,0,150);
                        break;
                    case "b":
                    case"B" :
                        c = new Color(0,0,255,150);
                        break;
                    case "r":
                    case "R":
                        c = new Color(255,0,0,150);
                        break;
                    case "y":
                    case"Y":
                        c = new Color(255,255,0,150);
                        break;
                    case "T":
                        c = new Color(255,240,150, 150);
                        break;
                    case"E":
                    case "%":
                        c = new Color(150,150,250,150);
                        break;
                    case "0":
                    case "1":
                    case "2":
                        c = new Color(250,150,150,150);
                        break;

                }
                g.setColor(c);
                g.fillRect(x*10,y*10,10,10);

            }
        }
    }

    /**
     * draws the background that can be seen
     * @param g, Graphics 2d
     * @param chunkSize, size of shown board
     * @param center, center of shown board
     * @param draw, draw or fill
     */
    private void drawBackgroundInChunk(Graphics2D g, int chunkSize, int center, boolean draw){
        for (EnemyInfo enemyInfo : EI) {
            enemyInfo.showExposed(enemyInfo.isExposed());
        }
        int indexX = prev == null ? position.x :(aniValX ==0? position.x:prev.x) - chunkSize/2 ;
        int indexY = prev == null ? position.y: (aniValY ==0? position.y:prev.y)  - chunkSize/2 ;

        for(int j = 0; j < chunkSize ; j++){
            for(int i = 0; i < chunkSize; i++){
                int x = indexX+i, y = indexY+j;
                if(x < 0 || x >= m.getBoardSize().x || y < 0 || y >= m.getBoardSize().y) continue;

                Point defaultP = new Point(((i* 70)-center)-aniValX,(j* 70)-center-aniValY);
                int wh = 70;
                Tile t =  m.getBoardTile(new Point(x,y));

                String tileChar = t == null ? "_":t.toString();
                CL cl = CL.Floor;
                String tile = "";
                if(checkTile(x,y+1)) tile += CL.D.toString();
                if(checkTile(x,y-1)) tile += CL.U.toString();
                if(checkTile(x-1,y)) tile += CL.L.toString();
                if(checkTile(x+1,y)) tile += CL.R.toString();
                switch (tileChar) {
                    default:
                        cl = CL.Floor;
                        break;
                    case "#":
                        switch(tile){
                            case "U":
                                cl = CL.U;
                                break;
                            case "D":
                                cl = CL.D;
                              break;
                            case "L":
                                cl = CL.L;
                                break;
                            case "R":
                                cl = CL.R;
                                break;
                            case "DU":
                                cl =  CL.DU;
                                break;
                            case "LR":
                                cl =  CL.LR;
                                break;
                            case "DUL":
                                cl =  CL.DUL;
                                break;
                            case "DUR":
                                cl =  CL.DUR ;
                                break;
                            case "DLR":
                                cl =  CL.DLR ;
                                break;
                            case "ULR":
                                cl =  CL.ULR ;
                               break;
                            case "DULR":
                                cl =  CL.DULR ;
                                break;
                            case "DR":
                                cl =  CL.DR ;
                                break;
                            case "DL":
                                cl =  CL.DL ;
                                break;
                            case "UR":
                                cl =  CL.UR ;
                                break;
                            case "UL":
                                cl =  CL.UL ;
                        }
                        break;
                    case "g":
                        cl =  CL.GK ;
                        break;
                    case "b":
                        cl =  CL.BK;
                        break;
                    case "r":
                        cl =  CL.RK ;
                        break;
                    case "y":
                        cl =  CL.YK;
                        break;
                    case "T":
                        cl =  CL.Treasure;
                        break;
                    case "I":
                        cl =  CL.Info ;
                        break;
                    case "R":
                        new TileDesigns(g,defaultP,wh, chunkSize,  new Point(i,j), CL.RD,  tile.equals("DU")?CL.DU:CL.LR,draw );
                        continue;
                    case"G":
                        new TileDesigns(g,defaultP,wh, chunkSize,  new Point(i,j), CL.G,   tile.equals("DU")?CL.DU:CL.LR,draw );
                        continue;
                    case"B" :
                        new TileDesigns(g,defaultP,wh, chunkSize,  new Point(i,j), CL.B,   tile.equals("DU")?CL.DU:CL.LR,draw );
                        continue;
                    case"Y":
                        new TileDesigns(g,defaultP,wh,chunkSize,  new Point(i,j), CL.Y,  tile.equals("DU")?CL.DU:CL.LR,draw );
                        continue;
                    case"E":
                        new TileDesigns(g,defaultP,wh,chunkSize,  new Point(i,j), CL.Exit,  tile.equals("DU")?CL.DU:CL.LR,draw );
                        continue;
                    case "0":
                        EI[0].changePos(new Point(x,y));
                        EI[0].setPos(defaultP);
                        EI[0].showExposed(true);
                        EI[0].draw(g);
                        break;
                    case "1":

                        EI[1].changePos(new Point(x,y));
                        EI[1].setPos(defaultP);
                        EI[1].showExposed(true);
                        EI[1].draw(g);
                        break;
                    case "2":
                        EI[2].changePos(new Point(x,y));
                        EI[2].setPos(defaultP);
                        EI[2].showExposed(true);
                        EI[2].draw(g);
                        break;
                    case "%":
                        if(!draw)g.drawImage(new ImageIcon("res/Exit.png").getImage(),defaultP.x,defaultP.y,wh,wh,null);
                        else g.drawRect(defaultP.x,defaultP.y,wh,wh);
                        continue;
                }
                new TileDesigns(g, defaultP, wh, chunkSize, new Point(i,j), cl, draw);

            }
        }
    }

    /**
     * helper method for checking wall orientation
     * @param x, pos in index x
     * @param y, pos in index y
     * @return true if tile is connected
     */
    private boolean checkTile(int x, int y){
        if(x < 0 || x >= m.getBoardSize().x || y < 0 || y >= m.getBoardSize().y) return false;
        Tile t = m.getBoardTile(new Point(x,y));
        if( t == null) return false;
        return t.toString().equals("#")||t.toString().equals("G")||t.toString().equals("B")||
                t.toString().equals("R")||t.toString().equals("Y")||t.toString().equals("E");
    }

    /**
     * helper method for drawing still movements
     * @param g, Graphics 2d
     * @param str, string orientation of player
     * @return false
     */
    private boolean still(Graphics2D g, String str){
        try{
            Image im = new ImageIcon("res/" + str + "-i" + count +".png").getImage();
            g.drawImage(im, size.width/2 - 35,size.height/2 - 35,70,70, null);

        }catch (Exception e){
            System.out.println("res/" + str + "-i" + count +".png");
        }
        if(count == 5) count = 1;
        return false;
    }

    /**
     *
     * @param g, Graphics 2d
     * @return true if animating
     */
    private boolean runLeft(Graphics2D g){
        if(count > imIndex.length || count < 1){
            count = 1;
            return false;
        }
        try{
            int i = imIndex[count-1];
            if(lr) i = i * 11;
            Image im = new ImageIcon("res/Run-Left-i" + i +".png").getImage();
            g.drawImage(im, size.width/2 - 35,size.height/2 - 35,70,70, null);

        }catch (Exception ignored){ }
        if(count == imIndex.length){
            count = 1;
            return false;
        }
        return true;
    }

    /**
     *
     * @param g, Graphics 2d
     * @return true if animating
     */
    private boolean runRight(Graphics2D g){
        if(count > imIndex.length || count < 1){
            count = 1;
            return false;
        }
        try{
            int i = imIndex[count-1];
            if(lr) i = i * 11;
            Image im = new ImageIcon("res/Run-Right-i" + i +".png").getImage();
            g.drawImage(im, size.width/2 - 35,size.height/2 - 35,70,70, null);

        }catch (Exception ignored){}
        if(count == imIndex.length){
            count = 1;
            return false;
        }
        return true;
    }

    /**
     *
     * @param g, Graphics 2d
     * @return true if animating
     */
    private boolean runUp(Graphics2D g){
        if(count > imIndex.length || count < 1){
            count = 1;
            return false;
        }
        try{
            int i = imIndex[count-1];
            if(lr) i = i * 11;
            Image im = new ImageIcon("res/Run-Up-i" + i +".png").getImage();
            g.drawImage(im, size.width/2 - 35,size.height/2 - 35,70,70, null);

        }catch (Exception ignored){ }
        if(count == imIndex.length){
            count = 1;
            return false;
        }
        return true;
    }

    /**
     *
     * @param g, Graphics 2d
     * @return true if animating
     */
    private boolean runDown(Graphics2D g){
        if(count > imIndex.length || count < 1){
            count = 1;
            return false;
        }
        try{
            int i = imIndex[count-1];
            if(lr) i = i * 11;
            Image im = new ImageIcon("res/Run-Down-i" + i +".png").getImage();
            g.drawImage(im, size.width/2 - 35,size.height/2 - 35,70,70, null);

        }catch (Exception ignored){ }
        if(count == imIndex.length){
            count = 1;
            return false;
        }
        return true;
    }


}
