import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

class Frog {
    private int posX,posY,lanePos,lives;
    private int [] winSpots = {0,0,0,0,0};

    public Frog(){
        posX = 445;
        posY = 735;
        lanePos = 1;
        lives = 3;
    }
    public void checkBound(){
        if (posX>=850){
            death();
        }
        if (posX<=0){
            death();
        }
        if (posY>=791){
            posY=791;
        }
        if (posY<=0){
            posY=0;
        }

    }
    public void win(int index) {
        posX = 440;
        posY = 730;
        lanePos = 1;
        if (winSpots[index] == 0) {
            winSpots[index] = 1;
        } else {
            death();
        }
    }
    public void death(){
        posX = 440;
        posY = 730;
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
    public int getLanePos() { return lanePos;}
    public int [] getWinSpots(){ return winSpots;}
    public void moveX(int value){
        posX += value;
    }
    public void moveY(int value){
        posY += value;
    }
    public void moveUp(){
        posY -= 55 ;
        lanePos ++;
    }
    public void moveDown() {
        posY += 55;
        lanePos--;
        if (lanePos < 1) {
            lanePos = 1;
        }
    }
    public void moveRight(){posX +=55 ;}
    public void moveLeft(){posX -=55 ;}

}
