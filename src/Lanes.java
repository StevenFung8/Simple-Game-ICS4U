import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.*;
class Lanes { //this class is used to generate each lane in the game. We decided to seperate the section of the game
    // into lanes to provide easy manipulation to whatever
    private int yPos ; // the y coordinate of the top of the lane
    private int speed; // the speed that each Area object travels
    private String direction; //moves left or right
    private String location; //water or road
    private Area[] areas = new Area[randint(2, 3)]; // each lane can have up to 3 Area objects
    private static Image car1, car2, car3, log1, log2, log3,turtle1;
    private static Image[] obstaclePics;


    public Lanes(int h, int s, String d, String l) {
        try { //loading all the pictures
            car1 = ImageIO.read(new File("Pictures/car1.png"));
            car2 = ImageIO.read(new File("Pictures/car2.png"));
            car3 = ImageIO.read(new File("Pictures/car3.png"));
            log1 = ImageIO.read(new File("Pictures/log1.png"));
            log2 = ImageIO.read(new File("Pictures/log2.png"));
            log3 = ImageIO.read(new File("Pictures/log3.png"));
            turtle1 = ImageIO.read(new File("Pictures/turtle1.png"));


        } catch (IOException e) {
            System.out.println(e);
        }
        obstaclePics = new Image[]{car1, car2, car3, log1, log2, log3}; //cars images in the list
        yPos = h;
        speed = s;
        direction = d;
        location = l;
        ArrayList<Integer> randomXPosArray= new ArrayList<Integer>();
        ArrayList<Integer> randomXPosArray2= new ArrayList<Integer>();



        int counter = 0;
        if (location == "road") {  // makes new Area objects in each lane
            for (int i = 0; i< areas.length ; i++) {
                counter++;
                Area newAreas = null;
                if (direction == "RIGHT") { // if objects go right have to select photo of car that goes right

                        int randomXPos = i * randint(1, 3) * 100 + obstaclePics[0].getWidth(null);

                            randomXPosArray.add(randomXPos);

                    newAreas = new Area(randomXPosArray.get(i), yPos + 2, 50, 50, obstaclePics[0]);
                } else if (direction == "LEFT") {
                    int randomXPos=i * randint(1, 3) * 100+ obstaclePics[0].getWidth(null);
                    randomXPosArray2.add(randomXPos);
                    Image newPics = obstaclePics[randint(1, 2)];
                    newAreas = new Area(randomXPosArray2.get(i), yPos + 2, 50, 50, newPics);
                }
                areas[counter - 1] = newAreas; //add the new Area object into the list
            }

        }
        if (location == "water"){
            for (int i = 0; i < areas.length ; i++) {
                counter++;
                Image newPics = obstaclePics[randint(3,5)];
                Area newAreas = null;
                if (direction == "RIGHT"){ //if the lane has a direction of right put in turtles instead of logs
                    newAreas = new Area(i * 250 + randint(50,100), yPos + 2, turtle1.getHeight(null), turtle1.getWidth(null), turtle1);
                }
                if (direction == "LEFT"){ //if the lane has a direction of left put in logs
                    newAreas = new Area(i * 250 + randint(50,100), yPos + 2, newPics.getHeight(null), newPics.getWidth(null), newPics);
                }

                areas[counter - 1] = newAreas; //adds it into the Area list
            }

        }
    }


    public int randint(int low, int high) {
        return (int) (Math.random() * (high - low + 1) + low);
    }
    // get functions to return values
    public int getYPos() {
        return yPos;
    }

    public Area[] getAreas() {
        return areas;
    }
    public String getLocation() { return location; }
    public String getDirection(){ return direction;}
    public int getSpeed(){ return speed;}
    public void changeSpeed(int value){
        speed += value;
    }
public void moveLanes() { //moves the Area objects in the lane
    for (Area a : areas) {
        if (direction == "LEFT") { //if it goes left subtract the x value
            a.setAx(a.getAx() - speed);
            a.getAreaRect().x -= speed;
            if (a.getAx() <= -(a.getPicture().getWidth(null))) { //if the object goes out of bounds reset back to the far right off the screen
                a.setAx(800 + a.getPicture().getWidth(null) + 50 );
                a.getAreaRect().x = 800 + a.getPicture().getWidth(null) + 50;
            }
        }
        if (direction == "RIGHT") { //sets x to add to move right
            a.setAx(a.getAx() + speed);
            a.getAreaRect().x += speed;
            if (a.getAx() >= 850 + a.getPicture().getWidth(null)) { //resets it to the far left if it goes off the screen to the right
                a.setAx(-(a.getPicture().getWidth(null) + 50));
                a.getAreaRect().x = -(a.getPicture().getWidth(null)+50 );
            }
        }
    }
}
}
