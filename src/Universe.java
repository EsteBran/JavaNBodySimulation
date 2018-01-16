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
	
	Scanner scan = new Scanner(System.in);
	private final int n; 			//number of bodies/planets
	private final Body[] bodies; 	//array of bodies
	
	public Universe() {
		//this will be the number of bodies
		n = scan.nextInt();
		
		//reads the n bodies and their velocity and positions
		bodies = new Body[n];
		for (int i = 0; i < n; i++) {
			double xPos = scan.nextDouble();
			double yPos = scan.nextDouble();
			double xVel = scan.nextDouble();
			double yVel = scan.nextDouble();
			double mass = scan.nextDouble();
			double[] positionData = {xPos, yPos};
			double[] velocityData = {xVel, yVel};
			Vector position = new Vector(positionData);
			Vector velocity = new Vector(velocityData);
			bodies[i] = new Body(position, velocity, mass);
			
		}
		
	}
}
