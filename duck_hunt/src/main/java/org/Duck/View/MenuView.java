/* *****************************************
 * CSCI205 -Software Engineering and Design
 * Spring2023* Instructor: Prof. Brian King
 *
 * Name: Jamie Miller
 * Section: 10AM
 * Date: 4/5/2023
 * Time: 10:48 AM
 *
 * Project: csci205_final_project
 * Package: org.Duck
 * Class: MenuView
 *
 * Description:
 *
 * ****************************************
 */

package org.Duck.View;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.Duck.Model.MenuModel;

public class MenuView extends Application {
    /** Root for the javafx of type {@link StackPane} */
    private StackPane root;
    /** The top layer of the menu of type {@link BorderPane} */
    private BorderPane menu;
    /** The {@link Button} used to start the game */
    private Button gameButton;
    /** The {@link Button} used to quit the game */
    private Button quitButton;
    /** The title of the game of type {@link Label} */
    private Label menuLbl;

    /**
     * Simple constructor for the menu view
     *
     * @param theModel that is connected to this view
     */
    public MenuView (MenuModel theModel) {
        initSceneGraph();
    }

    /**
     * @return the game start button
     */
    public Button getGameButton() {
        return gameButton;
    }

    /**
     * @return the quit button
     */
    public Button getQuitButton() {
        return quitButton;
    }

    @Override
    public void start(Stage primaryStage) {
        initSceneGraph();
        Scene scene = new Scene(root, 900, 500);
        scene.getStylesheets().add(
                getClass().getResource("/DuckHunt/Menu.css")
                        .toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initialize the entire scene graph
     */
    private void initSceneGraph(){
        root = new StackPane();
        root.setId("background");
        menu = new BorderPane();

        // Initialize Buttons
        gameButton = new Button("Play Game");
        quitButton = new Button ("Quit");

        // Initialize Label
        menuLbl = new Label();
        menuLbl.setText("Duck Hunter");
        menuLbl.setId("menuLbl");

        menu.setTop(menuLbl);
        menu.setCenter(gameButton);
        menu.setBottom(quitButton);

        root.getChildren().add(menu);
    }

    /**
     * @return the root
     */
    public Parent getRoot() {
        return root;
    }
}
