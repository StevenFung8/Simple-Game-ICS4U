import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

class Frog {
    private int posX,posY;
    public Frog(){
        posX = 400;
        posY = 770;
    }

    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public void movePos(int intervalX, int intervalY){
        posX += intervalX;
        posY += intervalY;
    }
}
