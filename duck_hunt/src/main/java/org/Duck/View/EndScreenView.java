/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2023
 * Instructor: Prof. Brian King
 *
 * Name: Harrison Halesworth
 * Section: 02 - 10am
 * Date: 4/19/23
 * Time: 10:30 AM
 *
 * Project: csci205_final_project
 * Package: org.Duck.View
 * Class: EndScreenView
 *
 * Description:
 *
 * ****************************************
 */
package org.Duck.View;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static org.Duck.View.GameView.getCountScore;

public class EndScreenView extends Application {
    private static Label menuLbl;
    private StackPane root;
    private Stage stage;
    private Scene endScreenScene;

    public EndScreenView () {
        initSceneGraph();
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        initSceneGraph();
        endScreenScene = new Scene(root, 900, 500);
        primaryStage.setScene(endScreenScene);
        endScreenScene.getStylesheets().add(
                getClass().getResource("/DuckHunt/EndScreen.css")
                        .toExternalForm());
        primaryStage.setScene(endScreenScene);
        primaryStage.show();
    }
    /**
     * Initialize the entire scene graph
     */
    public void initSceneGraph() {
        root = new StackPane();
        root.setId("background");
        BorderPane menu = new BorderPane();

        // Initialize Label
        menuLbl = new Label();
        menuLbl.setText("Score " + getCountScore());
        menuLbl.setId("menuLbl");
        menu.setTop(menuLbl);

        root.getChildren().add(menu);
    }

    /**
     * @return the root
     */
    public Parent getRoot() {
        return root;
    }

    public static Label getMenuLbl() {
        return menuLbl;
    }
}
