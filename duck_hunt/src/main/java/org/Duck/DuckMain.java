/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2023
 * Instructor: Prof. Brian King
 *
 * Name: Harrison Halesworth
 * Section: 02 - 10am
 * Date: 4/12/23
 * Time: 10:29 AM
 *
 * Project: csci205_final_project
 * Package: org.Duck
 * Class: DuckMain
 *
 * Description:
 *
 * ****************************************
 */
package org.Duck;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.Duck.Control.MenuControl;
import org.Duck.Model.MenuModel;
import org.Duck.View.GameView;
import org.Duck.View.MenuView;

public class DuckMain extends Application {

    /** Stores the GameView of the application */
    private GameView theGameView;

    /** Stores the MenuModel of the application */
    private MenuModel theModel;

    /** Stores the MenuView of the application */
    private MenuView theView;

    /** Stores the MenuControl of the application */
    private MenuControl theController;

    /** Stores the current view as a generic Application object */
    private Application currentView;

    /** Stores the Stage primary stage */
    private static Stage stage;


    /**
     * Our standard main program for a JavaFX application
     *
     * @param args (unused)
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Store the Stage primaryStage as an attribute
        this.stage = primaryStage;

        // Set a new Scene with theView as the root
        Scene scene = new Scene(this.theView.getRoot(),900,500);

        // Import css
        scene.getStylesheets().add(
                getClass().getResource("/DuckHunt/Menu.css")
                        .toExternalForm());

        // Set the title, the scene and show the application
        primaryStage.setTitle("DUCK HUNT");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Instantiate each component of the DuckHunt game in order to run it
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        // Call the init() method of the Application class
        super.init();

        // Instantiate each Model, View, and Control needed to run DuckHunt
        this.theGameView = new GameView();
        this.currentView = theView;
        this.theModel = new MenuModel(theView);
        this.theView = new MenuView(theModel);
        this.theController = new MenuControl(theView, theModel, theGameView);
    }

    /**
     * Returns the Stage primaryStage
     *
     * @return primaryStage
     */
    public static Stage getPrimaryStage() {return stage;}
}
