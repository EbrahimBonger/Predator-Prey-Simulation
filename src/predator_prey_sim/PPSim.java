package predator_prey_sim;

import util.DotPanel;
import util.Helper;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.*;


/*
 * You must add a way to represent predators.  When there is no prey, predators
 * should follow these simple rules:
 * 		if (1 in 5 chance):
 * 			turn to face a random direction (up/down/left/right)
 * 		Move in the current direction one space unless stepping out of the world.
 *
 * We will add additional rules for dealing with sighting or running into prey later.
 */

public class PPSim extends JFrame implements KeyListener, MouseListener {

	private static final long serialVersionUID = -5176170979783243427L;

	/** The Dot Panel object you will draw to */
	protected static DotPanel dp;
	public static DotPanel screenSource;
	private World ppworld;

	/* Define constants using static final variables */
	public static final int MAX_X = 100;
	public static final int MAX_Y = 100;
	public static final int DOT_SIZE = 6;
	private static final int NUM_PREY = 10;
	private static final int NUM_PREDATORS = 5;


	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares for predators and circles for prey
	 * to the screen.
	 */
	public PPSim() {

		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Predator Prey World");


		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);
		screenSource = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);
		/* Add the panel to the frame */
		Container cPane = this.getContentPane();
		cPane.add(dp);
		dp.setLayout(new FlowLayout());





		/* Initialize the DotPanel canvas:
		 * You CANNOT draw to the panel BEFORE this code is called.
		 * You CANNOT add new widgets to the frame AFTER this is called.
		 */
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		dp.addMouseListener(this);
		this.setVisible(true);
		this.addKeyListener(this);

		/* Create our city */

		ppworld = new World(MAX_X, MAX_Y, NUM_PREY, NUM_PREDATORS);
		//this.addKeyListener(this);
		/* This is the Run Loop (aka "simulation loop" or "game loop")
		 * It will loop forever, first updating the state of the world
		 * (e.g., having predators take a single step) and then it will
		 * draw the newly updated simulation. Since we don't want
		 * the simulation to run too fast for us to see, it will sleep
		 * after repainting the screen. Currently it sleeps for
		 * 33 milliseconds, so the program will update at about 30 frames
		 * per second.
		 */
		while(true)
		{
			// Run update rules for world and everything in it
			ppworld.update();
			ppworld.draw();
			dp.repaintAndSleep(50);

		}
	}

	public static void main(String[] args) {
		/* Create a new GUI window  */
		new PPSim();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		switch (key){
			case KeyEvent.VK_SPACE:
				ppworld.changeCanvas();
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_ENTER:
				ppworld.resetWorld();
				break;
		}
		char c = e.getKeyChar();

		if (c == 'w'){
			ppworld.userControl('w');
		}
		if (c == 's'){
			ppworld.userControl('s');
		}
		if (c == 'a'){
			ppworld.userControl('a');
		}
		if (c == 'd'){
			ppworld.userControl('d');
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ppworld.mouseInput(e.getX() / DOT_SIZE,e.getY() / DOT_SIZE);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}

