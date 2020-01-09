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
    private int destx,desty,boxx,boxy;
    public boolean ready=false;
    private boolean gotName=false;
    private boolean []keys;
    private Image back;

    public GamePanel(){
        try {
            back = ImageIO.read(new File("froggerBackground.png"));
        }
        catch (IOException e) {
        }
        setSize(800,600);
        keys = new boolean[KeyEvent.KEY_LAST+1];
        //back = new ImageIcon("froggerBackground.png").getImage();
        addMouseListener(new clickListener());
        boxx=200;
        boxy=200;
        destx=500;
        desty=200;
        setSize(1080,700);
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
        ready = true;
    }

    public void move() {
        if(boxx<destx){
            boxx+=5;
        }
        if(boxx>destx){
            boxx-=5;
        }
        if(boxy<desty){
            boxy+=5;
        }
        if(boxy>desty){
            boxy-=5;
        }
        if(boxx==destx && !gotName){
            gotName = true;
            String name = JOptionPane.showInputDialog("Name:");
            System.out.println(name);
        }
    }

    public void paintComponent(Graphics g){
        g.setColor(new Color(222,222,255));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(new Color(255,111,111));
        g.fillOval(destx,desty,10,10);
        g.setColor(Color.red);
        g.fillRect(boxx,boxy,20,20);
        g.drawImage(back,0,0,this);


    }

    class clickListener implements MouseListener{
        // ------------ MouseListener ------------------------------------------
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseClicked(MouseEvent e){}

        public void mousePressed(MouseEvent e){
            destx = e.getX();
            desty = e.getY();
        }
    }


}