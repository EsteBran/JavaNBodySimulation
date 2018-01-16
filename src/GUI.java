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
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class GUI extends Application {
	private int WIDTH = 800;
	private int HEIGHT = 600;
	private long oldTime;
	private double elapsedTime;	// in seconds
	private Canvas canvas;
	private GraphicsContext gc;
	private Universe universe;
	
	
	public static void main(String[] args) {
		launch(args);	// sets up the app and then calls start()
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		universe = new Universe();
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
				double deltaT = (now - oldTime)/1000000000.0;
				onUpdate(deltaT);
				oldTime = now;
			}
		};
		
		timer.start();	// once this timer is started it will call the onUpdate()
						// method once every 1/60 of a second
		
		stage.setTitle("Robot Navigator");
		// Set the scene for this stage
		stage.setScene(scene);
		// Finally, show the primary stage
		stage.show();
	}
	
	public void drawScene(GraphicsContext gc)
	{
		// draw the current state of your map here
		
	}
	
	private void onUpdate(double deltaT)
	{
		elapsedTime += deltaT;
		drawScene(gc);
	}
}