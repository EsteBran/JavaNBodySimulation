// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/* 
 * This class lets me use vector math to calculate gravity between n bodies
 * I will be using vector forms of physics equations to calculate gravity and forces on the bodies
 * 
 * This class implements vectors over real numbers
 * For example:
 * x		= (1.0, 2.0, 3.0, 4.0)
 * y		= (4.0, 3.0, 2.0, 1.0)	
 * x + y	= (5.0, 5.0, 5.0, 5.0)		//plus
 * 10x 		= (10.0, 20.0, 30.0, 40.0)	//scale
 * ||x||	= 5.47723...				//magnitude or norm of vector
 * ||x - y||= 4.47214...				//magnitude with further calculations
 * x*y		=	26						//dot product
 */



import java.util.Arrays;

public class Vector {
	
	private int m;			//number of components in the vector
	private double[] data;	//array for vector
	
	//creates a zero vector 
	public Vector(int n) {
        this.m = n;
        this.data = new double[n];
    }
	
	//creates a vector from an array and fills up the array with the components of the vector
	//e.g. Vector x = new Vector(1.0, 2.0, 3.0, 4.0)
	public Vector(double[] data) {
		m = data.length;  
		this.data = new double[m];
		for (int i = 0; i < m; i++) {
			this.data[i] = data[i];
		}
	}
	
	
	//returns the number of components of the vector
	public int numComponents() {
		return m;
	}
	
	//returns the dot product of vector x and y
	public double dot(Vector vector) {
		double sum = 0.0;
		for (int i = 0; i < m; i++) {
			sum = sum + (this.data[i] * vector.data[i]);
		}
		return sum;
	}
	
	//returns the magnitude/length of this vector
	public double magnitude() {
		return Math.sqrt(this.dot(this));
	}
	
    // return the sum of two vectors
    public Vector plus(Vector that) {
        Vector c = new Vector(m);
        for (int i = 0; i < m; i++)
            c.data[i] = data[i] + that.data[i];
        return c;
    }
    

    // return the result of subtracting a vector from that
    public Vector minus(Vector that) {
        Vector c = new Vector(m);
        for (int i = 0; i < m; i++)
            c.data[i] = data[i] - that.data[i];
        return c;
    }
	
 // return the corresponding coordinate
    public double[] components() {
    	return this.data;
    }
    
 //returns the corresponding unit vector
    public Vector direction() {
    	return this.times(1.0/this.magnitude());
    }
	
 // create and return a new object whose value is (this * factor)
    public Vector times(double factor) {
        Vector c = new Vector(m);
        for (int i = 0; i < m; i++)
            c.data[i] = factor * data[i];
        return c;
    }
	
    
    //for testing
    public static void main(String[] args) {
    	double[] xdata = {1.0, 2.0};
    	double[] ydata = {3.0, 4.0};
    	
    	Vector x = new Vector(xdata);
    	Vector y = new Vector(ydata);
    	
    	System.out.println(Arrays.toString(xdata));
    	System.out.println(x.dot(y));
    	System.out.println(x.magnitude());
    	System.out.println(x.minus(y).magnitude());
    	System.out.println(Arrays.toString(x.times(15).data));
    	System.out.println(Arrays.toString(x.components()));
    }
 		
}
