package predator_prey_sim;

import util.DotPanel;
import util.Helper;

import java.awt.*;

import static predator_prey_sim.PPSim.*;
import static util.Helper.newRandColor;

public class Predator extends Animal {

    public Predator(){}

    public Predator(int x, int y){
        this.c = Color.RED;
        this.x = x;
        this.y = y;
    }

    //abstract methods
    void move() {

        //check if the direction changes (5% rule)
        //  up = 0 , down =1 , left = 2,  right = 3
        int probablity = Helper.nextInt(100);
        int randDirection  = Helper.nextInt(4);

            // changing direction randomly
            if(probablity < 5){
                    direction = randDirection;
                }
        if (direction == 0){ moveUp();}
        if (direction == 1){moveDown();}
        if (direction == 2){moveLeft();}
        if (direction == 3){moveRight();}
           // }

        //move right- decrease x value
        //ArrayList<Pray> targatedPrays = new ArrayList<Pray>();
        //boolean prayPresent = false;
        //Pray targatedPray = new Pray();
        //moveRight();
        //moveLeft();
        //moveUp();
        //moveDown();
        //targatedPrays.add(targatedPray);
    }

    private void moveDown() {
        if(y < MAX_Y-2){
            y = y + 1;
            System.out.println(y + " Moving down...");
            //Predator movingDown = new Predator(x+0, y+2);
        }

    }
    private void moveUp() {
        if(y > 0){
            y = y - 1;
            System.out.println(y + " Moving up...");
            //Predator movingUp = new Predator(x+0, y-2);
        }

    }
    private void moveLeft() {
        if (x > 0){
            x = x - 1;
            System.out.println(x + " Moving left...");
            //Predator movingLeft = new Predator(x+2, y+0);
        }

    }
    private void moveRight() {
        if (x < MAX_X-2){
            x = x + 1;

            //dp.repaintAndSleep(3000);
            System.out.println(x + " Moving right...");
            //Predator movingRight = new Predator(x-2, y+0);
        }
        //System.out.println(x + " Moving right...false");
    }

    public void eat(Pray testPray, Predator testPred){
        //Pray targetedPray = new Pray();
        boolean eatPray = false;
        if(testPray.x == testPred.x && testPred.y == testPray.y){
            System.out.println(" Eat...*" + eatPray);
            //System.out.println("pred X:" + x + " pray X:" + targetedPray.getPrayXpos() + " Pred Y:" + y + " Pray Y:" + targetedPray.getPrayYpos());
        }
        //System.out.println(" Eat...1" + eatPray);
        //return targetedPray;
    }
    void reproduce() {

    }

    @Override
    boolean visionUp(Animal animal) {
        animal = (Pray)animal;
        int minDistance = animal.y + 10;
        if(minDistance >= y){
            return true;
        }
        return false;
    }

    @Override
    boolean visionDown(Animal animal) {
        animal = (Pray)animal;
        int minDistance = animal.y - 10;
        if(minDistance <= y){
            return true;
        }
        return false;
    }

    @Override
    boolean visionLeft(Animal animal) {
        animal = (Pray)animal;
        int minDistance = animal.x + 10;
        if(minDistance >= x){
            return true;
        }
        return false;
    }

    @Override
    boolean visionRight(Animal animal) {
        animal = (Pray)animal;
        int minDistance = animal.x - 10;
        if(minDistance <= x){
            return true;
        }
        return false;
    }


    void die() {

    }

    @Override
    void draw() { PPSim.dp.drawSquare(this.x, this.y, this.c); }

    public int getPredXpos(){ return this.x; }
    public int getPredYpos(){ return this.y; }
    public Color getPredColor() {return this.c;}

    public void setPredXpos(int x){ this.x = x; }
    public void setPredYpos(int y){ this.y = y; }


}


