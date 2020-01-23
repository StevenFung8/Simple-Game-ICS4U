import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

public class Frogger extends JFrame{
    Timer myTimer;
    GamePanel game;

    public Frogger() {
        super("Frogger Dylan & Steven ltd copyright");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(810,795);

        myTimer = new Timer(10, new TickListener());
        myTimer.start();

        game = new GamePanel();
        add(game);


        setResizable(false);
        setVisible(true);
    }

    class TickListener implements ActionListener{ //CALL FUNCTIONS HERE (JUST LIKE PYGAME "WHILE RUNNING LOOP")
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){
                game.laneMovement();
                game.move();
                game.collision();
                game.repaint();
                game.setClick();
            }
        }
    }

    public static void main(String[] arguments) {
        Frogger frame = new Frogger();

    }
}

class GamePanel extends JPanel implements KeyListener {
    Frog player = new Frog();
    public boolean ready=false;
    private boolean gotName=false;
    private boolean []keys;
    private boolean [] keysPressed ;
    private static Image back,frogPic,winFrogPic,heart;
    private static Lanes lanes[] = new Lanes [12];
    private static Rectangle winAreas[] = {new Rectangle(25,25,70,70),new Rectangle(195,25,70,70),new Rectangle(370,25,70,70),new Rectangle(540,25,70,70),new Rectangle(713,25,70,70)};
    private boolean click;

    public GamePanel(){

        try {
            back = ImageIO.read(new File("Pictures/froggerBackground.png"));
            frogPic = ImageIO.read(new File("Pictures/frogger.png"));
            winFrogPic = ImageIO.read(new File("Pictures/winFrog.png"));
            heart = ImageIO.read(new File("Pictures/heart.png"));
        }
        catch (IOException e) {
            System.out.println(e);
        }

        keys = new boolean[KeyEvent.KEY_LAST+1];
        addKeyListener(this);
        loadLanes();


    }
    public void setClick(){
        click=false;
    }

    public void move() {
        if (click) {
            if (keys[KeyEvent.VK_RIGHT]) {
                player.moveRight();
            }

            if (keys[KeyEvent.VK_LEFT]) {
                player.moveLeft();
            }
            if (keys[KeyEvent.VK_UP]) {
                player.moveUp();
            }
            if (keys[KeyEvent.VK_DOWN]) {
                player.moveDown();
            }
        }
        player.checkBound();
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
    public static void loadLanes(){
        for (int i = 0; i<12 ; i ++){
            Lanes makeLanes = null;
            if (i%2 == 1) {
                if (i>5 && i<11){
                    makeLanes = new Lanes(90 + 55 * i, 1, "RIGHT","road");
                }
                if (i>=0 && i<5){
                    makeLanes = new Lanes(90 + 55 * i, 1, "RIGHT","water");
                }
            }
            else{
                if (i>5 && i<11){
                    makeLanes = new Lanes(90 + 55 * i, 1, "LEFT","road");
                }
                if (i>=0 && i< 5){
                    makeLanes = new Lanes(90 + 55 * i, 1, "LEFT","water");
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
    public boolean collisionCheck(Frog player, Area a){
        return player.getX() < a.getAx() + a.getWidth() - 5 &&
                player.getX() + a.getWidth() > a.getAx() &&
                player.getY() < a.getAy() + a.getHeight() &&
                player.getY() + a.getHeight() > a.getAy();
    }
    public void collision() {
        boolean isCollide = false;
        int collidedLane = 0;
        for (int i = 0; i < 12; i++) {
            if ((i > 5 && i < 11) || (i >= 0 && i < 5)) {
                for (Area a : lanes[i].getAreas()) {
                    //if (collisionCheck(player, a) && !isCollide) {
                    if (a.getAreaRect().contains(player.getX(),player.getY()) && !isCollide){
                        isCollide = true;
                        collidedLane = i;
                        //System.out.println(a.getAx());
                        //System.out.println("collision");
                    }
                }
            }
        }
        if (player.getLanePos() < 7) {
            if (isCollide) {
                player.death();
            }
        }
        if (player.getLanePos() > 7) {
            if (isCollide) {
                int counter = 0;
                for (Rectangle w : winAreas){
                    if (w.contains(player.getX(),player.getY())){
                        System.out.println("winner");
                        player.win(counter);
                    }
                    counter ++;
                }
                if (lanes[collidedLane].getDirection().equals("LEFT")) {
                    player.moveX(-1/*(lanes[i].getSpeed()*/);
                }
                if (lanes[collidedLane].getDirection().equals("RIGHT")) {
                    player.moveX(1/*lanes[i].getSpeed()*/);
                }
            }
        }
        //System.out.println(player.getLanePos());
        if (player.getLanePos() > 7) {
            if (!isCollide) {
                int counter = 0;
                for (Rectangle w : winAreas){
                    if (w.contains(player.getX(),player.getY())){
                        System.out.println("winner");
                        player.win(counter);
                    }
                    counter ++;
                }
                player.death();
            }
        }
    }

    @Override
    public void paint(Graphics g){
        g.setColor(new Color(255,222,222));
        g.drawImage(back,0,0,this);
        for (int i = 0; i<12;i++){
            if ((i>5 && i<11) || (i>=0 && i<5) ) {
                g.setColor(new Color(255, 222, 222));
                //g.drawRect(0,lanes[i].getYPos(),800,751);
                for (Area a : lanes[i].getAreas()) { //start at 6
                    //System.out.println(a);
                    if ((i >5 && i < 11) || (i >= 0 && i < 5)) {
                        //g.drawRect(a.getAx(),a.getAy(),a.getWidth(),a.getHeight());
                        g.drawRect((int)a.getAreaRect().getX(),(int)a.getAreaRect().getY(),(int)a.getAreaRect().getWidth(),(int)a.getAreaRect().getHeight());
                        g.drawImage(a.getPicture(), a.getAx(), a.getAy(), this);
                    }
                }
            }
        }
        /*
        for (Rectangle w : winAreas){
            g.setColor( new Color(15, 10, 255));
            g.drawRect(w.x,w.y,w.width,w.height);
        }
*/
        int winCounter = 0;
        for (int num : player.getWinSpots()){
            if (num == 1){
                g.drawImage (winFrogPic,winAreas[winCounter].x,winAreas[winCounter].y,this);
            }
            winCounter ++;
        }

        g.drawImage(frogPic,player.getX()-25,player.getY()-25,this);
        g.drawRect(player.getX(),player.getY(),2,2);

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}


    @Override
    public void keyPressed(KeyEvent k) {
        keys[k.getKeyCode()]=true;
        click=true;
    }

    @Override
    public void keyReleased(KeyEvent k) {
        keys[k.getKeyCode()]=false;
        click=false;
    }
}