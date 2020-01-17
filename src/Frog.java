import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

class Frog {
    private int posX,posY;
    private Image frogpng;

    public Frog(){
        posX = 400;
        posY = 700;
    }
    public void checkBound(){
        if (posX>=810-55){
            posX=810-55;
        }
        if (posX<=0){
            posX=0;
        }
        if (posY>=700){
            posY=700;
        }
        if (posY<=0){
            posY=0;
        }

    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public void moveUp(){posY -=55 ;}
    public  void moveDown(){posY +=55 ;}
    public  void moveRight(){posX +=55 ;}
    public void moveLeft(){posX -=55 ;}

}
