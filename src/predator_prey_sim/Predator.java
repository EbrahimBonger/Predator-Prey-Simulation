package predator_prey_sim;

import util.Helper;

import java.awt.*;

import static predator_prey_sim.PPSim.*;

public class Predator extends Animal {

    /**New rule, the predator has initial energy that
     * increment if eats the pray or decrement it did not **/
    double energy = 150.0;
    public Predator(){}
    public Predator(int x, int y, Color c){
        super(x, y, c);
        this.c = c;
        this.x = x;
        this.y = y;

    }

    void move() {
        //  up = 0 , down =1 , left = 2,  right = 3
        int probability = Helper.nextInt(100);
        int randDirection  = Helper.nextInt(4);
        if(probability < 5 && probability > 0){
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
        }

    }
    void moveUp() {
        if(y > 0){
            y = y - 1;
        }

    }
    void moveLeft() {
        if (x > 0){
            x = x - 1;
        }

    }
    void moveRight() {
        if (x < MAX_X-2){
            x = x + 1;
        }
    }
    void draw() {
        PPSim.dp.drawSquare(this.x, this.y, this.c);
    }

}


