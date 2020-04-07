package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.util.ArrayList;

import static predator_prey_sim.PPSim.dp;
import static util.Helper.newRandColor;
import static util.Helper.nextInt;
import java.util.Random;


public class World {

	protected static int width, height;
	private Color canavasColor;

	ArrayList<Animal> animals = new ArrayList<Animal>();

	/**
	 * Create a new City and fill it with buildings and people.
	 *
	 * @param w           width of city
	 * @param h           height of city
	 * @param numPrey     number of prey
	 * @param numPredator number of predators
	 */
	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		canavasColor = Helper.newRandColor();

		// Add Prey and Predators to the world.
		populate(numPrey, numPredator);
	}


	/**
	 * Generates numPrey random prey and numPredator random predators
	 * distributed throughout the world.
	 * Prey must not be placed outside canavas!
	 *
	 * @param numPrey      the number of prey to generate
	 * @param numPredators the number of predators to generate
	 */
	private void populate(int numPrey, int numPredators) {
		// Generates numPrey prey and numPredator predators 
		// randomly placed around the world.


//		for (int i = 0; i < numPrey; i++) {
//			pray = new Pray();
//
//			pray.setPrayXpos(Helper.nextInt(width-2));
//			pray.setPrayYpos(Helper.nextInt(height-2));
//			animals.add(pray);
//			System.out.println(pray.getPrayXpos() + " " + pray.getPrayYpos());
//		}

		for (int i = 0; i < numPredators; i++) {
			animals.add(new Predator(Helper.nextInt(width-2),Helper.nextInt(height-2)));
//			animals.add(new Predator(0, 0)); // testing
//			animals.add(new Predator(0, 98));
//			animals.add(new Predator(98, 0));
//			animals.add(new Predator(98, 98));

			//predator.setPredXpos(Helper.nextInt(width-2));
			//predator.setPredYpos(Helper.nextInt(height-2));
			//System.out.println(i);

		}
	}

	/**
	 * Updates the state of the world for a time step.
	 */
	public void update() {
		// Move predators, prey, etc
		for (Animal a: animals) {
			a.move();
		}
	}

	/**
	 * Draw all the predators and prey.
	 */
	public void draw() {
		/* Clear the screen */
		PPSim.dp.clear(canavasColor);
		// ((B)a) B is child --a is  parent
		//System.out.println("Drawing..");
		for(Animal p : animals) {
			p.draw();
		}
	}


	public void printMethod(){
		System.out.println(animals.size() + " size");
	}
	public void eat(){
		Predator testPred = new Predator(52, 50);
		Pray testPray = new Pray(50, 52);
		//prays(50,50);

		if(testPred.x == testPray.x && testPred.y == testPray.y){
		}
		System.out.println(" Eat...");
	}



}
