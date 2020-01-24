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
class Frog { //this is the object class for the frog player containing all the stats such as x,y,lives,direction,rotation etc.
    private double rotation=0; // variable for rotating the frog
    private int posX,posY,posFinalY,lanePos,lives,level;
    private int [] winSpots = {0,0,0,0,0};//postion for win spots on top
    private Image frogPic,frogPic2;
    private Image  currentFrogPic;//current picture of the frog resting vs moving
    private Image deathPic1,deathPic2,deathPic3,deathPic4,deathPic5,deathPic6,deathPic7,winnerPic,loserPic;
    private int deathFrames=0;
    private int qMoves=0;
    private String direction;
    private Image endPic;
    private String endStat="";
    private int finalDeathQ=0;
    public Frog(){
        posX = 378;//frog position
        posY = 690;
        lanePos = 1;//check which lane the frog is in
        deathFrames =0;//for death animation
        lives = 3;//player lives
        level = 1;//which level
        posFinalY = 690;// posFinalY is the final where the frog will eventually land.
        try {
            frogPic = ImageIO.read(new File("Pictures/frogpic1.png"));//loading pictures for player
            frogPic2 = ImageIO.read(new File("Pictures/frogpic2.png"));
            deathPic1 = ImageIO.read(new File("Pictures/death1.png"));//death animations
            deathPic2 = ImageIO.read(new File("Pictures/death2.png"));
            deathPic3 = ImageIO.read(new File("Pictures/death3.png"));
            deathPic4 = ImageIO.read(new File("Pictures/death4.png"));
            deathPic5 = ImageIO.read(new File("Pictures/death5.png"));
            deathPic6 = ImageIO.read(new File("Pictures/death6.png"));
            deathPic7 = ImageIO.read(new File("Pictures/death7.png"));
            winnerPic= ImageIO.read(new File("Pictures/winner.png"));
            loserPic= ImageIO.read(new File("Pictures/gameOver.png"));


        }
        catch (IOException e) {
            System.out.println(e);
        }

        currentFrogPic=frogPic;


    }
    //getter functions
    public int getX(){ return posX; }
    public int getY(){ return posY; }
    public int getFinalY(){ return posFinalY; }
    public int getLevel(){ return level; }
    public double getRot(){return rotation;}
    public void setRotation(double r ){rotation=r;}
    public int getLives(){return lives;}
    public int getqMoves(){return qMoves;}
    public int getLanePos() { return lanePos;}
    public int [] getWinSpots(){ return winSpots;}
    public Image getImage() {
        if (qMoves > 10) {//delay so can't spam moves
            return frogPic2;
        }else{
            return currentFrogPic;
        }
    }
    public void win(int index) { //this method is called when you win
        posX = 378;//reset stats of the frog
        posY = 690;
        qMoves=0;
        lanePos = 1;
        lives++;
        if (lives>3){//max amount of lives
            lives=3;
        }
        posFinalY = 690;

        if (winSpots[index] == 0) { //takes the parameter and sets the win spot that you landed on as taken
            winSpots[index] = 1;
            int winSpotCounter = 0;
            for (int w : winSpots){
                if (w == 1){
                    winSpotCounter +=1;
                }
            }
            if (winSpotCounter == 5){
                if (level==3){
                    endPic=winnerPic;
                    endStat="win";
                    finalDeathQ=200;
                }
                levelUp(level+1);
            }
        } else { //if you land on a winSpot that you have already went onto, you will die
            death();
        }

    }
    public void death(){ //called when you cause an action that will make you die

        qMoves=0;
        if (deathFrames==0) {
            deathFrames = 140;
        }
        lanePos = 1;
        posFinalY = 690;


    }
    public void levelUp(int value){ //if all five winSpots are reached, your level moves up
        level = value;
        lives = 3;
        for (int i = 0 ; i < 5 ; i++){
            winSpots[i] = 0;

        }

    }
    public void moveX(int value){ //changes player x by a constant value
        posX += value;
    }
    public void minusDeath(){//death animation

        if (deathFrames>0){
            deathFrames--;
            System.out.println(deathFrames);
        }
        if(deathFrames==140){//when queued up death frames go down changes pictures at checkpoints
            currentFrogPic=deathPic1;
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
            currentFrogPic=frogPic;//changing frog pic to neutral
            lives --;//subtract lives
            posX = 378;//reset pos
            posY = 690;
            if (lives == 0){
                endPic=loserPic;//gameover picture
                endStat="lose";//lost the game
                finalDeathQ=200;//how long to wait before closing program

            }
        }



    }
    public void minusFinalDeathQ(){//what to do when finished game
        if (finalDeathQ>0) {
            finalDeathQ--;
        }
        if(finalDeathQ==1){
            System.exit(12345678);
        }

    }
    public void minusqMoves() {//Once queued up moves r set it will subtract until movement is done
        int movementAmount = 2;//change in speed
        if(qMoves == 1){
            movementAmount = 1;
        }
        if (qMoves > 0 && deathFrames==0) {//Can't move until finished death animation
            qMoves-=movementAmount;//slow movement 1 pixel at a time
            if (direction.equals("up")) {//direction
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
    public void moveUp(){ //when the up arrow key is pressed it calls this method
        posFinalY -=53;
        qMoves = 53;
        lanePos ++;
        direction="up";
    }

    public void moveDown() { //when the down arrow key is pressed it calls this method
        qMoves = 53;
        posFinalY +=53;
        lanePos --;
        if (lanePos < 1) {
            lanePos = 1;
        }
        direction="down";
    }
    public void moveRight(){ //when the up arrow key is pressed it calls this method
        qMoves=63;
        direction="right";
    }
    public void moveLeft(){ //when the left arrow key is pressed it calls this method
        qMoves=63;
        direction="left";
    }

    public int getDeathFrames() {
        return deathFrames;
    }

    public void changeY(int i) { //sets y to a specific value
        posY=i;
    }

    public void changeFinalY(int i) { //sets y to a specific value
        posFinalY=i;
    }
    public Image getEndPic(){
        return endPic;
    }
    public String getEndStat(){
        return endStat;
    }

}