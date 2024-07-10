/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2023
* Instructor: Prof. Brian King
*
* Name: Harrison Halesworth
* Section: 02 - 10am
* Date: 4/10/23
* Time: 10:42 AM
*
* Project: csci205_final_project
* Package: org.Duck.Control
* Class: MenuControl
*
* Description:
*
* ****************************************
*/
package org.Duck.Control;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.Duck.Model.MenuModel;
import org.Duck.View.GameView;
import org.Duck.View.MenuView;

import java.util.Objects;

import static org.Duck.DuckMain.getPrimaryStage;

public class MenuControl {

    /** This will store the current view as a generic Application object */
    private Application currentView;

    /** This will store the MenuView of the application */
    private final MenuView theView;

    /** This will store the MenuModel of the application*/
    private final MenuModel theModel;

    /** This will store the GameView of the application */
    private final GameView theGameView;

    /** This will store the GameControl of the application */
    private GameControl theGameController;

    /**
     * Construct a controller that connects the model and the view for our
     * main menu
     *
     * @param theView
     * @param theModel
     * @param theGameView
     */
    public MenuControl(MenuView theView, MenuModel theModel, GameView theGameView) throws Exception {
        // Instantiate the fields of the MenuControl object
        this.currentView = theView;
        this.theView = theView;
        this.theModel = theModel;
        this.theGameView = theGameView;

        // Initialize event handlers of the Controller
        initEventHandlers();
    }

    /**
     * This is an internal helper method to initialize the event handlers
     */
    private void initEventHandlers() throws Exception {

        // Set the button that says "Start" to switch to scene to GameView i.e. start the game
        this.theView.getGameButton().setOnAction(e -> {
            try {
                handleActionEvent(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Set the button that says "Quit" to quit out of the application
        this.theView.getQuitButton().setOnAction(e -> Platform.exit());
    }

    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    public void handleActionEvent(ActionEvent event) throws Exception {
        // Set the currentView to be theGameView
        this.currentView = this.theGameView;

        // Set a new scene with the GameView attribute as the root and import the styling
        Scene newScene = new Scene(this.theGameView.getRoot(),900,500);
        newScene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/DuckHunt/GamePlay.css"))
                        .toExternalForm());

        // Initialize the styling and the Scene Graph of the new scene
        this.theGameView.initStyling();
        this.theGameView.initSceneGraph();

        // Set the scene with the new scene and instantiate a controller for the game
        getPrimaryStage().setScene(newScene);
        this.theGameController = new GameControl(theView,theModel,theGameView);

    }
}

