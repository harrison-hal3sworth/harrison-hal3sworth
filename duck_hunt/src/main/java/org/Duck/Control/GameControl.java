/* *****************************************
 * CSCI205 -Software Engineering and Design
 * Spring2023* Instructor: Prof. Brian King
 *
 * Name: Jamie Miller
 * Section: 10AM
 * Date: 4/14/2023
 * Time: 9:58 AM
 *
 * Project: csci205_final_project
 * Package: org.Duck.Control
 * Class: GameControl
 *
 * Description:
 *
 * ****************************************
 */

package org.Duck.Control;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.Duck.Model.MenuModel;
import org.Duck.View.EndScreenView;
import org.Duck.View.GameView;
import org.Duck.View.MenuView;
import java.util.Random;
import java.util.Objects;

import static org.Duck.DuckMain.getPrimaryStage;
import static org.Duck.View.EndScreenView.getMenuLbl;
import static org.Duck.View.GameView.getCountScore;

public class GameControl {
    /** Hit box for the duck */
    private Circle circle;
    /** Image used for the duck */
    private Image duck;
    /** Location of the duck image */
    private static final String IMAGE_PATH = "birdAnimation.gif";
    /** View of the duck image */
    private ImageView duckView;
    /** The current view being used */
    private Application currentView;
    /** MenuView instance used for the game */
    private final MenuView theView;
    /** EndScreenView instance used for the game */
    private final EndScreenView endView;
    /** MenuModel instance used for the game */
    private final MenuModel theModel;
    /** GameView instance used for the game */
    private final GameView theGameView;
    /** EndScreenControl used for the game */
    private EndScreenControl endScreenControl;
    /** Boolean used for if the duck has been clicked */
    private static boolean isClicked = false;
    /** Integer used to track the number of shots remaining */
    private static int shots;
    /** Integer used to track the number of ducks hit */
    private static int ducksHit;
    /** Basic Pane used for movement of the duck */
    private Pane pane;
    /** Transition for the duck image */
    private PathTransition transition;
    /** Transition used for the circle hit box */
    private PathTransition transition2;
    /** Value for the X plane minimum value */
    private final int MINX = -1000;
    /** Value for the X plane maximum value */
    private final int MAXX = 1000;

    /**
     * Event Handler used for a missed hit that decrements the shot count and ends the game if
     * no shots remain
     */
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            shots--;
            transition.stop();
            transition2.stop();
            if(shots > 0){
                spawnCircle();
            } else{
                pane.getChildren().remove(duckView);
                pane.getChildren().remove(circle);
                try {
                    endScreenView();
                } catch (Exception ignored) {}
            }
        }
    };

    /**
     * Event Handler used for a hit shot that increments the number of ducks hit and ends the game if
     * 7 ducks are hit
     */
    EventHandler<MouseEvent> eventHandler2 = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            ducksHit++;
            shots++;
            transition.stop();
            transition2.stop();
            isClicked = true;
            getMenuLbl().setText("Score " + getCountScore());
            if(shots > 0 && ducksHit < 7){
                spawnCircle();
            } else {
                getMenuLbl().setText("Max Score");
                try {
                    endScreenView();
                } catch (Exception ignored) {}
            }
        }
    };

    /**
     * COnstructor used to generate a Game instance
     *
     * @param theView - MenuView being used
     * @param theModel - MenuModel being used
     * @param theGameView - GameView being used
     */
    public GameControl(MenuView theView, MenuModel theModel, GameView theGameView){
        this.theView = theView;
        this.theModel = theModel;
        this.theGameView = theGameView;
        ducksHit = 0;
        shots = 3;
        this.endView = new EndScreenView();

        initEventHandlers();
        spawnCircle();
    }

    /**
     * Generates the circle used as the hit box and the duck image
     */
    private void createCircle(){
        duck = new Image(IMAGE_PATH);
        duckView = new ImageView(duck);
        duckView.setFitWidth(150);
        duckView.setFitHeight(150);
        circle = new Circle(200, 150, 50, Color.TRANSPARENT);
        circle.setOpacity(0.7);
    }

    /**
     * Spawns the circle and duck image on the pane
     */
    private void spawnCircle() {
        isClicked = false;
        StackPane root = new StackPane();
        pane = new Pane();
        pane.getChildren().add(duckView);
        pane.getChildren().add(circle);
        root.getChildren().addAll(this.theGameView.getRoot(),pane);
        Scene scene = new Scene(root,900,500);
        duckView.setLayoutX(250);
        duckView.setLayoutY(250);
        circle.setLayoutX(250);
        circle.setLayoutY(250);
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/DuckHunt/GamePlay.css"))
                        .toExternalForm());
        this.theGameView.initStyling();
        this.theGameView.initSceneGraph();

        getPrimaryStage().setScene(scene);

        moveCircle();
    }

    /**
     * Handles the events of the screen being clicked and calls createCircle()
     */
    private void initEventHandlers(){
        createCircle();
        getPrimaryStage().addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler2);
    }

    /**
     * starts the transitions to random parts of the screen within set boundaries
     */
    private void moveCircle(){
        transition = new PathTransition();
        transition2 = new PathTransition();
        Line line = new Line();
        line.setStartX(0);
        line.setStartY(-110);
        Random rand = new Random();
        line.setEndX(rand.nextInt(MAXX-MINX)+MINX);
        line.setEndY(-1000);
        line.setLayoutX(250);
        line.setLayoutY(250);
        transition.setNode(duckView);
        transition2.setNode(circle);
        transition.setPath(line);
        transition2.setPath(line);
        transition.setDuration(Duration.seconds(5));
        transition2.setDuration(Duration.seconds(5));
        transition.play();
        transition2.play();
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane.getChildren().remove(duckView);
                spawnCircle();
            }
        });
        transition2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pane.getChildren().remove(circle);
            }
        });
    }

    /**
     * @return the number of shots remaining
     */
    public static int getShots() {
        return shots;
    }

    /**
     * @return the number of ducks hit
     */
    public static int getDucksHit() {
        return ducksHit;
    }

    /**
     * Switches the view from the game view to the end screen view if the number of shots hits zero
     * or if the number of ducks hit reaches seven
     */
    public void endScreenView(){
        // Set the currentView to be theGameView
        this.currentView = this.endView;

        // Set a new scene with the GameView attribute as the root and import the styling
        Scene newScene = new Scene(this.endView.getRoot(),900,500);
        newScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/DuckHunt/EndScreen.css"))
                        .toExternalForm());

        // Initialize the styling and the Scene Graph of the new scene
        this.endView.initSceneGraph();

        // Set the scene with the new scene and instantiate a controller for the game
        getPrimaryStage().setScene(newScene);
    }
}
