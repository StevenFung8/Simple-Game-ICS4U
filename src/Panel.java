/*import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class Panel extends JFrame implements ActionListener
{
    JPanel cards;   //a panel that uses CardLayout
    CardLayout cLayout = new CardLayout();  // I could make this dynamically, then use getLayout
    //      to get the reference to it, but this is easier.

    JButton mainStart = new JButton("Next");     // My various buttons. The better way to do this is
//    JButton midNext  = new JButton("Next");     // to make my panels an array, dynamically make the
//    JButton midPrev  = new JButton("Prev");     // buttons and just use the index to pop back and forth.
////    JButton lastPrev  = new JButton("Prev");    // This seems more in tune with most students work.
    public Panel ()
    {
        super ("Cards and Stuff");

        mainStart.addActionListener(this);   // got buttons ... need listeners
//        midNext.addActionListener(this);
//        midPrev.addActionListener(this);
//        lastPrev.addActionListener(this);

        JPanel mPage = new JPanel();                // Main Page - it just a panel
        mPage.setLayout(new BoxLayout(mPage,BoxLayout.Y_AXIS));
        JTextArea intro = new JTextArea(5,10);
        intro.setLineWrap(true);
        intro.setText("This is an example of using the card layout. I have to give Mike Lin credit, because he was the one touting it's advantages.");
        intro.append(" If you don't want to use it then don't. This is very similar to the JTabbedPane. ");
        mPage.add(intro);
        mPage.add(mainStart);

        JPanel gPage = new JPanel();




		/* THIS IS WHERE THE MAGIC HAPPENS
			This panel will allow us to store the other panels, and show the one we want.
			The panels are being stored (are shown) based on a simple string
		*/
/*
        cards = new JPanel(cLayout);
        cards.add(mPage, "Start Screen Frogger");
        cards.add(GamePanel.g, "Frogger");

        add(cards);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize (400, 500);
        setVisible (true);
    }

    public void actionPerformed(ActionEvent evt) {
        Object source =evt.getSource();
        if(source==mainNext || source==lastPrev)
            cLayout.show(cards,"mid");
        else if(source==midNext)
            cLayout.show(cards,"last");
        else if(source==midPrev)
            cLayout.show(cards,"main");
    }


    public static void main(String[] args){
        JFrame.setDefaultLookAndFeelDecorated(true);
        Panel cardEg = new Panel();
    }
}
*/