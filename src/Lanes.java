import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.awt.*;
class Lanes {
    private int yPos ;
    private int speed;
    private String direction;
    private String location;
    private Area[] areas = new Area[randint(2,4)];
    private static Image car1,car2,car3,log1,log2,log3;
    private static Image[] obstaclePics ;
    public Lanes(int h, int s, String d,String l){
        try {
            car1 = ImageIO.read(new File("Pictures/car1.png"));
            car2 = ImageIO.read(new File("Pictures/car2.png"));
            car3 = ImageIO.read(new File("Pictures/car3.png"));
            log1 = ImageIO.read(new File("Pictures/log1.png"));
            log2 = ImageIO.read(new File("Pictures/log2.png"));
            log3 = ImageIO.read(new File("Pictures/log3.png"));
        }
        catch (IOException e) {
            System.out.println(e);
        }
        obstaclePics = new Image[]{car1, car2, car3, log1, log2, log3};
        yPos = h;
        speed = s;
        direction = d;
        location = l;
        int counter = 0;
        if (location == "road") {
            for (int i = 0; i< areas.length ; i++) {
                counter++;
                Area newAreas = null;

                if (direction == "RIGHT") { // i * randint(1,3) * 100 + obstaclePics[0].getWidth(null)
                    newAreas = new Area(i * randint(1,3) * 100 + obstaclePics[0].getWidth(null), yPos + 8, 50, 50, obstaclePics[0]);
                }
                else if (direction == "LEFT"){
                    Image newPics = obstaclePics[randint(1,2)];
                    newAreas = new Area(i * randint(1,3) * 100 + newPics.getWidth(null)  , yPos + 8, 50, 50, newPics);
                }
                areas[counter - 1] = newAreas;
            }
        }
        if (location == "water"){
            for (int i = 0; i < areas.length ; i++) {
                counter++;
                Image newPics = obstaclePics[randint(3,5)];
                Area newAreas = new Area(i * randint(1,3) * 100 + newPics.getWidth(null)  , yPos +10 ,  newPics.getHeight(null), newPics.getWidth(null), newPics);
                areas[counter - 1] = newAreas;
            }

        }
    }

    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
    public int getYPos(){
        return yPos;
    }
    public Area[] getAreas(){
        return areas;
    }
    public String getLocation() { return location; }
    public String getDirection(){ return direction;}
    public int getSpeed(){ return speed;}

public void moveLanes() {
    for (Area a : areas) {
        if (direction == "LEFT") {
            a.setAx(a.getAx() - speed);
            a.getAreaRect().x -=1;
            if (a.getAx() <= -(a.getPicture().getWidth(null) + 50)) {
                a.setAx(850 + a.getPicture().getWidth(null));
                a.getAreaRect().x = 850 + a.getPicture().getWidth(null);
            }
        }
        if (direction == "RIGHT") {
            a.setAx(a.getAx() + speed);
            a.getAreaRect().x +=1;
            if (a.getAx() >= 850 + a.getPicture().getWidth(null)) {
                a.setAx(-(a.getPicture().getWidth(null) + 50));
                a.getAreaRect().x = -(50 + a.getPicture().getWidth(null));
            }
        }
    }
}
}
