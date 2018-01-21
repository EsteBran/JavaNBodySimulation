// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * This class is the graphical user interface (GUI). It parses the universe from the Universe class and calculates their
 * positions 60 times a second using the Body class. Finally, it shows the positions of the planets 60 times a second.
 */



import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class GUI extends Application {
	private int WIDTH = 1000;
	private int HEIGHT = 800;
	private long oldTime;
	private double elapsedTime;
	private Canvas canvas;
	private GraphicsContext gc;
	private Universe universe;
	private Body[] planets;
	private BodyGenerator bodyGen;
	private Body[] randPlanets;
	private double gridX, gridY; // the scaling factor for transferring calculations from planets with the mass
									// of earth and showing them on this screen

	public static void main(String[] args) {
		launch(args); // sets up the app and then calls start()

	}

	@Override
	public void start(Stage stage) throws Exception {

		// initializes the universe and bodygenerator class
		universe = new Universe();
		bodyGen = new BodyGenerator(5);

		canvas = new Canvas(WIDTH, HEIGHT);
		gc = canvas.getGraphicsContext2D();

		Pane root = new Pane();
		root.getChildren().add(canvas);

		// Create your scene using the root pane
		Scene scene = new Scene(root);

		// setup a timer to be used for repeatedly redrawing the scene
		oldTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				double deltaT = (now - oldTime) / 1000000000.0;
				onUpdate(deltaT);
				oldTime = now;
			}
		};

		timer.start(); // once this timer is started it will call the onUpdate()
						// method once every 1/60 of a second

		stage.setTitle("NBody Simulation");
		// Set the scene for this stage
		stage.setScene(scene);
		// Finally, show the primary stage
		stage.show();
	}

	public void drawScene(GraphicsContext gc) {
		planets = universe.getBodies();
		randPlanets = bodyGen.getBodies();

		// paints the canvas white every frame so that the planet image doesn't overlay
		// itself every successive time it's drawn
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		// scaling factor, everyting is scaled down by a factor of this so that it can
		// be displayed on screen
		gridX = 10e8;
		gridY = 10e8;

		// draws the randomly generated bodies as well as premade ones
		this.drawUniverse();
		this.drawBodyGen();

	}

	// iterates through the planets and draws one planet for each planet in the
	// array
	private void drawUniverse() {
		gc.setStroke(Color.BLACK);
		for (int i = 0; i < planets.length; i++) {
			while (!(planets[i]==null)) {
			double[] position = planets[i].getPosition();
			double xPos = WIDTH / 2 + position[0] / gridX;
			double yPos = HEIGHT / 2 + position[1] / gridY;
			double radius = planets[i].getRadius();
			gc.strokeOval(xPos, yPos, radius, radius);
			}
		}
	}

	// draws the random planets
	private void drawBodyGen() {
		gc.setStroke(Color.BLUE);
		for (int i = 0; i < randPlanets.length; i++) {
			while (!(planets[i]==null)) {
			double[] position = randPlanets[i].getPosition();
			double xPos = WIDTH / 2 + position[0] / gridX;
			double yPos = HEIGHT / 2 + position[1] / gridY;
			double radius = randPlanets[i].getRadius();
			gc.strokeOval(xPos, yPos, radius, radius);
			}
		}

	}

	private void onUpdate(double deltaT) {
		int t = 1500000;
		elapsedTime += deltaT;
		drawScene(gc);
		universe.updateUniverse(t * deltaT);
		bodyGen.updateUniverse(t * deltaT);
		

	}

}