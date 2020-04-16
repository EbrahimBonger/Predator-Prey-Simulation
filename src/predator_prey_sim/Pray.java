package predator_prey_sim;

import util.Helper;

import java.awt.*;

import static predator_prey_sim.PPSim.*;

public class Pray extends Animal {

    boolean camouflage = false;
    boolean reproduce = true;

    public Pray( int x, int y, Color c){
        super(x, y, c);
        this.x = x;
        this.y = y;
    }
    void move() {
        //  up = 0 , down =1 , left = 2,  right = 3
        int probability = Helper.nextInt(100) + 1;
        int randDirection  = Helper.nextInt(4);
        // changing direction randomly
        if(probability < 10 && probability > 0){
            direction = randDirection;
        }
            if (direction == 0){ moveUp();}
            if (direction == 1){moveDown();}
           if (direction == 2){moveLeft();}
           if (direction == 3){moveRight();}
    }

    public void moveDown() {
        if (alive == true) {
            if (y < MAX_Y - 2) {
                y = y + 1;
                //System.out.println(y);
            }
        }
    }
    public void moveUp() {
        if (alive == true){
            if (y > 0) {
                y = y - 1;
            }
        }
    }
    public void moveLeft() {
        if (alive == true){
            if (x > 0) {
                x = x - 1;
            }
        }
    }
    public void moveRight() {
        if (alive == true) {
            if (x < MAX_X - 2) {
                x = x + 1;
            }
        }
    }
    
    void draw() {
        PPSim.dp.drawCircle(this.x, this.y, this.c);
    }
    public int getXpos(){ return x; }
    public int getYpos(){return y; }


}
