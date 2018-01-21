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
		int i = rand.nextInt(15);
		int j = 0;
		if (rand.nextBoolean()) {
			j = -1;
		} else {
			j = 1;
		}
		double randVelX = (Math.pow(10, i) * rand.nextDouble()*j);
		double randVelY = (Math.pow(10, i) * rand.nextDouble()*j);
		double[] randVelData = { randVelX, randVelY };
		Vector randVel = new Vector(randVelData);
		return randVel;
	}

	// creates a random position
	public Vector getPos() {
		int i = rand.nextInt(20);
		int j = 0;
		if (rand.nextBoolean()) {
			j = -1;
		} else {
			j = 1;
		}
		double randPosX = (Math.pow(10, i) * rand.nextDouble()*j);
		double randPosY = (Math.pow(10, i) * rand.nextDouble()*j);
		double[] randPosData = { randPosX, randPosY };
		Vector randPos = new Vector(randPosData);
		return randPos;
	}

	public double getMass() {
		int i = rand.nextInt(40) + 10;
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
		for (int i = 0; i < nBodies; i++) {
			double mass = this.getMass();
			Vector position = this.getPos();
			Vector velocity = this.getVel();
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
			this.bodies[i] = new Body(velocity, position, mass, radius);
		}
	}

	public Body[] getBodies() {
		return this.bodies;
	}

	public static void main(String[] args) {
		BodyGenerator b = new BodyGenerator(15);
		for (int i = 0; i < b.bodies.length; i++) {
			System.out.println(Arrays.toString(b.bodies[i].getVelocity()));
		}
	}

}
