import java.awt.*;
import java.util.*;
import java.awt.Image.*;
class Area {
    private int width;
    private int height;
    private int ax;
    private int ay;
    private Rectangle areaRect;
    private Image obstaclePic;
    public Area(int x, int y, int h, int w, Image pic){
        width = w;
        height = h;
        ax = x;
        ay = y;
        obstaclePic = pic;
        areaRect = new Rectangle(ax,ay,width,height);

    }
    public int getAx(){
        return ax;
    }
    public int getAy(){
        return ay;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Image getPicture(){
        return obstaclePic;
    }
    public Rectangle getAreaRect(){ //System.out.println(areaRect);
         return areaRect;}
    public String toString(){
        return String.format("%d,%d,%d,%d",ax,ay,width,height);
    }
    public void setAx(int interval) {
        ax = interval;
    }
}
