import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Frogger extends JFrame{
    Timer myTimer;
    GamePanel game;

    public Frogger() {
        super("Frogger Dylan & Steven ltd copyright");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080,700);

        myTimer = new Timer(10, new TickListener());
        myTimer.start();

        game = new GamePanel();
        add(game);

        setResizable(false);
        setVisible(true);
    }

    class TickListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){
                game.move();
                game.repaint();
            }
        }
    }

    public static void main(String[] arguments) {
        Frogger frame = new Frogger();
    }
}

class GamePanel extends JPanel implements KeyListener {
    private int frogx,frogy;
    public boolean ready=false;
    private boolean gotName=false;
    private boolean []keys;
    private Image back;

    public GamePanel(){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        back = new ImageIcon("OuterSpace.jpg").getImage();
        frogx=200;
        frogy=200;
        addKeyListener(this);
        setSize(1080,700);
    }


    public void addNotify() {
        requestFocus();
        super.addNotify();
        ready = true;
    }

    public void move() {
        System.out.println("checking");
        if(keys[KeyEvent.VK_RIGHT]){
            System.out.println("right");
            frogx+=5;
        }
        if(keys[KeyEvent.VK_LEFT]){
            System.out.println("right");
            frogx-=5;
        }
        if(keys[KeyEvent.VK_UP]){
            System.out.println("right");
            frogy+=5;
        }
        if(keys[KeyEvent.VK_DOWN]){
            System.out.println("right");
            frogy-=5;
        }
    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(255,222,222));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.blue);
        g.fillRect(frogx,frogy,40,40);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if(g.getKeyCode())
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}