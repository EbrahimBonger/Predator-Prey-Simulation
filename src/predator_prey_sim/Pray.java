package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.awt.event.HierarchyListener;

import static predator_prey_sim.PPSim.*;
import static util.Helper.newRandColor;

public class Pray extends Animal {

    String name = "R";
    boolean camouflage = false;
    int reproduceInterval = 100;
    //boolean alive = true;
    //Color c = newRandColor();

    public Pray(){}

    public Pray( int x, int y, Color c){
        super(x, y, c);
        this.x = x;
        this.y = y;
        this.alive = true;
        //this.c = c;
        //this.camouflage = camouflage;

    }
    //modify move method
    public void run(){}

    //abstract methods
    void move() {
        int probablity = Helper.nextInt(100);
        int randDirection  = Helper.nextInt(4);
        //World w = new World();
        // changing direction randomly
        if(probablity < 10){
            direction = randDirection;
        }
        reproduceInterval--;
        //if (w.checkCoordination(x, y) == true){
            if (direction == 0){ moveUp();}
            if (direction == 1){moveDown();}
            if (direction == 2){moveLeft();}
            if (direction == 3){moveRight();}
        //}


    }

    private void moveDown() {
        if (alive == true) {
            if (y < MAX_Y - 2) {
                y = y + 1;
                //System.out.println(y + " Moving down...");
                //Predator movingDown = new Predator(x+0, y+2);
            }
        }
    }
    private void moveUp() {
        if (alive == true){
            if (y > 0) {
            y = y - 1;
            //System.out.println(y + " Moving up...");
            //Predator movingUp = new Predator(x+0, y-2);
        }
        }
    }
    private void moveLeft() {
        if (alive == true){
            if (x > 0) {
            x = x - 1;
            //System.out.println(x + " Moving left...");
            //Predator movingLeft = new Predator(x+2, y+0);
        }
        }
    }
    private void moveRight() {
        if (alive == true) {
            if (x < MAX_X - 2) {
                x = x + 1;

                //dp.repaintAndSleep(3000);
                //System.out.println(x + " Moving right...");
                //Predator movingRight = new Predator(x-2, y+0);
            }
        }//System.out.println(x + " Moving right...false");
    }


    public boolean reproduce() {

        int probablity = Helper.nextInt(100);
        if(probablity < 2 && reproduceInterval < 0 && offSpring == true){
            reproduceInterval = 1000;
            offSpring = false;
            return true;
        }
        return false;
    }

    @Override
    int visionUp(int enemyY) {
        int currentPosition = y;
        while (y > 0){
            currentPosition--;
            if(enemyY == currentPosition){
                return -1;
            }
        }
        return -1;
    }

    @Override
    int visionDown(int enemyY) {
        int currentPosition = y;
        while (y < MAX_Y-2){
            currentPosition++;
            if(enemyY == currentPosition){
                return -1;
            }
        }
        return -1;
    }

    @Override
    int visionLeft(int enemyX) {
        int currentPosition = x;
        while (x > 0){
            currentPosition--;
            if(enemyX == currentPosition){
                return -1;
            }
        }
        return -1;
    }

    @Override
    int visionRight(int enemyX) {
        int currentPosition = x;
        while (x  < MAX_X-2) {
            currentPosition--;
            if (enemyX == currentPosition) {
                currentPosition--;
                return -1;
            }
        }
        return -1;
    }

    void die() {}

    //public void draw() { PPSim.dp.drawCircle(this.x, this.y, ; }
    void draw() {

        if(alive != false){
            PPSim.dp.drawCircle(this.x, this.y, this.c);
            //System.out.println(alive);
            //return;
        }
        //PPSim.dp.drawCircle(this.x,this.y, this.c);
        //System.out.println(alive);

    }

    @Override
    String getName() {
        return this.name;
    }

    //
    // @Override
    //public String getName() {return this.name;}


    public Pray eat() {
        return null;
    }


    public Color getPrayColor() {return c;}
    public boolean getCamouflage() {return camouflage;}
    public void setCamouflage(boolean camouflage) { this.camouflage = camouflage;}
    public boolean getAlive(){return this.alive;}

    //check if the pray matches the world color
    public boolean visibility(Color canvasColor){
        if(canvasColor == c){
            //System.out.println("canvasColor matched.......");
             camouflage = true;
        }
        //System.out.println("canvasColor NOT...matched.......");
        return camouflage;
    }
    // check pray alive of not
    // any pray that has false return considered daed
  public void setAlive(boolean alive){this.alive = alive;}
  public boolean isAlive(){return this.alive;}
    public void setColor(Color color){ this.c = color; }
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }
    public int getXpos(){ return x; }
    public int getYpos(){return y; }
}
