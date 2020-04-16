package predator_prey_sim;

import util.Helper;

import java.awt.*;

public abstract class Animal {
    String name;
    Color c;
    int x;
    int y;
    //  up = 0 , down = 1 , left = 2,  right = 3
    int direction =  Helper.nextInt(4);
    boolean alive = true;
    boolean userAlive = false;
    public Animal(){}
    public Animal(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    abstract void move();
    abstract void draw();
}
