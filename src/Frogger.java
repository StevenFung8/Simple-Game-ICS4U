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
        setSize(810,785);

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
                game.repaint();
            }
        }
    }

    public static void main(String[] arguments) {
        Frogger frame = new Frogger();

    }
}
class Obstacle{
    private int sizex,sizey;
    private Image pic;

}
class GamePanel extends JPanel implements KeyListener {
    private int frogx,frogy;
    public boolean ready=false;
    private boolean gotName=false;
    private boolean []keys;
    private boolean [] keysPressed;
    private Image back,frogPic;

    public GamePanel(){
        try {
            back = ImageIO.read(new File("Pictures/froggerBackground.png"));
            frogPic = ImageIO.read(new File("Pictures/frogger.png"));
        }
        catch (IOException e) {
        }
        keys = new boolean[KeyEvent.KEY_LAST+1];
        setSize(800,751);
    }


    public void addNotify() {

        super.addNotify();
        requestFocus();
        ready = true;
    }


    @Override
    public void paintComponent(Graphics g){
        g.drawImage(back,0,0,this);
        g.setColor(Color.blue);
        g.fillRect(frogx,frogy,40,40);
        g.drawImage(frogPic,frogx,frogy,this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}


    @Override
    public void keyPressed(KeyEvent k) {
        System.out.println("skfkabdk");
        if(k.getKeyCode()==KeyEvent.VK_RIGHT && !keysPressed[KeyEvent.VK_RIGHT]){
            System.out.println("Right");
            frogx+=40;
        }

        if(k.getKeyCode()==KeyEvent.VK_LEFT && !keysPressed[KeyEvent.VK_LEFT]){
            System.out.println("Left");
            frogx-=40;
        }
        if(k.getKeyCode()==KeyEvent.VK_UP && !keysPressed[KeyEvent.VK_UP]){
            System.out.println("Up");
            frogy-=40;
        }
        if(k.getKeyCode()==KeyEvent.VK_DOWN && !keysPressed[KeyEvent.VK_DOWN]){
            System.out.println("Down");
            frogy+=40;
        }
        keysPressed[k.getKeyCode()]=true;

    }

    @Override
    public void keyReleased(KeyEvent k) {
        keysPressed[k.getKeyCode()]=false;
    }
}