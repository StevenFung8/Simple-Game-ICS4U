import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.Timer;
class Frog {
    private double rotation=0;
    private int posX,posY,lanePos,lives;
    private int [] winSpots = {0,0,0,0,0};
    private Image[] frogPics = new Image[2];
    private Image frogPic,frogPic2;
    private Image  currentFrogPic;
    private int frames=53;
    private int qMoves=0;
    private int frameCount=0;
    private String direction;
    public Frog(){
        posX = 378;
        posY = 690;
        lanePos = 1;
        lives = 3;
        try {
            frogPic = ImageIO.read(new File("Pictures/frogpic1.png"));
            frogPic2 = ImageIO.read(new File("Pictures/frogpic2.png"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
        frogPics[0]=frogPic;
        frogPics[1]=frogPic2;
        currentFrogPic=frogPics[0];
    }
    public void checkBound(){
        if (posX>=850){
            death();
        }
        if (posX<=0){
            death();
        }
        if (posY>=690){
            posY=690;
        }
        if (posY<=40){
            posY = 690;
            posX=378-25;

        }

    }
    public void win(int index) {
        posX = 378;
        posY = 690;
        qMoves=0;
        lanePos = 1;
        lives++;
        if (winSpots[index] == 0) {
            winSpots[index] = 1;
        } else {
            death();
        }
    }
    public void death(){
        posX = 378;
        posY = 690;
        qMoves=0;
        lanePos = 1;
        lives --;
        System.out.println(lives);
        if (lives == 0){
            System.exit(69420);
        }
    }
    public int getX(){
        return posX;
    }
    public int getY(){
        return posY;
    }
    public double getRot(){return rotation;}
    public void setRotation(double r ){rotation=r;}
    public int getLives(){return lives;}
    public Image getImage() {
        if (qMoves > 10) {//delay so can't spam moves
            return frogPic2;
        } else {
            return frogPic;
        }
    }
    public int getqMoves(){return qMoves;}
    public int getLanePos() { return lanePos;}
    public int [] getWinSpots(){ return winSpots;}
    public void moveX(int value){
        posX += value;
    }
    public void minuesqMoves() {
        System.out.println(qMoves);
        System.out.println("x" + posX + "y" + posY);

        if (qMoves > 10) {
            qMoves--;
            if (direction.equals("up")) {
                posY -= 1;
            }
            if (direction.equals("down")) {
                posY += 1;
            }
            if (direction.equals("left")) {
                posX -= 1;
            }
            if (direction.equals("right")) {
                posX += 1;
            }
        } else {
            qMoves = 0;
        }


    }
    public void moveUp(){
        qMoves=63;
        lanePos ++;
        direction="up";
    }




    public void moveDown() {
        qMoves=63;
        lanePos--;
        if (lanePos < 1) {
            lanePos = 1;
        }
        direction="down";
    }
    public void moveRight(){
        qMoves=63;
        direction="right";
    }
    public void moveLeft(){
        qMoves=63;
        direction="left";
    }
    public void decreaseFrames(){frames--;}

}
