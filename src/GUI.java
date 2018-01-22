// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * This class is the graphical user interface (GUI). It parses the universe from the Universe class and calculates their
 * positions 60 times a second using the Body class. Finally, it shows the positions of the planets 60 times a second.
 */

import javafx.scene.control.Button;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

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

	// buttons for exit, start, and random
	private Button start;
	private Button exit;
	private Button random;

	private Universe universe;
	private Body[] planets;
	private BodyGenerator bodyGen;
	private Body[] randPlanets;
	private double gridX = 10e8, gridY = 10e8; // the scaling factor for transferring calculations from planets with the
												// mass of earth and showing them on this screen
	private int t = 1500000; // time scale
	private boolean isRandom;

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

		// start button for starting simulation
		start = new Button("Start");
		start.setLayoutX(WIDTH / 2);
		start.setLayoutY(HEIGHT / 2 - 50);

		// random button which simulates the randomly generated bodies
		random = new Button("Random Planets");
		random.setLayoutX(WIDTH / 2);
		random.setLayoutY(HEIGHT / 2);

		// exit button for exiting window
		exit = new Button("Exit");
		exit.setLayoutX(WIDTH / 2);
		exit.setLayoutY(HEIGHT / 2 + 50);

		// initial pane, shows game menu (e.g. start, exit, etc.)
		Pane init = new Pane();
		init.getChildren().add(start);
		init.getChildren().add(canvas);
		init.getChildren().add(exit);
		init.getChildren().add(random);

		// root pane, shows simulation from premade
		Pane root = new Pane();
		root.getChildren().add(canvas);

		Scene scene = new Scene(root);
		Scene scene2 = new Scene(init, WIDTH, HEIGHT);

		stage.setTitle("NBody Simulation");
		// Set the scene for this stage
		stage.setScene(scene2);

		// if start button is pressed starts simulation with premade
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(scene);
				isRandom = false;
			}
		});

		// if exit button is pressed closes the application
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});

		// if random button is pressed asks for number of planets to simulate and
		// simulates them
		random.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(scene);
				isRandom = true;

			}
		});

		// setup a timer to be used for repeatedly redrawing the scene
		oldTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				double deltaT = (now - oldTime) / 1000000000.0;
				if (isRandom == false) {
					onUpdateStart(deltaT);
					oldTime = now;
				}
				if (isRandom == true) {
					onUpdateRandom(deltaT);
					oldTime = now;
				}
			}
		};

		timer.start(); // once this timer is started it will call the onUpdate()
						// method once every 1/60 of a second

		// Finally, show the primary stage
		stage.show();
	}

	public void drawSceneStart(GraphicsContext gc) {
		planets = universe.getBodies();

		// paints the canvas white every frame so that the planet image doesn't overlay
		// itself every successive time it's drawn
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		// draws premade universe
		this.drawUniverse();
	}

	// draws the generated bodies
	public void drawSceneRandom(GraphicsContext gc) {
		randPlanets = bodyGen.getBodies();

		// paints the canvas white every frame so that the planet image doesn't overlay
		// itself every successive time it's drawn
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		// draws the randomly generated bodies
		this.drawBodyGen();
	}

	// iterates through the planets and draws one planet for each planet in the
	// array
	private void drawUniverse() {
		gc.setStroke(Color.BLACK);
		for (int i = 0; i < planets.length; i++) {
			double[] position = planets[i].getPosition();
			double xPos = WIDTH / 2 + position[0] / gridX;
			double yPos = HEIGHT / 2 + position[1] / gridY;
			double radius = planets[i].getRadius();
			gc.strokeOval(xPos, yPos, radius, radius);
		}

	}

	// draws the random planets using the same method above
	private void drawBodyGen() {
		gc.setStroke(Color.BLUE);
		for (int i = 0; i < randPlanets.length; i++) {
			double[] position = randPlanets[i].getPosition();
			double xPos = WIDTH / 2 + position[0] / gridX;
			double yPos = HEIGHT / 2 + position[1] / gridY;
			double radius = randPlanets[i].getRadius();
			gc.strokeOval(xPos, yPos, radius, radius);
		}

	}

	// on each update updates the positions of each of the planets in the universe
	private void onUpdateStart(double deltaT) {
		elapsedTime += deltaT;
		drawSceneStart(gc);
		universe.updateUniverse(t * deltaT);

	}

	private void onUpdateRandom(double deltaT) {
		elapsedTime += deltaT;
		drawSceneRandom(gc);
		bodyGen.updateUniverse(t * deltaT);
	}

}