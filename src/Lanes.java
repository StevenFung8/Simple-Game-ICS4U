import java.util.*;
class Lanes {
    private int yPos ;
    private int speed;
    private String direction;
    private Area[] areas = new Area[randint(1,3)];
    public Lanes(int h, int s, String d){
        yPos = h;
        speed = s;
        direction = d;
        int counter = 0;
        for (Area a : areas){
            counter ++;
            Area newAreas = new Area(randint(10,780),yPos,50,50);
            areas[counter-1] = newAreas;
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
    public void moveLanes(){
        for (Area a : areas){
            if (direction == "LEFT"){
                a.setAx(a.getAx() - speed);
                if (a.getAx() <=-130){
                    a.setAx(900);
                }
            }
            else{
                a.setAx(a.getAx() + speed);
                if (a.getAx() >=900){
                    a.setAx(-130);
                }
            }
        }
    }
}
