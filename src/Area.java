import java.awt.*;
import java.util.*;
import java.awt.Image.*;
class Area { //Area objects are the obstacles in the lanes such as cars, turtles and logs
    private int width; //width of the obstacle
    private int height; //height of the obstacle
    private int ax; //x is the obstacle
    private int ay; //y is the obstacle
    private Rectangle areaRect;
    private Image obstaclePic;
    public Area(int x, int y, int h, int w, Image pic){
        width = w;
        height = h;
        ax = x;
        ay = y;
        obstaclePic = pic;
        areaRect = new Rectangle(ax,ay,width,height);//used for collision

    }
    // all get methods to return a value
    public int getAx(){ return ax; }
    public int getAy(){ return ay; }
    public Image getPicture(){ return obstaclePic; }
    public Rectangle getAreaRect(){ return areaRect;}
    public void setAx(int interval) { //sets the x value to some value
        ax = interval;
    }
}

