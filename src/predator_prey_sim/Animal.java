package predator_prey_sim;

import util.Helper;

import java.awt.*;
// implement natural disaster for extra credit
// Abstract
public abstract class Animal {
    String name;
    Color c;
    int x;
    int y;
    int direction = Helper.nextInt(4); //  up = 0 , down =1 , left = 2,  right = 3
    boolean alive = true;
    boolean offSpring = true;

    public Animal(){}
    public Animal(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    //abstract methods
    //public void printAnimal(){
        //System.out.println("x:" + x + " y:" + y);
    //}
    abstract void move();
    abstract boolean reproduce();
    abstract int visionUp(int enemyY);
    abstract int visionDown(int enemyY);
    abstract int visionLeft(int enemyX);
    abstract int visionRight(int enemyX);
    abstract boolean isAlive();
    abstract int getXpos();
    abstract int getYpos();
    abstract void setX(int x);
    abstract void setY(int y);
    abstract void draw();
    abstract String getName();


    //public boolean eat() {}


}
