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
    private int posX,posY,posFinalY,lanePos,lives,level,deathFrames;
    private Image deathPic1,deathPic2,deathPic3,deathPic4,deathPic5,deathPic6,deathPic7;
    private int [] winSpots = {1,1,0,1,1};
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
        deathFrames =0;
        lives = 3;
        level = 1;
        posFinalY = 690;
        try {
            frogPic = ImageIO.read(new File("Pictures/frogpic1.png"));
            frogPic2 = ImageIO.read(new File("Pictures/frogpic2.png"));
            deathPic1 = ImageIO.read(new File("Pictures/death1.png"));
            deathPic2 = ImageIO.read(new File("Pictures/death2.png"));
            deathPic3 = ImageIO.read(new File("Pictures/death3.png"));
            deathPic4 = ImageIO.read(new File("Pictures/death4.png"));
            deathPic5 = ImageIO.read(new File("Pictures/death5.png"));
            deathPic6 = ImageIO.read(new File("Pictures/death6.png"));
            deathPic7 = ImageIO.read(new File("Pictures/death7.png"));
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
        if (posY>=690 && posFinalY>=690){
            posY=690;
            posFinalY = 690;
        }
        if (posY<=40 && posFinalY <= 40){
            posY = 690;
            posFinalY = 690;
            posX=378-25;

        }

    }
    public void win(int index) {
        posX = 378;
        posY = 690;
        qMoves=0;
        lanePos = 1;
        lives++;
        posFinalY = 690;
        if (winSpots[index] == 0) {
            winSpots[index] = 1;
            int winSpotCounter = 0;
            for (int w : winSpots){
                if (w == 1){
                    winSpotCounter +=1;
                }
            }
            if (winSpotCounter == 5){
                levelUp(level+1);
            }
        } else {
            death();
        }

    }
    public void death(){
        qMoves=0;
        deathFrames=140;
        lanePos = 1;

        //System.out.println(lives);

        posFinalY = 690;
        System.out.println(lives);
        if (lives == 0){
            System.exit(69420);
        }
    }
    public void levelUp(int value){
        level = value;
        lives = 3;
        for (int i = 0 ; i < 5 ; i++){
            winSpots[i] = 0;
        }
        System.out.println("current level:" + level);

    }
    public void minusDeath(){
        if (deathFrames>0){
            deathFrames--;
        }
        if(deathFrames==140){
            currentFrogPic=deathPic2;
        }
        if(deathFrames==120){
            currentFrogPic=deathPic2;
        }
        else if(deathFrames==100){
            currentFrogPic=deathPic3;
        }
        else if(deathFrames==80){
            currentFrogPic=deathPic4;
        }
        else if(deathFrames==60){
            currentFrogPic=deathPic5;
        }
        else if(deathFrames==40){
            currentFrogPic=deathPic6;
        }
        else if(deathFrames==20){
            currentFrogPic=deathPic7;
        }
        else if(deathFrames==1){
            currentFrogPic=frogPic;
            lives --;
            posX = 378;
            posY = 690;
        }
    }
    public int getX(){
        return posX;
    }
    public int getY(){
        return posY;
    }
    public int getFinalY(){
        return posFinalY;
    }
    public int getLevel(){
        return level;
    }
    public double getRot(){return rotation;}
    public void setRotation(double r ){rotation=r;}
    public int getLives(){return lives;}
    public Image getImage() {
        if (qMoves > 10) {//delay so can't spam moves
            return frogPic2;
        }else{
            return currentFrogPic;
        }
    }
    public int getqMoves(){return qMoves;}
    public int getLanePos() { return lanePos;}
    public int [] getWinSpots(){ return winSpots;}
    public void moveX(int value){
        posX += value;
    }
    public void minuesqMoves() {
        //System.out.println(qMoves);
        //System.out.println("x" + posX + "y" + posY);

        int movementAmount = 2;
        if(qMoves == 1){
            movementAmount = 1;
        }
        if (qMoves > 0) {
            qMoves-=movementAmount;
            if (direction.equals("up")) {
                posY -= movementAmount;
            }
            if (direction.equals("down")) {
                posY += movementAmount;
            }
            if (direction.equals("left")) {
                posX -= movementAmount;
            }
            if (direction.equals("right")) {
                posX += movementAmount;
            }
        } else {
            qMoves = 0;
        }


    }
    public void moveUp(){
        posFinalY -=53;
        qMoves = 53;
        lanePos ++;
        direction="up";
    }

    public void moveDown() {
        qMoves = 53;
        posFinalY +=53;
        lanePos --;
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