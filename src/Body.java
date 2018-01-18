// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * Body class implements a 2D body with a position, velocity, and mass
 */

public class Body {

	private Vector velocity;	//velocity
	private Vector position;	//position
	private final double radius;
	private final double mass;	//mass
	private final double gConstant = 6.67e-11; //gravitational constant
	
	//body constructor
	public Body (Vector velocity, Vector position, double mass, double radius) {
		this.velocity = velocity;
		this.position = position;
		this.mass = mass;
		this.radius = radius;
	}
	
	//applies force f for time in seconds, moving the body a proportional amount
	public void move(Vector force, double time) {
		Vector a = force.times(1/mass);
		velocity = velocity.plus(a.times(time));
		position = position.plus(velocity.times(time));
	}
	
	//returns a vector in the direction of body b with a magnitude of totalForce
	public Vector forceFrom (Body b) {
		Body a = this;
		Vector delta = b.position.minus(a.position); //vector that defines the distance between two bodies as a vector
		double distance = delta.magnitude(); //distance between the two bodies
		double totalForce = (gConstant * a.mass * b.mass) / (distance*distance); //total force between two bodies
		return delta.direction().times(totalForce); 
		}
	
	//detects collision between two bodies, if true creates a new body with the mass of the two bodies
	public boolean detectCollision(Body b) {
		Body a = this;
		Vector delta = b.position.minus(a.position);
		double distance = delta.magnitude();
		if (distance <= (a.radius+b.radius)) {
			return true;
		}
		return false;
	}
	
	//returns the position 
	public double[] getPosition() {
		return this.position.components();
	}
	
	public double[] getVelocity() {
		return this.velocity.components();
	}
	
	public double getMass() {
		return this.mass;
	}
	
	public double getRadius() {
		return this.radius;
	}
	
	
	
}
