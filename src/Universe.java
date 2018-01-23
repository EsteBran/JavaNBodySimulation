// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * Universe Class
 * Reads a text file of the bodies in the universe and parses it into planets, 
 * their masses, and their positions. 
 * Radius is calculated via mass
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Universe {

	private int n; // number of bodies/planet
	private Body[] bodies; // array of bodies
	Random rand = new Random(); 
	
	public Universe() {
		File universe = null;
		//generates a random integer between 1 and 4, chooses a different file based on which integer is chosen, each file contains a diferent system
		int number = rand.nextInt(3)+1; 
		if (number == 1) {
			universe = new File("resources/Bodies1.txt");
		}
		else if (number == 2) {
			universe = new File("resources/Bodies2.txt");
		}
		else if (number == 3) {
			universe = new File("resources/Bodies3.txt");
		}
		else if (number == 4) {
			universe = new File("resources/Bodies4.txt");
		}
		try {

			Scanner scan = new Scanner(universe);
			// this will be the number of bodies
			n = scan.nextInt();
			
			// reads the n bodies and their velocity and positions
			bodies = new Body[n];
			for (int i = 0; i < n; i++) {
				double xPos = scan.nextDouble();
				double yPos = scan.nextDouble();
				double xVel = scan.nextDouble();
				double yVel = scan.nextDouble();
				double mass = scan.nextDouble();
				double[] positionData = { xPos, yPos };
				double[] velocityData = { xVel, yVel };
				Vector position = new Vector(positionData);
				Vector velocity = new Vector(velocityData);

				// sets radius according to mass, introduces more variety to the universe
				double radius = 0;
				if (mass >= 1e30)
					radius = 100;
				else if (mass >= 1e28)
					radius = 80;
				else if (mass >= 1e26)
					radius = 40;
				else if (mass >= 1e24)
					radius = 20;
				else if (mass >= 1e22)
					radius = 10;
				else if (mass >= 1e20)
					radius = 5;
				bodies[i] = new Body(position, velocity, mass, radius);
			}
			scan.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// updates the universe and calculates the new positions of all bodies
	public void updateUniverse(double dt) {
		// initialize the forces to zero
		Vector[] f = new Vector[n];
		for (int i = 0; i < n; i++) {
			f[i] = new Vector(new double[2]);
		}

		// compute the forces
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i != j) {
					f[i] = f[i].plus(bodies[i].forceFrom(bodies[j]));
				}
			}
		}

		// moves the bodies
		for (int i = 0; i < n; i++) {
			bodies[i].move(f[i], dt);
		}

	}

	public Body[] getBodies() {
		return this.bodies;
	}

}
