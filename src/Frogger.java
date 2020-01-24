import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.Timer;

public class Frogger extends JFrame{
    Timer myTimer;
    MainGame game;
    private int width=756; //screen width and height
    private int height=810;
    private int timePassed=0;//total time passed for timer
    private int finalTime=0;

    public Frogger() {
        super("Frogger by Dylan and Steven");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width,height);

        myTimer = new Timer(10, new TickListener());//tick every 10 ms
        myTimer.start();

        game = new MainGame();
        add(game);

        setResizable(false);
        setVisible(true);
    }

    class TickListener implements ActionListener { //CALL FUNCTIONS HERE (JUST LIKE PYGAME "WHILE RUNNING LOOP")
        public void actionPerformed(ActionEvent evt) {
            if (game != null && game.ready) {
                game.laneMovement();//moving obstacles
                game.move();//moving player
                game.player.minusqMoves();//Subtracting queued up moves from move method
                game.collision();//check collision
                game.repaint();
                game.player.minusDeath();//subtracting queued up animations from death

                timePassed += 10;//adding time
                if (timePassed == 1000) {//goes down every 1 second

                    game.decreaseTime();//decreasing from timer
                    timePassed = 0;
                }
                if (game.player.getEndStat()!=""){//once game ends starts counting down until close
                    game.player.minusFinalDeathQ();
                }
                game.checkLevel();
                game.checkBound();
            }
        }

    }

    public static void main(String[] arguments) {
        Frogger frame = new Frogger();

    }

}

class MainGame extends JPanel implements KeyListener {
    Frog player = new Frog();//create new player
    public boolean ready=false;//idk
    private boolean clickUp,clickDown,clickLeft,clickRight = true; //for movement
    private boolean []keys;//list of pressed keys
    private Font froggerFont;//font for words
    private int score=0;//initial score
    private int totalMove=700;//total moves made to keep track of when to increase score
    private int time=30;//time to complete level
    private static Image back,winFrogPic,heart;
    private static Lanes lanes[] = new Lanes [12];
    private ArrayList<Rectangle> winAreas  = new ArrayList<Rectangle>();
    private int currentLevel = 1;
    public MainGame(){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        try {
            // Loading font
            froggerFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/froggerFont.ttf"));
            froggerFont= froggerFont.deriveFont(20f);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            //Loading pictures
            back = ImageIO.read(new File("Pictures/froggerBackground.png"));
            winFrogPic = ImageIO.read(new File("Pictures/winFrog.png"));
            heart = ImageIO.read(new File("Pictures/heart.png"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
        //lily pad win areas
        winAreas.add(new Rectangle(20,25,70,60));
        winAreas.add(new Rectangle(180,25,70,60));
        winAreas.add(new Rectangle(345,25,70,60));
        winAreas.add(new Rectangle(505,25,70,60));
        winAreas.add(new Rectangle(665,25,70,60));

        addKeyListener(this);
        loadLanes();

    }
    public void decreaseTime(){ //timer subtract every second
        time--;
        if (time == 0){
            time = 30;
            player.death();//die when timer is 0
        }
    }

    public void loadLanes(){ //loads Lane objects into the game; The water and the road are seperated into five lanes,
        //each with Area objects (cars, logs,turtles)
        for (int i = 0; i<12 ; i ++){
            Lanes makeLanes = null;
            if (i%2 == 1) { // used to switch between cars going left and cars going right
                if (i>5 && i<11){
                    makeLanes = new Lanes(83 + 53 * i, 1, "RIGHT","road"); //add 83 to x because lanes
                    //start right after the winSpots
                }
                if (i>=0 && i<5){
                    makeLanes = new Lanes(83 + 53 * i, 1, "RIGHT","water");
                }
            }
            else{
                if (i>5 && i<11){
                    makeLanes = new Lanes(83 + 53 * i, 1, "LEFT","road");
                }
                if (i>=0 && i< 5){
                    makeLanes = new Lanes(83 + 53 * i, 1 , "LEFT","water");
                }
            }
            lanes[i] = makeLanes; //add them to a list of Lane objects
        }
    }

    public static void laneMovement(){ //calls this to move every single lanes
        for (int i = 0; i<12;i++){
            if ((i>5 && i<11) || (i>=0 && i<5) ) {
                lanes[i].moveLanes();
            }
        }
    }
    public void checkBound(){ //this constantly checks if the frog leaves the borders of the game
        if (player.getX()>=800){
            if(player.getDeathFrames() == 0) {//die when reach side borders
                player.death();
                time = 30;
            }
        }
        if (player.getX()<=0){
            if(player.getDeathFrames() == 0) {//die when reach side borders
                player.death();
                time =30;
            }
        }
        if (player.getY()>=690 && player.getFinalY()>=690){//reset position
            player.changeY(690);
            player.changeFinalY(690);
        }
        if (player.getY()<=40 && player.getFinalY() <= 40){//reset postion
            player.changeY(690);
            player.changeFinalY(690);
        }

    }
    public void collision() { //this function checks if the player collides with any Area object
        boolean isCollide = false; //checks if it is touching anything
        int collidedLane = 0;// counter

        for (int i = 0; i < 12; i++) { //checks every lane and every Area object in each lane
            if ((i > 5 && i < 11) || (i >= 0 && i < 5)) { //if it touches any Area object, sets isCollide to true
                for (Area a : lanes[i].getAreas()) {
                    if (a.getAreaRect().contains(player.getX(), player.getFinalY()) && !isCollide) {
                        isCollide = true;
                        collidedLane = i;
                    }
                }
            }
        }
        if (player.getLanePos() < 7) {//check if hit by car (<7 is the road)
            if (isCollide) { //if it hits something it must be a car therefore it dies
                time = 30;
                player.death();
            }
        }
        if (player.getLanePos() > 7) {//in water area
            if (isCollide) { //if it collides with something in the water it either is a log or a turtle
                if (lanes[collidedLane].getDirection().equals("LEFT")) {//Moving on logs
                    if (player.getqMoves() == 0) {
                        player.moveX(-(lanes[collidedLane].getSpeed()));
                    }
                }
                if (lanes[collidedLane].getDirection().equals("RIGHT")) {
                    if (player.getqMoves() == 0) {
                        player.moveX(lanes[collidedLane].getSpeed());
                    }
                }
            }
        }
        if (player.getLanePos() > 7) { //in water
            if (!isCollide) { //if it doesn't collide it must be in the water or a lily pad
                int counter = 0; //tracks which lily pad it landed on
                boolean winCondition = false;
                for (Rectangle w : winAreas) {
                    if (w.contains(player.getX(), player.getFinalY())) { //if player hits a lily pad
                        System.out.println("winner");
                        counter = winAreas.indexOf(w);
                        winCondition = true;
                    }
                }
                if (winCondition) { //calls win and reset the stats of the frog
                    score += 100;
                    time = 30;
                    totalMove = 700;
                    player.win(counter);
                } else {
                    time = 30;
                    System.out.println("die");
                    player.death();
                }
            }
        }
    }


    public void checkLevel(){ //constantly checks the level and checks if level changes
        if (player.getLevel() > currentLevel){
            speedUp();
            currentLevel ++;
        }
    }
    public void speedUp(){ //when level up we speed up each lane
        for (int i = 0; i < 12; i++) {
            if ((i > 5 && i < 11) || (i >= 0 && i < 5)) {
                lanes[i].changeSpeed(1);
            }
        }
    }
    public void addNotify() { //politely requesting focus
        super.addNotify();
        requestFocus();
        ready = true;
    }

    public void move() {//moving the frog , calls the Frog "move" functions

        if (player.getqMoves() == 0) {
            if (keys[KeyEvent.VK_RIGHT] && clickRight) {
                player.setRotation(Math.toRadians(90));//rotating
                player.moveRight();
                clickRight = false;//can't move while holding it down
            }
            if (keys[KeyEvent.VK_LEFT] && clickLeft) {
                player.setRotation(Math.toRadians(270));
                player.moveLeft();
                clickLeft = false;
            }
            if (keys[KeyEvent.VK_UP] && clickUp) {
                player.setRotation(Math.toRadians(0));
                player.moveUp();
                if (player.getY() < totalMove) {//total move to keep track of score
                    totalMove -= 53;
                    score += 10;
                }
                if (totalMove <= 40) {//reset score ares
                    totalMove = 700;
                }
                clickUp = false;
            }
            if (keys[KeyEvent.VK_DOWN] && clickDown) {
                player.setRotation(Math.toRadians(180));
                player.moveDown();
                clickDown = false;
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 756, 810);//background
        g.setColor(new Color(255, 222, 222));
        g.drawImage(back, 0, 0, this);//other background
        ////////////Rotating player
        Graphics2D g2D = (Graphics2D) g;
        AffineTransform rot = new AffineTransform();
        rot.rotate(player.getRot(), 25, 25);
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);

        for (int i = 0; i < 12; i++) {
            if ((i > 5 && i < 11) || (i >= 0 && i < 5)) {
                g.setColor(new Color(255, 222, 222));
                for (Area a : lanes[i].getAreas()) { // draws all the Area objects in all the lanes
                    if ((i > 5 && i < 11) || (i >= 0 && i < 5)) {
                        g.drawImage(a.getPicture(), a.getAx(), a.getAy(), this);
                    }
                }
            }
        }
        g.getFont();

        for(int i = player.getLives(); i>0; i--){ //draws the hearts (lives)
            g.drawImage(heart,200 + 50*i,715,this);
        }
        g2D.setFont(froggerFont);//setting arcade font
        g2D.drawString("Score: " + score, 20, 750);//blitting words
        g2D.drawString("Time: " + time, 550, 750);
        g2D.drawString("Lvl: " + currentLevel,400,750);

        int winCounter = 0;
        //////winning frog blit on the lily pad
        for (int num : player.getWinSpots()) {
            if (num == 1) {
                g.drawImage(winFrogPic, winAreas.get(winCounter).x + 10, winAreas.get(winCounter).y + 10, this);
            }
            winCounter++;
        }
        g.fillRect(550, 750, time * 5, 15);//timer
        g2D.drawImage((BufferedImage) player.getImage(), rotOp, player.getX()-25, player.getY()-25);//drawing frog
        g.drawImage(player.getEndPic(),170,380,this);//once game ends it blits win or lose

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}
    @Override
    public void keyPressed(KeyEvent k) {//move once pressed
        keys[k.getKeyCode()]=true;
        move();
    }
    @Override
    public void keyReleased(KeyEvent k) {
        //can't hold down keys
        keys[k.getKeyCode()]=false;
        if (keys[k.getKeyCode()]==keys[KeyEvent.VK_LEFT]) { clickLeft = true; }
        if (keys[k.getKeyCode()]==keys[KeyEvent.VK_RIGHT]) { clickRight = true; }
        if (keys[k.getKeyCode()]==keys[KeyEvent.VK_UP]) { clickUp = true; }
        if (keys[k.getKeyCode()]==keys[KeyEvent.VK_DOWN]) { clickDown = true; }
    }
}