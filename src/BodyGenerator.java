// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * creates a set number of bodies with random positions, velocities, and masses
 * the method updateUniverse updates the all the bodies in the class by one step
 * Overall same theory as the Universe class except this time every number is randomly generated
 */

import java.util.Random;


public class BodyGenerator {

	private Random rand = new Random();
	private int nBodies;
	private Body[] bodies;
	private double randMass;

	// creates a random velocity within bounds (10e1-10e3)
	public Vector getVel() {
		int i = rand.nextInt(3) + 1;
		int j = 0;
		if (rand.nextBoolean()) {
			j = -1;
		} else {
			j = 1;
		}
		double randVelX = (Math.pow(10, i) * rand.nextDouble() * j);
		double randVelY = (Math.pow(10, i) * rand.nextDouble() * j);
		double[] randVelData = { randVelX, randVelY };
		Vector randVel = new Vector(randVelData);
		return randVel;
	}

	// creates a random position within bounds (10e7-10e11)
	public Vector getPos() {
		int i = rand.nextInt(4) + 7;
		int j = 0;
		if (rand.nextBoolean()) {
			j = -1;
		} else {
			j = 1;
		}
		double randPosX = (Math.pow(10, i) * rand.nextDouble() * j);
		double randPosY = (Math.pow(10, i) * rand.nextDouble() * j);
		double[] randPosData = { randPosX, randPosY };
		Vector randPos = new Vector(randPosData);
		return randPos;
	}
	//returns a random mass within a bound (10e20-10e60)
	public double getMass() {
		int i = rand.nextInt(40) + 20;
		randMass = (Math.pow(10, i) * rand.nextDouble());
		return randMass;
	}


	// constructor for BodyGenerator, generates n random bodies
	public BodyGenerator(int n) {
		nBodies = n;
		this.bodies = new Body[nBodies];
		double mass;
		Vector position;
		Vector velocity;

		for (int i = 0; i < nBodies; i++) {

			mass = this.getMass();
			position = this.getPos();
			velocity = this.getVel();
			// sets radius according to mass, introduces more variety to the universe
			double radius = 0;
			if (mass >= 1e40)
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
			this.bodies[i] = new Body(velocity, position, mass, radius);

		}

	}
	//returns the body array
	public Body[] getBodies() {
		return this.bodies;

	}

	//calculates the forces and moves all the bodies a corresponding amount
	public void updateUniverse(double dt) {
		// initialize the forces to zero
		Vector[] f = new Vector[nBodies];
		for (int i = 0; i < nBodies; i++) {
			f[i] = new Vector(new double[2]);
		}

		// compute the forces
		for (int i = 0; i < nBodies; i++) {
			for (int j = 0; j < nBodies; j++) {
				if (i != j) {
					f[i] = f[i].plus(bodies[i].forceFrom(bodies[j]));
				}
			}
		}

		// moves the bodies
		for (int i = 0; i < nBodies; i++) {
			bodies[i].move(f[i], dt);
		}

	}

}
