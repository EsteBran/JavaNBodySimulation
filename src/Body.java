// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * Body class implements a 2D body with a position, velocity, and mass.
 * the forceFrom method calculates force between two bodies using newton's law of gravity given two bodies, their masses, and their positions
 * the move method calculates the velocity of a planet given a force vector and time 
 */

public class Body {
	
	private Vector velocity;	//velocity
	private Vector position;	//position
	private double radius;
	private double mass;	//mass
	private final double gConstant = 6.67e-11; //gravitational constant
	
	//body constructor
	public Body (Vector velocity, Vector position, double mass, double radius) {
		this.velocity = velocity;
		this.position = position;
		this.mass = mass;
		this.radius = radius;
	}
	//if planet hits the bounds of the window bounces back
	public void bounceOffVerticalWall() {

		this.setVelocity(-velocity.components()[0], this.velocity.components()[1]);
	}
	//if planet hits the bounds of the window bounces back
	public void bounceOffHorizontalWall() {
		
		this.setVelocity(this.velocity.components()[0], -velocity.components()[1]);
	}
	
	//applies force f for time in seconds, moving the body a proportional amount
	public void move(Vector force, double time) {
		Vector a = force.times(1/mass);
		velocity = velocity.plus(a.times(time));
		position = position.plus(velocity.times(time));
		
		//if position goes out of bounds of the window it bounces back
		if (position.components()[0] >= 4e11 - radius*10e8) {
			bounceOffVerticalWall();
		}
		if (position.components()[0] <= -4e11) {
			bounceOffVerticalWall();
		}
		if (Math.abs(position.components()[1]) >= 4e11) {
			bounceOffHorizontalWall();
		}
		
	}
	
	//returns a vector in the direction of body b with a magnitude of totalForce
	public Vector forceFrom (Body b) {
		Body a = this;
		Vector delta = b.position.minus(a.position); //vector that defines the distance between two bodies as a vector
		double distance = delta.magnitude(); //distance between the two bodies
		double totalForce = (gConstant * a.mass * b.mass) / (distance*distance); //total force between two bodies
		return delta.direction().times(totalForce); 
		}

	//returns the position 
	public double[] getPosition() {
		return this.position.components();
	}
	//gets velocity
	public double[] getVelocity() {
		return this.velocity.components();
	}
	//gets mass
	public double getMass() {
		return this.mass;
	}
	//gets radius
	public double getRadius() {
		return this.radius;
	}
	
	//sets the velocity to (x,y), useed in the bounceOffWall methods
	public void setVelocity(double x, double y) {
		double[] velocityData = {x,y};
		this.velocity = new Vector(velocityData);
	}
	
	
}
