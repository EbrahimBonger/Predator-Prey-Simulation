package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.util.ArrayList;


public class World {

	protected static int width, height;
	private Color canavasColor;
	//static int programCounter = 0;

	ArrayList<Animal> animals = new ArrayList<>();
	ArrayList<Predator> predators = new ArrayList<Predator>();
	ArrayList<Pray> prays = new ArrayList<Pray>();

	ArrayList<Animal> prayAnimal = animals;
	//ArrayList<String> arrayListClone =  (ArrayList<String>) arrayListObject.clone();

	ArrayList<Predator> clonePrd;

	ArrayList<Pray> clonePray;

	ArrayList<Predator> die = new ArrayList<Predator>();
	ArrayList<Pray> eaten = new ArrayList<Pray>();
	boolean isPrayAlive, isprdAlive, prayCamof;
	int prayX, prayY, prdX, prdY, prdEnergy;

	/**
	 * Create a new City and fill it with buildings and people.
	 *
	 //* @param w           width of city
	 //* @param h           height of city
	 //* @param numPrey     number of prey
	 //* @param numPredator number of predators
	 */
	public World(){}
	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		//canavasColor = Color.white;
		canavasColor = Helper.newRandColor();
		//System.out.println("programCounter..." + programCounter++);

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

		for (int i = 0; i < numPrey; i++) {
			int initialPrayX = Helper.nextInt(width - 2);
			int initialPrayY = Helper.nextInt(height - 2);
			Color initialColor = Helper.newRandColor();
			// public Pray( int x, int y, Color c, boolean camouflage )
			Pray newPray = new Pray(initialPrayX, initialPrayY, initialColor);
			newPray.setCamouflage(false);
			newPray.visibility(canavasColor);
			//System.out.println("Populating...Prays...");
			prays.add(newPray);
		}
		//clonePray = ((Pray)animals);

		for (int i = 0; i < numPredators; i++) {
			int initialPrdX = Helper.nextInt(width - 2);
			int initialPrdY = Helper.nextInt(height - 2);
			int prdEnergy = 100;
			Color newColor = Color.RED;
			Predator newPrd = new Predator(initialPrdX, initialPrdY, newColor, prdEnergy);
			newPrd.setEnergy(100);

			//Predator newPrd = new Predator(new Predator(initialPrdX, initialPrdY, newPrd.getPredColor(), newPrd.getEnergy()));
			//System.out.println("Populating...Predator BEFORE");
			predators.add(newPrd);
			//System.out.println("Populating...Predator");

		}
		//toString();

	}

	/**
	 * Updates the state of the world for a time step.
	 */
	public void update() {
		// Move predators, prey, etc
		//System.out.println("P-- " + predators.size() + "  R-- " + prays.size());
		if ( prays.size() < 3 && predators.isEmpty() == false){
			//predators.get(i).setIsAlive(false);
			for(int c=0; c<predators.size(); c++){
				predators.get(c).setEnergy(predators.get(c).getEnergy() - 100000);
				//predators.get(i).setEnergy(predators.get(i).getEnergy() - 1000000);
				//predators.get(c).setIsAlive(false);
				System.out.println("Dying..." + predators.get(c).getEnergy() + "  size "+ predators.size());
				toString(predators);
			}


		}

		if (predators != null && prays != null) {

			for (int i = 0; i < predators.size(); i++) {
				//System.out.println("P-- " + predators.get(i).isAlive() + "  Energy:  " + predators.get(i).getEnergy() );
				if ( predators.get(i).getEnergy() <= 0) {
					//predators.get(i).setColor(Color.blue);
					//System.out.println("P-- " + predators.get(i).isAlive() + "  Energy:  " + predators.get(i).getEnergy() );
					//checkCoordination(prays.get(i).getXpos(), prays.get(i).getYpos());
					predators.remove(i);
				}
				//System.out.println("P-- size: " + predators.size() );
				if(predators.size() != 0){
					//checkCoordination(predators.get(i).getXpos(), predators.get(i).getYpos());
					//System.out.println("P-- size: not Empty" + predators.size() );
				}

			}

		}

		//System.out.println("programCounter...updating..." + programCounter++);
		for (int i = 0; i < eaten.size(); i++) {
			for (int j = 0; j < prays.size(); j++) {
				if (eaten.get(i).equals(prays.get(j)) == true) {
					prays.remove(prays.get(j));
				}
			}
		}


		ArrayList<Predator> pp = predators;
		ArrayList<Pray> rr = prays;

		//checkCoordination(pp, rr);
		for (Pray r : prays) {
			if (r.alive != false) {
				r.move();
			}

			//checkCoordination(predators, prays);
		}
		for (Predator p : predators) {
			p.move();
		}
		reproduce();
	}
	public boolean checkCoordination(int x, int y) {
		//System.out.println("programCounter...checking coordination..." + programCounter++);

		for (int i = 0; i < predators.size(); i++) {
			for (int j = 0; j < prays.size(); j++) {
				//p = prays.get(j);
				if(predators.get(i).getEnergy() <= 0){
					predators.get(i).setIsAlive(false);
					predators.get(i).setColor(Color.blue);
				}
				x = prays.get(j).getXpos();
				y =prays.get(j).getYpos();
				if (predators.get(i).getXpos() == x && predators.get(i).getYpos() == y && prays.get(j).visibility(canavasColor) != true) {
					prays.get(j).setAlive(false);
					//System.out.println("PP size... " + predators.size() + " Pray size... " + prays.size() + " --Cam :" + prays.get(j).getCamouflage() + " Vis : " + prays.get(j).visibility(canavasColor));
					eaten.add(prays.get(j));
					//System.out.println("Intersected...");
					//System.out.println("Before... " + predators.get(i).getEnergy());
					//if(predators.get(i).getEnergy() <= 100000000){
						predators.get(i).setEnergy(predators.get(i).getEnergy() + 100000000);
						//System.out.println("After... " + predators.get(i).getEnergy());
					//}


					predators.get(i).setColor(Color.black);

					//System.out.println( predators.get(j).getXpos() + "...Killed..." +  predators.get(j).getYpos());
					//System.out.println( prays.get(j).getXpos() + "..." +  prays.get(j).getYpos() + " Intersected ..." + prays.get(j).alive + " Canvas: "+ prays.get(j).visibility(canavasColor));
					//prays.get(j).setColor(Color.black);
					//p = prays.get(j);
					prays.remove(prays.get(j));
					return false;
				}
			}
		}
		return true;
	}
	public void reproduce() {

		if (prays != null) {

		for (int i = 0; i < prays.size(); i++) {
			int max = 3;
			//System.out.println("NOT Produced...");
			if (prays.get(i).reproduce() == true) {
				while (max != 0) {

					int x = Helper.nextInt(width - 2);
					int y = Helper.nextInt(height - 2);
					prays.add(new Pray(x, y, Helper.newRandColor()));
					//prays.get(i).setX( Helper.nextInt(width - 2));
					//prays.get(i).setY( Helper.nextInt(height - 2));
					//prays.get(i).setColor(Color.blue);
					//Helper.newRandColor()
					//System.out.println("Produced...-R-....." + prays.get(i).offSpring);
					max--;
				}

			}
		}
		for(int i=0; i<predators.size(); i++){
			if (predators != null){
				int max = 2;
				if (predators.get(i).reproduce() == true) {
					while (max != 0) {

						int x = Helper.nextInt(width - 2);
						int y = Helper.nextInt(height - 2);
						int energy = 100;
						predators.add(new Predator(x, y, Color.yellow,energy));
						//prays.get(i).setX( Helper.nextInt(width - 2));
						//prays.get(i).setY( Helper.nextInt(height - 2));
						//prays.get(i).setColor(Color.blue);
						//Helper.newRandColor()
						//System.out.println("Produced...-P-...." + predators.get(i).offSpring);
						max--;
					}

				}
			}

			}
		}
	}
	/**
	 * Draw all the predators and prey.
	 */
	public void draw() {
		/* Clear the screen */
		//System.out.println("Drawing...");
		//System.out.println("programCounter...Drawing..." + programCounter++);
		PPSim.dp.clear(canavasColor);
		// ((B)a) B is child --a is  parent
		//System.out.println("Drawing..");


//		for (int i = 0; i < animals.size(); i++) {
//			//System.out.println("Looking to remove..." + animals.get(i).isAlive());
//			//public void checkCoordination(ArrayList<Predator> predators, ArrayList<Pray> prays)
//			//checkCoordination(animals);
//			if (animals.get(i).isAlive() == false) {
//				animals.remove(i);
//				//System.out.println("pray removed...from draw method " + animals.size());
//			}
//			animals.get(i).draw();
//
//		}

		for (Predator p : predators) {
			checkCoordination(p.x, p.y);
			p.draw();
		}
//		for (int i = 0; i < prays.size(); i++) {
////			if (checkCoordination(prays.get(i)) == true){
//			checkCoordination(p.x, p.y);
//			prays.get(i).draw();
////			}
//		}
		for(Pray r: prays){
			//checkCoordination(r.x, r.y);
			r.draw();
		}

	}


	public String toString(ArrayList<Predator> animals){
		String str = " ";
		for(Animal a: animals){
			str+= " : ";
			System.out.println(a.getName() + " x: " + a.x + " --  y: " + a.y);
		}
		return str;
	}

}

