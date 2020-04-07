package predator_prey_sim;

import java.awt.*;

import static predator_prey_sim.PPSim.dp;
import static util.Helper.newRandColor;

public class Pray extends Animal {


    public Pray(){}
    public Pray(int x, int y){

        this.c = newRandColor();
        this.x = x;
        this.y = y;
        //PPSim.dp.drawCircle(x, y, c);
    }
    //modify move method
    public void run(){}

    //abstract methods
    void move() {
        Predator animal = new Predator();
        //Left border case
        if(visionDown(animal) == true || visionUp(animal) == true && x < 5){
            moveRight();
            moveRight();
        }
        //Right border case
        else if(visionDown(animal) == true || visionUp(animal) == true && x >= 95){
            moveLeft();
            moveLeft();
        }
        // upper border case
        else if(visionLeft(animal) == true || visionRight(animal) == true && y < 5){
            moveDown();
            moveDown();
        }
        // lower border case
        else if(visionLeft(animal) == true || visionRight(animal) == true && y > 95){
            moveUp();
            moveUp();
        }
        // middle cases
        if(visionDown(animal) == true || visionUp(animal) == true){
            moveRight();
            moveRight();
        }
        //Right border case
        else if(visionDown(animal) == true || visionUp(animal) == true){
            moveLeft();
            moveLeft();
        }
        // upper border case
        else if(visionLeft(animal) == true || visionRight(animal) == true){
            moveDown();
            moveDown();
        }
        // lower border case
        else if(visionLeft(animal) == true || visionRight(animal) == true){
            moveUp();
            moveUp();
        }
    }

    private void moveDown() {
        if(y < 98 && y > 0){
            y = y + 2;
            System.out.println(y + " Moving down...");
            //Predator movingDown = new Predator(x+0, y+2);
        }

    }
    private void moveUp() {
        if(y < 100 && y > 0){
            y = y - 2;
            System.out.println(y + " Moving up...");
            //Predator movingUp = new Predator(x+0, y-2);
        }

    }
    private void moveLeft() {
        if (x < 100 && x > 0){
            x = x - 2;
            System.out.println(x + " Moving left...");
            //Predator movingLeft = new Predator(x+2, y+0);
        }

    }
    private void moveRight() {
        if (x > 0 && x < 98){
            x = x + 2;
            System.out.println(x + " Moving right...");
            //Predator movingRight = new Predator(x-2, y+0);
        }
    }


    void reproduce() {

    }

    @Override
    boolean visionUp(Animal animal) {
        //dp.repaintAndSleep(3000);
        animal = (Predator)animal;
        int minDistance = animal.y + 10;
        if(minDistance >= y && animal.x == x){
            //System.out.println(y + " " + minDistance);
            moveDown();
            moveDown();
            //dp.repaintAndSleep(3000);
            return true;
        }
        return false;
    }

    @Override
    boolean visionDown(Animal animal) {
        animal = (Predator)animal;
        int minDistance = animal.y - 10;
        if(minDistance <= y && animal.x == x){
            moveUp();
            moveUp();
            return true;
        }
        return false;
    }

    @Override
    boolean visionLeft(Animal animal) {
        animal = (Predator)animal;
        int minDistance = animal.x + 10;
        if(minDistance >= x && animal.y == y){
            moveRight();
            moveRight();
            return true;
        }
        return false;
    }

    @Override
    boolean visionRight(Animal animal) {
        animal = (Predator)animal;
        int minDistance = animal.x - 10;
        if(minDistance <= x && animal.y == y){
            moveLeft();
            moveLeft();
            return true;
        }
        return false;
    }

    void die() {}

    public void draw() { PPSim.dp.drawCircle(this.x, this.y, newRandColor()); }


    public Pray eat() {
        return null;
    }

    public int getPrayXpos(){
        return x;
    }
    public int getPrayYpos(){
        return y;
    }
    public Color getPrayColor() {return c;}

    public void setPrayXpos(int x){ this.x = x; }
    public void setPrayYpos(int y){ this.y = y; }
}
