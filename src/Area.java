import java.util.*;
import java.awt.image.*;
class Area {
    private int width;
    private int height;
    private int ax;
    private int ay;
    public Area(int x, int y, int h, int w ){
        width = w;
        height = h;
        ax = x;
        ay = y;

    }
    public int getAx(){
        return ax;
    }
    public int getAy(){
        return ay;
    }
    public int width(){
        return width;
    }
    public int height(){
        return height();
    }
    public String toString(){
        return String.format("%d,%d,%d,%d",ax,ay,width,height);
    }
    public void setAx(int interval) {
        ax = interval;
    }
}
