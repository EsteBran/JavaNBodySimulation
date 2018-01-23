// Tawseef Hanif
// Culminating Assignment
// ICS3U
// Mr. Radulovic

/*
 * This class is the graphical user interface (GUI). It parses the universe from the Universe class and calculates their
 * positions 60 times a second using the Body class. Finally, it shows the positions of the planets 60 times a second.
 */

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUI extends Application {
	private int WIDTH = 800;
	private int HEIGHT = 800;
	private long oldTime;
	private double elapsedTime;
	private Canvas canvas;
	private GraphicsContext gc;

	// buttons for exit, start, and random
	private Button start;
	private Button exit;
	private Button random;
	private Button back;

	// universe, its planets, bodygenerator, its planets
	private Universe universe;
	private Body[] planets;
	private BodyGenerator bodyGen;
	private Body[] randPlanets;
	private double gridX = 10e8, gridY = 10e8; // the scaling factor for transferring calculations from planets with the
												// mass of earth and showing them on this screen
	private double t = 1500000; // time scale
	private boolean isRandom; // boolean that checks if user wants to produce random planets or use premade
								// ones

	public static void main(String[] args) {
		launch(args); // sets up the app and then calls start()

	}

	@Override
	public void start(Stage stage) throws Exception {

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

		// goes back to game menu
		back = new Button("Back");
		back.setLayoutX(50);
		back.setLayoutY(750);

		// takes in an int and generates that many planets
		TextField input = new TextField();
		input.setLayoutX(WIDTH / 2 - 160);
		input.setLayoutY(HEIGHT / 2);

		Text title = new Text("N BODY SIMULATOR ");
		title.setFill(Color.BLACK);
		title.setFont(Font.font("Verdana", 20));
		title.setLayoutY(200);
		title.setLayoutX(WIDTH / 2 - 100);

		// instruction to play the game
		Text subtext = new Text(
				" - Press Start to simulate premade planets \n - Input an integer into the text field and press Random to simulate \n   n-random planets \n - Press exit to exit");
		subtext.setFill(Color.BLACK);
		subtext.setFont(Font.font("Verdana", 15));
		subtext.setLayoutY(250);
		subtext.setLayoutX(WIDTH / 2 - 180);

		// sets the colour of the menu to be Grey
		Rectangle r = new Rectangle(WIDTH, HEIGHT);
		r.setFill(Color.LIGHTGREY);

		// initial pane, shows game menu (e.g. start, exit, etc.)
		Pane init = new Pane();
		init.getChildren().addAll(r, title, subtext, start, canvas, exit, random, input);

		// root pane, shows simulation from premade
		Pane root = new Pane();
		root.getChildren().addAll(canvas, back);

		Scene scene = new Scene(root);
		Scene scene2 = new Scene(init, WIDTH, HEIGHT);

		// title and scene
		stage.setTitle("NBody Simulation");
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

		// if random button is pressed after inputting a number
		// simulates that many planets
		random.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// checks if a number is put into the textfield, if nothing it put there or it
				// isn't a number nothing happens
				if (isInt(input.getText())) {
					isRandom = true;
					stage.setScene(scene);
					bodyGen = new BodyGenerator(Integer.parseInt(input.getText()));
				}
				if (input.getText() == null) {
					System.out.println("Please enter a number");
				}
			}
		});

		// if back is pressed goes back to previous scene(game menu) and initializes a new universe (so if start is pressed again it shows a new system based on random integer)
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(scene2);
				universe = new Universe();
			}
		});

		// initializes the universe
		universe = new Universe();

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

	// checks if the input entered in the textfield is true
	public boolean isInt(String message) {
		try {
			Integer.parseInt(message);
			return true;
		} catch (NumberFormatException e) {
		}

		return false;
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

	// on each update updates the positions of each of the planets in the randomly
	// generated system
	private void onUpdateRandom(double deltaT) {
		elapsedTime += deltaT;
		drawSceneRandom(gc);
		bodyGen.updateUniverse(t * deltaT);
	}

}