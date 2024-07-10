/* ***************************************** * CSCI205 - Software Engineering and Design * Spring 2023
 * Instructor: Prof. Brian King
 *
 * Name: Conor McNichols
 * Section: 9am/10am  * Date: 4/5/23
 * Time: 10:36 AM
 *
 * Project: csci205_final_project
 * Package: org.Duck * Class: GameView
 *
 * Description:
 *
 * **************************************** */
package org.Duck.View;


import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

import static org.Duck.Control.GameControl.*;


public class GameView extends Application {

    /** Borderpane for the game view*/
    private BorderPane backgroundRoot;
    /** scene for game view */
    private Scene gameScene;
    /** primary stage for the game view */
    private Stage stage;
    /** the label box which contains the images to
     * keep track of the shots left*/
    private Label shotCount;
    /** the label box to keep track of ducks hit and
     * ducks left to hit using duck images */
    private Label duckHitCount;
    /** label box to keep track of score based on
     * ducks hit */
    private Label scoreCount;

    /** integer to count the score displayed in the
     * score count */
    private static int countScore = 0;


    /** Images for the ducks hit*/
    Image ducksLeftImage = new Image("/ducksLeft.png");
    Image ducksHitImage = new Image("/duckHit.png");
    ImageView ducksHitImageView = new ImageView(ducksHitImage);

    /** Pane containing the Array of images of ducks hit and left */
    Pane ducksLeftPane = new Pane();

    /** Image for the shots left */
    Image shotsLeftImage = new Image("/shotCountImage.png");
    /** Pane containing the array of images for shots left */
    Pane shotsLeftPane = new Pane();
    /** an array of images which displays the shots you have left
     * and is altered if  shot is missed
     */
    private static ArrayList<ImageView> shotsimageBar;


    /**
     * Constructor to initialize the scene graph
     */
    public GameView() {
        initSceneGraph();
    }

    /**
     * Initialize the entire scene graph
     */
    public void initSceneGraph(){
        backgroundRoot = new BorderPane();
        backgroundRoot.setId("background");

        shotCount = new Label();
        shotCount.setId("shotCount");

        duckHitCount = new Label();
        duckHitCount.setId("duckHitCount");

        scoreCount = new Label();
        scoreCount.setId("scoreCount");
    }

    /**
     * Intialize the styling for the context of the scene graph
     */
    public void initStyling(){
        shotCount.setTranslateY(370);

        duckHitCount.setTranslateY(207);

        ArrayList<ImageView> ducksimageBar = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            ImageView duckImageView = new ImageView(ducksLeftImage);
            duckImageView.setFitHeight(38);
            duckImageView.setFitWidth(38);
            duckImageView.setTranslateY(15);
            duckImageView.setTranslateX(i * 40);
            ducksimageBar.add(duckImageView);
        }

        for (Node node : ducksimageBar) {
            ducksLeftPane.getChildren().add(node);
        }

        shotsimageBar = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ImageView shotImageView = new ImageView(shotsLeftImage);
            shotImageView.setFitHeight(38);
            shotImageView.setFitWidth(38);
            shotImageView.setTranslateY(15);
            shotImageView.setTranslateX(i * 40);
            shotsimageBar.add(shotImageView);
        }

        for (Node node : shotsimageBar) {
            shotsLeftPane.getChildren().add(node);
        }

        shotCount.setGraphic(shotsLeftPane);


        ducksHitImageView.setFitHeight(38);
        ducksHitImageView.setFitWidth(38);

        duckHitCount.setGraphic(ducksLeftPane);

        ChangeToHit(ducksimageBar);

        shotMissCount(shotsimageBar);

        scoreCount.setTranslateY(370);
        scoreCount.setText("Score: " + countScore);

        backgroundRoot.setLeft(shotCount);
        backgroundRoot.setCenter(duckHitCount);
        backgroundRoot.setRight(scoreCount);
    }


    /**
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     *
     * USED FOR TESTING ONLY
     */

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        initSceneGraph();
        initStyling();
        gameScene= new Scene(backgroundRoot, 900, 500);
        primaryStage.setScene(gameScene);
        gameScene.getStylesheets().add(
                getClass().getResource("/GamePlay.css")
                        .toExternalForm());
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }



    /**
     * method which changes the ducks in the ducksHitBar from white to red
     * if the duck is hit
     * @param imageBar
     */

    private void ChangeToHit(ArrayList<ImageView> imageBar) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int ducksHit = 0;
                while (ducksHit < 7) {
                    ImageView nodeAtIndex = imageBar.get(ducksHit);
                    if (getDucksHit() > ducksHit) {
                        ducksHit++;
                        IncrementScore();
                        nodeAtIndex.setImage(ducksHitImage);
                    }
                    Thread.sleep(50);
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }


    /**
     * Method which alters the bar showing how many shots you have left and how many
     * you have missed, ending the game if you miss three before completing all your
     * ducks.
     * @param shotsimageBar
     */
    private void shotMissCount(ArrayList<ImageView> shotsimageBar) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int shotsHit = 3;
                while (shotsHit > 0) {
                    if (getShots() < shotsHit) {
                        shotsHit--;
                        ImageView nodeAtIndex = shotsimageBar.get(shotsHit);
                        nodeAtIndex.setImage(null);
                    }
                    Thread.sleep(100);
                }
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }


    /**
     * getter for the array of images displaying shots
     * @return getShotsImageBar
     */
    public static ArrayList getshotsImageBar(){
        return shotsimageBar;
    }

    /**
     * getter for the background root
     * @return backgroundRoot
     */
    public Parent getRoot() {
        return backgroundRoot;
    }

    /**
     * Very basic method used to increment the score
     */
    static void IncrementScore() {
        countScore += 50;
    }

    public static int getCountScore() {
        return countScore;
    }
}

