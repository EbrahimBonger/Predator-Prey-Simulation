package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static predator_prey_sim.PPSim.*;


public class World {




	protected static int width, height;
	private Color canvasColor;
	ArrayList<Predator> predators = new ArrayList<Predator>();
	ArrayList<Pray> prays = new ArrayList<Pray>();
	ArrayList<Pray> user = new ArrayList<>();
	private int initialPrays;
	private int initialPredators;



	/**
	 * Create a new City and fill it with buildings and people.
	 *
	 //* @param w           width of city
	 //* @param h           height of city
	 //* @param numPrey     number of prey
	 //* @param numPredator number of predators
	 */
	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		initialPrays = numPrey;
		initialPredators = numPredator;
		canvasColor = Helper.newRandColor();

		// Add Prey and Predators to the world.
		populate(numPrey, numPredator);

		/*Input key  description */
		System.out.println("Press ENTER to RESET the World!");
		System.out.println("Press ESCAPE to EXIT the program!");
		System.out.println("Press SPACE to CHANGE the Canvas Color!");
		System.out.println("Click on the Canvas to add Pray at the clicked location!");

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
		// and place them randomly in the world.


		for (int i = 0; i < numPrey; i++) {
			int initialPrayX = Helper.nextInt(width - 2);
			int initialPrayY = Helper.nextInt(height - 2);
			Color initialColor = Helper.newRandColor();
			Pray newPray = new Pray(initialPrayX, initialPrayY, initialColor);



			if(initialColor == canvasColor){
				newPray.camouflage = true;
			}
			prays.add(newPray);
		}

		for (int i = 0; i < numPredators; i++) {
			int initialPrdX = Helper.nextInt(width - 2);
			int initialPrdY = Helper.nextInt(height - 2);
			Predator newPrd = new Predator(initialPrdX, initialPrdY, Color.RED);
			predators.add(newPrd);
		}
	}

	/**
	 * Updates the state of the world for a time step.
	 */
	public void update() {
		// Move predators, prey, etc

		lookUp();
		move();
		reproduce("P", "R");
		checkMutation();
		die();

		/**
		 * New rules and Extra features
		 * **/
		predatorGhosting();
		PrayIntelligence();

	}

	/**
	 * Move the pray and predators
	 * **/
	public void move(){
		movePray();
		movePredator();
	}

	/**
	 * Move the pray
	 * **/
	public void movePray(){
		for(int i=0; i<prays.size(); i++){
			prays.get(i).move();

		}
	}

	/**
	 * Move the pray
	 * **/
	public void movePredator(){
		for (Predator p : predators) {
			p.move();
		}
	}

	/**
	 * Check the coordination weather the predator eat the pray
	 * */
	public void checkCoordination(int x, int y, String name) {
		if(name.contains("P")){
			// iterate through evey predator for every pray
			for(int i = 0; i < predators.size(); i++) {
				for (int j = 0; j < prays.size(); j++) {
					x = prays.get(j).getXpos();
					y =prays.get(j).getYpos();

					if(prays.get(j).camouflage == true){
						System.out.println("I am INVISIBLE");
					}
					// compare each predator's x and y position with the pray which it's color not camouflaged
					if(predators.get(i).x == x && predators.get(i).y == y && prays.get(j).camouflage != true) {
						// each pray's life will set to false
						prays.get(j).alive = false;
						// then it wil be removed from the world
						prays.remove(j);
						// the predator that eats the pray will gain energy
						predators.get(i).energy+= 1;
					}
					else{
						// otherwise, any predator that did not eat any pray at that point in time will loose energy
						predators.get(i).energy-= 0.001;
					}
				}
			}
		}
	}
	/**
	 * Calculate the rate of reproducing for pray and predator in time
	 * **/
	public void reproduce(String predator, String pray) {
		// set the probability 0 to 100
		int probability = Helper.nextInt(100)+ 1;
		// the predator has a 1% chance of reproducing an offspring
		if(predator.contains("P") && probability == 1 && predators.size() != 0){
			// Three offspring's will populate in the world
			populate(0, 3);
		}
		// the predator has a 10% chance of reproducing a single offspring
		else if(pray.contains("R") && probability > 0 && probability <= 10){
			populate(1, 0);


			}
		}

	/**
	 * Calculate the rate of mutation for pray
	 * **/
	public void checkMutation(){
		int probability = Helper.nextInt(100)+1;
		// the pray has a 10% chance of mutation
		int mutationRate = prays.size() /100 * 10;
		// check the probability of mutation
		if(probability > 0 && probability <= 10){
			// a 10% of the mutation will occur in the pray population at this point in time
			for(int i=0; i<mutationRate; i++){
				// if the pray reproducing chance will set to true if it was already false
				if(prays.get(Helper.nextInt(prays.size())).reproduce == true){
					prays.get(Helper.nextInt(prays.size())).reproduce = false;
				}
			}
		}
	}
	public void die(){
		for(int i=0; i< predators.size(); i++){
			if(predators.get(i).energy <= 0){
				predators.remove(i);
			}
		}
	}

	/**
	 * Draw all the predators and prey.
	 */
	public void draw() {
		/* Clear the screen */

		PPSim.dp.clear(canvasColor);

		for (Predator p : predators) {
			checkCoordination(p.x, p.y, "P");
			p.draw();
		}
		for(int i=0; i<prays.size(); i++){
			//checkCoordination(prays.get(i).x, prays.get(i).y);
			prays.get(i).draw();
		}

	}
	/**
	 * Prays will detect a predator from certain distance and try to run away
	 * Predators will detect a pray from certain distance try to chase to ward that direction
	 * **/
	public void lookUp() {

		/**
		* Check predators presence
		* up = 0 , down = 1 , left = 2,  right = 3
		**/
		// maximum range the pray can detect predator
		int maxRange = 10;
		// iterate through evey pray for every predator
		for (int i = 0; i < prays.size(); i++) {
				for (int j = 0; j < predators.size(); j++) {
					int x = predators.get(j).x;
					int y = predators.get(j).y;

					// the pray will escape to up, if the predator approach from south
					if ((prays.get(i).x == x) && (y - prays.get(i).y) <= maxRange && (y - prays.get(i).y) > 0) {
						// if there is enough space, the pray will run 2 square away
						if(prays.get(i).y > 2) {
							prays.get(i).y = prays.get(i).y - 2;
						}
						prays.get(i).direction = 0;
					}
					// The pray will escape to down, if the predator approach from north
					if ((prays.get(i).x == x) && (prays.get(i).y - y) <= maxRange && (prays.get(i).y - y) > 0 ) {
						// if there is enough space, the pray will run 2 square away
						if(prays.get(i).y + 2 < MAX_Y - 2) {
							prays.get(i).y = prays.get(i).y + 2;
						}
						prays.get(i).direction = 1;
					}
					// the pray will escape to left, if the predator approach from East
					if ((prays.get(i).y == y) && (x - prays.get(i).x) <= maxRange && (x - prays.get(i).x) > 0 && prays.get(i).x >= 2) {
						// if there is enough space, the pray will run 2 square away
						if(prays.get(i).x > 2) {
							prays.get(i).x = prays.get(i).x - 2;
						}
						prays.get(i).direction = 2;
						//prays.get(i).direction = 2;
					}
					//the pray will escape to right, if the predator approach from West
					if ((prays.get(i).y == y) && (prays.get(i).x - x) <= maxRange && (prays.get(i).x - x) > 0) {
						// if there is enough space, the pray will run 2 square away
						if(prays.get(i).x < MAX_X - 2) {
							prays.get(i).x = prays.get(i).x + 2;
						}
						prays.get(i).direction = 3;
					}

				}
			}
			/**
			 * Check prays presence
			 * up = 0 , down = 1 , left = 2,  right = 3
			 **/
		// maximum range the predator can detect pray
			maxRange = 15;
			// iterate through evey predator for every pray
			for (int i = 0; i < predators.size(); i++) {
				for (int j = 0; j < prays.size(); j++) {

					int x = prays.get(j).x;
					int y =prays.get(j).y;
					// chase the pray up if it appears from NORTH
					if ( (predators.get(i).x == x) && (predators.get(i).y - y) <= maxRange && (predators.get(i).y - y) > 0 && prays.get(j).camouflage != true) {
						predators.get(i).direction = 0;
					}
					// chase the pray down if it appears from SOUTH
					if (predators.get(i).x == x && y - predators.get(i).y <= maxRange && y - predators.get(i).y > 0 && prays.get(j).camouflage != true) {

						predators.get(i).direction = 1;
					}
					// chase the pray left if it appears from WEST
					if (predators.get(i).y == y && predators.get(i).x - x <= maxRange && predators.get(i).x - x > 0 && prays.get(j).camouflage != true) {
						predators.get(i).direction = 2;
					}
					// chase the pray right if it appears from EAST
					if (predators.get(i).y == y && x - predators.get(i).x <= maxRange && x - predators.get(i).x > 0 && prays.get(j).camouflage != true) {
						predators.get(i).direction = 3;
					}
				}
			}
	}
	/**
	 * The pray analyzes multi obstacle scenarios to scape from the predator
	 * including random directions if there are more than one escaping possibilities
	 * */
	public void PrayIntelligence(){

		int maxRange = 10;
			for (int i = 0; i < prays.size(); i++) {
				for (int j = 0; j < predators.size(); j++) {
					int x = predators.get(j).x;
					int y = predators.get(j).y;
					int randomNum = ThreadLocalRandom.current().nextInt(2, 3 + 1);

					/**
					 * All edge cases have been handled
					 * */

					// The pray will change the direction to the left
					if ((prays.get(i).x == x) && (prays.get(i).y - y) <= maxRange && (prays.get(i).y - y) > 0 && MAX_X - prays.get(i).x <= 10 && MAX_Y - prays.get(i).y <= 2) {
						prays.get(i).direction = 2;
					}
					// The pray will change the direction to the left or right randomly while the predator chasing from North to south
					else if((prays.get(i).x == x) && (prays.get(i).y - y) <= maxRange && (prays.get(i).y - y) > 0 && prays.get(i).x >= MAX_X / 10 && prays.get(i).x <= MAX_X - 10 && MAX_Y - prays.get(i).y <= 2){
						prays.get(i).direction = randomNum;
					}
					// The pray will change the direction to the left or right randomly while the predator chasing from South to North
					else if((prays.get(i).x == x) && (y - prays.get(i).y) <= maxRange && (y - prays.get(i).y) > 0 && prays.get(i).x >= MAX_X / 10 && prays.get(i).x <= MAX_X - 10 && prays.get(i).y <= 2){
						//MAX_Y - prays.get(i).y <= 2
						prays.get(i).direction = randomNum;
					}
					// The pray will change the direction to the left
					if ((prays.get(i).x == x) && (y - prays.get(i).y) <= maxRange && (y - prays.get(i).y) > 0 && MAX_X - prays.get(i).x <= 10 && prays.get(i).y <= 2) {
						prays.get(i).direction = 2;
					}
					// The pray will change the direction to the left
					if ((prays.get(i).x == x) && (y - prays.get(i).y) <= maxRange && (y - prays.get(i).y) > 0 && prays.get(i).x <= 10 && prays.get(i).y <= 2) {
						prays.get(i).direction = 3;
					}
					// The pray will randomly turn up or down
					if(prays.get(i).y  == y && prays.get(i).x - x <= maxRange && prays.get(i).x - x > 0 && prays.get(i).y >= MAX_Y / 10 && prays.get(i).y <= MAX_Y - 10 && MAX_X - prays.get(i).x <= 2){
						// scape to up or down
						prays.get(i).direction = Helper.nextInt(2);
					}
					//the pray will turn to down at the edge
					if(prays.get(i).y  == y && prays.get(i).x - x <= maxRange && prays.get(i).x - x > 0 && prays.get(i).y <= 2 && MAX_X - prays.get(i).x <= 2){
						// scape to up or down
						prays.get(i).direction = 1;
					}
					// the pray will turn to up at the edge
					if(prays.get(i).y  == y && prays.get(i).x - x <= maxRange && prays.get(i).x - x > 0 && MAX_Y - prays.get(i).y <= 2 && MAX_X - prays.get(i).x <= 2){
						// scape to up or down
						prays.get(i).direction = 0;
					}
					// The pray will randomly turn up or down
					if(prays.get(i).y  == y && prays.get(i).x - x <= maxRange && x - prays.get(i).x > 0 && prays.get(i).y >= MAX_Y / 10 && prays.get(i).y <= MAX_Y - 10 && prays.get(i).x <= 2){
						// scape to up or down
						prays.get(i).direction = Helper.nextInt(2);
					}
					// the pray will turn to up at the edge
					if(prays.get(i).y  == y && prays.get(i).x - x <= maxRange && x - prays.get(i).x > 0 && MAX_Y - prays.get(i).y <= 2 && prays.get(i).x <= 2){
						// scape to up or down
						prays.get(i).direction = 0;
					}
					// the pray will turn to down at the edge
					if(prays.get(i).y  == y && prays.get(i).x - x <= maxRange && x - prays.get(i).x > 0 && prays.get(i).y <= 2 && prays.get(i).x <= 2){
						// scape to up or down
						prays.get(i).direction = 1;
					}
				}
			}
	}

	/**
	 * The predator moves aggressively toward the pray
	 * when it maintains a precision of certain distance between the pray
	 * */
	public void predatorGhosting(){

		for (int i = 0; i < predators.size(); i++) {
			for (int j = 0; j < prays.size(); j++) {
				int x = predators.get(i).x;
				int y = predators.get(i).y;
				int pX = prays.get(j).x;
				int pY = prays.get(j).y;
				// locking distance the predator can ghost to the pray
				int lockedDis = 6;
				// ghosting up while the pray present in the precision distance
				if (x - pX == 0 && y - pY == lockedDis && prays.get(j).camouflage != true){
					predators.get(i).y-=lockedDis;
				}
				// ghosting down  while the pray present in the precision distance
				if(x - pX == 0 && pY - y == lockedDis && prays.get(j).camouflage != true){
					predators.get(i).y+=lockedDis;
				}
				// ghosting right  while the pray present in the precision distance
				if(y - pY == 0 && x - pX == lockedDis && prays.get(j).camouflage != true){
					predators.get(i).x-=lockedDis;
				}
				// ghosting left  while the pray present in the precision distance
				if(y - pY == 0 && pX - x == lockedDis && prays.get(j).camouflage != true){
					predators.get(i).x+=lockedDis;
				}

			}
		}
	}
	/**
	 * New random color pray will drop at the x and y coordinate
	 * within the bound of the world where the user clicked
	 * **/
	public void mouseInput(int x, int y){
			Pray dropPray = new Pray(x, y, Helper.newRandColor());
			prays.add(dropPray);
	}

	/**
	 * The world listen to the SPACE key and changes the canvas to random color
	 * **/
	public void changeCanvas(){
		canvasColor = Helper.newRandColor();
	}
	/**
	 * The world listen to the ENTER key and resets
	 * the world to the initial the number of prays and predators
	 * **/
	public void resetWorld() {
		// Clear all predators and prays from the canvas
		prays.clear();
		predators.clear();
		user.clear();
		// repopulate the predators and prays to the canvas
		populate(initialPrays, initialPredators);
	}

}

