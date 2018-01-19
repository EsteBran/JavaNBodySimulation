import java.util.Random;

// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * creates a set number of bodies with random positions, velocities, and masses
 */
public class BodyGenerator {

	private Random rand;
	private int nBodies;
	private Body[] bodies;
	private double randMass;
	private double radius;

	public Vector getVel() {
		double x = 1e4;
		double y = 1e8;
		double randVelX = (rand.nextDouble() * (y - x));
		double randVelY = (rand.nextDouble() * (y - x));
		double[] randVelData = { randVelX, randVelY };
		Vector randVel = new Vector(randVelData);
		return randVel;
	}

	public Vector getPos() {
		double x1 = 1e4;
		double y1 = 1e14;
		double randPosX = (rand.nextDouble() * (y1 - x1));
		double randPosY = (rand.nextDouble() * (y1 - x1));
		double[] randPosData = { randPosX, randPosY };
		Vector randPos = new Vector(randPosData);
		return randPos;
	}

	public double getMass() {
		double range1 = 1e10;
		double range2 = 1e40;
		randMass = (rand.nextDouble() * (range2 - range1));
		return randMass;
	}
	public int getRandN() {
		int n = rand.nextInt(16);
		return n;
	}

	public BodyGenerator(int n) {
		nBodies = n;
		this.bodies = new Body[nBodies];
		for (int i = 0; i < nBodies; i++) {
			double mass = this.getMass();
			Vector position = this.getPos();
			Vector velocity = this.getVel();
			this.bodies[i] = new Body(position, velocity, mass, this.radius);
		}
	}

	public Body[] getBodies() {
		return this.bodies;
	}
	
	public static void main(String[] args) {
		BodyGenerator b = new BodyGenerator(15);
		System.out.println(b.nBodies);
		
	}

}
