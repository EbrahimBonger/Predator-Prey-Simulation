package predator_prey_sim;

import util.Helper;

import java.awt.*;
// implement natural disaster for extra credit
// Abstract
public abstract class Animal {
    Color c;
    int x;
    int y;
    int direction = Helper.nextInt(4); //  up = 0 , down =1 , left = 2,  right = 3

    //abstract methods
    public void printAnimal(){
        System.out.println("x:" + x + " y:" + y);
    }
    abstract void move();
    abstract void reproduce();
    abstract boolean visionUp(Animal x);
    abstract boolean visionDown(Animal x);
    abstract boolean visionLeft(Animal x);
    abstract boolean visionRight(Animal x);
    abstract void draw();

    //public boolean eat() {}


}
