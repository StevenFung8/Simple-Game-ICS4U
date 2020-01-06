import javax.swing.*;
import java.awt.*;
public class Frogger extends JFrame implements ActionListener{
    public Frogger(){

    }
    public static void main (String [] args){
        System.out.println("Send bobs and vagine");

    }

}
class GamePanel extends JPanel {
    private int boxx, boxy;

    public GamePanel() {
        boxx = 170;
        boxy = 170;
    }
}