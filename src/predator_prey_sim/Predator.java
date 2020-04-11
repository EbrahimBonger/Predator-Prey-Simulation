package predator_prey_sim;

import util.Helper;

import java.awt.*;

import static predator_prey_sim.PPSim.*;

public class Predator extends Animal {
    String name = "P";
    int energy = 100000000;
    Color c = Color.RED;
    int reproduceInterval = 10;
    //boolean offSpring = true;
    public Predator(){}
    public Predator(int x, int y, Color c, int energy){
        super(x, y, c);
        this.c = c;
        this.x = x;
        this.y = y;
        this.energy = energy;


    }

    //abstract methods
    void move() {

        //check if the direction changes (5% rule)
        //  up = 0 , down =1 , left = 2,  right = 3
        int probablity = Helper.nextInt(100);
        int randDirection  = Helper.nextInt(4);
        //energy--;
        //System.out.println(energy);
        reproduceInterval--;
        energy--;
        //System.out.println(energy);
            // changing direction randomly
        if(probablity < 5){
                direction = randDirection;
        }
        if (direction == 0){ moveUp();}
        if (direction == 1){moveDown();}
        if (direction == 2){moveLeft();}
        if (direction == 3){moveRight();}
    }


    void moveDown() {
        if(y < MAX_Y-2){
            y = y + 1;
            //System.out.println(y + " Moving down...");
            //Predator movingDown = new Predator(x+0, y+2);
        }

    }
    void moveUp() {
        if(y > 0){
            y = y - 1;
            //System.out.println(y + " Moving up...");
            //Predator movingUp = new Predator(x+0, y-2);
        }

    }
    void moveLeft() {
        if (x > 0){
            x = x - 1;
            //System.out.println(x + " Moving left...");
            //Predator movingLeft = new Predator(x+2, y+0);
        }

    }
    void moveRight() {
        if (x < MAX_X-2){
            x = x + 1;

            //dp.repaintAndSleep(3000);
            //System.out.println(x + " Moving right...");
        }
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
    int visionUp(int maxDistance) {
        int currentPosition = y;
        boolean visibility = false;
        while (maxDistance != 0){
            maxDistance--;
            currentPosition--;
            visibility = true;
            return currentPosition;
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

    private void chase(int prayX, int prayY){
        //System.out.println("Moving toward Pray..");
        // chase to the Right
        int visibility = 15;
        if (prayY == y && prayX - x < visibility) {
            moveRight();
           // System.out.println("Moving toward Pray..R");
        }
        // chase to the Left
        if (prayY == y && x - prayX < visibility){
            moveLeft();
           // System.out.println("Moving toward Pray..L");
        }
        // chase to the UP
        if (prayX == x && y - prayY < visibility){
            moveUp();
           // System.out.println("Moving toward Pray..U");
        }
        // chase to the Down
        if (prayX == x && prayY - y < visibility){
            moveUp();
           // System.out.println("Moving toward Pray..D");
        }
    }

    //private void freeze(int pray){ }


    @Override
    void draw() {
        PPSim.dp.drawSquare(this.x, this.y, this.c);
    }

    @Override
    String getName() {
        return this.name;
    }

    public int getXpos(){ return this.x; }
    public int getYpos(){ return this.y; }
    public int getEnergy(){ return this.energy; }
    public boolean isAlive(){ return alive; }
    public Color getPredColor() {return this.c;}
    public void setX(int x){ this.x = x; }
    public void setIsAlive(boolean alive){ this.alive = false;}
    public void setY(int y){ this.y = y; }
    public void setEnergy(int energy){this.energy = energy;}
    public void setColor(Color c){this.c = c;}

}


