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
    public Frog(){
        posX = 378;
        posY = 690+20;
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
        if (posY>=700){
            posY=700;
        }
        if (posY<=40){
            posY = 690+20;
            posX=378-25;

        }

    }
    public void win(int index) {
        posX = 378;
        posY = 690+20;
        lanePos = 1;
        if (winSpots[index] == 0) {
            winSpots[index] = 1;
        } else {
            death();
        }
    }
    public void death(){
        posX = 378;
        posY = 690+20;
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
    public Image getImage(){return currentFrogPic;}
    public int getLanePos() { return lanePos;}
    public int [] getWinSpots(){ return winSpots;}
    public void moveX(int value){
        posX += value;
    }
    public void moveY(int value){
        posY += value;
    }
    public void moveUp(){
        while(frames>0) {
            posY -= 53;
            currentFrogPic=frogPic2;
        }
        currentFrogPic=frogPic;
        frames=53;
        lanePos ++;
    }
    public void moveDown() {
        posY += 53;
        lanePos--;
        if (lanePos < 1) {
            lanePos = 1;
        }
    }
    public void moveRight(){posX +=53 ;}
    public void moveLeft(){posX -=53 ;}
    public void decreaseFrames(){frames--;}
}
