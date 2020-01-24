import javax.swing.*;
import java.awt.event.KeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class Panel extends JFrame{
    private JLayeredPane layeredPane=new JLayeredPane();

    public Panel() {
        super("Frogger");
        setSize(756,810);

        ImageIcon backPic = new ImageIcon("Pictures//startScreen.png");
        JLabel back = new JLabel(backPic);
        back.setBounds(0, 0,backPic.getIconWidth(),backPic.getIconHeight());
        layeredPane.add(back,1);

        ImageIcon startPic = new ImageIcon("Pictures//start.png");
        JButton startBtn = new JButton(startPic);
        startBtn.setBackground(Color.BLACK);
        startBtn.setBorder(new LineBorder(Color.GREEN));
        startBtn.addActionListener(new ClickStart());
        startBtn.setBounds((756/2)-startPic.getIconHeight()-55,600,startPic.getIconWidth(),startPic.getIconHeight());
        layeredPane.add(startBtn,2);

        setContentPane(layeredPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        Panel frame = new Panel();
    }

    class ClickStart implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            Frogger game = new Frogger();
            setVisible(false);
        }
    }
}
