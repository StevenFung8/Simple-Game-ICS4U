import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

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
    private int destx,desty,boxx,boxy;
    public boolean ready=false;
    private boolean gotName=false;
    private boolean []keys;
    private Image back;

    public GamePanel(){
        try {
            back = ImageIO.read(new File("Pictures/froggerBackground.png"));
        }
        catch (IOException e) {
        }
        keys = new boolean[KeyEvent.KEY_LAST+1];
        back = new ImageIcon("OuterSpace.jpg").getImage();
        frogx=200;
        frogy=200;
        addKeyListener(this);
        setSize(800,751);
    }
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }


    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }


    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(222,222,255));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.blue);
        g.fillRect(frogx,frogy,40,40);
    }

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {



    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}