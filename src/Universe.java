import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * Universe Class
 * Reads a text file of the bodies in the universe and parses it into planets, their masses, and their positions
 */
public class Universe {

	private int n; // number of bodies/planets
	private Body[] bodies; // array of bodies
	

	public Universe() {

		File universe = new File("resources/Bodies1.txt");

		try {

			Scanner scan = new Scanner(universe);

			
			// this will be the number of bodies
			n = scan.nextInt();
			
			double radius = scan.nextDouble(); //radius of the planet, will be used in the GUI class 

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
				bodies[i] = new Body(position, velocity, mass, radius);

			}
			scan.close();
			//print for testing 
			for (int i = 0; i < bodies.length; i++) {
				System.out.println(Arrays.toString(bodies[i].getPosition()) +" "+ Arrays.toString(bodies[i].getVelocity()) + " " + bodies[i].getMass() +" "+ bodies[i].getRadius());
			}

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
	
}
