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
    private int width=756;
    private int height=810;
    private int timePassed=0;
    private int frames=53;

    public Frogger() {
        super("Frogger by Dylan and Steven");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width,height);

        myTimer = new Timer(10, new TickListener());
        myTimer.start();

        game = new MainGame();
        add(game);

        setResizable(false);
        setVisible(true);
    }
    public MainGame getGamePanel(){
        return game;
    }
    class TickListener implements ActionListener{ //CALL FUNCTIONS HERE (JUST LIKE PYGAME "WHILE RUNNING LOOP")
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){
                game.laneMovement();
                game.move();
                game.player.minuesqMoves();
                game.player.minusDeath();
                game.collision();
                game.repaint();
                timePassed+=10;
                if (timePassed==1000){
                    game.decreaseTime();
                    timePassed=0;
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
    Frog player = new Frog();
    public boolean ready=false;
    private boolean clickUp,clickDown,clickLeft,clickRight = true;
    private boolean gotName=false;
    private boolean []keys;
    private Font froggerFont;
    private int score=0;
    private int totalMove=700;
    private int time=30;
    private int frames=53;
    private int lives=3;
    int currentLevel = 1;
    private static Image back,winFrogPic,heart;
    private static Lanes lanes[] = new Lanes [12];
    private ArrayList<Rectangle> winAreas  = new ArrayList<Rectangle>();

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
            back = ImageIO.read(new File("Pictures/froggerBackground.png"));
            winFrogPic = ImageIO.read(new File("Pictures/winFrog.png"));
            heart = ImageIO.read(new File("Pictures/heart.png"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
        winAreas.add(new Rectangle(20,25,70,60));
        winAreas.add(new Rectangle(180,25,70,60));
        winAreas.add(new Rectangle(345,25,70,60));
        winAreas.add(new Rectangle(505,25,70,60));
        winAreas.add(new Rectangle(665,25,70,60));

        addKeyListener(this);
        loadLanes();

    }
    public void decreaseTime(){
        time--;
        if (time == 0){
            time = 30;
            player.death();
        }
    }

    public void loadLanes(){
        for (int i = 0; i<12 ; i ++){
            Lanes makeLanes = null;
            if (i%2 == 1) {
                if (i>5 && i<11){
                    makeLanes = new Lanes(83 + 53 * i, 1, "RIGHT","road");
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
            lanes[i] = makeLanes;
        }
    }

    public static void laneMovement(){
        for (int i = 0; i<12;i++){
            if ((i>5 && i<11) || (i>=0 && i<5) ) {
                lanes[i].moveLanes();
            }
        }
    }
    public void checkBound(){
        if (player.getX()>=800){
            if(player.getDeathFrames() == 0) {
                player.death();
                time = 30;
            }
        }
        if (player.getX()<=0){
            if(player.getDeathFrames() == 0) {
                player.death();
                time =30;
            }
        }
        if (player.getY()>=690 && player.getFinalY()>=690){
            player.changeY(690);
            player.changeFinalY(690);
        }
        if (player.getY()<=40 && player.getFinalY() <= 40){
            player.changeY(690);
            player.changeFinalY(690);
        }

    }
    public void collision() {
        boolean isCollide = false;
        int collidedLane = 0;
        for (int i = 0; i < 12; i++) {
            if ((i > 5 && i < 11) || (i >= 0 && i < 5)) {
                for (Area a : lanes[i].getAreas()) {
                    //if (collisionCheck(player, a) && !isCollide) {
                    if (a.getAreaRect().contains(player.getX(), player.getFinalY()) && !isCollide) {
                        isCollide = true;
                        collidedLane = i;
                    }
                }
            }
        }
        if (player.getLanePos() < 7) {
            if (isCollide) {
                time = 30;
                player.death();
            }
        }
        if (player.getLanePos() > 7) {
            if (isCollide) {
                int counter = 0;
                for (Rectangle w : winAreas) {
                    if (w.contains(player.getX(), player.getFinalY())) {
                        System.out.println("winner");
                        time = 30;
                        player.win(counter);
                    }
                    counter++;
                }
                if (lanes[collidedLane].getDirection().equals("LEFT")) {
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
        if (player.getLanePos() > 7) {
            if (!isCollide) {
                int counter = 0;
                boolean winCondition = false;
                for (Rectangle w : winAreas) {
                    if (w.contains(player.getX(), player.getFinalY())) {
                        System.out.println("winner");
                        counter = winAreas.indexOf(w);
                        winCondition = true;
                        /*
                        score+=100;
                        time = 30;
                        player.win(counter);
                                              */
                    }
                }
                /*
                time = 30;
                player.death();
                 */
                if (winCondition){
                    score +=100;
                    time = 30;
                    totalMove = 700;
                    player.win(counter);
                }
                else{
                    time = 30;
                    player.death();
                }
            }
        }
    }
    public void checkLevel(){
        if (player.getLevel() > currentLevel){
            speedUp();
            currentLevel ++;
        }
    }
    public void speedUp(){
        for (int i = 0; i < 12; i++) {
            if ((i > 5 && i < 11) || (i >= 0 && i < 5)) {
                lanes[i].changeSpeed(1);
            }
        }
    }
    public void resetGame(){

    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }

    public void move() {
        if (player.getqMoves() == 0) {
            if (keys[KeyEvent.VK_RIGHT] && clickRight) {
                player.setRotation(Math.toRadians(90));
                player.moveRight();

                clickRight = false;


            }
            if (keys[KeyEvent.VK_LEFT] && clickLeft) {
                player.setRotation(Math.toRadians(270));
                player.moveLeft();
                clickLeft = false;


            }
            if (keys[KeyEvent.VK_UP] && clickUp) {
                player.setRotation(Math.toRadians(0));
                player.moveUp();
                if (player.getY() < totalMove) {
                    totalMove -= 53;
                    score += 10;
                }
                if (totalMove <= 40) {
                    totalMove = 700;
                }
                clickUp = false;


            }
            if (keys[KeyEvent.VK_DOWN] && clickDown) {
                player.setRotation(Math.toRadians(180));
                player.moveDown();
                clickDown = false;


            }

            //player.checkBound();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 756, 810);
        g.setColor(new Color(255, 222, 222));
        g.drawImage(back, 0, 0, this);
        Graphics2D g2D = (Graphics2D) g;
        AffineTransform rot = new AffineTransform();
        rot.rotate(player.getRot(), 25, 25);
        AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);

        for (int i = 0; i < 12; i++) {
            if ((i > 5 && i < 11) || (i >= 0 && i < 5)) {
                g.setColor(new Color(255, 222, 222));
                //g.drawRect(0,lanes[i].getYPos(),800,751);
                for (Area a : lanes[i].getAreas()) { //start at 6
                    //System.out.println(a);
                    if ((i > 5 && i < 11) || (i >= 0 && i < 5)) {
                        //g.drawRect(a.getAx(),a.getAy(),a.getWidth(),a.getHeight());
                        //g.drawRect((int) a.getAreaRect().getX(), (int) a.getAreaRect().getY(), (int) a.getAreaRect().getWidth(), (int) a.getAreaRect().getHeight());
                        g.drawImage(a.getPicture(), a.getAx(), a.getAy(), this);
                    }
                }
            }
        }
        g.getFont();

        for(int i = player.getLives(); i>0; i--){
            g.drawImage(heart,200 + 50*i,715,this);
        }
        g2D.setFont(froggerFont);
        g2D.drawString("Score: " + score, 20, 750);
        g2D.drawString("Time: " + time, 500, 750);


        /*
        for (Rectangle w : winAreas){
            g.setColor( new Color(15, 10, 255));
            g.drawRect(w.x,w.y,w.width,w.height);
        }
        */


        int winCounter = 0;
        for (int num : player.getWinSpots()) {
            if (num == 1) {
                g.drawImage(winFrogPic, winAreas.get(winCounter).x + 10, winAreas.get(winCounter).y + 10, this);
            }
            winCounter++;
        }
        g.fillRect(500, 750, time * 5, 15);
        g2D.drawImage((BufferedImage) player.getImage(), rotOp, player.getX()-25, player.getY()-25);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}



    @Override
    public void keyPressed(KeyEvent k) {
        keys[k.getKeyCode()]=true;
        move();
    }

    @Override
    public void keyReleased(KeyEvent k) {
        keys[k.getKeyCode()]=false;
        if (keys[k.getKeyCode()]==keys[KeyEvent.VK_LEFT]) { clickLeft = true; }
        if (keys[k.getKeyCode()]==keys[KeyEvent.VK_RIGHT]) { clickRight = true; }
        if (keys[k.getKeyCode()]==keys[KeyEvent.VK_UP]) { clickUp = true; }
        if (keys[k.getKeyCode()]==keys[KeyEvent.VK_DOWN]) { clickDown = true; }
    }
}