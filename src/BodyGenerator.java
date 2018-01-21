import java.util.Arrays;
import java.util.Random;

// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * creates a set number of bodies with random positions, velocities, and masses
 */
public class BodyGenerator {

	private Random rand = new Random();
	private int nBodies;
	private Body[] bodies;
	private double randMass;

	// creates a random velocity
	public Vector getVel() {
		int i = rand.nextInt(4) + 4;
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

	// creates a random position
	public Vector getPos() {
		int i = rand.nextInt(5) + 7;
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

	public double getMass() {
		int i = rand.nextInt(40) + 20;
		randMass = (Math.pow(10, i) * rand.nextDouble());
		return randMass;
	}

	public int getRandN() {
		int n = rand.nextInt(16);
		return n;
	}

	// constructor for BodyGenerator, generates n random bodies
	public BodyGenerator(int n) {
		nBodies = n;
		this.bodies = new Body[nBodies];
		double mass1 = this.getMass();
		Vector position1 = this.getPos();
		Vector velocity1 = this.getVel();
		// sets radius according to mass, introduces more variety to the universe
		double radius1 = 0;
		if (mass1 >= 1e40)
			radius1 = 100;
		else if (mass1 >= 1e28)
			radius1 = 80;
		else if (mass1 >= 1e26)
			radius1 = 40;
		else if (mass1 >= 1e24)
			radius1 = 20;
		else if (mass1 >= 1e22)
			radius1 = 10;
		else if (mass1 >= 1e20)
			radius1 = 5;
		this.bodies[0] = new Body(velocity1, position1, mass1, radius1);
		
		for (int i = 1; i < nBodies; i++) {
			
			double mass = this.getMass();
			Vector position = this.getPos();
			Vector velocity = this.getVel();
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
			
			if (this.bodies[i].detectCollision(bodies[i-1])) {
				this.bodies[i] = new Body(velocity, position, mass, radius);
			}
		}

	}

	public Body[] getBodies() {
		return this.bodies;
	}

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

			// detects if two bodies collide and combines the bodies to produce a bigger one
			for (int j = 0; j < nBodies - 1; j++)
				if (bodies[j].detectCollision(bodies[j + 1])) {
					double mass1 = bodies[j].getMass();
					double mass2 = bodies[j + 1].getMass();
					System.out.println("collission detected" + " " + mass1 + " " + mass2);

					if (mass1 > mass2) {
						bodies[j + 1].setMass(0);
						bodies[j + 1].setRadius(0);
						bodies[j].setMass(mass1 + mass2);
						System.out.println(" " + bodies[j].getMass() + " " + bodies[j + 1].getMass());
					} else {
						bodies[j].setMass(0);
						bodies[j].setRadius(0);
						bodies[j + 1].setMass(mass1 + mass2);
						System.out.println(" " + bodies[j + 1].getMass() + " " + bodies[j].getMass());

					}
				}
		}

	}

	// for testing purposes
	public static void main(String[] args) {
		BodyGenerator b = new BodyGenerator(15);
		for (int i = 0; i < b.bodies.length; i++) {
			System.out.println(Arrays.toString(b.bodies[i].getPosition()) + " " + b.bodies[i].getMass() + " "
					+ b.bodies[i].getRadius());
		}
	}

}
